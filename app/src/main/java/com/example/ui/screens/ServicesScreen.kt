package com.example.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AppCurrency
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun ServicesScreen(
    isArabic: Boolean,
    currency: AppCurrency,
    onOpenOrderDialog: (serviceType: String) -> Unit,
    onOpenTermbase: () -> Unit
) {
    var quickWordCount by remember { mutableStateOf(1000) }
    val quickEstimateDzd = remember(quickWordCount) { (quickWordCount * 3.5).toInt() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title & ISO Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isArabic) "خدمات الترجمة المعتمدة والمؤتمرات" else "Certified Translation & Conferences",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isArabic) "مطابقة بالكامل للمواصفة القياسية الدولية ISO 17100:2015" else "Fully compliant with ISO 17100:2015 standard",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = onOpenTermbase,
                modifier = Modifier.testTag("services_open_termbase_btn")
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = "AI Assistant", tint = RedPrimary)
            }
        }

        // Live Fast Quote Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("services_quote_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "⚡ حاسبة التسعير الفوري للترجمة:" else "⚡ Instant Price Calculator:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = currency.format(quickEstimateDzd),
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$quickWordCount ${if (isArabic) "كلمة" else "words"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (isArabic) "مدة الإنجاز: 24 - 48 ساعة" else "Delivery: 24 - 48 hours",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = SuccessGreen
                    )
                }

                Slider(
                    value = quickWordCount.toFloat(),
                    onValueChange = { quickWordCount = it.toInt() },
                    valueRange = 250f..8000f,
                    steps = 30,
                    colors = SliderDefaults.colors(thumbColor = RedPrimary, activeTrackColor = RedPrimary)
                )

                Button(
                    onClick = { onOpenOrderDialog("قانونية ورسمية") },
                    modifier = Modifier.fillMaxWidth().height(42.dp).testTag("quick_order_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "طلب تسليم بهذا الحجم الآن" else "Order This Word Count Now")
                }
            }
        }

        // Services Catalog
        val servicesList = listOf(
            Quadruple(
                "قانونية ورسمية",
                Icons.Default.Gavel,
                if (isArabic) "عقود تجارية، اتفاقيات توريد، مذكرات تفاهم FIDIC، وثائق المحاكم، وسجلات تجارية محلفة." else "Commercial contracts, FIDIC agreements, court records, and sworn translations.",
                "3.5 د.ج / كلمة • تدقيق مزدوج"
            ),
            Quadruple(
                "تقنية وهندسية",
                Icons.Default.Engineering,
                if (isArabic) "كتيبات صيانة، وثائق مشاريع الطاقة وسوناطراك، مواصفات برمجية، وبراءات اختراع INAPI." else "Engineering manuals, energy & oil documentation, software specs, and patents.",
                "3.8 د.ج / كلمة • ذاكرة مصطلحية"
            ),
            Quadruple(
                "طبية ودوائية",
                Icons.Default.LocalHospital,
                if (isArabic) "تقارير التجارب السريرية، اليقظة الدوائية Pharmacovigilance، وتراخيص المستحضرات الطبية." else "Clinical trial reports, pharmacovigilance, and certified medical dossiers.",
                "4.2 د.ج / كلمة • إشراف أطباء"
            ),
            Quadruple(
                "ترجمة فورية للمؤتمرات",
                Icons.Default.RecordVoiceOver,
                if (isArabic) "تأمين كبائن الترجمة الفورية، مترجمين معتمدين للمركز الدولي للمؤتمرات (CIC عبد اللطيف رحال)." else "Simultaneous interpretation booths & teams for major international summits.",
                "يومي / للمؤتمرات • كابينة معتمدة"
            )
        )

        servicesList.forEach { (title, icon, desc, badge) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("service_item_${title}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(RedPrimary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(22.dp))
                            }
                            Column {
                                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                                Text(badge, style = MaterialTheme.typography.labelSmall, color = RedPrimary, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        SuggestionChip(
                            onClick = {},
                            label = { Text("معتمد ✓", fontSize = 10.sp, color = SuccessGreen) }
                        )
                    }

                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { onOpenOrderDialog(title) },
                            modifier = Modifier.height(38.dp).testTag("order_service_${title}"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isArabic) "طلب الخدمة الآن" else "Order Service", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
