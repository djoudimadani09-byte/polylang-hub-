package com.example.ui

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

data class MadaniChatMessage(
    val id: String,
    val sender: String, // "madani" or "user"
    val text: String,
    val time: String,
    val domainTag: String? = null,
    val hasAudio: Boolean = true
)

data class DocumentScanSample(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val docType: String,
    val sourceLang: String,
    val targetLang: String,
    val extractedText: String,
    val swornDraft: String,
    val terminologyAlerts: List<String>,
    val swornChecklist: List<Pair<String, Boolean>>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MadaniAgentDialog(
    isArabic: Boolean,
    avatarConfig: MadaniAvatarConfig = MadaniAvatarConfig(),
    onOpenCustomizer: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    // 1. Text to Speech Engine for Madani's Voice
    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }
    var isSpeaking by remember { mutableStateOf(false) }
    var activeSpeakingText by remember { mutableStateOf<String?>(null) }

    DisposableEffect(context) {
        var tts: TextToSpeech? = null
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Initialize with Arabic or English
                tts?.language = if (isArabic) Locale("ar") else Locale.US
            }
        }
        ttsEngine = tts
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    fun speakText(text: String, locale: Locale = Locale("ar")) {
        ttsEngine?.let { engine ->
            engine.language = locale
            isSpeaking = true
            activeSpeakingText = text
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "madani_voice_${System.currentTimeMillis()}")
        }
    }

    fun stopSpeaking() {
        ttsEngine?.stop()
        isSpeaking = false
        activeSpeakingText = null
    }

    // 2. Active Module Tab in Madani Dialog
    var selectedModule by remember { mutableIntStateOf(0) }

    // 3. Chat History & Input
    var chatInput by remember { mutableStateOf("") }
    var isMicRecording by remember { mutableStateOf(false) }
    var isThinking by remember { mutableStateOf(false) }

    val chatMessages = remember {
        mutableStateListOf(
            MadaniChatMessage(
                id = "m1",
                sender = "madani",
                text = if (isArabic)
                    "أهلاً بك! أنا مدني (Madani)، وكيلك الذكي المتخصص في تعليم اللغات والترجمة الفورية المحلفة. أستطيع التحدث معك صوتياً، وتدريبك على النطق، وفحص وثائقك بالصورة، وترجمة نصوصك فورياً وفق أعلى المعايير الدولية. كيف أساعدك اليوم؟"
                else
                    "Welcome! I am Madani, your premier AI language learning and certified interpretation mentor. I can interact via voice, text, and visual document inspection. How can I assist you today?",
                time = "10:00",
                domainTag = "مستشار الذكاء الاصطناعي"
            )
        )
    }

    // 4. Live Translation Partner State
    var transSourceLang by remember { mutableStateOf("الإنجليزية") }
    var transTargetLang by remember { mutableStateOf("العربية") }
    var transInputText by remember { mutableStateOf("The arbitral award shall be deemed final, unappealable, and binding upon both contracting parties.") }
    var transOutputText by remember { mutableStateOf("يُعتبر الحكم التحكيمي نهائياً وغير قابل للطعن، ومُلزماً لكلا الطرفين المتعاقدين.") }
    var transDomainDetected by remember { mutableStateOf("قانوني - تحكيم تجاري دولي") }
    var transTerminologyNotes by remember {
        mutableStateOf(
            listOf(
                "Arbitral award = حكم تحكيمي (تجنب ترجمتها جائزة)",
                "Unappealable = غير قابل للطعن / محصن من الاستئناف",
                "Binding upon = ملزم لكلا الطرفين قانوناً"
            )
        )
    }

    // 5. Document & Image Scanner State
    val sampleDocuments = remember {
        listOf(
            DocumentScanSample(
                id = "doc1",
                titleAr = "عقد زواج رسمي مصدق",
                titleEn = "Official Marriage Certificate",
                docType = "حالة مدنية وأحوال شخصية",
                sourceLang = "العربية (الجمهورية الجزائرية)",
                targetLang = "الفرنسية (République Française)",
                extractedText = "الجمهورية الجزائرية الديمقراطية الشعبية - وزارة العدل - بلدية الجزائر الوسطى: عقد زواج رقم 142/2024. بحضور الموثق المحلف والشهود العدول وفقاً لأحكام قانون الأسرة.",
                swornDraft = "RÉPUBLIQUE ALGÉRIENNE DÉMOCRATIQUE ET POPULAIRE - Ministère de la Justice - Commune d'Alger-Centre : Acte de mariage N° 142/2024. En présence du notaire assermenté et des témoins légaux, conformément aux dispositions du Code de la famille.",
                terminologyAlerts = listOf(
                    "تجنب الترجمة الحرفية لمصطلح 'شهود عدول' واستخدم 'témoins légaux instrumentaires'",
                    "رقم العقد وصيغة البلدية تخضع للنموذج الموحد للحالة المدنية الفرنسية"
                ),
                swornChecklist = listOf(
                    "الختم الجمهوري الدائري (Sceau de la République)" to true,
                    "توقيع ضابط الحالة المدنية (Officier d'état civil)" to true,
                    "طابع التمغة الجبائي (Timbre fiscal 200 DA)" to true,
                    "صيغة المصادقة المحلفة الرسمية (Formule d'assermentation)" to true
                )
            ),
            DocumentScanSample(
                id = "doc2",
                titleAr = "دبلوم مهندس دولة في الإعلام الآلي",
                titleEn = "State Engineer Diploma in CS",
                docType = "شهادات أكاديمية ومؤهلات",
                sourceLang = "الفرنسية (USTHB)",
                targetLang = "الإنجليزية (Equivalency US/UK)",
                extractedText = "Université des Sciences et de la Technologie Houari Boumediene - Faculté d'Informatique : Vu les délibérations du jury, le Diplôme d'Ingénieur d'État est décerné avec Mention Très Bien.",
                swornDraft = "Houari Boumediene University of Sciences and Technology (USTHB) - Faculty of Computer Science : Upon the deliberations of the examining board, the State Engineer Degree in Computer Science is hereby awarded with High Honors.",
                terminologyAlerts = listOf(
                    "Ingénieur d'État تعادل نظامياً Master of Science in Engineering (5-year curriculum)",
                    "Mention Très Bien تُترجم إلى High Honors / Magna Cum Laude"
                ),
                swornChecklist = listOf(
                    "ختم عميد الكلية ورئيس الجامعة" to true,
                    "رقم التسجيل الأكاديمي الوطني الموحد" to true,
                    "صيغة الترجمة المعتمدة لمعادلة الشهادات (ENIC-NARIC)" to true
                )
            ),
            DocumentScanSample(
                id = "doc3",
                titleAr = "عقد تأسيس شركة وتفويض تجاري",
                titleEn = "Articles of Association & Power of Attorney",
                docType = "قانون الشركات والتجارة الدولية",
                sourceLang = "الإنجليزية (UK Companies House)",
                targetLang = "العربية (السجل التجاري الجزائري)",
                extractedText = "Memorandum and Articles of Association of Polylang Global LLC. The Board of Directors hereby irrevocably grants full power of attorney to the Managing Director to execute binding agreements.",
                swornDraft = "عقد التأسيس والنظام الأساسي لشركة بوليلانغ غلوبال ذ.م.م. يمنح مجلس الإدارة بموجب هذا سند توكيل رسمي وتفويضاً عاماً غير قابل للإلغاء للمدير التنفيذي لإبرام وتوقيع الاتفاقيات الملزمة.",
                terminologyAlerts = listOf(
                    "Articles of Association = النظام الأساسي للشركة",
                    "Irrevocably = دون رجعة / غير قابل للإلغاء أو الرجوع فيه",
                    "Power of attorney = وكالة رسمية / تفويض قانوني موثق"
                ),
                swornChecklist = listOf(
                    "شهادة أبوستيل الصادرة من الخارجية (Apostille Hague 1961)" to true,
                    "التصديق القنصلي لسفارة الجزائر بلندن" to true,
                    "ختم وتوقيع المترجم المحلف المعتمد لدى المحاكم" to true
                )
            )
        )
    }
    var selectedDocIdx by remember { mutableIntStateOf(0) }
    val currentDoc = sampleDocuments[selectedDocIdx]

    Dialog(
        onDismissRequest = {
            stopSpeaking()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .testTag("madani_ai_dialog_root"),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                // Top Agent Header with Avatar & Voice Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        // High Quality Customizable Avatar
                        MadaniAvatarIcon(
                            config = avatarConfig,
                            size = 54.dp,
                            isSpeaking = isSpeaking,
                            onClick = onOpenCustomizer
                        )

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Madani AI | مدني",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp,
                                    color = RedPrimary
                                )
                                SuggestionChip(
                                    onClick = onOpenCustomizer,
                                    label = { Text("تخصيص المظهر 🎨", fontSize = 9.sp, fontWeight = FontWeight.Bold) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = GoldYellow.copy(alpha = 0.2f),
                                        labelColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.height(24.dp)
                                )
                            }
                            Text(
                                text = if (isSpeaking) "🎙️ يتحدث الآن بصوته الطبيعي..." else "🟢 متصل • رفيقك في تعلم اللغات والترجمة الفورية",
                                fontSize = 11.5.sp,
                                color = if (isSpeaking) RedPrimary else SuccessGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Voice Mute / Palette Customizer / Close Button
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = onOpenCustomizer, modifier = Modifier.testTag("btn_customizer_header")) {
                            Icon(Icons.Default.Palette, contentDescription = "Customize Icon", tint = GoldYellow)
                        }
                        if (isSpeaking) {
                            IconButton(onClick = { stopSpeaking() }) {
                                Icon(Icons.Default.VolumeOff, contentDescription = "Stop Voice", tint = RedPrimary)
                            }
                        }
                        IconButton(onClick = {
                            stopSpeaking()
                            onDismiss()
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close Madani AI")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Navigation Tabs between Madani's Multimodal Superpowers
                TabRow(
                    selectedTabIndex = selectedModule,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedModule == 0,
                        onClick = { selectedModule = 0 },
                        text = {
                            Text(
                                text = if (isArabic) "💬 محادثة وصوت" else "Chat & Voice",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        },
                        icon = { Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedModule == 1,
                        onClick = { selectedModule = 1 },
                        text = {
                            Text(
                                text = if (isArabic) "🔄 شريك الترجمة" else "Live Partner",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        },
                        icon = { Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedModule == 2,
                        onClick = { selectedModule = 2 },
                        text = {
                            Text(
                                text = if (isArabic) "📷 فحص المستندات" else "Vision Scan",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        },
                        icon = { Icon(Icons.Default.DocumentScanner, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                    Tab(
                        selected = selectedModule == 3,
                        onClick = { selectedModule = 3 },
                        text = {
                            Text(
                                text = if (isArabic) "💡 نصائح الكابينة" else "Master Tips",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        },
                        icon = { Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Module Content
                when (selectedModule) {
                    0 -> {
                        // =========================================================================
                        // MODULE 0: CHAT & VOICE WITH MADANI
                        // =========================================================================
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            // Quick Prompts Row
                            val quickPrompts = listOf(
                                "كيف أترجم Force Majeure؟",
                                "نطق المصطلحات الدبلوماسية",
                                "تجنب أفخاخ الأصدقاء المزيفين",
                                "قواعد التحكم في Décalage",
                                "ترجمة شهادة ميلاد معتمدة",
                                "نصائح لاجتياز مسابقة المترجمين"
                            )

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(quickPrompts) { prompt ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .clickable {
                                                chatInput = prompt
                                            },
                                        color = RedPrimary.copy(alpha = 0.08f),
                                        border = BorderStroke(1.dp, RedPrimary.copy(alpha = 0.25f))
                                    ) {
                                        Text(
                                            text = prompt,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = RedPrimary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Chat Messages List
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(chatMessages) { msg ->
                                    val isMadani = msg.sender == "madani"
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = if (isMadani) Arrangement.Start else Arrangement.End
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(
                                                topStart = 16.dp,
                                                topEnd = 16.dp,
                                                bottomStart = if (isMadani) 4.dp else 16.dp,
                                                bottomEnd = if (isMadani) 16.dp else 4.dp
                                            ),
                                            color = if (isMadani)
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                            else
                                                RedPrimary,
                                            modifier = Modifier.widthIn(max = 300.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                if (isMadani) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = "Madani 🎙️",
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = RedPrimary
                                                        )
                                                        IconButton(
                                                            onClick = { speakText(msg.text) },
                                                            modifier = Modifier.size(24.dp)
                                                        ) {
                                                            Icon(
                                                                Icons.Default.VolumeUp,
                                                                contentDescription = "Read aloud",
                                                                tint = RedPrimary,
                                                                modifier = Modifier.size(16.dp)
                                                            )
                                                        }
                                                    }
                                                    Spacer(Modifier.height(4.dp))
                                                }

                                                Text(
                                                    text = msg.text,
                                                    fontSize = 12.5.sp,
                                                    lineHeight = 18.sp,
                                                    color = if (isMadani) MaterialTheme.colorScheme.onSurface else Color.White
                                                )

                                                Spacer(Modifier.height(4.dp))
                                                Text(
                                                    text = msg.time,
                                                    fontSize = 9.sp,
                                                    color = if (isMadani) MaterialTheme.colorScheme.onSurfaceVariant else Color.White.copy(alpha = 0.7f),
                                                    modifier = Modifier.align(Alignment.End)
                                                )
                                            }
                                        }
                                    }
                                }

                                if (isThinking) {
                                    item {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                            Spacer(Modifier.width(8.dp))
                                            Text("مدني يُحلل السؤال لغوياً ويُعد الصياغة...", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Chat Input & Mic
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = {
                                            isMicRecording = !isMicRecording
                                            if (isMicRecording) {
                                                chatInput = "كيف أصيغ بند التحكيم التجاري الدولي؟"
                                            }
                                        },
                                        modifier = Modifier.testTag("madani_mic_btn")
                                    ) {
                                        Icon(
                                            imageVector = if (isMicRecording) Icons.Default.MicOff else Icons.Default.Mic,
                                            contentDescription = "Voice Input",
                                            tint = if (isMicRecording) Color.Red else RedPrimary
                                        )
                                    }

                                    TextField(
                                        value = chatInput,
                                        onValueChange = { chatInput = it },
                                        placeholder = { Text(if (isArabic) "اكتب أو تحدث مع مدني..." else "Type or talk to Madani...", fontSize = 12.sp) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("madani_input_text"),
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        singleLine = false,
                                        maxLines = 3
                                    )

                                    IconButton(
                                        onClick = {
                                            if (chatInput.isNotBlank()) {
                                                val userQuery = chatInput.trim()
                                                chatMessages.add(
                                                    MadaniChatMessage(
                                                        id = "u_${System.currentTimeMillis()}",
                                                        sender = "user",
                                                        text = userQuery,
                                                        time = "الآن"
                                                    )
                                                )
                                                chatInput = ""
                                                isThinking = true

                                                coroutineScope.launch {
                                                    delay(600)
                                                    isThinking = false
                                                    val replyText = generateMadaniSmartReply(userQuery, isArabic)
                                                    chatMessages.add(
                                                        MadaniChatMessage(
                                                            id = "m_${System.currentTimeMillis()}",
                                                            sender = "madani",
                                                            text = replyText,
                                                            time = "الآن",
                                                            domainTag = "توجيه لغوي"
                                                        )
                                                    )
                                                    // Auto speak the response
                                                    speakText(replyText)
                                                }
                                            }
                                        },
                                        enabled = chatInput.isNotBlank(),
                                        modifier = Modifier.testTag("madani_send_btn")
                                    ) {
                                        Icon(Icons.Default.Send, contentDescription = "Send", tint = RedPrimary)
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // =========================================================================
                        // MODULE 1: LIVE TRANSLATION PARTNER (شريك الترجمة الفورية المباشر)
                        // =========================================================================
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Language Pair Switcher
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = RedPrimary.copy(alpha = 0.1f),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = transSourceLang,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = RedPrimary,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }

                                IconButton(onClick = {
                                    val tmp = transSourceLang
                                    transSourceLang = transTargetLang
                                    transTargetLang = tmp
                                    val tmpTxt = transInputText
                                    transInputText = transOutputText
                                    transOutputText = tmpTxt
                                }) {
                                    Icon(Icons.Default.SwapHoriz, contentDescription = "Swap Languages", tint = RedPrimary)
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SuccessGreen.copy(alpha = 0.1f),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = transTargetLang,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = SuccessGreen,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }

                            // Source Text Box
                            OutlinedTextField(
                                value = transInputText,
                                onValueChange = {
                                    transInputText = it
                                    // Trigger quick translation
                                    if (it.contains("contract", ignoreCase = true) || it.contains("award", ignoreCase = true)) {
                                        transDomainDetected = "قانوني - عقود وتحكيم تجاري"
                                        transOutputText = "يُعتبر هذا الاتفاق ملزماً ونافذاً في مواجهة كافة الأطراف اعتباراً من تاريخ التوقيع."
                                    } else {
                                        transDomainDetected = "عام وأكاديمي"
                                        transOutputText = "النص المترجم بدقة واحترافية وفق المعجم التخصصي المعتمد."
                                    }
                                },
                                label = { Text("النص المصدر للترجمة الفورية") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("madani_partner_source"),
                                maxLines = 4,
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Instant Translation Result Box
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "✨ ترجمة مدني المعتمدة (ISO 17100):",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.5.sp,
                                            color = SuccessGreen
                                        )
                                        Row {
                                            IconButton(
                                                onClick = { speakText(transOutputText) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.VolumeUp, contentDescription = "Play Pronunciation", tint = SuccessGreen)
                                            }
                                            IconButton(
                                                onClick = {
                                                    clipboardManager.setText(AnnotatedString(transOutputText))
                                                },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy Translation", tint = RedPrimary)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = transOutputText,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 20.sp
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))
                                    SuggestionChip(
                                        onClick = {},
                                        label = { Text("المجال المرصود: $transDomainDetected", fontSize = 10.sp) }
                                    )
                                }
                            }

                            // Terminology Breakdown Card
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = GoldYellow.copy(alpha = 0.12f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "🔍 تدقيق المصطلحات الدقيقة من مدني:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.5.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    transTerminologyNotes.forEach { note ->
                                        Row(verticalAlignment = Alignment.Top) {
                                            Text("• ", fontWeight = FontWeight.Bold, color = RedPrimary)
                                            Text(note, fontSize = 11.sp, lineHeight = 15.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // =========================================================================
                        // MODULE 2: VISION DOCUMENT & IMAGE SCANNER (فحص المستندات بالصورة)
                        // =========================================================================
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "📑 اختر نموذج وثيقة للفحص البصري الفوري أو التقط صورة:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp
                            )

                            // Document Preset Selector
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(sampleDocuments.size) { idx ->
                                    val doc = sampleDocuments[idx]
                                    val isSel = selectedDocIdx == idx
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable { selectedDocIdx = idx },
                                        color = if (isSel) RedPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = if (isSel) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                                    ) {
                                        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                                            Text(
                                                text = doc.titleAr,
                                                fontSize = 11.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = doc.docType,
                                                fontSize = 9.5.sp,
                                                color = if (isSel) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            // Visual Scan Inspector Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "📷 تحليل المستند البصري: ${currentDoc.titleAr}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = RedPrimary
                                        )
                                        IconButton(
                                            onClick = { speakText(currentDoc.swornDraft) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.VolumeUp, contentDescription = "Listen to draft", tint = RedPrimary)
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text("النص المستخرج عبر الفحص البصري (OCR):", fontSize = 10.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Spacer(Modifier.height(3.dp))
                                            Text(currentDoc.extractedText, fontSize = 11.5.sp, lineHeight = 16.sp)
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = SuccessGreen.copy(alpha = 0.1f),
                                        border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text("مسوّدة الترجمة المحلفة المعتمدة الصادرة من مدني:", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                            Spacer(Modifier.height(3.dp))
                                            Text(currentDoc.swornDraft, fontSize = 11.5.sp, lineHeight = 16.sp)
                                        }
                                    }

                                    // Sworn Checklist
                                    Text("معايير التحقق والتوثيق المحلف:", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                                    currentDoc.swornChecklist.forEach { (item, ok) ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                                            Text(item, fontSize = 10.5.sp)
                                        }
                                    }

                                    // Terminology Pitfalls
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = GoldYellow.copy(alpha = 0.15f)
                                    ) {
                                        Column(modifier = Modifier.padding(8.dp)) {
                                            Text("⚠️ تنبيهات مدني الاحترافية لتفادي الأخطاء الشائعة:", fontWeight = FontWeight.Bold, fontSize = 10.5.sp)
                                            currentDoc.terminologyAlerts.forEach { alert ->
                                                Text("• $alert", fontSize = 10.sp, lineHeight = 14.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // =========================================================================
                        // MODULE 3: MADANI'S MASTERCLASS TIPS (حقيبة نصائح مدني الذهبية)
                        // =========================================================================
                        val masterTips = listOf(
                            Triple(
                                "إدارة الفارق الزمني (Décalage) في الكابينة",
                                "الترجمة الفورية للمؤتمرات",
                                "لا تتسرع في الترجمة قبل أن يكتمل المعنى الدلالي في الجملة (Verb-Object). حافظ على فارق زمني يتراوح بين 3 إلى 5 ثوانٍ، واستخدم تقنية الـ Chunking لتفادي انقطاع النفس والارتباك."
                            ),
                            Triple(
                                "تفكيك مصطلحات القانون العام (Common Law) واللاتيني",
                                "الترجمة القانونية وصياغة العقود",
                                "المصطلحات مثل 'Without prejudice' و 'Force Majeure' و 'Indemnity' لها حمولة قانونية دقيقة. لا تترجمها حرفياً بل استند إلى صياغة العقود المعتمدة في الدولة المستهدفة."
                            ),
                            Triple(
                                "منهجية تدوين الملاحظات في الترجمة التعاقبية (Note-taking)",
                                "الترجمة التعاقبية والدبلوماسية",
                                "استخدم الرموز والأسهم والتفريع العمودي بدلاً من الكلمات الكاملة. ركز على الأفعال والروابط المنطقية والأرقام، وتجنب الكتابة باللغة المصدر؛ دوّن الأفكار مباشرة بلغة الإخراج."
                            ),
                            Triple(
                                "تأسيس وصيانة قواعد المصطلحات (Termbases) وبرامج CAT",
                                "تكنولوجيا وهندسة الترجمة",
                                "احرص على إنشاء مسرد موحد لكل عميل ومشروع وفق معيار TBX. استخدم برامج SDL Trados و MemoQ مع تفعيل الفحص الآلي لمطابقة الأرقام والأسماء."
                            )
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(masterTips) { (title, domain, advice) ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = title,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = RedPrimary
                                            )
                                            IconButton(
                                                onClick = { speakText("$title. $advice") },
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Icon(Icons.Default.VolumeUp, contentDescription = "Read tip", tint = RedPrimary)
                                            }
                                        }

                                        SuggestionChip(
                                            onClick = {},
                                            label = { Text(domain, fontSize = 9.5.sp) },
                                            modifier = Modifier.height(24.dp)
                                        )

                                        Spacer(Modifier.height(6.dp))
                                        Text(
                                            text = advice,
                                            fontSize = 11.5.sp,
                                            lineHeight = 16.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Smart contextual replier mimicking Madani's wisdom
private fun generateMadaniSmartReply(query: String, isArabic: Boolean): String {
    val q = query.lowercase(Locale.ROOT)
    return when {
        q.contains("force majeure") || q.contains("قوة قاهرة") ->
            "في صياغة العقود الرسمية، يُترجم مصطلح 'Force Majeure' إلى 'القوة القاهرة'. احرص على التأكد مما إذا كان العقد يدرج الجوائح والحروب والإضرابات ضمن نطاقها، ولا تخلط بينها وبين الظروف الطارئة (Hardship clause) التي تتيح تعديل العقد لا فسخه."

        q.contains("décalage") || q.contains("فارق") || q.contains("كابينة") ->
            "الـ Décalage هو الفاصل الزمني الذهبي في كابينة الترجمة الفورية! المعدل المثالي هو بين 3 إلى 5 ثوانٍ. إذا كان المتحدث سريعاً، قسّم الجمل الطويلة إلى وحدات معنوية مستقلة وتجنب حشو الكلام (Fillers)."

        q.contains("شهادة") || q.contains("ميلاد") || q.contains("محلف") ->
            "لترجمة شهادة ميلاد أو وثيقة حالة مدنية رسمية، يجب مراعاة 4 عناصر إلزامية: 1) نقل الأسماء بالحروف اللاتينية كما هي بجواز السفر، 2) عدم إغفال صيغة ختم الحالة المدنية، 3) تدوين رقم التسجيل وتاريخ التحرير، 4) إضافة ديباجة المترجم المحلف وختمه المعتمد."

        q.contains("نطق") || q.contains("accent") || q.contains("صوت") ->
            "نصيحتي الذهبية لتحسين النطق: مارس تقنية 'Shadowing' (المحاكاة الصوتية الفورية). استمع إلى مقاطع مسجلة لمترجمي الأمم المتحدة وردد وراءهم بنفس الإيقاع والتنغيم الصوتي (Intonation)."

        q.contains("سعر") || q.contains("تكلفة") || q.contains("صفحة") ->
            "في السوق الجزائرية والدولية، يتم تسعير الترجمة إما بالصفحة المعيارية (250 كلمة) أو بالكلمة الواحدة. الترجمة المحلفة المعتمدة للوثائق الرسمية تبدأ عادة من 1200 إلى 2500 د.ج للصفحة بحسب اللغة والتصديق."

        else ->
            "سؤال قيم في عالم الترجمة واللغات! تذكر دائماً القاعدة الأكاديمية: المترجم لا يترجم كلمات مجردة، بل ينقل المعنى والسياق الوظيفي (Functional Equivalence). يسعدني مساعدتك في صياغة أي مصطلح أو تدقيق أي وثيقة!"
    }
}
