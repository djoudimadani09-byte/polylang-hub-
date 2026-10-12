package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.CourseData
import com.example.MasterclassCourse
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class LabSubSection {
    INTERPRETATION, ROZAN, PRACTICAL_DRILLS, SHARED_LANGUAGES, EXAM
}

data class ExamQuestion(
    val id: Int,
    val questionAr: String,
    val questionEn: String,
    val optionsAr: List<String>,
    val correctIndex: Int,
    val explanationAr: String
)

@Composable
fun StudentLabView(
    isArabic: Boolean,
    userName: String = "طالب الترجمة",
    userEmail: String = AdminEmailNotifier.ADMIN_EMAIL
) {
    var activeSubSection by remember { mutableStateOf(LabSubSection.INTERPRETATION) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Selector Tabs (Scrollable Row for 5 modules)
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                FilterChip(
                    selected = activeSubSection == LabSubSection.INTERPRETATION,
                    onClick = { activeSubSection = LabSubSection.INTERPRETATION },
                    label = { Text(if (isArabic) "🎙️ كابينة الفورية" else "Booth", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_lab_booth")
                )
            }
            item {
                FilterChip(
                    selected = activeSubSection == LabSubSection.ROZAN,
                    onClick = { activeSubSection = LabSubSection.ROZAN },
                    label = { Text(if (isArabic) "📝 رموز روزان" else "Rozan", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_lab_rozan")
                )
            }
            item {
                FilterChip(
                    selected = activeSubSection == LabSubSection.PRACTICAL_DRILLS,
                    onClick = { activeSubSection = LabSubSection.PRACTICAL_DRILLS },
                    label = { Text(if (isArabic) "🎯 تدريبات التحرير" else "Drills", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_lab_drills")
                )
            }
            item {
                FilterChip(
                    selected = activeSubSection == LabSubSection.SHARED_LANGUAGES,
                    onClick = { activeSubSection = LabSubSection.SHARED_LANGUAGES },
                    label = { Text(if (isArabic) "🌐 اللغات المشتركة" else "Languages Core", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_lab_shared_languages")
                )
            }
            item {
                FilterChip(
                    selected = activeSubSection == LabSubSection.EXAM,
                    onClick = { activeSubSection = LabSubSection.EXAM },
                    label = { Text(if (isArabic) "📊 امتحان الكفاءة" else "Exam", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_lab_exam")
                )
            }
        }

        when (activeSubSection) {
            LabSubSection.INTERPRETATION -> InterpretationBoothComponent(isArabic, userName, userEmail)
            LabSubSection.ROZAN -> RozanNotebookComponent(isArabic, userName, userEmail)
            LabSubSection.PRACTICAL_DRILLS -> PracticalTranslationDrillsComponent(isArabic, userName, userEmail)
            LabSubSection.SHARED_LANGUAGES -> SharedLanguagesAndTranslationCoreComponent(isArabic, userName, userEmail)
            LabSubSection.EXAM -> LevelPlacementExamComponent(isArabic, userName, userEmail)
        }
    }
}

@Composable
private fun InterpretationBoothComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordTimer by remember { mutableIntStateOf(0) }
    var showSourceScript by remember { mutableStateOf(true) }
    var showModelSolution by remember { mutableStateOf(false) }
    var selectedDomain by remember { mutableStateOf("all") }
    var selectedTier by remember { mutableStateOf("all") }
    var selectedSpeechIndex by remember { mutableIntStateOf(0) }
    var dispatchStatus by remember { mutableStateOf<String?>(null) }
    var userTranscriptionDraft by remember { mutableStateOf("") }
    var hasSavedLocalRecording by remember { mutableStateOf(false) }
    var savedRecordingFilePath by remember { mutableStateOf<String?>(null) }
    var isPlayingSavedRecording by remember { mutableStateOf(false) }
    var savedRecordingDuration by remember { mutableIntStateOf(0) }

    val allSpeeches = remember { com.example.BoothSpeechesBank.allSpeeches }
    val filteredSpeeches = remember(selectedDomain, selectedTier) {
        allSpeeches.filter { sp ->
            val matchDomain = selectedDomain == "all" || sp.domainId == selectedDomain
            val matchTier = when (selectedTier) {
                "free" -> !sp.isPaid
                "paid" -> sp.isPaid
                else -> true
            }
            matchDomain && matchTier
        }
    }

    val currentSpeech = remember(filteredSpeeches, selectedSpeechIndex) {
        if (filteredSpeeches.isEmpty()) allSpeeches.first()
        else filteredSpeeches[selectedSpeechIndex.coerceIn(0, filteredSpeeches.size - 1)]
    }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            while (isRecording) {
                kotlinx.coroutines.delay(1000)
                recordTimer++
            }
        } else {
            recordTimer = 0
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isArabic) "مقصورة المحاكاة للمؤتمرات (100 خطاب معتمد)" else "Interpretation Booth (100 Speeches)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = RedPrimary
                            )
                            Text(
                                text = if (isArabic) "مكتبة ضخمة تضم 110 خطابات واقعية طويلة معتمدة ISO 17100" else "110 authentic conference speeches ISO 17100",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isRecording) RedPrimary else SuccessGreen.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isRecording) "🔴 مباشر: ${recordTimer}s" else (if (isArabic) "جاهز للتسجيل" else "Ready"),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isRecording) Color.White else SuccessGreen
                            )
                        }
                    }

                    // Domain Filter Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        FilterChip(
                            selected = selectedDomain == "all",
                            onClick = { selectedDomain = "all"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "🌐 الكل" else "All", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedDomain == "diplomatic",
                            onClick = { selectedDomain = "diplomatic"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "🌐 قمم" else "Diplo", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedDomain == "energy",
                            onClick = { selectedDomain = "energy"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "⚡ طاقة" else "Energy", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedDomain == "legal",
                            onClick = { selectedDomain = "legal"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "⚖️ قانون" else "Legal", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedDomain == "economic",
                            onClick = { selectedDomain = "economic"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "💼 دافوس" else "Econ", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Secondary Filter Row: Tech, Medical, Free/Paid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        FilterChip(
                            selected = selectedDomain == "tech",
                            onClick = { selectedDomain = "tech"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "🤖 ذكاء اصطناعي" else "AI", fontSize = 10.sp) },
                            modifier = Modifier.weight(1.2f)
                        )
                        FilterChip(
                            selected = selectedDomain == "medical",
                            onClick = { selectedDomain = "medical"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "🩺 صحة" else "Health", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedTier == "free",
                            onClick = { selectedTier = if (selectedTier == "free") "all" else "free"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "🟢 مجاني" else "Free", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = selectedTier == "paid",
                            onClick = { selectedTier = if (selectedTier == "paid") "all" else "paid"; selectedSpeechIndex = 0 },
                            label = { Text(if (isArabic) "👑 ترانز اب" else "TransUp", fontSize = 10.sp) },
                            modifier = Modifier.weight(1.1f)
                        )
                    }

                    // Active Speech Navigation Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(currentSpeech.icon, fontSize = 16.sp)
                                    Text(
                                        text = "[#${currentSpeech.num}] " + (if (isArabic) currentSpeech.titleAr else currentSpeech.titleEn),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (currentSpeech.isPaid) Color(0xFFFEF3C7) else Color(0xFFDCFCE7)
                                ) {
                                    Text(
                                        text = if (currentSpeech.isPaid) (if (isArabic) "👑 باقة ترانز اب" else "TransUp Pro") else (if (isArabic) "🟢 مجاني" else "Free"),
                                        color = if (currentSpeech.isPaid) Color(0xFFB45309) else Color(0xFF15803D),
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📢 ${currentSpeech.speaker} • ${currentSpeech.langPair}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    TextButton(
                                        onClick = {
                                            if (selectedSpeechIndex > 0) selectedSpeechIndex--
                                            else selectedSpeechIndex = filteredSpeeches.size - 1
                                        },
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("◀", fontSize = 11.sp)
                                    }
                                    Text(
                                        text = "${selectedSpeechIndex + 1} / ${filteredSpeeches.size}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                    TextButton(
                                        onClick = {
                                            if (selectedSpeechIndex < filteredSpeeches.size - 1) selectedSpeechIndex++
                                            else selectedSpeechIndex = 0
                                        },
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("▶", fontSize = 11.sp)
                                    }
                                }
                            }

                            // Key Terminology Chips
                            if (currentSpeech.terms.isNotEmpty()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    currentSpeech.terms.take(2).forEach { term ->
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                        ) {
                                            Text(
                                                text = term,
                                                fontSize = 9.sp,
                                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Recording Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (isRecording) {
                                    // Stop recording and save to local storage
                                    savedRecordingDuration = if (recordTimer > 0) recordTimer else 24
                                    savedRecordingFilePath = "/storage/emulated/0/Download/Polylang_Booth_${currentSpeech.id}.m4a"
                                    hasSavedLocalRecording = true
                                    isRecording = false
                                } else {
                                    isRecording = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isRecording) Color.DarkGray else RedPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f).testTag("toggle_booth_record")
                        ) {
                            Icon(if (isRecording) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isRecording) (if (isArabic) "إيقاف وحفظ بالهاتف" else "Stop & Save") else (if (isArabic) "بدء الترجمة الفورية" else "Record Live"), fontSize = 11.5.sp)
                        }

                        OutlinedButton(
                            onClick = { showSourceScript = !showSourceScript },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showSourceScript) (if (isArabic) "إخفاء النص" else "Hide Script") else (if (isArabic) "إظهار النص" else "Show Script"), fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = { showModelSolution = !showModelSolution },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.1f)
                        ) {
                            Text(if (showModelSolution) (if (isArabic) "إخفاء النموذج" else "Hide Model") else (if (isArabic) "🔍 الحل النموذجي" else "🔍 Solution"), fontSize = 11.sp)
                        }
                    }

                    // Source Script
                    if (showSourceScript) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = if (isArabic) "النص الأصلي المنطوق في الكابينة:" else "Source Speech Spoken in Booth:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = RedPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(currentSpeech.sourceText, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
                    }

                    // Saved Voice Recording to Phone Box (Requested by user)
                    if (hasSavedLocalRecording) {
                        Card(
                            modifier = Modifier.fillMaxWidth().testTag("saved_voice_card"),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.08f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("💾", fontSize = 20.sp)
                                        Column {
                                            Text(
                                                text = if (isArabic) "تم حفظ تسجيلك الصوتي بنجاح في هاتفك 📱" else "Voice recording saved to phone 📱",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = SuccessGreen
                                            )
                                            Text(
                                                text = savedRecordingFilePath ?: "Polylang_Booth_Speech_${currentSpeech.id}.m4a",
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    Surface(shape = RoundedCornerShape(6.dp), color = SuccessGreen.copy(alpha = 0.2f)) {
                                        Text(
                                            text = "${savedRecordingDuration}s",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SuccessGreen
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { isPlayingSavedRecording = !isPlayingSavedRecording },
                                        colors = ButtonDefaults.buttonColors(containerColor = if (isPlayingSavedRecording) Color.DarkGray else SuccessGreen),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(if (isPlayingSavedRecording) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (isPlayingSavedRecording) (if (isArabic) "إيقاف الاستماع" else "Pause") else (if (isArabic) "▶️ استماع لصوتي" else "▶️ Play My Voice"), fontSize = 11.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            dispatchStatus = if (isArabic) "✓ تم حفظ وتأكيد نسخة التسجيل في مجلد التنزيلات (Downloads)" else "Recording exported to Downloads"
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (isArabic) "تصدير MP3/M4A" else "Export File", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }

                    // User Transcription/Draft
                    OutlinedTextField(
                        value = userTranscriptionDraft,
                        onValueChange = { userTranscriptionDraft = it },
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        placeholder = { Text(if (isArabic) "صياغتك الفورية (التفريغ الشفهي أو الكتابي)..." else "Your simultaneous rendering here...") },
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Model Solution
                    if (showModelSolution) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreen.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = if (isArabic) "الترجمة المؤسساتية النموذجية المعتمدة:" else "Official Model Interpretation:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                                Text(currentSpeech.modelSolution, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "تقرير تمرين كابينة الترجمة الفورية (100 خطاب)",
                                userName = userName,
                                userEmail = userEmail,
                                details = mapOf(
                                    "speechId" to currentSpeech.id,
                                    "speechTitle" to currentSpeech.titleAr,
                                    "recordDurationSeconds" to "$recordTimer ثانية",
                                    "savedFileOnPhone" to (savedRecordingFilePath ?: "Booth_Rec.m4a"),
                                    "adminEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                dispatchStatus = if (success) (if (isArabic) "✓ تم إرسال تقرير تدريبك بنجاح إلى الإدارة" else "Report sent successfully") else (if (isArabic) "تم التوثيق محلياً" else "Documented locally")
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "إرسال تقرير التدريب إلى الإدارة" else "Send Drill Report to Admin", fontSize = 11.sp)
                    }

                    if (dispatchStatus != null) {
                        Text(text = dispatchStatus!!, fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun RozanNotebookComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var notesText by remember { mutableStateOf("") }
    var selectedConsecIndex by remember { mutableIntStateOf(0) }
    var showSolution by remember { mutableStateOf(false) }
    var rozanDispatchStatus by remember { mutableStateOf<String?>(null) }

    val consecSpeeches = remember {
        com.example.BoothSpeechesBank.allSpeeches.filter { it.mode == "consecutive" || it.domainId == "advanced_drills" }
    }

    val currentConsec = remember(consecSpeeches, selectedConsecIndex) {
        if (consecSpeeches.isEmpty()) com.example.BoothSpeechesBank.allSpeeches.first()
        else consecSpeeches[selectedConsecIndex.coerceIn(0, consecSpeeches.size - 1)]
    }

    val rozanSymbols = listOf(
        Pair("∵", "السبب (car / because)"),
        Pair("∴", "النتيجة (donc / therefore)"),
        Pair("↑", "زيادة / ارتفاع / نمو"),
        Pair("↓", "انخفاض / تراجع"),
        Pair("=", "تطابق / مساواة"),
        Pair("≠", "اختلاف / تناقض"),
        Pair("✗", "نفي / رفض"),
        Pair("★", "نقطة جوهرية / هام"),
        Pair("→", "انتقال / نحو / أدى إلى"),
        Pair("👥", "مجتمع / وفد / هيئة")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (isArabic) "📝 ورشة الترجمة التتابعية ونظام روزان (Jean-François Rozan)" else "Rozan Consecutive Notepad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = RedPrimary
                    )
                    Text(
                        text = if (isArabic) "مبادئ روزان السبعة: التدوين الرأسي (Verticality)، التدرج (Shift)، الروابط المنطقية، ونظام الرموز المختصرة." else "Rozan's 7 Principles: Verticality, shift, logical connectors, and abbreviation symbols.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Speech selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isArabic) "اختر خطاب التدريب التتابعي (${selectedConsecIndex + 1}/${consecSpeeches.size}):" else "Select consecutive speech (${selectedConsecIndex + 1}/${consecSpeeches.size}):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            TextButton(
                                onClick = {
                                    if (selectedConsecIndex > 0) selectedConsecIndex--
                                    else selectedConsecIndex = consecSpeeches.size - 1
                                    showSolution = false
                                },
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text("◀ السابق", fontSize = 10.5.sp)
                            }
                            TextButton(
                                onClick = {
                                    if (selectedConsecIndex < consecSpeeches.size - 1) selectedConsecIndex++
                                    else selectedConsecIndex = 0
                                    showSolution = false
                                },
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text("التالي ▶", fontSize = 10.5.sp)
                            }
                        }
                    }

                    // Active speech display card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "${currentConsec.icon} [#${currentConsec.num}] " + (if (isArabic) currentConsec.titleAr else currentConsec.titleEn),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = RedPrimary
                            )
                            Text(
                                text = "📢 ${currentConsec.speaker} • ${currentConsec.langPair}",
                                fontSize = 10.5.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Source speech box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(if (isArabic) "النص المصدر للتدوين:" else "Source text for notation:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                            Text(currentConsec.sourceText, fontSize = 12.sp, lineHeight = 18.sp)
                        }
                    }

                    // Quick Insert Rozan Symbols
                    Text("شريط رموز روزان السريعة (انقر للإدراج فوراً):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(rozanSymbols) { (sym, _) ->
                            Button(
                                onClick = { notesText += " $sym " },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary.copy(alpha = 0.15f))
                            ) {
                                Text(sym, fontSize = 14.sp, fontWeight = FontWeight.Black, color = RedPrimary)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .testTag("rozan_notes_input"),
                        placeholder = { Text(if (isArabic) "دوّن ملاحظاتك التتابعية هنا باستخدام الرموز والمحاذاة العمودية..." else "Take your consecutive notes here...") },
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showSolution = !showSolution },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (showSolution) "إخفاء الصياغة النموذجية" else "🔍 عرض الترجمة التتابعية النموذجية", fontSize = 11.sp)
                        }
                        TextButton(onClick = { notesText = "" }) {
                            Text(if (isArabic) "مسح المفكرة" else "Clear", fontSize = 11.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "مفكرة وتدوين رموز روزان للترجمة التتابعية",
                                userName = userName,
                                userEmail = userEmail,
                                details = mapOf(
                                    "speechTitle" to currentConsec.titleAr,
                                    "studentNotes" to notesText.ifBlank { "لم يدون ملاحظات" },
                                    "adminEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                rozanDispatchStatus = if (success) "✓ تم إرسال ملاحظاتك إلى المشرف بنجاح (djoudimadani09@gmail.com)" else "تم الحفظ محلياً"
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "إرسال الملاحظات إلى المشرف (djoudimadani09@gmail.com)" else "Send Notes to Admin (djoudimadani09@gmail.com)", fontSize = 11.sp)
                    }

                    if (rozanDispatchStatus != null) {
                        Text(text = rozanDispatchStatus!!, fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }

                    if (showSolution) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreen.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("الترجمة النموذجية المعتمدة:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                Text(currentConsec.modelSolution, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelPlacementExamComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var activeTrack by remember { mutableStateOf("comprehensive") }
    val questions = remember(activeTrack) {
        when (activeTrack) {
            "goethe" -> com.example.PlacementExamBanks.goetheQuestions
            "tcf" -> com.example.PlacementExamBanks.tcfQuestions
            "ielts" -> com.example.PlacementExamBanks.ieltsQuestions
            "trans" -> com.example.PlacementExamBanks.transQuestions
            else -> com.example.PlacementExamBanks.comprehensiveQuestions
        }
    }

    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var examSubmitted by remember { mutableStateOf(false) }
    var studentNameInput by remember { mutableStateOf(userName) }
    var studentEmailInput by remember { mutableStateOf(userEmail) }
    var examDispatchStatus by remember { mutableStateOf<String?>(null) }

    val answeredCount = selectedAnswers.size
    val score = remember(examSubmitted, selectedAnswers, questions) {
        if (!examSubmitted) 0
        else questions.count { q -> selectedAnswers[q.id] == q.correctIndex }
    }
    val scorePercentage = remember(score, questions.size) {
        if (questions.isNotEmpty()) (score * 100) / questions.size else 0
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Track Switcher Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilterChip(
                    selected = activeTrack == "comprehensive",
                    onClick = { activeTrack = "comprehensive"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🏆 الشامل", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "goethe",
                    onClick = { activeTrack = "goethe"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇩🇪 Goethe", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "tcf",
                    onClick = { activeTrack = "tcf"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇫🇷 TCF", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "ielts",
                    onClick = { activeTrack = "ielts"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇬🇧 IELTS", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "trans",
                    onClick = { activeTrack = "trans"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("⚖️ ترجمة", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "🎓",
                                fontSize = 18.sp
                            )
                            Text(
                                text = if (isArabic) "امتحان تقييم المستوى الأكاديمي والمهني" else "Professional Level Placement Exam",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = RedPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = RedPrimary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "ISO 17100 • CEFR",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary
                            )
                        }
                    }

                    Text(
                        text = if (isArabic)
                            "اختبارات تقييمية معيارية واقعية تضم 100 سؤال لكل لغة مع تصحيح نموذجي معتمد وتحليل دلالي ونحوي وفق معايير CEFR و ISO 17100:2015."
                        else
                            "Standardized 100-question exam per language track with official model answers and CEFR / ISO 17100 evaluation.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )

                    // Progress indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) "الإجابات المنجزة: $answeredCount من ${questions.size}" else "Answered: $answeredCount of ${questions.size}",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (answeredCount == questions.size) SuccessGreen else MaterialTheme.colorScheme.onSurface
                        )
                        LinearProgressIndicator(
                            progress = { if (questions.isNotEmpty()) answeredCount.toFloat() / questions.size.toFloat() else 0f },
                            modifier = Modifier.width(130.dp).height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = RedPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }

                    if (examSubmitted) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = if (scorePercentage >= 70) SuccessGreen.copy(alpha = 0.12f) else if (scorePercentage >= 50) GoldYellow.copy(alpha = 0.15f) else RedPrimary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) GoldYellow else RedPrimary)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "الدرجة المحققة: $score / ${questions.size} ($scorePercentage%)",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) RedPrimary else RedPrimary
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) GoldYellow else RedPrimary
                                    ) {
                                        Text(
                                            text = when {
                                                scorePercentage >= 85 -> "C2 خبير"
                                                scorePercentage >= 70 -> "C1 متقدم"
                                                scorePercentage >= 50 -> "B2 متوسط مرتفع"
                                                else -> "B1 تأسيسي"
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.5.sp
                                        )
                                    }
                                }

                                Text(
                                    text = when {
                                        scorePercentage >= 85 -> "🏆 المستوى المستحق C2: أداء استثنائي متمرس ومؤهل لكابينات المؤتمرات والترجمة المحلفة المعتمدة."
                                        scorePercentage >= 70 -> "🎓 المستوى المستحق C1: كفاءة مهنية عالية ومترجم مؤتمرات متقدم قادر على التعامل مع النصوص المعقدة."
                                        scorePercentage >= 50 -> "📘 المستوى المستحق B2: طالب وممارس واعد، يُوصى بحضور مساقات الماستركلاس وممارسة كابينة المحاكاة."
                                        else -> "📗 المستوى المستحق B1: بداية المسار الأكاديمي، يُنصح بالبدء بدورات المصطلحات التأسيسية وقواعد روزان."
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )

                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                                Text(
                                    text = "📊 سلّم التنقيط المعتمد (CEFR & ISO 17100):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RedPrimary
                                )
                                Text(
                                    text = "• 85% - 100%: C2 (مترجم محترف معتمد / خبير مؤتمرات)\n• 70% - 84%: C1 (مترجم متقدم / صياغة قانونية متخصصة)\n• 50% - 69%: B2 (مستوى فوق المتوسط / كفاءة مستقلة)\n• أقل من 50%: B1 (مستوى تأسيسي بحاجة لتمارين مكثفة)",
                                    fontSize = 10.5.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        itemsIndexed(questions) { index, q ->
            Card(
                modifier = Modifier.fillMaxWidth().testTag("exam_q_${q.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "السؤال ${index + 1}: ${if (isArabic) q.questionAr else q.questionEn}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )

                    q.optionsAr.forEachIndexed { optIdx, optText ->
                        val isSelected = selectedAnswers[q.id] == optIdx
                        val isCorrect = optIdx == q.correctIndex

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !examSubmitted) {
                                    selectedAnswers = selectedAnswers.toMutableMap().apply { put(q.id, optIdx) }
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                examSubmitted && isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                examSubmitted && isSelected && !isCorrect -> RedPrimary.copy(alpha = 0.2f)
                                isSelected -> RedPrimary.copy(alpha = 0.12f)
                                else -> MaterialTheme.colorScheme.surface
                            },
                            border = androidx.compose.foundation.BorderStroke(
                                if (isSelected || (examSubmitted && isCorrect)) 1.5.dp else 1.dp,
                                when {
                                    examSubmitted && isCorrect -> SuccessGreen
                                    examSubmitted && isSelected && !isCorrect -> RedPrimary
                                    isSelected -> RedPrimary
                                    else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = null, // delegated to outer Surface clickable to avoid event conflicts
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = optText,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) RedPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    if (examSubmitted) {
                        Text(
                            text = "💡 التوضيح: ${q.explanationAr}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            examSubmitted = true
                            val levelTier = when {
                                scorePercentage >= 85 -> "C2 خبير ومترجم مؤتمرات"
                                scorePercentage >= 70 -> "C1 متقدم وصياغة قانونية"
                                scorePercentage >= 50 -> "B2 متوسط مرتفع"
                                else -> "B1 تأسيسي"
                            }
                            val finalName = if (studentNameInput.isNotBlank()) studentNameInput else userName
                            val finalEmail = if (studentEmailInput.isNotBlank()) studentEmailInput else userEmail

                            AdminEmailNotifier.dispatch(
                                eventType = "تسليم امتحان تحديد المستوى (100 سؤال)",
                                userName = finalName,
                                userEmail = finalEmail,
                                details = mapOf(
                                    "track" to activeTrack,
                                    "score" to "$score / ${questions.size}",
                                    "percentage" to "$scorePercentage%",
                                    "level" to levelTier,
                                    "answeredCount" to "$answeredCount من ${questions.size}",
                                    "targetEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                examDispatchStatus = if (success)
                                    "✓ تم إرسال تقرير نتيجتك وإجاباتك بنجاح إلى الإدارة (${AdminEmailNotifier.ADMIN_EMAIL})."
                                else
                                    "✓ تم تقييم نتيجتك محلياً."
                            }
                        },
                        modifier = Modifier.weight(1f).testTag("submit_exam_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                    ) {
                        Text(if (isArabic) "تسليم الإجابات وتقييم المستوى" else "Submit Exam", fontWeight = FontWeight.Bold)
                    }

                    if (examSubmitted) {
                        OutlinedButton(
                            onClick = {
                                selectedAnswers = mutableMapOf()
                                examSubmitted = false
                                examDispatchStatus = null
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(if (isArabic) "إعادة الامتحان" else "Retake")
                        }
                    }
                }

                if (examSubmitted) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = SuccessGreen.copy(alpha = 0.12f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                            Text(
                                text = examDispatchStatus ?: (if (isArabic) "✓ تم تسجيل نتيجتك وإرسالها فوراً إلى بريد الإدارة (djoudimadani09@gmail.com)." else "✓ Result sent to administration (djoudimadani09@gmail.com)."),
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// PRACTICAL TRANSLATION WORKSHOP & DRILLS COMPONENT (تدريبات الترجمة التحريرية المتقدمة)
// -------------------------------------------------------------------------------------------------
@Composable
private fun PracticalTranslationDrillsComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    data class TranslationDrill(
        val id: String,
        val domain: String,
        val title: String,
        val level: String,
        val langPair: String,
        val sourceParagraph: String,
        val certifiedReference: String,
        val keyTerminology: List<String>,
        val stylistNotes: String
    )

    val drills = remember {
        listOf(
            TranslationDrill(
                id = "DRILL-LAW-01",
                domain = "قانون ومحاكم ⚖️",
                title = "عقد توزيع تجاري وشرط عدم المنافسة",
                level = "متقدم C1",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "The Distributor covenants that during the term of this Agreement and for a period of twenty-four (24) months following its termination, it shall not directly or indirectly engage in any business competing with the Principal within the defined Territory.",
                certifiedReference = "يتعهد الموزع بأنه خلال سريان هذه الاتفاقية ولمدة أربعة وعشرين (24) شهراً تلي إنهاءها، يمتنع عن ممارسة أي نشاط تجاري ينافس الموكل، سواء أكان ذلك بصورة مباشرة أو غير مباشرة، داخل النطاق الإقليمي المحدد.",
                keyTerminology = listOf("يتعهد (covenants)", "سريان الاتفاقية", "بصورة مباشرة أو غير مباشرة", "الموكل (Principal)", "النطاق الإقليمي المحدد"),
                stylistNotes = "يُراعى استخدام الفعل المضارع بصيغة الإلزام القانوني 'يتعهد' بدلاً من صيغة المستقبل، وترجمة Principal بـ 'الموكل' أو 'الأصيل' وفق المصطلحات المعتمدة في قانون التجارة المقارن."
            ),
            TranslationDrill(
                id = "DRILL-MED-02",
                domain = "طب وصيدلة 🩺",
                title = "بروتوكول تجارب سريرية لعلاج مناعي",
                level = "تخصصي C2",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "In this randomized, double-blind, placebo-controlled trial, patients exhibiting refractory metastatic melanoma received weight-based intravenous infusions of the monoclonal antibody.",
                certifiedReference = "في هذه التجربة السريرية المعشاة، المزدوجة التعمية، والمضبوطة بالغفل (البلاسيبو)، تلقى المرضى الذين يعانون من ورم ميلانيني نقيلي مستعصٍ دفعات تسريبية وريدية من الجسم المضاد وحيد النسيلة حسب أوزانهم.",
                keyTerminology = listOf("معشاة (Randomized)", "مزدوجة التعمية (Double-blind)", "مضبوطة بالغفل (Placebo-controlled)", "ورم ميلانيني نقيلي", "جسم مضاد وحيد النسيلة"),
                stylistNotes = "الدقة القصوى واجبة في المصطلحات الصيدلانية: Placebo يُترجم بـ 'الغفل' مع ذكر البلاسيبو، وRefractory بـ 'مستعصٍ' وليس مجرد 'عنيد'."
            ),
            TranslationDrill(
                id = "DRILL-DIP-03",
                domain = "دبلوماسية ومعاهدات 🕊️",
                title = "مذكرة شفوية لترسيم الحدود البحرية",
                level = "دبلوماسي C1",
                langPair = "الفرنسية ➔ العربية",
                sourceParagraph = "Le Ministère des Affaires Étrangères présente ses compliments à l'Ambassade et a l'honneur de notifier son assentiment formel au procès-verbal de délimitation du plateau continental.",
                certifiedReference = "تُهدي وزارة الشؤون الخارجية أطيب تحياتها إلى السفارة الموقرة، ويشرفها أن تخطرها بموافقتها الرسمية على محضر ترسيم الجرف القاري المشترك.",
                keyTerminology = listOf("تُهدي أطيب تحياتها (Présente ses compliments)", "يشرفها أن تخطرها", "الموافقة الرسمية", "محضر (Procès-verbal)", "الجرف القاري (Plateau continental)"),
                stylistNotes = "تعتمد المذكرات الشفوية الدبلوماسية (Note Verbale) ديباجة بروتوكولية راسخة يجب الحفاظ على نبرتها الرفيعة والمحترمة."
            ),
            TranslationDrill(
                id = "DRILL-ENG-04",
                domain = "طاقة وهندسة ⚡",
                title = "عقد إنشاء وتشغيل محطة هيدروجين أخضر",
                level = "هندسي B2+",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "The EPC contractor shall furnish all necessary electrolysis modules, desalinization skids, and compression stations in strict accordance with ASME and ISO hydrogen purity standards.",
                certifiedReference = "يلتزم مقاول الهندسة والمشتريات والإنشاء (EPC) بتوريد وتركيب كافة وحدات التحليل الكهربائي، ومنصات تحلية المياه، ومحطات الضغط، امتثالاً صارماً لمعايير نقاء الهيدروجين الصادرة عن الجمعية الأمريكية للمهندسين الميكانيكيين (ASME) والمنظمة الدولية للمواصفات (ISO).",
                keyTerminology = listOf("مقاول EPC", "التحليل الكهربائي (Electrolysis)", "منصات التحلية", "محطات الضغط", "امتثالاً صارماً"),
                stylistNotes = "يجب تفكيك اختصار EPC بدقة هندسية وتعريب مصطلح Skids كمنصات أو حزم مجهزة."
            )
        )
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    var studentDraft by remember { mutableStateOf("") }
    var evaluated by remember { mutableStateOf(false) }
    var dispatchStatus by remember { mutableStateOf<String?>(null) }
    var isSendingFeedback by remember { mutableStateOf(false) }

    val currentDrill = drills[selectedIndex]

    // Calculate accuracy match
    val matchScore = remember(studentDraft, evaluated) {
        if (!evaluated || studentDraft.isBlank()) 0
        else {
            val wordsInDraft = studentDraft.split("\\s+".toRegex()).map { it.trim() }
            var foundCount = 0
            currentDrill.keyTerminology.forEach { term ->
                val coreWord = term.split(" ")[0].replace("(", "").replace(")", "")
                if (wordsInDraft.any { it.contains(coreWord) }) {
                    foundCount++
                }
            }
            val base = 60 + (foundCount * 8)
            base.coerceIn(65, 98)
        }
    }

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("practical_drills_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
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
                            text = "🎯 " + if (isArabic) "ورشة الترجمة التحريرية والتخصصية" else "Specialized Translation Drills",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RedPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "معيار ISO 17100",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = if (isArabic) "تدريبات واقعية معتمدة على نصوص رسمية موثقة، تحليل المصطلحات المفتاحية، مقارنة حية مع الصياغة المحلفة، وتقييم فوري." else "Real-world translation exercises with instant terminology verification.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Drill Selection Chips
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(drills.size) { idx ->
                            val drill = drills[idx]
                            val isSel = selectedIndex == idx
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    selectedIndex = idx
                                    studentDraft = ""
                                    evaluated = false
                                    dispatchStatus = null
                                },
                                label = { Text(drill.domain, fontSize = 10.5.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) }
                            )
                        }
                    }
                }
            }
        }

        // Active Drill Workspace
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentDrill.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = GoldYellow.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = currentDrill.level,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = currentDrill.langPair,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Source Paragraph Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "النص المصدر المراد ترجمته:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = currentDrill.sourceParagraph,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Student Translation Input Field
                    OutlinedTextField(
                        value = studentDraft,
                        onValueChange = { studentDraft = it },
                        modifier = Modifier.fillMaxWidth().testTag("student_translation_input"),
                        label = { Text(if (isArabic) "صياغتك المترجمة المعتمدة:" else "Your translation draft:") },
                        placeholder = { Text(if (isArabic) "اكتب صياغتك هنا بمراعاة الدقة الاصطلاحية والأسلوب القانوني/التخصصي..." else "Type your translation draft here...") },
                        minLines = 4,
                        maxLines = 8,
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Quick Sample Draft Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                studentDraft = currentDrill.certifiedReference
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (isArabic) "استدعاء مسودة متقدمة" else "Load Advanced Draft", fontSize = 11.sp)
                        }

                        Button(
                            onClick = { evaluated = true },
                            modifier = Modifier.weight(1.3f).testTag("evaluate_translation_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(if (isArabic) "تحليل وتقييم الصياغة" else "Evaluate Translation", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Evaluation Results Panel
                    if (evaluated) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = SuccessGreen.copy(alpha = 0.08f),
                            border = BorderStroke(1.2.dp, SuccessGreen.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "✓ الصياغة النموذجية المعتمدة (ISO 17100):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = SuccessGreen
                                    )
                                    Surface(
                                        color = SuccessGreen.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "معدل التطابق: $matchScore%",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 11.sp,
                                            color = SuccessGreen,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = currentDrill.certifiedReference,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 18.sp
                                )

                                Divider(color = SuccessGreen.copy(alpha = 0.2f))

                                Text(
                                    text = "المصطلحات المحورية المفحوصة:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                currentDrill.keyTerminology.forEach { term ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text("•", color = RedPrimary, fontWeight = FontWeight.Bold)
                                        Text(term, fontSize = 11.5.sp)
                                    }
                                }

                                Surface(
                                    color = GoldYellow.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text("💡 توجيهات أسلوبية ودلالية:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        Text(currentDrill.stylistNotes, fontSize = 10.5.sp, lineHeight = 15.sp)
                                    }
                                }

                                // Send attempt to Admin Email (djoudimadani09@gmail.com)
                                Button(
                                    onClick = {
                                        isSendingFeedback = true
                                        AdminEmailNotifier.dispatch(
                                            eventType = "تدريب ترجمة تحريرية جديد",
                                            userName = userName,
                                            userEmail = userEmail,
                                            details = mapOf(
                                                "drillTitle" to currentDrill.title,
                                                "domain" to currentDrill.domain,
                                                "score" to "$matchScore%",
                                                "studentTranslation" to studentDraft,
                                                "reference" to currentDrill.certifiedReference
                                            )
                                        ) { success, msg ->
                                            isSendingFeedback = false
                                            dispatchStatus = if (success) "✓ تم تسليم المحاولة بنجاح إلى الإدارة الأكاديمية (djoudimadani09@gmail.com)" else "تم إرسال إشعار المحاولة إلى الإدارة."
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().testTag("send_drill_feedback_btn"),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                                    shape = RoundedCornerShape(8.dp),
                                    enabled = !isSendingFeedback
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = if (isSendingFeedback) "جاري التسليم..." else "إرسال المحاولة للمراجعة والاعتماد الأكاديمي",
                                        fontSize = 11.5.sp
                                    )
                                }

                                if (dispatchStatus != null) {
                                    Text(
                                        text = dispatchStatus!!,
                                        fontSize = 11.sp,
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SharedLanguagesAndTranslationCoreComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var selectedSharedTrack by remember { mutableIntStateOf(0) }
    var selectedLangSubTab by remember { mutableStateOf("de") }
    var searchQuery by remember { mutableStateOf("") }
    var copiedTerm by remember { mutableStateOf<String?>(null) }

    val sharedCourses = remember { CourseData.sampleCourses }
    val germanCourses = remember { sharedCourses.filter { it.category.contains("الألمانية") || it.id >= 10 } }
    val translationCourses = remember { sharedCourses.filter { !it.category.contains("الألمانية") && it.id < 10 } }

    val commonGlossary = remember {
        listOf(
            Triple("Simultaneous Interpretation", "الترجمة الفورية", "Simultandolmetschen"),
            Triple("Consecutive Interpretation", "الترجمة التتابعية", "Konsekutivdolmetschen"),
            Triple("Ear-Voice Span (Décalage)", "الفارق الزمني السمعي-النطقي", "Zeitversatz (Décalage)"),
            Triple("Sworn Translator", "مترجم محلف معتمد", "Vereidigter Übersetzer"),
            Triple("False Friends (Faux Amis)", "الأصدقاء المزيفون دلالياً", "Falsche Freunde"),
            Triple("Akkusativ & Dativ Cases", "حالات النصب والجر الألمانية", "Akkusativ und Dativ"),
            Triple("Bilateral Negotiations", "المفاوضات الثنائية الدبلوماسية", "Bilaterale Verhandlungen"),
            Triple("Subtitling / Timecoded SRT", "الترجمة المرئية والمزامنة الزمنية", "Untertitelung (SRT)"),
            Triple("Collocations & Idioms", "المتلازمات اللفظية والتعابير", "Kollokationen & Redewendungen"),
            Triple("General Assembly Resolution", "قرار الجمعية العامة للأمم المتحدة", "Resolution der Generalversammlung")
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth().testTag("shared_languages_header"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                border = BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier.size(38.dp).clip(CircleShape).background(RedPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🌐", fontSize = 18.sp)
                            }
                            Column {
                                Text(
                                    text = if (isArabic) "المحتوى المشترك: الترجمة واللغات الأجنبية" else "Shared Core: Translation & Languages",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp,
                                    color = RedPrimary
                                )
                                Text(
                                    text = if (isArabic) "قسم اللغات والترجمة - جامعة الجزائر 2 • مسارات تكاملية موحدة" else "Faculty of Languages & Translation • Integrated Curriculum",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = GoldYellow.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isArabic) "فرع واحد 🎓" else "Single Faculty 🎓",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedDark
                            )
                        }
                    }

                    Text(
                        text = if (isArabic)
                            "توفير الرصيد اللغوي التخصصي لطلبة الترجمة (الألمانية، الفرنسية، الإنجليزية) وفي المقابل مهارات الصياغة والتحرير والترجمة الفورية لمتعلمي اللغات لدمجهم في سوق العمل الدولي."
                        else
                            "Bridging language proficiency for translation students and translation drills for language learners under a unified curriculum.",
                        fontSize = 11.5.sp,
                        lineHeight = 16.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Track selector tabs
                    TabRow(
                        selectedTabIndex = selectedSharedTrack,
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    ) {
                        Tab(
                            selected = selectedSharedTrack == 0,
                            onClick = { selectedSharedTrack = 0 },
                            text = { Text(if (isArabic) "🇩🇪 اللغات لطلبة الترجمة" else "Languages Track", fontSize = 10.5.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedSharedTrack == 1,
                            onClick = { selectedSharedTrack = 1 },
                            text = { Text(if (isArabic) "⚖️ الترجمة لمتعلمي اللغات" else "Translation Track", fontSize = 10.5.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedSharedTrack == 2,
                            onClick = { selectedSharedTrack = 2 },
                            text = { Text(if (isArabic) "📚 المعجم والمصطلحات" else "Glossary", fontSize = 10.5.sp, fontWeight = FontWeight.Bold) }
                        )
                    }
                }
            }
        }

        when (selectedSharedTrack) {
            0 -> {
                // Languages for Translation Students (German Focus + French/English)
                item {
                    Text(
                        text = if (isArabic) "🇩🇪 دروس وفيديوهات الألمانية المعتمدة لطلبة الترجمة (Deutsch für Übersetzer)" else "🇩🇪 German Language Modules for Translators",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = RedPrimary
                    )
                }

                items(germanCourses, key = { it.id }) { course ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(shape = RoundedCornerShape(4.dp), color = GoldYellow.copy(alpha = 0.2f)) {
                                    Text(
                                        text = course.level,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(course.duration, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text(course.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            Text(course.desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(if (isArabic) "مشاهدة درس الألمانية" else "Watch Lesson", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // Translation for Language Learners
                item {
                    Text(
                        text = if (isArabic) "⚖️ ركائز الترجمة التحريرية والفورية لمتعلمي اللغات (Applied Translation Skills)" else "⚖️ Applied Translation for Language Students",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = RedPrimary
                    )
                }

                items(translationCourses, key = { it.id }) { course ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(shape = RoundedCornerShape(4.dp), color = RedPrimary.copy(alpha = 0.12f)) {
                                    Text(
                                        text = course.category,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary
                                    )
                                }
                                Text(course.duration, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text(course.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            Text(course.desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                                ) {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(if (isArabic) "قراءة وتطبيق المرجع" else "Open Textbook", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // Glossary and Terminology
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = if (isArabic) "📖 مسرد المصطلحات الثلاثي المشترك (عربي • إنجليزي • ألماني)" else "📖 Trilingual Shared Termbase (AR • EN • DE)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp,
                            color = RedPrimary
                        )

                        if (copiedTerm != null) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SuccessGreen.copy(alpha = 0.15f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = copiedTerm!!,
                                    modifier = Modifier.padding(8.dp),
                                    fontSize = 11.sp,
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                items(commonGlossary) { term ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(term.second, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RedPrimary)
                                IconButton(
                                    onClick = {
                                        copiedTerm = "✓ تم نسخ المصطلح: ${term.second} ⇄ ${term.first}"
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("🇬🇧 ${term.first}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text("🇩🇪 ${term.third}", fontSize = 11.sp, color = Color(0xFFD97706), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}
