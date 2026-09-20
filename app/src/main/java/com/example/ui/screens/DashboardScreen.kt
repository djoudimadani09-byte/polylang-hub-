package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AdminEmailNotifier
import com.example.AppCurrency
import com.example.TranslationOrder
import com.example.UserRole
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun DashboardScreen(
    isArabic: Boolean,
    currency: AppCurrency,
    userName: String,
    userEmail: String,
    userPlan: String,
    userRole: UserRole,
    orders: List<TranslationOrder>,
    onOpenAuthDialog: () -> Unit,
    onOpenOrderDialog: (String) -> Unit,
    onOpenAcademy: () -> Unit
) {
    var emailTestMessage by remember { mutableStateOf<String?>(null) }
    var isSendingTest by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("dashboard_user_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(RedPrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(1).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(userName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(userEmail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Role Badge
                    SuggestionChip(
                        onClick = onOpenAuthDialog,
                        label = { Text(userRole.labelAr, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = when (userRole) {
                                UserRole.ADMIN -> GoldYellow.copy(alpha = 0.2f)
                                UserRole.TRANSLATOR -> SuccessGreen.copy(alpha = 0.15f)
                                UserRole.CLIENT -> RedPrimary.copy(alpha = 0.12f)
                            }
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${if (isArabic) "الباقة:" else "Plan:"} $userPlan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    TextButton(
                        onClick = onOpenAuthDialog,
                        modifier = Modifier.testTag("switch_account_btn")
                    ) {
                        Icon(Icons.Default.SwitchAccount, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isArabic) "تبديل الحساب / تسجيل الدخول" else "Switch / Sign In", fontSize = 11.sp)
                    }
                }
            }
        }

        // Stats Counters Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val stats = listOf(
                Pair(currency.format(8500), if (isArabic) "الرصيد المتاح" else "Balance"),
                Pair("${orders.size}", if (isArabic) "الطلبات النشطة" else "Active Orders"),
                Pair("32,450", if (isArabic) "الكلمات المنجزة" else "Total Words")
            )
            stats.forEach { (value, label) ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(value, fontWeight = FontWeight.ExtraBold, color = RedPrimary, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Translation Orders Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isArabic) "سجل العمليات والطلبات (${orders.size}):" else "Operations & Orders (${orders.size}):",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )

            Button(
                onClick = { onOpenOrderDialog("قانونية ورسمية") },
                modifier = Modifier.height(34.dp).testTag("dash_new_order_btn"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isArabic) "طلب جديد" else "New Order", fontSize = 11.sp)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            orders.forEach { order ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("order_item_${order.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "#${order.id} • ${order.category}",
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                fontSize = 12.sp
                            )
                            SuggestionChip(
                                onClick = {},
                                label = { Text(order.status, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                            )
                        }

                        Text(order.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(order.langPair, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${order.wordCount} ${if (isArabic) "كلمة" else "words"} • ${currency.format(order.priceDzd)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Email Notification Monitoring Center
        Card(
            modifier = Modifier.fillMaxWidth().testTag("email_notifier_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "📬 مركز متابعة بريد الإدارة الفوري" else "📬 Admin Email Notification Center",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SuccessGreen))
                }

                Text(
                    text = "${if (isArabic) "المستلم المعتمد لجميع التسجيلات والطلبات:" else "Designated Recipient:"} ${AdminEmailNotifier.ADMIN_EMAIL}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedButton(
                    onClick = {
                        isSendingTest = true
                        AdminEmailNotifier.dispatch(
                            eventType = "إشعار فحص من لوحة تحكم أندرويد",
                            userName = userName,
                            userEmail = userEmail,
                            details = mapOf("status" to "Dashboard test alert", "role" to userRole.name)
                        ) { ok, msg ->
                            isSendingTest = false
                            emailTestMessage = if (ok) (if (isArabic) "✓ تم إرسال الإشعار بنجاح إلى الإدارة (${AdminEmailNotifier.ADMIN_EMAIL})" else "✓ Notification sent to ${AdminEmailNotifier.ADMIN_EMAIL}") else "❌ $msg"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(38.dp).testTag("test_email_btn"),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isArabic) "إرسال إشعار تجريبي للإدارة" else "Send Test Alert", fontSize = 11.sp)
                }

                if (emailTestMessage != null) {
                    Text(
                        text = emailTestMessage ?: "",
                        fontWeight = FontWeight.Bold,
                        color = if (emailTestMessage!!.startsWith("✓")) SuccessGreen else RedPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
