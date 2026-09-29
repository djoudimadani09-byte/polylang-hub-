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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
    var promoCodeInput by remember { mutableStateOf("AGHILAS3M50") }
    var isPromoApplied by remember { mutableStateOf(true) } // Pre-applied by default for AGHILAS offer!
    var promoMessage by remember { mutableStateOf(if (isArabic) "تم تفعيل خصم 50% عبر كود AGHILAS3M50 (عرض أغيلاس)" else "50% discount active via code AGHILAS3M50 (Aghilas Offer)") }

    val discountMultiplier = if (isPromoApplied) 0.5f else 1.0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. SPECIAL OFFER HERO BANNER (GoInterPrep / AGHILAS3M50) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("pricing_aghilas_offer_banner"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
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
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEF08A)
                    ) {
                        Text(
                            text = if (isArabic) "🔥 عرض أغيلاس الخاص (AGHILAS-DZ)" else "🔥 Special Aghilas Offer",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
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
                            text = "-50% OFF",
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
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = if (isArabic)
                        "استفد من خصم 50% الحصري لطلاب معاهد الترجمة والمترجمين في الجزائر باستخدام كوبون AGHILAS3M50."
                    else
                        "Enjoy 50% exclusive discount for translation students and practitioners using coupon AGHILAS3M50.",
                    fontSize = 12.5.sp,
                    color = Color(0xFFDBEAFE),
                    lineHeight = 18.sp
                )

                // Promo Code Box & Toggle Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.ConfirmationNumber, contentDescription = null, tint = Color(0xFFFEF08A), modifier = Modifier.size(20.dp))
                        Column {
                            Text(
                                text = "AGHILAS3M50",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = if (isPromoApplied) "✓ الخصم مفعّل تلقائياً" else "انقر للتفعيل",
                                fontSize = 10.5.sp,
                                color = if (isPromoApplied) Color(0xFF86EFAC) else Color(0xFF93C5FD)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isPromoApplied = !isPromoApplied
                            promoMessage = if (isPromoApplied) {
                                if (isArabic) "تم تطبيق خصم 50% بنجاح!" else "50% discount applied!"
                            } else {
                                if (isArabic) "تم إلغاء تفعيل الكود" else "Promo code removed"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPromoApplied) Color(0xFF22C55E) else Color(0xFF3B82F6)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (isPromoApplied) (if (isArabic) "مفعّل ✓" else "Active ✓") else (if (isArabic) "تفعيل الخصم" else "Apply 50%"),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // --- 2. HEADER: Clean, concise & academic ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isArabic) "خطط التدريب والاشتراكات" else "Training Plans & Access",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = if (isArabic) "شفافية تامة ودفع محلي بالدينار (الذهبية / بريدي موب / CIB)" else "ISO certified pricing with DZD BaridiMob/Edahabia & Cards",
                    fontSize = 11.5.sp,
                    color = Color(0xFF64748B)
                )
            }
            if (isPromoApplied) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFDCFCE7)
                ) {
                    Text(
                        text = if (isArabic) "خصم 50% سارٍ" else "50% Applied",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF15803D)
                    )
                }
            }
        }

        // Plan 1: Starter / Student Lab (Bilingual Interpreter Training)
        val p1Orig = 4500
        val p1Final = (p1Orig * discountMultiplier).toInt()
        CleanPlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة الطالب الممارس (Student Basic)" else "Student Practice Basic",
            badge = if (isArabic) "للطلاب والمبتدئين" else "Students & Beginners",
            badgeColor = Color(0xFF0284C7),
            originalPriceDzd = p1Orig,
            finalPriceDzd = p1Final,
            isPromoApplied = isPromoApplied,
            currency = currency,
            features = if (isArabic) listOf(
                "ولوج غير محدود لمقصورة الترجمة الفورية الذكية",
                "تمارين الترجمة التتابعية وتدوين رموز روزان",
                "تمارين التظليل الصوتي (Shadowing) لتحسين الطلاقة",
                "امتحان تحديد المستوى CEFR مع شهادة تقييم آلية"
            ) else listOf(
                "Unlimited access to AI Interpretation Booth",
                "Consecutive interpretation & Rozan note-taking",
                "Speech shadowing drills for listening & fluency",
                "CEFR Diagnostic Exam with performance scorecard"
            ),
            isPopular = false,
            onSelect = { onSelectPlan(if (isArabic) "باقة الطالب الممارس" else "Student Practice Basic", p1Final) }
        )

        // Plan 2: Professional Interpreter Pro (Most Popular)
        val p2Orig = 12000
        val p2Final = (p2Orig * discountMultiplier).toInt()
        CleanPlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة المترجم المحترف (Interpreter Pro)" else "Interpreter Pro Pass",
            badge = if (isArabic) "الأكثر طلباً - مع كود أغيلاس" else "Best Value with AGHILAS",
            badgeColor = Color(0xFFB45309),
            originalPriceDzd = p2Orig,
            finalPriceDzd = p2Final,
            isPromoApplied = isPromoApplied,
            currency = currency,
            features = if (isArabic) listOf(
                "كل مميزات باقة الطالب + تقييم بالذكاء الاصطناعي",
                "سيناريوهات الترجمة الفورية الهاتفية (OPI)",
                "مكتبة الماستركلاس مع نخبة أساتذة اللغات والترجمة",
                "تقارير أداء دورية معتمدة وموجهة لسوق العمل",
                "دعم وتوجيه مباشر من المترجمين المحلفين"
            ) else listOf(
                "All Basic features + AI speech evaluation",
                "Real-world Over-the-Phone Interpreting (OPI)",
                "Full Language Masterclass library with named instructors",
                "Comprehensive progress report for employment",
                "Direct mentorship from certified sworn translators"
            ),
            isPopular = true,
            onSelect = { onSelectPlan(if (isArabic) "باقة المترجم المحترف (Pro)" else "Interpreter Pro Pass", p2Final) }
        )

        // Plan 3: Certified Translation Services & Legal Orders
        val p3Orig = 18000
        val p3Final = (p3Orig * discountMultiplier).toInt()
        CleanPlanCard(
            isArabic = isArabic,
            title = if (isArabic) "باقة المستندات الرسمية والشركات" else "Certified Translation & Corporate",
            badge = if (isArabic) "خدمات المترجم المحلف" else "Sworn Translation",
            badgeColor = RedPrimary,
            originalPriceDzd = p3Orig,
            finalPriceDzd = p3Final,
            isPromoApplied = isPromoApplied,
            currency = currency,
            features = if (isArabic) listOf(
                "ترجمة رسمية معتمدة حتى 6,000 كلمة بختم محلف",
                "عقود دولية، مناقصات، ووثائق إدارية",
                "اتفاقية سرية وحماية بيانات مهنية (NDA)",
                "أولوية تدقيق ومطابقة مسارد بالذكاء الاصطناعي"
            ) else listOf(
                "Certified sworn translation up to 6,000 words",
                "International contracts & legal tenders",
                "Strict Non-Disclosure Agreement (NDA)",
                "Priority human proofreading & AI termbase"
            ),
            isPopular = false,
            onSelect = { onSelectPlan(if (isArabic) "باقة المستندات الرسمية والشركات" else "Certified Translation & Corporate", p3Final) }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CleanPlanCard(
    isArabic: Boolean,
    title: String,
    badge: String,
    badgeColor: Color,
    originalPriceDzd: Int,
    finalPriceDzd: Int,
    isPromoApplied: Boolean,
    currency: AppCurrency,
    features: List<String>,
    isPopular: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("plan_card_${title.take(8).replace(" ", "_")}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular) Color(0xFFF0F5FF) else Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isPopular) 2.dp else 1.dp,
            color = if (isPopular) Color(0xFF2563EB) else Color(0xFFE2E8F0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPopular) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row: Badge & Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    color = badgeColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = badge,
                        color = badgeColor,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    if (isPromoApplied) {
                        Text(
                            text = currency.format(originalPriceDzd),
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = currency.format(finalPriceDzd),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isPopular) Color(0xFF1D4ED8) else Color(0xFF0F172A)
                    )
                }
            }

            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            HorizontalDivider(color = Color(0xFFE2E8F0))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                features.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = feature,
                            fontSize = 12.5.sp,
                            color = Color(0xFF334155),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPopular) Color(0xFF1D4ED8) else Color(0xFF334155)
                )
            ) {
                Text(
                    text = if (isArabic) "اختيار هذه الباقة والاستفادة من العرض ←" else "Select Plan & Claim Offer →",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}
