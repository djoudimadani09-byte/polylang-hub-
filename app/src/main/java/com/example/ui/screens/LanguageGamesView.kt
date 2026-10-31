package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.PlatformTelemetry
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
    IDIOMS_MASTER("كاشف التعبيرات الاصطلاحية", "Idioms Decipherer", "💡"),
    ACRONYM_CRACKER("مفكك الاختصارات الدولية", "Acronyms Cracker", "🌐"),
    SIGHT_TRANSLATION_SPRINT("تحدي الترجمة المنظورة", "Sight Sprint", "👁️"),
    TERMINOLOGY_BATTLE("معركة المصطلحات المتخصصة", "Domain Battle", "⚔️"),
    COLLOCATION_MASTER("ماستر المتلازمات اللفظية", "Collocation Master", "🔗"),
    PROOFREADER_CHALLENGE("كاشف أخطاء الترجمة", "Proofreader Detective", "🔍"),
    CULTURAL_NUANCE_QUEST("الفروق القانونية والثقافية", "Legal & Cultural Nuance", "🏛️"),
    AUDIO_SHADOWING_CHALLENGE("محاكاة النطق والتفريغ", "Audio Shadowing & Dictation", "🎧")
}

@Composable
fun LanguageGamesView(
    isArabic: Boolean,
    userName: String = "طالب الترجمة"
) {
    var selectedGame by remember { mutableStateOf(GameType.SPEED_MATCH) }
    var totalGamerScore by remember { mutableIntStateOf(160) }

    LaunchedEffect(Unit) {
        PlatformTelemetry.logSession(
            userName = userName,
            userEmail = "learner@polylang.dz",
            role = "طالب / متعلم",
            actionType = "دخول استوديو الألعاب",
            details = "بدء تدريب تفاعلي على 12 لعبة لغات وتثبيت مصطلحات"
        )
    }

    fun onGameScoreEarned(pts: Int) {
        totalGamerScore += pts
        PlatformTelemetry.logExercise(
            studentName = userName,
            studentEmail = "learner@polylang.dz",
            type = "ألعاب اللغات",
            title = selectedGame.titleAr,
            score = "+$pts نقطة (المجموع: $totalGamerScore)"
        )
    }

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
                        text = if (isArabic) "12 لعبة تفاعلية لترسيخ المعاجم القانونية والطبية والدبلوماسية وتفادي الأفخاخ" else "12 Gamified language & translation reflex challenges",
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

        // Game Selection Chips (8 Games - Scrollable LazyRow for great ergonomics & aesthetics)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(GameType.values().size) { idx ->
                val game = GameType.values()[idx]
                val isSelected = selectedGame == game
                Surface(
                    modifier = Modifier
                        .width(110.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedGame = game }
                        .testTag("game_chip_${game.name}"),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) RedPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(game.icon, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = if (isArabic) game.titleAr else game.titleEn,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            lineHeight = 13.sp
                        )
                    }
                }
            }
        }

        // Active Game Component
        when (selectedGame) {
            GameType.SPEED_MATCH -> SpeedTerminologyMatchGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.FALSE_FRIENDS -> FalseFriendsDetectiveGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.SENTENCE_BUILDER -> SentenceBuilderGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.BOOTH_REFLEX -> BoothReflexRushGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.IDIOMS_MASTER -> IdiomsDeciphererGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.ACRONYM_CRACKER -> AcronymsCrackerGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.SIGHT_TRANSLATION_SPRINT -> SightTranslationSprintGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.TERMINOLOGY_BATTLE -> TerminologyBattleGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.COLLOCATION_MASTER -> CollocationMasterGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.PROOFREADER_CHALLENGE -> ProofreaderChallengeGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.CULTURAL_NUANCE_QUEST -> CulturalNuancesGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
            )
            GameType.AUDIO_SHADOWING_CHALLENGE -> AudioShadowingGame(
                isArabic = isArabic,
                onAddScore = { onGameScoreEarned(it) }
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
// GAME 2: FALSE FRIENDS DETECTIVE (16 Curated Traps with Multi-Option Shuffling)
// -------------------------------------------------------------------------------------------------
@Composable
fun FalseFriendsDetectiveGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class FalseFriendQuestion(
        val word: String,
        val context: String,
        val options: List<String>,
        val correctAnswer: String,
        val explanation: String
    )

    val questions = remember {
        listOf(
            FalseFriendQuestion(
                word = "Actually (الإنجليزية)",
                context = "He seemed composed, but actually he was under severe emotional stress.",
                options = listOf("في الحقيقة والواقع / في الأصل", "حالياً / في الوقت الراهن", "بالتأكيد / بلا أدنى شك"),
                correctAnswer = "في الحقيقة والواقع / في الأصل",
                explanation = "كلمة Actually من أشهر الأفخاخ؛ لا تعني حالياً (Currently/Actuellement)، بل تعني في الحقيقة والواقع."
            ),
            FalseFriendQuestion(
                word = "Éventuel (الفرنسية)",
                context = "Un accord éventuel entre les ministres de l'énergie des deux pays.",
                options = listOf("محتمل / ممكن الحدوث مستقبلاً", "اتفاق نهائي وحتمي", "استثنائي وفوري"),
                correctAnswer = "محتمل / ممكن الحدوث مستقبلاً",
                explanation = "في الفرنسية Éventuel تعني محتمل أو وارد الوقوع، بينما Eventual بالإنجليزية تعني نهائي أو في نهاية المطاف."
            ),
            FalseFriendQuestion(
                word = "Sensible (الإنجليزية)",
                context = "The court reached a sensible and balanced commercial compromise.",
                options = listOf("حكيم / رشيد ومنطقي", "حساس ومرهف المشاعر", "ملحوظ وجوهري"),
                correctAnswer = "حكيم / رشيد ومنطقي",
                explanation = "كلمة Sensible بالإنجليزية تعني عقلاني أو سديد الرأي. أما حساس فتقابلها Sensitive."
            ),
            FalseFriendQuestion(
                word = "Préservatif (الفرنسية)",
                context = "L'usage de ce produit comme préservatif alimentaire est formellement interdit.",
                options = listOf("واقي طبي / وسيلة حماية وقائية", "مادة حافظة للأغذية", "محلول تعقيم دوائي"),
                correctAnswer = "واقي طبي / وسيلة حماية وقائية",
                explanation = "المادة الحافظة بالفرنسية هي Conservateur وبالإنجليزية Preservative. الخلط بينهما خطأ فادح في الترجمة الطبية."
            ),
            FalseFriendQuestion(
                word = "Demander (الفرنسية)",
                context = "L'avocat va demander des éclaircissements au greffier du tribunal.",
                options = listOf("يطلب / يسأل / يلتمس بأدب", "يأمر بحزم ويفرض بالقوة", "يرفض الامتثال لطلب المحكمة"),
                correctAnswer = "يطلب / يسأل / يلتمس بأدب",
                explanation = "الفعل الفرنسي Demander يعني مجرد الطلب أو السؤال، ولا يعني الأمر الجازم كالفعل الإنجليزي Demand."
            ),
            FalseFriendQuestion(
                word = "Librairie (الفرنسية)",
                context = "Il a acheté ce dictionnaire juridique ancien dans une librairie parisienne.",
                options = listOf("مكتبة تجارية لبيع الكتب", "مكتبة عامة للإعارة والمطالعة", "دار نشر وترجمة دولية"),
                correctAnswer = "مكتبة تجارية لبيع الكتب",
                explanation = "في الفرنسية: Librairie هي دكان بيع الكتب (Bookstore)، بينما المكتبة العامة للمطالعة تسمى Bibliothèque."
            ),
            FalseFriendQuestion(
                word = "Fast (الألمانية)",
                context = "Wir haben die juristische Übersetzung fast abgeschlossen.",
                options = listOf("تقريباً / على وشك الإنجاز", "بسرعة فائقة واستعجال", "بشكل دائم ومستمر"),
                correctAnswer = "تقريباً / على وشك الإنجاز",
                explanation = "في الألمانية Fast تعني تقريباً أو كاد (Almost)، ولا تعني السرعة كما في الإنجليزية."
            ),
            FalseFriendQuestion(
                word = "Attendre (الفرنسية)",
                context = "Les diplomates doivent attendre l'arrivée du secrétaire général au salon d'honneur.",
                options = listOf("ينتظر / يترقب الوصول", "يحضر الجلسة ويشارك فيها", "يستمع لخطاب الوفد"),
                correctAnswer = "ينتظر / يترقب الوصول",
                explanation = "الفعل Attendre بالفرنسية يعني ينتظر، بينما يحضر مؤتمراً تقابلها Assister à."
            ),
            FalseFriendQuestion(
                word = "Deception (الإنجليزية)",
                context = "The contractor engaged in wilful deception regarding the origin of materials.",
                options = listOf("الخداع والتضليل المتعمد", "خيبة الأمل والإحباط الشديد", "الاشتباه في الجودة"),
                correctAnswer = "الخداع والتضليل المتعمد",
                explanation = "في الإنجليزية Deception تعني الخداع والغش، أما في الفرنسية Déception فتعني خيبة الأمل!"
            ),
            FalseFriendQuestion(
                word = "Blamieren (الألمانية)",
                context = "Der Dolmetscher wollte sich vor der UN-Delegation nicht blamieren.",
                options = listOf("يُحرج نفسه أو يجعل من نفسه أضحوكة", "يلوم وينتقد الآخرين بشدة", "يتلعثم في النطق الفوري"),
                correctAnswer = "يُحرج نفسه أو يجعل من نفسه أضحوكة",
                explanation = "في الألمانية Blamieren تعني إحراج النفس وجلب العار لها، ولا تعني إلقاء اللوم على الغير (To blame)."
            ),
            FalseFriendQuestion(
                word = "Blessé (الفرنسية)",
                context = "Trois casques bleus ont été blessés lors de la patrouille nocturne.",
                options = listOf("أُصيب بجروح / جريح", "مبارك ومحمي إلهياً", "ممتن وشاكر للدعم"),
                correctAnswer = "أُصيب بجروح / جريح",
                explanation = "كلمة Blessé بالفرنسية تعني جريح، بينما كلمة Blessed بالإنجليزية تعني مبارك أو محظوظ."
            ),
            FalseFriendQuestion(
                word = "Sympathetic (الإنجليزية)",
                context = "The arbitrator was very sympathetic to the humanitarian plight of refugees.",
                options = listOf("متعاطف ومتفهم وذو رحمة", "لطيف ومحبوب واجتماعي", "متطابق في الآراء السياسية"),
                correctAnswer = "متعاطف ومتفهم وذو رحمة",
                explanation = "كلمة Sympathetic تعني التعاطف مع محنة الغير، بينما لطيف/محبوب في الفرنسية واللغات اللاتينية هي Sympathique."
            ),
            FalseFriendQuestion(
                word = "Physician (الإنجليزية)",
                context = "The patient was examined by a board-certified physician.",
                options = listOf("طبيب معالج / دكتور بشري", "عالم فيزياء وتجارب نووية", "معالج بالأعشاب الطبيعية"),
                correctAnswer = "طبيب معالج / دكتور بشري",
                explanation = "كلمة Physician تعني حصراً الطبيب البشري الممارس، بينما عالم الفيزياء هو Physicist."
            ),
            FalseFriendQuestion(
                word = "Gift (الألمانية)",
                context = "Vorsicht vor diesem gefährlichen Gift im Laboratorium!",
                options = listOf("سم قاتل / مادة سامة", "هدية تذكارية مجانية", "موهبة فطرية خارقة"),
                correctAnswer = "سم قاتل / مادة سامة",
                explanation = "كلمة Gift بالألمانية تعني سُمّاً خطيراً، على النقيض التام من معناها الإنجليزي (هدية)."
            ),
            FalseFriendQuestion(
                word = "Envie (الفرنسية)",
                context = "J'ai une grande envie d'assister à ce sommet diplomatique.",
                options = listOf("رغبة شديدة أو تطلع أو اشتهاء", "حسد وحقد وضغينة تجاه الآخرين", "غيرة مهنية وتنافس"),
                correctAnswer = "رغبة شديدة أو تطلع أو اشتهاء",
                explanation = "في الفرنسية Avoir envie de تعني الرغبة في الشيء، بينما Envy بالإنجليزية تعني حصراً الحسد وتمني زوال النعمة."
            ),
            FalseFriendQuestion(
                word = "Actuel (الفرنسية)",
                context = "La crise actuelle du marché pétrolier exige une concertation urgente.",
                options = listOf("الحالية / الراهنة في الوقت المعاصر", "الحقيقية والفعلية والملموسة", "النشطة والفاعلة"),
                correctAnswer = "الحالية / الراهنة في الوقت المعاصر",
                explanation = "في الفرنسية Actuel تعني الراهن والحالي، بينما Actual بالإنجليزية تعني الحقيقي والفعلي."
            )
        )
    }

    var currentQIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }

    val currentQ = questions[currentQIndex]
    val shuffledOptions = remember(currentQIndex) { currentQ.options.shuffled() }

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
                text = if (isArabic) "أي الخيارات يمثل المعنى السليم الدقيق ويكشف فخ الشبه الشكلي؟" else "Pick the authentic translation without falling into cognate trap:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )

            // Dynamic Shuffled Options
            shuffledOptions.forEach { opt ->
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
// GAME 3: SENTENCE BUILDER
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
        val options: List<String>,
        val culturalEquivalent: String,
        val context: String,
        val explanation: String
    )

    val idioms = remember {
        listOf(
            IdiomItem(
                idiom = "To bite the bullet",
                options = listOf("تجرّع المرارة / الإقدام على أمر شاق لا مفر منه", "عضّ الرصاصة بأسنانه", "إطلاق النار عشوائياً"),
                culturalEquivalent = "تجرّع المرارة / الإقدام على أمر شاق لا مفر منه",
                context = "\"The government had to bite the bullet and cut energy subsidies.\"",
                explanation = "تعبير يعود لعلاج الجرحى بالحروب قديماً؛ ويعني قبول موقف صعب ومؤلم بشجاعة لأن لا مفر منه."
            ),
            IdiomItem(
                idiom = "A level playing field",
                options = listOf("تكافؤ الفرص والعدالة التنافسية", "ملعب كرة قدم مستوٍ ومعشب", "ميدان تدريب عسكري"),
                culturalEquivalent = "تكافؤ الفرص والعدالة التنافسية",
                context = "\"New antitrust regulations ensure a level playing field for all tech startups.\"",
                explanation = "في الاقتصاد والتجارة، يعني توفير شروط تنافسية عادلة ومتكافئة لكافة الشركات دون تمييز."
            ),
            IdiomItem(
                idiom = "To give someone the cold shoulder",
                options = listOf("إبداء الجفاء / التجاهل المقصود والصدّ المتعمد", "إعطاء لحم وكتف بارد", "تقديم المساعدة المترددة"),
                culturalEquivalent = "إبداء الجفاء / التجاهل المقصود والصدّ المتعمد",
                context = "\"The dissenting delegate received a cold shoulder from his counterparts.\"",
                explanation = "يعني التعامل بجفاء وازدراء مقصود ورفض التواصل الدبلوماسي مع الشخص."
            ),
            IdiomItem(
                idiom = "Back to the drawing board",
                options = listOf("العودة إلى نقطة الصفر / إعادة التخطيط من البداية", "العودة إلى لوح الرسم الخشبي", "الاستسلام والتراجع النهائي"),
                culturalEquivalent = "العودة إلى نقطة الصفر / إعادة التخطيط من البداية",
                context = "\"After the preliminary talks collapsed, the mediators went back to the drawing board.\"",
                explanation = "يعني فشل الخطة الأصلية والاضطرار للبدء من جديد بوضع خطة عمل بديلة."
            ),
            IdiomItem(
                idiom = "To read between the lines",
                options = listOf("استشفاف المعنى الضمني / فهم ما وراء الكلمات", "قراءة الفراغ الأبيض بين السطور", "التدقيق اللغوي السريع"),
                culturalEquivalent = "استشفاف المعنى الضمني / فهم ما وراء الكلمات",
                context = "\"Diplomats must know how to read between the lines in official communiqués.\"",
                explanation = "مهارة فهم المعاني والمقاصد غير المصرح بها صراحة في الخطابات الدبلوماسية."
            ),
            IdiomItem(
                idiom = "Burn the midnight oil",
                options = listOf("السهر والمثابرة بالعمل والمذاكرة حتى وقت متأخر", "إحراق زيت الإضاءة ليلاً", "إهدار الطاقة والموارد"),
                culturalEquivalent = "السهر والمثابرة بالعمل والمذاكرة حتى وقت متأخر",
                context = "\"The translation team had to burn the midnight oil to submit the dossier.\"",
                explanation = "كناية عن العمل الشاق والمذاكرة الدؤوبة ليلاً لإنهاء مشروع عاجل."
            ),
            IdiomItem(
                idiom = "Hit the nail on the head",
                options = listOf("أصاب كبد الحقيقة / وصف الأمر بدقة متناهية", "ضرب المسمار بالمطرقة على رأسه", "تسبب في إصابة عضلية"),
                culturalEquivalent = "أصاب كبد الحقيقة / وصف الأمر بدقة متناهية",
                context = "\"The economic analyst hit the nail on the head regarding stagflation.\"",
                explanation = "يعني التوصل للتشخيص الدقيق جداً أو قول الصواب بعينه دون مواربة."
            ),
            IdiomItem(
                idiom = "Break a leg",
                options = listOf("تمنيات بالتوفيق والنجاح الباهر (في المسرح والخطابة)", "كسر الساق في حادث أليم", "التوقف عن الحركة فوراً"),
                culturalEquivalent = "تمنيات بالتوفيق والنجاح الباهر (في المسرح والخطابة)",
                context = "\"Before she entered the simultaneous translation booth, her mentor said: Break a leg!\"",
                explanation = "تقليد مسرحي غربي شهير للتمني بالتوفيق والحظ الحسن تجنباً للحسد."
            ),
            IdiomItem(
                idiom = "Under the weather",
                options = listOf("موعوك صحياً / يشعر بوعكة أو إعياء خفيف", "تحت تأثير تقلبات الطقس والمطر", "يعيش في العراء"),
                culturalEquivalent = "موعوك صحياً / يشعر بوعكة أو إعياء خفيف",
                context = "\"The lead interpreter was feeling under the weather and requested a substitute.\"",
                explanation = "يعني الشعور بالمرض الخفيف أو عدم الارتياح الجسدي دون أن يكون خطيراً."
            ),
            IdiomItem(
                idiom = "Spill the beans",
                options = listOf("إفشاء السر وكشف المستور عفوياً", "سكب حبات الفاصولياء على الأرض", "طهي وجبة طعام سريعة"),
                culturalEquivalent = "إفشاء السر وكشف المستور عفوياً",
                context = "\"A disgruntled staff member spilled the beans to the press about the merger.\"",
                explanation = "إفشاء معلومات سرية أو تفاصيل لم يكن ينبغي الإفصاح عنها علناً."
            ),
            IdiomItem(
                idiom = "Barking up the wrong tree",
                options = listOf("البحث في الاتجاه الخاطئ / اتهام الشخص الخطأ", "النباح عند شجرة خاطئة في الغابة", "تسلق شجرة خطرة"),
                culturalEquivalent = "البحث في الاتجاه الخاطئ / اتهام الشخص الخطأ",
                context = "\"If the investigators suspect him of embezzlement, they are barking up the wrong tree.\"",
                explanation = "تعبير يعود للصيد بالكلاب؛ ويعني توجيه الشبهة أو الجهود في مسار لا طائل منه."
            ),
            IdiomItem(
                idiom = "A drop in the ocean",
                options = listOf("قطرة في بحر / مبلغ ضئيل لا يكاد يُذكر", "سقوط قطرة ماء في المحيط", "فقدان شيء ثمين في الأعماق"),
                culturalEquivalent = "قطرة في بحر / مبلغ ضئيل لا يكاد يُذكر",
                context = "\"The emergency food grant is just a drop in the ocean compared to the famine needs.\"",
                explanation = "يدل على ضآلة الشيء مقارنة بالحجم الهائل للاحتياج الكلي المطلوب."
            ),
            IdiomItem(
                idiom = "Cut corners",
                options = listOf("التهاون في المعايير توفيراً للوقت أو التكلفة", "قطع زوايا الورقة الهندسية", "سلوك طريق مختصر في السفر"),
                culturalEquivalent = "التهاون في المعايير توفيراً للوقت أو التكلفة",
                context = "\"The pharmaceutical manufacturer refused to cut corners on safety trials.\"",
                explanation = "يعني التضحية بالجودة أو السلامة من أجل التوفير المالي أو كسب الوقت."
            ),
            IdiomItem(
                idiom = "Cost an arm and a leg",
                options = listOf("باهظ الثمن جداً / يكلف مالاً طائلاً", "يكلف التضحية بالذراع والساق", "يتطلب جهداً بدنياً خارقاً"),
                culturalEquivalent = "باهظ الثمن جداً / يكلف مالاً طائلاً",
                context = "\"Specialized simultaneous interpretation equipment can cost an arm and a leg.\"",
                explanation = "كناية عن غلاء السعر وارتفاع التكلفة المالية بصورة مفرطة."
            ),
            IdiomItem(
                idiom = "Let the cat out of the bag",
                options = listOf("كشف السر بالخطأ ودون قصد مسبق", "إخراج القطة من الكيس", "تحرير الحيوانات الأليفة"),
                culturalEquivalent = "كشف السر بالخطأ ودون قصد مسبق",
                context = "\"He accidentally let the cat out of the bag regarding the upcoming ministerial reshuffle.\"",
                explanation = "إظهار أو التصريح بأمر كان ينبغي أن يظل طي الكتمان."
            ),
            IdiomItem(
                idiom = "Cry over spilled milk",
                options = listOf("التحسر والندم على ما فات ولا يمكن تداركه", "البكاء على الحليب المسكوب", "إفساد وجبة طعام عائلية"),
                culturalEquivalent = "التحسر والندم على ما فات ولا يمكن تداركه",
                context = "\"The deal fell through, but there is no point in crying over spilled milk.\"",
                explanation = "الحكمة المقابلة: لا فائدة من البكاء على ما فات أو الندم على ما لا يُسترد."
            )
        )
    }

    var currentIdx by remember { mutableIntStateOf(0) }
    var selectedAns by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val item = idioms[currentIdx]
    val shuffledOptions = remember(currentIdx) { item.options.shuffled() }

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
                    text = if (isArabic) "💡 كاشف التعبيرات الاصطلاحية والأمثال" else "💡 Idioms & Metaphors Decipherer",
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
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "التعبير: \"${item.idiom}\"",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
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
                text = if (isArabic) "أي الخيارات يمثل المقابل الثقافي البلاغي الدقيق ويتجنب الترجمة الحرفية؟" else "Pick the authentic cultural equivalent without literal distortion:",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Dynamic Shuffled Options
            shuffledOptions.forEach { opt ->
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

// -------------------------------------------------------------------------------------------------
// GAME 6: INTERNATIONAL ACRONYMS CRACKER
// -------------------------------------------------------------------------------------------------
@Composable
fun AcronymsCrackerGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class AcronymChallenge(
        val acronym: String,
        val fullEnglish: String,
        val category: String,
        val correctArabic: String,
        val options: List<String>,
        val explanation: String
    )

    val challenges = remember {
        listOf(
            AcronymChallenge(
                acronym = "UNHCR",
                fullEnglish = "United Nations High Commissioner for Refugees",
                category = "دبلوماسية وإنسانية",
                correctArabic = "المفوضية السامية للأمم المتحدة لشؤون اللاجئين",
                options = listOf(
                    "المفوضية السامية للأمم المتحدة لشؤون اللاجئين",
                    "مجلس حقوق الإنسان التابع للأمم المتحدة",
                    "منظمة الأمم المتحدة للتنمية الإنسانية"
                ),
                explanation = "تأسست المفوضية عام 1950 لحماية اللاجئين والنازحين قسراً ومقرها جنيف."
            ),
            AcronymChallenge(
                acronym = "ICJ",
                fullEnglish = "International Court of Justice",
                category = "قانون دولي عام",
                correctArabic = "محكمة العدل الدولية",
                options = listOf(
                    "المحكمة الجنائية الدولية",
                    "محكمة العدل الدولية",
                    "محكمة التحكيم الدائمة"
                ),
                explanation = "الجهاز القضائي الرئيسي للأمم المتحدة ومقرها لاهاي (قصر السلام)، وتفصل في النزاعات بين الدول."
            ),
            AcronymChallenge(
                acronym = "IAEA",
                fullEnglish = "International Atomic Energy Agency",
                category = "طاقة وأمن دولي",
                correctArabic = "الوكالة الدولية للطاقة الذرية",
                options = listOf(
                    "الوكالة الدولية للطاقة المتجددة",
                    "الهيئة الدولية لحظر الأسلحة الكيميائية",
                    "الوكالة الدولية للطاقة الذرية"
                ),
                explanation = "مركز عالمي للتعاون في المجال النووي والاستخدام السلمي للطاقة ومقرها فيينا."
            ),
            AcronymChallenge(
                acronym = "WIPO",
                fullEnglish = "World Intellectual Property Organization",
                category = "ملكية فكرية وبراءات",
                correctArabic = "المنظمة العالمية للملكية الفكرية (الويبو)",
                options = listOf(
                    "المنظمة العالمية للملكية الفكرية (الويبو)",
                    "المنظمة الدولية للمواصفات والمقاييس",
                    "الاتحاد الدولي للملكية الصناعية"
                ),
                explanation = "وكالة الأمم المتحدة المتخصصة في حماية براءات الاختراع والعلامات التجارية وحقوق المؤلف."
            ),
            AcronymChallenge(
                acronym = "OPEC",
                fullEnglish = "Organization of the Petroleum Exporting Countries",
                category = "طاقة واقتصاد عالمي",
                correctArabic = "منظمة الدول المصدرة للنفط (أوبك)",
                options = listOf(
                    "منظمة الأقطار العربية المصدرة للبترول",
                    "منظمة الدول المصدرة للنفط (أوبك)",
                    "الوكالة الدولية للطاقة"
                ),
                explanation = "منظمة حكومية دولية تضم الدول الرئيسية المصدرة للنفط لتنسيق السياسات النفطية ومقرها فيينا."
            ),
            AcronymChallenge(
                acronym = "WHO",
                fullEnglish = "World Health Organization",
                category = "صحة عامة ورعاية",
                correctArabic = "منظمة الصحة العالمية",
                options = listOf(
                    "منظمة الصحة العالمية",
                    "الاتحاد العالمي للمستشفيات",
                    "صندوق الأمم المتحدة للسكان"
                ),
                explanation = "الوكالة التابعة للأمم المتحدة والمسؤولة عن الصحة العامة الدولية ومقرها جنيف."
            ),
            AcronymChallenge(
                acronym = "WTO",
                fullEnglish = "World Trade Organization",
                category = "تجارة واقتصاد دولي",
                correctArabic = "منظمة التجارة العالمية",
                options = listOf(
                    "منظمة التجارة العالمية",
                    "منظمة السياحة العالمية",
                    "غرفة التجارة الدولية"
                ),
                explanation = "المنظمة الدولية الوحيدة التي تختص بالقوانين التي تحكم التجارة بين الأمم ومقرها جنيف."
            ),
            AcronymChallenge(
                acronym = "IMF",
                fullEnglish = "International Monetary Fund",
                category = "مالية ونقد دولي",
                correctArabic = "صندوق النقد الدولي",
                options = listOf(
                    "صندوق النقد الدولي",
                    "البنك الدولي للإنشاء والتعمير",
                    "بنك التسويات الدولية"
                ),
                explanation = "مؤسسة مالية دولية تعمل على تعزيز الاستقرار المالي والتجارة الدولية ومقرها واشنطن."
            ),
            AcronymChallenge(
                acronym = "NATO",
                fullEnglish = "North Atlantic Treaty Organization",
                category = "أمن ودفاع مشترك",
                correctArabic = "حلف شمال الأطلسي (الناتو)",
                options = listOf(
                    "حلف شمال الأطلسي (الناتو)",
                    "منظمة الأمن والتعاون في أوروبا",
                    "مجلس حلف جنوب شرق آسيا"
                ),
                explanation = "تحالف عسكري حكومي دولي تأسس عام 1949 للدفاع المشترك ومقره بروكسل."
            ),
            AcronymChallenge(
                acronym = "UNESCO",
                fullEnglish = "United Nations Educational, Scientific and Cultural Organization",
                category = "تربية وثقافة وتراث",
                correctArabic = "منظمة الأمم المتحدة للتربية والعلم والثقافة (اليونسكو)",
                options = listOf(
                    "منظمة الأمم المتحدة للتربية والعلم والثقافة (اليونسكو)",
                    "منظمة العمل الدولية",
                    "صندوق الأمم المتحدة للطفولة"
                ),
                explanation = "وكالة الأمم المتحدة لحماية التراث العالمي وتشجيع التعليم والعلوم ومقرها باريس."
            ),
            AcronymChallenge(
                acronym = "ILO",
                fullEnglish = "International Labour Organization",
                category = "عمل وتشغيل",
                correctArabic = "منظمة العمل الدولية",
                options = listOf(
                    "منظمة العمل الدولية",
                    "الاتحاد الدولي لنقابات العمال",
                    "المنظمة الدولية للهجرة"
                ),
                explanation = "وكالة أممية ثلاثية التكوين تجمع الحكومات وأرباب العمل والعمال ومقرها جنيف."
            ),
            AcronymChallenge(
                acronym = "FAO",
                fullEnglish = "Food and Agriculture Organization",
                category = "أمن غذائي وزراعة",
                correctArabic = "منظمة الأغذية والزراعة للأمم المتحدة (الفاو)",
                options = listOf(
                    "منظمة الأغذية والزراعة للأمم المتحدة (الفاو)",
                    "برنامج الأغذية العالمي",
                    "الصندوق الدولي للتنمية الزراعية"
                ),
                explanation = "وكالة تقود الجهود الدولية للقضاء على الجوع وتحقيق الأمن الغذائي ومقرها روما."
            ),
            AcronymChallenge(
                acronym = "ICC",
                fullEnglish = "International Criminal Court",
                category = "قضاء جنائي دولي",
                correctArabic = "المحكمة الجنائية الدولية",
                options = listOf(
                    "المحكمة الجنائية الدولية",
                    "محكمة العدل الدولية",
                    "غرفة التجارة الدولية"
                ),
                explanation = "محكمة مستقلة تختص بمحاكمة مرتكبي أشد الجرائم خطورة كجرائم الإبادة والعدوان ومقرها لاهاي."
            ),
            AcronymChallenge(
                acronym = "INTERPOL",
                fullEnglish = "International Criminal Police Organization",
                category = "أمن وإنفاذ القانون",
                correctArabic = "المنظمة الدولية للشرطة الجنائية (الإنتربول)",
                options = listOf(
                    "المنظمة الدولية للشرطة الجنائية (الإنتربول)",
                    "وكالة مكافحة المخدرات الدولية",
                    "اليوروبول الأوروبي"
                ),
                explanation = "منظمة تيسر التعاون الشرطي الدولي في مكافحة الجريمة العابرة للحدود ومقرها ليون بفرنسا."
            ),
            AcronymChallenge(
                acronym = "ASEAN",
                fullEnglish = "Association of Southeast Asian Nations",
                category = "تكتلات إقليمية",
                correctArabic = "رابطة دول جنوب شرق آسيا (آسيان)",
                options = listOf(
                    "رابطة دول جنوب شرق آسيا (آسيان)",
                    "منتدى التعاون الاقتصادي لدول آسيا والمحيط الهادئ",
                    "اتحاد دول جنوب آسيا للتعاون الإقليمي"
                ),
                explanation = "منظمة اقتصادية وسياسية تضم 10 دول في جنوب شرق آسيا ومقر أمانتها جاكرتا."
            ),
            AcronymChallenge(
                acronym = "OECD",
                fullEnglish = "Organisation for Economic Co-operation and Development",
                category = "تنمية واقتصاد",
                correctArabic = "منظمة التعاون الاقتصادي والتنمية",
                options = listOf(
                    "منظمة التعاون الاقتصادي والتنمية",
                    "صندوق التنمية الاقتصادية العالمي",
                    "منظمة التجارة الحرة الدولية"
                ),
                explanation = "منظمة دولية تضم الدول المتقدمة لمناقشة وتطوير السياسات الاقتصادية والاجتماعية ومقرها باريس."
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val current = challenges[currentIndex]
    val shuffledOptions = remember(currentIndex) { current.options.shuffled() }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_acronym_cracker"),
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
                    text = "🌐 " + if (isArabic) "مفكك الاختصارات الدولية (${currentIndex + 1}/${challenges.size})" else "Acronyms Cracker (${currentIndex + 1}/${challenges.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = RedPrimary
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = current.category,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = RedPrimary.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = current.acronym,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary,
                        letterSpacing = 2.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = current.fullEnglish,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = if (isArabic) "ما المقابل العربي المعتمد رسمياً في الأمم المتحدة؟" else "What is the official Arabic translation?",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Dynamic Shuffled Options
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == current.correctArabic
                val isChosen = selectedOption == opt
                val bg = when {
                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                    isChosen -> Color.Red.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !isAnswered) {
                            selectedOption = opt
                            isAnswered = true
                            if (isCorrect) onAddScore(30)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        fontSize = 12.sp,
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
                        Text("💡 الفائدة الدبلوماسية والتاريخية:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(current.explanation, fontSize = 11.sp, lineHeight = 15.sp)
                    }
                }

                Button(
                    onClick = {
                        isAnswered = false
                        selectedOption = null
                        currentIndex = (currentIndex + 1) % challenges.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "الاختصار التالي ➔" else "Next Acronym ➔")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 7: SIGHT TRANSLATION SPRINT
// -------------------------------------------------------------------------------------------------
@Composable
fun SightTranslationSprintGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    data class SprintCard(
        val domain: String,
        val sourceText: String,
        val officialTranslation: String,
        val keyKeywords: List<String>
    )

    val cards = remember {
        listOf(
            SprintCard(
                domain = "مجلس الأمن والأمم المتحدة",
                sourceText = "\"The Security Council, acting under Chapter VII of the Charter, decides to extend the mandate of the peacekeeping mission until 31 December.\"",
                officialTranslation = "\"إن مجلس الأمن، إذ يتصرف بموجب الفصل السابع من الميثاق، يقرر تمديد ولاية بعثة حفظ السلام حتى 31 كانون الأول/ديسمبر.\"",
                keyKeywords = listOf("الفصل السابع", "تمديد الولاية", "بعثة حفظ السلام")
            ),
            SprintCard(
                domain = "التحكيم الدولي والاستثمار",
                sourceText = "\"Any dispute arising out of or in connection with this contract shall be finally settled under the Rules of Arbitration of the ICC.\"",
                officialTranslation = "\"تتم تسوية أي نزاع ينشأ عن هذا العقد أو يرتبط به بصفة نهائية وفقاً لقواعد التحكيم الصادرة عن غرفة التجارة الدولية.\"",
                keyKeywords = listOf("تسوية نهائية", "قواعد التحكيم", "غرفة التجارة الدولية")
            ),
            SprintCard(
                domain = "طب ومستحضرات صيدلانية",
                sourceText = "\"Administer intramuscularly at a dose of 5 mg/kg every 12 hours. Contraindicated in patients with severe hepatic impairment.\"",
                officialTranslation = "\"يُعطى عن طريق الحقن العضلي بجرعة 5 ملغ/كغ كل 12 ساعة. يُحظر استعماله للمرضى الذين يعانون من قصور كبدي حاد.\"",
                keyKeywords = listOf("حقن عضلي", "يُحظر استعماله", "قصور كبدي حاد")
            ),
            SprintCard(
                domain = "هندسة الطاقات المتجددة",
                sourceText = "\"The photovoltaic plant integrates dual-axis solar trackers to optimize irradiance absorption and minimize grid curtailment losses.\"",
                officialTranslation = "\"تدمج محطة الطاقة الكهروضوئية أجهزة تتبع شمسية ثنائية المحور لتحسين امتصاص الإشعاع والحد من خسائر التقليص على الشبكة.\"",
                keyKeywords = listOf("طاقة كهروضوئية", "تتبع ثنائي المحور", "تقليص الشبكة")
            )
        )
    }

    var cardIdx by remember { mutableIntStateOf(0) }
    var secondsLeft by remember { mutableIntStateOf(15) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var showModelAnswer by remember { mutableStateOf(false) }

    val currentCard = cards[cardIdx]

    LaunchedEffect(cardIdx, isTimerRunning) {
        if (isTimerRunning && secondsLeft > 0) {
            while (secondsLeft > 0 && isTimerRunning) {
                delay(1000)
                secondsLeft--
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_sight_translation"),
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
                    text = "👁️ " + if (isArabic) "الترجمة المنظورة (${cardIdx + 1}/${cards.size})" else "Sight Translation Sprint",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = RedPrimary
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (secondsLeft <= 5) Color.Red.copy(alpha = 0.2f) else SuccessGreen.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "⏱️ $secondsLeft ثانية",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = if (secondsLeft <= 5) Color.Red else SuccessGreen,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "مقتطف النص المصدر [${currentCard.domain}]:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = currentCard.sourceText,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(
                text = if (isArabic) "💡 ترجم فورياً بصوت مرتفع دون توقف ثم قارن مع الصياغة المحلفة:" else "Sight translate aloud now, then reveal model answer:",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!showModelAnswer) {
                Button(
                    onClick = {
                        showModelAnswer = true
                        isTimerRunning = false
                        val bonus = if (secondsLeft > 5) 40 else 25
                        onAddScore(bonus)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(if (isArabic) "كشف الصياغة النموذجية المعتمدة (+نقاط)" else "Reveal Model Translation")
                }
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = SuccessGreen.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "✓ الصياغة النموذجية المحلفة (ISO 17100):",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = currentCard.officialTranslation,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 17.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "المصطلحات المفتاحية الواجب مراعاتها: " + currentCard.keyKeywords.joinToString(" • "),
                            fontSize = 10.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = {
                        showModelAnswer = false
                        secondsLeft = 15
                        isTimerRunning = true
                        cardIdx = (cardIdx + 1) % cards.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedDark)
                ) {
                    Text(if (isArabic) "النص التالي ➔" else "Next Text ➔")
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// GAME 8: DOMAIN TERMINOLOGY BATTLE (معركة المصطلحات المتخصصة)
// -------------------------------------------------------------------------------------------------
@Composable
fun TerminologyBattleGame(
    isArabic: Boolean,
    onAddScore: (Int) -> Unit
) {
    var selectedDomain by remember { mutableStateOf("قانون وعقود ⚖️") }

    data class BattleItem(
        val term: String,
        val prompt: String,
        val correct: String,
        val options: List<String>,
        val tip: String
    )

    val battleDatabase = remember {
        mapOf(
            "قانون وعقود ⚖️" to listOf(
                BattleItem("Estoppel", "ما المقابل القانوني الدقيق للمصطلح اللاتيني-الإنكليزي في القانون الإنجلو-أمريكي؟", "الإغلاق الحكمي / المنع من الادعاء", listOf("الإغلاق الحكمي / المنع من الادعاء", "التوقف عن الدفع", "الإعفاء من الالتزام"), "مبدأ يمنع الشخص من اتخاذ موقف يناقض ما أقر به سابقاً وألحق ضرراً بالغير."),
                BattleItem("Without prejudice", "توضع في المراسلات التسووية بين المحامين وتعني:", "دون إخلال بالحقوق والمراكز القانونية", listOf("دون تحيز أو تعصب شخصي", "دون إخلال بالحقوق والمراكز القانونية", "بشكل نهائي وقاطع لا رجعة فيه"), "تضمن عدم استخدام المراسلات كإقرار قضائي في حال فشل المفاوضات الودية."),
                BattleItem("Res judicata", "قاعدة لاتينية راسخة في المرافعات القضائية:", "حجية الشيء المقضي به / سبق الفصل في الدعوى", listOf("إعادة فتح باب المرافعة", "بطلان الحكم لعلة إجرائية", "حجية الشيء المقضي به / سبق الفصل في الدعوى"), "تعني أن النزاع قد فُصل فيه بحكم قضائي نهائي بات ولا يجوز إعادة طرحه."),
                BattleItem("Bona fide", "شرط جوهري في إبرام العقود والمعاملات الدولية:", "حسن النية والسلامة من التدليس", listOf("سداد الثمن بالكامل", "حسن النية والسلامة من التدليس", "الضمان التجاري المصرفي"), "تعبير لاتيني يقابله Mala fide (سوء النية)، وهو ركن أساسي في صحة التصرفات."),
                BattleItem("Liquidated damages", "بند مالي مدرج في عقود الإنشاءات والتوريد:", "التعويض الاتفاقي المشروط / الشرط الجزائي", listOf("التعويض القضائي اللاحق", "التعويض الاتفاقي المشروط / الشرط الجزائي", "غرامة التصفية الضريبية"), "مبلغ يُتفق عليه مسبقاً في العقد كتعويض في حال الإخلال بالتنفيذ دون الحاجة لإثبات الضرر."),
                BattleItem("Force Majeure", "دفع قانوني تلجأ له الشركات عند الكوارث والحروب:", "القوة القاهرة المعفية من المسؤولية", listOf("الخطأ الجسيم غير المغتفر", "الظروف الطارئة المخففة", "القوة القاهرة المعفية من المسؤولية"), "حدث استثنائي لا يمكن توقعه أو دفعه يجعل تنفيذ الالتزام التعاقدي مستحيلاً.")
            ),
            "طب وصحة 🩺" to listOf(
                BattleItem("Idiopathic", "تُطلق على الحالات والأمراض وتعني:", "مجهول السبب / مجهول المنشأ", listOf("شديد العدوى والانتقال", "مجهول السبب / مجهول المنشأ", "مرض وراثي طافر"), "من اليونانية idios (خاص/مستقل) وpathos (مرض) أي مرض ينشأ ذاتياً دون سبب خارجي معروف."),
                BattleItem("Prognosis", "الفرق الجوهري بينها وبين Diagnosis:", "التكهن بالمآل وسير المرض المتوقع", listOf("التشخيص المخبري والسريري الدقيق", "التكهن بالمآل وسير المرض المتوقع", "الفحص الإشعاعي للأعضاء"), "Diagnosis هو تشخيص المرض الحالي، بينما Prognosis هو التنبؤ بمستقبل حالة المريض واستجابته."),
                BattleItem("Contraindication", "تحذير رئيسي في النشرات الدوائية المرافقة:", "موانع الاستعمال الدوائي", listOf("الجرعة الزائدة الموصى بها", "الآثار الجانبية الشائعة", "موانع الاستعمال الدوائي"), "الحالات المرضية أو الفسيولوجية التي يحظر فيها إعطاء الدواء لما قد يشكله من خطر جسيم."),
                BattleItem("Myocardial infarction", "حالة طبية حرجة تتطلب تدخلاً إسعافياً فورياً:", "احتشاء عضلة القلب (الجلطة القلبية)", listOf("احتشاء عضلة القلب (الجلطة القلبية)", "التهاب الغشاء التاموري المزمن", "اضطراب نبضات الأذين"), "موت جزء من عضلة القلب نتيجة انقطاع التروية الدموية الشريانية التاجية."),
                BattleItem("Intravenous vs Intramuscular", "طرق إعطاء الأدوية والحقن:", "حقن وريدي مقابل حقن عضلي", listOf("حقن وريدي مقابل حقن عضلي", "حقن تحت الجلد مقابل حقن شرياني", "حقن سطحي مقابل حقن عميق"), "IV هو الحقن في الوريد مباشرة وله تأثير فوري، بينما IM هو الحقن في العضلة لامتصاص أبطأ."),
                BattleItem("Pathogenesis", "مفهوم محوري في علم الأمراض:", "الإمراضية / آلية نشوء وتطور المرض", listOf("خريطة الجينات الوراثية", "الإمراضية / آلية نشوء وتطور المرض", "طرق العلاج الطبيعي"), "المسار البيولوجي والخلوي الذي يؤدي من مسبب المرض إلى ظهور الأعراض السريرية.")
            ),
            "مالية واقتصاد 💹" to listOf(
                BattleItem("Liquidity ratio", "مؤشر مالي هام للشركات والمصارف:", "نسبة السيولة النقدية وسرعة الوفاء", listOf("معدل دوران المخزون السلعي", "نسبة السيولة النقدية وسرعة الوفاء", "نسبة الأرباح الرأسمالية السنوية"), "يقيس قدرة المؤسسة على الوفاء بالتزاماتها المالية قصيرة الأجل فور استحقاقها."),
                BattleItem("Bear market", "حالة السوق المالي عندما تنخفض الأسعار بشكل متواصل:", "السوق الهابطة (سوق الدببة)", listOf("السوق الصاعدة (سوق الثيران)", "السوق الهابطة (سوق الدببة)", "السوق الراكدة المستقرة"), "سوق يتسم بتشاؤم المستثمرين وهبوط مؤشرات الأسهم بنسبة 20% فأكثر."),
                BattleItem("Due diligence", "إجراء مصرفي واستثماري قبل عمليات الاندماج:", "الفحص النافي للجهالة والتقصي اللازم", listOf("الفحص النافي للجهالة والتقصي اللازم", "التدقيق المحاسبي الروتيني", "التأمين ضد مخاطر الإفلاس"), "مراجعة شاملة لجميع السجلات المالية والقانونية والتشغيلية للشركة المستهدفة بالاستحواذ."),
                BattleItem("Hedging", "استراتيجية مالية في إدارة المحافظ الاستثمارية:", "التحوط ضد مخاطر تقلب الأسعار", listOf("المضاربة السريعة في البورصة", "التحوط ضد مخاطر تقلب الأسعار", "تجميد الحسابات المصرفية"), "استخدام عقود المشتقات المالية للحد من الخسائر المحتملة عند تقلب أسعار العملات أو السلع."),
                BattleItem("EBITDA", "مقياس مالي شهير لأداء الشركات التشغيلي:", "الأرباح قبل الفوائد والضرائب والإهلاك والاستهلاك", listOf("الأرباح الصافية بعد خصم كافة الرسوم", "الأرباح قبل الفوائد والضرائب والإهلاك والاستهلاك", "إجمالي رأس المال العامل"), "Earnings Before Interest, Taxes, Depreciation, and Amortization يقيس الربحية التشغيلية الصافية."),
                BattleItem("Insolvency", "وضع مالي حرج للمدين أو الشركة:", "الإعسار المالي / عجز السيولة عن سداد الديون", listOf("الإعسار المالي / عجز السيولة عن سداد الديون", "التهرب الضريبي التجاري", "تجميد الأصول الدولية"), "عدم قدرة المدين على سداد ديونه المستحقة، وهو يسبق إعلان الإفلاس القضائي الرسمي.")
            ),
            "طاقة وهندسة ⚡" to listOf(
                BattleItem("Upstream vs Downstream", "في الصناعة البترولية تعني مرحلياً:", "الاستكشاف والإنتاج مقابل التكرير والتوزيع", listOf("التدفق السطحي مقابل التدفق الجوفي", "الاستكشاف والإنتاج مقابل التكرير والتوزيع", "الأنابيب البرية مقابل البحرية"), "Upstream تشمل التنقيب والاستخراج، وDownstream تشمل التكرير والبتروكيماويات والتسويق."),
                BattleItem("Carbon sequestration", "تقنية حيوية لمكافحة الاحتباس الحراري:", "احتجاز وتخزين الكربون في باطن الأرض", listOf("حرق النفايات الهيدروكربونية", "احتجاز وتخزين الكربون في باطن الأرض", "تحلية الانبعاثات بالترشيح"), "احتجاز غاز ثاني أكسيد الكربون من المصادر الصناعية وتخزينه في تكوينات جيولوجية عميقة."),
                BattleItem("Photovoltaic cell", "المكون الأساسي لمحطات الطاقة الشمسية:", "الخلية الكهروضوئية المنتجة للكهرباء", listOf("المرآة الحرارية المجمعة", "الخلية الكهروضوئية المنتجة للكهرباء", "العاكس الكهربائي الذكي"), "أداة شبه موصلة تحول الطاقة الضوئية للشمس مباشرة إلى تيار كهربائي مستمر."),
                BattleItem("Smart grid", "تحديث شبكات نقل وتوزيع الطاقة الكهربائية:", "الشبكة الكهربائية الذكية التفاعلية", listOf("محطة التوليد النووية", "الشبكة الكهربائية الذكية التفاعلية", "خطوط الضغط الفائق التقليدية"), "شبكة تستخدم تكنولوجيا المعلومات الرقمية لرصد تدفق الكهرباء والتجاوب مع تغيرات الطلب."),
                BattleItem("Depletion rate", "معدل استنزاف الحقول الهيدروكربونية:", "معدل نضوب الاحتياطي النفطي أو الغازي", listOf("معدل تدفق سائل الحفر", "معدل نضوب الاحتياطي النفطي أو الغازي", "نسبة شوائب الكبريت"), "النسبة السنوية لما يتم استخراجه من مكمن نفطي مقارنة بإجمالي الاحتياطي المقدر المتبقي."),
                BattleItem("Gas flaring", "ممارسة صناعية في مواقع إنتاج النفط:", "حرق الغاز المصاحب في الشعلات الهوائية", listOf("إعادة حقن الغاز لزيادة الضغط", "حرق الغاز المصاحب في الشعلات الهوائية", "تسييل الغاز الطبيعي"), "حرق الغاز الطبيعي غير المستغل اقتصادياً المرافق لاستخراج النفط، وتخضع لضوابط بيئية صارمة.")
            )
        )
    }

    val currentItems = battleDatabase[selectedDomain] ?: emptyList()
    var currentItemIdx by remember { mutableIntStateOf(0) }
    var selectedAns by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    val item = currentItems.getOrNull(currentItemIdx % currentItems.size) ?: return
    val shuffledOptions = remember(item) { item.options.shuffled() }

    Card(
        modifier = Modifier.fillMaxWidth().testTag("game_terminology_battle"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "⚔️ " + if (isArabic) "معركة المصطلحات المتخصصة" else "Domain Terminology Battle",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = RedPrimary
            )

            // Domain Tabs
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(battleDatabase.keys.toList().size) { idx ->
                    val domainKey = battleDatabase.keys.toList()[idx]
                    val isSel = selectedDomain == domainKey
                    FilterChip(
                        selected = isSel,
                        onClick = {
                            selectedDomain = domainKey
                            currentItemIdx = 0
                            selectedAns = null
                            isAnswered = false
                        },
                        label = { Text(domainKey, fontSize = 10.5.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "المصطلح: ${item.term}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.prompt,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = if (isArabic) "اختر المقابل التخصصي الدقيق المعتمد في الصناعة:" else "Pick the authoritative domain translation:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Dynamic Shuffled Options
            shuffledOptions.forEach { opt ->
                val isCorrect = opt == item.correct
                val isChosen = selectedAns == opt
                val bg = when {
                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
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
                            if (isCorrect) onAddScore(35)
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = bg,
                    border = if (isChosen) BorderStroke(1.5.dp, if (isCorrect) SuccessGreen else Color.Red) else null
                ) {
                    Text(
                        text = opt,
                        fontSize = 12.sp,
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
                        Text("💡 الفائدة الاصطلاحية التخصصية:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(item.tip, fontSize = 11.sp, lineHeight = 15.sp)
                    }
                }

                Button(
                    onClick = {
                        isAnswered = false
                        selectedAns = null
                        currentItemIdx = (currentItemIdx + 1) % currentItems.size
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "التحدي التالي ➔" else "Next Battle ➔")
                }
            }
        }
    }
}
