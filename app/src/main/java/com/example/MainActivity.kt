package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.*
import com.example.ui.screens.*
import com.example.ui.theme.PolylangTheme
import com.example.ui.theme.RedPrimary
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolylangTheme {
                PolylangHubApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolylangHubApp() {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigation & Localization States
    var currentTab by remember { mutableStateOf(AppTab.HOME) }
    var isArabic by remember { mutableStateOf(true) }
    var currentCurrency by remember { mutableStateOf(AppCurrency.DZD) }

    // User Session State
    var userName by remember { mutableStateOf("كريم حمداوي") }
    var userEmail by remember { mutableStateOf("karim@polylang.dz") }
    var userRole by remember { mutableStateOf(UserRole.CLIENT) }
    var userPlan by remember { mutableStateOf("Pro Translator (معتمد)") }

    // Data State
    val allCourses = remember { CourseData.sampleCourses }
    val orders = remember {
        mutableStateListOf(
            TranslationOrder("PL-8421", "عقد امتياز واستثمار دولي", "قانونية ورسمية", "الإنجليزية ⇄ العربية", 2400, 8400, "قيد التدقيق المحلف", false),
            TranslationOrder("PL-9032", "كتيب مواصفات توربينات الغاز", "تقنية وهندسية", "الفرنسية ⇄ العربية", 1850, 7030, "مكتمل وتم التسليم", false),
            TranslationOrder("PL-3114", "كابينة الترجمة لقمة الطاقة بالجزائر", "ترجمة فورية للمؤتمرات", "الإنجليزية ⇄ الفرنسية", 0, 45000, "مؤكد ومجدول", true)
        )
    }

    val subtitleCues = remember {
        mutableStateListOf(
            SrtCue(1, "00:00:01,000", "00:00:04,200", "Welcome to Polylang Hub official platform.", "مرحباً بكم في منصة بوليلانغ هاب الرسمية."),
            SrtCue(2, "00:00:04,500", "00:00:08,100", "We provide certified sworn translations under ISO 17100.", "نقدم خدمات الترجمة المحلفة المعتمدة وفق معيار ISO 17100."),
            SrtCue(3, "00:00:08,300", "00:00:12,500", "Simultaneous interpretation and professional masterclasses.", "ترجمة فورية للمؤتمرات ومساقات تدريبية احترافية.")
        )
    }

    // Dialog Visibility States
    var showAuthDialog by remember { mutableStateOf(false) }
    var activeCourseForViewer by remember { mutableStateOf<MasterclassCourse?>(null) }
    var showOrderDialog by remember { mutableStateOf(false) }
    var initialOrderServiceType by remember { mutableStateOf("قانونية ورسمية") }
    var showPaymentDialog by remember { mutableStateOf(false) }
    var pendingPaymentPlan by remember { mutableStateOf("Pro Translator") }
    var pendingPaymentPriceDzd by remember { mutableStateOf(4500) }
    var showCertificateDialog by remember { mutableStateOf(false) }
    var certificateCourseTitle by remember { mutableStateOf("الترجمة القانونية وصياغة العقود الدولية") }
    var showTermbaseDialog by remember { mutableStateOf(false) }

    // Currency Menu
    var showCurrencyMenu by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Polylang Hub",
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary,
                            style = MaterialTheme.typography.titleLarge
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text("ISO 17100", fontSize = 9.sp, fontWeight = FontWeight.Bold) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = RedPrimary.copy(alpha = 0.1f),
                                labelColor = RedPrimary
                            )
                        )
                    }
                },
                actions = {
                    // Currency Selector
                    Box {
                        TextButton(
                            onClick = { showCurrencyMenu = true },
                            modifier = Modifier.testTag("currency_selector_btn")
                        ) {
                            Text(currentCurrency.code, fontWeight = FontWeight.Bold, color = RedPrimary, fontSize = 12.sp)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = RedPrimary)
                        }
                        DropdownMenu(
                            expanded = showCurrencyMenu,
                            onDismissRequest = { showCurrencyMenu = false }
                        ) {
                            AppCurrency.values().forEach { curr ->
                                DropdownMenuItem(
                                    text = { Text("${curr.code} (${curr.symbol})") },
                                    onClick = {
                                        currentCurrency = curr
                                        showCurrencyMenu = false
                                    }
                                )
                            }
                        }
                    }

                    // Language Toggle
                    IconButton(
                        onClick = { isArabic = !isArabic },
                        modifier = Modifier.testTag("lang_toggle_btn")
                    ) {
                        Text(if (isArabic) "EN" else "عربي", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedPrimary)
                    }

                    // Account / Login Button
                    IconButton(
                        onClick = { showAuthDialog = true },
                        modifier = Modifier.testTag("top_auth_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "User Account & Login",
                            tint = RedPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                // Home
                NavigationBarItem(
                    selected = currentTab == AppTab.HOME,
                    onClick = { currentTab = AppTab.HOME },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(if (isArabic) "الرئيسية" else "Home", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_home")
                )
                // Services
                NavigationBarItem(
                    selected = currentTab == AppTab.SERVICES,
                    onClick = { currentTab = AppTab.SERVICES },
                    icon = { Icon(Icons.Default.Translate, contentDescription = null) },
                    label = { Text(if (isArabic) "الخدمات" else "Services", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_services")
                )
                // Subtitles
                NavigationBarItem(
                    selected = currentTab == AppTab.SUBTITLING,
                    onClick = { currentTab = AppTab.SUBTITLING },
                    icon = { Icon(Icons.Default.Subtitles, contentDescription = null) },
                    label = { Text(if (isArabic) "SRT مرئية" else "SRT Studio", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_subtitles")
                )
                // Academy
                NavigationBarItem(
                    selected = currentTab == AppTab.ACADEMY,
                    onClick = { currentTab = AppTab.ACADEMY },
                    icon = { Icon(Icons.Default.School, contentDescription = null) },
                    label = { Text(if (isArabic) "الأكاديمية" else "Academy", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_academy")
                )
                // Pricing
                NavigationBarItem(
                    selected = currentTab == AppTab.PRICING,
                    onClick = { currentTab = AppTab.PRICING },
                    icon = { Icon(Icons.Default.Sell, contentDescription = null) },
                    label = { Text(if (isArabic) "الأسعار" else "Pricing", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_pricing")
                )
                // Dashboard
                NavigationBarItem(
                    selected = currentTab == AppTab.DASHBOARD,
                    onClick = { currentTab = AppTab.DASHBOARD },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text(if (isArabic) "حسابي" else "Profile", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_dashboard")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME -> HomeScreen(
                    isArabic = isArabic,
                    onNavigate = { currentTab = it },
                    onOpenOrderDialog = { service ->
                        initialOrderServiceType = service
                        showOrderDialog = true
                    },
                    onOpenAuthDialog = { showAuthDialog = true }
                )
                AppTab.SERVICES -> ServicesScreen(
                    isArabic = isArabic,
                    currency = currentCurrency,
                    onOpenOrderDialog = { service ->
                        initialOrderServiceType = service
                        showOrderDialog = true
                    },
                    onOpenTermbase = { showTermbaseDialog = true }
                )
                AppTab.SUBTITLING -> SubtitlingScreen(
                    isArabic = isArabic,
                    cues = subtitleCues,
                    onExportSrt = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = if (isArabic) "✓ تم تجهيز وتصدير ملف Subtitles.srt بنجاح" else "✓ Subtitles.srt successfully exported"
                            )
                        }
                    }
                )
                AppTab.ACADEMY -> AcademyScreen(
                    isArabic = isArabic,
                    courses = allCourses,
                    onOpenCourse = { course ->
                        // User clicked on course content -> open viewer!
                        activeCourseForViewer = course
                    },
                    onClaimCertificate = { title ->
                        certificateCourseTitle = title
                        showCertificateDialog = true
                    }
                )
                AppTab.PRICING -> PricingScreen(
                    isArabic = isArabic,
                    currency = currentCurrency,
                    onSelectPlan = { plan, price ->
                        pendingPaymentPlan = plan
                        pendingPaymentPriceDzd = price
                        showPaymentDialog = true
                    }
                )
                AppTab.DASHBOARD -> DashboardScreen(
                    isArabic = isArabic,
                    currency = currentCurrency,
                    userName = userName,
                    userEmail = userEmail,
                    userPlan = userPlan,
                    userRole = userRole,
                    orders = orders,
                    onOpenAuthDialog = { showAuthDialog = true },
                    onOpenOrderDialog = { service ->
                        initialOrderServiceType = service
                        showOrderDialog = true
                    },
                    onOpenAcademy = { currentTab = AppTab.ACADEMY }
                )
            }
        }
    }

    // 1. Auth Dialog (Sign In / Register)
    if (showAuthDialog) {
        AuthDialog(
            isArabic = isArabic,
            onDismiss = { showAuthDialog = false },
            onLoginSuccess = { name, email, role ->
                userName = name
                userEmail = email
                userRole = role
                showAuthDialog = false
                currentTab = AppTab.DASHBOARD
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isArabic) "مرحباً بك يا $name! تم تسجيل الدخول بصلاحية: ${role.labelAr}" else "Welcome $name! Logged in as: ${role.name}"
                    )
                }
            }
        )
    }

    // 2. Course Viewer Dialog (Content viewer)
    activeCourseForViewer?.let { course ->
        CourseViewerDialog(
            course = course,
            isArabic = isArabic,
            onDismiss = { activeCourseForViewer = null },
            onClaimCertificate = { title ->
                certificateCourseTitle = title
                showCertificateDialog = true
            }
        )
    }

    // 3. Order Service Dialog
    if (showOrderDialog) {
        OrderServiceDialog(
            initialServiceType = initialOrderServiceType,
            currency = currentCurrency,
            isArabic = isArabic,
            userName = userName,
            userEmail = userEmail,
            onDismiss = { showOrderDialog = false },
            onSubmitOrder = { newOrder ->
                orders.add(0, newOrder)
                showOrderDialog = false
                currentTab = AppTab.DASHBOARD
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isArabic) "✓ تم تأكيد وإرسال طلب الترجمة #${newOrder.id} بنجاح!" else "✓ Order #${newOrder.id} placed successfully!"
                    )
                }
            }
        )
    }

    // 4. Payment / Subscription Dialog
    if (showPaymentDialog) {
        PaymentDialog(
            isArabic = isArabic,
            currency = currentCurrency,
            planName = pendingPaymentPlan,
            priceDzd = pendingPaymentPriceDzd,
            userName = userName,
            userEmail = userEmail,
            onDismiss = { showPaymentDialog = false },
            onPaymentSuccess = {
                userPlan = pendingPaymentPlan
                showPaymentDialog = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isArabic) "✓ تم تأكيد عملية الدفع وتفعيل باقة $pendingPaymentPlan بنجاح!" else "✓ Plan $pendingPaymentPlan activated successfully!"
                    )
                }
            }
        )
    }

    // 5. Official Certificate Dialog
    if (showCertificateDialog) {
        CertificateDialog(
            isArabic = isArabic,
            studentName = userName,
            courseTitle = certificateCourseTitle,
            onDismiss = { showCertificateDialog = false }
        )
    }

    // 6. AI Termbase Dialog
    if (showTermbaseDialog) {
        AiTermbaseDialog(
            isArabic = isArabic,
            onDismiss = { showTermbaseDialog = false }
        )
    }
}
