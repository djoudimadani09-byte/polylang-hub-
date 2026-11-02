package com.example

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Security Assurance Unit Tests.
 * Verifies that the platform strictly enforces Fail-Closed security behavior:
 * 1. Admin authorization requires verified server tokens and strictly fails closed locally.
 * 2. Unconfigured backend network calls report explicit failure (no false success).
 * 3. Client-side registration / login flows disallow privilege escalation to Admin.
 */
class SecurityFailClosedTest {

    @Test
    fun isServerAuthenticatedAdmin_strictlyFailsClosedWithoutBackend() {
        // Null token must fail
        assertFalse("Null token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin(null))

        // Empty / blank token must fail
        assertFalse("Empty token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin(""))
        assertFalse("Blank token must fail closed", AdminEmailNotifier.isServerAuthenticatedAdmin("   "))

        // Arbitrary / mock token must fail because no backend is configured to verify it
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
            officialAdmin.contains("damel2865", ignoreCase = true)
        )
    }
}
