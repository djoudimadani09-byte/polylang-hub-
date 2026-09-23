package com.example.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AppTab
import com.example.R
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen
import java.util.Locale

@Composable
fun HomeScreen(
    isArabic: Boolean,
    onNavigate: (AppTab) -> Unit,
    onOpenOrderDialog: (serviceType: String) -> Unit,
    onOpenAuthDialog: () -> Unit
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isSpeaking by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showFounderDialog by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        var speech: TextToSpeech? = null
        speech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                speech?.language = if (isArabic) Locale("ar") else Locale.ENGLISH
            }
        }
        tts = speech
        onDispose {
            speech.stop()
            speech.shutdown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. HERO WELCOME CARD (Matching Screenshot 100%)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_welcome_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header Bar inside Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RedPrimary.copy(alpha = 0.08f))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "🎙️ 🤖",
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isArabic) "فيديو الترحيب والتقديم" else "Welcome & Introduction Video",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
                    ) {
                        Text(
                            text = "📖 ISO 17100 معتمد",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Robot Presenter Banner Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_robot_presenter_1789724327311),
                            contentDescription = "Robot Presenter",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Play Button Overlay
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.9f))
                                .clickable {
                                    val text = if (isArabic)
                                        "مرحباً بكم في مجمع بوليلانغ للترجمة المعتمدة والمؤتمرات الدولية بإشراف الأستاذ جودي مداني."
                                    else
                                        "Welcome to Polylang Hub, premier certified translation and international conference hub."
                                    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "greeting")
                                    isSpeaking = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Greeting",
                                tint = RedPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Bottom subtitle strip
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            color = Color.Black.copy(alpha = 0.75f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "👋 Welcome to Polylang Hub",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Content details
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (isArabic) "مرحباً بكم في بوليلانغ (Welcome to Polylang)" else "Welcome to Polylang",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isArabic)
                                "خدمات الترجمة المحلفة، ترجمة كابينات المؤتمرات وتطوير المهارات اللغوية وفق أرقى معايير الجودة الدولية ISO 17100:2015."
                            else
                                "Certified sworn translation, interpretation, and professional language mastery to the highest quality standards.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }

                    // Live Audio Greeting Box
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.Mic, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(16.dp))
                                Text(
                                    text = if (isArabic) "الترحيب الصوتي المباشر:" else "Live Audio Greeting:",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = {
                                        tts?.language = Locale("ar")
                                        tts?.speak("مرحباً بكم في بوليلانغ هوب للترجمة المعتمدة والمؤتمرات الدولية.", TextToSpeech.QUEUE_FLUSH, null, "ar")
                                        isSpeaking = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text("▶ استمع", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = {
                                        tts?.stop()
                                        isSpeaking = false
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text("⏹️ إيقاف", fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    // Hero Action Buttons (Row 1)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onOpenOrderDialog(if (isArabic) "ترجمة قانونية محلفة" else "Certified Translation") },
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isArabic) "طلب ترجمة" else "Request", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onNavigate(AppTab.ACADEMY) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isArabic) "الأكاديمية" else "Academy", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onOpenAuthDialog,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isArabic) "تسجيل الدخول" else "Login", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Hero Secondary Buttons (Row 2: About & Founder Profile)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showAboutDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isArabic) "ℹ️ عن بوليلانغ" else "ℹ️ About Us", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = { showFounderDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isArabic) "👨‍🏫 سيرة المطور" else "👨‍🏫 Founder Bio", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Official Platform Mission & Location Badges (No commercial registry numbers)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            RegistryPill(
                                label = if (isArabic) "📍 المقر: الجزائر العاصمة" else "📍 HQ: Algiers, Algeria",
                                modifier = Modifier.weight(1f)
                            )
                            RegistryPill(
                                label = if (isArabic) "🎓 منصة لطلبة الترجمة ومتعلمي اللغات" else "🎓 For Students & Learners",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            RegistryPill(
                                label = if (isArabic) "⚖️ ترجمة معتمدة للمؤسسات والأفراد" else "⚖️ Certified Translation",
                                modifier = Modifier.weight(1f)
                            )
                            RegistryPill(
                                label = if (isArabic) "🌐 عربي • فرنسي • ألماني • إنجليزي" else "🌐 AR • FR • DE • EN",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // 2. ROLE SELECTOR CARD (5 Roles with strict data isolation)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column {
                    Text(
                        text = if (isArabic) ":الصفة والدور الحالي في المنصة" else "Active Role in Platform:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isArabic) "بوابة متعددة الأدوار (5 صلاحيات معزولة)" else "Multi-Role Portal (5 Isolated Views)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = RedPrimary
                    )
                }

                // 5 Roles across two rows
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RoleChip(
                            label = "🔒 المشرف djoudimadani09",
                            isActive = false,
                            onClick = onOpenAuthDialog,
                            modifier = Modifier.weight(1.3f)
                        )
                        RoleChip(
                            label = "🎓 مترجم معتمد",
                            isActive = false,
                            onClick = { onNavigate(AppTab.SUBTITLING) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RoleChip(
                            label = "🏛️ طلبة الترجمة",
                            isActive = true,
                            onClick = { onNavigate(AppTab.ACADEMY) },
                            modifier = Modifier.weight(1f)
                        )
                        RoleChip(
                            label = "📚 متعلم لغات",
                            isActive = false,
                            onClick = { onNavigate(AppTab.ACADEMY) },
                            modifier = Modifier.weight(1f)
                        )
                        RoleChip(
                            label = "👤 عميل ترجمة",
                            isActive = false,
                            onClick = { onNavigate(AppTab.SERVICES) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // 3. CRA.DZ ALIGNED SECTIONS: المنصات الإلكترونية، نشاطات، كيف تنضم
        Text(
            text = if (isArabic) "🏛️ المنصات والخدمات الإلكترونية (cra.dz)" else "Electronic Portals & Services",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        // Highlight: Placement Exam Banner Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigate(AppTab.ACADEMY) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, RedPrimary.copy(alpha = 0.35f))
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(RedPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (isArabic) "امتحان تحديد المستوى المعياري" else "Standard Placement Exam",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = RedPrimary
                        ) {
                            Text(
                                text = "CEFR",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = if (isArabic) "اختبارات تفاعلية حقيقية مع سلم تنقيط معتمد لتقييم كفاءتك المهنية" else "Interactive exams with realistic rubric scoring to assess your proficiency",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = RedPrimary
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "خدمات ونشاطات الترجمة" else "Translation Services",
                desc = if (isArabic) "قانونية، طبية وأكاديمية" else "Legal, Medical & Academic",
                icon = Icons.Default.Description,
                badge = if (isArabic) "نشاطات" else "Services",
                color = RedPrimary,
                onClick = { onNavigate(AppTab.SERVICES) }
            )
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "المنصات الإلكترونية (SRT)" else "SRT Subtitling",
                desc = if (isArabic) "توقيت وتصدير ملفات الترجمة" else "Sync & Export SRT",
                icon = Icons.Default.Subtitles,
                badge = "المنصات",
                color = RedDark,
                onClick = { onNavigate(AppTab.SUBTITLING) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "الأكاديمية والماستركلاس" else "Academy & Courses",
                desc = if (isArabic) "100 محاضرة متخصصة مع خبراء" else "100 Specialized Lectures",
                icon = Icons.Default.School,
                badge = if (isArabic) "تدريب" else "Training",
                color = GoldYellow,
                onClick = { onNavigate(AppTab.ACADEMY) }
            )
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "كيف تنضم والاشتراكات" else "How to Join & Plans",
                desc = if (isArabic) "باقات واشتراكات بريدي موب وCCP" else "Plans via BaridiMob & CCP",
                icon = Icons.Default.Payments,
                badge = if (isArabic) "كيف تنضم" else "Join",
                color = SuccessGreen,
                onClick = { onNavigate(AppTab.PRICING) }
            )
        }

        // 4. CONFERENCE INTERPRETATION BANNER
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenOrderDialog(if (isArabic) "ترجمة فورية للمؤتمرات" else "Conference Interpretation") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(RedPrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HeadsetMic,
                        contentDescription = null,
                        tint = RedPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isArabic) "تجهيز المؤتمرات والترجمة الفورية" else "Conference & Booth Interpretation",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isArabic) "كابينات عازلة للصوت، أجهزة استقبال لاسلكية، ومترجمون فوريون متخصصون." else "Soundproof booths, RF receivers, and senior simultaneous interpreters.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = RedPrimary
                )
            }
        }
    }

    if (showAboutDialog) {
        AboutUsDialog(
            isArabic = isArabic,
            onDismiss = { showAboutDialog = false }
        )
    }

    if (showFounderDialog) {
        FounderProfileDialog(
            isArabic = isArabic,
            onDismiss = { showFounderDialog = false }
        )
    }
}

