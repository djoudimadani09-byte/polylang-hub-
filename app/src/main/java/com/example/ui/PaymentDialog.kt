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
import androidx.compose.ui.text.style.TextDecoration
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
    var selectedMethod by remember { mutableStateOf("baridi") }
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf(userName) }
    var couponInput by remember { mutableStateOf("AGHILAS3M50") }
    var isCouponActive by remember { mutableStateOf(true) }
    var isSubmitting by remember { mutableStateOf(false) }

    val activePrice = if (isCouponActive) (priceDzd * 0.5).toInt() else priceDzd

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
                        text = if (isArabic) "الدفع وتفعيل الاشتراك (DZD)" else "Payment & Subscription",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Selected Plan Card with Coupon calculation
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F5FF)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
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
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = planName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E3A8A)
                            )
                            if (isCouponActive) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFDCFCE7),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "خصم 50% (عرض أغيلاس AGHILAS3M50)",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF15803D),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            if (isCouponActive) {
                                Text(
                                    text = currency.format(priceDzd),
                                    fontSize = 12.sp,
                                    color = Color(0xFF94A3B8),
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Text(
                                text = currency.format(activePrice),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1D4ED8)
                            )
                        }
                    }
                }

                // Coupon Code Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponInput,
                        onValueChange = {
                            couponInput = it
                            isCouponActive = it.trim().equals("AGHILAS3M50", ignoreCase = true) || it.trim().equals("AGHILAS-DZ", ignoreCase = true)
                        },
                        label = { Text(if (isArabic) "كود الخصم (Promo Code)" else "Promo Code") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )
                    Button(
                        onClick = {
                            isCouponActive = couponInput.trim().equals("AGHILAS3M50", ignoreCase = true) || couponInput.trim().equals("AGHILAS-DZ", ignoreCase = true)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isCouponActive) Color(0xFF16A34A) else Color(0xFF2563EB))
                    ) {
                        Text(if (isCouponActive) "✓ مفعّل" else "تطبيق")
                    }
                }

                Text(
                    text = if (isArabic) "اختر وسيلة الدفع المعتمدة بالجزائر:" else "Choose Payment Method:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Payment Methods
                val methods = listOf(
                    Triple("baridi", if (isArabic) "📱 بريدي موب BaridiMob (دفع فوري)" else "BaridiMob RIP", "00799999002898430240"),
                    Triple("ccp", if (isArabic) "📮 الحساب البريدي الجاري (CCP)" else "Postal CCP Account", "0028984302 40"),
                    Triple("edahabia", if (isArabic) "💳 البطاقة الذهبية (Edahabia)" else "Edahabia Card", "بريد الجزائر"),
                    Triple("cib", if (isArabic) "🏦 البطاقة البنكية (CIB)" else "CIB Bank Card", "البنوك الجزائرية")
                )

                methods.forEach { (id, title, subtitle) ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMethod = id },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (selectedMethod == id) Color(0xFFEFF6FF) else MaterialTheme.colorScheme.surface
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (selectedMethod == id) 1.5.dp else 1.dp,
                            color = if (selectedMethod == id) Color(0xFF2563EB) else Color(0xFFE2E8F0)
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
                                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = if (id == "baridi" || id == "ccp") Color(0xFF1D4ED8) else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = if (id == "baridi" || id == "ccp") FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }

                // Official Notice Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (isArabic) "🔒 بيانات التحويل المعتمدة:" else "Official Account Details:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp,
                            color = Color(0xFF1E3A8A)
                        )
                        Text(
                            text = "• بريدي موب (RIP): 00799999002898430240\n• الحساب الجاري (CCP): 0028984302 40",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Card Number / Transaction input
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = {
                        Text(
                            if (selectedMethod == "baridi" || selectedMethod == "ccp")
                                (if (isArabic) "رقم العملية أو الحوالة من وصل الدفع" else "Transaction / Receipt Number")
                            else
                                (if (isArabic) "رقم البطاقة (16 رقماً)" else "Card Number (16 digits)")
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
                            eventType = "اشتراك وترقية باقة (عرض أغيلاس)",
                            userName = userName,
                            userEmail = userEmail,
                            details = mapOf(
                                "plan" to planName,
                                "price" to "$activePrice DZD",
                                "originalPrice" to "$priceDzd DZD",
                                "couponCode" to if (isCouponActive) couponInput.trim() else "None",
                                "offerReference" to "AGHILAS-DZ",
                                "paymentMethod" to selectedMethod,
                                "holder" to cardHolder,
                                "transactionNumber" to cardNumber
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
                        text = if (isArabic) "تأكيد الدفع وتفعيل الاشتراك بموجب العرض" else "Confirm & Activate Offer",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
