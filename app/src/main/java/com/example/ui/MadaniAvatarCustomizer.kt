package com.example.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class MadaniAuraTheme(
    val titleAr: String,
    val titleEn: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color,
    val glowDescription: String
) {
    IMPERIAL_GOLD(
        "الهالة الذهبية الإمبراطورية",
        "Imperial Gold Halo",
        Color(0xFFD97706),
        Color(0xFFFBBF24),
        Color(0xFFFDE68A),
        "إطار ذهبي ملكي يرمز للتفوق والريادة الأكاديمية"
    ),
    CYBER_POLYGLOT(
        "السايبر نيون المستقبلي",
        "Cyber Neon Polyglot",
        Color(0xFF0284C7),
        Color(0xFF06B6D4),
        Color(0xFF10B981),
        "أمواج نيون تقنية تحاكي الذكاء الاصطناعي المتقدم"
    ),
    DIPLOMATIC_NAVY(
        "الدبلوماسي الملكي الرسمي",
        "Diplomatic Royal Navy",
        Color(0xFF1E3A8A),
        Color(0xFF991B1B),
        Color(0xFFF3F4F6),
        "ألوان رسمية مستوحاة من أروقة الأمم المتحدة والمؤتمرات الدولية"
    ),
    EMERALD_ALGIERS(
        "الزمردي العاصمي الأصيل",
        "Emerald Algiers",
        Color(0xFF047857),
        Color(0xFF10B981),
        Color(0xFFF59E0B),
        "أخضر زمردي جزائري نقي يرمز للعلم والترجمة المحلفة"
    ),
    RUBY_PASSION(
        "الياقوتي الناري المتوهج",
        "Ruby Flame Passion",
        Color(0xFF991B1B),
        Color(0xFFDC2626),
        Color(0xFFF97316),
        "أحمر ياقوتي ناري يعبر عن الطاقة والحماسة في الكابينة"
    )
}

enum class MadaniAvatarShape(val titleAr: String, val cornerRadius: Dp) {
    CIRCULAR("دائري كلاسيكي", 100.dp),
    SQUIRCLE("مربع مستدير ناعم", 16.dp),
    HEXA_SHIELD("درع الأكاديمية", 22.dp)
}

data class MadaniAvatarConfig(
    val aura: MadaniAuraTheme = MadaniAuraTheme.IMPERIAL_GOLD,
    val shape: MadaniAvatarShape = MadaniAvatarShape.CIRCULAR,
    val borderThickness: Dp = 2.5.dp,
    val showSoundwaveRings: Boolean = true,
    val isPulsingAnimation: Boolean = true
)

