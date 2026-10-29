package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AdminEmailNotifier {
    private const val DEFAULT_ADMIN_EMAIL = "support@yourdomain.com"

    private fun adminEmail(): String {
        return BuildConfig.ADMIN_EMAIL.ifBlank { DEFAULT_ADMIN_EMAIL }
    }

    fun dispatch(
        eventType: String,
        userName: String,
        userEmail: String,
        details: Map<String, String>,
        onResult: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var success = false
            var responseMessage = ""
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val timestamp = dateFormat.format(Date())
                val targetAdmin = adminEmail()

                val jsonObj = JSONObject()
                jsonObj.put("_subject", "[Polylang Hub] $eventType: ${userName.ifBlank { "عميل جديد" }}")
                jsonObj.put("_replyto", userEmail.ifBlank { targetAdmin })
                jsonObj.put("_captcha", "false")
                jsonObj.put("eventType", eventType)
                jsonObj.put("timestamp", timestamp)
                jsonObj.put("userName", userName.ifBlank { "غير مسجل / زائر" })
                jsonObj.put("userEmail", userEmail.ifBlank { "لم يحدد" })
                jsonObj.put("targetAdmin", targetAdmin)

                for ((key, value) in details) {
                    jsonObj.put(key, value)
                }

                val url = URL("https://formsubmit.co/ajax/$targetAdmin")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("User-Agent", "PolylangHub-Android/2.0")
                conn.doOutput = true
                conn.connectTimeout = 8000
                conn.readTimeout = 8000

                val writer = OutputStreamWriter(conn.outputStream, "UTF-8")
                writer.use {
                    it.write(jsonObj.toString())
                    it.flush()
                }

                val code = conn.responseCode
                success = (code in 200..299)
                responseMessage = if (success) "تم الإرسال بنجاح إلى $targetAdmin" else "كود الاستجابة: $code"
                conn.disconnect()
            } catch (e: Exception) {
                success = false
                responseMessage = e.localizedMessage ?: "تعذر الاتصال بالخادم"
            }

            withContext(Dispatchers.Main) {
                onResult(success, responseMessage)
            }
        }
    }

    fun openMailClient(context: Context, subject: String, body: String) {
        try {
            val email = adminEmail()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {}
    }
}
