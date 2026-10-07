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
    const val ADMIN_EMAIL = "djoudimadani09@gmail.com"

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

                val jsonObj = JSONObject()
                jsonObj.put("_subject", "[Polylang Hub] $eventType: ${userName.ifBlank { "عميل جديد" }}")
                jsonObj.put("_replyto", userEmail.ifBlank { ADMIN_EMAIL })
                jsonObj.put("_captcha", "false")
                jsonObj.put("eventType", eventType)
                jsonObj.put("timestamp", timestamp)
                jsonObj.put("userName", userName.ifBlank { "غير مسجل / زائر" })
                jsonObj.put("userEmail", userEmail.ifBlank { "لم يحدد" })
                jsonObj.put("targetAdmin", ADMIN_EMAIL)

                for ((key, value) in details) {
                    jsonObj.put(key, value)
                }

                val url = URL("https://formsubmit.co/ajax/$ADMIN_EMAIL")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                conn.setRequestProperty("Accept", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 7000
                conn.readTimeout = 7000

                val writer = OutputStreamWriter(conn.outputStream, "UTF-8")
                writer.use {
                    it.write(jsonObj.toString())
                    it.flush()
                }

                val code = conn.responseCode
                success = (code in 200..299)
                responseMessage = if (success) "تم الإرسال بنجاح إلى $ADMIN_EMAIL" else "كود الاستجابة: $code"
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
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$ADMIN_EMAIL")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {}
    }
}
