package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(dynamicColor = false) {
        PolylangHubApp()
      }
    }
  }
}

// Retained for screenshot & unit testing compatibility
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

enum class AppTab {
  HOME, SERVICES, SUBTITLING, ACADEMY, PRICING, DASHBOARD
}

enum class UserRole {
  CLIENT, TRANSLATOR
}

data class TranslationOrder(
  val id: String,
  val title: String,
  val category: String,
  val langPair: String,
  val wordCount: Int,
  val priceDzd: Int,
  val status: String
)

data class SrtCue(
  val id: Int,
  val start: String,
  val end: String,
  val sourceText: String,
  val subtitleText: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolylangHubApp() {
  var isArabic by remember { mutableStateOf(true) }
  var currentTab by remember { mutableStateOf(AppTab.HOME) }
  var currentRole by remember { mutableStateOf(UserRole.CLIENT) }
  var userName by remember { mutableStateOf("كريم حمداوي") }
  var userPlan by remember { mutableStateOf("Pro Translator") }
  var userBalance by remember { mutableStateOf(8500) }

  // State for orders
  val orders = remember {
    mutableStateListOf(
      TranslationOrder("PL-8821", "عقد توريد معدات طاقة شمسية", "قانونية", "EN ➔ AR", 2400, 9600, "مكتمل ومسلّم"),
      TranslationOrder("PL-8940", "دليل مستخدم نظام إدارة السدود", "تقنية", "FR ➔ AR", 1850, 7400, "قيد الترجمة والتدقيق"),
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

  // Snackbars
  val snackbarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

  CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
    Scaffold(
      modifier = Modifier.fillMaxSize().statusBarsPadding(),
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
                  fontSize = 17.sp,
                  color = RedDark
                )
                Text(
                  text = if (isArabic) "بوابة الترجمة والتدريب" else "Translation & Training Hub",
                  fontSize = 10.sp,
                  color = TextMuted
                )
              }
            }
          },
          actions = {
            // Language Switcher button
            OutlinedButton(
              onClick = {
                isArabic = !isArabic
                coroutineScope.launch {
                  snackbarHostState.showSnackbar(if (isArabic) "تم التبديل للغة العربية" else "Switched to English")
                }
              },
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.padding(end = 6.dp).testTag("lang_toggle_btn"),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, RedContainer)
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
                .clickable {
                  currentRole = if (currentRole == UserRole.CLIENT) UserRole.TRANSLATOR else UserRole.CLIENT
                  userName = if (currentRole == UserRole.CLIENT) "كريم حمداوي (عميل)" else "د. ليلى مزياني (مترجم)"
                  coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                      if (currentRole == UserRole.CLIENT) "وضع العميل نشط" else "وضع المترجم المعتمد نشط"
                    )
                  }
                }
                .testTag("role_toggle_pill")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Icon(
                  if (currentRole == UserRole.CLIENT) Icons.Default.Person else Icons.Default.School,
                  contentDescription = "Role",
                  tint = RedDark,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                  text = if (currentRole == UserRole.CLIENT) (if (isArabic) "عميل" else "Client") else (if (isArabic) "مترجم" else "Translator"),
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
          tonalElevation = 8.dp
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
            label = { Text(if (isArabic) "SRT" else "SRT", fontSize = 10.sp) },
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
          .padding(innerPadding)
      ) {
        when (currentTab) {
          AppTab.HOME -> HomeScreen(
            isArabic = isArabic,
            onNavigate = { currentTab = it }
          )
          AppTab.SERVICES -> ServicesScreen(
            isArabic = isArabic,
            onOrderCreated = { newOrder ->
              orders.add(0, newOrder)
              coroutineScope.launch {
                snackbarHostState.showSnackbar(if (isArabic) "تم تأكيد طلب الترجمة ${newOrder.id} بنجاح!" else "Order ${newOrder.id} confirmed!")
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
            onMessage = { msg -> coroutineScope.launch { snackbarHostState.showSnackbar(msg) } }
          )
          AppTab.PRICING -> PricingScreen(
            isArabic = isArabic,
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

    // Payment Dialog for Algerian local payment methods
    if (showPaymentDialog) {
      PaymentDialog(
        isArabic = isArabic,
        planName = pendingPlanName,
        planPrice = pendingPlanPrice,
        onDismiss = { showPaymentDialog = false },
        onConfirmPayment = {
          userPlan = pendingPlanName
          showPaymentDialog = false
          coroutineScope.launch {
            snackbarHostState.showSnackbar(
              if (isArabic) "تم الدفع وتفعيل $pendingPlanName بنجاح!" else "Payment completed. $pendingPlanName activated!"
            )
          }
          currentTab = AppTab.DASHBOARD
        }
      )
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
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // AI Robot Presenter Welcome Card
    var isRobotSpeaking by remember { mutableStateOf(false) }
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE53935)),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
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
              text = if (isArabic) "🤖 روبوت الترحيب الذكي (Polylang AI)" else "🤖 Polylang AI Host",
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
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44E53935)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = if (isArabic)
                "\"مرحباً بكم في بوليلانغ هوب (Polylang Hub)! منصتكم المعتمدة للترجمة المحلفة، الترجمة الفورية للمؤتمرات، وتدريب المترجمين وفق المعايير الدولية.\""
              else
                "\"Welcome to Polylang Hub! Your certified platform for sworn legal translation, conference interpretation, and professional language training.\"",
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
                modifier = Modifier.height(30.dp)
              ) {
                Text(
                  text = if (isRobotSpeaking) "🔊 صوت الروبوت نشط" else "🔊 استمع للترحيب",
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
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier.fillMaxWidth().testTag("hero_banner_card")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Surface(
          color = Color(0x33E53935),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = if (isArabic) "✨ المنصة الأولى المعتمدة للترجمة والتدريب" else "✨ #1 Certified Translation & Training SaaS",
            color = Color(0xFFFCA5A5),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Spacer(Modifier.height(12.dp))

        Text(
          text = if (isArabic) "حلول ترجمة احترافية فائقة الدقة وأكاديمية تدريب معتمدة" else "Ultra-Accurate Translations & Certified Academy",
          color = Color.White,
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          lineHeight = 28.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
          text = if (isArabic)
            "نقدم خدمات الترجمة القانونية، التقنية والمؤسساتية، إدارة ملفات SRT، وأكاديمية تفاعلية لإعداد المترجمين."
          else
            "Legal, technical and institutional translations, real-time SRT subtitling, and interactive interpreter training.",
          color = Color(0xFFCBD5E1),
          fontSize = 13.sp,
          lineHeight = 18.sp
        )

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = { onNavigate(AppTab.SERVICES) },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f).testTag("home_order_btn")
          ) {
            Text(if (isArabic) "📄 طلب ترجمة" else "📄 Request Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
          OutlinedButton(
            onClick = { onNavigate(AppTab.ACADEMY) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66FFFFFF)),
            modifier = Modifier.weight(1f).testTag("home_academy_btn")
          ) {
            Text(if (isArabic) "🎓 الأكاديمية" else "🎓 Academy", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(color = Color(0x22FFFFFF))
        Spacer(Modifier.height(12.dp))

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

    Text(
      text = if (isArabic) "أقسام ومنظومة Polylang Hub" else "Polylang Hub Modules",
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
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
    Text(value, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
    Text(label, color = Color(0xFF94A3B8), fontSize = 10.sp)
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
    modifier = modifier.clickable { onClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(RedLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = title, tint = RedDark, modifier = Modifier.size(20.dp))
      }
      Spacer(Modifier.height(10.dp))
      Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
      Spacer(Modifier.height(4.dp))
      Text(desc, fontSize = 11.sp, color = TextMuted, lineHeight = 14.sp)
    }
  }
}

// --------------------------------------------------------------------------
// 2. SERVICES & ORDER CALCULATOR SCREEN
// --------------------------------------------------------------------------
@Composable
fun ServicesScreen(
  isArabic: Boolean,
  onOrderCreated: (TranslationOrder) -> Unit
) {
  val scrollState = rememberScrollState()

  var selectedCategory by remember { mutableStateOf("قانونية ورسمية") }
  var sourceLang by remember { mutableStateOf("الإنجليزية") }
  var targetLang by remember { mutableStateOf("العربية") }
  var wordCountText by remember { mutableStateOf("850") }
  var clientNotes by remember { mutableStateOf("") }
  var attachedFileName by remember { mutableStateOf<String?>(null) }

  val wordCount = wordCountText.toIntOrNull() ?: 500
  val ratePerWord = when (selectedCategory) {
    "قانونية ورسمية" -> 4.0
    "تقنية وهندسية" -> 3.8
    "مؤسساتية وأكاديمية" -> 4.2
    else -> 4.5
  }
  val estimatedPrice = (wordCount * ratePerWord).toInt()
  val deliveryTime = if (wordCount > 3000) "3 - 5 أيام" else if (wordCount > 1000) "48 - 72 ساعة" else "24 - 48 ساعة"

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = if (isArabic) "حاسبة وطلب ترجمة المستندات" else "Document Translation Calculator",
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      color = RedDark
    )

    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
          if (isArabic) "المجال التخصصي" else "Specialized Domain",
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

        Text(if (isArabic) "عدد الكلمات التقديري" else "Estimated Word Count", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
          value = wordCountText,
          onValueChange = { if (it.all { char -> char.isDigit() }) wordCountText = it },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("word_count_input"),
          singleLine = true
        )

        // Dropzone simulator
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = RedLight,
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              attachedFileName = "Contract_Agreement_2026.pdf"
            }
            .padding(vertical = 4.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, RedContainer)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.CloudUpload, contentDescription = "Upload", tint = RedDark, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(4.dp))
            Text(
              text = if (attachedFileName == null)
                (if (isArabic) "انقر لإرفاق المستند (PDF, DOCX)" else "Tap to attach document (PDF, DOCX)")
              else
                "📄 $attachedFileName (محدد ✓)",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = RedDark
            )
          }
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
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(if (isArabic) "مدة التسليم المقدرة:" else "Delivery:", color = TextMuted, fontSize = 13.sp)
          Text(deliveryTime, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(if (isArabic) "التدقيق اللغوي والمطابقة:" else "Quality Audit:", color = TextMuted, fontSize = 13.sp)
          Text(if (isArabic) "مشمول مجاناً ✓" else "Included ✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        HorizontalDivider(color = BorderLight, modifier = Modifier.padding(vertical = 4.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
          Text(if (isArabic) "التكلفة التقديرية:" else "Total Price:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
          Text("$estimatedPrice دج", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = RedDark)
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
              priceDzd = estimatedPrice,
              status = "قيد المراجعة"
            )
            onOrderCreated(order)
          },
          colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("confirm_order_btn")
        ) {
          Text(if (isArabic) "🚀 تأكيد وتقديم طلب الترجمة" else "🚀 Confirm & Submit Order", fontWeight = FontWeight.Bold)
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
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          if (isArabic) "محرر الترجمة التحتية (SRT Studio)" else "SRT Subtitle Studio",
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp,
          color = RedDark
        )
        Text(
          if (isArabic) "مزامنة التوقيت وتحميل ملف .SRT" else "Sync timecodes & export .srt",
          fontSize = 11.sp,
          color = TextMuted
        )
      }

      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        IconButton(
          onClick = onAddCue,
          modifier = Modifier.size(36.dp).background(RedLight, CircleShape).testTag("add_cue_btn")
        ) {
          Icon(Icons.Default.Add, contentDescription = "Add Cue", tint = RedDark, modifier = Modifier.size(20.dp))
        }
        Button(
          onClick = onExport,
          colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
          shape = RoundedCornerShape(12.dp),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
          modifier = Modifier.testTag("export_srt_btn")
        ) {
          Text(if (isArabic) "تصدير SRT" else "Export SRT", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Video Dubbing Mockup Box
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = Color(0xFF0F172A),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(
            if (isArabic) "🎙️ الصوت المعاكس (Voice Dubbing Preview)" else "🎙️ Audio Dubbing Preview",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
          Text(
            if (isArabic) "نادية (فصحى احترافية - وثائقي) | مطابقة 98%" else "Nadia (Arabic Docu) | 98% lip-sync",
            color = Color(0xFF94A3B8),
            fontSize = 10.sp
          )
        }
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = RedPrimary,
          modifier = Modifier.clickable { onExport() }
        ) {
          Text("▶ تشغيل", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        }
      }
    }

    // Cues List
    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(cues, key = { it.id }) { cue ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = CircleShape,
                  color = RedLight,
                  modifier = Modifier.size(24.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text("#${cue.id}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = RedDark)
                  }
                }
                Spacer(Modifier.width(8.dp))
                Text(
                  text = "${cue.start} ➔ ${cue.end}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TextMuted
                )
              }
              IconButton(
                onClick = { onDeleteCue(cue) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.LightGray, modifier = Modifier.size(16.dp))
              }
            }
            Spacer(Modifier.height(4.dp))
            Text(cue.sourceText, fontSize = 12.sp, color = TextDark)
            Spacer(Modifier.height(2.dp))
            Text(cue.subtitleText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RedDark)
          }
        }
      }
    }
  }
}

