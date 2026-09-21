package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AppCurrency
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun PricingScreen(
    isArabic: Boolean,
    currency: AppCurrency,
    onSelectPlan: (planName: String, priceDzd: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Column {
            Text(
                text = if (isArabic) "باقات الأسعار والاشتراكات" else "Pricing & Service Plans",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isArabic) "أسعار شفافة ومطابقة لمعايير ISO مع إمكانية الدفع بالدينار (CIB/Dahabia/BaridiMob) والعملات الدولية"
                else "Transparent ISO pricing with local CIB/BaridiMob and international payments",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Plan 1: Starter / Documents
        PlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة المستندات الأساسية" else "Starter Documents",
            badge = if (isArabic) "الأكثر طلباً للأفراد" else "Popular for Individuals",
            badgeColor = RedPrimary,
            priceDzd = 4500,
            currency = currency,
            features = if (isArabic) listOf(
                "ترجمة مستندات حتى 1,500 كلمة",
                "ختم رسمي من مترجم محلف معتمد",
                "تسليم خلال 48 ساعة بصيغة PDF ونسخة ورقية",
                "تدقيق لغوي ومطابقة للمصطلحات"
            ) else listOf(
                "Up to 1,500 words translation",
                "Official certified translator stamp",
                "48-hour delivery (PDF + hardcopy)",
                "Proofreading & terminology check"
            ),
            isPopular = false,
            onSelect = { onSelectPlan(if (isArabic) "باقة المستندات الأساسية" else "Starter Documents", 4500) }
        )

        // Plan 2: Business & Legal Pro
        PlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة الشركات والعقود الاحترافية" else "Business & Legal Pro",
            badge = if (isArabic) "أفضل قيمة للمؤسسات" else "Best for Enterprises",
            badgeColor = GoldYellow,
            priceDzd = 18000,
            currency = currency,
            features = if (isArabic) listOf(
                "ترجمة متخصصة حتى 6,000 كلمة",
                "عقود قانونية، مناقصات وتقارير مالية",
                "اتفاقية سرية بيانات صارمة (NDA)",
                "أولوية تسليم فائقة (24 ساعة)",
                "فريق عمل مخصص ومدير حساب"
            ) else listOf(
                "Up to 6,000 words specialized translation",
                "Contracts, tenders & financial reports",
                "Strict Non-Disclosure Agreement (NDA)",
                "Express priority delivery (24 hours)",
                "Dedicated translation team & account manager"
            ),
            isPopular = true,
            onSelect = { onSelectPlan(if (isArabic) "باقة الشركات والعقود الاحترافية" else "Business & Legal Pro", 18000) }
        )

        // Plan 3: Conference VIP
        PlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة المؤتمرات والترجمة الفورية" else "Conference Interpretation VIP",
            badge = if (isArabic) "قمم ومؤتمرات" else "Summits & Conferences",
            badgeColor = RedDark,
            priceDzd = 50000,
            currency = currency,
            features = if (isArabic) listOf(
                "يوم كامل ترجمة فورية متزامنة (Simultaneous)",
                "فريق مترجمين فوريين معتمدين (شخصين)",
                "تجهيز كابينة الترجمة وسماعات الراديو اللاسلكية",
                "تغطية تقنية وهندسية كاملة للقاعة",
                "تنسيق مسبق للمسارد والمصطلحات التخصصية"
            ) else listOf(
                "Full-day simultaneous interpretation",
                "Team of 2 certified senior interpreters",
                "Soundproof booth & wireless RF receivers",
                "Complete on-site technical sound engineering",
                "Pre-event glossary & terminology preparation"
            ),
            isPopular = false,
            onSelect = { onSelectPlan(if (isArabic) "باقة المؤتمرات والترجمة الفورية" else "Conference Interpretation VIP", 50000) }
        )

        // Plan 4: Academy All-Access
        PlanCard(
            isArabic = isArabic,
            title = if (isArabic) "اشتراك الأكاديمية السنوي" else "Masterclass Annual Pass",
            badge = if (isArabic) "للمترجمين والطلبة" else "For Translators & Students",
            badgeColor = SuccessGreen,
            priceDzd = 12000,
            currency = currency,
            features = if (isArabic) listOf(
                "وصول غير محدود لجميع ورشات العمل والماستركلاس",
                "تطبيقات عملية على نصوص حقيقية ومصطلحات دولية",
                "شهادات إتمام دورات معتمدة برقم تسلسلي موثق",
                "انضمام لمجتمع المترجمين الخاص وشبكة التوظيف"
            ) else listOf(
                "Unlimited access to all masterclass workshops",
                "Hands-on practice with real legal & UN texts",
                "Official verifiable certificates with serial IDs",
                "Access to exclusive translator community & job board"
            ),
            isPopular = false,
            onSelect = { onSelectPlan(if (isArabic) "اشتراك الأكاديمية السنوي" else "Masterclass Annual Pass", 12000) }
        )
    }
}

@Composable
private fun PlanCard(
    isArabic: Boolean,
    title: String,
    badge: String,
    badgeColor: Color,
    priceDzd: Int,
    currency: AppCurrency,
    features: List<String>,
    isPopular: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("plan_card_${title.take(8).replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
        ),
        border = if (isPopular) androidx.compose.foundation.BorderStroke(2.dp, RedPrimary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPopular) 4.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = badgeColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        color = badgeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = currency.format(priceDzd),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = RedPrimary
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                features.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isPopular) RedPrimary else MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = if (isArabic) "اختيار هذه الباقة والدفع" else "Select Plan & Pay",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
