package com.example.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen
import java.util.Locale

// =================================================================================================
// GAME 9: COLLOCATION MASTER (ماستر المتلازمات اللفظية الاحترافية)
// =================================================================================================
@Composable
fun CollocationMasterGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class CollocationQuestion(
        val promptEn: String,
        val promptAr: String,
        val domain: String,
        val options: List<String>,
        val correct: String,
        val explanationAr: String,
        val explanationEn: String
    )

    val questions = remember {
        listOf(
            CollocationQuestion(
                promptEn = "In international diplomacy, countries do not simply break relations; they _____ diplomatic ties.",
                promptAr = "في السلك الدبلوماسي، لا يُقال مجرد قطع العلاقات، بل المتلازمة المعتمدة هي:",
                domain = "السلك الدبلوماسي والمنظمات",
                options = listOf("sever", "cut off", "smash", "disconnect"),
                correct = "sever",
                explanationAr = "المتلازمة الدبلوماسية الرسمية هي: 'Sever diplomatic ties' (قطع العلاقات الدبلوماسية).",
                explanationEn = "The official diplomatic collocation is 'to sever diplomatic ties'."
            ),
            CollocationQuestion(
                promptEn = "In commercial dispute resolution, the arbitral tribunal will _____ a binding arbitral award.",
                promptAr = "في التحكيم التجاري الدولي، هيئة التحكيم تصدر حكماً تحكيمياً ملزماً؛ المتلازمة الدقيقة هي:",
                domain = "التحكيم التجاري الدولي",
                options = listOf("render", "make", "throw", "deliverate"),
                correct = "render",
                explanationAr = "يقال قانونياً: 'render an award / render a verdict' بمعنى إصدار حكم تحكيمي أو قضائي.",
                explanationEn = "Legal jargon requires 'render an award' or 'render a verdict'."
            ),
            CollocationQuestion(
                promptEn = "Before signing the acquisition merger, the buyer's counsel must _____ due diligence.",
                promptAr = "قبل إبرام صفقات الاستحواذ والاندماج، يلتزم الفريق القانوني بالقيام بالفحص النافي للجهالة:",
                domain = "قانون الشركات والاستثمار",
                options = listOf("conduct", "make", "play", "build"),
                correct = "conduct",
                explanationAr = "المتلازمة الاستثمارية: 'conduct due diligence' (إجراء الفحص النافي للجهالة).",
                explanationEn = "The standard commercial collocation is 'conduct due diligence'."
            ),
            CollocationQuestion(
                promptEn = "The defendant agreed to _____ full restitution for the economic damages incurred.",
                promptAr = "وافق المدعى عليه على تقديم رد كامل وجبر للأضرار الاقتصادية الناشئة:",
                domain = "المسؤولية المدنية والتعويضات",
                options = listOf("make", "give", "do", "construct"),
                correct = "make",
                explanationAr = "يقال في صياغة العقود: 'make restitution' (رد الحقوق / التعويض العيني).",
                explanationEn = "English legal drafting pairs 'make' with 'restitution'."
            ),
            CollocationQuestion(
                promptEn = "Failure to comply with the statutory deadline will _____ the right of appeal.",
                promptAr = "عدم الالتزام بالمهلة القانونية المحددة يؤدي إلى إسقاط وسقوط حق الاستئناف:",
                domain = "قانون الإجراءات المدنية",
                options = listOf("extinguish", "burn", "terminate", "fade"),
                correct = "extinguish",
                explanationAr = "المصطلح القانوني لإسقاط الحقوق بالتقادم أو مرور الميعاد هو 'extinguish a right / extinguish a claim'.",
                explanationEn = "In procedural law, rights are 'extinguished' upon lapse of prescription periods."
            ),
            CollocationQuestion(
                promptEn = "The presiding judge decided to _____ the hearing until next Tuesday.",
                promptAr = "قرر رئيس الجلسة القضائية رفع الجلسة وتأجيلها إلى الثلاثاء القادم:",
                domain = "إجراءات المحاكم",
                options = listOf("adjourn", "delay", "postpone indefinitely", "stop"),
                correct = "adjourn",
                explanationAr = "يقال في المحاكم والمؤتمرات: 'adjourn the hearing / session' (رفع الجلسة مؤقتاً).",
                explanationEn = "In judicial proceedings, hearings are 'adjourned'."
            ),
            CollocationQuestion(
                promptEn = "The contracting parties agreed to _____ all claims arising from the contract.",
                promptAr = "اتفقت الأطراف المتعاقدة على التنازل وإبراء الذمة من كافة المطالبات:",
                domain = "صياغة العقود",
                options = listOf("waive", "delete", "throw away", "ignore"),
                correct = "waive",
                explanationAr = "المتلازمة التعاقدية للتنازل عن حق أو مطالبة هي 'waive a claim / waive rights'.",
                explanationEn = "Legal contracts use 'waive a claim' to express voluntary relinquishment."
            ),
            CollocationQuestion(
                promptEn = "The company was found liable for _____ of contract under Article 12.",
                promptAr = "ثبتت مسؤولية الشركة عن الإخلال ببنود العقد بموجب المادة 12:",
                domain = "القانون التجاري",
                options = listOf("breach", "break", "tear", "fracture"),
                correct = "breach",
                explanationAr = "المصطلح القانوني المعتمد للإخلال بالعقد هو حصراً 'breach of contract'.",
                explanationEn = "Contractual violation is termed 'breach of contract'."
            ),
            CollocationQuestion(
                promptEn = "The minister delivered a speech to _____ tribute to international translators.",
                promptAr = "ألقى الوزير كلمة للإشادة والثناء والتقدير للمترجمين الدوليين:",
                domain = "الخطابات الرسمية",
                options = listOf("pay", "give", "spend", "donate"),
                correct = "pay",
                explanationAr = "المتلازمة البلاغية الشهيرة هي 'pay tribute to' (الإشادة بـ / تكريم).",
                explanationEn = "The established phrase is 'to pay tribute to'."
            ),
            CollocationQuestion(
                promptEn = "The board convened an emergency meeting to _____ a vote of no confidence.",
                promptAr = "عقد مجلس الإدارة اجتماعاً طارئاً لطرح وسحب الثقة بالاقتراع:",
                domain = "حوكمة الشركات والبرلمان",
                options = listOf("table", "put", "seat", "stand"),
                correct = "table",
                explanationAr = "في الإجراءات البرلمانية والمؤسسية: 'table a motion / table a vote' (طرح اقتراح للتصويت).",
                explanationEn = "Parliamentary procedure uses 'table a motion'."
            ),
            CollocationQuestion(
                promptEn = "The physician advised the patient to _____ strict compliance with antibiotic therapy.",
                promptAr = "نصح الطبيب المريض بالحفاظ على الامتثال والالتزام التام بالعلاج:",
                domain = "الترجمة الطبية والدوائية",
                options = listOf("maintain", "hold", "keep up", "catch"),
                correct = "maintain",
                explanationAr = "يقال طبياً: 'maintain compliance' (الالتزام الصارم بتعليمات العلاج).",
                explanationEn = "Medical advice stresses 'maintaining compliance'."
            ),
            CollocationQuestion(
                promptEn = "The central bank acted swiftly to _____ liquidity into the banking system.",
                promptAr = "تدخل البنك المركزي على الفور لضخ السيولة النقدية في النظام المصرفي:",
                domain = "السياسة النقدية والمصارف",
                options = listOf("inject", "throw", "push", "pour"),
                correct = "inject",
                explanationAr = "المتلازمة المالية الشائعة: 'inject liquidity' (ضخ السيولة النقدية).",
                explanationEn = "Monetary authorities 'inject liquidity' into financial markets."
            ),
            CollocationQuestion(
                promptEn = "The witness was placed under oath to _____ testimony before the grand jury.",
                promptAr = "أُحلف الشاهد اليمين القانونية لتقديم الشهادة أمام هيئة المحلفين الكبرى:",
                domain = "القضاء الجنائي",
                options = listOf("give", "speak", "tell", "chant"),
                correct = "give",
                explanationAr = "المتلازمة القضائية: 'give testimony' (الإدلاء بالشهادة).",
                explanationEn = "Witnesses 'give testimony' under oath."
            ),
            CollocationQuestion(
                promptEn = "The treaty explicitly states that all provisions shall _____ precedence over local statutes.",
                promptAr = "تنص المعاهدة صراحة على أن تكون لجميع أحكامها الأولوية والرجحان على القوانين المحلية:",
                domain = "القانون الدولي العام",
                options = listOf("take", "have", "gain", "hold"),
                correct = "take",
                explanationAr = "يقال في صياغة القوانين والمعاهدات: 'take precedence over' (تسمو على / تأخذ الأسبقية).",
                explanationEn = "International treaties 'take precedence over' domestic law."
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val currentQ = questions[currentIndex]
    val shuffledOptions = remember(currentIndex) {
        currentQ.options.shuffled(java.util.Random(currentIndex.toLong() * 19 + 7))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_collocation_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔗 " + if (isArabic) "ماستر المتلازمات اللفظية (Collocation Master)" else "Collocation Master",
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary,
                    fontSize = 14.sp
                )
                Badge(containerColor = GoldYellow.copy(alpha = 0.25f)) {
                    Text(
                        text = "${currentIndex + 1}/${questions.size}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            SuggestionChip(
                onClick = {},
                label = { Text("مجال: ${currentQ.domain}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isArabic) currentQ.promptAr else currentQ.promptEn,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isArabic) currentQ.promptEn else currentQ.promptAr,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Options Grid (Randomly Shuffled for each question)
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == currentQ.correct
                val isChosen = selectedOption == opt
                val bgColor = when {
                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                    isChosen -> Color.Red.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isAnswered) {
                            selectedOption = opt
                            isAnswered = true
                            if (isCorrect) onAddScore(30)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bgColor,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = opt,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        if (isAnswered) {
                            if (isCorrect) {
                                Text("✓ صحيح", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            } else if (isChosen) {
                                Text("✗ غير دقيق", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isAnswered) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = GoldYellow.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "💡 الشرح اللغوي والسياقي:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = if (isArabic) currentQ.explanationAr else currentQ.explanationEn,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isAnswered = false
                            selectedOption = null
                            currentIndex = (currentIndex + 1) % questions.size
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                    ) {
                        Text(if (isArabic) "التحدي التالي ➔" else "Next Challenge ➔")
                    }
                }
            }
        }
    }
}

// =================================================================================================
// GAME 10: PROOFREADER DETECTIVE (كاشف أخطاء الترجمة والتدقيق اللغوي)
// =================================================================================================
@Composable
fun ProofreaderChallengeGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class ProofreaderCase(
        val sourceText: String,
        val candidateTranslation: String,
        val flawType: String,
        val options: List<String>,
        val correctFix: String,
        val explanation: String
    )

    val cases = remember {
        listOf(
            ProofreaderCase(
                sourceText = "The contract is null and void ab initio.",
                candidateTranslation = "العقد باطل وملغى من الآن فصاعداً.",
                flawType = "خطأ في فهم المصطلح اللاتيني (Ab initio)",
                options = listOf(
                    "العقد باطل بطلاناً مطلقاً بأثر رجعي منذ البداية (منذ نشوئه)",
                    "العقد يتم إنهاؤه بالتراضي ودون شروط مسبقة",
                    "العقد باطل فقط في شروطه الجزائية"
                ),
                correctFix = "العقد باطل بطلاناً مطلقاً بأثر رجعي منذ البداية (منذ نشوئه)",
                explanation = "عبارة 'Ab initio' تعني قانونياً 'منذ البداية / بأثر رجعي'، وليس 'من الآن فصاعداً' (Ex nunc)."
            ),
            ProofreaderCase(
                sourceText = "The company filed for Chapter 11 bankruptcy protection.",
                candidateTranslation = "قامت الشركة بتعبئة الفصل 11 من كتاب الإفلاس.",
                flawType = "ترجمة حرفية مخلة (Calque / Literal Translation)",
                options = listOf(
                    "قدمت الشركة طلباً للحماية من الإفلاس بموجب الفصل 11 (إعادة الهيكلة القضائية)",
                    "رفعت الشركة دعوى إفلاس في الغرفة الحادية عشرة بالمحكمة",
                    "أعلنت الشركة تصفية أموالها نهائياً"
                ),
                correctFix = "قدمت الشركة طلباً للحماية من الإفلاس بموجب الفصل 11 (إعادة الهيكلة القضائية)",
                explanation = "Chapter 11 في القانون الأمريكي هو نظام إعادة التنظيم والحماية من الدائنين، وليس فصلاً في كتاب!"
            ),
            ProofreaderCase(
                sourceText = "The patient presents with acute myocardial infarction.",
                candidateTranslation = "المريض لديه تعفن حاد في عضلات الصدر.",
                flawType = "خطأ طبي خطير (Medical Malpractice Mistranslation)",
                options = listOf(
                    "يعاني المريض من احتشاء حاد في عضلة القلب (جلطة قلبية حادة)",
                    "المريض مصاب بذبحة صدرية خفيفة غير مستقرة",
                    "المريض يعاني من التهاب مزمن في الغشاء التاموري"
                ),
                correctFix = "يعاني المريض من احتشاء حاد في عضلة القلب (جلطة قلبية حادة)",
                explanation = "Myocardial infarction تعني تحديداً 'احتشاء عضلة القلب'، والترجمة الخاطئة قد تضلل الطاقم العلاجي."
            ),
            ProofreaderCase(
                sourceText = "Without prejudice to the foregoing provisions.",
                candidateTranslation = "بدون أي تعصب للأحكام الواردة أعلاه.",
                flawType = "خلط بين المعنى العام والمعنى القانوني لكلمة Prejudice",
                options = listOf(
                    "دون الإخلال بالأحكام / البنود سالفة الذكر أعلاه",
                    "دون تحيز للطرف الأول في العقد",
                    "مع استبعاد كافة المواد السابقة"
                ),
                correctFix = "دون الإخلال بالأحكام / البنود سالفة الذكر أعلاه",
                explanation = "في العقود، 'Without prejudice to' تعني دائماً 'دون إخلال بـ'، وليس لها أي علاقة بالتعصب أو العنصرية."
            ),
            ProofreaderCase(
                sourceText = "The suspect was remanded in custody pending trial.",
                candidateTranslation = "تم إرجاع المشتبه به إلى الحضانة المنزلية بانتظار المحاكمة.",
                flawType = "خطأ في دلالة مصطلح Custody القضائي",
                options = listOf(
                    "أودع المشتبه فيه رهن الحبس الاحتياطي على ذمة المحاكمة",
                    "وُضع المتهم تحت الإقامة الجبرية المشروطة",
                    "تم إطلاق سراح المشتبه فيه بكفالة مالية"
                ),
                correctFix = "أودع المشتبه فيه رهن الحبس الاحتياطي على ذمة المحاكمة",
                explanation = "في القانون الجنائي، 'remanded in custody' تعني الحبس الاحتياطي وليس حضانة الأطفال أو المنزل."
            ),
            ProofreaderCase(
                sourceText = "The treaty was ratified by an overwhelming majority.",
                candidateTranslation = "تم التحقق من المعاهدة بغالبية ساحقة.",
                flawType = "ترجمة خاطئة لمصطلح Ratification الدبلوماسي",
                options = listOf(
                    "صودق على المعاهدة بأغلبية ساحقة",
                    "تم توقيع مسودة المعاهدة بالأحرف الأولى",
                    "عُدلت بنود المعاهدة بتوافق الآراء"
                ),
                correctFix = "صودق على المعاهدة بأغلبية ساحقة",
                explanation = "المصطلح الدبلوماسي الرسمي لـ Ratify هو 'يصادق برلمانياً' وليس يتحقق أو يثبت."
            ),
            ProofreaderCase(
                sourceText = "The minister delivered an off-the-record briefing.",
                candidateTranslation = "ألقى الوزير خطاباً غير مسجل على الشريط الصوتي.",
                flawType = "تفسير حرفي ساذج لمصطلح صحفي إعلامي",
                options = listOf(
                    "قدم الوزير إحاطة إعلامية غير قابلة للنشر أو الاقتباس المباشر",
                    "رفض الوزير الإدلاء بأي تصريح للصحافة",
                    "ألقى الوزير كلمة مسجلة خارج الاستوديو"
                ),
                correctFix = "قدم الوزير إحاطة إعلامية غير قابلة للنشر أو الاقتباس المباشر",
                explanation = "في الصحافة والدبلوماسية، 'off-the-record' تعني تصريحات للاسترشاد فقط دون ذكر اسم المصدر أو اقتباسه."
            ),
            ProofreaderCase(
                sourceText = "The court issued a writ of habeas corpus.",
                candidateTranslation = "أصدرت المحكمة أمراً بجلب جثة المتوفى.",
                flawType = "خلط لاتيني فادح بين Corpus و Habeas Corpus",
                options = listOf(
                    "أصدرت المحكمة أمر إحضار للمثول والتحقق من مشروعية التوقيف",
                    "أمرت المحكمة بتشريح الجثمان جنائياً",
                    "أصدر القاضي حكماً بالحبس المؤبد"
                ),
                correctFix = "أصدرت المحكمة أمر إحضار للمثول والتحقق من مشروعية التوقيف",
                explanation = "'Habeas corpus' مذكرة قضائية دستورية تضمن عدم احتجاز أي شخص بصورة تعسفية دون مبرر قانوني."
            ),
            ProofreaderCase(
                sourceText = "The contract includes an indemnity clause.",
                candidateTranslation = "يحتوي العقد على بند خاص بإدانة المتسبب.",
                flawType = "الخلط اللغوي بين Indemnity والإدانة",
                options = listOf(
                    "يتضمن العقد بند التعويض وجبر الضرر وإبراء الذمة",
                    "يشتمل العقد على غرامات جزائية جنائية",
                    "يفرض العقد عقوبة السجن على المقاول"
                ),
                correctFix = "يتضمن العقد بند التعويض وجبر الضرر وإبراء الذمة",
                explanation = "'Indemnity' تعني التعويض المالي والحماية من الخسائر، وليس الإدانة الجنائية (Condemnation)."
            ),
            ProofreaderCase(
                sourceText = "The patient is in stable condition post-op.",
                candidateTranslation = "المريض في حالة مستقرة بعد العملية السرية.",
                flawType = "فهم مغلوط لاختصار post-op الطبي",
                options = listOf(
                    "حالة المريض مستقرة بعد العملية الجراحية (ما بعد الجراحة)",
                    "المريض يخضع للعلاج الطبيعي المكثف",
                    "المريض خرج من المستشفى نهائياً"
                ),
                correctFix = "حالة المريض مستقرة بعد العملية الجراحية (ما بعد الجراحة)",
                explanation = "اختصار post-op يعني post-operative أي ما بعد العملية الجراحية وليس عملية سرية (Op)."
            ),
            ProofreaderCase(
                sourceText = "The company declared an interim dividend of $2 per share.",
                candidateTranslation = "أعلنت الشركة عن أرباح مؤقتة قدرها دولارين.",
                flawType = "عدم دقة المصطلح المالي لـ Interim dividend",
                options = listOf(
                    "أعلنت الشركة توزيع أرباح مرحلية (نصف سنوية) بقيمة دولارين للسهم",
                    "ألغت الشركة توزيع الأرباح هذا العام",
                    "فرضت الشركة رسوماً مؤقتة على المساهمين"
                ),
                correctFix = "أعلنت الشركة توزيع أرباح مرحلية (نصف سنوية) بقيمة دولارين للسهم",
                explanation = "في الأسواق المالية، 'Interim dividend' هي الأرباح المرحلية التي توزع قبل الحسابات الختامية السنوية."
            ),
            ProofreaderCase(
                sourceText = "The legislation was passed by a bipartisan coalition.",
                candidateTranslation = "تم تمرير التشريع من قبل تحالف ثنائي الطرفين.",
                flawType = "الترجمة الحرفية لمصطلح Bipartisan السياسي",
                options = listOf(
                    "أُقرّ التشريع بدعم مشترك من الحزبين الرئيسيين (عابر للأحزاب)",
                    "رُفض مشروع القانون من المعارضة البرلمانية",
                    "تم تأجيل التصويت على التشريع لدورة لاحقة"
                ),
                correctFix = "أُقرّ التشريع بدعم مشترك من الحزبين الرئيسيين (عابر للأحزاب)",
                explanation = "'Bipartisan' تعني التوافق بين الحزبين المتنافسين (مثل الديمقراطيين والجمهوريين) وليس مجرد طرفين."
            )
        )
    }

    var activeIdx by remember { mutableIntStateOf(0) }
    var selectedFix by remember { mutableStateOf<String?>(null) }
    var isEvaluated by remember { mutableStateOf(false) }

    val c = cases[activeIdx]
    val shuffledOptions = remember(activeIdx) {
        c.options.shuffled(java.util.Random(activeIdx.toLong() * 31 + 13))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_proofreader_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔍 " + if (isArabic) "كاشف أخطاء الترجمة والتدقيق (Proofreader)" else "Proofreader Detective",
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary,
                    fontSize = 14.sp
                )
                Badge(containerColor = RedPrimary.copy(alpha = 0.15f)) {
                    Text(
                        text = "الحالة ${activeIdx + 1}/${cases.size}",
                        color = RedPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("📄 النص الأصلي (Source):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(c.sourceText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("⚠️ الترجمة المرشحة المعيبة (Defective Draft):", fontSize = 11.sp, color = Color.Red)
                    Text(c.candidateTranslation, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.Red.copy(alpha = 0.85f))
                    Spacer(Modifier.height(4.dp))
                    Text("نوع الخلل: ${c.flawType}", fontSize = 11.sp, color = GoldYellow, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = if (isArabic) "اختر التصحيح المهني المعتمد:" else "Select the certified correct translation:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            // Shuffled options
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == c.correctFix
                val isChosen = selectedFix == opt
                val bg = when {
                    !isEvaluated -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                    isChosen -> Color.Red.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isEvaluated) {
                            selectedFix = opt
                            isEvaluated = true
                            if (isCorrect) onAddScore(35)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 17.sp
                    )
                }
            }

            if (isEvaluated) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = SuccessGreen.copy(alpha = 0.15f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("✓ تعليق المحكم اللغوي المحلف:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SuccessGreen)
                        Text(c.explanation, fontSize = 11.5.sp, lineHeight = 16.sp)
                    }
                }

                Button(
                    onClick = {
                        isEvaluated = false
                        selectedFix = null
                        activeIdx = (activeIdx + 1) % cases.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "الحالة التالية ➔" else "Next Case ➔")
                }
            }
        }
    }
}

// =================================================================================================
// GAME 11: CULTURAL & LEGAL NUANCE QUEST (الفروق القانونية والثقافية المقارنة)
// =================================================================================================
@Composable
fun CulturalNuancesGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class NuanceQuest(
        val question: String,
        val options: List<String>,
        val correct: String,
        val legalInsight: String
    )

    val quests = remember {
        listOf(
            NuanceQuest(
                question = "ما هو الفرق الجوهري الدقيق بين Notary Public في أمريكا و Notaire في فرنسا والجزائر؟",
                options = listOf(
                    "الـ Notaire موثق عمومي رسمي يصيغ ويوثق العقود، بينما في أمريكا شاهد على التوقيع فقط",
                    "كلاهما قضاة يعينهم رئيس الجمهورية بالمحكمة العليا",
                    "الـ Notary Public الأمريكي يشترط أن يكون حاملاً للدكتوراه في القانون الدولي"
                ),
                correct = "الـ Notaire موثق عمومي رسمي يصيغ ويوثق العقود، بينما في أمريكا شاهد على التوقيع فقط",
                legalInsight = "في النظم المدنية (Civil Law)، الموثق ضابط عمومي يصيغ العقود الرسمية ويضمن صحتها القانونية. أما في الولايات المتحدة، فالـ Notary Public لا يشترط شهادة قانونية ووظيفته التحقق من هوية الموقعين."
            ),
            NuanceQuest(
                question = "كيف تُترجم عبارة 'Certified True Copy' في المستندات الرسمية المعتمدة الصادرة بالإنجليزية؟",
                options = listOf(
                    "نسخة طبق الأصل ومطابقة للأصل المحفوظ",
                    "نسخة حقيقية مسحوبة من الإنترنت",
                    "صورة ملونة موثقة بالختم الجاف"
                ),
                correct = "نسخة طبق الأصل ومطابقة للأصل المحفوظ",
                legalInsight = "الصياغة الإدارية والترجمية الجزائرية والدولية الرسمية هي حصراً 'نسخة طبق الأصل' (Copie conforme à l'original)."
            ),
            NuanceQuest(
                question = "ماذا تعني قاعدة 'Locus regit actum' في تنازع القوانين وتوثيق المعاملات الدولية؟",
                options = listOf(
                    "قانون محل إبرام التصرف هو الذي يحكم شكله وصحته الظاهرية",
                    "القاضي يحكم وفق قانون موطنه فقط دون غيره",
                    "مكان إقامة المدعى عليه هو الاختصاص القضائي الحصري"
                ),
                correct = "قانون محل إبرام التصرف هو الذي يحكم شكله وصحته الظاهرية",
                legalInsight = "قاعدة لاتينية شهيرة في القانون الدولي الخاص تفيد بأن شكل التصرف القانوني يخضع لقانون الدولة التي أُبرم فيها."
            ),
            NuanceQuest(
                question = "ما الفرق بين Tort في القانون الإنجلو-أمريكي والجريمة Crime؟",
                options = listOf(
                    "Tort مسؤولية مدنية تقصيرية توجب التعويض للمتضرر، بينما Crime اعتداء على المجتمع يعاقب عليه القانون العام",
                    "Tort هي عقوبة الإعدام بينما Crime هي الغرامة المالية",
                    "كلاهما مصطلحان مترادفان تماماً في القانون الجنائي"
                ),
                correct = "Tort مسؤولية مدنية تقصيرية توجب التعويض للمتضرر، بينما Crime اعتداء على المجتمع يعاقب عليه القانون العام",
                legalInsight = "الـ Tort هو الفعل الضار المدني (Delict في القانون المدني)، والدعوى فيه ترفع من الضحية للتعويض، بخلاف الجريمة التي تحركها النيابة العامة."
            ),
            NuanceQuest(
                question = "في الصياغة الدبلوماسية، ما الفرق الدقيق بين Persona non grata و Persona grata؟",
                options = listOf(
                    "Persona non grata تعني دبلوماسي غير مرغوب فيه ويطلب مغادرته، بينما الثانية تعني مقبول ومعتمد",
                    "الأولى تعني السفير المعين والثانية تعني القنصل الفخري",
                    "الأولى تطلق على المترجمين والثانية على السفراء"
                ),
                correct = "Persona non grata تعني دبلوماسي غير مرغوب فيه ويطلب مغادرته، بينما الثانية تعني مقبول ومعتمد",
                legalInsight = "بموجب المادة 9 من اتفاقية فيينا للعلاقات الدبلوماسية (1961)، يحق للدولة المستقبلة إعلان أي مبعوث كشخص غير مرغوب فيه دون إبداء الأسباب."
            ),
            NuanceQuest(
                question = "ما هو المقابل الدقيق لمصطلح 'Barrister' مقابل 'Solicitor' في النظام القضائي الإنجليزي؟",
                options = listOf(
                    "Barrister هو محامي المرافعات أمام المحاكم العليا، بينما Solicitor يقدم الاستشارات ويعد الملفات",
                    "Barrister قاضي تحقيق و Solicitor كاتب ضبط",
                    "كلاهما نفس التخصص ونفس رداء المحكمة"
                ),
                correct = "Barrister هو محامي المرافعات أمام المحاكم العليا، بينما Solicitor يقدم الاستشارات ويعد الملفات",
                legalInsight = "النظام القضائي الإنجليزي يفصل تقليدياً بين محامي المرافعة (Barrister) الذي يرتدي الباروكة، والمحامي المكتبي الاستشاري (Solicitor)."
            ),
            NuanceQuest(
                question = "ماذا تعني عبارة 'Pacta sunt servanda' في المعاهدات الدولية؟",
                options = listOf(
                    "العقد شريعة المتعاقدين ويجب تنفيذه بحسن نية",
                    "المعاهدات تلغى فور تغيير الحكومة",
                    "الحق في نقض الاتفاقية بأثر فوري"
                ),
                correct = "العقد شريعة المتعاقدين ويجب تنفيذه بحسن نية",
                legalInsight = "المبدأ الأساسي في القانون الدولي العام (المادة 26 من اتفاقية فيينا لقانون المعاهدات): الاتفاقيات ملزمة لأطرافها ويجب تطبيقها بحسن نية."
            ),
            NuanceQuest(
                question = "في الترجمة المالية، ما الفرق الدقيق بين 'Revenue' و 'Net Profit'؟",
                options = listOf(
                    "Revenue هو إجمالي الإيرادات قبل حسم التكاليف، بينما Net Profit هو صافي الأرباح بعد المصاريف والضرائب",
                    "Revenue هي أرباح الأسهم و Net Profit هو رأس مال الشركة",
                    "كلاهما يعني نفس الرقم في الميزانية العمومية"
                ),
                correct = "Revenue هو إجمالي الإيرادات قبل حسم التكاليف، بينما Net Profit هو صافي الأرباح بعد المصاريف والضرائب",
                legalInsight = "الخلط بين الإيراد الإجمالي (Turnover/Revenue) وصافي الربح (Bottom Line / Net Income) يغير القيمة الاستثمارية تماماً."
            ),
            NuanceQuest(
                question = "ماذا يُقصد بمصطلح 'Force Majeure' مقارنة بمصطلح 'Act of God' في العقود الإنجليزية؟",
                options = listOf(
                    "Act of God يشمل الكوارث الطبيعية فقط، بينما Force Majeure يشمل أيضاً الحروب والاضطرابات السياسية",
                    "Act of God يقتصر على أخطاء المقاولين البشريين",
                    "Force Majeure تشترط موافقة المحكمة المسبقة"
                ),
                correct = "Act of God يشمل الكوارث الطبيعية فقط، بينما Force Majeure يشمل أيضاً الحروب والاضطرابات السياسية",
                legalInsight = "في القانون الإنجليزي، 'Act of God' حصراً للكوارث الطبيعية (زلازل، فيضانات)، بينما بند القوة القاهرة أوسع ويشمل أفعال البشر كالحروب والإضرابات العامة."
            ),
            NuanceQuest(
                question = "ما هي 'Ultra Vires' في قانون الشركات والقانون الإداري؟",
                options = listOf(
                    "تجاوز الصلاحيات والاختصاصات القانونية المقررة نظاماً",
                    "الصلاحيات المطلقة الممنوحة للمدير العام",
                    "العقود المبرمة بأغلبية الثلثين"
                ),
                correct = "تجاوز الصلاحيات والاختصاصات القانونية المقررة نظاماً",
                legalInsight = "مبدأ لاتيني يترتب عليه بطلان أي تصرف يصدر عن هيئة أو شركة يتجاوز الأغراض المحددة في نظامها الأساسي."
            ),
            NuanceQuest(
                question = "ما الفرق بين 'Bilateral' و 'Multilateral' في الاتفاقيات والمعاهدات؟",
                options = listOf(
                    "Bilateral ثنائي الأطراف بين دولتين، و Multilateral متعدد الأطراف بين ثلاث دول فأكثر",
                    "Bilateral معاهدة سلام و Multilateral معاهدة تجارة",
                    "Bilateral مؤقتة و Multilateral دائمة"
                ),
                correct = "Bilateral ثنائي الأطراف بين دولتين، و Multilateral متعدد الأطراف بين ثلاث دول فأكثر",
                legalInsight = "التفرقة الدبلوماسية الدقيقة بين الاتفاقيات الثنائية (Bilateral Treaties) والاتفاقيات الدولية متعددة الأطراف (Multilateral Conventions)."
            ),
            NuanceQuest(
                question = "كيف يُترجم مفهوم 'Common Law' مقابل 'Civil Law'؟",
                options = listOf(
                    "القانون العام (الأنجلو-أمريكي القائم على السوابق) مقابل القانون المدني (اللاتيني المقنن)",
                    "القانون الشعبي مقابل القانون العسكري",
                    "القانون التجاري مقابل القانون الدستوري"
                ),
                correct = "القانون العام (الأنجلو-أمريكي القائم على السوابق) مقابل القانون المدني (اللاتيني المقنن)",
                legalInsight = "أهم تقسيم للأنظمة القانونية في العالم المقارن: النظام الإنجليزي المبني على السوابق القضائية، والنظام الروماني الجرماني المعتمد على التقنين المكتوب."
            )
        )
    }

    var qIndex by remember { mutableIntStateOf(0) }
    var userChoice by remember { mutableStateOf<String?>(null) }
    var solved by remember { mutableStateOf(false) }

    val q = quests[qIndex]
    val shuffledOptions = remember(qIndex) {
        q.options.shuffled(java.util.Random(qIndex.toLong() * 23 + 5))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_nuance_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏛️ " + if (isArabic) "تحدي الفروق القانونية والثقافية" else "Legal & Cultural Nuance Quest",
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary,
                    fontSize = 14.sp
                )
                Badge(containerColor = GoldYellow.copy(alpha = 0.25f)) {
                    Text("${qIndex + 1}/${quests.size}", fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = q.question,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 19.sp
            )

            // Shuffled options
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == q.correct
                val isChosen = userChoice == opt
                val bg = when {
                    !solved -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                    isChosen -> Color.Red.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !solved) {
                            userChoice = opt
                            solved = true
                            if (isCorrect) onAddScore(40)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (solved) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("💡 التحليل القانوني والمقارن المعتمد:", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(q.legalInsight, fontSize = 11.5.sp, lineHeight = 16.sp)
                    }
                }

                Button(
                    onClick = {
                        solved = false
                        userChoice = null
                        qIndex = (qIndex + 1) % quests.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "اللغز المقارن التالي ➔" else "Next Nuance Quest ➔")
                }
            }
        }
    }
}

