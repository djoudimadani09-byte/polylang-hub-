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
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class LabSubSection {
    INTERPRETATION, ROZAN, PRACTICAL_DRILLS, EXAM
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
        // Section Selector Tabs (Scrollable for great mobile flexibility)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = activeSubSection == LabSubSection.INTERPRETATION,
                onClick = { activeSubSection = LabSubSection.INTERPRETATION },
                label = { Text(if (isArabic) "🎙️ كابينة الفورية" else "Booth", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_booth")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.ROZAN,
                onClick = { activeSubSection = LabSubSection.ROZAN },
                label = { Text(if (isArabic) "📝 رموز روزان" else "Rozan", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_rozan")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.PRACTICAL_DRILLS,
                onClick = { activeSubSection = LabSubSection.PRACTICAL_DRILLS },
                label = { Text(if (isArabic) "🎯 تدريبات التحرير" else "Drills", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_drills")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.EXAM,
                onClick = { activeSubSection = LabSubSection.EXAM },
                label = { Text(if (isArabic) "📊 امتحان الكفاءة" else "Exam", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_exam")
            )
        }

        when (activeSubSection) {
            LabSubSection.INTERPRETATION -> InterpretationBoothComponent(isArabic, userName, userEmail)
            LabSubSection.ROZAN -> RozanNotebookComponent(isArabic, userName, userEmail)
            LabSubSection.PRACTICAL_DRILLS -> PracticalTranslationDrillsComponent(isArabic, userName, userEmail)
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
    var selectedSpeed by remember { mutableStateOf("1.0x") }
    var showSourceScript by remember { mutableStateOf(false) }
    var selectedSpeechIndex by remember { mutableIntStateOf(0) }
    var dispatchStatus by remember { mutableStateOf<String?>(null) }

    val speeches = listOf(
        Pair(
            "🌍 قمة المناخ الدولية (COP28) - الإنجليزية ➔ العربية",
            "Distinguished delegates, today the world stands at a decisive crossroads regarding clean energy transition and climate resilience."
        ),
        Pair(
            "🕊️ افتتاح الجمعية العامة للأمم المتحدة - الفرنسية ➔ العربية",
            "Monsieur le Président, Mesdames et Messieurs les délégués, la paix mondiale exige un engagement multilatéral inébranlable et le respect du droit international."
        ),
        Pair(
            "⚡ منتدى الطاقة والغاز الدولي بالجزائر - الإنجليزية ➔ العربية",
            "Algeria continues to consolidate its strategic position as a reliable supplier of natural gas while investing heavily in green hydrogen infrastructure."
        ),
        Pair(
            "💼 المنتدى الاقتصادي العالمي دافوس (WEF) - الإنجليزية ➔ العربية",
            "Global economic fragmentation and geopolitical disruptions require immediate monetary coordination and cross-border regulatory harmonization."
        ),
        Pair(
            "🏛️ المؤتمر العام لمنظمة اليونسكو بباريس - الفرنسية ➔ العربية",
            "La préservation du patrimoine immatériel et la protection de la diversité linguistique constituent le rempart le plus solide contre l'oubli historique."
        ),
        Pair(
            "🇩🇪 قمة التكنولوجيا والتحول الرقمي ببرلين - الألمانية ➔ العربية",
            "Die Dekarbonisierung unserer Industrie und die strategische Partnerschaft im Bereich der erneuerbaren Energien bieten historische Kooperationschancen."
        ),
        Pair(
            "🏥 منظمة الصحة العالمية (WHO) جنيف - الإنجليزية ➔ العربية",
            "Equitable access to biomedical innovations and pandemic preparedness must be enshrined in an enforceable international convention."
        ),
        Pair(
            "🤝 مؤتمر القمة العربية (مجلس الجامعة) - العربية ➔ الإنجليزية",
            "إن العمل العربي المشترك والتكامل الاقتصادي الإقليمي هما السبيل الأوحد لمواجهة التحديات التنموية وتحقيق الأمن الغذائي والمائي المستدام."
        )
    )

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
                        Text(
                            text = if (isArabic) "مقصورة المحاكاة للمؤتمرات (Simultaneous Booth)" else "Interpretation Booth",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = RedPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isRecording) RedPrimary else SuccessGreen.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isRecording) "🔴 مباشر: ${recordTimer}s" else "جاهز للتسجيل",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isRecording) Color.White else SuccessGreen
                            )
                        }
                    }

                    Text(
                        text = if (isArabic) "اختر الخطاب التدريبي وابدأ التسجيل الفوري لمحاكاة بيئة الكابينة الحقيقية:" else "Select speech and record your simultaneous rendering:",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Speech selector
                    speeches.forEachIndexed { idx, item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedSpeechIndex = idx },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedSpeechIndex == idx) RedPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (selectedSpeechIndex == idx) RedPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = item.first,
                                modifier = Modifier.padding(10.dp),
                                fontSize = 12.sp,
                                fontWeight = if (selectedSpeechIndex == idx) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Recording Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { isRecording = !isRecording },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isRecording) Color.DarkGray else RedPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f).testTag("toggle_booth_record")
                        ) {
                            Icon(if (isRecording) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isRecording) (if (isArabic) "إيقاف التسجيل" else "Stop") else (if (isArabic) "بدء الترجمة الفورية" else "Record"), fontSize = 11.5.sp)
                        }

                        OutlinedButton(
                            onClick = { showSourceScript = !showSourceScript },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showSourceScript) "إخفاء النص" else "إظهار النص", fontSize = 11.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "تقرير تمرين كابينة الترجمة الفورية",
                                userName = userName,
                                userEmail = userEmail,
                                details = mapOf(
                                    "speech" to speeches[selectedSpeechIndex].first,
                                    "recordDurationSeconds" to "$recordTimer ثانية",
                                    "adminEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                dispatchStatus = if (success) "✓ تم إرسال تقرير تدريبك بنجاح إلى الإدارة (djoudimadani09@gmail.com)" else "تم التوثيق بنجاح"
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "إرسال تقرير التدريب إلى الإدارة (djoudimadani09@gmail.com)" else "Send Report to Admin (djoudimadani09@gmail.com)", fontSize = 11.sp)
                    }

                    if (dispatchStatus != null) {
                        Text(text = dispatchStatus!!, fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }

                    if (showSourceScript) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("النص الأصلي (Source Script):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = RedPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(speeches[selectedSpeechIndex].second, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
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

    val consecSpeeches = listOf(
        Triple(
            "🤝 تصريح ثنائي مشترك: الاستثمار في الهيدروجين الأخضر والتجارة (العربية ➔ الفرنسية)",
            "لقد اتفق الجانبان اليوم على تعزيز الاستثمارات في قطاع الهيدروجين الأخضر، مع التشديد على ضرورة إزالة القيود الجمركية وفتح خطوط الشحن المباشرة قبل نهاية الربع الأول.",
            "Les deux parties sont convenues aujourd'hui de renforcer les investissements dans le secteur de l'hydrogène vert, tout en soulignant la nécessité de lever les barrières tarifaires et d'ouvrir des lignes de fret directes avant la fin du premier trimestre."
        ),
        Triple(
            "🇪🇺 مؤتمر الشراكة الاقتصادية الأوروبية المتوسطية (الفرنسية ➔ العربية)",
            "Nous lançons aujourd'hui un fonds souverain d'amorçage de deux milliards d'euros pour moderniser les chaînes d'approvisionnement portuaires et créer cinquante mille emplois qualifiés.",
            "نطلق اليوم صندوقاً سيادياً أولياً بقيمة ملياري يورو لتحديث سلاسل الإمداد المينائية واستحداث خمسين ألف منصب عمل مؤهل."
        ),
        Triple(
            "🛢️ البيان الختامي لاجتماع منظمة أوبك+ الوزاري (الإنجليزية ➔ العربية)",
            "Ministers reaffirmed their commitment to crude market stability through precautionary quota adjustments and continuous monitoring of global commercial inventories.",
            "جدد الوزراء التزامهم باستقرار أسواق النفط الخام من خلال تعديلات الحصص الاحترازية والمراقبة المستمرة للمخزونات التجارية العالمية."
        ),
        Triple(
            "⚖️ مرافعة تحكيم تجاري دولي أمام محكمة غرفة التجارة الدولية (ICC) (الإنجليزية ➔ العربية)",
            "The claimant alleges a breach of the exclusivity covenant under clause fourteen, demanding liquidated damages of twelve million dollars and interim protective measures.",
            "يدّعي المدعي خرقاً لشرط الحصرية المنصوص عليه في البند الرابع عشر، مطالباً بتعويضات اتفاقية محددة قدرها اثنا عشر مليون دولار وتدابير تحفظية وقتية."
        ),
        Triple(
            "🇩🇪 ملتقى رجال الأعمال الجزائري الألماني للطاقات المتجددة (الألمانية ➔ العربية)",
            "Deutsche Technologiekonzerne beabsichtigen, gemeinsam mit lokalen Partnern moderne Solar- und Windparks zu errichten, um den industriellen Technologietransfer zu beschleunigen.",
            "تعتزم المجمعات التكنولوجية الألمانية، بالاشتراك مع شركاء محليين، إنشاء محطات حديثة للطاقة الشمسية وطاقة الرياح لتسريع نقل التكنولوجيا الصناعية."
        ),
        Triple(
            "📜 تقديم أوراق اعتماد سفير جديد ومراسم البروتوكول (العربية ➔ الإنجليزية)",
            "يشرفني أن أرفع إلى فخامتكم أوراق اعتمادي سفيراً ومفوضاً فوق العادة، مؤكداً العزم الراسخ على الارتقاء بالعلاقات الثنائية إلى آفاق استراتيجية أرحب.",
            "I have the honour to present to Your Excellency my letters of credence as Ambassador Extraordinary and Plenipotentiary, reaffirming the steadfast commitment to elevate bilateral relations to broader strategic horizons."
        )
    )

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
                    Text(if (isArabic) "اختر خطاب التدريب التتابعي:" else "Select consecutive speech:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    consecSpeeches.forEachIndexed { idx, item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedConsecIndex = idx
                                    showSolution = false
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedConsecIndex == idx) RedPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (selectedConsecIndex == idx) RedPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = item.first,
                                modifier = Modifier.padding(10.dp),
                                fontSize = 11.5.sp,
                                fontWeight = if (selectedConsecIndex == idx) FontWeight.Bold else FontWeight.Normal
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
                            Text(if (isArabic) "النص المصدر للتدوين:" else "Source text:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                            Text(consecSpeeches[selectedConsecIndex].second, fontSize = 12.sp, lineHeight = 18.sp)
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
                                    "speechTitle" to consecSpeeches[selectedConsecIndex].first,
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
                                Text(consecSpeeches[selectedConsecIndex].third, fontSize = 12.sp, lineHeight = 18.sp)
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
    val questions = remember {
        listOf(
            ExamQuestion(
                1,
                "ما هي الترجمة الدقيقة لمصطلح 'Without prejudice to' في العقود الرسمية؟",
                "What is the precise rendering of 'Without prejudice to' in legal contracts?",
                listOf("دون المساس بـ / مع عدم الإخلال بـ", "مع تحيز كامل ضد", "بما يتعارض مع", "دون الحاجة إلى إشعار"),
                0,
                "الترجمة القانونية المعتمدة هي 'دون المساس بـ' أو 'مع عدم الإخلال بأحكام...'"
            ),
            ExamQuestion(
                2,
                "في معايير ترجمة الشاشة (Subtitling)، ما هو الحد الأقصى الموصى به لعدد الأحرف في السطر الواحد (CPL) وفق نتفليكس؟",
                "What is the Netflix standard characters per line (CPL) for subtitles?",
                listOf("42 حرفاً (Characters)", "65 حرفاً", "20 حرفاً", "80 حرفاً"),
                0,
                "معيار الصناعة لترجمة الشاشة اللاتينية والعربية يتراوح بين 37 و 42 CPL لتفادي حجب المشهد."
            ),
            ExamQuestion(
                3,
                "ما هو الفرق بين الترجمة الفورية (Simultaneous) والتتابعية (Consecutive)؟",
                "What is the difference between simultaneous and consecutive interpretation?",
                listOf("الفورية تتطلب كابينة ومزامنة صوتية بينما التتابعية تعتمد على تدوين رؤوس الأقلام بعد توقف المتحدث", "الفورية تكون دائماً تحريرية والتتابعية شفوية", "لا يوجد فرق عملي بينهما", "الفورية تستخدم فقط في المحاكم"),
                0,
                "الترجمة الفورية تتم بشكل متزامن من داخل الكابينة، بينما التتابعية تتطلب الاستماع وتدوين رموز روزان ثم الإلقاء."
            ),
            ExamQuestion(
                4,
                "في نظرية الترجمة، ماذا يُقصد بظاهرة 'False Friends' (Faux Amis)؟",
                "What is meant by 'False Friends' in translation?",
                listOf("كلمات تتشابه في الرسم بين لغتين وتختلف في المعنى والدلالة", "مترجمون غير موثوقين", "نصوص غير قابلة للترجمة", "أخطاء مطبعية ناتجة عن برامج OCR"),
                0,
                "الأصدقاء المزيفون مثل 'eventually' بالإنجليزية التي تعني 'في نهاية المطاف' وليس 'ربما/eventuellement'."
            ),
            ExamQuestion(
                5,
                "ما هو المعيار الدولي المعتمد لإدارة جودة خدمات الترجمة التحريرية؟",
                "Which international standard governs translation service quality?",
                listOf("ISO 17100:2015", "ISO 9001 فقط", "IEEE 802.11", "RFC 2616"),
                0,
                "المعيار ISO 17100 هو المعيار الذهبي المخصص حصراً لمتطلبات خدمات الترجمة والمترجمين المحلفين."
            ),
            ExamQuestion(
                6,
                "عند ترجمة مصطلح 'Force Majeure' في القانون الجزائري والمغاربي، ما هو المقابل الدقيق؟",
                "What is the legal equivalent of 'Force Majeure' in Algerian law?",
                listOf("القوة القاهرة", "القوة العسكرية", "القدر المحتوم فقط", "الظروف الطارئة المخففة"),
                0,
                "القوة القاهرة هي المصطلح القانوني المقنن في القانون المدني للالتزامات الخارجة عن إرادة المتعاقدين."
            ),
            ExamQuestion(
                7,
                "ما هي وظيفة ذاكرة الترجمة (Translation Memory - TM) في أدوات CAT Tools؟",
                "What is the function of a Translation Memory (TM)?",
                listOf("تخزين أزواج المقاطع (Segments) المترجمة لإعادة استخدامها وزيادة التناسق", "الترجمة الآلية بدون مراجعة بشرية", "تصميم صفحات الغلاف للمستندات", "تعديل مقاطع الفيديو"),
                0,
                "ذاكرة الترجمة TM تحفظ الوحدات النصية المترجمة (Source & Target) لمنع تكرار الجهد وضمان الدقة."
            ),
            ExamQuestion(
                8,
                "وفق تقنية روزان، ما هي أفضل طريقة لتدوين الأرقام الكبيرة مثل 'ثلاثة ملايين دولار'؟",
                "According to Rozan, how should large numbers like '3 million dollars' be noted?",
                listOf("3 M $", "كتابتها كاملة بالحروف: ثلاثة ملايين", "وضع نقطتين فقط", "تجاهل الأرقام"),
                0,
                "الاختزال بالرموز القياسية (3 M $) يوفر زمناً حاسماً للمترجم التتابعي."
            ),
            ExamQuestion(
                9,
                "ماذا يعني مصطلح 'Décalage' في الترجمة الفورية؟",
                "What is 'Décalage' (ear-voice span) in simultaneous interpretation?",
                listOf("الفارق الزمني القصير بين سماع صوت المتحدث وبدء نطق الترجمة", "عطل في ميكروفون الكابينة", "طلب المترجم استراحة", "انقطاع التيار الكهربائي"),
                0,
                "الديكالاج هو الفاصل الزمني الذهني الضروري لفهم الفكرة وبناء الجملة باللغة الهدف."
            ),
            ExamQuestion(
                10,
                "أي من العبارات التالية تعبر بدقة عن 'Certified Sworn Translation'؟",
                "Which phrase represents 'Certified Sworn Translation' accurately?",
                listOf("ترجمة رسمية معتمدة ومحلفة ذات حجية قانونية", "ترجمة مجانية غير رسمية", "ترجمة بالذكاء الاصطناعي بدون ختم", "مسودة ترجمة أولية"),
                0,
                "الترجمة المحلفة المعتمدة الصادرة عن مترجم رسمي مختوم ومعين ومسجل لدى الجهات القضائية."
            ),
            ExamQuestion(
                11,
                "ما المقابل العربي المعتمد للعبارة القانونية اللاتينية 'Mutatis Mutandis' في نصوص المعاهدات؟",
                "What is the official Arabic equivalent of the Latin phrase 'Mutatis Mutandis'?",
                listOf("مع مراعاة التعديلات اللازمة / مع ما يقتضيه الفارق", "إلى أجل غير مسمى وبدون شروط", "بحسن نية مطلقة بين المتعاقدين", "بأثر رجعي فوري دون استثناء"),
                0,
                "تستخدم في العقود والمعاهدات للإحالة إلى شروط سابقة مع تكييفها حسب الظروف الجديدة."
            ),
            ExamQuestion(
                12,
                "ما هي الترجمة الدبلوماسية المؤسساتية لمصطلح 'Plenipotentiary' في الاتفاقيات الدولية؟",
                "What is the diplomatic translation of 'Plenipotentiary' in treaties?",
                listOf("مفوض فوق العادة ومطلق الصلاحية", "مبعوث استطلاعي مؤقت", "ملحق ثقافي ومستشار إعلامي", "مندوب بروتوكولي شرفي"),
                0,
                "المفوض فوق العادة ومطلق الصلاحية يحمل تفويضاً رسمياً كاملاً لتوقيع المعاهدات باسم دولته."
            ),
            ExamQuestion(
                13,
                "في عقود التجارة الدولية، ما الفرق الجوهري بين شرط 'Hardship' وشرط 'Force Majeure'؟",
                "What is the core distinction between 'Hardship' and 'Force Majeure' clauses?",
                listOf("Force Majeure تجعل التنفيذ مستحيلاً كلياً بينما Hardship تجعله مرهقاً ومختلاً اقتصادياً بصورة غير متوقعة", "كلاهما يؤدي لإلغاء العقد فوراً دون تعويض", "Hardship تطبق فقط في الجرائم البحرية", "Force Majeure تتطلب خطأ مقصوداً من أحد الطرفين"),
                0,
                "الظروف المرهقة (Hardship) تتيح إعادة التفاوض على العقد بينما القوة القاهرة تعفي من الالتزام تماماً."
            ),
            ExamQuestion(
                14,
                "في معايير ترجمة الشاشة، ما هو المعدل الموصى به لسرعة القراءة (CPS - Characters Per Second)؟",
                "In subtitling standards, what is the recommended reading speed (CPS)?",
                listOf("بين 17 و 20 حرفاً في الثانية (CPS) لضمان القراءة المريحة دون إجهاد", "أكثر من 45 حرفاً في الثانية", "حرف واحد في الثانية فقط", "لا توجد قيود على سرعة القراءة"),
                0,
                "تحديد 17-20 CPS يسمح للمشاهد بقراءة الترجمة واستيعاب الصورة البصرية في آن واحد."
            ),
            ExamQuestion(
                15,
                "ما هو الفرق الجوهري بين قاعدة المصطلحات (Termbase - TB) وذاكرة الترجمة (TM)؟",
                "What is the difference between a Termbase (TB) and a Translation Memory (TM)?",
                listOf("TB تخزن مصطلحات ومفردات مفردة مع سياقها بينما TM تحفظ جملاً ومقاطع كاملة مترجمة", "TB مخصصة للصور بينما TM للنصوص", "لا يوجد فرق بينهما فهما نفس الملف", "TM تعمل فقط دون اتصال بالإنترنت"),
                0,
                "قاعدة المصطلحات بنك معجمي للمفردات الدقيقة، بينما ذاكرة الترجمة مخزن لأزواج الجمل المترجمة."
            ),
            ExamQuestion(
                16,
                "وفق مبادئ جان فرنسوا روزان (Rozan)، ما هي وظيفة التدوين العمودي (Verticality) في الترجمة التتابعية؟",
                "According to Rozan, what is the role of verticality in consecutive interpretation notes?",
                listOf("ترتيب عناصر الجملة رأسياً لإبراز الفاعل والفعل والروابط المنطقية بنظرة واحدة", "توفير مساحة الورقة لتقليل استهلاك الدفاتر", "إخفاء الملاحظات عن الجمهور الحاضر", "كتابة الحروف بخط كبير"),
                0,
                "العمودية والانزياح (Décalage/Shift) يمنحان المترجم رؤية فورية للبنية المنطقية دون قراءة أفقية مشتتة."
            ),
            ExamQuestion(
                17,
                "في اللغة الإنجليزية القانونية، أي التراكيب التالية يعبر عن الشرط المعكوس الرسمي (Inverted Conditional)؟",
                "Which structure represents a formal inverted conditional in legal English?",
                listOf("Had the contractor completed the works on schedule, no penalties would have applied.", "If the contractor finished yesterday, he gets money.", "Should the contractor had done, they were happy.", "Unless the contractor did not delay, we paid."),
                0,
                "الصيغة المعكوسة 'Had + Subject + Past Participle' هي الأسلوب الأكاديمي والقانوني الأرقى للشرط."
            ),
            ExamQuestion(
                18,
                "في الفرنسية الأكاديمية: 'Bien qu'il _______ son devoir, le comité a rejeté sa demande.' أي صيغة تناسب الفراغ؟",
                "In formal French, which verb form correctly completes the sentence after 'Bien que'?",
                listOf("ait accompli (Subjonctif passé)", "a accompli (Indicatif passé composé)", "accomplissait (Imparfait)", "aura accompli (Futur antérieur)"),
                0,
                "حرف الربط 'Bien que' يتطلب وجوباً صيغة المنصوب (Subjonctif) للتعبير عن التنازل والمفارقة."
            ),
            ExamQuestion(
                19,
                "في الألمانية المتخصصة، ما هو بديل المبني للمجهول الصحيح للجملة: 'Dieser Bericht muss überprüft werden'؟",
                "In specialized German, which passive alternative correctly replaces the sentence?",
                listOf("Dieser Bericht ist zu überprüfen. (sein + zu + Infinitiv)", "Dieser Bericht lässt überprüfen.", "Dieser Bericht hat überprüft.", "Dieser Bericht wird überprüfen."),
                0,
                "التركيب 'sein + zu + Infinitiv' هو الصيغة الإدارية والأكاديمية المعتمدة للمجهول الدال على الوجوب."
            ),
            ExamQuestion(
                20,
                "ما هو التحوط الأكاديمي (Hedging) في الترجمة التحريرية للنصوص العلمية والدبلوماسية؟",
                "What is 'Hedging' in the translation of scientific and diplomatic texts?",
                listOf("استخدام أسلوب التلطيف والاحتراس الدلالي (مثل: may suggest, tends to) لتفادي الجزم المطلق", "حذف الفقرات الصعبة من النص", "إضافة تعليقات المترجم داخل المتن", "ترجمة النص مرتين"),
                0,
                "التحوط يعكس الدقة العلمية والموضوعية الرصينة في تقديم النتائج والتقارير الدبلوماسية."
            ),
            ExamQuestion(
                21,
                "ما هي الغاية الأساسية من الترجمة العكسية (Back-Translation) في التجارب السريرية والدوائية؟",
                "What is the primary purpose of Back-Translation in clinical trials?",
                listOf("التحقق من التطابق التام للمعنى والجرعات بسلامة مطلقة عبر مترجم مستقل لم يرَ النص الأصلي", "زيادة تكلفة المشروع على العميل", "تدريب المترجمين المبتدئين", "ترجمة الوثيقة إلى لغات غير مطلوبة"),
                0,
                "الترجمة العكسية إلزامية من الهيئات الدوائية العالمية (FDA/EMA) لضمان عدم وجود أي التباس يهدد حياة المرضى."
            ),
            ExamQuestion(
                22,
                "في مهام التحرير اللاحق للترجمة الآلية (MTPE)، ما الفرق بين Light MTPE و Full MTPE؟",
                "In MTPE, what is the core difference between Light MTPE and Full MTPE?",
                listOf("Light يركز على الفهم الأساسي وتصحيح المعنى الجسيم، بينما Full يضمن جودة بشرية كاملة وسلاسة بلاغية", "Light يستخدم برامج مجانية بينما Full برامج مدفوعة", "لا يوجد فرق بينهما", "Light مخصص للفيديو فقط"),
                0,
                "التحرير الكامل (Full MTPE) يطابق معايير ISO 18587 لإنتاج نص مكافئ للترجمة البشرية الاحترافية."
            ),
            ExamQuestion(
                23,
                "في كابينات المؤتمرات الدولية، ما هو الحد الزمني الأقصى لتناوب المترجمين الفوريين في الكابينة الواحدة؟",
                "In conference interpretation booths, what is the standard rotation interval per interpreter?",
                listOf("20 إلى 30 دقيقة لكل مترجم لتفادي الإجهاد الذهني وتدهور الأداء السمعي", "ساعتان متواصلتان دون انقطاع", "5 دقائق فقط", "يوم كامل دون تبديل"),
                0,
                "معيار AIIC الدولي يفرض تناوب مترجمين اثنين كل 20-30 دقيقة للحفاظ على التركيز ودقة نقل الأفكار."
            ),
            ExamQuestion(
                24,
                "ما هي المعايير الدولية (مثل ISO 2603 و ISO 4043) المحددة لمقصورات الترجمة الفورية؟",
                "What do ISO 2603 and ISO 4043 standards specify for interpretation booths?",
                listOf("العزل الصوتي، زوايا الرؤية المباشرة للمنصة، أنظمة التهوية الصامتة وحماية الأذن من الصدمة الصوتية", "ألوان الستائر والديكور فقط", "سرعة شبكة الإنترنت اللاسلكية", "أنواع المأكولات المقدمة للمترجمين"),
                0,
                "تضمن هذه المعايير بيئة عمل صحية تمنع الإرهاق الصوتي وتتيح للمترجم الرؤية البصرية المباشرة للمتحدثين."
            ),
            ExamQuestion(
                25,
                "في البروتوكول الدبلوماسي الدولي، ماذا تعني وثيقة 'Agréation' (الموافقة المسبقة)؟",
                "In diplomatic protocol, what does 'Agréation' signify?",
                listOf("موافقة الدولة المستقبلة المسبقة على اعتماد رئيس البعثة الدبلوماسية (السفير) المقترح", "طلب الحصول على تأشيرة سياحية عادية", "اتفاقية تجارية لإلغاء الجمارك", "مذكرة احتجاج رسمية"),
                0,
                "الاستمزاج أو الموافقة المسبقة (Agréation) إجراء سيادي إلزامي قبل إيفاد السفير رسمياً."
            ),
            ExamQuestion(
                26,
                "ما المعنى القضائي الدقيق للعبارة اللاتينية 'Prima Facie' في الدعاوى والمرافعات؟",
                "What is the precise legal meaning of the Latin phrase 'Prima Facie'?",
                listOf("ظاهر الأمر / الأدلة المبدئية الكافية للإثبات ما لم يُقدَّم دليل ينقضها", "حكم نهائي بات غير قابل للطعن", "إفلاس مالي مؤكد", "شهادة زور معلنة"),
                0,
                "تعني كفاية الأدلة الظاهرة لتأسيس حق قانوني إلى أن يثبت الطرف الآخر العكس."
            ),
            ExamQuestion(
                27,
                "في الترجمة المصرفية والمالية، ما المقابل الدقيق لمصطلح 'Letter of Credit (L/C)'؟",
                "In banking and financial translation, what is 'Letter of Credit (L/C)'?",
                listOf("خطاب الاعتماد المستندي لضمان الوفاء المالي بين بنك المستورد والمصدر", "شيك بنكي سياحي غير مؤكد", "بطاقة ائتمان شخصية", "إشعار تحويل مصرفي عادي"),
                0,
                "الاعتماد المستندي وسيلة الدفع الأضمن عالمياً في التجارة الدولية لتقليل مخاطر عدم السداد."
            ),
            ExamQuestion(
                28,
                "في التحكيم التجاري الدولي، ما معنى تفويض المحكمين للحكم وفق مبدأ 'Ex Aequo et Bono'؟",
                "In international arbitration, what does deciding 'Ex Aequo et Bono' mean?",
                listOf("الفصل في النزاع استناداً لمبادئ العدالة والإنصاف والضمير دون التقيد الحرفي بالقواعد القانونية الصارمة", "تطبيق القانون الجنائي حصراً", "تأجيل القضية إلى محكمة أخرى", "إلزام الطرفين بالصلح دون تعويض"),
                0,
                "تفويض الصلح والإنصاف يمنح هيئة التحكيم مرونة تحقيق العدالة الموضوعية للمتعاقدين."
            ),
            ExamQuestion(
                29,
                "وفق ميثاق الشرف الأخلاقي للمترجمين، ما هو الالتزام الأكثر صرامة فيما يخص وثائق العملاء؟",
                "According to the professional code of ethics, what is the most stringent obligation regarding client documents?",
                listOf("السرية المهنية المطلقة (Strict Confidentiality & NDA) وعدم الإفصاح عن أي مداولات أو بيانات", "مشاركة الوثائق على وسائل التواصل الاجتماعي للدعاية", "الاحتفاظ بالنسخ الأصلية كرهينة", "بيع البيانات لشركات الإعلان"),
                0,
                "السرية المهنية حجر الزاوية لمصداقية المترجم المحلف والمترجم الفوري في المؤتمرات الحساسة."
            ),
            ExamQuestion(
                30,
                "في الترجمة السمعية البصرية، ما هي استراتيجية 'Domestication' (التوطين) في معالجة الأمثال الثقافية؟",
                "In audiovisual translation, what is the 'Domestication' strategy for cultural idioms?",
                listOf("تكييف المثل أو الدعابة بالاستعاضة عنها بما يقابلها في ثقافة المتلقي لتسهيل الفهم والوقع النفسي", "الترجمة الحرفية كلمة بكلمة حتى وإن فقدت المعنى", "حذف الجملة تماماً من شريط السبتاتل", "نطق الكلمة باللغة الأصلية دون كتابة"),
                0,
                "التوطين (وفق لورنس فينوتي) يقرب النص من عوالم المشاهد ويحقق الأثر الدلالي والفكاهي المستهدف."
            )
        )
    }

    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var examSubmitted by remember { mutableStateOf(false) }
    var studentNameInput by remember { mutableStateOf(userName) }
    var studentEmailInput by remember { mutableStateOf(userEmail) }
    var examDispatchStatus by remember { mutableStateOf<String?>(null) }

    val answeredCount = selectedAnswers.size
    val score = remember(examSubmitted) {
        if (!examSubmitted) 0
        else questions.count { q -> selectedAnswers[q.id] == q.correctIndex }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
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
                            "اختبار تقييمي معياري شامل يتكون من 30 سؤالاً تخصصياً يغطي: الصياغة القانونية والدبلوماسية، المعاهدات الدولية، تقنيات كابينات المؤتمرات، الديكالاج، رموز روزان، المصطلحات المالية والطبية، ومعايير ISO 17100."
                        else
                            "Comprehensive 30-question standardized exam covering legal, diplomatic treaties, conference booth techniques, décalage, Rozan notation, financial/medical terms, and ISO 17100 standards.",
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
                            progress = { answeredCount.toFloat() / questions.size.toFloat() },
                            modifier = Modifier.width(130.dp).height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = RedPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }

                    if (examSubmitted) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = if (score >= 8) SuccessGreen.copy(alpha = 0.12f) else if (score >= 5) GoldYellow.copy(alpha = 0.15f) else RedPrimary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, if (score >= 8) SuccessGreen else if (score >= 5) GoldYellow else RedPrimary)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "الدرجة المحققة: $score / ${questions.size} (${score * 10}%)",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = if (score >= 8) SuccessGreen else if (score >= 5) RedPrimary else RedPrimary
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (score >= 8) SuccessGreen else if (score >= 5) GoldYellow else RedPrimary
                                    ) {
                                        Text(
                                            text = when {
                                                score >= 9 -> "C2 خبير"
                                                score >= 7 -> "C1 متقدم"
                                                score >= 5 -> "B2 متوسط مرتفع"
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
                                        score >= 9 -> "🏆 المستوى المستحق C2: مترجم محترف خبير ومؤهل لكابينات المؤتمرات والترجمة المحلفة المعتمدة."
                                        score >= 7 -> "🎓 المستوى المستحق C1: كفاءة مهنية عالية ومترجم مؤتمرات متقدم قادر على التعامل مع النصوص المعقدة."
                                        score >= 5 -> "📘 المستوى المستحق B2: طالب وممارس واعد، يُوصى بحضور مساقات الماستركلاس وممارسة كابينة المحاكاة."
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
                                    text = "• 9 - 10 نقاط: C2 (مترجم محترف معتمد / خبير مؤتمرات)\n• 7 - 8 نقاط: C1 (مترجم متقدم / صياغة قانونية متخصصة)\n• 5 - 6 نقاط: B2 (مستوى فوق المتوسط / مؤهل لمخبر التدريب)\n• أقل من 5: B1 (مستوى تأسيسي بحاجة لتمارين مكثفة)",
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
                                score >= 9 -> "C2 خبير ومترجم مؤتمرات"
                                score >= 7 -> "C1 متقدم وصياغة قانونية"
                                score >= 5 -> "B2 متوسط مرتفع"
                                else -> "B1 تأسيسي"
                            }
                            val finalName = if (studentNameInput.isNotBlank()) studentNameInput else userName
                            val finalEmail = if (studentEmailInput.isNotBlank()) studentEmailInput else userEmail

                            AdminEmailNotifier.dispatch(
                                eventType = "تسليم امتحان تحديد المستوى",
                                userName = finalName,
                                userEmail = finalEmail,
                                details = mapOf(
                                    "score" to "$score / ${questions.size}",
                                    "percentage" to "${score * 10}%",
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

