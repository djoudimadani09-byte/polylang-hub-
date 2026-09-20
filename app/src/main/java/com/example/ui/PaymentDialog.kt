package com.example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AdminEmailNotifier
import com.example.AppCurrency
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun PaymentDialog(
    isArabic: Boolean,
    currency: AppCurrency,
    planName: String,
    priceDzd: Int,
    userName: String,
    userEmail: String,
    onDismiss: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf("edahabia") }
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf(userName) }
    var isSubmitting by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .testTag("payment_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Text(
                        text = if (isArabic) "الدفع بالدينار الجزائري (DZD)" else "Payment & Subscription",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Selected Plan Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isArabic) "باقة الاشتراك:" else "Plan Subscription:",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = planName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary
                            )
                        }
                        Text(
                            text = currency.format(priceDzd),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                    }
                }

                Text(
                    text = if (isArabic) "اختر وسيلة الدفع المعتمدة بالجزائر:" else "Choose Algerian Payment Method:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Payment Methods
                val methods = listOf(
                    Triple("edahabia", if (isArabic) "💳 البطاقة الذهبية (Edahabia)" else "Edahabia Card", "بريد الجزائر"),
                    Triple("cib", if (isArabic) "🏦 البطاقة البنكية (CIB)" else "CIB Bank Card", "البنوك الجزائرية"),
                    Triple("baridi", if (isArabic) "📱 بريدي موب / تطبيق فوري" else "BaridiMob App", "دفع فوري QR"),
                    Triple("ccp", if (isArabic) "📜 حوالة بريدية (CCP)" else "Postal CCP Order", "صك بريدي رسمي")
                )

                methods.forEach { (id, title, subtitle) ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMethod = id },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (selectedMethod == id) RedPrimary.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            RadioButton(
                                selected = selectedMethod == id,
                                onClick = { selectedMethod = id }
                            )
                            Column {
                                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                // Card Number input
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { if (it.length <= 16) cardNumber = it },
                    label = {
                        Text(
                            if (selectedMethod == "ccp") (if (isArabic) "رقم الحساب البريدي الجاري (CCP + Clé)" else "CCP Account Number")
                            else (if (isArabic) "رقم البطاقة (16 رقماً)" else "Card Number (16 digits)")
                        )
                    },
                    modifier = Modifier.fillMaxWidth().testTag("payment_card_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Cardholder name
                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text(if (isArabic) "اسم صاحب الحساب أو البطاقة" else "Cardholder Name") },
                    modifier = Modifier.fillMaxWidth().testTag("payment_name_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Confirm Payment Button
                Button(
                    onClick = {
                        isSubmitting = true
                        AdminEmailNotifier.dispatch(
                            eventType = "اشتراك وترقية باقة",
                            userName = userName,
                            userEmail = userEmail,
                            details = mapOf(
                                "plan" to planName,
                                "price" to "$priceDzd DZD",
                                "paymentMethod" to selectedMethod,
                                "holder" to cardHolder
                            )
                        )
                        onPaymentSuccess()
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("confirm_payment_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isArabic) "تأكيد الدفع وتفعيل الاشتراك" else "Confirm & Activate Plan",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
