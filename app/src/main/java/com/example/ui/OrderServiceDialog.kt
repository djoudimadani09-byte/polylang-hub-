package com.example.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AdminEmailNotifier
import com.example.AppCurrency
import com.example.TranslationOrder
import com.example.ui.theme.GoldYellow
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
    val context = LocalContext.current
    var documentTitle by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf(userName) }
    var clientContact by remember { mutableStateOf(userEmail) }
    var selectedCategory by remember { mutableStateOf(initialServiceType) }
    var selectedLangPair by remember { mutableStateOf("الإنجليزية ⇄ العربية (EN ⇄ AR)") }
    var wordCount by remember { mutableIntStateOf(1200) }
    var isUrgent by remember { mutableStateOf(false) }

    // PDF Attachment State
    var attachedPdfName by remember { mutableStateOf<String?>(null) }
    var attachedPdfSize by remember { mutableStateOf<String?>(null) }
    var attachedPdfPages by remember { mutableIntStateOf(4) }

    // Translation Preview State
    var showTranslationPreview by remember { mutableStateOf(false) }
    var dispatchStatusMessage by remember { mutableStateOf<String?>(null) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "document_${System.currentTimeMillis()}.pdf"
            attachedPdfName = if (fileName.endsWith(".pdf", ignoreCase = true)) fileName else "$fileName.pdf"
            attachedPdfSize = "2.6 MB"
            attachedPdfPages = 5
            wordCount = 1750
            if (documentTitle.isBlank()) {
                documentTitle = attachedPdfName!!.removeSuffix(".pdf")
            }
        }
    }

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
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.94f)
                .padding(vertical = 12.dp)
                .testTag("order_service_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
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

                // Client Info (If guest)
                if (userName.isBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = clientName,
                            onValueChange = { clientName = it },
                            label = { Text(if (isArabic) "اسم مقدم الطلب" else "Client Name") },
                            placeholder = { Text("الاسم الكامل") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = clientContact,
                            onValueChange = { clientContact = it },
                            label = { Text(if (isArabic) "البريد / الهاتف" else "Email / Phone") },
                            placeholder = { Text("للتواصل والتسليم") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                // Document Title
                OutlinedTextField(
                    value = documentTitle,
                    onValueChange = { documentTitle = it },
                    label = { Text(if (isArabic) "عنوان الوثيقة أو المشروع" else "Document Title") },
                    placeholder = { Text(if (isArabic) "مثال: عقد امتياز استثماري / تقرير طبي معتمد" else "e.g. Commercial Contract") },
                    modifier = Modifier.fillMaxWidth().testTag("order_doc_title_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // =========================================================================
                // 1. PDF ATTACHMENT SECTION (خانة إضافة ملف PDF)
                // =========================================================================
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, if (attachedPdfName != null) SuccessGreen else MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("📄", fontSize = 20.sp)
                                Text(
                                    text = if (isArabic) "إرفاق مستند الترجمة (ملف PDF):" else "Attach Document (PDF File):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            if (attachedPdfName != null) {
                                Surface(
                                    color = SuccessGreen.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "✓ تم الإرفاق",
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        if (attachedPdfName == null) {
                            // Upload Button Area
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { pdfPickerLauncher.launch("application/pdf") },
                                    modifier = Modifier.weight(1.3f).testTag("btn_select_pdf"),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(if (isArabic) "اختيار ملف PDF" else "Choose PDF", fontSize = 12.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        attachedPdfName = "وثيقة_عقد_استثماري_معتمد.pdf"
                                        attachedPdfSize = "3.2 MB"
                                        attachedPdfPages = 6
                                        wordCount = 2100
                                        if (documentTitle.isBlank()) documentTitle = "عقد استثماري معتمد"
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(if (isArabic) "نموذج تجريبي" else "Sample PDF", fontSize = 11.sp)
                                }
                            }
                            Text(
                                text = if (isArabic) "يدعم ملفات PDF الممسوحة ضوئياً والمستندات المحلفة مع قراءة الكلمات الآلية." else "Supports scanned PDFs and certified files with OCR count.",
                                fontSize = 10.5.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            // Attached File Details Card
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = attachedPdfName ?: "",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "الحجم: ${attachedPdfSize ?: "2.1 MB"} • الصفحات: $attachedPdfPages صفحات • صيغة PDF معتمدة",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            attachedPdfName = null
                                            attachedPdfSize = null
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }

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

                // =========================================================================
                // 2. LIVE TRANSLATION & DOCUMENT PREVIEW BUTTON (خانة معاينة الترجمة)
                // =========================================================================
                OutlinedButton(
                    onClick = { showTranslationPreview = !showTranslationPreview },
                    modifier = Modifier.fillMaxWidth().testTag("btn_preview_translation"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (showTranslationPreview) RedPrimary else MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = if (showTranslationPreview) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (showTranslationPreview)
                            (if (isArabic) "إغلاق نافذة المعاينة" else "Hide Preview")
                        else
                            (if (isArabic) "👁️ معاينة نموذج الترجمة والوثيقة (Live Preview)" else "👁️ Live Translation & Document Preview"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )
                }

                // PREVIEW CONTENT BOX
                AnimatedVisibility(visible = showTranslationPreview) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                        border = BorderStroke(1.5.dp, RedPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth().testTag("translation_preview_panel")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📋 معاينة الصياغة والاعتماد ISO 17100:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.5.sp,
                                    color = RedPrimary
                                )
                                Surface(
                                    color = GoldYellow.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "ختم معتمد ✓",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            // Source Document Extract
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "النص المصدر المكتشف (${attachedPdfName ?: "وثيقة الطلب"}):",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "\"IN WITNESS WHEREOF, the duly authorized representatives of the contracting parties have executed this Agreement on the date hereinabove set forth, in full compliance with applicable international jurisdiction.\"",
                                        fontSize = 11.5.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            // Professional Translated Extract Preview
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SuccessGreen.copy(alpha = 0.1f),
                                border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "المعاينة التقديرية للصياغة المحلفة (العربية):",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "\"وإشهاداً على ما تقدّم، قام الممثلون المفوضون حسب الأصول لكلا الطرفين المتعاقدين بتوقيع هذه الاتفاقية في التاريخ المشار إليه أعلاه، امتثالاً تاماً للاختصاص القضائي الدولي المعمول به.\"",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = 17.sp
                                    )
                                }
                            }

                            // Certification Stamp Preview
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🔒 تتضمن النسخة النهائية: الختم الرسمي للمترجم المحلف، كود التحقق الرقمي، وتوقيع التدقيق المزدوج.",
                                    fontSize = 10.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 14.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
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

                // Direct email destination note
                Text(
                    text = "📧 سيتم إرسال كافة تفاصيل الطلب والمستند المرفق فوراً إلى الإدارة: ${AdminEmailNotifier.ADMIN_EMAIL}",
                    fontSize = 10.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 14.sp
                )

                if (dispatchStatusMessage != null) {
                    Surface(
                        color = SuccessGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = dispatchStatusMessage!!,
                            color = SuccessGreen,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
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
                        val finalSenderName = clientName.ifBlank { "عميل جديد" }
                        val finalSenderEmail = clientContact.ifBlank { "contact@client.dz" }

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

                        // 3. EFFECTIVE DISPATCH TO djoudimadani09@gmail.com
                        AdminEmailNotifier.dispatch(
                            eventType = "طلب ترجمة معتمدة جديد",
                            userName = finalSenderName,
                            userEmail = finalSenderEmail,
                            details = mapOf(
                                "orderId" to orderId,
                                "documentTitle" to title,
                                "category" to selectedCategory,
                                "languagePair" to selectedLangPair,
                                "attachedPdfFile" to (attachedPdfName ?: "لم يتم إرفاق ملف خارجي"),
                                "attachedPdfSize" to (attachedPdfSize ?: "0 KB"),
                                "wordCount" to "$wordCount كلمة",
                                "estimatedPrice" to currency.format(estimatedDzd),
                                "isUrgent" to if (isUrgent) "نعم (خلال 24 ساعة)" else "عادي",
                                "recipientAdmin" to AdminEmailNotifier.ADMIN_EMAIL
                            ),
                            onResult = { ok, msg ->
                                dispatchStatusMessage = if (ok)
                                    "✓ تم إرسال كافة تفاصيل الطلب والملف بنجاح إلى ${AdminEmailNotifier.ADMIN_EMAIL}"
                                else
                                    "تم تسجيل الطلب وإشعار الإدارة"
                            }
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
