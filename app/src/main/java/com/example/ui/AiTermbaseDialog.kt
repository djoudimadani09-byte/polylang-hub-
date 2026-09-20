package com.example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun AiTermbaseDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTermIndex by remember { mutableStateOf(0) }

    val terms = remember {
        listOf(
            Triple("Force Majeure", "القوة القاهرة / الحادث الفجائي (إعفاء تعاقدي وفق المادة 127 مدني)", "قانوني"),
            Triple("Indemnity & Hold Harmless", "التعويض وإبراء الذمة وحماية المتعاقد من مطالبات الغير", "عقود"),
            Triple("Liquidated Damages", "التعويض الاتفاقي والشرط الجزائي محدد القيمة مسبقاً", "مالي وقانوني"),
            Triple("Ultra Vires", "تجاوز الصلاحيات القانونية والتصرف خارج نطاق الاختصاص", "قضائي"),
            Triple("Informed Consent", "الموافقة المستنيرة السريرية المكتوبة للمريض", "طبي ودوائي"),
            Triple("Pharmacovigilance", "اليقظة والرصد الدوائي ومأمونية المستحضرات السريرية", "طبي")
        )
    }

    val filtered = remember(searchQuery) {
        if (searchQuery.isBlank()) terms
        else terms.filter { it.first.contains(searchQuery, true) || it.second.contains(searchQuery, true) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .testTag("termbase_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Text(
                        text = if (isArabic) "مساعد الترجمة الذكي (Polylang AI)" else "Polylang AI Terminology",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(if (isArabic) "ابحث في مسرد المصطلحات المحلفة..." else "Search certified terms...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().testTag("termbase_search_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 280.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filtered.forEachIndexed { idx, (en, ar, cat) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTermIndex = idx },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedTermIndex == idx) RedPrimary.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(en, fontWeight = FontWeight.Bold, color = RedPrimary, fontSize = 14.sp)
                                    SuggestionChip(
                                        onClick = {},
                                        label = { Text(cat, fontSize = 10.sp) },
                                        modifier = Modifier.height(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(ar, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Text(if (isArabic) "إغلاق المساعد" else "Close")
                }
            }
        }
    }
}
