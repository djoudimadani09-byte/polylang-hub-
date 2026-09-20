package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.AdminEmailNotifier
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun CertificateDialog(
    isArabic: Boolean,
    studentName: String,
    courseTitle: String,
    onDismiss: () -> Unit
) {
    val certId = remember { "PL-CERT-2026-${(1000..9999).random()}" }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .wrapContentHeight()
                .padding(vertical = 14.dp)
                .testTag("certificate_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Text(
                        text = if (isArabic) "شهادة إتمام معتمدة رسمياً" else "Official Verified Certificate",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Ornate Certificate Paper Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, GoldYellow), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Seal icon
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(GoldYellow.copy(alpha = 0.2f))
                                .border(1.5.dp, GoldYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = RedDark,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Text(
                            text = "POLYLANG ACADEMY OF TRANSLATION",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedDark,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = if (isArabic) "📜 شهادة تأهيل وتفوق مهني معتمدة" else "Certificate of Professional Mastery",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (isArabic) "يشهد المجلس الأكاديمي لمنصة بوليلانغ بأن الأستاذ(ة):" else "This is to certify that:",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569)
                        )

                        Text(
                            text = studentName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = RedPrimary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (isArabic) "قد أتم بنجاح متطلبات المسار التخصصي والتدريب العملي في:" else "Has successfully completed the masterclass curriculum in:",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "« $courseTitle »",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A),
                            textAlign = TextAlign.Center
                        )

                        Divider(color = GoldYellow.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 4.dp))

                        // Signatories
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("د. ليلى مزياني", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E293B))
                                Text("مترجم محلف ومعتمد", fontSize = 10.sp, color = Color(0xFF64748B))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("أ. مداني جودي", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E293B))
                                Text("المشرف العام للمنصة", fontSize = 10.sp, color = Color(0xFF64748B))
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Serial: $certId • ISO 17100:2015 Accredited",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                // Download / Share Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isArabic) "إغلاق" else "Close")
                    }

                    Button(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "إصدار شهادة معتمدة",
                                userName = studentName,
                                userEmail = AdminEmailNotifier.ADMIN_EMAIL,
                                details = mapOf("certId" to certId, "course" to courseTitle)
                            )
                            onDismiss()
                        },
                        modifier = Modifier.weight(2f).height(44.dp).testTag("cert_download_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "حفظ وتوثيق الشهادة" else "Save Certificate")
                    }
                }
            }
        }
    }
}
