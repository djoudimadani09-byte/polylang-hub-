package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
    SPEED_MATCH("مطابقة المصطلحات السريعة", "Speed Terminology Match", "⚡"),
    FALSE_FRIENDS("صائد الأصدقاء المزيفين", "False Friends Detective", "🕵️"),
    SENTENCE_BUILDER("تركيب الجملة المترجمة", "Sentence Builder Challenge", "🧩"),
    BOOTH_REFLEX("تحدي سرعة بديهة الكابينة", "Simultaneous Booth Blitz", "🎙️")
}

@Composable
fun LanguageGamesView(
    isArabic: Boolean,
    userName: String = "طالب الترجمة"
) {
    var selectedGame by remember { mutableStateOf(GameType.SPEED_MATCH) }
    var totalGamerScore by remember { mutableIntStateOf(120) }

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
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "🎮 " + if (isArabic) "استوديو الألعاب التعليمية للغات والترجمة" else "Language & Translation Games Studio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = if (isArabic) "طوّر حصيلتك اللغوية وسرعة استحضار المصطلحات بالتحديات التفاعلية" else "Gamify vocabulary retention and translation reflex",
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

        // Game Selection Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(game.icon, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isArabic) game.titleAr else game.titleEn,
                            fontSize = 9.5.sp,
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
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 1: SPEED TERMINOLOGY MATCHING
// -------------------------------------------------------------------------------------------------
@Composable
fun SpeedTerminologyMatchGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class TermPair(val id: Int, val foreign: String, val arabic: String, val lang: String)

    val allPairs = remember {
        listOf(
            TermPair(1, "Force Majeure", "القوة القاهرة", "القانون التجاري"),
            TermPair(2, "Plenipotentiary", "مفوض فوق العادة", "السلك الدبلوماسي"),
            TermPair(3, "Subpoena", "مذكرة إحضار قضائية", "القانون الجنائي"),
            TermPair(4, "Décalage", "الفارق الزمني في الكابينة", "الترجمة الفورية"),
            TermPair(5, "Indemnity", "تعويض وإبراء ذمة", "العقود والشركات"),
            TermPair(6, "Mutatis Mutandis", "مع مراعاة الفوارق اللازمة", "المصطلحات اللاتينية")
        )
    }

    var selectedForeignId by remember { mutableStateOf<Int?>(null) }
    var selectedArabicId by remember { mutableStateOf<Int?>(null) }
    var matchedIds by remember { mutableStateOf(setOf<Int>()) }
    var mistakeStreak by remember { mutableStateOf(0) }
    var streakCombo by remember { mutableIntStateOf(1) }
    var gameCompleted by remember { mutableStateOf(false) }

    val shuffledForeign = remember { allPairs.shuffled() }
    val shuffledArabic = remember { allPairs.shuffled() }

    fun checkMatch(fId: Int, aId: Int) {
        if (fId == aId) {
            matchedIds = matchedIds + fId
            onAddScore(20 * streakCombo)
            streakCombo++
            selectedForeignId = null
            selectedArabicId = null
            if (matchedIds.size == allPairs.size) {
                gameCompleted = true
            }
        } else {
            mistakeStreak++
            streakCombo = 1
            selectedForeignId = null
            selectedArabicId = null
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_speed_match"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
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
                        text = if (isArabic) "اضغط على المصطلح بالإنجليزية/الفرنسية ثم اختر المقابل العربي الدقيق" else "Tap a term then tap its exact Arabic sworn equivalent",
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
                        Text("🎉 ممتاز! تم إتقان جميع المصطلحات بنجاح!", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 15.sp)
                        Text("حصلت على +120 نقطة خبرة لغوية للمترجمين", fontSize = 12.5.sp)
                        Button(
                            onClick = {
                                matchedIds = emptySet()
                                gameCompleted = false
                                streakCombo = 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                        ) {
                            Text(if (isArabic) "إعادة التحدي 🔄" else "Play Again 🔄")
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
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                    Text(
                                        text = item.foreign,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp,
                                        color = if (isSelected) Color.White else (if (isMatched) SuccessGreen else MaterialTheme.colorScheme.onSurface)
                                    )
                                    Text(
                                        text = item.lang,
                                        fontSize = 10.sp,
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
                            text = if (isArabic) "المقابل العربي المعتمد" else "Arabic Sworn Equivalent",
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
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                    Text(
                                        text = item.arabic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp,
                                        color = if (isSelected) Color.White else (if (isMatched) SuccessGreen else MaterialTheme.colorScheme.onSurface)
                                    )
                                    Text(
                                        text = if (isMatched) "✓ تم التوثيق" else "انقر للمطابقة",
                                        fontSize = 10.sp,
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
// GAME 2: FALSE FRIENDS DETECTIVE (صائد الأصدقاء المزيفين)
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
                context = "Un accord éventuel entre les deux délégations.",
                trapAnswer = "اتفاق نهائي وحتمي ❌",
                correctAnswer = "اتفاق محتمل / ممكن الحدوث ✔️",
                explanation = "في الفرنسية Éventuel تعني 'محتمل أو وارد الوقوع'، بينما Eventual بالإنجليزية تعني 'نهائي/في نهاية المطاف'."
            ),
            FalseFriendQuestion(
                word = "Sensible (الإنجليزية)",
                context = "She made a very sensible business decision.",
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
            )
        )
    }

    var currentQIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }

    val currentQ = questions[currentQIndex]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_false_friends"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
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
                        fontSize = 17.sp,
                        color = RedPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "السياق: ${currentQ.context}",
                        fontSize = 12.5.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = if (isArabic) "أي الخيارين هو المعنى السليم الدقيق وتجنب فخ الشبه الشكلي؟" else "Which is the authentic meaning without falling into the false cognate trap?",
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
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = opt,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            if (hasAnswered) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GoldYellow.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("💡 التحليل الترجمي واللغوي:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(currentQ.explanation, fontSize = 11.5.sp, lineHeight = 16.sp)
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
                    Text(if (isArabic) "السؤال التالي ➡️" else "Next Word ➡️")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 3: SENTENCE BUILDER (تركيب الجملة المترجمة)
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
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_sentence_builder"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isArabic) "🧩 لعبة تركيب وبناء الجملة المترجمة" else "🧩 Sentence Builder Challenge",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text(task.context, fontSize = 10.5.sp) }
                )
            }

            // Source Sentence Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "النص المصدر (Source):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = task.source,
                        fontSize = 14.sp,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 65.dp)
            ) {
                if (selectedTokens.isEmpty()) {
                    Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isArabic) "انقر على الكلمات بالترتيب النحوي السليم لبناء الجملة..." else "Tap words in grammatical sequence...",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedTokens.forEachIndexed { idx, token ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = RedPrimary,
                                modifier = Modifier.clickable(enabled = !isSubmitted) {
                                    // Remove token and return to available
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
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { resetTask() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isArabic) "إعادة ترتيب 🔄" else "Reset 🔄")
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
                    Text(if (isArabic) "تحقق من الصياغة ✔️" else "Verify ✔️")
                }
            }

            if (isSubmitted) {
                if (isCorrect) {
                    Text(
                        text = "🎉 صياغة دقيقة واحترافية متطابقة مع المعايير!",
                        color = SuccessGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )
                } else {
                    Text(
                        text = "❌ الصياغة غير مطابقة، الترتيب النموذجي: " + task.targetTokens.joinToString(" "),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp
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
                    Text(if (isArabic) "الجملة التالية ➡️" else "Next Sentence ➡️")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 4: BOOTH REFLEX BLITZ (تحدي سرعة بديهة الكابينة)
// -------------------------------------------------------------------------------------------------
@Composable
fun BoothReflexRushGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class BoothTerm(
        val speakerSentence: String,
        val correctChoice: String,
        val wrongChoices: List<String>
    )

    val boothChallenges = remember {
        listOf(
            BoothTerm(
                speakerSentence = "\"We cannot ignore the elephant in the room regarding maritime trade.\"",
                correctChoice = "المشكلة الجلية والواضحة التي يتفاداها الجميع",
                wrongChoices = listOf("وجود الفيل الضخم في الغرفة", "التجارة البحرية الحيوانية", "العائق الإداري البسيط")
            ),
            BoothTerm(
                speakerSentence = "\"The resolution was adopted by acclamation without a vote.\"",
                correctChoice = "اعتُمد القرار بالإجماع والتصفيق",
                wrongChoices = listOf("اعتُمد القرار بعد مناقشة عاصفة", "اعتُمد القرار بالأغلبية البسيطة", "تم تأجيل القرار للاقتراع")
            ),
            BoothTerm(
                speakerSentence = "\"This provision is without prejudice to national security.\"",
                correctChoice = "دون الإخلال بالأمن القومي / مع عدم المساس به",
                wrongChoices = listOf("مع إلحاق الضرر بالأمن القومي", "بناءً على طلب الأمن القومي", "خارج نطاق السيادة الوطنية")
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAns by remember { mutableStateOf<String?>(null) }
    var answered by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableIntStateOf(10) }

    val challenge = boothChallenges[currentIndex]
    val options = remember(currentIndex) {
        (challenge.wrongChoices + challenge.correctChoice).shuffled()
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
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_booth_reflex"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
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
                        text = if (isArabic) "محاكاة الضغط اللحظي: اختر المقابل السريع قبل انتهاء المؤقت!" else "Pick the exact translation under 10-second booth pressure",
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
                        text = challenge.speakerSentence,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp
                    )
                }
            }

            // Options List
            options.forEach { opt ->
                val isCorrect = opt == challenge.correctChoice
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
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (answered) {
                if (selectedAns == "TIMEOUT") {
                    Text("⏳ انتهى الوقت! في الكابينة، التأخير أكثر من 8 ثوانٍ يسبب انقطاع المعنى.", color = Color.Red, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
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
