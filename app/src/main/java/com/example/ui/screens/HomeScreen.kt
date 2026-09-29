package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AppTab
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class TrainingCategoryFilter {
    ALL, SIMULTANEOUS, CONSECUTIVE, SHADOWING, OPI, EXAM, ACADEMY
}

@Composable
fun HomeScreen(
    isArabic: Boolean,
    onNavigate: (AppTab) -> Unit,
    onOpenOrderDialog: (serviceType: String) -> Unit,
    onOpenAuthDialog: () -> Unit
) {
    var showShadowingDialog by remember { mutableStateOf(false) }
    var showOpiDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(TrainingCategoryFilter.ALL) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. GOINTERPREP AGHILAS SPECIAL OFFER HERO BANNER ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .clickable { onNavigate(AppTab.PRICING) }
                .testTag("home_premium_banner"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFFEF08A)
                    ) {
                        Text(
                            text = if (isArabic) "🔥 عرض أغيلاس الحصري • AGHILAS-DZ" else "🔥 Exclusive Aghilas Offer",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF854D0E)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF2563EB)
                    ) {
                        Text(
                            text = "50% OFF",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                Text(
                    text = if (isArabic) "منصة تدريب المترجمين الذكية - GoInterPrep" else "Smart Interpreter Training Platform",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    text = if (isArabic)
                        "تدرّب على الترجمة الفورية، التتابعية، والتظليل الصوتي بأحدث بيئات المحاكاة. كود التخفيض: AGHILAS3M50"
                    else
                        "Practice simultaneous, consecutive, and speech shadowing. Use promo code: AGHILAS3M50",
                    fontSize = 12.5.sp,
                    color = Color(0xFFDBEAFE),
                    lineHeight = 18.sp
                )

                Button(
                    onClick = { onNavigate(AppTab.PRICING) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("banner_upgrade_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.LocalOffer, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) "الاستفادة من عرض 50% وتصفح الباقات ←" else "Claim 50% Offer & View Plans →",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // --- 2. SECTION HEADER (Clean & Minimal) ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = if (isArabic) "أنماط تدريب المترجم الشفهي" else "Interpreter Training Modes",
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (isArabic) "اختر المسار التدريبي المناسب لمستواك المهني والأكاديمي" else "Select the training path tailored to your proficiency level",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )
        }

        // --- 3. CATEGORY FILTER CHIPS (Quick Filter) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedCategory == TrainingCategoryFilter.ALL,
                onClick = { selectedCategory = TrainingCategoryFilter.ALL },
                label = { Text(if (isArabic) "الكل" else "All", fontSize = 11.5.sp, fontWeight = FontWeight.Bold) }
            )
            FilterChip(
                selected = selectedCategory == TrainingCategoryFilter.SIMULTANEOUS,
                onClick = { selectedCategory = TrainingCategoryFilter.SIMULTANEOUS },
                label = { Text(if (isArabic) "فورية" else "Simul", fontSize = 11.5.sp) }
            )
            FilterChip(
                selected = selectedCategory == TrainingCategoryFilter.CONSECUTIVE,
                onClick = { selectedCategory = TrainingCategoryFilter.CONSECUTIVE },
                label = { Text(if (isArabic) "تتابعية" else "Consec", fontSize = 11.5.sp) }
            )
            FilterChip(
                selected = selectedCategory == TrainingCategoryFilter.SHADOWING,
                onClick = { selectedCategory = TrainingCategoryFilter.SHADOWING },
                label = { Text(if (isArabic) "تظليل" else "Shadow", fontSize = 11.5.sp) }
            )
            FilterChip(
                selected = selectedCategory == TrainingCategoryFilter.EXAM,
                onClick = { selectedCategory = TrainingCategoryFilter.EXAM },
                label = { Text(if (isArabic) "تقييم" else "Exam", fontSize = 11.5.sp) }
            )
        }

        // --- 4. UNCLUTTERED TRAINING MODE CARDS ---

        // CARD 1: الترجمة الفورية (Simultaneous Interpretation)
        if (selectedCategory == TrainingCategoryFilter.ALL || selectedCategory == TrainingCategoryFilter.SIMULTANEOUS) {
            CleanTrainingCard(
                icon = Icons.Default.Groups,
                iconBgColor = Color(0xFFFEE2E2),
                iconTint = Color(0xFFDC2626),
                title = if (isArabic) "الترجمة الفورية (Simultaneous)" else "Simultaneous Interpretation",
                description = if (isArabic)
                    "محاكاة مقصورة المؤتمرات الحية مع تدريب على تقليص الفارق الزمني (Décalage)."
                else
                    "Live conference booth simulation focusing on lag management and fast delivery.",
                levelBadge = if (isArabic) "متقدم" else "Advanced",
                levelBadgeBg = Color(0xFFFEE2E2),
                levelBadgeText = Color(0xFF991B1B),
                actionLabel = if (isArabic) "دخول مقصورة الترجمة ←" else "Enter Booth →",
                onClick = { onNavigate(AppTab.ACADEMY) },
                testTag = "card_simultaneous"
            )
        }

        // CARD 2: الترجمة التتابعية (Consecutive Interpretation)
        if (selectedCategory == TrainingCategoryFilter.ALL || selectedCategory == TrainingCategoryFilter.CONSECUTIVE) {
            CleanTrainingCard(
                icon = Icons.Default.Schedule,
                iconBgColor = Color(0xFFFEF9C3),
                iconTint = Color(0xFFB45309),
                title = if (isArabic) "الترجمة التتابعية (Consecutive)" else "Consecutive Interpretation",
                description = if (isArabic)
                    "تدوين الملاحظات بنظام رموز روزان المعتمد والإلقاء السليم خلال فواصل الخطاب."
                else
                    "Note-taking via Rozan symbolics and structured delivery during pauses.",
                levelBadge = if (isArabic) "متوسط" else "Intermediate",
                levelBadgeBg = Color(0xFFFEF9C3),
                levelBadgeText = Color(0xFF854D0E),
                actionLabel = if (isArabic) "بدء التدريب التتابعي ←" else "Start Consecutive →",
                onClick = { onNavigate(AppTab.ACADEMY) },
                testTag = "card_consecutive"
            )
        }

        // CARD 3: محاكاة النطق والتظليل الصوتي (Shadowing)
        if (selectedCategory == TrainingCategoryFilter.ALL || selectedCategory == TrainingCategoryFilter.SHADOWING) {
            CleanTrainingCard(
                icon = Icons.Default.SyncAlt,
                iconBgColor = Color(0xFFDCFCE7),
                iconTint = Color(0xFF16A34A),
                title = if (isArabic) "محاكاة النطق والتظليل (Shadowing)" else "Speech Shadowing",
                description = if (isArabic)
                    "ترديد كلام المتحدث الفصيح لصقل مخارج الحروف وبناء الطلاقة والذاكرة السمعية."
                else
                    "Repeat native speech in real-time to build vocal fluency and auditory memory.",
                levelBadge = if (isArabic) "مبتدئ إلى متقدم" else "All Levels",
                levelBadgeBg = Color(0xFFDCFCE7),
                levelBadgeText = Color(0xFF166534),
                actionLabel = if (isArabic) "تجربة التظليل الصوتي ←" else "Try Shadowing →",
                onClick = { showShadowingDialog = true },
                testTag = "card_shadowing"
            )
        }

        // CARD 4: OPI - الترجمة الفورية عبر الهاتف
        if (selectedCategory == TrainingCategoryFilter.ALL) {
            CleanTrainingCard(
                icon = Icons.Default.PhoneInTalk,
                iconBgColor = Color(0xFFFEF3C7),
                iconTint = Color(0xFFD97706),
                title = if (isArabic) "الترجمة الهاتفية (OPI)" else "Over-the-Phone Interpreting",
                description = if (isArabic)
                    "سيناريوهات مهنية حية للمكالمات الطبية، الدبلوماسية، والطارئة بين لغات متعددة."
                else
                    "Simulated live medical, consular and emergency phone interpretation drills.",
                levelBadge = if (isArabic) "محاكاة مهنية" else "Professional",
                levelBadgeBg = Color(0xFFFEF3C7),
                levelBadgeText = Color(0xFFB45309),
                actionLabel = if (isArabic) "بدء السيناريو الهاتفي ←" else "Start Phone Drill →",
                onClick = { showOpiDialog = true },
                testTag = "card_opi"
            )
        }

        // CARD 5: امتحان تشخيص الكفاءة وتحديد المستوى (CEFR)
        if (selectedCategory == TrainingCategoryFilter.ALL || selectedCategory == TrainingCategoryFilter.EXAM) {
            CleanTrainingCard(
                icon = Icons.Default.School,
                iconBgColor = Color(0xFFEDE9FE),
                iconTint = Color(0xFF7C3AED),
                title = if (isArabic) "اختبار الكفاءة المعياري (CEFR)" else "CEFR Diagnostic Exam",
                description = if (isArabic)
                    "تقييم أكاديمي دقيق لكفاءتك اللغوية والترجمية مع تقرير نتائج معتمد يرسل للمشرف."
                else
                    "Standardized proficiency test with instant evaluation scorecard dispatched to admin.",
                levelBadge = if (isArabic) "تقييم أكاديمي" else "CEFR Test",
                levelBadgeBg = Color(0xFFEDE9FE),
                levelBadgeText = Color(0xFF6D28D9),
                actionLabel = if (isArabic) "بدء الاختبار التشخيصي ←" else "Start Assessment →",
                onClick = { onNavigate(AppTab.ACADEMY) },
                testTag = "card_exam"
            )
        }

        // CARD 6: أكاديمية اللغات والماستركلاس
        if (selectedCategory == TrainingCategoryFilter.ALL) {
            CleanTrainingCard(
                icon = Icons.Default.VideoLibrary,
                iconBgColor = Color(0xFFE0E7FF),
                iconTint = Color(0xFF4338CA),
                title = if (isArabic) "أكاديمية اللغات والماستركلاس" else "Language Masterclasses",
                description = if (isArabic)
                    "محاضرات مرئية منتقاة لنخبة الأساتذة والمترجمين المحلفين مع ذكر أصحاب الفيديوهات."
                else
                    "Curated video masterclasses with verified professors and channel authors.",
                levelBadge = if (isArabic) "مكتبة معتمدة" else "Curated",
                levelBadgeBg = Color(0xFFE0E7FF),
                levelBadgeText = Color(0xFF3730A3),
                actionLabel = if (isArabic) "تصفح المحاضرات واللغات ←" else "Browse Library →",
                onClick = { onNavigate(AppTab.ACADEMY) },
                testTag = "card_videos"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // --- INTERACTIVE DIALOGS FOR SHADOWING & OPI ---
    if (showShadowingDialog) {
        ShadowingPracticeDialog(
            isArabic = isArabic,
            onDismiss = { showShadowingDialog = false }
        )
    }

    if (showOpiDialog) {
        OpiScenariosDialog(
            isArabic = isArabic,
            onDismiss = { showOpiDialog = false }
        )
    }
}

/**
 * Modern, uncluttered card component styled cleanly like GoInterPrep
 */
@Composable
private fun CleanTrainingCard(
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    title: String,
    description: String,
    levelBadge: String,
    levelBadgeBg: Color,
    levelBadgeText: Color,
    actionLabel: String,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Row: Icon on Left/Right & Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = levelBadgeBg
                ) {
                    Text(
                        text = levelBadge,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = levelBadgeText
                    )
                }
            }

            // Title
            Text(
                text = title,
                fontSize = 16.5.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )

            // 1-sentence Clear Description (No non-essential noise)
            Text(
                text = description,
                fontSize = 12.5.sp,
                color = Color(0xFF475569),
                lineHeight = 18.sp
            )

            // Direct Action CTA Button
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9))
            ) {
                Text(
                    text = actionLabel,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }
        }
    }
}