// --------------------------------------------------------------------------
// 4. TRAINING ACADEMY SCREEN
// --------------------------------------------------------------------------
@Composable
fun AcademyScreen(
  isArabic: Boolean,
  onMessage: (String) -> Unit
) {
  var activeTrack by remember { mutableStateOf("oral") } // "oral" or "written"
  var oralNotes by remember { mutableStateOf("") }
  var writtenInput by remember { mutableStateOf("") }
  var showFeedback by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      if (isArabic) "أكاديمية التدريب الشفهي والتحريري" else "Oral & Written Training Academy",
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      color = RedDark
    )

    // Track toggle
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      FilterChip(
        selected = activeTrack == "oral",
        onClick = { activeTrack = "oral" },
        label = { Text(if (isArabic) "🎙️ الترجمة الفورية والشفهية" else "🎙️ Oral Interpretation", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White)
      )
      FilterChip(
        selected = activeTrack == "written",
        onClick = { activeTrack = "written" },
        label = { Text(if (isArabic) "✍️ الترجمة التحريرية" else "✍️ Written Translation", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = RedPrimary, selectedLabelColor = Color.White)
      )
    }

    if (activeTrack == "oral") {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
            Text(
              if (isArabic) "تمرين #04: كلمة مؤتمر الطاقة الرقمي" else "Exercise #04: Energy Summit Speech",
              color = RedDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Text(
            if (isArabic)
              "استمع للمتحدث وسجل ملاحظاتك باستخدام رموز روزان (Rozan Note-taking Symbols) ثم اضغط فحص."
            else
              "Listen to the speaker and jot down notes using Rozan shorthand symbols, then evaluate.",
            fontSize = 12.sp,
            color = TextMuted,
            lineHeight = 16.sp
          )

          // Audio player simulator
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = BgLight,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("🔊 Speech: 00:45 / 02:15", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              Button(
                onClick = { onMessage("🔊 Playing speaker: Digital transformation is our strategic imperative...") },
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
              ) {
                Text("استماع", fontSize = 11.sp)
              }
            }
          }

          // Shorthand Symbol Palette
          Text(if (isArabic) "لوحة رموز تدوين الملاحظات السريعة (Rozan):" else "Rozan Shorthand Symbols:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          val symbols = listOf("➔ (ناتج)", "▲ (زيادة)", "▼ (انخفاض)", "≠ (تعارض)", "§ (تشريع)", "⏳ (مستقبل)", "★ (هام)")
          Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            symbols.forEach { sym ->
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = RedLight,
                modifier = Modifier.clickable { oralNotes += " $sym " }
              ) {
                Text(sym, fontSize = 10.sp, color = RedDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
              }
            }
          }

          OutlinedTextField(
            value = oralNotes,
            onValueChange = { oralNotes = it },
            placeholder = { Text(if (isArabic) "اكتب ملاحظاتك ورموزك هنا أثناء الاستماع..." else "Jot your notes here...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(100.dp).testTag("oral_notes_input")
          )

          Button(
            onClick = { showFeedback = true },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("eval_oral_btn")
          ) {
            Text(if (isArabic) "🎯 فحص وتقييم الترجمة والملاحظات" else "🎯 Evaluate Performance", fontWeight = FontWeight.Bold)
          }
        }
      }

      // Feedback Panel
      AnimatedVisibility(visible = showFeedback) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0))
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              if (isArabic) "🏆 درجة الأداء العام: 94 / 100 (ممتاز)" else "🏆 Score: 94 / 100 (Excellent)",
              color = Color(0xFF065F46),
              fontWeight = FontWeight.ExtraBold,
              fontSize = 15.sp
            )
            Text("✔️ الدقة الدلالية (Accuracy): 96% - التقاط دقيق للأرقام والنسب", fontSize = 11.sp, color = Color(0xFF047857))
            Text("✔️ جودة الملاحظات (Rozan Symbols): تم توظيف أسهم السببية بمهارة", fontSize = 11.sp, color = Color(0xFF047857))
            Spacer(Modifier.height(4.dp))
            Text(
              "الترجمة النموذجية: 'لقد أكد فخامة الرئيس أن تسريع وتيرة التحول الرقمي يمثل الركيزة الاستراتيجية لتأمين إمدادات الطاقة.'",
              fontSize = 11.sp,
              color = TextDark,
              lineHeight = 15.sp
            )
          }
        }
      }
    } else {
      // Written Track
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Surface(color = RedLight, shape = RoundedCornerShape(8.dp)) {
            Text("صياغة عقود تجارية دولية (ICC & FIDIC)", color = RedDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
          }

          Text(
            "Source: 'Neither party shall be held liable for any delay resulting from circumstances beyond its reasonable control, including acts of God.'",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
          )

          OutlinedTextField(
            value = writtenInput,
            onValueChange = { writtenInput = it },
            placeholder = { Text(if (isArabic) "اكتب صياغتك القانونية هنا (القوة القاهرة...)..." else "Enter translation...") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(100.dp)
          )

          Button(
            onClick = {
              onMessage(if (isArabic) "✓ التدقيق: صياغة قانونية متينة (92/100) - تم اعتماد مصطلح 'القوة القاهرة' بنجاح" else "Audit: 92/100 Solid legal draft")
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(if (isArabic) "🔍 تدقيق ومراجعة الصياغة" else "🔍 Audit Translation", fontWeight = FontWeight.Bold)
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
  onSelectPlan: (String, Int) -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      if (isArabic) "باقات الاشتراكات والترقية" else "Subscription Plans",
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      color = RedDark
    )
    Text(
      if (isArabic) "ادعم عملك مع قبول البطاقة الذهبية، CIB والمحفظة الرقمية" else "Supports local payment: Edahabia, CIB, Digital Wallet",
      fontSize = 11.sp,
      color = TextMuted
    )

    // Plan 1: Free
    PlanCard(
      name = if (isArabic) "الباقة الأساسية" else "Free Starter",
      price = "0 دج",
      period = if (isArabic) "/ شهرياً" else "/ mo",
      isPopular = false,
      features = listOf("1,500 كلمة شهرياً", "5 تمارين تدريبية أساسية", "تصدير SRT حتى 10 دقائق"),
      buttonLabel = if (isArabic) "البدء مجاناً" else "Get Started Free",
      isPrimary = false,
      onClick = { onSelectPlan("Free Starter", 0) }
    )

    // Plan 2: Pro (Featured)
    PlanCard(
      name = if (isArabic) "المترجم المحترف (Pro)" else "Pro Translator",
      price = "4,500 دج",
      period = if (isArabic) "/ شهرياً" else "/ mo",
      isPopular = true,
      features = listOf("وصول غير محدود للأكاديمية", "أولوية استلام مشاريع العملاء", "تصدير SRT ودبلجة غير محدودة", "شارة مترجم معتمد موثوق"),
      buttonLabel = if (isArabic) "⚡ ترقية حسابي الآن" else "⚡ Upgrade to Pro",
      isPrimary = true,
      onClick = { onSelectPlan("Pro Translator", 4500) }
    )

    // Plan 3: Enterprise
    PlanCard(
      name = if (isArabic) "المؤسسات والشركات" else "Enterprise Hub",
      price = "18,000 دج",
      period = if (isArabic) "/ شهرياً" else "/ mo",
      isPopular = false,
      features = listOf("ترجمة غير محدودة شهرياً", "مدير حساب مخصص وتسليم فوري", "فواتير رسمية مطابقة للمحاسبة", "اتفاقية سرية مستوى الشركات"),
      buttonLabel = if (isArabic) "طلب اشتراك مؤسساتي" else "Enterprise Request",
      isPrimary = false,
      onClick = { onSelectPlan("Enterprise Hub", 18000) }
    )
  }
}

@Composable
fun PlanCard(
  name: String,
  price: String,
  period: String,
  isPopular: Boolean,
  features: List<String>,
  buttonLabel: String,
  isPrimary: Boolean,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    border = androidx.compose.foundation.BorderStroke(if (isPopular) 2.dp else 1.dp, if (isPopular) RedPrimary else BorderLight),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isPopular) 4.dp else 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      if (isPopular) {
        Surface(color = RedPrimary, shape = RoundedCornerShape(12.dp)) {
          Text("الأكثر طلباً للمحترفين ★", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
        }
        Spacer(Modifier.height(8.dp))
      }

      Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
      Row(verticalAlignment = Alignment.Bottom) {
        Text(price, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = if (isPopular) RedDark else TextDark)
        Text(period, fontSize = 12.sp, color = TextMuted, modifier = Modifier.padding(bottom = 4.dp, start = 4.dp))
      }

      Spacer(Modifier.height(10.dp))

      features.forEach { feat ->
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
          Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          Spacer(Modifier.width(6.dp))
          Text(feat, fontSize = 12.sp, color = TextDark)
        }
      }

      Spacer(Modifier.height(14.dp))

      Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = if (isPrimary) RedPrimary else RedLight, contentColor = if (isPrimary) Color.White else RedDark),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(buttonLabel, fontWeight = FontWeight.Bold, fontSize = 13.sp)
      }
    }
  }
}

