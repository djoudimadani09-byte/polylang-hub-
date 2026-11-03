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

/**
 * Enterprise Notification & Security Service for Polylang Hub.
 * Enforces Fail-Closed security:
 * - Admin authorization is strictly server-gated (fails closed without verified server token).
 * - Client-side role claims or email checks are never accepted as proof of admin privilege.
 * - Network failures report honest error status, never faked success.
 */
object AdminEmailNotifier {
    val OFFICIAL_ADMIN_EMAIL: String = BuildConfig.ADMIN_EMAIL.ifBlank { "admin@example.com" }
    val OFFICIAL_SUPPORT_EMAIL: String = BuildConfig.SUPPORT_EMAIL.ifBlank { "support@example.com" }
    val ADMIN_EMAIL: String = OFFICIAL_ADMIN_EMAIL

    /**
     * Fail-Closed Security Policy:
     * Without a verified backend session token issued by a trusted authentication server,
     * administrative authorization is strictly denied.
     */
    fun isServerAuthenticatedAdmin(authToken: String?): Boolean {
        // Fail-Closed: tokens must be verified by a real server. No client-side mock bypass.
        if (authToken.isNullOrBlank()) return false
        // Placeholder check: In a production deployment, this verifies JWT signature against the backend.
        return false
    }

    /**
     * Sends notification synchronously within coroutine scope.
     * Returns Pair(success, responseMessage).
     * Strictly enforces Fail-Closed error reporting without fake success.
     */
    suspend fun sendNotification(
        eventType: String,
        userName: String,
        userEmail: String,
        details: Map<String, String>
    ): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val baseUrl = BuildConfig.API_BASE_URL
        // Fail closed if backend endpoint is an unconfigured placeholder
        if (baseUrl.isBlank() || baseUrl.contains("example.com")) {
            return@withContext Pair(
                false,
                "فشل الإرسال: الخادم الخلفي غير مهيأ (Backend Unconfigured - Fail-Closed)"
            )
        }

        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val timestamp = dateFormat.format(Date())

            // Data Minimization: strictly filter out any potential credentials or sensitive personal fields
            val sanitizedDetails = details.filterKeys { key ->
                val lower = key.lowercase()
                !lower.contains("pass") && !lower.contains("phone") && !lower.contains("card") &&
                !lower.contains("token") && !lower.contains("secret") && !lower.contains("holder")
            }

            val jsonObj = JSONObject().apply {
                put("source", "PolylangHub-Android")
                put("eventType", eventType)
                put("timestamp", timestamp)
                put("userName", userName.ifBlank { "زائر تجريبي" })
                put("userEmail", userEmail.ifBlank { "demo@example.com" })
                put("details", JSONObject(sanitizedDetails))
            }

            val apiUrl = URL("$baseUrl/notifications")
            val conn = (apiUrl.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                setRequestProperty("Accept", "application/json")
                setRequestProperty("User-Agent", "PolylangHub-Android/${BuildConfig.VERSION_NAME}")
                doOutput = true
                connectTimeout = 5000
                readTimeout = 5000
            }

            try {
                OutputStreamWriter(conn.outputStream, "UTF-8").use {
                    it.write(jsonObj.toString())
                    it.flush()
                }
                val code = conn.responseCode
                if (code in 200..299) {
                    Pair(true, "تم تأكيد الاستلام بنجاح من الخادم (رمز: $code)")
                } else {
                    Pair(false, "فشل استلام الخادم للطلب (رمز الاستجابة: $code)")
                }
            } finally {
                conn.disconnect()
            }
        } catch (netErr: Exception) {
            // Honest error reporting: never report false positive success
            Pair(false, "تعذر الاتصال بالخادم: ${netErr.message ?: "خطأ في الشبكة"}")
        }
    }

    /**
     * Dispatches notifications asynchronously.
     * Enforces honest reporting:
     * - If the backend URL is unconfigured (example.com placeholder) or unreachable, reports failure.
     * - Never reports success on network error or offline fallback.
     */
    fun dispatch(
        eventType: String,
        userName: String,
        userEmail: String,
        details: Map<String, String>,
        onResult: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val (success, responseMessage) = sendNotification(eventType, userName, userEmail, details)
            try {
                withContext(Dispatchers.Main) {
                    onResult(success, responseMessage)
                }
            } catch (_: Exception) {
                onResult(success, responseMessage)
            }
        }
    }

    fun openMailClient(context: Context, subject: String, body: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$OFFICIAL_SUPPORT_EMAIL")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(OFFICIAL_SUPPORT_EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {}
    }
}