/**
 * Interactive Dialog for Speech Shadowing (محاكاة النطق والتظليل اللغوي)
 */
@Composable
private fun ShadowingPracticeDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    var isPlayingAudio by remember { mutableStateOf(false) }
    var isRecordingUser by remember { mutableStateOf(false) }
    var selectedExerciseIndex by remember { mutableIntStateOf(0) }

    val exercises = listOf(
        Pair(
            "International Trade & Diplomacy (EN)",
            "Distinguished delegates, sustainable economic partnerships require transparent legal frameworks and reliable cross-border cooperation."
        ),
        Pair(
            "Conférence de l'Énergie Propre (FR)",
            "Mesdames et messieurs, la transition énergétique vers l'hydrogène vert constitue un pilier stratégique pour l'avenir économique mondial."
        ),
        Pair(
            "الخطاب الافتتاحي لمنتدى الترجمة المعتمدة (AR)",
            "يرتكز نجاح المترجم الشفهي في كابينات المؤتمرات على سرعة البديهة والتحكم الصوتي الدقيق ونقل المعنى دون أي تلكؤ."
        )
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFDCFCE7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SyncAlt, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(20.dp))
                        }
                        Text(
                            text = if (isArabic) "محاكاة النطق والتظليل (Shadowing)" else "Speech Shadowing",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = if (isArabic)
                        "استمع للمتحدث الفصيح وردّد فورياً لصقل مخارج الحروف وبناء الطلاقة وإيقاع الإلقاء في كابينة الترجمة."
                    else
                        "Listen to native speech and shadow concurrently to develop fluency, rhythm and booth delivery.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    exercises.forEachIndexed { idx, item ->
                        FilterChip(
                            selected = selectedExerciseIndex == idx,
                            onClick = { selectedExerciseIndex = idx },
                            label = { Text(if (idx == 0) "English" else if (idx == 1) "Français" else "العربية", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF8FAFC),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = exercises[selectedExerciseIndex].first,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2563EB)
                        )
                        Text(
                            text = exercises[selectedExerciseIndex].second,
                            fontSize = 13.5.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0F172A)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { isPlayingAudio = !isPlayingAudio },
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D4ED8))
                    ) {
                        Icon(if (isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isPlayingAudio) "إيقاف الصوت" else "تشغيل المتحدث")
                    }

                    OutlinedButton(
                        onClick = { isRecordingUser = !isRecordingUser },
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isRecordingUser) Color(0xFFDC2626) else Color(0xFF0F172A)
                        )
                    ) {
                        Icon(if (isRecordingUser) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isRecordingUser) "إيقاف التسجيل" else "سجّل صوتك")
                    }
                }

                if (isRecordingUser) {
                    Text(
                        text = "● جاري التقاط صوتك للمحاكاة والمقارنة اللحظية...",
                        fontSize = 11.sp,
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Interactive Dialog for OPI (Over-the-Phone Interpreting)
 */
@Composable
private fun OpiScenariosDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    var isCallActive by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFFEF3C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PhoneInTalk, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(20.dp))
                        }
                        Text(
                            text = "OPI - محاكاة الترجمة عبر الهاتف",
                            fontSize = 16.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = "سيناريو تجريبي واقعي: مكالمة طارئة في مركز استشفائي ومصالح الهجرة.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF8FAFC),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("📞 الطرف الأول (المستشفى - EN):", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
                        Text("\"Doctor: The patient presents severe abdominal pain and requires immediate allergy tests before surgery.\"", fontSize = 12.5.sp, color = Color(0xFF0F172A))
                        
                        HorizontalDivider(color = Color(0xFFE2E8F0))

                        Text("🗣️ دورك كمترجم فوري OPI (نقل للطرف الثاني بالعربية):", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                        Text("\"الطبيب: المريض يعاني من آلام حادة بالبطن ويلزم إجراء اختبارات الحساسية فوراً قبل التدخل الجراحي.\"", fontSize = 12.5.sp, color = Color(0xFF0F172A))
                    }
                }

                Button(
                    onClick = { isCallActive = !isCallActive },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isCallActive) Color(0xFFDC2626) else Color(0xFF16A34A))
                ) {
                    Icon(if (isCallActive) Icons.Default.CallEnd else Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isCallActive) "إنهاء المكالمة التدريبية" else "بدء اتصال المحاكاة الهاتفي")
                }
            }
        }
    }
}