@Composable
fun MadaniAvatarIcon(
    config: MadaniAvatarConfig,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
    isSpeaking: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    // Pulsing Scale Animation
    val infiniteTransition = rememberInfiniteTransition(label = "MadaniAuraPulse")
    val pulseScale by if (config.isPulsingAnimation || isSpeaking) {
        infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = if (isSpeaking) 1.08f else 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(if (isSpeaking) 600 else 1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
        )
    } else {
        remember { mutableFloatStateOf(1.0f) }
    }

    val gradientBrush = Brush.sweepGradient(
        listOf(
            config.aura.primaryColor,
            config.aura.secondaryColor,
            config.aura.accentColor,
            config.aura.primaryColor
        )
    )

    val actualShape: Shape = when (config.shape) {
        MadaniAvatarShape.CIRCULAR -> CircleShape
        MadaniAvatarShape.SQUIRCLE -> RoundedCornerShape(config.shape.cornerRadius)
        MadaniAvatarShape.HEXA_SHIELD -> RoundedCornerShape(
            topStart = config.shape.cornerRadius,
            topEnd = config.shape.cornerRadius,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .scale(pulseScale)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        // Outer Glowing Aura Ring
        if (config.showSoundwaveRings || isSpeaking) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .clip(actualShape)
                    .background(config.aura.secondaryColor.copy(alpha = if (isSpeaking) 0.35f else 0.18f))
            )
        }

        // Main Image Container with Custom Gradient Border
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.5.dp)
                .clip(actualShape)
                .border(BorderStroke(config.borderThickness, gradientBrush), actualShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_madani_ai_avatar),
                contentDescription = "Madani AI Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Live Audio Soundwave Indicator Pip
        if (isSpeaking) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(size * 0.28f)
                    .clip(CircleShape)
                    .background(SuccessGreen)
                    .border(1.dp, Color.White, CircleShape)
            ) {
                Text(
                    text = "🎙️",
                    fontSize = (size.value * 0.15f).sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun MadaniAvatarCustomizerDialog(
    currentConfig: MadaniAvatarConfig,
    isArabic: Boolean,
    onDismiss: () -> Unit,
    onSaveConfig: (MadaniAvatarConfig) -> Unit
) {
    var selectedAura by remember { mutableStateOf(currentConfig.aura) }
    var selectedShape by remember { mutableStateOf(currentConfig.shape) }
    var selectedBorderThickness by remember { mutableStateOf(currentConfig.borderThickness) }
    var showSoundwaves by remember { mutableStateOf(currentConfig.showSoundwaveRings) }
    var isPulsing by remember { mutableStateOf(currentConfig.isPulsingAnimation) }

    val previewConfig = MadaniAvatarConfig(
        aura = selectedAura,
        shape = selectedShape,
        borderThickness = selectedBorderThickness,
        showSoundwaveRings = showSoundwaves,
        isPulsingAnimation = isPulsing
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("madani_customizer_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isArabic) "🎨 تخصيص مظهر أيقونة مداني" else "Customize Madani AI Icon",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = RedPrimary
                        )
                        Text(
                            text = if (isArabic) "اختر الهالة والنمط المفضل لمستشارك الذكي" else "Personalize aura, frames & animations",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Interactive Live Preview Box
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    border = BorderStroke(1.dp, selectedAura.primaryColor.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isArabic) "معاينة فورية حية (Live Preview):" else "Live Preview:",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Large Preview Avatar
                        MadaniAvatarIcon(
                            config = previewConfig,
                            size = 80.dp,
                            isSpeaking = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = selectedAura.titleAr,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = selectedAura.primaryColor
                        )
                        Text(
                            text = selectedAura.glowDescription,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Section 1: Choose Aura & Gradient
                Text(
                    text = if (isArabic) "1. طراز الهالة اللونية المتوهجة (Aura Theme):" else "1. Aura & Lighting Theme:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(MadaniAuraTheme.values()) { aura ->
                        val isSelected = selectedAura == aura
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedAura = aura },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) aura.primaryColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            border = BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) aura.primaryColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(aura.primaryColor)
                                )
                                Text(
                                    text = if (isArabic) aura.titleAr else aura.titleEn,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Section 2: Avatar Shape & Frame
                Text(
                    text = if (isArabic) "2. هندسة الإطار والشكل (Frame Geometry):" else "2. Shape & Frame Geometry:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MadaniAvatarShape.values().forEach { shape ->
                        val isSelected = selectedShape == shape
                        OutlinedButton(
                            onClick = { selectedShape = shape },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) RedPrimary else MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text(
                                text = if (isArabic) shape.titleAr else shape.name,
                                fontSize = 10.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) RedPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Section 3: Interactive Toggles (Soundwaves & Pulse)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isArabic) "أمواج صوتية مضيئة حول الأيقونة" else "Soundwave aura rings",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Switch(
                                checked = showSoundwaves,
                                onCheckedChange = { showSoundwaves = it },
                                modifier = Modifier.scale(0.8f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isArabic) "نبضات حيوية متحركة (Dynamic Pulse)" else "Dynamic pulse animation",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Switch(
                                checked = isPulsing,
                                onCheckedChange = { isPulsing = it },
                                modifier = Modifier.scale(0.8f)
                            )
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (isArabic) "إلغاء" else "Cancel")
                    }

                    Button(
                        onClick = {
                            onSaveConfig(previewConfig)
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_save_madani_customization"),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (isArabic) "حفظ وتطبيق ✨" else "Save & Apply ✨", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
