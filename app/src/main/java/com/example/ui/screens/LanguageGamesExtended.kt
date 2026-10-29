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
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val currentQ = questions[currentIndex]

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

            // Options Grid
            currentQ.options.forEach { opt ->
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
            )
        )
    }

    var activeIdx by remember { mutableIntStateOf(0) }
    var selectedFix by remember { mutableStateOf<String?>(null) }
    var isEvaluated by remember { mutableStateOf(false) }

    val c = cases[activeIdx]

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

            c.options.forEach { opt ->
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
// GAME 11: CULTURAL & LEGAL NUANCE QUEST (تحدي الفروق الثقافية والقانونية)
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
                question = "ما هو الفرق الجوهري بين مصطلح 'Notary Public' في الولايات المتحدة ومصطلح 'Notaire' في فرنسا والجزائر؟",
                options = listOf(
                    "الـ Notaire محامٍ موثق عمومي يحمل صفة قضائية رسمية، بينما في أمريكا هو مجرد شاهد على التوقيع دون فحص قانوني",
                    "لا يوجد أي فرق، كلاهما قضاة في المحكمة العليا",
                    "الـ Notary Public الأمريكي يجب أن يكون حاصلاً على دكتوراه في القانون الدولي"
                ),
                correct = "الـ Notaire محامٍ موثق عمومي يحمل صفة قضائية رسمية، بينما في أمريكا هو مجرد شاهد على التوقيع دون فحص قانوني",
                legalInsight = "في الدول اللاتينية (Civil Law) كفرنسا والجزائر، الموثق ضابط عمومي يصيغ العقود الرسمية. أما في الولايات المتحدة فالـ Notary Public يكتفي بالتأكد من هوية الموقعين فقط."
            ),
            NuanceQuest(
                question = "كيف تُترجم عبارة 'Certified True Copy' في المستندات الرسمية المعتمدة؟",
                options = listOf(
                    "نسخة طبق الأصل ومطابقة للأصل المحفوظ",
                    "نسخة حقيقية معتمدة من الإنترنت",
                    "صورة طبق النسخة الأصلية"
                ),
                correct = "نسخة طبق الأصل ومطابقة للأصل المحفوظ",
                legalInsight = "الصياغة الإدارية والقانونية الجزائرية والدولية المعتمدة هي 'نسخة طبق الأصل' المقابلة للفرنسية 'Copie conforme à l\\'original'."
            ),
            NuanceQuest(
                question = "في القانون الدولي الخاص، ماذا يعني مفهوم 'Locus regit actum' عند توثيق العقود؟",
                options = listOf(
                    "قانون محل إبرام التصرف هو الذي يحكم شكله وصحته الظاهرية",
                    "القاضي يحكم وفق قانون موطنه فقط",
                    "مكان إقامة المدعى عليه هو الاختصاص الحصري"
                ),
                correct = "قانون محل إبرام التصرف هو الذي يحكم شكله وصحته الظاهرية",
                legalInsight = "قاعدة لاتينية شهيرة في تنازع القوانين تعني أن شكل التصرف القانوني يخضع لقانون الدولة التي أُبرم فيها."
            )
        )
    }

    var qIndex by remember { mutableIntStateOf(0) }
    var userChoice by remember { mutableStateOf<String?>(null) }
    var solved by remember { mutableStateOf(false) }

    val q = quests[qIndex]

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

            q.options.forEach { opt ->
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
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("⚖️ إضاءة فقهية وقانونية مقارنة:", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(3.dp))
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
                    Text(if (isArabic) "السؤال المقارن التالي ➔" else "Next Nuance Question ➔")
                }
            }
        }
    }
}

// =================================================================================================
// GAME 12: AUDIO SHADOWING & DICTATION REFLEX (محاكاة النطق والتفريغ الصوتي الفوري)
// =================================================================================================
@Composable
fun AudioShadowingGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isTtsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsInstance?.language = Locale.US
                isTtsReady = true
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
            )
        )
    }

    var aIdx by remember { mutableIntStateOf(0) }
    var selectedOpt by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }

    val cur = audioChallenges[aIdx]

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

            cur.options.forEach { opt ->
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
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("الرمز الصوتي والنطق: ${cur.phoneticHint}", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                        Text("المعنى في الترجمة الرسمية: ${cur.meaningAr}", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
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
                    Text(if (isArabic) "المصطلح الصوتي التالي ➔" else "Next Audio Term ➔")
                }
            }
        }
    }
}
