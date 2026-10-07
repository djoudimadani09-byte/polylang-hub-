package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.delay

enum class GameType(val titleAr: String, val titleEn: String, val icon: String) {
    SPEED_MATCH("مطابقة المصطلحات السريعة", "Speed Term Match", "⚡"),
    FALSE_FRIENDS("صائد الأصدقاء المزيفين", "False Friends Detective", "🕵️"),
    SENTENCE_BUILDER("تركيب الجملة المترجمة", "Sentence Builder", "🧩"),
    BOOTH_REFLEX("تحدي كابينة الفورية", "Booth Reflex Blitz", "🎙️"),
    IDIOMS_MASTER("كاشف التعبيرات الاصطلاحية", "Idioms Decipherer", "💡")
}

@Composable
fun LanguageGamesView(
    isArabic: Boolean,
    userName: String = "طالب الترجمة"
) {
    var selectedGame by remember { mutableStateOf(GameType.SPEED_MATCH) }
    var totalGamerScore by remember { mutableIntStateOf(160) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .testTag("language_games_root"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Game Header & Score Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🎮 " + if (isArabic) "استوديو الألعاب التعليمية للغات والترجمة" else "Language & Translation Games Studio",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isArabic) "5 ألعاب تفاعلية لترسيخ المعاجم القانونية والطبية والدبلوماسية وتفادي الأفخاخ" else "Gamify vocabulary retention and translation reflex",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = GoldYellow.copy(alpha = 0.2f),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("🏆", fontSize = 16.sp)
                        Text(
                            text = "$totalGamerScore pts",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Game Selection Chips (5 Games)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            GameType.values().forEach { game ->
                val isSelected = selectedGame == game
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { selectedGame = game }
                        .testTag("game_chip_${game.name}"),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) RedPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(game.icon, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isArabic) game.titleAr else game.titleEn,
                            fontSize = 8.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        // Active Game Component
        when (selectedGame) {
            GameType.SPEED_MATCH -> SpeedTerminologyMatchGame(
                isArabic = isArabic,
                onAddScore = { totalGamerScore += it }
            )
            GameType.FALSE_FRIENDS -> FalseFriendsDetectiveGame(
                isArabic = isArabic,
                onAddScore = { totalGamerScore += it }
            )
            GameType.SENTENCE_BUILDER -> SentenceBuilderGame(
                isArabic = isArabic,
                onAddScore = { totalGamerScore += it }
            )
            GameType.BOOTH_REFLEX -> BoothReflexRushGame(
                isArabic = isArabic,
                onAddScore = { totalGamerScore += it }
            )
            GameType.IDIOMS_MASTER -> IdiomsDeciphererGame(
                isArabic = isArabic,
                onAddScore = { totalGamerScore += it }
            )
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 1: SPEED TERMINOLOGY MATCHING (With 3 Multi-Domain Rounds)
// -------------------------------------------------------------------------------------------------
@Composable
fun SpeedTerminologyMatchGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class TermPair(val id: Int, val foreign: String, val arabic: String, val domain: String)

    var currentRound by remember { mutableIntStateOf(1) }

    val roundPairs = remember(currentRound) {
        when (currentRound) {
            1 -> listOf(
                TermPair(1, "Force Majeure", "القوة القاهرة", "قانون مدني وتجاري"),
                TermPair(2, "Plenipotentiary", "مفوض فوق العادة ومطلق الصلاحية", "السلك الدبلوماسي"),
                TermPair(3, "Subpoena", "مذكرة إحضار قضائية", "إجراءات جزائية"),
                TermPair(4, "Décalage", "الفارق الزمني في الكابينة", "الترجمة الفورية"),
                TermPair(5, "Indemnity", "تعويض وإبراء ذمة", "العقود والشركات"),
                TermPair(6, "Mutatis Mutandis", "مع مراعاة الفوارق اللازمة", "المصطلحات اللاتينية")
            )
            2 -> listOf(
                TermPair(7, "Informed Consent", "الموافقة المستنيرة المسبقة", "الأخلاقيات الطبية"),
                TermPair(8, "Adverse Event", "حدث ضار / عرض جانبي غير مرغوب", "التجارب السريرية"),
                TermPair(9, "Prophylaxis", "الوقاية الاستباقية / تدبير وقائي", "الطب الوقائي"),
                TermPair(10, "Placebo", "عقار وهمي / علاج إرضائي", "البحث الصيدلاني"),
                TermPair(11, "Contraindication", "موانع الاستعمال الدوائي", "علم الصيدلة"),
                TermPair(12, "Double-Blind", "دراسة مزدوجة التعمية", "التجارب الإكلينيكية")
            )
            else -> listOf(
                TermPair(13, "Point of Order", "نقطة نظام إجرائية", "المؤتمرات الدولية"),
                TermPair(14, "Adjournment", "رفع الجلسة / إرجاء المداولات", "الجمعية العامة"),
                TermPair(15, "Consensus", "التوافق بالتراضي دون تصويت", "المفاوضات الأممية"),
                TermPair(16, "Ratification", "التصديق البرلماني على المعاهدة", "القانون الدولي"),
                TermPair(17, "Memorandum of Understanding", "مذكرة تفاهم رسمية", "العلاقات الثنائية"),
                TermPair(18, "Quorum", "النصاب القانوني لانعقاد الجلسة", "اللوائح الداخلية")
            )
        }
    }

    var selectedForeignId by remember { mutableStateOf<Int?>(null) }
    var selectedArabicId by remember { mutableStateOf<Int?>(null) }
    var matchedIds by remember(currentRound) { mutableStateOf(setOf<Int>()) }
    var streakCombo by remember { mutableIntStateOf(1) }
    var gameCompleted by remember { mutableStateOf(false) }

    val shuffledForeign = remember(currentRound) { roundPairs.shuffled() }
    val shuffledArabic = remember(currentRound) { roundPairs.shuffled() }

    fun checkMatch(fId: Int, aId: Int) {
        if (fId == aId) {
            matchedIds = matchedIds + fId
            onAddScore(20 * streakCombo)
            streakCombo++
            selectedForeignId = null
            selectedArabicId = null
            if (matchedIds.size == roundPairs.size) {
                gameCompleted = true
            }
        } else {
            streakCombo = 1
            selectedForeignId = null
            selectedArabicId = null
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_speed_match"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isArabic) "⚡ لعبة مطابقة المصطلحات السريعة" else "⚡ Speed Terminology Match",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (isArabic) "اختر المجال واضغط على المصطلح لمطابقته مع الترجمة المحلفة" else "Match source term with its certified equivalent",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = RedPrimary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Combo x$streakCombo 🔥",
                        color = RedPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Domain Tabs Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    1 to if (isArabic) "⚖️ العقود والقانون" else "Legal",
                    2 to if (isArabic) "🩺 الطب والصيدلة" else "Medical",
                    3 to if (isArabic) "🏛️ المؤتمرات الدولية" else "Diplomatic"
                ).forEach { (round, title) ->
                    FilterChip(
                        selected = currentRound == round,
                        onClick = {
                            currentRound = round
                            matchedIds = emptySet()
                            gameCompleted = false
                            streakCombo = 1
                        },
                        label = { Text(title, fontSize = 10.5.sp) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (gameCompleted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("🎉 رائع! تم إتقان جميع مصطلحات الجولة بنجاح!", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 14.sp)
                        Text("حصلت على +120 نقطة خبرة لغوية", fontSize = 12.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    currentRound = if (currentRound < 3) currentRound + 1 else 1
                                    matchedIds = emptySet()
                                    gameCompleted = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                            ) {
                                Text(if (isArabic) "الجولة التالية ➔" else "Next Round ➔")
                            }
                            OutlinedButton(
                                onClick = {
                                    matchedIds = emptySet()
                                    gameCompleted = false
                                }
                            ) {
                                Text(if (isArabic) "إعادة التحدي 🔄" else "Replay")
                            }
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Foreign Column
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isArabic) "المصطلح الأجنبي" else "Foreign Term",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        shuffledForeign.forEach { item ->
                            val isMatched = matchedIds.contains(item.id)
                            val isSelected = selectedForeignId == item.id
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable(enabled = !isMatched) {
                                        selectedForeignId = item.id
                                        selectedArabicId?.let { aId -> checkMatch(item.id, aId) }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = when {
                                    isMatched -> SuccessGreen.copy(alpha = 0.2f)
                                    isSelected -> RedPrimary
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                                border = if (isSelected) BorderStroke(1.5.dp, RedPrimary) else null
                            ) {
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                                    Text(
                                        text = item.foreign,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isSelected) Color.White else (if (isMatched) SuccessGreen else MaterialTheme.colorScheme.onSurface)
                                    )
                                    Text(
                                        text = item.domain,
                                        fontSize = 9.5.sp,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // Arabic Column
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isArabic) "المقابل العربي المعتمد" else "Arabic Equivalent",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        shuffledArabic.forEach { item ->
                            val isMatched = matchedIds.contains(item.id)
                            val isSelected = selectedArabicId == item.id
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable(enabled = !isMatched) {
                                        selectedArabicId = item.id
                                        selectedForeignId?.let { fId -> checkMatch(fId, item.id) }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = when {
                                    isMatched -> SuccessGreen.copy(alpha = 0.2f)
                                    isSelected -> RedPrimary
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                                border = if (isSelected) BorderStroke(1.5.dp, RedPrimary) else null
                            ) {
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                                    Text(
                                        text = item.arabic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isSelected) Color.White else (if (isMatched) SuccessGreen else MaterialTheme.colorScheme.onSurface)
                                    )
                                    Text(
                                        text = if (isMatched) "✓ موثق" else "انقر للمطابقة",
                                        fontSize = 9.5.sp,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
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

// -------------------------------------------------------------------------------------------------
// GAME 2: FALSE FRIENDS DETECTIVE (Expanded to 8 Questions)
// -------------------------------------------------------------------------------------------------
@Composable
fun FalseFriendsDetectiveGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class FalseFriendQuestion(
        val word: String,
        val context: String,
        val trapAnswer: String,
        val correctAnswer: String,
        val explanation: String
    )

    val questions = remember {
        listOf(
            FalseFriendQuestion(
                word = "Actually",
                context = "He said he was fine, but actually he was suffering.",
                trapAnswer = "حالياً / في الوقت الراهن ❌",
                correctAnswer = "في الحقيقة والواقع / في الأصل ✔️",
                explanation = "كلمة Actually من أشهر الأفخاخ؛ لا تعني 'حالياً' (Currently/Actuellement)، بل تعني 'في الحقيقة أو في الواقع'."
            ),
            FalseFriendQuestion(
                word = "Éventuel (الفرنسية)",
                context = "Un accord éventuel entre les deux délégations ministérielles.",
                trapAnswer = "اتفاق نهائي وحتمي ❌",
                correctAnswer = "اتفاق محتمل / ممكن الحدوث ✔️",
                explanation = "في الفرنسية Éventuel تعني 'محتمل أو وارد الوقوع'، بينما Eventual بالإنجليزية تعني 'نهائي/في نهاية المطاف'."
            ),
            FalseFriendQuestion(
                word = "Sensible (الإنجليزية)",
                context = "She made a very sensible business decision during the financial crisis.",
                trapAnswer = "حساس / عاطفي مرهف ❌",
                correctAnswer = "حكيم / رشيد ومنطقي ✔️",
                explanation = "كلمة Sensible بالإنجليزية تعني عقلاني أو سديد الرأي. أما 'حساس' فتقابلها كلمة Sensitive."
            ),
            FalseFriendQuestion(
                word = "Préservatif (الفرنسية)",
                context = "Ce n'est pas un produit préservatif pour aliments.",
                trapAnswer = "مادة حافظة للأغذية ❌",
                correctAnswer = "واقي طبي / وقائي ✔️",
                explanation = "المادة الحافظة بالفرنسية هي Conservateur وبالإنجليزية Preservative. الخلط بينهما خطأ فادح في الترجمة الطبية."
            ),
            FalseFriendQuestion(
                word = "Demander (الفرنسية)",
                context = "L'avocat va demander des éclaircissements au tribunal.",
                trapAnswer = "يأمر بحزم / يفرض بالقوة ❌",
                correctAnswer = "يطلب / يلتمس / يسأل ✔️",
                explanation = "الفعل الفرنسي Demander يعني مجرد الطلب أو السؤال، ولا يعني الأمر الجازم كالفعل الإنجليزي Demand."
            ),
            FalseFriendQuestion(
                word = "Librairie (الفرنسية)",
                context = "Il a acheté ce dictionnaire juridique dans une librairie.",
                trapAnswer = "مكتبة عامة للإعارة والمطالعة (Library) ❌",
                correctAnswer = "مكتبة تجارية لبيع الكتب (Bookstore) ✔️",
                explanation = "في الفرنسية: Librairie هي دكان بيع الكتب، بينما المكتبة العامة للمطالعة تسمى Bibliothèque."
            ),
            FalseFriendQuestion(
                word = "Fast (الألمانية)",
                context = "Wir haben die Übersetzung fast abgeschlossen.",
                trapAnswer = "بسرعة فائقة (Fast الإنجليزية) ❌",
                correctAnswer = "تقريباً / على وشك الإنجاز (Almost) ✔️",
                explanation = "في الألمانية Fast تعني 'تقريباً / كاد'، ولا تعني السرعة كما يظن الناطقون بالإنجليزية."
            ),
            FalseFriendQuestion(
                word = "Attendre (الفرنسية)",
                context = "Les diplomates doivent attendre l'arrivée du secrétaire général.",
                trapAnswer = "يحضر الجلسة / يشارك فيها (Attend) ❌",
                correctAnswer = "ينتظر / يترقب الوصول ✔️",
                explanation = "الفعل Attendre بالفرنسية يعني 'ينتظر'، بينما 'يحضر مؤتمراً' تقابلها Assister à."
            )
        )
    }

    var currentQIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }

    val currentQ = questions[currentQIndex]

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_false_friends"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isArabic) "🕵️ صائد الأصدقاء المزيفين (False Friends)" else "🕵️ False Friends Detective",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${currentQIndex + 1} / ${questions.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "المصطلح: \"${currentQ.word}\"",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = RedPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "السياق: ${currentQ.context}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = if (isArabic) "أي الخيارين هو المعنى السليم الدقيق وتجنب فخ الشبه الشكلي؟" else "Pick authentic translation without falling into cognate trap:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )

            // Options
            listOf(currentQ.correctAnswer, currentQ.trapAnswer).shuffled(remember(currentQIndex) { java.util.Random(currentQIndex.toLong()) }).forEach { opt ->
                val isCorrect = opt == currentQ.correctAnswer
                val isChosen = selectedOption == opt
                val btnColor = when {
                    !hasAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.25f)
                    isChosen -> Color.Red.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !hasAnswered) {
                            selectedOption = opt
                            hasAnswered = true
                            if (isCorrect) onAddScore(25)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = btnColor,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (hasAnswered) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("💡 التحليل الترجمي واللغوي:", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(currentQ.explanation, fontSize = 11.sp, lineHeight = 16.sp)
                    }
                }

                Button(
                    onClick = {
                        hasAnswered = false
                        selectedOption = null
                        currentQIndex = (currentQIndex + 1) % questions.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "السؤال التالي ➔" else "Next Question ➔")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 3: SENTENCE BUILDER (5 Tasks)
// -------------------------------------------------------------------------------------------------
@Composable
fun SentenceBuilderGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class SentenceTask(
        val source: String,
        val targetTokens: List<String>,
        val context: String
    )

    val tasks = remember {
        listOf(
            SentenceTask(
                source = "The contracting parties agree to settle all disputes amicably.",
                targetTokens = listOf("اتفقت", "الأطراف", "المتعاقدة", "على", "تسوية", "كافة", "النزاعات", "ودياً"),
                context = "عقود تجارية ومعاهدات"
            ),
            SentenceTask(
                source = "The patient showed marked improvement after the clinical trial.",
                targetTokens = listOf("أظهر", "المريض", "تحسناً", "ملحوظاً", "عقب", "التجربة", "السريرية"),
                context = "ترجمة طبية صيدلانية"
            ),
            SentenceTask(
                source = "The ambassador presented his credentials to the head of state.",
                targetTokens = listOf("قدّم", "السفير", "أوراق", "اعتماده", "إلى", "رئيس", "الدولة"),
                context = "البروتوكول الدبلوماسي"
            ),
            SentenceTask(
                source = "This agreement shall enter into force upon signature by all parties.",
                targetTokens = listOf("تدخل", "هذه", "الاتفاقية", "حيز", "النفاذ", "فور", "توقيع", "الأطراف"),
                context = "صياغة معاهدات دولية"
            ),
            SentenceTask(
                source = "The central bank decided to raise interest rates to curb inflation.",
                targetTokens = listOf("قرر", "البنك", "المركزي", "رفع", "أسعار", "الفائدة", "لكبح", "التضخم"),
                context = "النشرات الاقتصادية والمالية"
            )
        )
    }

    var currentTaskIndex by remember { mutableIntStateOf(0) }
    val task = tasks[currentTaskIndex]

    var selectedTokens by remember { mutableStateOf<List<String>>(emptyList()) }
    var availableTokens by remember(currentTaskIndex) { mutableStateOf(task.targetTokens.shuffled()) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    fun resetTask() {
        selectedTokens = emptyList()
        availableTokens = task.targetTokens.shuffled()
        isSubmitted = false
        isCorrect = false
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_sentence_builder"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isArabic) "🧩 لعبة تركيب وبناء الجملة المترجمة" else "🧩 Sentence Builder",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${currentTaskIndex + 1} / ${tasks.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "النص المصدر (${task.context}):",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = task.source,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = RedPrimary
                    )
                }
            }

            // Built Sentence Display Area
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (selectedTokens.isEmpty()) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, if (isSubmitted) (if (isCorrect) SuccessGreen else Color.Red) else MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 60.dp)
            ) {
                if (selectedTokens.isEmpty()) {
                    Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isArabic) "انقر على الكلمات بالترتيب النحوي السليم لبناء الجملة..." else "Tap words in sequence...",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedTokens.forEachIndexed { idx, token ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = RedPrimary,
                                modifier = Modifier.clickable(enabled = !isSubmitted) {
                                    selectedTokens = selectedTokens.toMutableList().also { it.removeAt(idx) }
                                    availableTokens = availableTokens + token
                                }
                            ) {
                                Text(
                                    text = "$token ✕",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Word Tokens Bank
            Text(
                text = if (isArabic) "بنك الكلمات المتاحة (اضغط للإضافة):" else "Available Words Bank:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                availableTokens.forEach { token ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.clickable {
                            selectedTokens = selectedTokens + token
                            availableTokens = availableTokens - token
                        }
                    ) {
                        Text(
                            text = token,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { resetTask() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isArabic) "إعادة ترتيب 🔄" else "Reset 🔄", fontSize = 11.sp)
                }

                Button(
                    onClick = {
                        isSubmitted = true
                        isCorrect = selectedTokens == task.targetTokens
                        if (isCorrect) onAddScore(30)
                    },
                    enabled = selectedTokens.isNotEmpty() && !isSubmitted,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "تحقق من الصياغة ✔️" else "Verify ✔️", fontSize = 11.sp)
                }
            }

            if (isSubmitted) {
                if (isCorrect) {
                    Text(
                        text = "🎉 صياغة دقيقة واحترافية متطابقة مع المعايير!",
                        color = SuccessGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                } else {
                    Text(
                        text = "❌ الصياغة غير مطابقة، الترتيب النموذجي: " + task.targetTokens.joinToString(" "),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = {
                        currentTaskIndex = (currentTaskIndex + 1) % tasks.size
                        resetTask()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "الجملة التالية ➔" else "Next Sentence ➔")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 4: BOOTH REFLEX BLITZ (6 Scenarios)
// -------------------------------------------------------------------------------------------------
@Composable
fun BoothReflexRushGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class BoothTerm(
        val speech: String,
        val correct: String,
        val wrong: List<String>
    )

    val boothChallenges = remember {
        listOf(
            BoothTerm(
                speech = "\"We cannot ignore the elephant in the room regarding maritime trade.\"",
                correct = "المشكلة الجلية والواضحة التي يتفاداها الجميع",
                wrong = listOf("وجود الفيل الضخم في الغرفة", "التجارة البحرية الحيوانية", "العائق الإداري البسيط")
            ),
            BoothTerm(
                speech = "\"The resolution was adopted by acclamation without a vote.\"",
                correct = "اعتُمد القرار بالإجماع والتصفيق دون اقتراع",
                wrong = listOf("اعتُمد القرار بعد مناقشة عاصفة", "اعتُمد القرار بأغلبية الثلثين", "تم تأجيل القرار لجلسة قادمة")
            ),
            BoothTerm(
                speech = "\"This provision is without prejudice to national security interests.\"",
                correct = "دون الإخلال بمصالح الأمن القومي / مع عدم المساس بها",
                wrong = listOf("مع إلحاق الضرر بالأمن القومي", "بناءً على طلب الأمن القومي", "خارج نطاق السيادة الوطنية")
            ),
            BoothTerm(
                speech = "\"The parties must demonstrate political will to break the deadlock.\"",
                correct = "إبداء الإرادة السياسية لكسر الجمود والانسداد",
                wrong = listOf("إظهار القوة العسكرية لتفجير المأزق", "الرغبة في تأجيل المفاوضات", "الوصول إلى طريق مسدود حتمي")
            ),
            BoothTerm(
                speech = "\"We call for the immediate cessation of hostilities on all fronts.\"",
                correct = "الوقف الفوري للأعمال العدائية على كافة الجبهات",
                wrong = listOf("استمرار العمليات العسكرية بحذر", "تعليق المساعدات الإنسانية فوراً", "إعادة تنظيم القوات المسلحة")
            ),
            BoothTerm(
                speech = "\"The draft declaration reflects our shared commitment to net-zero emissions.\"",
                correct = "مشروع الإعلان يعكس التزامنا المشترك بالوصول للحياد الكربوني",
                wrong = listOf("البيان الختامي يرفض خفض الانبعاثات", "الإعلان ينفي وجود التغير المناخي", "تخفيض بنسبة صفر بالمئة")
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAns by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableIntStateOf(10) }

    val challenge = boothChallenges[currentIndex]
    val options = remember(currentIndex) {
        (challenge.wrong + challenge.correct).shuffled()
    }

    LaunchedEffect(currentIndex, answered) {
        if (!answered) {
            timerSeconds = 10
            while (timerSeconds > 0 && !answered) {
                delay(1000)
                timerSeconds--
            }
            if (timerSeconds == 0 && !answered) {
                answered = true
                selectedAns = "TIMEOUT"
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_booth_reflex"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isArabic) "🎙️ تحدي كابينة المترجم الفوري (Booth Blitz)" else "🎙️ Booth Reflex Blitz",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (isArabic) "محاكاة الضغط اللحظي: اختر المقابل السريع قبل انتهاء المؤقت!" else "Pick translation under 10s booth pressure",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = if (timerSeconds <= 3) Color.Red else RedPrimary,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$timerSeconds",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Booth Microphone Simulator
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF0F172A),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red))
                        Text(
                            text = "LIVE SPEECH AUDIO FEED",
                            color = Color.Red,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = challenge.speech,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
                    )
                }
            }

            // Options List
            options.forEach { opt ->
                val isCorrect = opt == challenge.correct
                val isChosen = selectedAns == opt
                val bg = when {
                    !answered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.3f)
                    isChosen -> Color.Red.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !answered) {
                            selectedAns = opt
                            answered = true
                            if (isCorrect) onAddScore(25 + timerSeconds * 2)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (answered) {
                if (selectedAns == "TIMEOUT") {
                    Text("⏳ انتهى الوقت! في الكابينة، التأخير أكثر من 8 ثوانٍ يسبب انقطاع المعنى.", color = Color.Red, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        answered = false
                        selectedAns = null
                        currentIndex = (currentIndex + 1) % boothChallenges.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "التحدي التالي 🎙️" else "Next Speech Feed 🎙️")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 5: IDIOMS & COLLOCATIONS DECIPHERER (NEW GAME)
// -------------------------------------------------------------------------------------------------
@Composable
fun IdiomsDeciphererGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class IdiomItem(
        val idiom: String,
        val literalTrap: String,
        val culturalEquivalent: String,
        val context: String,
        val explanation: String
    )

    val idioms = remember {
        listOf(
            IdiomItem(
                idiom = "To bite the bullet",
                literalTrap = "عضّ الرصاصة بأسنانه ❌",
                culturalEquivalent = "تجرّع المرارة / الإقدام على أمر شاق لا مفر منه ✔️",
                context = "\"The government had to bite the bullet and cut subsidies.\"",
                explanation = "تعبير اصطلاحي يعود لعلاج الجرحى قديماً؛ ويعني قبول موقف صعب ومؤلم بشجاعة لأن لا مفر منه."
            ),
            IdiomItem(
                idiom = "A level playing field",
                literalTrap = "ملعب كرة قدم مستوٍ ❌",
                culturalEquivalent = "تكافؤ الفرص والعدالة التنافسية ✔️",
                context = "\"New antitrust laws ensure a level playing field for all tech startups.\"",
                explanation = "في الاقتصاد والتجارة، يعني توفير شروط تنافسية عادلة ومتساوية لكافة الأطراف دون تمييز."
            ),
            IdiomItem(
                idiom = "To give someone the cold shoulder",
                literalTrap = "إعطاء كتف بارد ولحم بارد ❌",
                culturalEquivalent = "إبداء الجفاء / التجاهل المقصود والصدّ المتعمد ✔️",
                context = "\"The delegate received a cold shoulder from his counterparts.\"",
                explanation = "يعني التعامل بجفاء وازدراء مقصود ورفض التواصل مع الشخص."
            ),
            IdiomItem(
                idiom = "Back to the drawing board",
                literalTrap = "العودة إلى لوح الرسم الخشبي ❌",
                culturalEquivalent = "العودة إلى نقطة الصفر / إعادة التخطيط من البداية ✔️",
                context = "\"After the talks collapsed, the mediators went back to the drawing board.\"",
                explanation = "يعني فشل الخطة والاضطرار للبدء من جديد بوضع خطة بديلة."
            ),
            IdiomItem(
                idiom = "To read between the lines",
                literalTrap = "قراءة الفراغ الأبيض بين السطور ❌",
                culturalEquivalent = "استشفاف المعنى الضمني / فهم ما وراء الكلمات ✔️",
                context = "\"Diplomats must know how to read between the lines in communiqués.\"",
                explanation = "مهارة فهم المعاني غير المصرح بها صراحة في الخطابات الدبلوماسية."
            )
        )
    }

    var currentIdx by remember { mutableIntStateOf(0) }
    var selectedAns by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val item = idioms[currentIdx]

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_idioms_master"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isArabic) "💡 كاشف التعبيرات الاصطلاحية والأمثال" else "💡 Idioms & Collocations Master",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${currentIdx + 1} / ${idioms.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "التعبير الاصطلاحي: \"${item.idiom}\"",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "السياق: ${item.context}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = if (isArabic) "أي الخيارين يمثل المقابل الثقافي البلاغي الدقيق ويتجنب الترجمة الحرفية القاتلة؟" else "Pick the authentic cultural equivalent:",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )

            listOf(item.culturalEquivalent, item.literalTrap).shuffled(remember(currentIdx) { java.util.Random(currentIdx.toLong()) }).forEach { opt ->
                val isCorrect = opt == item.culturalEquivalent
                val isChosen = selectedAns == opt
                val bg = when {
                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.25f)
                    isChosen -> Color.Red.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !isAnswered) {
                            selectedAns = opt
                            isAnswered = true
                            if (isCorrect) onAddScore(30)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (isAnswered) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("💡 الأصل البلاغي والترجمي:", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(item.explanation, fontSize = 11.sp, lineHeight = 16.sp)
                    }
                }

                Button(
                    onClick = {
                        isAnswered = false
                        selectedAns = null
                        currentIdx = (currentIdx + 1) % idioms.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "التعبير التالي ➔" else "Next Idiom ➔")
                }
            }
        }
    }
}
