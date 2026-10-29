package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BoothSpeechesBank
import com.example.ConferenceSpeech
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

data class DecalageDataPoint(
    val speechNum: Int,
    val title: String,
    val domain: String,
    val decalageSeconds: Float, // e.g., 6.4 down to 3.2
    val fluencyScore: Int,      // e.g., 78 to 98%
    val date: String,
    val speedWpm: Int,
    val notes: String
)

data class DomainProgressStat(
    val domainId: String,
    val nameAr: String,
    val icon: String,
    val completedCount: Int,
    val totalCount: Int,
    val avgDecalage: Float,
    val color: Color
)

@Composable
fun StudentProgressDashboard(
    isArabic: Boolean,
    userName: String = "طالب الترجمة",
    onLaunchBoothSpeech: (speechId: String) -> Unit = {}
) {
    // 1. Décalage History Dataset tracking progression across the 100 training speeches
    val decalageTimeline = remember {
        listOf(
            DecalageDataPoint(1, "افتتاح الدورة 78 للجمعية العامة للأمم المتحدة", "دبلوماسي", 6.5f, 74, "2026-09-01", 120, "بداية التدريب: تردد ملحوظ وانقطاع طفيف في تدفق الجمل."),
            DecalageDataPoint(5, "مجلس الأمن الدولي: صيانة السلم والأمن", "دبلوماسي", 6.0f, 79, "2026-09-08", 125, "تحسن في تقنية تقسيم الجمل (Chunking)."),
            DecalageDataPoint(15, "مؤتمر أوبك بلس وإمدادات الطاقة العالمية", "اقتصادي", 5.4f, 82, "2026-09-19", 130, "استيعاب المصطلحات النفطية وسرعة التقاط الأرقام."),
            DecalageDataPoint(28, "محكمة العدل الدولية: نزاع ترسيم الحدود البحرية", "قانوني", 4.9f, 86, "2026-10-02", 135, "تراجع زمن الانتظار لأقل من 5 ثوانٍ مع دقة المصطلحات اللاتينية."),
            DecalageDataPoint(45, "منظمة الصحة العالمية: الجاهزية للأوبئة", "طبي", 4.3f, 90, "2026-10-14", 140, "تحكم ممتاز في التنفس والـ Décalage يقترب من المعيار الاحترافي."),
            DecalageDataPoint(65, "قمة المناخ COP30: تمويل التحول الطاقوي", "مناخ وبيئة", 3.8f, 93, "2026-10-21", 145, "فارق زمني قياسي 3.8 ثوانٍ دون أي حذف أو إسقاط للمعنى."),
            DecalageDataPoint(85, "المؤتمر الوزاري للتجارة العالمية ومكافحة الإغراق", "اقتصادي", 3.4f, 96, "2026-10-27", 150, "أداء مثالي متطابق مع كابينة مترجمي الأمم المتحدة والاتحاد الدولي AIIC.")
        )
    }

    var selectedPoint by remember { mutableStateOf<DecalageDataPoint?>(decalageTimeline.last()) }

    // 2. Domain Breakdown Progress
    val domainStats = remember {
        listOf(
            DomainProgressStat("diplomatic", "القمم الدبلوماسية والأمم المتحدة", "🌐", 18, 25, 3.5f, Color(0xFF1E3A8A)),
            DomainProgressStat("legal", "المحاكم والتحكيم التجاري الدولي", "⚖️", 12, 20, 3.8f, Color(0xFF991B1B)),
            DomainProgressStat("economic", "الطاقة والمالية والتجارة العالمية", "🛢️", 9, 20, 3.6f, Color(0xFFD97706)),
            DomainProgressStat("medical", "الطب والصحة العامة والتجارب السريرية", "🩺", 7, 15, 4.0f, Color(0xFF047857)),
            DomainProgressStat("climate", "المناخ والبيئة والتكنولوجيا الخضراء", "🌍", 8, 20, 3.7f, Color(0xFF0891B2))
        )
    }

    val totalCompletedSpeeches = domainStats.sumOf { it.completedCount } // 54 of 100
    val totalTargetSpeeches = 100
    val overallAverageDecalage = 3.6f // Current average

    // 3. Filterable Speeches Matrix
    var searchQuery by remember { mutableStateOf("") }
    var selectedDomainFilter by remember { mutableStateOf("الكل") }

    val allSpeeches = remember { BoothSpeechesBank.allSpeeches.take(100) }
    val filteredSpeeches = remember(allSpeeches, searchQuery, selectedDomainFilter) {
        allSpeeches.filter { sp ->
            val matchDomain = selectedDomainFilter == "الكل" || sp.domainAr.contains(selectedDomainFilter)
            val matchQuery = searchQuery.isBlank() ||
                    sp.titleAr.contains(searchQuery, ignoreCase = true) ||
                    sp.speaker.contains(searchQuery, ignoreCase = true) ||
                    sp.domainAr.contains(searchQuery, ignoreCase = true)
            matchDomain && matchQuery
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("student_progress_dashboard_root"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Header & AIIC Milestone Badge
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(RedPrimary, GoldYellow, SuccessGreen)))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("📊", fontSize = 20.sp)
                                Text(
                                    text = if (isArabic) "لوحة تحليل أداء الكابينة والفارق الزمني" else "Booth Analytics & Décalage Tracker",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = RedPrimary
                                )
                            }
                            Text(
                                text = if (isArabic) "المتابعة البصرية الدقيقة لتقدم الطالب في الـ 100 خطاب تدريبي معتمد" else "Visual progress across the 100 simulated conference speeches",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SuccessGreen.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, SuccessGreen)
                        ) {
                            Text(
                                text = if (isArabic) "✓ مستوى متقدم AIIC" else "AIIC Ready",
                                color = SuccessGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // KPI Statistics Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Card 1: Completed Speeches
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🎯 الخطابات المنجزة", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text("$totalCompletedSpeeches", fontSize = 22.sp, fontWeight = FontWeight.Black, color = RedPrimary)
                                    Text("/$totalTargetSpeeches", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                LinearProgressIndicator(
                                    progress = { totalCompletedSpeeches / totalTargetSpeeches.toFloat() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp)
                                        .height(5.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = RedPrimary,
                                    trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                )
                            }
                        }

                        // Card 2: Current Average Décalage
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("⏱️ الفارق الزمني (Décalage)", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text("$overallAverageDecalage", fontSize = 22.sp, fontWeight = FontWeight.Black, color = SuccessGreen)
                                    Text(" ثوانٍ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                }
                                Text("المعيار الذهبي: 3.0s - 4.0s", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        // Card 3: Fluency & Precision
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("✨ الطلاقة والدقة", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(Modifier.height(4.dp))
                                Text("94.2%", fontSize = 22.sp, fontWeight = FontWeight.Black, color = GoldYellow)
                                Text("ساعات الكابينة: 38.5h", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }

        // Section 1: Visual Interactive Décalage Line Chart
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("decalage_chart_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "📈 " + if (isArabic) "رسم بياني لتطور الفارق الزمني (Décalage Timeline)" else "Décalage Evolution Over Time",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isArabic) "انخفاض زمن التأخير من 6.5s إلى 3.4s بفضل التدريب المركز على تقسيم الجمل (Chunking)" else "Progression from initial 6.5s hesitation to 3.4s international fluency",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        SuggestionChip(
                            onClick = {},
                            label = { Text("تحسن +47%", fontWeight = FontWeight.Bold, color = SuccessGreen) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = SuccessGreen.copy(alpha = 0.15f))
                        )
                    }

                    // Custom Canvas Chart
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Draw Target Golden Zone (3.0s to 4.0s)
                            val maxDecalage = 7.0f
                            val minDecalage = 2.5f
                            val range = maxDecalage - minDecalage

                            fun getY(v: Float): Float {
                                return h - ((v - minDecalage) / range * h)
                            }

                            val y4 = getY(4.0f)
                            val y3 = getY(3.0f)

                            // Green Benchmark Band
                            drawRect(
                                color = Color(0xFF10B981).copy(alpha = 0.15f),
                                topLeft = Offset(0f, y4),
                                size = androidx.compose.ui.geometry.Size(w, y3 - y4)
                            )

                            // Benchmark threshold line at 4.0s
                            drawLine(
                                color = Color(0xFF10B981).copy(alpha = 0.6f),
                                start = Offset(0f, y4),
                                end = Offset(w, y4),
                                strokeWidth = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )

                            // Horizontal Grid Lines
                            listOf(6.0f, 5.0f, 4.0f, 3.0f).forEach { sec ->
                                val yPos = getY(sec)
                                drawLine(
                                    color = Color.Gray.copy(alpha = 0.2f),
                                    start = Offset(0f, yPos),
                                    end = Offset(w, yPos),
                                    strokeWidth = 1f
                                )
                            }

                            // Plot Timeline Points & Connective Smooth Path
                            val points = decalageTimeline.mapIndexed { index, dp ->
                                val x = (index.toFloat() / (decalageTimeline.size - 1)) * (w - 40f) + 20f
                                val y = getY(dp.decalageSeconds)
                                Offset(x, y)
                            }

                            // Draw Area Gradient Fill
                            val path = Path().apply {
                                moveTo(points.first().x, h)
                                lineTo(points.first().x, points.first().y)
                                for (i in 1 until points.size) {
                                    val prev = points[i - 1]
                                    val curr = points[i]
                                    val cx = (prev.x + curr.x) / 2
                                    cubicTo(cx, prev.y, cx, curr.y, curr.x, curr.y)
                                }
                                lineTo(points.last().x, h)
                                close()
                            }

                            drawPath(
                                path = path,
                                brush = Brush.verticalGradient(
                                    colors = listOf(RedPrimary.copy(alpha = 0.35f), Color.Transparent),
                                    startY = 0f,
                                    endY = h
                                )
                            )

                            // Draw Line Stroke
                            val linePath = Path().apply {
                                moveTo(points.first().x, points.first().y)
                                for (i in 1 until points.size) {
                                    val prev = points[i - 1]
                                    val curr = points[i]
                                    val cx = (prev.x + curr.x) / 2
                                    cubicTo(cx, prev.y, cx, curr.y, curr.x, curr.y)
                                }
                            }

                            drawPath(
                                path = linePath,
                                color = RedPrimary,
                                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                            )

                            // Draw Circular Nodes
                            points.forEachIndexed { idx, pt ->
                                val isSelected = selectedPoint == decalageTimeline[idx]
                                drawCircle(
                                    color = if (isSelected) GoldYellow else Color.White,
                                    radius = if (isSelected) 7.dp.toPx() else 4.5.dp.toPx(),
                                    center = pt
                                )
                                drawCircle(
                                    color = if (isSelected) RedPrimary else Color(0xFF7F1D1D),
                                    radius = if (isSelected) 4.5.dp.toPx() else 3.dp.toPx(),
                                    center = pt
                                )
                            }
                        }
                    }

                    // Interactive Point Selector Chips
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(decalageTimeline) { pt ->
                            val isSel = selectedPoint == pt
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedPoint = pt },
                                color = if (isSel) RedPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = if (isSel) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "#${pt.speechNum}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${pt.decalageSeconds}s",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.5.sp,
                                        color = if (isSel) GoldYellow else SuccessGreen
                                    )
                                }
                            }
                        }
                    }

                    // Selected Point Detailed Inspector Card
                    selectedPoint?.let { pt ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = GoldYellow.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, GoldYellow.copy(alpha = 0.35f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "الخطاب رقم #${pt.speechNum}: ${pt.title}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.5.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Badge(containerColor = SuccessGreen.copy(alpha = 0.2f)) {
                                        Text("دقة: ${pt.fluencyScore}%", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    }
                                }

                                Spacer(Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                    Text("⏱️ الفارق: ${pt.decalageSeconds} ثوانٍ", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                                    Text("⚡ سرعة الإلقاء: ${pt.speedWpm} WPM", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("📅 ${pt.date}", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "💡 ملاحظات مقيّم الكابينة: ${pt.notes}",
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Domain Breakdown Progress
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "🌐 " + if (isArabic) "توزيع إنجاز الـ 100 خطاب بحسب التخصصات الخمسة" else "100 Speeches Domain Mastery",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    domainStats.forEach { stat ->
                        val pct = stat.completedCount / stat.totalCount.toFloat()
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(stat.icon, fontSize = 14.sp)
                                    Text(stat.nameAr, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "${stat.completedCount}/${stat.totalCount}",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = stat.color
                                    )
                                    Text(
                                        text = "متوسط الـ Décalage: ${stat.avgDecalage}s",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            LinearProgressIndicator(
                                progress = { pct },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = stat.color,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Section 3: Interactive 100 Speeches Grid & Roadmap
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🎙️ " + if (isArabic) "مصفوفة الـ 100 خطاب تدريبي (100 Speeches Matrix)" else "100 Speeches Master Matrix",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isArabic) "اختر أي خطاب للتدرب عليه في كابينة المحاكاة الفورية" else "Select any speech to launch immediate booth simulation",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = "${filteredSpeeches.size} خطاب",
                            fontWeight = FontWeight.ExtraBold,
                            color = RedPrimary,
                            fontSize = 13.sp
                        )
                    }

                    // Search & Domain Filter
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(if (isArabic) "ابحث عن عنوان الخطاب أو المتحدث..." else "Search speech title or speaker...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Domain Filter Chips
                    val domainsList = listOf("الكل", "دبلوماسي", "قانوني", "اقتصادي", "طبي", "مناخ")
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(domainsList) { d ->
                            val isSel = selectedDomainFilter == d
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedDomainFilter = d },
                                label = { Text(d, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) }
                            )
                        }
                    }

                    // Speeches List Preview (Showing top matching speeches)
                    filteredSpeeches.take(12).forEach { speech ->
                        val isDone = speech.num <= 54
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onLaunchBoothSpeech(speech.id) },
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, if (isDone) SuccessGreen.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
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
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isDone) SuccessGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = if (isDone) "✓" else "#${speech.num}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.5.sp,
                                                color = if (isDone) SuccessGreen else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }

                                    Column {
                                        Text(
                                            text = speech.titleAr,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "${speech.domainAr} • ${speech.speaker}",
                                            fontSize = 10.5.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Button(
                                    onClick = { onLaunchBoothSpeech(speech.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (isDone) SuccessGreen else RedPrimary),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (isDone) "إعادة التدريب" else "دخول الكابينة 🎙️", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
