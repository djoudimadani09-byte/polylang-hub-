package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.MasterclassCourse
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun CourseViewerDialog(
    course: MasterclassCourse,
    isArabic: Boolean,
    onDismiss: () -> Unit,
    onClaimCertificate: (courseTitle: String) -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var currentProgress by remember { mutableStateOf(0.35f) }
    var playbackSpeed by remember { mutableStateOf("1.0x") }
    var activeSubTab by remember { mutableStateOf(0) } // 0: Lessons, 1: Glossary, 2: Practice Quiz
    var completedLessons by remember { mutableStateOf(setOf(1, 2)) }
    var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    val courseModules = remember(course.id) {
        listOf(
            Triple(1, if (isArabic) "المقدمة والأسس النظرية للمساق" else "Introduction & Theoretical Fundamentals", "07:15"),
            Triple(2, if (isArabic) "المصطلحات المحلفة والمعايير المعتمدة" else "Sworn Terminology & International Standards", "11:30"),
            Triple(3, if (isArabic) "ورشة التطبيق العملي والمحاكاة الحية" else "Live Practical Workshop & Simulation", "14:40"),
            Triple(4, if (isArabic) "مراجعة الجودة وضوابط معيار ISO 17100" else "Quality Review & ISO 17100 Compliance", "08:55")
        )
    }

    val glossaryTerms = remember(course.id) {
        listOf(
            Pair("Force Majeure", if (isArabic) "القوة القاهرة / الإعفاء التعاقدي الفجائي" else "Force Majeure / Contractual Exemption"),
            Pair("Indemnity & Hold Harmless", if (isArabic) "التعويض وإبراء الذمة وحماية المتعاقد" else "Indemnity & Hold Harmless"),
            Pair("Simultaneous Décalage", if (isArabic) "الفارق الزمني في كابينة الترجمة الفورية" else "Acoustic Delay in Booth"),
            Pair("ISO 17100:2015", if (isArabic) "المعيار الدولي لخدمات الترجمة التحريرية والتدقيق" else "International Translation Service Standard")
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.92f)
                .padding(vertical = 12.dp)
                .testTag("course_viewer_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("course_viewer_close_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(course.category, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = RedPrimary.copy(alpha = 0.1f),
                                labelColor = RedPrimary
                            )
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text(course.level, fontSize = 11.sp) }
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SuccessGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "1080p Full HD",
                                color = SuccessGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Course Title & Instructor
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${if (isArabic) "المحاضر:" else "Instructor:"} ${course.instructor} • ${course.duration}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Interactive Video Player Simulator Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .testTag("course_video_player"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Background visuals & live subtitles
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
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
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (isPlaying) Color.Green else Color.Red)
                                    )
                                    Text(
                                        text = if (isPlaying) (if (isArabic) "بث مباشر تفاعلي" else "Live Stream") else (if (isArabic) "متوقف مؤقتاً" else "Paused"),
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 11.sp
                                    )
                                }
                                Text(
                                    text = "Polylang Masterclass Player",
                                    color = GoldYellow,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Subtitle Caption Bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.7f))
                                    .padding(vertical = 6.dp, horizontal = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isArabic) "💬 [ترجمة فورية]: معايير صياغة وتدقيق العقود المعتمدة دولياً وفق ISO 17100" else "💬 [Subtitles]: Standardized sworn translation principles under ISO 17100",
                                    color = Color.Yellow,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Video Controls Row
                            Column {
                                // Progress Slider
                                Slider(
                                    value = currentProgress,
                                    onValueChange = { currentProgress = it },
                                    modifier = Modifier.fillMaxWidth().height(20.dp),
                                    colors = SliderDefaults.colors(
                                        thumbColor = RedPrimary,
                                        activeTrackColor = RedPrimary,
                                        inactiveTrackColor = Color.Gray.copy(alpha = 0.5f)
                                    )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        IconButton(
                                            onClick = { isPlaying = !isPlaying },
                                            modifier = Modifier.size(32.dp).testTag("video_play_pause_btn")
                                        ) {
                                            Icon(
                                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = "Play/Pause",
                                                tint = Color.White
                                            )
                                        }
                                        Text(
                                            text = "12:45 / ${course.duration}",
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 11.sp
                                        )
                                    }

                                    // Speed Selector
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        listOf("1.0x", "1.25x", "1.5x").forEach { speed ->
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(if (playbackSpeed == speed) RedPrimary else Color.White.copy(alpha = 0.2f))
                                                    .clickable { playbackSpeed = speed }
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(speed, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation Tabs inside Course Content
                TabRow(
                    selectedTabIndex = activeSubTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    contentColor = RedPrimary,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)).testTag("course_sub_tabs")
                ) {
                    Tab(
                        selected = activeSubTab == 0,
                        onClick = { activeSubTab = 0 },
                        text = { Text(if (isArabic) "المحاور والدروس" else "Lessons", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = activeSubTab == 1,
                        onClick = { activeSubTab = 1 },
                        text = { Text(if (isArabic) "المسرد المصطلحي" else "Glossary", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = activeSubTab == 2,
                        onClick = { activeSubTab = 2 },
                        text = { Text(if (isArabic) "اختبار الفهم" else "Quiz", fontSize = 12.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable Content Pane
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (activeSubTab) {
                        0 -> {
                            // Modules list
                            courseModules.forEach { (index, title, duration) ->
                                val isDone = completedLessons.contains(index)
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            completedLessons = if (isDone) completedLessons - index else completedLessons + index
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isDone) SuccessGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isDone) SuccessGreen else RedPrimary.copy(alpha = 0.15f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (isDone) {
                                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                                } else {
                                                    Text("$index", color = RedPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                                }
                                            }
                                            Column {
                                                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                                Text(duration, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            }
                                        }

                                        Icon(
                                            imageVector = Icons.Default.PlayCircle,
                                            contentDescription = "Play Lesson",
                                            tint = RedPrimary
                                        )
                                    }
                                }
                            }
                        }
                        1 -> {
                            // Glossary list
                            glossaryTerms.forEach { (en, ar) ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(en, fontWeight = FontWeight.Bold, color = RedPrimary, style = MaterialTheme.typography.bodyMedium)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(ar, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                        }
                        2 -> {
                            // Interactive Practice Quiz
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text(
                                        text = if (isArabic) "سؤال الفحص السريع:" else "Quick Assessment Question:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = if (isArabic) "ما هو الشرط الجوهري لاعتماد الترجمة وفق معيار ISO 17100:2015؟" else "What is the key requirement for ISO 17100 certification?",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    val options = listOf(
                                        if (isArabic) "التدقيق المزدوج المستقل (Bilingual Revision by Second Translator)" else "Independent Bilingual Revision",
                                        if (isArabic) "الاعتماد الحصري على الترجمة الآلية الفورية" else "Relying purely on Machine Translation",
                                        if (isArabic) "تجاهل مسرد المصطلحات المعياري" else "Ignoring standard glossaries"
                                    )

                                    options.forEachIndexed { i, opt ->
                                        OutlinedCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    selectedQuizOption = i
                                                    quizSubmitted = false
                                                },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = CardDefaults.outlinedCardColors(
                                                containerColor = if (selectedQuizOption == i) RedPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                                            )
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                RadioButton(
                                                    selected = selectedQuizOption == i,
                                                    onClick = { selectedQuizOption = i }
                                                )
                                                Text(opt, style = MaterialTheme.typography.bodySmall)
                                            }
                                        }
                                    }

                                    Button(
                                        onClick = { quizSubmitted = true },
                                        enabled = selectedQuizOption != null,
                                        modifier = Modifier.fillMaxWidth().height(42.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                                    ) {
                                        Text(if (isArabic) "تأكيد الإجابة والتقييم" else "Submit Answer")
                                    }

                                    if (quizSubmitted) {
                                        if (selectedQuizOption == 0) {
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.15f)),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = if (isArabic) "✓ إجابة ممتازة وصحيحة! تم احتساب 100 نقطة في ملفك المهني." else "✓ Correct! +100 points added to your profile.",
                                                    color = SuccessGreen,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        } else {
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = if (isArabic) "❌ إجابة غير دقيقة. الخيار الصحيح هو التدقيق المزدوج المستقل." else "❌ Incorrect. The correct answer is independent bilingual revision.",
                                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isArabic) "إغلاق" else "Close")
                    }

                    Button(
                        onClick = {
                            onDismiss()
                            onClaimCertificate(course.title)
                        },
                        modifier = Modifier.weight(2f).height(46.dp).testTag("claim_course_cert_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldYellow)
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) "📜 استخراج شهادة إتمام المساق" else "Claim Course Certificate",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
