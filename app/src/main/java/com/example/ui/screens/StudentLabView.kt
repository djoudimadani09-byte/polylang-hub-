package com.example.ui.screens

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
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class LabSubSection {
    INTERPRETATION, ROZAN, EXAM
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
    isArabic: Boolean
) {
    var activeSubSection by remember { mutableStateOf(LabSubSection.INTERPRETATION) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Selector Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = activeSubSection == LabSubSection.INTERPRETATION,
                onClick = { activeSubSection = LabSubSection.INTERPRETATION },
                label = { Text(if (isArabic) "🎙️ مقصورة الترجمة الفورية" else "Booth", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1.1f).testTag("tab_lab_booth")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.ROZAN,
                onClick = { activeSubSection = LabSubSection.ROZAN },
                label = { Text(if (isArabic) "📝 دفتر رموز روزان" else "Rozan", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_rozan")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.EXAM,
                onClick = { activeSubSection = LabSubSection.EXAM },
                label = { Text(if (isArabic) "📊 امتحان تحديد المستوى" else "Exam", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1.1f).testTag("tab_lab_exam")
            )
        }

        when (activeSubSection) {
            LabSubSection.INTERPRETATION -> InterpretationBoothComponent(isArabic)
            LabSubSection.ROZAN -> RozanNotebookComponent(isArabic)
            LabSubSection.EXAM -> LevelPlacementExamComponent(isArabic)
        }
    }
}

@Composable
private fun InterpretationBoothComponent(isArabic: Boolean) {
    var isRecording by remember { mutableStateOf(false) }
    var recordTimer by remember { mutableIntStateOf(0) }
    var selectedSpeed by remember { mutableStateOf("1.0x") }
    var showSourceScript by remember { mutableStateOf(false) }
    var selectedSpeechIndex by remember { mutableIntStateOf(0) }

    val speeches = listOf(
        Pair(
            "خطاب قمة الطاقة بالجزائر (عربي ⇄ فرنسي/إنجليزي)",
            "أصحاب المعالي والسعادة، نرحب بكم في الجزائر عاصمة الدبلوماسية والتنمية المستدامة. إن أمن الطاقة اليوم لا ينفصل عن العدالة المناخية والتعاون جنوب-شمال."
        ),
        Pair(
            "ندوة التحكيم التجاري الدولي (إنجليزي ⇄ عربي)",
            "Ladies and gentlemen, arbitration clauses in institutional contracts require utmost precision, particularly regarding choice of law and procedural rules."
        ),
        Pair(
            "مؤتمر المنظمة العالمية للصحة (فرنسي ⇄ عربي)",
            "L'accès équitable aux technologies médicales et aux vaccins constitue un impératif éthique pour l'ensemble des États membres."
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
private fun RozanNotebookComponent(isArabic: Boolean) {
    var notesText by remember { mutableStateOf("") }
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
                        text = if (isArabic) "📝 دفتر تقنية روزان للترجمة التتابعية (Jean-François Rozan)" else "Rozan Consecutive Notepad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = RedPrimary
                    )
                    Text(
                        text = if (isArabic) "مبادئ روزان السبعة: التدوين الرأسي (Verticality)، التدرج (Shift)، الروابط المنطقية، ونظام الرموز المختصرة." else "Rozan's 7 Principles: Verticality, shift, logical connectors, and abbreviation symbols.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Quick Insert Rozan Symbols
                    Text("شريط الرموز السريعة (انقر للإدراج):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(rozanSymbols) { (sym, desc) ->
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
                            .height(180.dp)
                            .testTag("rozan_notes_input"),
                        placeholder = { Text(if (isArabic) "دوّن ملاحظاتك التتابعية هنا باستخدام الرموز والمحاذاة العمودية..." else "Take your consecutive notes here...") },
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { notesText = "" }) {
                            Text(if (isArabic) "مسح المفكرة" else "Clear", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelPlacementExamComponent(isArabic: Boolean) {
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
            )
        )
    }

    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var examSubmitted by remember { mutableStateOf(false) }

    val score = remember(examSubmitted) {
        if (!examSubmitted) 0
        else questions.count { q -> selectedAnswers[q.id] == q.correctIndex }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (isArabic) "📊 امتحان تحديد مستوى المترجم الأكاديمي (10 أسئلة)" else "Translator Level Placement Exam",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = RedPrimary
                    )
                    Text(
                        text = if (isArabic)
                            "أجب عن الأسئلة العشرة التالية لتقييم كفاءتك في المصطلحات القانونية، معايير السبتاتلينغ، تقنيات روزان، والترجمة الفورية."
                        else
                            "Answer all 10 questions to assess your competency in legal translation, subtitling standards, and conference interpretation.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (examSubmitted) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = if (score >= 8) SuccessGreen.copy(alpha = 0.15f) else GoldYellow.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (score >= 8) SuccessGreen else GoldYellow)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "النتيجة: $score / 10",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = if (score >= 8) SuccessGreen else RedPrimary
                                )
                                Text(
                                    text = when {
                                        score >= 9 -> "🏆 المستوى C2: مترجم محترف خبير ومؤهل للاعتماد"
                                        score >= 7 -> "🎓 المستوى C1: كفاءة مهنية عالية ومترجم متقدم"
                                        score >= 5 -> "📘 المستوى B2: طالب ترجمة واعد يحتاج لمزيد من التدريب العملي"
                                        else -> "📗 المستوى B1: متعلم في بداية المسار الأكاديمي"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
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
                                .clickable {
                                    if (!examSubmitted) {
                                        selectedAnswers = selectedAnswers.toMutableMap().apply { put(q.id, optIdx) }
                                    }
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = when {
                                examSubmitted && isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                examSubmitted && isSelected && !isCorrect -> RedPrimary.copy(alpha = 0.2f)
                                isSelected -> RedPrimary.copy(alpha = 0.12f)
                                else -> MaterialTheme.colorScheme.surface
                            },
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                when {
                                    examSubmitted && isCorrect -> SuccessGreen
                                    examSubmitted && isSelected && !isCorrect -> RedPrimary
                                    isSelected -> RedPrimary
                                    else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        if (!examSubmitted) {
                                            selectedAnswers = selectedAnswers.toMutableMap().apply { put(q.id, optIdx) }
                                        }
                                    },
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(optText, fontSize = 11.5.sp)
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { examSubmitted = true },
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
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (isArabic) "إعادة الامتحان" else "Retake")
                    }
                }
            }
        }
    }
}
