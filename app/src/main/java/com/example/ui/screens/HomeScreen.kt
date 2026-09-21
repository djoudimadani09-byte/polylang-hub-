package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun HomeScreen(
    isArabic: Boolean,
    onNavigate: (AppTab) -> Unit,
    onOpenOrderDialog: (serviceType: String) -> Unit,
    onOpenAuthDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.polylang_logo_1789450374812),
                        contentDescription = "Polylang Hub Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentScale = ContentScale.Fit
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isArabic) "مجمع بوليلانغ للترجمة" else "Polylang Hub",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = if (isArabic) "الرائد في الترجمة المعتمدة والمؤتمرات الدولية" else "Premier Certified Translation & Conference Hub",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }

                Text(
                    text = if (isArabic)
                        "خدمات ترجمة قانونية، طبية، تقنية وفورية وفق معايير الجودة الدولية ISO 17100:2015 بإشراف نخبة من كبار المترجمين المحلفين والمعتمدين دولياً."
                    else
                        "Legal, medical, technical, and conference interpretation adhering to ISO 17100:2015 standards, supervised by certified sworn translators.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                    lineHeight = 19.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onOpenOrderDialog(if (isArabic) "ترجمة معتمدة" else "Certified Translation") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_order_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) "طلب ترجمة" else "Order Translation",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = { onNavigate(AppTab.ACADEMY) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_academy_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) "الماستركلاس" else "Masterclass",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Trust Badges & ISO Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TrustItem(icon = Icons.Default.Verified, title = "ISO 17100:2015", subtitle = if (isArabic) "معيار دولي" else "Certified Standard")
                VerticalDivider(modifier = Modifier.height(36.dp))
                TrustItem(icon = Icons.Default.Gavel, title = if (isArabic) "اعتماد رسمي" else "Sworn Swear", subtitle = if (isArabic) "مقبول لدى السفارات" else "Embassy Accepted")
                VerticalDivider(modifier = Modifier.height(36.dp))
                TrustItem(icon = Icons.Default.Security, title = "100%", subtitle = if (isArabic) "سرية تامة NDA" else "Strict NDA")
            }
        }

        // Quick Portals Grid
        Text(
            text = if (isArabic) "بوابات الخدمات السريعة" else "Core Service Portals",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "خدمات الترجمة" else "Translation",
                desc = if (isArabic) "قانونية، طبية وأكاديمية" else "Legal, Medical & Academic",
                icon = Icons.Default.Description,
                badge = if (isArabic) "معتمد" else "Sworn",
                color = RedPrimary,
                onClick = { onNavigate(AppTab.SERVICES) }
            )
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "محرر SRT المرئي" else "SRT Subtitling",
                desc = if (isArabic) "توقيت وتصدير ملفات SRT" else "Sync & Export SRT",
                icon = Icons.Default.Subtitles,
                badge = "Netflix CPS",
                color = RedDark,
                onClick = { onNavigate(AppTab.SUBTITLING) }
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "الأكاديمية والماستركلاس" else "Academy & Courses",
                desc = if (isArabic) "ورشات عمل متقدمة مع خبراء" else "Workshops with Top Experts",
                icon = Icons.Default.School,
                badge = if (isArabic) "شهادات معتمدة" else "Certificates",
                color = GoldYellow,
                onClick = { onNavigate(AppTab.ACADEMY) }
            )
            PortalCard(
                modifier = Modifier.weight(1f),
                title = if (isArabic) "الأسعار والباقات" else "Pricing & Plans",
                desc = if (isArabic) "أسعار شفافة بالدينار والعملات" else "Clear Rates in DZD/USD/EUR",
                icon = Icons.Default.Payments,
                badge = if (isArabic) "عروض خاصة" else "Best Rates",
                color = SuccessGreen,
                onClick = { onNavigate(AppTab.PRICING) }
            )
        }

        // Conference Interpretation Special Feature
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
}

@Composable
private fun TrustItem(icon: ImageVector, title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Text(text = subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