// --------------------------------------------------------------------------
// 6. DASHBOARD SCREEN
// --------------------------------------------------------------------------
@Composable
fun DashboardScreen(
  isArabic: Boolean,
  userName: String,
  role: UserRole,
  plan: String,
  balance: Int,
  orders: List<TranslationOrder>,
  onAction: (String) -> Unit
) {
  val isTranslator = role == UserRole.TRANSLATOR

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // User header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = if (isTranslator) (if (isArabic) "لوحة المترجم المعتمد" else "Translator Dashboard") else (if (isArabic) "لوحة العميل" else "Client Dashboard"),
          fontWeight = FontWeight.Bold,
          fontSize = 17.sp,
          color = RedDark
        )
        Text(userName, fontSize = 12.sp, color = TextMuted)
      }
      Surface(shape = RoundedCornerShape(12.dp), color = RedLight) {
        Text(plan, color = RedDark, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
      }
    }

    // Metric cards row
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      MetricCard(
        label = if (isTranslator) "مشاريع متاحة" else "طلبات نشطة",
        value = if (isTranslator) "7" else orders.count { it.status != "مكتمل ومسلّم" }.toString(),
        modifier = Modifier.weight(1f)
      )
      MetricCard(
        label = if (isTranslator) "كلمات منجزة" else "كلمات مستلمة",
        value = if (isTranslator) "38,500" else "14,250",
        modifier = Modifier.weight(1f)
      )
      MetricCard(
        label = if (isArabic) "المحفظة" else "Wallet",
        value = "$balance دج",
        modifier = Modifier.weight(1f)
      )
    }

    Text(
      if (isArabic) "المشاريع والطلبات المسجلة" else "Active Orders & Projects",
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      color = TextDark
    )

    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(orders, key = { it.id }) { order ->
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text(order.id, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = RedDark)
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (order.status.contains("مكتمل")) Color(0xFFDCFCE7) else RedLight
              ) {
                Text(
                  order.status,
                  color = if (order.status.contains("مكتمل")) Color(0xFF166534) else RedDark,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Spacer(Modifier.height(4.dp))
            Text(order.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
            Spacer(Modifier.height(2.dp))
            Text("${order.langPair} • ${order.wordCount} كلمة • ${order.priceDzd} دج", fontSize = 11.sp, color = TextMuted)

            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
              OutlinedButton(
                onClick = {
                  onAction(if (isTranslator) "تم فتح استوديو الترجمة للمشروع ${order.id}" else "جاري تحميل الحزمة المعتمدة لـ ${order.id}")
                },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
              ) {
                Text(if (isTranslator) "الاطلاع والترجمة" else "تحميل الوثيقة", fontSize = 11.sp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(label, fontSize = 10.sp, color = TextMuted)
      Spacer(Modifier.height(4.dp))
      Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = RedDark)
    }
  }
}

// --------------------------------------------------------------------------
// 7. LOCAL PAYMENT DIALOG (EDAHABIA, CIB, WALLET)
// --------------------------------------------------------------------------
@Composable
fun PaymentDialog(
  isArabic: Boolean,
  planName: String,
  planPrice: Int,
  onDismiss: () -> Unit,
  onConfirmPayment: () -> Unit
) {
  var selectedMethod by remember { mutableStateOf("edahabia") }
  var cardNumber by remember { mutableStateOf("6280 5840 1928 3746") }
  var phone by remember { mutableStateOf("0560 12 34 50") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
          Text(if (isArabic) "إتمام الدفع الإلكتروني" else "Secure Payment", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RedDark)
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
          }
        }

        Text("$planName - $planPrice دج", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Text(if (isArabic) "اختر وسيلة الدفع المحلية:" else "Select payment method:", fontSize = 11.sp, color = TextMuted)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          PaymentMethodButton(
            title = "البطاقة الذهبية",
            selected = selectedMethod == "edahabia",
            onClick = { selectedMethod = "edahabia" },
            modifier = Modifier.weight(1f)
          )
          PaymentMethodButton(
            title = "بطاقة CIB",
            selected = selectedMethod == "cib",
            onClick = { selectedMethod = "cib" },
            modifier = Modifier.weight(1f)
          )
          PaymentMethodButton(
            title = "المحفظة الرقمية",
            selected = selectedMethod == "wallet",
            onClick = { selectedMethod = "wallet" },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = cardNumber,
          onValueChange = { cardNumber = it },
          label = { Text(if (isArabic) "رقم البطاقة" else "Card Number", fontSize = 11.sp) },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text(if (isArabic) "رقم الهاتف لاستلام رمز OTP" else "Phone for OTP SMS", fontSize = 11.sp) },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Button(
          onClick = onConfirmPayment,
          colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().testTag("confirm_payment_btn")
        ) {
          Text(if (isArabic) "🔒 تأكيد الدفع وتفعيل الباقة" else "🔒 Confirm Payment", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun PaymentMethodButton(
  title: String,
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = if (selected) RedLight else BgLight,
    border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) RedPrimary else BorderLight),
    modifier = modifier.clickable { onClick() }
  ) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)) {
      Text(
        text = title,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = if (selected) RedDark else TextDark,
        textAlign = TextAlign.Center
      )
    }
  }
}
