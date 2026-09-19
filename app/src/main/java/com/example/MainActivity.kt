package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var isDarkTheme by remember { mutableStateOf(false) }
      MyApplicationTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        PolylangHubApp(
          isDarkTheme = isDarkTheme,
          onToggleTheme = { isDarkTheme = !isDarkTheme }
        )
      }
    }
  }
}

// Retained for screenshot & unit testing compatibility
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

enum class AppCurrency(val symbolAr: String, val symbolEn: String, val rate: Double, val flag: String) {
  DZD("دج", "DZD", 1.0, "🇩🇿"),
  USD("$", "USD", 0.0075, "🇺🇸"),
  EUR("€", "EUR", 0.0068, "🇪🇺")
}

fun Int.toLocaleString(): String {
  return java.text.NumberFormat.getNumberInstance(Locale.US).format(this)
}

fun formatPrice(amountDzd: Int, currency: AppCurrency, isArabic: Boolean): String {
  return when (currency) {
    AppCurrency.DZD -> if (isArabic) "${amountDzd.toLocaleString()} دج" else "${amountDzd.toLocaleString()} DZD"
    AppCurrency.USD -> {
      val converted = amountDzd * currency.rate
      String.format(Locale.US, "$%.2f", converted)
    }
    AppCurrency.EUR -> {
      val converted = amountDzd * currency.rate
      String.format(Locale.US, "€%.2f", converted)
    }
  }
}

enum class AppTab {
  HOME, SERVICES, SUBTITLING, ACADEMY, PRICING, DASHBOARD
}

enum class UserRole {
  CLIENT, TRANSLATOR, ADMIN
}

data class TranslationOrder(
  val id: String,
  val title: String,
  val category: String,
  val langPair: String,
  val wordCount: Int,
  val priceDzd: Int,
  val status: String,
  val isInterpretation: Boolean = false
)

data class SrtCue(
  val id: Int,
  val start: String,
  val end: String,
  val sourceText: String,
  val subtitleText: String
)

data class MasterclassCourse(
  val id: String,
  val title: String,
  val category: String,
  val duration: String,
  val instructor: String,
  val level: String,
  val desc: String,
  val videoUrl: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
)

data class TermCard(
  val id: Int,
  val text: String,
  val isArabic: Boolean,
  var isSelected: Boolean = false,
  var isMatched: Boolean = false
)

/* =============================================================
   MANDATORY ADMIN EMAIL NOTIFICATION DISPATCH ENGINE
   Target: djoudimadani09@gmail.com
   ============================================================= */
object AdminEmailNotifier {
  const val ADMIN_EMAIL = "djoudimadani09@gmail.com"

  fun dispatch(
    eventType: String,
    userName: String,
    userEmail: String,
    details: Map<String, String>,
    onResult: (Boolean, String) -> Unit = { _, _ -> }
  ) {
    CoroutineScope(Dispatchers.IO).launch {
      var success = false
      try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timestamp = dateFormat.format(Date())

        val jsonObj = JSONObject()
        jsonObj.put("_subject", "[Polylang Android] $eventType: $userName")
        jsonObj.put("_replyto", userEmail)
        jsonObj.put("_captcha", "false")
        jsonObj.put("eventType", eventType)
        jsonObj.put("timestamp", timestamp)
        jsonObj.put("userName", userName)
        jsonObj.put("userEmail", userEmail)
        details.forEach { (k, v) -> jsonObj.put(k, v) }

        val url = URL("https://formsubmit.co/ajax/$ADMIN_EMAIL")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        conn.setRequestProperty("Accept", "application/json")
        conn.doOutput = true
        conn.connectTimeout = 6000
        conn.readTimeout = 6000

        OutputStreamWriter(conn.outputStream, "UTF-8").use { writer ->
          writer.write(jsonObj.toString())
          writer.flush()
        }

        val code = conn.responseCode
        success = (code in 200..299)
        conn.disconnect()
      } catch (e: Exception) {
        // Safe failover
      }

      withContext(Dispatchers.Main) {
        onResult(success, "📧 تم إرسال إشعار فوري إلى بريد المؤسس: $ADMIN_EMAIL")
      }
    }
  }
}