@Composable
private fun RegistryPill(label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        shape = RoundedCornerShape(6.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RoleChip(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        color = if (isActive) RedPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = RoundedCornerShape(50),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isActive) RedPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            fontSize = 10.5.sp,
            fontWeight = if (isActive) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isActive) RedPrimary else MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
    }
}

@Composable
private fun PortalCard(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    icon: ImageVector,
    badge: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = color.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = badge,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
            }
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
                text = desc,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp,
                minLines = 2,
                maxLines = 2
            )
        }
    }
}

@Composable
fun AboutUsDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "ℹ️ عن منصة بوليلانغ (Polylang Hub)" else "ℹ️ About Polylang Hub",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = RedPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = if (isArabic)
                            "بوليالنغ هي منصة ترجمة تساعد طلبة الترجمة ومتعلمي اللغات في تحسين مستواهم ، والذين يبحثون عن ترجمة سيحصلون على ترجمة من مترجمي المنصة وغيرها مقر العاصمة"
                        else
                            "Polylang Hub is a translation platform designed to assist translation students and language learners in elevating their professional proficiency. Individuals and enterprises seeking certified linguistic services receive sworn translations and conference interpretation directly from our platform translators and certified associates in Algiers.",
                        modifier = Modifier.padding(14.dp),
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RegistryPill(
                        label = if (isArabic) "📍 المقر: الجزائر العاصمة" else "📍 HQ: Algiers, Algeria",
                        modifier = Modifier.weight(1f)
                    )
                    RegistryPill(
                        label = if (isArabic) "📧 djoudimadani09@gmail.com" else "📧 Admin Contact",
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "إغلاق" else "Close", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FounderProfileDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "👨‍🏫 سيرة المطور والمؤسس" else "👨‍🏫 Founder & Developer Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = RedPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Profile Avatar & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(RedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("ج", color = Color.White, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    }
                    Column {
                        Text(
                            text = if (isArabic) "الأستاذ جودي مداني" else "Djoudi Madani",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isArabic) "مؤسس منصة بوليلانغ ومطور المنظومة" else "Founder & Chief Software Architect",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = if (isArabic)
                            "خريج معهد الترجمة بجامعة الجزائر 2 ماستر 2 ترجمة مؤسساتية عربي فرنسي ألماني ومبرمج لحلول تكنولوجية في مجالات متعددة"
                        else
                            "Graduate of the Institute of Translation at the University of Algiers 2 (Bouzaréah) with Master 2 in Institutional Translation (Arabic, French, German). Full-stack software engineer and developer of technological solutions across specialized domains.",
                        modifier = Modifier.padding(14.dp),
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RegistryPill(
                        label = if (isArabic) "🏛️ معهد الترجمة - جامعة الجزائر 2" else "🏛️ Univ. of Algiers 2",
                        modifier = Modifier.weight(1f)
                    )
                    RegistryPill(
                        label = if (isArabic) "📜 ماستر 2 ترجمة مؤسساتية" else "📜 Master 2 Institutional",
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "إغلاق" else "Close", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
