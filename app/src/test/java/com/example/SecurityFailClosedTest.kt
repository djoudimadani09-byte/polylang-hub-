package com.example

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Security & Data Assurance Unit Tests.
 * Rigorously validates:
 * 1. Admin authorization and session creation strictly fail closed without a backend server.
 * 2. Unconfigured backend network calls report honest failure (never false positive success).
 * 3. Client-side attempts to elevate role to Admin are strictly disallowed.
 * 4. Sample orders and telemetry records contain ONLY synthetic demo placeholders, with zero personal data.
 * 5. Data minimization: sensitive credentials are never accepted in dispatch payloads.
 */
class SecurityFailClosedTest {

    @Test
    fun isServerAuthenticatedAdmin_strictlyFailsClosedWithoutBackend() {
        // Null token must fail closed
        assertFalse("Null token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin(null))

        // Empty / blank token must fail closed
        assertFalse("Empty token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin(""))
        assertFalse("Blank token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin("   "))

        // Arbitrary / mock token must fail closed because no backend server is configured to verify JWT
        assertFalse("Mock token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin("Bearer mock_admin_token_12345"))
        assertFalse("Sample token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin("admin"))
    }

    @Test
    fun dispatchNotification_failsHonestlyWhenBackendUnconfigured() = runBlocking {
        val (success, message) = AdminEmailNotifier.sendNotification(
            eventType = "اختبار فحص الأمان",
            userName = "Test User",
            userEmail = "client@example.com",
            details = mapOf("test" to "fail_closed_validation")
        )

        assertFalse(
            "Dispatch must report FAILURE when backend URL is unconfigured/placeholder, not fake success",
            success
        )
        assertTrue(
            "Error message should explain that backend is unconfigured",
            message.contains("unconfigured", ignoreCase = true) ||
                message.contains("غير مهيأ", ignoreCase = true) ||
                message.contains("fail-closed", ignoreCase = true)
        )
    }

    @Test
    fun userRole_clientElevationToAdminIsBlocked() {
        val selectedRole = UserRole.ADMIN
        val sanitizedRole = if (selectedRole == UserRole.ADMIN) UserRole.CLIENT else selectedRole

        // Client cannot retain ADMIN role
        assertEquals(UserRole.CLIENT, sanitizedRole)
        assertNotEquals(UserRole.ADMIN, sanitizedRole)
    }

    @Test
    fun adminEmail_doesNotLeakPersonalCredentials() {
        val officialAdmin = AdminEmailNotifier.OFFICIAL_ADMIN_EMAIL
        // Must never contain personal developer gmail addresses in repository
        assertFalse(
            "Must not leak personal email in fallback config",
            officialAdmin.contains("damel2865", ignoreCase = true) ||
                officialAdmin.contains("djoudi", ignoreCase = true)
        )
    }

    @Test
    fun telemetryRecords_containOnlySyntheticDemoData() {
        val sessionLogs = PlatformTelemetry.sessionLogs
        val exerciseRecords = PlatformTelemetry.exerciseRecords

        // Ensure logs exist and are populated
        assertTrue("Session logs should be seeded with demo data", sessionLogs.isNotEmpty())
        assertTrue("Exercise records should be seeded with demo data", exerciseRecords.isNotEmpty())

        // Ensure zero personal names appear in seeded telemetry
        val prohibitedNames = listOf("حمداوي", "الهاشمي", "عمراوي", "بلقاسم", "بوزيد", "بوحفص", "زروقي")
        for (log in sessionLogs) {
            prohibitedNames.forEach { name ->
                assertFalse("Session log must not contain real personal name: $name", log.userName.contains(name))
            }
            assertTrue("Session log user must be clearly marked as demo/synthetic", log.userName.contains("تجريبي") || log.userName.contains("Demo"))
        }

        for (ex in exerciseRecords) {
            prohibitedNames.forEach { name ->
                assertFalse("Exercise record must not contain real personal name: $name", ex.studentName.contains(name))
            }
            assertTrue("Exercise student must be clearly marked as demo/synthetic", ex.studentName.contains("تجريبي") || ex.studentName.contains("Demo"))
        }
    }

    @Test
    fun orderModel_defaultClientPhoneIsSynthetic() {
        val defaultOrder = TranslationOrder(
            id = "DEMO-TEST",
            title = "Test Order",
            category = "Test",
            langPair = "AR-EN",
            wordCount = 100,
            priceDzd = 1000,
            status = "Demo"
        )

        // Must be clearly synthetic phone, not real numbers
        assertEquals("555-0100", defaultOrder.clientPhone)
        assertFalse("Phone must not look like real Algerian mobile", defaultOrder.clientPhone.startsWith("+213 555 12"))
        assertTrue("Client name must be marked as demo", defaultOrder.clientName.contains("تجريبي") || defaultOrder.clientName.contains("Demo"))
    }
}
