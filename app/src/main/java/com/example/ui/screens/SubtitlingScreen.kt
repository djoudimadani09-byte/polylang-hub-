package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.SrtCue
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun SubtitlingScreen(
    isArabic: Boolean,
    cues: MutableList<SrtCue>,
    onExportSrt: () -> Unit
) {
    var selectedCueIndex by remember { mutableIntStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }

    // Dialog state for adding cue
    var newStart by remember { mutableStateOf("00:00:10,000") }
    var newEnd by remember { mutableStateOf("00:00:14,000") }
    var newSource by remember { mutableStateOf("") }
    var newSub by remember { mutableStateOf("") }

    val activeCue = if (cues.isNotEmpty() && selectedCueIndex in cues.indices) cues[selectedCueIndex] else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header & Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isArabic) "محرر الترجمة المرئية (SRT Studio)" else "SRT Subtitle Studio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isArabic) "معايير Netflix: 20 CPS / 42 CPL" else "Netflix Compliant: 20 CPS / 42 CPL",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { showAddDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.testTag("add_cue_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isArabic) "سطر جديد" else "Add Cue", fontSize = 12.sp)
                }

                Button(
                    onClick = onExportSrt,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                    modifier = Modifier.testTag("export_srt_btn")
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isArabic) "تصدير SRT" else "Export SRT", fontSize = 12.sp)
                }
            }
        }

        // Live Simulated Video Player Preview
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .testTag("video_preview_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Video Screen simulated content
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = activeCue?.let { "${it.start}  -->  ${it.end}" } ?: "00:00:00,000 --> 00:00:00,000",
                        color = GoldYellow,
                        fontSize = 11.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }

                // Subtitle Overlay at the bottom
                if (activeCue != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = activeCue.subtitleText.ifEmpty { activeCue.sourceText },
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Cues List
        Text(
            text = if (isArabic) "قائمة المقاطع والتوقيت (${cues.size})" else "Subtitle Cues (${cues.size})",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(cues) { index, cue ->
                val isSelected = index == selectedCueIndex
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCueIndex = index },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) RedPrimary else MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${cue.id}",
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${cue.start} ➔ ${cue.end}",
                                fontSize = 11.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = cue.sourceText,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Text(
                                text = cue.subtitleText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        IconButton(
                            onClick = {
                                if (cues.size > 1) {
                                    cues.removeAt(index)
                                    if (selectedCueIndex >= cues.size) {
                                        selectedCueIndex = cues.size - 1
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete cue",
                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Cue Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = if (isArabic) "إضافة مقطع ترجمة جديد" else "Add New Subtitle Cue",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newStart,
                            onValueChange = { newStart = it },
                            label = { Text(if (isArabic) "البداية" else "Start") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = newEnd,
                            onValueChange = { newEnd = it },
                            label = { Text(if (isArabic) "النهاية" else "End") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = newSource,
                        onValueChange = { newSource = it },
                        label = { Text(if (isArabic) "النص الأصلي" else "Source Text") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newSub,
                        onValueChange = { newSub = it },
                        label = { Text(if (isArabic) "نص الترجمة" else "Subtitle Text") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val nextId = (cues.maxOfOrNull { it.id } ?: 0) + 1
                        cues.add(
                            SrtCue(
                                id = nextId,
                                start = newStart,
                                end = newEnd,
                                sourceText = newSource.ifEmpty { "Source segment" },
                                subtitleText = newSub.ifEmpty { "Translated segment" }
                            )
                        )
                        newSource = ""
                        newSub = ""
                        showAddDialog = false
                    }
                ) {
                    Text(text = if (isArabic) "إضافة" else "Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(text = if (isArabic) "إلغاء" else "Cancel")
                }
            }
        )
    }
}
