package com.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                jsonObj.put("_subject", "[Polylang Android] $eventType: $userName")
                jsonObj.put("_replyto", userEmail)
                jsonObj.put("_captcha", "false")
                jsonObj.put("eventType", eventType)
                jsonObj.put("timestamp", timestamp)
                jsonObj.put("userName", userName)
                jsonObj.put("userEmail", userEmail)

                for ((key, value) in details) {
                    jsonObj.put(key, value)
                }

                val url = URL("https://formsubmit.co/ajax/$ADMIN_EMAIL")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                conn.setRequestProperty("Accept", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 6000
                conn.readTimeout = 6000

                val writer = OutputStreamWriter(conn.outputStream, "UTF-8")
                writer.use {
                    it.write(jsonObj.toString())
                    it.flush()
                }

                val code = conn.responseCode
                success = (code in 200..299)
                responseMessage = if (success) "تم الإرسال بنجاح إلى الإدارة" else "كود الاستجابة: $code"
                conn.disconnect()
            } catch (e: Exception) {
                success = false
                responseMessage = e.localizedMessage ?: "تعذر الإرسال"
            }

            kotlinx.coroutines.withContext(Dispatchers.Main) {
                onResult(success, responseMessage)
            }
        }
    }
}
