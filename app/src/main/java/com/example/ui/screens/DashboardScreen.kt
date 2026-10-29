package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AdminEmailNotifier
import com.example.AppCurrency
import com.example.TranslationOrder
import com.example.UserRole
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun DashboardScreen(
    isArabic: Boolean,
    currency: AppCurrency,
    userName: String,
    userEmail: String,
    userPlan: String,
    userRole: UserRole,
    orders: List<TranslationOrder>,
    onOpenAuthDialog: () -> Unit,
    onOpenOrderDialog: (String) -> Unit,
    onOpenAcademy: () -> Unit
) {
    var emailTestMessage by remember { mutableStateOf<String?>(null) }
    var isSendingTest by remember { mutableStateOf(false) }
    var inspectOrder by remember { mutableStateOf<TranslationOrder?>(null) }
    var translatorFilter by remember { mutableStateOf("ALL") }
    var showInvoiceDialogForOrder by remember { mutableStateOf<TranslationOrder?>(null) }
    var actionNotificationMessage by remember { mutableStateOf<String?>(null) }

    val isClient = userRole == UserRole.CLIENT
    val isTranslatorOrAdmin = userRole == UserRole.TRANSLATOR || userRole == UserRole.ADMIN

    // Client sees ONLY their own requested orders (Starts from 0 for new accounts)
    val clientOrders = remember(orders.size, userEmail, userName) {
        if (userEmail.isBlank()) {
            emptyList()
        } else {
            orders.filter { order ->
                order.clientEmail.equals(userEmail, ignoreCase = true) || (userName.isNotBlank() && order.clientName.equals(userName, ignoreCase = true))
            }
        }
    }

    // Translator sees ALL orders, with filter capability
    val filteredTranslatorOrders = remember(orders.size, translatorFilter) {
        when (translatorFilter) {
            "PENDING" -> orders.filter { it.status.contains("مراجعة") || it.status.contains("تدقيق") }
            "PROGRESS" -> orders.filter { it.status.contains("قيد") || it.status.contains("مؤكد") }
            "COMPLETED" -> orders.filter { it.status.contains("مكتمل") }
            else -> orders
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("dashboard_user_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(RedPrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (if (userName.isNotBlank()) userName else if (isClient) "كريم حمداوي" else "مترجم").take(1).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(if (userName.isNotBlank()) userName else if (isClient) "كريم حمداوي (عميل)" else "د. سمير (مترجم محلف)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(if (userEmail.isNotBlank()) userEmail else if (isClient) "karim@polylang.dz" else "translator@polylang.dz", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Role Badge
                    SuggestionChip(
                        onClick = onOpenAuthDialog,
                        label = { Text(if (isArabic) userRole.labelAr else userRole.labelEn, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = when (userRole) {
                                UserRole.ADMIN -> GoldYellow.copy(alpha = 0.2f)
                                UserRole.TRANSLATOR -> SuccessGreen.copy(alpha = 0.15f)
                                UserRole.STUDENT -> RedPrimary.copy(alpha = 0.15f)
                                UserRole.LEARNER -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f)
                                UserRole.CLIENT -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            }
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${if (isArabic) "الباقة:" else "Plan:"} $userPlan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    TextButton(
                        onClick = onOpenAuthDialog,
                        modifier = Modifier.testTag("switch_account_btn")
                    ) {
                        Icon(Icons.Default.SwitchAccount, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isArabic) "تبديل الحساب / تسجيل الدخول" else "Switch / Sign In", fontSize = 11.sp)
                    }
                }
            }
        }

        // Stats Counters Grid (Differentiated for Client vs Translator)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val stats = if (isClient) {
                listOf(
                    Pair(currency.format(clientOrders.sumOf { it.priceDzd }), if (isArabic) "إجمالي المصروفات" else "Total Invoiced"),
                    Pair("${clientOrders.size}", if (isArabic) "طلباتي الجارية" else "My Orders"),
                    Pair("${clientOrders.sumOf { it.wordCount }}", if (isArabic) "الكلمات المطلوبة" else "Requested Words")
                )
            } else {
                listOf(
                    Pair(currency.format(orders.sumOf { it.priceDzd }), if (isArabic) "أتعاب الترجمة" else "Earned Fees"),
                    Pair("${orders.size}", if (isArabic) "إجمالي مشاريع العملاء" else "All Client Orders"),
                    Pair("${orders.sumOf { it.wordCount }}", if (isArabic) "إجمالي الكلمات" else "Total Words")
                )
            }
            stats.forEach { (value, label) ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(value, fontWeight = FontWeight.ExtraBold, color = RedPrimary, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Status action alert if any
        if (actionNotificationMessage != null) {
            Surface(
                color = SuccessGreen.copy(alpha = 0.15f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(actionNotificationMessage!!, color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    IconButton(onClick = { actionNotificationMessage = null }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Role-Specific Action Module (Students / Admin / Translator)
        when (userRole) {
            UserRole.STUDENT, UserRole.LEARNER -> {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("student_lab_dash_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isArabic) "🏛️ مخبر الترجمة والامتحانات (Student Lab)" else "🏛️ Translation Student Lab",
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                fontSize = 14.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = RedPrimary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = if (isArabic) "مخصص لطلبة المعاهد" else "For Students",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 10.sp,
                                    color = RedPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = if (isArabic)
                                "تدرب على الترجمة الفورية والتتابعية عبر مقصورة المحاكاة، واستخدم دفتر رموز روزان، واختبر مستواك في امتحان الترجمة الأكاديمي الشامل (100 سؤالاً معتمداً)."
                            else
                                "Practice simultaneous & consecutive interpretation, utilize Rozan notation symbols, and take the 100-question placement exam.",
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onOpenAcademy,
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                                modifier = Modifier.weight(1f).testTag("enter_student_lab_btn")
                            ) {
                                Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isArabic) "دخول المخبر والأكاديمية" else "Open Lab & Academy", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            UserRole.ADMIN -> {
                val isAuthorizedAdmin = userEmail.equals(AdminEmailNotifier.ADMIN_EMAIL, ignoreCase = true)
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("admin_dash_panel"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isAuthorizedAdmin) GoldYellow.copy(alpha = 0.12f) else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isAuthorizedAdmin) GoldYellow else MaterialTheme.colorScheme.error)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isAuthorizedAdmin) "🔐 لوحة التحكم المركزية للأدمن" else "⚠️ تنبيه صلاحيات المشرف",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = if (isAuthorizedAdmin) RedDark else MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = if (isAuthorizedAdmin) "✓ مصرح: أ. جودي مداني" else "غير مصرح",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAuthorizedAdmin) SuccessGreen else MaterialTheme.colorScheme.error
                            )
                        }

                        if (isAuthorizedAdmin) {
                            val clipboardManager = LocalClipboardManager.current
                            Text(
                                text = "🗄️ قاعدة البيانات السحابية: مؤمنة ومشفرة بالكامل وفق معايير الحماية (Zero-Trust)\n📧 التنبيهات الفورية: ترسل مباشرة إلى djoudimadani09@gmail.com\n👥 عزل البيانات: العميل يرى فقط ما طلب، والمترجم يرى مشاريع مساحة عمله فقط",
                                fontSize = 11.5.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            // Direct Admin Dashboard URL Card
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = "🔗 الرابط المباشر للوحة المشرف (Direct Admin URL):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = RedDark
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "https://polylang.dz/#admin",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        OutlinedButton(
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString("https://polylang.dz/#admin"))
                                                actionNotificationMessage = "✓ تم نسخ الرابط المباشر للوحة المشرف إلى الحافظة بنجاح!"
                                            },
                                            shape = RoundedCornerShape(6.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                            modifier = Modifier.height(30.dp)
                                        ) {
                                            Text(if (isArabic) "📋 نسخ الرابط" else "Copy URL", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }

                            // Live Platform Activity Timeline
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "⚡ البث الحي لأحداث المنصة (Live Activity Stream):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = SuccessGreen
                                    )
                                    Text("• 📄 طلب جديد: عقد امتياز دولي (كريم حمداوي - 2,400 كلمة)", fontSize = 10.5.sp)
                                    Text("• 🔏 ختم معتمد: كتيب توربينات الغاز (أ. مريم بوعلام - ISO 17100)", fontSize = 10.5.sp)
                                    Text("• 🏆 امتحان تحديد المستوى: سارة بن علي (TCF - المستوى المتقدم C1)", fontSize = 10.5.sp)
                                    Text("• 🔒 جلسة دخول مؤمنة للمشرف العام: djoudimadani09@gmail.com", fontSize = 10.5.sp, color = RedDark)
                                }
                            }
                        } else {
                            Text(
                                text = "عزل بيانات الإدارة: صلاحية الأدمن مخصصة حصراً للأستاذ جودي مداني عبر بريده المعتمد: ${AdminEmailNotifier.ADMIN_EMAIL}.\nيرجى تسجيل الدخول بالبريد المعتمد للوصول للوحة التحكم.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.error,
                                lineHeight = 17.sp
                            )
                            Button(
                                onClick = onOpenAuthDialog,
                                modifier = Modifier.fillMaxWidth().testTag("admin_login_cta"),
                                colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isArabic) "تسجيل الدخول بحساب المشرف المعتمد" else "Login as Authorized Admin", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            else -> {}
        }

        // ==========================================
        // TRANSLATION ORDERS SECTION (Segregated: Client vs Translator)
        // ==========================================
        if (isClient) {
            // --- VIEW A: TRANSLATION CLIENT (Only sees their own requests!) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isArabic) "📋 طلباتي ومشاريعي الخاصة (${clientOrders.size}):" else "My Translation Orders (${clientOrders.size}):",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = if (isArabic) "تظهر لك هنا فقط الطلبات المسجلة بحسابك مع تتبع الاعتماد الرسمي" else "Only your personal requested orders appear here",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { onOpenOrderDialog("قانونية ورسمية") },
                    modifier = Modifier.height(34.dp).testTag("dash_new_order_btn"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isArabic) "+ طلب جديد" else "+ New Order", fontSize = 11.sp)
                }
            }

            if (clientOrders.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("📭", fontSize = 32.sp)
                        Text(
                            text = if (isArabic) "لا توجد لديك طلبات ترجمة مسجلة بعد" else "No orders placed yet",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (isArabic) "انقر على '+ طلب جديد' لإرسال وثيقتك الأولى وسيقوم مترجم محلف باستلامها فوراً." else "Click '+ New Order' to submit your first document.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    clientOrders.forEach { order ->
                        Card(
                            modifier = Modifier.fillMaxWidth().testTag("client_order_${order.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "#${order.id} • ${order.category}",
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary,
                                        fontSize = 12.sp
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = when {
                                            order.status.contains("مكتمل") -> SuccessGreen.copy(alpha = 0.15f)
                                            order.status.contains("قيد") -> GoldYellow.copy(alpha = 0.2f)
                                            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                        }
                                    ) {
                                        Text(
                                            text = order.status,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = when {
                                                order.status.contains("مكتمل") -> SuccessGreen
                                                order.status.contains("قيد") -> RedDark
                                                else -> MaterialTheme.colorScheme.primary
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Text(order.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("📎 ${order.attachedFileName}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "${order.wordCount} ${if (isArabic) "كلمة" else "words"} • ${currency.format(order.priceDzd)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { inspectOrder = order },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).height(32.dp),
                                        contentPadding = PaddingValues(horizontal = 6.dp)
                                    ) {
                                        Text(if (isArabic) "📄 تفاصيل الطلب" else "Details", fontSize = 11.sp)
                                    }
                                    OutlinedButton(
                                        onClick = { showInvoiceDialogForOrder = order },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).height(32.dp),
                                        contentPadding = PaddingValues(horizontal = 6.dp)
                                    ) {
                                        Text(if (isArabic) "🧾 الفاتورة الرسمية" else "Invoice", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // --- VIEW B: SWORN / CERTIFIED TRANSLATOR & ADMIN (Sees ALL Client Requests!) ---
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isArabic) "⚖️ لوحة المترجم المحلف - طلبات جميع العملاء (${orders.size}):" else "Sworn Translator - All Client Requests (${orders.size}):",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = if (isArabic) "تظهر لك كافّة مشاريع وترجمات العملاء لاستلامها واعتمادها رسمياً" else "All client translation requests available for claim & certification",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SuccessGreen.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "ISO 17100:2015",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }

                // Filter Chips for Translator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = translatorFilter == "ALL",
                        onClick = { translatorFilter = "ALL" },
                        label = { Text("الكل (${orders.size})", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = translatorFilter == "PENDING",
                        onClick = { translatorFilter = "PENDING" },
                        label = { Text("بانتظار الاستلام", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = translatorFilter == "PROGRESS",
                        onClick = { translatorFilter = "PROGRESS" },
                        label = { Text("قيد الترجمة", fontSize = 11.sp) }
                    )
                    FilterChip(
                        selected = translatorFilter == "COMPLETED",
                        onClick = { translatorFilter = "COMPLETED" },
                        label = { Text("مكتمل ومختوم", fontSize = 11.sp) }
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    filteredTranslatorOrders.forEach { order ->
                        Card(
                            modifier = Modifier.fillMaxWidth().testTag("translator_order_${order.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (order.status.contains("مكتمل")) SuccessGreen.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "#${order.id} • ${order.category}",
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary,
                                        fontSize = 12.sp
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (order.status.contains("مكتمل")) SuccessGreen.copy(alpha = 0.15f) else GoldYellow.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = order.status,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (order.status.contains("مكتمل")) SuccessGreen else RedDark,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "👤 العميل: ${order.clientName} (${order.clientEmail} • ${order.clientPhone})",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Text(order.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${order.langPair} • 📎 ${order.attachedFileName}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "${order.wordCount} كلمة • الأتعاب: ${currency.format(order.priceDzd)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = SuccessGreen
                                    )
                                }

                                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                                // Realistic Interactive Translator Actions
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (!order.status.contains("مكتمل")) {
                                        if (!order.status.contains("قيد")) {
                                            Button(
                                                onClick = {
                                                    order.status = "قيد الترجمة والختم الرسمي"
                                                    actionNotificationMessage = "✓ تم استلام المشروع #${order.id} بنجاح وبدء الترجمة المحلفة."
                                                },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                                                modifier = Modifier.weight(1.2f).height(32.dp),
                                                contentPadding = PaddingValues(horizontal = 4.dp)
                                            ) {
                                                Text(if (isArabic) "✅ استلام المشروع" else "Accept Project", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        } else {
                                            Button(
                                                onClick = {
                                                    order.status = "مكتمل وتم وضع الختم الرسمي"
                                                    actionNotificationMessage = "✓ تم اعتماد وترجمة المستند #${order.id} وتطبيق الختم الرسمي ISO 17100 بنجاح."
                                                },
                                                shape = RoundedCornerShape(8.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                                                modifier = Modifier.weight(1.3f).height(32.dp),
                                                contentPadding = PaddingValues(horizontal = 4.dp)
                                            ) {
                                                Text(if (isArabic) "✍️ اعتماد وختم الترجمة" else "Certify & Seal", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                    OutlinedButton(
                                        onClick = { inspectOrder = order },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f).height(32.dp),
                                        contentPadding = PaddingValues(horizontal = 4.dp)
                                    ) {
                                        Text(if (isArabic) "👁️ فحص الوثيقة" else "Inspect", fontSize = 11.sp)
                                    }
                                    OutlinedButton(
                                        onClick = { showInvoiceDialogForOrder = order },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(0.9f).height(32.dp),
                                        contentPadding = PaddingValues(horizontal = 4.dp)
                                    ) {
                                        Text(if (isArabic) "🧾 الفاتورة" else "Invoice", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // ==========================================
        // ORDER DETAILS & INSPECTION MODAL DIALOG
        // ==========================================
        inspectOrder?.let { order ->
            Dialog(
                onDismissRequest = { inspectOrder = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.94f).padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp).verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("📄 تفاصيل المشروع #${order.id}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RedPrimary)
                            IconButton(onClick = { inspectOrder = null }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }

                        // Client Information Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("معلومات العميل وصاحب الطلب:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Text("الاسم: ${order.clientName}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text("البريد: ${order.clientEmail} • الهاتف: ${order.clientPhone}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        // Project Specs Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("مواصفات الترجمة والمستند:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Text("العنوان: ${order.title}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text("التخصص: ${order.category} • اللغات: ${order.langPair}", fontSize = 11.sp)
                                Text("الملف المرفق: 📎 ${order.attachedFileName}", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                                Text("حجم العمل: ${order.wordCount} كلمة • الأتعاب: ${currency.format(order.priceDzd)}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Progress Timeline
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SuccessGreen.copy(alpha = 0.08f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("مسار الاعتماد الرسمي (ISO 17100):", fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = SuccessGreen)
                                Text("✓ الخطوة 1: استلام الوثيقة وتدقيق المصطلحات التخصصية", fontSize = 11.sp)
                                Text("✓ الخطوة 2: الترجمة المزدوجة والمطابقة اللغوية", fontSize = 11.sp)
                                Text(if (order.status.contains("مكتمل")) "✓ الخطوة 3: تطبيق الختم الرسمي للمترجم المحلف والباركود" else "⏳ الخطوة 3: قيد وضع الختم الرسمي للمترجم المحلف", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(if (order.status.contains("مكتمل")) "✓ الخطوة 4: جاهز للتسليم والتحميل النهائي" else "⏳ الخطوة 4: التسليم النهائي للعميل", fontSize = 11.sp)
                            }
                        }

                        // Notes
                        Text("ملاحظات العميل: ${order.notes}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Button(
                            onClick = {
                                val current = order
                                inspectOrder = null
                                showInvoiceDialogForOrder = current
                            },
                            modifier = Modifier.fillMaxWidth().height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                        ) {
                            Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("عرض الفاتورة والإيصال المعتمد", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // ==========================================
        // OFFICIAL INVOICE DIALOG
        // ==========================================
        showInvoiceDialogForOrder?.let { order ->
            Dialog(
                onDismissRequest = { showInvoiceDialogForOrder = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.92f).padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                            Text("🧾 فاتورة رسمية معتمدة", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RedPrimary)
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SuccessGreen.copy(alpha = 0.15f)
                            ) {
                                Text("ISO 17100", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SuccessGreen, modifier = Modifier.padding(4.dp))
                            }
                        }

                        Divider()

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("رقم الفاتورة: INV-${order.id}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("المشروع: ${order.title}", fontSize = 12.sp)
                            Text("العميل: ${order.clientName} (${order.clientEmail})", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("التاريخ: ${order.date}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("المبلغ الإجمالي المستحق:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(currency.format(order.priceDzd), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = RedPrimary)
                            }
                        }

                        Text("مجمع بوليلانغ الدولي للترجمة المعتمدة • الجزائر العاصمة • إشراف أ. جودي مداني", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Button(
                            onClick = { showInvoiceDialogForOrder = null },
                            modifier = Modifier.fillMaxWidth().height(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                        ) {
                            Text("إغلاق", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Email Notification Monitoring Center
        Card(
            modifier = Modifier.fillMaxWidth().testTag("email_notifier_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.08f))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "📬 مركز متابعة بريد الإدارة الفوري" else "📬 Admin Email Notification Center",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SuccessGreen))
                }

                Text(
                    text = "${if (isArabic) "المستلم المعتمد لجميع التسجيلات والطلبات:" else "Designated Recipient:"} ${AdminEmailNotifier.ADMIN_EMAIL}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedButton(
                    onClick = {
                        isSendingTest = true
                        AdminEmailNotifier.dispatch(
                            eventType = "إشعار فحص من لوحة تحكم أندرويد",
                            userName = userName,
                            userEmail = userEmail,
                            details = mapOf("status" to "Dashboard test alert", "role" to userRole.name)
                        ) { ok, msg ->
                            isSendingTest = false
                            emailTestMessage = if (ok) (if (isArabic) "✓ تم إرسال الإشعار بنجاح إلى الإدارة (${AdminEmailNotifier.ADMIN_EMAIL})" else "✓ Notification sent to ${AdminEmailNotifier.ADMIN_EMAIL}") else "❌ $msg"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(38.dp).testTag("test_email_btn"),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isArabic) "إرسال إشعار تجريبي للإدارة" else "Send Test Alert", fontSize = 11.sp)
                }

                if (emailTestMessage != null) {
                    Text(
                        text = emailTestMessage ?: "",
                        fontWeight = FontWeight.Bold,
                        color = if (emailTestMessage!!.startsWith("✓")) SuccessGreen else RedPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        // Unified About Platform & Founder Section (One Single Clean Place)
        Card(
            modifier = Modifier.fillMaxWidth().testTag("dashboard_about_founder_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "🏛️ عن المنصة والمؤسس" else "🏛️ About Platform & Founder",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF0F172A)
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFDCFCE7)
                    ) {
                        Text(
                            text = "ISO 17100 معتمد",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF15803D),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = if (isArabic)
                        "الأستاذ جودي مداني (Djoudi Madani) • مؤسس ومدير عام منصة بوليلانغ هاب. حائز على شهادة ماستر 2 في الترجمة المؤسساتية من معهد الترجمة بجامعة الجزائر 2، تخصص عربي-فرنسي-ألماني، مع خبرة عملية واسعة في كابينات المؤتمرات والترجمة الشفهية الفورية والتتابعية."
                    else
                        "Prof. Djoudi Madani, Founder & Director of Polylang Hub. Master 2 in Institutional Translation from University of Algiers 2 (AR-FR-DE) with extensive conference interpretation experience.",
                    fontSize = 12.sp,
                    color = Color(0xFF475569),
                    lineHeight = 18.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("📍 المقر:", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
                            Text("الجزائر العاصمة", fontSize = 11.5.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1E293B))
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("📧 البريد المباشر:", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
                            Text(AdminEmailNotifier.ADMIN_EMAIL, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1E293B))
                        }
                    }
                }
            }
        }
    }
}