// =================================================================================================
// GAME 12: AUDIO SHADOWING & DICTATION CHALLENGE (محاكاة النطق والتفريغ السمعي)
// =================================================================================================
@Composable
fun AudioShadowingGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(Unit) {
        val ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Initialized successfully
            }
        }
        tts = ttsInstance
        onDispose {
            ttsInstance.stop()
            ttsInstance.shutdown()
        }
    }

    data class AudioChallenge(
        val spokenPhrase: String,
        val langLocale: Locale,
        val options: List<String>,
        val correct: String,
        val phoneticHint: String,
        val meaningAr: String
    )

    val audioChallenges = remember {
        listOf(
            AudioChallenge(
                spokenPhrase = "Sub Judice",
                langLocale = Locale.UK,
                options = listOf("Sub Judice", "Subjudice", "Sub Judy", "Subject Justice"),
                correct = "Sub Judice",
                phoneticHint = "/sʌb ˈdʒuːdɪsi/",
                meaningAr = "قضية معروضة أمام القضاء ويحظر النشر فيها"
            ),
            AudioChallenge(
                spokenPhrase = "Prima Facie",
                langLocale = Locale.US,
                options = listOf("Prima Facie", "Primary Face", "Prima Facey", "Prime Factor"),
                correct = "Prima Facie",
                phoneticHint = "/ˌpraɪmə ˈfeɪʃi/",
                meaningAr = "للوهلة الأولى / دلالة ظاهرية كافية"
            ),
            AudioChallenge(
                spokenPhrase = "Quid Pro Quo",
                langLocale = Locale.US,
                options = listOf("Quid Pro Quo", "Quit Pro Clue", "Kid Pro Call", "Quick Promo"),
                correct = "Quid Pro Quo",
                phoneticHint = "/ˌkwɪd prəʊ ˈkwəʊ/",
                meaningAr = "مقابل مساوٍ / شيء مقابل شيء في الالتزامات"
            ),
            AudioChallenge(
                spokenPhrase = "Ultra Vires",
                langLocale = Locale.UK,
                options = listOf("Ultra Vires", "Ultra Virus", "Ultra Various", "Outer Vires"),
                correct = "Ultra Vires",
                phoneticHint = "/ˌʌltrə ˈvaɪriːz/",
                meaningAr = "تجاوز الصلاحيات والاختصاصات القانونية"
            ),
            AudioChallenge(
                spokenPhrase = "Force Majeure",
                langLocale = Locale.FRENCH,
                options = listOf("Force Majeure", "Force Major", "Forced Mature", "Force Master"),
                correct = "Force Majeure",
                phoneticHint = "/ˌfɔːs mæˈʒɜːr/",
                meaningAr = "القوة القاهرة المعفية من المسؤولية التعاقدية"
            ),
            AudioChallenge(
                spokenPhrase = "Habeas Corpus",
                langLocale = Locale.US,
                options = listOf("Habeas Corpus", "Heavy Corpus", "Have Us Corps", "Habeas Core"),
                correct = "Habeas Corpus",
                phoneticHint = "/ˌheɪbiəs ˈkɔːrpəs/",
                meaningAr = "أمر إحضار للمثول للتحقق من مشروعية الاحتجاز"
            ),
            AudioChallenge(
                spokenPhrase = "Mutatis Mutandis",
                langLocale = Locale.UK,
                options = listOf("Mutatis Mutandis", "Mutant Mutandis", "Mutatis Multi", "Muted Mutandis"),
                correct = "Mutatis Mutandis",
                phoneticHint = "/mjuːˈteɪtɪs mjuːˈtændɪs/",
                meaningAr = "مع مراعاة التعديلات والفوارق الضرورية"
            ),
            AudioChallenge(
                spokenPhrase = "Bona Fide",
                langLocale = Locale.US,
                options = listOf("Bona Fide", "Bonafide", "Bono Fide", "Bone Feed"),
                correct = "Bona Fide",
                phoneticHint = "/ˌboʊnə ˈfaɪdi/",
                meaningAr = "بحسن نية / شرعي وحقيقي دون احتيال"
            ),
            AudioChallenge(
                spokenPhrase = "Ad Interim",
                langLocale = Locale.UK,
                options = listOf("Ad Interim", "Add Interim", "At Interim", "Ad Internal"),
                correct = "Ad Interim",
                phoneticHint = "/æd ˈɪntərɪm/",
                meaningAr = "مؤقتاً / بالنيابة لفترة انتقالية محددة"
            ),
            AudioChallenge(
                spokenPhrase = "Sui Generis",
                langLocale = Locale.US,
                options = listOf("Sui Generis", "Suey Generic", "So Generous", "Sui General"),
                correct = "Sui Generis",
                phoneticHint = "/ˌsuːaɪ ˈdʒɛnərɪs/",
                meaningAr = "فريد من نوعه / ذو طبيعة قانونية قائمة بذاتها"
            ),
            AudioChallenge(
                spokenPhrase = "De Facto",
                langLocale = Locale.UK,
                options = listOf("De Facto", "The Factor", "Defacto", "De Factor"),
                correct = "De Facto",
                phoneticHint = "/deɪ ˈfæktoʊ/",
                meaningAr = "بحكم الأمر الواقع والواقع الفعلي"
            ),
            AudioChallenge(
                spokenPhrase = "Ipso Facto",
                langLocale = Locale.US,
                options = listOf("Ipso Facto", "Episode Facto", "Ipso Factor", "Hypso Facto"),
                correct = "Ipso Facto",
                phoneticHint = "/ˌɪpsoʊ ˈfæktoʊ/",
                meaningAr = "بحكم الواقع نفسه وبذاته تلقائياً"
            )
        )
    }

    var aIdx by remember { mutableIntStateOf(0) }
    var selectedOpt by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }

    val cur = audioChallenges[aIdx]
    val shuffledOptions = remember(aIdx) {
        cur.options.shuffled(java.util.Random(aIdx.toLong() * 17 + 11))
    }

    fun speakCurrentPhrase() {
        tts?.let {
            it.language = cur.langLocale
            it.speak(cur.spokenPhrase, TextToSpeech.QUEUE_FLUSH, null, "shadowing_${System.currentTimeMillis()}")
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_shadowing_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎧 " + if (isArabic) "محاكاة النطق والتفريغ السمعي (Audio Shadowing)" else "Audio Shadowing Reflex",
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary,
                    fontSize = 14.sp
                )
                Badge(containerColor = RedPrimary.copy(alpha = 0.15f)) {
                    Text("${aIdx + 1}/${audioChallenges.size}", fontWeight = FontWeight.Bold, color = RedPrimary)
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = { speakCurrentPhrase() },
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = RedPrimary.copy(alpha = 0.15f)),
                        modifier = Modifier.testTag("listen_audio_term_btn")
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Play Audio", tint = RedPrimary)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isArabic) "استمع للنطق الصوتي الفوري 🔊" else "Listen to Audio 🔊",
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                    }

                    Text(
                        text = if (isArabic) "اضغط للاستماع ثم اختر المصطلح المكتوب بدقة:" else "Listen and identify the exact phrase spoken:",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Shuffled options
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == cur.correct
                val isChosen = selectedOpt == opt
                val bg = when {
                    !answered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                    isChosen -> Color.Red.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !answered) {
                            selectedOpt = opt
                            answered = true
                            if (isCorrect) onAddScore(35)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (answered) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "💡 النطق الفونولوجي: ${cur.phoneticHint}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "الدلالة والمفهوم: ${cur.meaningAr}",
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                Button(
                    onClick = {
                        answered = false
                        selectedOpt = null
                        aIdx = (aIdx + 1) % audioChallenges.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "المصطلح الصوتي التالي ➔" else "Next Audio Challenge ➔")
                }
            }
        }
    }
}