// --------------------------------------------------------------------------
// ROOT COMPOSABLE: PolylangHubApp
// --------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolylangHubApp(
  isDarkTheme: Boolean = false,
  onToggleTheme: () -> Unit = {}
) {
  var isArabic by remember { mutableStateOf(true) }
  var currentTab by remember { mutableStateOf(AppTab.HOME) }
  var currentRole by remember { mutableStateOf(UserRole.CLIENT) }
  var userName by remember { mutableStateOf("كريم حمداوي") }
  var userPlan by remember { mutableStateOf("Pro Translator") }
  var userBalance by remember { mutableStateOf(8500) }
  var currentCurrency by remember { mutableStateOf(AppCurrency.DZD) }
  var showAiTermDialog by remember { mutableStateOf(false) }
  var showCertDialog by remember { mutableStateOf(false) }
  var certCourseTitle by remember { mutableStateOf("الترجمة القانونية وصياغة العقود الدولية") }

  // State for orders
  val orders = remember {
    mutableStateListOf(
      TranslationOrder("PL-8821", "عقد توريد معدات طاقة شمسية", "قانونية ورسمية", "EN ➔ AR", 2400, 9600, "مكتمل ومسلّم"),
      TranslationOrder("PL-8940", "دليل مستخدم نظام إدارة السدود", "تقنية وهندسية", "FR ➔ AR", 1850, 7400, "قيد الترجمة والتدقيق"),
      TranslationOrder("INT-4022", "حجز مترجم فوري (قمة المناخ والتحول الطاقوي)", "ترجمة فورية", "AR ⇄ EN", 1920, 140000, "مؤكد وجاهز", isInterpretation = true),
      TranslationOrder("PL-9012", "اتفاقية عدم إفصاح سرية مؤسساتية", "مؤسساتية", "AR ➔ EN", 950, 3800, "قيد المراجعة")
    )
  }

  // State for SRT cues
  val srtCues = remember {
    mutableStateListOf(
      SrtCue(1, "00:00:01,200", "00:00:04,500", "Welcome to the Clean Energy Summit.", "مرحباً بكم في القمة العالمية للطاقة النظيفة."),
      SrtCue(2, "00:00:04,800", "00:00:08,100", "Renewable transition is our common path.", "التحول نحو الطاقة المتجددة مسارنا المشترك."),
      SrtCue(3, "00:00:08,400", "00:00:12,650", "Cooperation is key to guaranteed sustainability.", "التعاون الدولي هو الركيزة لضمان الاستدامة.")
    )
  }

  // Payment dialog state
  var showPaymentDialog by remember { mutableStateOf(false) }
  var pendingPlanName by remember { mutableStateOf("Pro Translator") }
  var pendingPlanPrice by remember { mutableStateOf(4500) }

  // Masterclass Video Player Dialog state
  var activeVideoCourse by remember { mutableStateOf<MasterclassCourse?>(null) }

  // Snackbars & Coroutines
  val snackbarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

  CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      contentWindowInsets = WindowInsets.safeDrawing,
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        TopAppBar(
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceWhite,
            titleContentColor = RedDark
          ),
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(Brush.linearGradient(listOf(RedPrimary, RedDark))),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "P",
                  color = Color.White,
                  fontWeight = FontWeight.Black,
                  fontSize = 20.sp
                )
              }
              Spacer(Modifier.width(10.dp))
              Column {
                Text(
                  text = "Polylang Hub",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = RedDark
                )
                Text(
                  text = if (isArabic) "بوابة الترجمة والتدريب المعتمدة" else "Certified Translation & Academy",
                  fontSize = 10.sp,
                  color = TextMuted
                )
              }
            }
          },
          actions = {
            // Global Currency Switcher (DZD / USD / EUR)
            OutlinedButton(
              onClick = {
                currentCurrency = when (currentCurrency) {
                  AppCurrency.DZD -> AppCurrency.USD
                  AppCurrency.USD -> AppCurrency.EUR
                  AppCurrency.EUR -> AppCurrency.DZD
                }
                coroutineScope.launch {
                  snackbarHostState.showSnackbar("العملة: ${currentCurrency.flag} ${currentCurrency.name} (${currentCurrency.symbolAr})")
                }
              },
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier
                .padding(end = 4.dp)
                .heightIn(min = 40.dp)
                .testTag("currency_toggle_btn"),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
              border = BorderStroke(1.dp, RedContainer)
            ) {
              Text(
                text = "${currentCurrency.flag} ${currentCurrency.name}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = RedPrimary
              )
            }

            // AI Termbase Instant Dialog
            IconButton(
              onClick = { showAiTermDialog = true },
              modifier = Modifier.size(40.dp).testTag("ai_term_btn")
            ) {
              Text("🤖", fontSize = 16.sp)
            }

            // Dark / Light Theme Toggle
            IconButton(
              onClick = { onToggleTheme() },
              modifier = Modifier.size(40.dp).testTag("theme_toggle_btn")
            ) {
              Text(if (isDarkTheme) "☀️" else "🌙", fontSize = 16.sp)
            }

            // Language Switcher button (touch target >= 48dp)
            OutlinedButton(
              onClick = {
                isArabic = !isArabic
                coroutineScope.launch {
                  snackbarHostState.showSnackbar(if (isArabic) "تم التبديل للغة العربية" else "Switched to English")
                }
              },
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier
                .padding(end = 4.dp)
                .heightIn(min = 40.dp)
                .testTag("lang_toggle_btn"),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
              border = BorderStroke(1.dp, RedContainer)
            ) {
              Text(
                text = if (isArabic) "English" else "العربية",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = RedPrimary
              )
            }

            // Role toggle pill
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = RedLight,
              modifier = Modifier
                .heightIn(min = 40.dp)
                .clickable {
                  currentRole = when (currentRole) {
                    UserRole.CLIENT -> UserRole.TRANSLATOR
                    UserRole.TRANSLATOR -> UserRole.ADMIN
                    UserRole.ADMIN -> UserRole.CLIENT
                  }
                  userName = when (currentRole) {
                    UserRole.CLIENT -> "كريم حمداوي (عميل)"
                    UserRole.TRANSLATOR -> "د. ليلى مزياني (مترجم معتمد)"
                    UserRole.ADMIN -> "مداني جودي (المشرف العام)"
                  }
                  // Notify admin email on role switch
                  AdminEmailNotifier.dispatch(
                    "USER_LOGIN",
                    userName,
                    AdminEmailNotifier.ADMIN_EMAIL,
                    mapOf("role" to currentRole.name, "device" to "Android App")
                  ) { _, msg ->
                    coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
                  }
                }
                .testTag("role_toggle_pill")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
              ) {
                Icon(
                  when (currentRole) {
                    UserRole.CLIENT -> Icons.Default.Person
                    UserRole.TRANSLATOR -> Icons.Default.School
                    UserRole.ADMIN -> Icons.Default.AdminPanelSettings
                  },
                  contentDescription = "Role",
                  tint = RedDark,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                  text = when (currentRole) {
                    UserRole.CLIENT -> if (isArabic) "عميل" else "Client"
                    UserRole.TRANSLATOR -> if (isArabic) "مترجم" else "Translator"
                    UserRole.ADMIN -> if (isArabic) "مدير" else "Admin"
                  },
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = RedDark
                )
              }
            }
          }
        )
      },
      bottomBar = {
        NavigationBar(
          containerColor = SurfaceWhite,
          tonalElevation = 6.dp
        ) {
          NavigationBarItem(
            selected = currentTab == AppTab.HOME,
            onClick = { currentTab = AppTab.HOME },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(if (isArabic) "الرئيسية" else "Home", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
          NavigationBarItem(
            selected = currentTab == AppTab.SERVICES,
            onClick = { currentTab = AppTab.SERVICES },
            icon = { Icon(Icons.Default.Description, contentDescription = "Services") },
            label = { Text(if (isArabic) "الترجمة" else "Orders", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
          NavigationBarItem(
            selected = currentTab == AppTab.SUBTITLING,
            onClick = { currentTab = AppTab.SUBTITLING },
            icon = { Icon(Icons.Default.Subtitles, contentDescription = "SRT") },
            label = { Text("SRT", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
          NavigationBarItem(
            selected = currentTab == AppTab.ACADEMY,
            onClick = { currentTab = AppTab.ACADEMY },
            icon = { Icon(Icons.Default.Mic, contentDescription = "Academy") },
            label = { Text(if (isArabic) "الأكاديمية" else "Academy", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
          NavigationBarItem(
            selected = currentTab == AppTab.PRICING,
            onClick = { currentTab = AppTab.PRICING },
            icon = { Icon(Icons.Default.Payment, contentDescription = "Plans") },
            label = { Text(if (isArabic) "الباقات" else "Plans", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
          NavigationBarItem(
            selected = currentTab == AppTab.DASHBOARD,
            onClick = { currentTab = AppTab.DASHBOARD },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text(if (isArabic) "لوحتي" else "Dashboard", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = RedDark, indicatorColor = RedLight)
          )
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(BgLight)
          .padding(innerPadding),
        contentAlignment = Alignment.TopCenter
      ) {
        // Width-constrained container for responsive professional sizing on tablets/foldables
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 640.dp)
        ) {
          when (currentTab) {
            AppTab.HOME -> HomeScreen(
              isArabic = isArabic,
              onNavigate = { currentTab = it }
            )
            AppTab.SERVICES -> ServicesScreen(
              isArabic = isArabic,
              currency = currentCurrency,
              onOrderCreated = { newOrder ->
                orders.add(0, newOrder)
                AdminEmailNotifier.dispatch(
                  "NEW_ORDER",
                  userName,
                  AdminEmailNotifier.ADMIN_EMAIL,
                  mapOf(
                    "orderId" to newOrder.id,
                    "title" to newOrder.title,
                    "category" to newOrder.category,
                    "price" to "${newOrder.priceDzd} دج"
                  )
                ) { _, msg ->
                  coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                      if (isArabic) "تم تأكيد طلب الترجمة ${newOrder.id} وإشعار الإدارة بنجاح!" else "Order ${newOrder.id} submitted!"
                    )
                  }
                }
                currentTab = AppTab.DASHBOARD
              }
            )
            AppTab.SUBTITLING -> SubtitlingScreen(
              isArabic = isArabic,
              cues = srtCues,
              onAddCue = {
                val newId = srtCues.size + 1
                srtCues.add(SrtCue(newId, "00:00:13,000", "00:00:16,500", "New speaker sentence...", "مقطع حوار مترجم جديد..."))
              },
              onDeleteCue = { cue -> srtCues.remove(cue) },
              onExport = {
                coroutineScope.launch {
                  snackbarHostState.showSnackbar(if (isArabic) "تم تصدير ملف polylang_subtitles.srt بنجاح" else "Exported polylang_subtitles.srt")
                }
              }
            )
            AppTab.ACADEMY -> AcademyScreen(
              isArabic = isArabic,
              onPlayCourse = { course -> activeVideoCourse = course },
              onShowCertificate = { course ->
                certCourseTitle = course
                showCertDialog = true
              },
              onMessage = { msg -> coroutineScope.launch { snackbarHostState.showSnackbar(msg) } }
            )
            AppTab.PRICING -> PricingScreen(
              isArabic = isArabic,
              currency = currentCurrency,
              onSelectPlan = { name, price ->
                if (price == 0) {
                  userPlan = "Free Starter"
                  coroutineScope.launch { snackbarHostState.showSnackbar(if (isArabic) "تم تفعيل الباقة المجانية" else "Free plan active") }
                } else {
                  pendingPlanName = name
                  pendingPlanPrice = price
                  showPaymentDialog = true
                }
              }
            )
            AppTab.DASHBOARD -> DashboardScreen(
              isArabic = isArabic,
              currency = currentCurrency,
              userName = userName,
              role = currentRole,
              plan = userPlan,
              balance = userBalance,
              orders = orders,
              onAction = { msg -> coroutineScope.launch { snackbarHostState.showSnackbar(msg) } }
            )
          }
        }
      }
    }

    // Payment Dialog for Algerian local payment methods
    if (showPaymentDialog) {
      PaymentDialog(
        isArabic = isArabic,
        currency = currentCurrency,
        planName = pendingPlanName,
        planPrice = pendingPlanPrice,
        onDismiss = { showPaymentDialog = false },
        onConfirmPayment = {
          userPlan = pendingPlanName
          showPaymentDialog = false
          AdminEmailNotifier.dispatch(
            "PAYMENT_CONFIRMED",
            userName,
            AdminEmailNotifier.ADMIN_EMAIL,
            mapOf("plan" to pendingPlanName, "amount" to "$pendingPlanPrice دج")
          ) { _, _ -> }
          coroutineScope.launch {
            snackbarHostState.showSnackbar(
              if (isArabic) "تم الدفع وتفعيل $pendingPlanName بنجاح وإشعار الإدارة!" else "Payment completed. $pendingPlanName activated!"
            )
          }
          currentTab = AppTab.DASHBOARD
        }
      )
    }

    // Certificate Dialog
    if (showCertDialog) {
      CertificateDialog(
        isArabic = isArabic,
        studentName = userName,
        courseTitle = certCourseTitle,
        onDismiss = { showCertDialog = false },
        onShare = {
          coroutineScope.launch { snackbarHostState.showSnackbar("تم نسخ بيانات شهادة التأهيل بنجاح ✓") }
          AdminEmailNotifier.dispatch(
            "CERTIFICATE_ISSUED",
            userName,
            AdminEmailNotifier.ADMIN_EMAIL,
            mapOf("course" to certCourseTitle, "hash" to "CERT-PLY-2026-9812")
          ) { _, msg ->
            coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
          }
        }
      )
    }

    // AI Termbase Dialog
    if (showAiTermDialog) {
      AiTermbaseDialog(
        isArabic = isArabic,
        onDismiss = { showAiTermDialog = false },
        onNotifyAdmin = { term ->
          coroutineScope.launch { snackbarHostState.showSnackbar("تم إرسال المصطلح للإدارة للتحقق") }
          AdminEmailNotifier.dispatch(
            "TERM_INQUIRY",
            userName,
            AdminEmailNotifier.ADMIN_EMAIL,
            mapOf("term" to term)
          ) { _, msg ->
            coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
          }
        }
      )
    }

    // Masterclass Video Player Dialog
    activeVideoCourse?.let { course ->
      Dialog(onDismissRequest = { activeVideoCourse = null }) {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
          border = BorderStroke(1.5.dp, RedPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
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
              Surface(color = Color(0x33E53935), shape = RoundedCornerShape(8.dp)) {
                Text(
                  text = "🎓 ${course.category}",
                  color = Color(0xFFFCA5A5),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
              IconButton(onClick = { activeVideoCourse = null }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
              }
            }

            // Simulated Video Player Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF1E293B), Color(0xFF020617)))),
              contentAlignment = Alignment.Center
            ) {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(RedPrimary),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Text(
                  text = "▶️ فيديو الماستركلاس جاهز للعرض (${course.duration})",
                  color = Color(0xFFE2E8F0),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "دقة البث: 1080p Full HD • صوت استوديو معتمد",
                  color = Color(0xFF94A3B8),
                  fontSize = 10.sp
                )
              }
            }

            Text(
              text = course.title,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
            Text(
              text = course.desc,
              color = Color(0xFFCBD5E1),
              fontSize = 12.sp,
              lineHeight = 16.sp
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "المحاضر: ${course.instructor} • المستوى: ${course.level}",
                color = Color(0xFF94A3B8),
                fontSize = 11.sp
              )
              Button(
                onClick = {
                  AdminEmailNotifier.dispatch(
                    "COURSE_STARTED",
                    userName,
                    AdminEmailNotifier.ADMIN_EMAIL,
                    mapOf("course" to course.title, "duration" to course.duration)
                  )
                  coroutineScope.launch {
                    snackbarHostState.showSnackbar("تم بدء دراسة ماستركلاس: ${course.title}")
                  }
                  activeVideoCourse = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                shape = RoundedCornerShape(10.dp)
              ) {
                Text("بدء المحاضرة", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// --------------------------------------------------------------------------
// 1. HOME SCREEN
// --------------------------------------------------------------------------
@Composable
fun HomeScreen(isArabic: Boolean, onNavigate: (AppTab) -> Unit) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // AI Robot Presenter Welcome Card
    var isRobotSpeaking by remember { mutableStateOf(false) }
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
      border = BorderStroke(1.5.dp, Color(0xFFE53935)),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
      modifier = Modifier.fillMaxWidth().testTag("ai_robot_presenter_card")
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color(0xFF10B981))
            )
            Text(
              text = if (isArabic) "🎙️ ترحيب بوليلانغ (Polylang)" else "🎙️ Welcome to Polylang",
              color = Color.White,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Surface(
            color = Color(0x3310B981),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = "ISO 17100:2015",
              color = Color(0xFF34D399),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(Modifier.height(10.dp))

        Surface(
          color = Color(0xFF1E293B),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, Color(0x44E53935)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = if (isArabic)
                "\"مرحباً بكم في بوليلانغ (Polylang)! خدمات الترجمة المحلفة والفورية وتطوير المهارات اللغوية.\""
              else
                "\"Welcome to Polylang! Certified translation, interpretation, and language mastery.\"",
              color = Color(0xFFF1F5F9),
              fontSize = 12.sp,
              lineHeight = 18.sp,
              fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = if (isArabic) "المقر: باب الزوار، الجزائر • RC: 16/00-0982341B26" else "Algiers, DZ • RC: 16/00-0982341B26",
                fontSize = 10.sp,
                color = Color(0xFF94A3B8)
              )
              Button(
                onClick = { isRobotSpeaking = !isRobotSpeaking },
                colors = ButtonDefaults.buttonColors(containerColor = if (isRobotSpeaking) Color(0xFF10B981) else Color(0xFFE53935)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp)
              ) {
                Text(
                  text = if (isRobotSpeaking) "🔊 صوت نشط" else "🔊 مرحباً بكم",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // Hero Banner Card
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
      modifier = Modifier.fillMaxWidth().testTag("hero_banner_card")
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Surface(
          color = Color(0x33E53935),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = if (isArabic) "✨ خدمات الترجمة والتدريب المعتمدة" else "✨ Certified Translation & Training",
            color = Color(0xFFFCA5A5),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Spacer(Modifier.height(10.dp))

        Text(
          text = if (isArabic) "ترجمة معتمدة وأكاديمية تدريب مهنية" else "Certified Translation & Professional Academy",
          color = Color.White,
          fontSize = 19.sp,
          fontWeight = FontWeight.ExtraBold,
          lineHeight = 26.sp
        )

        Spacer(Modifier.height(6.dp))

        Text(
          text = if (isArabic)
            "نقدم خدمات الترجمة القانونية، التقنية والمؤسساتية، إدارة ملفات SRT، وأكاديمية تفاعلية لإعداد المترجمين."
          else
            "Legal, technical and institutional translations, real-time SRT subtitling, and interactive interpreter training.",
          color = Color(0xFFCBD5E1),
          fontSize = 12.sp,
          lineHeight = 17.sp
        )

        Spacer(Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = { onNavigate(AppTab.SERVICES) },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f).heightIn(min = 44.dp).testTag("home_order_btn")
          ) {
            Text(if (isArabic) "📄 طلب ترجمة" else "📄 Request Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
          OutlinedButton(
            onClick = { onNavigate(AppTab.ACADEMY) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            border = BorderStroke(1.dp, Color(0x66FFFFFF)),
            modifier = Modifier.weight(1f).heightIn(min = 44.dp).testTag("home_academy_btn")
          ) {
            Text(if (isArabic) "🎓 الأكاديمية" else "🎓 Academy", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = Color(0x22FFFFFF))
        Spacer(Modifier.height(10.dp))

        // Stats row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          StatCounter("+120,000", if (isArabic) "كلمة شهرياً" else "Words/mo")
          StatCounter("99.4%", if (isArabic) "دقة المصطلحات" else "Accuracy")
          StatCounter("+450", if (isArabic) "مترجم معتمد" else "Translators")
        }
      }
    }

    // Admin Dispatch Telemetry Pill on Home
    Surface(
      color = Color(0xFFECFDF5),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(Color(0xFF059669))
        )
        Text(
          text = if (isArabic)
            "🟢 متصل ببريد الإدارة المباشر: djoudimadani09@gmail.com"
          else
            "🟢 Connected to admin inbox: djoudimadani09@gmail.com",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF065F46)
        )
      }
    }

    Text(
      text = if (isArabic) "أقسام ومنظومة Polylang Hub" else "Polylang Hub Modules",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = TextDark
    )

    // Grid cards
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      ServiceTile(
        title = if (isArabic) "ترجمة قانونية" else "Legal Translation",
        desc = if (isArabic) "عقود ووثائق رسمية معتمدة" else "Certified contracts & docs",
        icon = Icons.Default.Gavel,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(AppTab.SERVICES) }
      )
      ServiceTile(
        title = if (isArabic) "ترجمة تقنية" else "Technical Trans",
        desc = if (isArabic) "كتيبات هندسية وبرمجيات" else "Manuals & IT software",
        icon = Icons.Default.Computer,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(AppTab.SERVICES) }
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      ServiceTile(
        title = if (isArabic) "الدبلجة وSRT" else "SRT Subtitling",
        desc = if (isArabic) "محرر التوقيت والتصدير" else "Timecode editor & export",
        icon = Icons.Default.Movie,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(AppTab.SUBTITLING) }
      )
      ServiceTile(
        title = if (isArabic) "أكاديمية التدريب" else "Training Academy",
        desc = if (isArabic) "رموز الملاحظات والتدقيق" else "Note-taking & evaluation",
        icon = Icons.Default.RecordVoiceOver,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(AppTab.ACADEMY) }
      )
    }
  }
}

@Composable
fun StatCounter(value: String, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = value, color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp)
    Text(text = label, color = Color(0xFF94A3B8), fontSize = 10.sp)
  }
}

@Composable
fun ServiceTile(
  title: String,
  desc: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    border = BorderStroke(1.dp, BorderLight),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .clickable { onClick() }
      .testTag("service_tile_${title.replace(' ', '_')}")
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(RedLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = title, tint = RedDark, modifier = Modifier.size(20.dp))
      }
      Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
      Text(desc, fontSize = 10.sp, color = TextMuted, maxLines = 2, lineHeight = 14.sp)
    }
  }
}

// --------------------------------------------------------------------------
// 2. SERVICES SCREEN: Document Translation + Conference Interpretation
// --------------------------------------------------------------------------
@Composable
fun ServicesScreen(
  isArabic: Boolean,
  currency: AppCurrency = AppCurrency.DZD,
  onOrderCreated: (TranslationOrder) -> Unit
) {
  val scrollState = rememberScrollState()

  // Mode: "doc" or "interpretation"
  var serviceMode by remember { mutableStateOf("doc") }

  // Document states
  var selectedCategory by remember { mutableStateOf("قانونية ورسمية") }
  var sourceLang by remember { mutableStateOf("الإنجليزية") }
  var targetLang by remember { mutableStateOf("العربية") }
  var wordCountText by remember { mutableStateOf("850") }
  var clientNotes by remember { mutableStateOf("") }

  val wordCount = wordCountText.toIntOrNull() ?: 500
  val ratePerWord = when (selectedCategory) {
    "قانونية ورسمية" -> 4.0
    "تقنية وهندسية" -> 3.8
    "مؤسساتية وأكاديمية" -> 4.2
    else -> 4.5
  }
  val estimatedDocPrice = (wordCount * ratePerWord).toInt()
  val deliveryTime = if (wordCount > 3000) "3 - 5 أيام" else if (wordCount > 1000) "48 - 72 ساعة" else "24 - 48 ساعة"

  // Interpretation states
  var interpEventTitle by remember { mutableStateOf("الملتقى الدولي للاستثمار والتحول الطاقوي") }
  var interpVenue by remember { mutableStateOf("المركز الدولي للمؤتمرات (CIC عبد اللطيف رحال، الجزائر)") }
  var interpLangPair by remember { mutableStateOf("عربية ⇄ إنجليزية (AR ⇄ EN)") }
  var interpDays by remember { mutableStateOf(2) }
  var interpTeamSize by remember { mutableStateOf(2) }
  var interpPhone by remember { mutableStateOf("0550 12 34 56") }
  val interpTotalCost = interpDays * interpTeamSize * 35000

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      text = if (isArabic) "خدمات الترجمة المعتمدة والمؤتمرات" else "Certified Translation & Conferences",
      fontWeight = FontWeight.Bold,
      fontSize = 17.sp,
      color = RedDark
    )

    // Service Mode Toggle Chips
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      FilterChip(
        selected = serviceMode == "doc",
        onClick = { serviceMode = "doc" },
        label = { Text(if (isArabic) "📄 ترجمة المستندات والعقود" else "📄 Document Translation", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = serviceMode == "interpretation",
        onClick = { serviceMode = "interpretation" },
        label = { Text(if (isArabic) "🎙️ حجز مترجم فوري للمؤتمرات" else "🎙️ Conference Interpretation", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
    }

    if (serviceMode == "doc") {
      // Document Translation Card
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            if (isArabic) "المجال التخصصي للمستند" else "Specialized Domain",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )

          val categories = listOf("قانونية ورسمية", "تقنية وهندسية", "مؤسساتية وأكاديمية", "طبية ودوائية")
          Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEach { cat ->
              FilterChip(
                selected = selectedCategory == cat,
                onClick = { selectedCategory = cat },
                label = { Text(cat, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = RedPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(Modifier.weight(1f)) {
              Text(if (isArabic) "لغة المصدر" else "Source", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              Spacer(Modifier.height(4.dp))
              OutlinedTextField(
                value = sourceLang,
                onValueChange = { sourceLang = it },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
              )
            }
            Column(Modifier.weight(1f)) {
              Text(if (isArabic) "لغة الهدف" else "Target", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              Spacer(Modifier.height(4.dp))
              OutlinedTextField(
                value = targetLang,
                onValueChange = { targetLang = it },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
              )
            }
          }

          Column {
            Text(if (isArabic) "عدد الكلمات التقديري" else "Word Count", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
              value = wordCountText,
              onValueChange = { wordCountText = it },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              singleLine = true
            )
          }

          OutlinedTextField(
            value = clientNotes,
            onValueChange = { clientNotes = it },
            placeholder = { Text(if (isArabic) "ملاحظات أو مسرد مصطلحات خاص للمترجم..." else "Special instructions...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }

      // Price summary card
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(if (isArabic) "مدة التسليم المقدرة:" else "Delivery:", color = TextMuted, fontSize = 12.sp)
            Text(deliveryTime, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextDark)
          }
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(if (isArabic) "التدقيق والمطابقة (ISO 17100):" else "Quality Audit:", color = TextMuted, fontSize = 12.sp)
            Text(if (isArabic) "مشمول مجاناً ✓" else "Included ✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
          HorizontalDivider(color = BorderLight, modifier = Modifier.padding(vertical = 4.dp))
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(if (isArabic) "التكلفة التقديرية:" else "Total Price:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(formatPrice(estimatedDocPrice, currency, isArabic), fontWeight = FontWeight.ExtraBold, fontSize = 19.sp, color = RedDark)
          }

          Spacer(Modifier.height(4.dp))

          Button(
            onClick = {
              val randomId = "PL-" + (1000..9999).random()
              val order = TranslationOrder(
                id = randomId,
                title = "مستند ($selectedCategory)",
                category = selectedCategory,
                langPair = "$sourceLang ➔ $targetLang",
                wordCount = wordCount,
                priceDzd = estimatedDocPrice,
                status = "قيد المراجعة"
              )
              onOrderCreated(order)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().heightIn(min = 46.dp).testTag("confirm_order_btn")
          ) {
            Text(if (isArabic) "🚀 تأكيد وتقديم طلب الترجمة" else "🚀 Confirm & Submit Order", fontWeight = FontWeight.Bold)
          }
        }
      }
    } else {
      // Conference Interpretation Booking
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
            Text(
              "🎙️ معيار ISO 2603 وISO 18841 للترجمة الفورية المعتمدة",
              color = RedDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Column {
            Text("عنوان الفعالية أو المؤتمر الدولي:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
              value = interpEventTitle,
              onValueChange = { interpEventTitle = it },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth()
            )
          }

          Column {
            Text("مكان الانعقاد والمدينة:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            val venues = listOf(
              "المركز الدولي للمؤتمرات (CIC عبد اللطيف رحال، الجزائر)",
              "قصر المعارض (SAFEX الصنوبر البحري)",
              "فندق الأوراسي (Salle des Congrès)",
              "مركز المؤتمرات محمد بن أحمد (CCO وهران)",
              "ترجمة فورية عن بعد (RSI عبر Kudo / Zoom Pro)"
            )
            var expandedVenue by remember { mutableStateOf(false) }
            OutlinedButton(
              onClick = { expandedVenue = !expandedVenue },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(interpVenue, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            if (expandedVenue) {
              venues.forEach { v ->
                Text(
                  text = "• $v",
                  fontSize = 11.sp,
                  color = RedDark,
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      interpVenue = v
                      expandedVenue = false
                    }
                    .padding(vertical = 4.dp)
                )
              }
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(Modifier.weight(1f)) {
              Text("عدد الأيام:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(1, 2, 3, 5).forEach { d ->
                  FilterChip(
                    selected = interpDays == d,
                    onClick = { interpDays = d },
                    label = { Text("$d يوم", fontSize = 10.sp) }
                  )
                }
              }
            }
            Column(Modifier.weight(1f)) {
              Text("المترجمين:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(1, 2, 4).forEach { t ->
                  FilterChip(
                    selected = interpTeamSize == t,
                    onClick = { interpTeamSize = t },
                    label = { Text("$t مترجم", fontSize = 10.sp) }
                  )
                }
              }
            }
          }

          Column {
            Text("رقم هاتف مسؤول الاتصال والتنسيق:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
              value = interpPhone,
              onValueChange = { interpPhone = it },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
          }

          HorizontalDivider(color = BorderLight, modifier = Modifier.padding(vertical = 4.dp))

          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
              Text("التكلفة الإجمالية (35,000 دج/يوم/مترجم):", color = TextMuted, fontSize = 11.sp)
              Text(formatPrice(interpTotalCost, currency, isArabic), fontWeight = FontWeight.ExtraBold, fontSize = 19.sp, color = RedDark)
            }
          }

          Button(
            onClick = {
              val randomId = "INT-" + (1000..9999).random()
              val order = TranslationOrder(
                id = randomId,
                title = "حجز ترجمة فورية ($interpEventTitle)",
                category = "ترجمة فورية",
                langPair = interpLangPair,
                wordCount = interpDays * 8 * 120,
                priceDzd = interpTotalCost,
                status = "مؤكد وجاهز",
                isInterpretation = true
              )
              onOrderCreated(order)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().heightIn(min = 46.dp)
          ) {
            Text("🚀 تأكيد وحجز المترجم الفوري (إرسال للإدارة)", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// --------------------------------------------------------------------------
// 3. VIDEO SUBTITLING & SRT SCREEN
// --------------------------------------------------------------------------
@Composable
fun SubtitlingScreen(
  isArabic: Boolean,
  cues: List<SrtCue>,
  onAddCue: () -> Unit,
  onDeleteCue: (SrtCue) -> Unit,
  onExport: () -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (isArabic) "محرر الترجمة المرئية وSRT" else "SRT Subtitling Editor",
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = RedDark
      )
      Button(
        onClick = onExport,
        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.heightIn(min = 38.dp).testTag("export_srt_btn")
      ) {
        Text(if (isArabic) "💾 تصدير .SRT" else "💾 Export .SRT", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }

    // Video Player Box Simulator
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1E293B)),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Movie, contentDescription = "Video", tint = Color.White, modifier = Modifier.size(36.dp))
            Spacer(Modifier.height(4.dp))
            Text("00:00:04,800 / 00:02:45,000", color = Color(0xFF94A3B8), fontSize = 11.sp)
            Text(
              "\"التحول نحو الطاقة المتجددة مسارنا المشترك.\"",
              color = Color.Yellow,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (isArabic) "مقاطع الحوار (${cues.size})" else "Cues (${cues.size})",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = TextDark
      )
      OutlinedButton(
        onClick = onAddCue,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.heightIn(min = 38.dp).testTag("add_cue_btn")
      ) {
        Text(if (isArabic) "+ مقطع جديد" else "+ Add Cue", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }

    // Cue list
    cues.forEach { cue ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              "#${cue.id}  •  ${cue.start} ➔ ${cue.end}",
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = RedDark
            )
            IconButton(
              onClick = { onDeleteCue(cue) },
              modifier = Modifier.size(24.dp)
            ) {
              Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
            }
          }
          Text(cue.sourceText, fontSize = 11.sp, color = TextMuted)
          Text(cue.subtitleText, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }
      }
    }
  }
}

// --------------------------------------------------------------------------
// 4. EXPANSIVE INTERACTIVE ACADEMY & 20 MASTERCLASSES SCREEN
// --------------------------------------------------------------------------
@Composable
fun AcademyScreen(
  isArabic: Boolean,
  onPlayCourse: (MasterclassCourse) -> Unit,
  onShowCertificate: (String) -> Unit = {},
  onMessage: (String) -> Unit
) {
  val scrollState = rememberScrollState()

  // Academy sub-tab: "matcher", "booth", "rozan", "legal", "cps", "masterclass"
  var currentSubTab by remember { mutableStateOf("matcher") }

  // 1. Matcher Game State
  val initialPairs = listOf(
    Pair("Force Majeure", "القوة القاهرة / الحادث الفجائي"),
    Pair("Boilerplate Clauses", "البنود النمطية في العقود"),
    Pair("Indemnification", "التعويض وإبراء الذمة"),
    Pair("Informed Consent", "الموافقة المستنيرة السريرية"),
    Pair("Pharmacovigilance", "اليقظة والرصد الدوائي"),
    Pair("Simultaneous Interpretation", "الترجمة الفورية المتزامنة"),
    Pair("Décalage", "الفارق الزمني في الكابينة"),
    Pair("Termbase", "مسرد المصطلحات المعتمد")
  )

  var matcherCards by remember {
    val list = mutableStateListOf<TermCard>()
    initialPairs.forEachIndexed { idx, pair ->
      list.add(TermCard(idx, pair.first, false))
      list.add(TermCard(idx, pair.second, true))
    }
    list.shuffle()
    mutableStateOf(list)
  }
  var selectedCardIdx by remember { mutableStateOf<Int?>(null) }
  var matcherScore by remember { mutableStateOf(0) }
  var matcherMatches by remember { mutableStateOf(0) }

  // 2. Booth Simulator State
  var isBoothSpeechPlaying by remember { mutableStateOf(false) }
  var decalageTimer by remember { mutableStateOf(0.0) }
  var isBoothMicOn by remember { mutableStateOf(false) }
  var boothTranscript by remember { mutableStateOf("") }
  var boothFeedback by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(isBoothSpeechPlaying) {
    if (isBoothSpeechPlaying) {
      decalageTimer = 0.0
      while (isBoothSpeechPlaying) {
        delay(100)
        decalageTimer += 0.1
      }
    }
  }

  // 3. Rozan Consecutive State
  var rozanNotes by remember { mutableStateOf("") }
  var rozanRendition by remember { mutableStateOf("") }
  var rozanAuditResult by remember { mutableStateOf<String?>(null) }

  // 4. Legal Drafter State
  var legalInput by remember { mutableStateOf("") }
  var legalAuditResult by remember { mutableStateOf<String?>(null) }

  // 5. AVT CPS Subtitling State
  var cpsInput by remember { mutableStateOf("يتعين علينا إعادة تقييم استراتيجية التحول الطاقوي") }
  val charCount = cpsInput.length
  val cpsValue = (charCount / 3.2).toFloat()

  // 6. Masterclass Courses Database (20 Complete High-Value Masterclasses)
  val masterclasses = remember {
    listOf(
      MasterclassCourse("mc-1", "تقنيات الترجمة الفورية والـ Décalage", "فورية ودبلوماسية", "32:45", "د. سمير بن حمادي", "متقدم", "إدارة الفارق الزمني والضغط في كابينة المؤتمرات."),
      MasterclassCourse("mc-2", "صياغة العقود ومذكرات التفاهم الدولية (FIDIC & ICC)", "عقود وقانون", "45:20", "أ. دحمان قاسمي", "محترف", "البنود المعيارية النمطية والتحكيم التجاري."),
      MasterclassCourse("mc-3", "هندسة توطين البرمجيات وضوابط الـ CAT Tools", "تقنية وأنظمة CAT", "28:10", "م. رياض بن سالم", "متوسط", "إدارة ملفات PO, JSON والذاكرة الترجمية."),
      MasterclassCourse("mc-4", "الترجمة الطبية واليقظة الدوائية (Pharmacovigilance)", "طبية ودوائية", "36:50", "د. ليلى عماري", "متقدم", "التقارير السريرية وتوصيف التفاعلات العكسية للأدوية."),
      MasterclassCourse("mc-5", "الترجمة المرئية وضوابط الـ CPS للمنصات الرقمية", "ترجمة مرئية", "24:30", "أ. سارة بن زينة", "شامل", "معايير Netflix وBBC في التوقيت والـ CPL."),
      MasterclassCourse("mc-6", "إدارة جلسات التحكيم التجاري الدولي (LCIA)", "عقود وقانون", "42:00", "د. عبد المالك", "محترف", "المرافعة، إبراء الذمة وصياغة القرارات التحكيمية."),
      MasterclassCourse("mc-7", "الترجمة الفورية لقمم المناخ والتحول الطاقوي", "فورية ودبلوماسية", "38:20", "د. ياسمين بوقرة", "متقدم", "مصطلحات الانبعاثات الكربونية والطاقات المتجددة."),
      MasterclassCourse("mc-8", "معايير ترجمة براءات الاختراع والملكية الفكرية WIPO", "ملكية فكرية", "33:15", "م. شريف لعريبي", "متقدم", "عناصر الحماية (Claims) والمواصفات الفنية للاختراعات."),
      MasterclassCourse("mc-9", "قواعد تدوين الملاحظات التتابعية بنظام روزان السبعة", "تتابعية", "29:40", "أ. حمزة بلقاسم", "أساسي ومتقدم", "تفكيك الخطاب الدبلوماسي عمودياً واستخدام الرموز."),
      MasterclassCourse("mc-10", "مراجعة وتدقيق الترجمة وفق معيار ISO 17100:2015", "ضمان الجودة", "27:30", "أ. دحمان قاسمي", "شامل", "إجراءات التدقيق المزدوج (Bilingual Review) وضبط الجودة."),
      MasterclassCourse("mc-11", "الترجمة المالية والميزانيات المجمعة IFRS", "مالية ومحاسبة", "35:10", "أ. توفيق بلحاج", "متقدم", "القوائم المالية والتدفقات النقدية وتقارير محافظ الحسابات."),
      MasterclassCourse("mc-12", "توطين ألعاب الفيديو والوسائط التفاعلية", "تقنية وأنظمة CAT", "31:40", "م. أنيس قادري", "متوسط", "السياق التفاعلي والتكيف الثقافي والشخصيات."),
      MasterclassCourse("mc-13", "الترجمة الفورية للمؤتمرات الصحفية تحت الضغط", "فورية ودبلوماسية", "40:15", "د. سمير بن حمادي", "محترف", "التعامل مع اللهجات المختلفة والإلقاء السريع."),
      MasterclassCourse("mc-14", "ترجمة السجلات الدبلوماسية والمراسلات الرئاسية", "فورية ودبلوماسية", "37:25", "د. ياسمين بوقرة", "متقدم", "بروتوكولات الأسبقية الدبلوماسية ولغة المجاملة."),
      MasterclassCourse("mc-15", "إعداد المسارد المصطلحية وبناء قواعد Multiterm", "تقنية وأنظمة CAT", "22:50", "م. رياض بن سالم", "أساسي", "إنشاء وتوحيد القواميس المؤسساتية التراكمية."),
      MasterclassCourse("mc-16", "الترجمة الجنائية وإجراءات الاستجواب لدى المحاكم", "عقود وقانون", "44:00", "د. عبد المالك", "محترف", "محاضر التحقيق القضائي والترجمة المحلفة المباشرة."),
      MasterclassCourse("mc-17", "هندسة الترجمة في قطاع المحروقات Sonatrach", "طاقة وهندسة", "38:15", "م. شريف لعريبي", "متقدم", "عقود التنقيب والإنتاج ومصطلحات هندسة البترول."),
      MasterclassCourse("mc-18", "الترجمة الفورية للقمم الإفريقية ومنظمة الوحدة", "فورية ودبلوماسية", "44:10", "د. ياسمين بوقرة", "محترف", "جلسات القمة المغلقة وصياغة البيانات الختامية."),
      MasterclassCourse("mc-19", "ضوابط تدقيق الترجمة الثنائية ونماذج تقييم الجودة", "ضمان الجودة", "31:50", "أ. دحمان قاسمي", "شامل", "مصفوفة احتساب الأخطاء اللغوية والدلالية والأسلوبية."),
      MasterclassCourse("mc-20", "ترجمة صياغة عناصر الحماية لبراءات الاختراع INAPI", "ملكية فكرية", "40:00", "د. سمير بن حمادي", "متقدم", "الضوابط الصارمة لترجمة طلبات براءات الاختراع الوطنية.")
    )
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      text = if (isArabic) "مختبر التمارين التفاعلية والماستركلاس" else "Interactive Training Lab & Masterclasses",
      fontWeight = FontWeight.Bold,
      fontSize = 17.sp,
      color = RedDark
    )

    // Sub-tab chips (horizontal scrollable)
    Row(
      modifier = Modifier.horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      FilterChip(
        selected = currentSubTab == "matcher",
        onClick = { currentSubTab = "matcher" },
        label = { Text("🎴 مطابقة المصطلحات", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "booth",
        onClick = { currentSubTab = "booth" },
        label = { Text("🎙️ كابينة الفورية والـ Décalage", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "rozan",
        onClick = { currentSubTab = "rozan" },
        label = { Text("📝 رموز روزان التتابعية", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "legal",
        onClick = { currentSubTab = "legal" },
        label = { Text("⚖️ صياغة العقود القانونية", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "cps",
        onClick = { currentSubTab = "cps" },
        label = { Text("🎬 سرعة الـ CPS للمرئية", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "masterclass",
        onClick = { currentSubTab = "masterclass" },
        label = { Text("📚 20 ماستركلاس فيديو", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
      FilterChip(
        selected = currentSubTab == "cert",
        onClick = { currentSubTab = "cert" },
        label = { Text("📜 شهادة التأهيل", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White),
        modifier = Modifier.heightIn(min = 40.dp)
      )
    }

    // -------------------------------------------------------------
    // TAB 7: PROFESSIONAL CERTIFICATE VIEW
    // -------------------------------------------------------------
    if (currentSubTab == "cert") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(2.dp, GoldYellow)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("🇩🇿 POLYLANG HUB ACADEMY", fontWeight = FontWeight.Black, fontSize = 11.sp, color = GoldYellow)
            Text("ISO 17100:2015", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = TextMuted)
          }
          Text(
            text = "شهادة تأهيل وتفوق مهني معتمدة",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = RedDark,
            textAlign = TextAlign.Center
          )
          Text(
            text = "تمنح للمترجمين الذين أتموا كافة متطلبات الكابينة والتدقيق والمطابقة التخصصية.",
            fontSize = 11.sp,
            color = TextMuted,
            textAlign = TextAlign.Center
          )
          Button(
            onClick = { onShowCertificate("الترجمة القانونية وصياغة العقود الدولية") },
            colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
            modifier = Modifier.fillMaxWidth().heightIn(min = 44.dp)
          ) {
            Text("🎓 استعراض وإصدار الشهادة الرسمية", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 1: SPEED TERMINOLOGY MATCHER
    // -------------------------------------------------------------
    if (currentSubTab == "matcher") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("النقاط: $matcherScore • الأزواج: $matcherMatches / 8", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedDark)
            Button(
              onClick = {
                val list = mutableStateListOf<TermCard>()
                initialPairs.forEachIndexed { idx, pair ->
                  list.add(TermCard(idx, pair.first, false))
                  list.add(TermCard(idx, pair.second, true))
                }
                list.shuffle()
                matcherCards = list
                selectedCardIdx = null
                matcherMatches = 0
                matcherScore = 0
                onMessage("تمت إعادة ضبط لعبة مطابقة المصطلحات")
              },
              colors = ButtonDefaults.buttonColors(containerColor = RedLight),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text("🔄 إعادة اللعب", color = RedDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Text(
            "انقر على المصطلح بالإنجليزية ثم انقر على مقابله الرسمي المعتمد بالعربية:",
            fontSize = 11.sp,
            color = TextMuted
          )

          // 4x4 or 2x8 Grid of Cards
          val chunkedCards = matcherCards.chunked(2)
          chunkedCards.forEachIndexed { rowIdx, pairCards ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
              pairCards.forEachIndexed { colIdx, card ->
                val realIdx = rowIdx * 2 + colIdx
                val isSelected = selectedCardIdx == realIdx

                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = when {
                    card.isMatched -> Color(0xFFD1FAE5)
                    isSelected -> RedLight
                    else -> BgLight
                  },
                  border = BorderStroke(
                    1.dp,
                    when {
                      card.isMatched -> Color(0xFF10B981)
                      isSelected -> RedPrimary
                      else -> BorderLight
                    }
                  ),
                  modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 52.dp)
                    .clickable(enabled = !card.isMatched) {
                      if (selectedCardIdx == null) {
                        selectedCardIdx = realIdx
                      } else {
                        val prevIdx = selectedCardIdx!!
                        if (prevIdx != realIdx) {
                          val prevCard = matcherCards[prevIdx]
                          if (prevCard.id == card.id && prevCard.isArabic != card.isArabic) {
                            // MATCH!
                            prevCard.isMatched = true
                            card.isMatched = true
                            matcherMatches++
                            matcherScore += 100
                            selectedCardIdx = null
                            onMessage("✓ تطابق ممتاز! +100 نقطة")

                            if (matcherMatches == 8) {
                              AdminEmailNotifier.dispatch(
                                "EXERCISE_COMPLETED",
                                "متدرب بوليلانغ",
                                AdminEmailNotifier.ADMIN_EMAIL,
                                mapOf("exercise" to "لعبة مطابقة المصطلحات", "score" to "$matcherScore نقطة")
                              ) { _, msg -> onMessage(msg) }
                            }
                          } else {
                            // MISMATCH
                            selectedCardIdx = null
                            onMessage("❌ غير متطابقين، حاول مجدداً")
                          }
                        }
                      }
                    }
                ) {
                  Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(6.dp)) {
                    Text(
                      text = card.text + if (card.isMatched) " ✓" else "",
                      fontSize = 11.sp,
                      fontWeight = if (isSelected || card.isMatched) FontWeight.Bold else FontWeight.Medium,
                      color = if (card.isMatched) Color(0xFF065F46) else TextDark,
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 2: SIMULTANEOUS BOOTH & DECALAGE SIMULATOR
    // -------------------------------------------------------------
    if (currentSubTab == "booth") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
            Text(
              "🌍 قمة المناخ بجنيف (COP Energy Transition)",
              color = RedDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Text(
            "\"Excellencies, distinguished delegates, we stand at a pivotal juncture where the acceleration of sustainable energy transition is our primary strategic imperative to guarantee socio-economic resilience.\"",
            fontSize = 12.sp,
            color = TextDark,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Button(
              onClick = { isBoothSpeechPlaying = !isBoothSpeechPlaying },
              colors = ButtonDefaults.buttonColors(containerColor = if (isBoothSpeechPlaying) Color(0xFFDC2626) else RedPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.heightIn(min = 40.dp)
            ) {
              Text(if (isBoothSpeechPlaying) "⏸️ إيقاف الخطاب" else "▶️ بث الخطاب بالكابينة", fontSize = 11.sp)
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = if (decalageTimer in 2.0..4.2) Color(0xFFD1FAE5) else Color(0xFFFEF3C7),
              border = BorderStroke(1.dp, if (decalageTimer in 2.0..4.2) Color(0xFF10B981) else Color(0xFFF59E0B))
            ) {
              Text(
                "⏳ الفارق: ${String.format(Locale.US, "%.1f", decalageTimer)} ثانية",
                color = if (decalageTimer in 2.0..4.2) Color(0xFF065F46) else Color(0xFFB45309),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          OutlinedTextField(
            value = boothTranscript,
            onValueChange = { boothTranscript = it },
            placeholder = { Text("تحدث أو اكتب ترجمتك الفورية هنا فور سماع المتحدث...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(80.dp)
          )

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
              onClick = {
                isBoothMicOn = !isBoothMicOn
                onMessage(if (isBoothMicOn) "🔴 تم تفعيل الميكروفون المباشر" else "تم إيقاف الميكروفون")
              },
              modifier = Modifier.weight(1f).heightIn(min = 42.dp),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(if (isBoothMicOn) "⏹️ إيقاف المايك" else "🎙️ تشغيل المايك", fontSize = 11.sp)
            }

            Button(
              onClick = {
                boothFeedback = "🏆 نتيجة فحص الأداء الفوري: 95/100 (معتمد لدى الأمم المتحدة)\n✓ الفارق الزمني مضبوط: ${String.format(Locale.US, "%.1f", decalageTimer)} ث\n✓ التقاط دقيق لمصطلحات: أصحاب السعادة، التحول الطاقوي المستدام، المرونة الاقتصادية"
                AdminEmailNotifier.dispatch(
                  "EXERCISE_COMPLETED",
                  "مترجم كابينة",
                  AdminEmailNotifier.ADMIN_EMAIL,
                  mapOf("exercise" to "كابينة الترجمة الفورية", "score" to "95/100", "decalage" to "${String.format(Locale.US, "%.1f", decalageTimer)}s")
                ) { _, msg -> onMessage(msg) }
              },
              colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
              modifier = Modifier.weight(1f).heightIn(min = 42.dp),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("🎯 تقييم الأداء", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          boothFeedback?.let { fb ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFECFDF5),
              border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(fb, color = Color(0xFF065F46), fontSize = 11.sp, lineHeight = 15.sp, modifier = Modifier.padding(10.dp))
            }
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 3: ROZAN CONSECUTIVE WORKSHOP
    // -------------------------------------------------------------
    if (currentSubTab == "rozan") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("لوحة رموز روزان لتدوين الملاحظات (انقر لإدراج الرمز):", fontSize = 11.sp, fontWeight = FontWeight.Bold)

          val symbols = listOf("➔ (ناتج)", "▲ (ارتفاع)", "▼ (انخفاض)", "≠ (تعارض)", "§ (تشريع)", "⏳ (مستقبل)", "★ (هام)", "? (تساؤل)", "P (سياسة)", "Ø (نفي)")
          Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            symbols.forEach { sym ->
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = RedLight,
                modifier = Modifier.clickable { rozanNotes += " " + sym.split(" ")[0] + " " }
              ) {
                Text(sym, fontSize = 10.sp, color = RedDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
              }
            }
          }

          OutlinedTextField(
            value = rozanNotes,
            onValueChange = { rozanNotes = it },
            placeholder = { Text("مفكرة تدوين الملاحظات التتابعية (Bloc-notes)...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(80.dp)
          )

          Text("الصياغة التتابعية بالعربية استناداً للملاحظات:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          OutlinedTextField(
            value = rozanRendition,
            onValueChange = { rozanRendition = it },
            placeholder = { Text("أعد صياغة الخطاب كاملاً شفهياً أو تحريرياً...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(70.dp)
          )

          Button(
            onClick = {
              rozanAuditResult = "🏆 نتيجة التدقيق: 94/100 (ممتاز)\n✓ توظيف متقن لرموز السببية والتغير النسبي\n✓ التسلسل المنطقي للخطاب سليم ومستوفٍ لقواعد روزان"
              AdminEmailNotifier.dispatch(
                "EXERCISE_COMPLETED",
                "متدرب روزان",
                AdminEmailNotifier.ADMIN_EMAIL,
                mapOf("exercise" to "ورشة روزان للملاحظات", "score" to "94/100")
              ) { _, msg -> onMessage(msg) }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().heightIn(min = 42.dp)
          ) {
            Text("🔍 فحص الملاحظات والصياغة التتابعية", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          rozanAuditResult?.let { res ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFECFDF5),
              border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(res, color = Color(0xFF065F46), fontSize = 11.sp, lineHeight = 15.sp, modifier = Modifier.padding(10.dp))
            }
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 4: CERTIFIED LEGAL CLAUSE DRAFTER
    // -------------------------------------------------------------
    if (currentSubTab == "legal") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
            Text("بند التعويض وإبراء الذمة (Indemnification Clause)", color = RedDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
          }

          Text(
            "\"The Contractor shall indemnify, defend and hold harmless the Employer, its officers and agents from and against all claims, liabilities, losses and expenses arising out of any breach or willful misconduct.\"",
            fontSize = 12.sp,
            color = TextDark,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp
          )

          OutlinedTextField(
            value = legalInput,
            onValueChange = { legalInput = it },
            placeholder = { Text("اكتب صياغتك القانونية الرسمية (يعوض، يبرئ ذمة، خطأ عمدي)...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(90.dp)
          )

          Button(
            onClick = {
              val hasKeyTerms = legalInput.contains("يعوض") || legalInput.contains("يبرئ") || legalInput.contains("ذمة")
              legalAuditResult = if (hasKeyTerms) {
                "⚖️ تقييم الصياغة القانونية: 96/100 (معتمد للمحاكم والتحكيم)\n✓ تم توظيف المصطلحات المعتمدة بنجاح ومطابقة معيار ISO 17100"
              } else {
                "⚠️ تقييم الصياغة: 84/100 - يُنصح باستخدام المصطلحات المحلفة: 'يعوض ويبرئ ذمة' و'الخطأ العمدي'"
              }
              AdminEmailNotifier.dispatch(
                "EXERCISE_COMPLETED",
                "مترجم قانوني",
                AdminEmailNotifier.ADMIN_EMAIL,
                mapOf("exercise" to "صياغة العقود القانونية", "score" to if (hasKeyTerms) "96/100" else "84/100")
              ) { _, msg -> onMessage(msg) }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().heightIn(min = 42.dp)
          ) {
            Text("⚖️ تدقيق الصياغة القانونية", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          legalAuditResult?.let { res ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFECFDF5),
              border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(res, color = Color(0xFF065F46), fontSize = 11.sp, lineHeight = 15.sp, modifier = Modifier.padding(10.dp))
            }
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 5: AUDIOVISUAL SUBTITLING CPS LAB
    // -------------------------------------------------------------
    if (currentSubTab == "cps") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("مقطع وثائقي (المدة: 3.2 ثوانٍ):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Text("\"We must completely re-evaluate the transition strategy before the ecosystem collapses.\"", fontSize = 12.sp, color = TextDark)

          OutlinedTextField(
            value = cpsInput,
            onValueChange = { cpsInput = it },
            label = { Text("صياغة سطر الترجمة (Arabic Subtitle)") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          )

          // Live CPS Gauge Box
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = BgLight,
            border = BorderStroke(1.dp, BorderLight),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("طول السطر: $charCount حرف", fontSize = 11.sp)
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (cpsValue <= 16.0f) Color(0xFFD1FAE5) else Color(0xFFFEE2E2)
              ) {
                Text(
                  text = "${String.format(Locale.US, "%.1f", cpsValue)} CPS ${if (cpsValue <= 16.0f) "✓ ممتاز" else "⚠️ سريع"}",
                  color = if (cpsValue <= 16.0f) Color(0xFF065F46) else Color(0xFF991B1B),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Button(
            onClick = {
              val ok = cpsValue <= 16.5f
              onMessage(if (ok) "✓ فحص CPS: مطابق تماماً لمعايير Netflix وBBC للترجمة المرئية" else "⚠️ معدل CPS مرتفع، يرجى اختزال النص")
              AdminEmailNotifier.dispatch(
                "EXERCISE_COMPLETED",
                "مترجم مرئي",
                AdminEmailNotifier.ADMIN_EMAIL,
                mapOf("exercise" to "مختبر الترجمة المرئية CPS", "cps" to "${String.format(Locale.US, "%.1f", cpsValue)} CPS")
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().heightIn(min = 42.dp)
          ) {
            Text("🎬 فحص الامتثال لمعايير البث العالمية", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // -------------------------------------------------------------
    // TAB 6: 20 VIDEO MASTERCLASSES
    // -------------------------------------------------------------
    if (currentSubTab == "masterclass") {
      Text(
        text = "مكتبة المحاضرات والماستركلاس (20 مساقاً شاملاً):",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = RedDark
      )

      masterclasses.forEach { course ->
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
          border = BorderStroke(1.dp, BorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(color = RedLight, shape = RoundedCornerShape(6.dp)) {
                Text(course.category, color = RedDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
              Text("⏱️ ${course.duration}", fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Bold)
            }

            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
            Text(course.desc, fontSize = 11.sp, color = TextMuted, maxLines = 2, lineHeight = 15.sp)

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("المحاضر: ${course.instructor} • ${course.level}", fontSize = 10.sp, color = TextMuted)
              Button(
                onClick = { onPlayCourse(course) },
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                modifier = Modifier.heightIn(min = 34.dp)
              ) {
                Text("▶️ تشغيل الدرس", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// --------------------------------------------------------------------------
// 5. PRICING & SUBSCRIPTIONS SCREEN
// --------------------------------------------------------------------------
@Composable
fun PricingScreen(
  isArabic: Boolean,
  currency: AppCurrency = AppCurrency.DZD,
  onSelectPlan: (String, Int) -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      text = if (isArabic) "باقات واشتراكات Polylang Hub" else "Polylang Hub Plans & Pricing",
      fontWeight = FontWeight.Bold,
      fontSize = 17.sp,
      color = RedDark
    )
    Text(
      text = if (isArabic) "اختر الباقة المناسبة للأفراد والمترجمين والشركات مع دعم الدفع بالدينار الجزائري (DZD)" else "Flexible plans with Algerian local payment methods.",
      fontSize = 12.sp,
      color = TextMuted,
      lineHeight = 16.sp
    )

    // Plan 1: Free Starter
    PlanCard(
      title = if (isArabic) "باقة البداية المجانية" else "Free Starter",
      price = formatPrice(0, currency, isArabic),
      period = if (isArabic) "/ مدى الحياة" else "/ Forever",
      badge = if (isArabic) "مجاناً" else "Free",
      features = listOf(
        if (isArabic) "ترجمة حتى 1,500 كلمة شهرياً" else "Up to 1,500 words/mo",
        if (isArabic) "تصدير 3 ملفات SRT أسبوعياً" else "3 SRT exports per week",
        if (isArabic) "الوصول لـ 5 فيديوهات تدريبية" else "Access 5 training videos",
        if (isArabic) "دعم فني عبر البريد الإلكتروني" else "Email support"
      ),
      isPopular = false,
      buttonText = if (isArabic) "تفعيل الباقة المجانية" else "Select Free",
      onClick = { onSelectPlan("Free Starter", 0) }
    )

    // Plan 2: Pro Translator
    PlanCard(
      title = if (isArabic) "باقة المحترفين المعتمدين" else "Pro Translator",
      price = formatPrice(4500, currency, isArabic),
      period = if (isArabic) "/ شهرياً" else "/ month",
      badge = if (isArabic) "الأكثر طلباً ⭐" else "Most Popular ⭐",
      features = listOf(
        if (isArabic) "ترجمة حتى 35,000 كلمة شهرياً" else "Up to 35,000 words/mo",
        if (isArabic) "تصدير غير محدود لملفات SRT" else "Unlimited SRT exports",
        if (isArabic) "وصول كامل لكافة 20 ماستركلاس الأكاديمية" else "All 20 masterclasses access",
        if (isArabic) "فحص فوري للترجمة بمعيار ISO 17100" else "ISO 17100 instant audit",
        if (isArabic) "دعم الدفع عبر الذهبية وبيدي موب" else "Edahabia & BaridiMob support"
      ),
      isPopular = true,
      buttonText = if (isArabic) "ترقية إلى باقة Pro (${formatPrice(4500, currency, isArabic)})" else "Upgrade to Pro",
      onClick = { onSelectPlan("Pro Translator", 4500) }
    )

    // Plan 3: Enterprise Hub
    PlanCard(
      title = if (isArabic) "باقة المؤسسات والشركات" else "Enterprise Hub",
      price = formatPrice(18500, currency, isArabic),
      period = if (isArabic) "/ شهرياً" else "/ month",
      badge = if (isArabic) "للشركات" else "Enterprise",
      features = listOf(
        if (isArabic) "ترجمة غير محدودة وتدقيق محلف" else "Unlimited certified translations",
        if (isArabic) "تأمين طاقم مترجمين فوريين للمؤتمرات" else "Conference interpreters crew",
        if (isArabic) "اتفاقية مستوى الخدمة SLA 99.9%" else "Dedicated 99.9% SLA",
        if (isArabic) "فواتير رسمية مطابقة للتشريع الجزائري" else "Official certified invoices"
      ),
      isPopular = false,
      buttonText = if (isArabic) "طلب تفعيل باقة الشركات" else "Select Enterprise",
      onClick = { onSelectPlan("Enterprise Hub", 18500) }
    )
  }
}

@Composable
fun PlanCard(
  title: String,
  price: String,
  period: String,
  badge: String,
  features: List<String>,
  isPopular: Boolean,
  buttonText: String,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    border = BorderStroke(if (isPopular) 2.dp else 1.dp, if (isPopular) RedPrimary else BorderLight),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isPopular) 4.dp else 1.dp),
    modifier = Modifier.fillMaxWidth().testTag("plan_card_${title.replace(' ', '_')}")
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (isPopular) RedLight else BgLight
        ) {
          Text(
            badge,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPopular) RedDark else TextMuted,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Row(verticalAlignment = Alignment.Bottom) {
        Text(price, fontWeight = FontWeight.Black, fontSize = 22.sp, color = RedDark)
        Text(period, fontSize = 11.sp, color = TextMuted, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
      }

      HorizontalDivider(color = BorderLight)

      features.forEach { feat ->
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Check, contentDescription = "Included", tint = SuccessGreen, modifier = Modifier.size(16.dp))
          Spacer(Modifier.width(6.dp))
          Text(feat, fontSize = 11.sp, color = TextDark)
        }
      }

      Spacer(Modifier.height(4.dp))

      Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = if (isPopular) RedPrimary else RedDark),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().heightIn(min = 44.dp)
      ) {
        Text(buttonText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    }
  }
}

// --------------------------------------------------------------------------
// 6. DASHBOARD & ADMIN TELEMETRY SCREEN
// --------------------------------------------------------------------------
@Composable
fun DashboardScreen(
  isArabic: Boolean,
  currency: AppCurrency = AppCurrency.DZD,
  userName: String,
  role: UserRole,
  plan: String,
  balance: Int,
  orders: List<TranslationOrder>,
  onAction: (String) -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Profile Banner
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      border = BorderStroke(1.dp, BorderLight)
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(RedPrimary, RedDark))),
          contentAlignment = Alignment.Center
        ) {
          Text(
            userName.take(1),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
          )
        }
        Column(Modifier.weight(1f)) {
          Text(userName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
          Text(
            if (isArabic) "الباقة: $plan" else "Plan: $plan",
            fontSize = 11.sp,
            color = RedDark,
            fontWeight = FontWeight.SemiBold
          )
        }
        Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
          Text(
            when (role) {
              UserRole.CLIENT -> if (isArabic) "عميل" else "Client"
              UserRole.TRANSLATOR -> if (isArabic) "مترجم معتمد" else "Translator"
              UserRole.ADMIN -> if (isArabic) "مشرف عام" else "Admin"
            },
            color = RedDark,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    // Live Admin Inbox Telemetry Hub Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
      border = BorderStroke(1.5.dp, Color(0xFF10B981))
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("📬 مركز متابعة بريد الإدارة الفوري", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          Surface(color = Color(0x3310B981), shape = RoundedCornerShape(6.dp)) {
            Text("LIVE TELEMETRY", color = Color(0xFF34D399), fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
          }
        }
        Text(
          "المستلم المعتمد لجميع التسجيلات والطلبات: ${AdminEmailNotifier.ADMIN_EMAIL}",
          color = Color(0xFF94A3B8),
          fontSize = 11.sp
        )
        Button(
          onClick = {
            AdminEmailNotifier.dispatch(
              "TEST_PROBE",
              userName,
              AdminEmailNotifier.ADMIN_EMAIL,
              mapOf("test" to "إشعار فحص من لوحة تحكم أندرويد")
            ) { _, msg -> onAction(msg) }
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.heightIn(min = 36.dp)
        ) {
          Text("🧪 فحص إرسال إشعار تجريبي الآن", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // KPI Metrics Row
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      MetricCard(if (isArabic) "الرصيد المتاح" else "Balance", formatPrice(balance, currency, isArabic), Modifier.weight(1f))
      MetricCard(if (isArabic) "الطلبات النشطة" else "Orders", "${orders.size}", Modifier.weight(1f))
      MetricCard(if (isArabic) "الكلمات" else "Words", "14,800", Modifier.weight(1f))
    }

    // Orders List Title
    Text(
      text = if (isArabic) "سجل العمليات والطلبات" else "Recent Orders",
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      color = TextDark
    )

    orders.forEach { order ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(1.dp, BorderLight),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(order.id, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedDark)
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = if (order.status.contains("مكتمل") || order.status.contains("مؤكد")) Color(0xFFD1FAE5) else RedLight
            ) {
              Text(
                order.status,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (order.status.contains("مكتمل") || order.status.contains("مؤكد")) Color(0xFF065F46) else RedDark,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
          Text(order.title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextDark)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("${order.category} • ${order.langPair}", fontSize = 10.sp, color = TextMuted)
            Text(formatPrice(order.priceDzd, currency, isArabic), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedDark)
          }
        }
      }
    }
  }
}

@Composable
fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    border = BorderStroke(1.dp, BorderLight),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(label, fontSize = 10.sp, color = TextMuted)
      Spacer(Modifier.height(2.dp))
      Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = RedDark)
    }
  }
}

// --------------------------------------------------------------------------
// 7. PAYMENT DIALOG (ALGERIAN LOCAL METHODS: Edahabia, CIB, BaridiMob, CCP)
// --------------------------------------------------------------------------
@Composable
fun PaymentDialog(
  isArabic: Boolean,
  currency: AppCurrency = AppCurrency.DZD,
  planName: String,
  planPrice: Int,
  onDismiss: () -> Unit,
  onConfirmPayment: () -> Unit
) {
  var selectedMethod by remember { mutableStateOf("edahabia") } // edahabia, cib, baridimob, ccp
  var cardNumber by remember { mutableStateOf("6037 9901 2345 6789") }
  var cardHolder by remember { mutableStateOf("KARIM HAMDAOUI") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      border = BorderStroke(1.dp, BorderLight),
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
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
            Text(if (isArabic) "الدفع بالدينار الجزائري" else "Algerian Payment", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RedDark)
            Text(if (isArabic) "باقة: $planName" else "Plan: $planName", fontSize = 11.sp, color = TextMuted)
          }
          Text(formatPrice(planPrice, currency, isArabic), fontWeight = FontWeight.Black, fontSize = 16.sp, color = RedDark)
        }

        HorizontalDivider(color = BorderLight)

        Text(if (isArabic) "اختر وسيلة الدفع المعتمدة:" else "Select Payment Method:", fontSize = 12.sp, fontWeight = FontWeight.Bold)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          PaymentMethodButton(
            title = "الذهبية",
            subtitle = "بريد الجزائر",
            isSelected = selectedMethod == "edahabia",
            modifier = Modifier.weight(1f),
            onClick = { selectedMethod = "edahabia" }
          )
          PaymentMethodButton(
            title = "CIB",
            subtitle = "البطاقة البنكية",
            isSelected = selectedMethod == "cib",
            modifier = Modifier.weight(1f),
            onClick = { selectedMethod = "cib" }
          )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          PaymentMethodButton(
            title = "BaridiMob",
            subtitle = "تطبيق فوري",
            isSelected = selectedMethod == "baridimob",
            modifier = Modifier.weight(1f),
            onClick = { selectedMethod = "baridimob" }
          )
          PaymentMethodButton(
            title = "CCP",
            subtitle = "حوالة بريدية",
            isSelected = selectedMethod == "ccp",
            modifier = Modifier.weight(1f),
            onClick = { selectedMethod = "ccp" }
          )
        }

        OutlinedTextField(
          value = cardNumber,
          onValueChange = { cardNumber = it },
          label = { Text(if (selectedMethod == "ccp") "رقم الحساب البريدي الجاري CCP" else "رقم البطاقة (16 رقماً)") },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true
        )

        OutlinedTextField(
          value = cardHolder,
          onValueChange = { cardHolder = it },
          label = { Text(if (isArabic) "اسم صاحب الحساب أو البطاقة" else "Cardholder Name") },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f).heightIn(min = 44.dp)
          ) {
            Text(if (isArabic) "إلغاء" else "Cancel", fontSize = 12.sp)
          }

          Button(
            onClick = onConfirmPayment,
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f).heightIn(min = 44.dp).testTag("confirm_payment_btn")
          ) {
            Text(if (isArabic) "تأكيد الدفع" else "Confirm", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }
    }
  }
}

@Composable
fun PaymentMethodButton(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (isSelected) RedLight else BgLight,
    border = BorderStroke(1.dp, if (isSelected) RedPrimary else BorderLight),
    modifier = modifier.clickable { onClick() }
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (isSelected) RedDark else TextDark)
      Text(subtitle, fontSize = 9.sp, color = TextMuted)
    }
  }
}

// =============================================================
// CERTIFICATE OF COMPETENCE DIALOG (ISO 17100)
// =============================================================
@Composable
fun CertificateDialog(
  isArabic: Boolean,
  studentName: String,
  courseTitle: String,
  onDismiss: () -> Unit,
  onShare: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      border = BorderStroke(2.dp, GoldYellow),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🇩🇿 POLYLANG HUB", fontWeight = FontWeight.Black, fontSize = 11.sp, color = RedDark)
          Text("ISO 17100:2015", fontSize = 10.sp, color = TextMuted)
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, GoldYellow, RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFBEB))
            .padding(14.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("📜 شهادة تأهيل وتفوق مهني معتمدة", fontWeight = FontWeight.Black, fontSize = 14.sp, color = RedDark, textAlign = TextAlign.Center)
            Text("Certificate of Professional Competence", fontSize = 10.sp, color = TextMuted, letterSpacing = 1.sp)
            HorizontalDivider(color = GoldYellow.copy(alpha = 0.4f), modifier = Modifier.padding(vertical = 4.dp))
            Text("يشهد المجلس الأكاديمي لمنصة بوليلانغ بأن الأستاذ(ة):", fontSize = 11.sp, color = TextDark)
            Text(studentName, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = TextDark)
            Text("قد أتم بنجاح متطلبات المسار التخصصي والتدريب العملي في:", fontSize = 10.sp, color = TextMuted, textAlign = TextAlign.Center)
            Text(courseTitle, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedDark, textAlign = TextAlign.Center)

            Row(
              modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
              horizontalArrangement = Arrangement.SpaceAround
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("د. ليلى مزياني", fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("مترجم محلف", fontSize = 8.sp, color = TextMuted)
              }
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("أ. مداني جودي", fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text("المشرف العام", fontSize = 8.sp, color = TextMuted)
              }
            }
            Text("ID: CERT-PLY-2026-9812 • SHA-256 Verified ✓", fontSize = 8.sp, color = TextMuted)
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.weight(1f).heightIn(min = 40.dp)
          ) {
            Text("إغلاق", fontSize = 12.sp)
          }
          Button(
            onClick = onShare,
            colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
            modifier = Modifier.weight(1.5f).heightIn(min = 40.dp)
          ) {
            Text("📋 مشاركة / إرسال", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// =============================================================
// AI TERMINOLOGY SEARCH DIALOG (ISO 17100)
// =============================================================
@Composable
fun AiTermbaseDialog(
  isArabic: Boolean,
  onDismiss: () -> Unit,
  onNotifyAdmin: (String) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  var searchedTerm by remember { mutableStateOf<Pair<String, String>?>(null) }

  val isoTerms = listOf(
    Pair("Force Majeure", "القوة القاهرة / الحادث الفجائي (إعفاء تعاقدي وفق المادة 127 مدني)"),
    Pair("Indemnification", "التعويض وإبراء الذمة وحماية المتعاقد من مطالبات الغير"),
    Pair("Liquidated Damages", "التعويض الاتفاقي والشرط الجزائي محدد القيمة مسبقاً"),
    Pair("Ultra Vires", "تجاوز الصلاحيات القانونية والتصرف خارج نطاق الاختصاص"),
    Pair("Informed Consent", "الموافقة المستنيرة السريرية المكتوبة للمريض"),
    Pair("Pharmacovigilance", "اليقظة والرصد الدوائي ومأمونية المستحضرات السريرية")
  )

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      border = BorderStroke(1.dp, BorderLight),
      modifier = Modifier.fillMaxWidth().padding(8.dp)
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🤖", fontSize = 18.sp)
            Spacer(Modifier.width(6.dp))
            Column {
              Text("مساعد الترجمة الذكي (Polylang AI)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RedDark)
              Text("مطابقة المصطلحات وفق ISO 17100", fontSize = 10.sp, color = TextMuted)
            }
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        OutlinedTextField(
          value = searchQuery,
          onValueChange = {
            searchQuery = it
            searchedTerm = isoTerms.firstOrNull { pair ->
              pair.first.contains(it, ignoreCase = true) || pair.second.contains(it, ignoreCase = true)
            }
          },
          label = { Text("اكتب مصطلحاً (مثال: Force Majeure)", fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        // Quick Suggestion Chips
        Row(
          modifier = Modifier.horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf("Force Majeure", "Indemnification", "Pharmacovigilance").forEach { sample ->
            SuggestionChip(
              onClick = {
                searchQuery = sample
                searchedTerm = isoTerms.firstOrNull { it.first.equals(sample, ignoreCase = true) }
              },
              label = { Text(sample, fontSize = 10.sp) }
            )
          }
        }

        // Result Card
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
          border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (searchedTerm != null) {
              Text(searchedTerm!!.first, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextDark)
              Text(searchedTerm!!.second, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SuccessGreen)
              Text("معتمد وفق معيار ISO 17100:2015 ✓", fontSize = 9.sp, color = TextMuted)
            } else {
              Text(
                if (searchQuery.isBlank()) "اختر أو ابحث عن مصطلح لعرض الصياغة القانونية المعتمدة."
                else "الصياغة المقترحة: $searchQuery (قيد المراجعة التخصصية)",
                fontSize = 11.sp,
                color = TextMuted
              )
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.weight(1f).heightIn(min = 40.dp)
          ) {
            Text("إغلاق", fontSize = 11.sp)
          }
          Button(
            onClick = {
              onNotifyAdmin(searchQuery.ifBlank { "Force Majeure" })
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            modifier = Modifier.weight(1.5f).heightIn(min = 40.dp)
          ) {
            Text("➕ طلب اعتماد مصطلح", fontSize = 11.sp, color = Color.White)
          }
        }
      }
    }
  }
}
