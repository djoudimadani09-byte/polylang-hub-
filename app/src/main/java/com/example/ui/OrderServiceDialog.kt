package com.example.ui

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AdminEmailNotifier
import com.example.AppCurrency
import com.example.TranslationOrder
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun OrderServiceDialog(
    initialServiceType: String = "قانونية ورسمية",
    currency: AppCurrency,
    isArabic: Boolean,
    userName: String,
    userEmail: String,
    onDismiss: () -> Unit,
    onSubmitOrder: (TranslationOrder) -> Unit
) {
    var documentTitle by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(initialServiceType) }
    var selectedLangPair by remember { mutableStateOf("الإنجليزية ⇄ العربية (EN ⇄ AR)") }
    var wordCount by remember { mutableStateOf(1200) }
    var isUrgent by remember { mutableStateOf(false) }

    val baseRatePerWord = 3.5 // 3.5 DZD per word
    val estimatedDzd = remember(wordCount, isUrgent) {
        val base = (wordCount * baseRatePerWord).toInt()
        if (isUrgent) (base * 1.3).toInt() else base
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .testTag("order_service_dialog"),
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
                        text = if (isArabic) "طلب ترجمة معتمدة جديدة" else "New Translation Order",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Document Title
                OutlinedTextField(
                    value = documentTitle,
                    onValueChange = { documentTitle = it },
                    label = { Text(if (isArabic) "عنوان الوثيقة أو المشروع" else "Document Title") },
                    placeholder = { Text(if (isArabic) "مثال: عقد تأسيس شركة / تقرير طبي" else "e.g. Commercial Contract") },
                    modifier = Modifier.fillMaxWidth().testTag("order_doc_title_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Category selector
                Text(
                    text = if (isArabic) "مجال وتصنيف الترجمة:" else "Translation Domain:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                val categories = listOf("قانونية ورسمية", "تقنية وهندسية", "طبية ودوائية", "ترجمة فورية للمؤتمرات")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.take(2).forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.drop(2).forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Word count slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isArabic) "عدد الكلمات المقدر:" else "Word Count:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "$wordCount ${if (isArabic) "كلمة" else "words"}",
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                    }
                    Slider(
                        value = wordCount.toFloat(),
                        onValueChange = { wordCount = it.toInt() },
                        valueRange = 200f..10000f,
                        steps = 48,
                        colors = SliderDefaults.colors(thumbColor = RedPrimary, activeTrackColor = RedPrimary)
                    )
                }

                // Urgent Delivery Checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = isUrgent,
                        onCheckedChange = { isUrgent = it }
                    )
                    Text(
                        text = if (isArabic) "تسليم مستعجل فائق السرعة (خلال 24 ساعة) +30%" else "Urgent delivery (within 24h) +30%",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Cost Summary Card
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
                                text = if (isArabic) "التكلفة التقديرية المعتمدة:" else "Estimated Total Cost:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = currency.format(estimatedDzd),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary
                            )
                        }
                        SuggestionChip(
                            onClick = {},
                            label = { Text("معيار ISO 17100 ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                        )
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        val title = if (documentTitle.isBlank()) {
                            if (isArabic) "مشروع ترجمة: $selectedCategory" else "Translation: $selectedCategory"
                        } else documentTitle

                        val orderId = "PL-${(1000..9999).random()}"
                        val isInterp = selectedCategory.contains("فورية")

                        val newOrder = TranslationOrder(
                            id = orderId,
                            title = title,
                            category = selectedCategory,
                            langPair = selectedLangPair,
                            wordCount = wordCount,
                            priceDzd = estimatedDzd,
                            status = if (isArabic) "قيد المراجعة والتدقيق" else "Under Review",
                            isInterpretation = isInterp
                        )

                        AdminEmailNotifier.dispatch(
                            eventType = "طلب ترجمة معتمدة جديد",
                            userName = userName,
                            userEmail = userEmail,
                            details = mapOf(
                                "orderId" to orderId,
                                "documentTitle" to title,
                                "category" to selectedCategory,
                                "wordCount" to "$wordCount",
                                "estimatedDzd" to "$estimatedDzd DZD",
                                "urgent" to if (isUrgent) "نعم" else "لا"
                            )
                        )

                        onSubmitOrder(newOrder)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("confirm_order_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isArabic) "تأكيد وإرسال طلب الترجمة" else "Submit Translation Order",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
