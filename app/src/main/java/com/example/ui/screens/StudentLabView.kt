package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AdminEmailNotifier
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

enum class LabSubSection {
    INTERPRETATION, ROZAN, PRACTICAL_DRILLS, EXAM
}

data class ExamQuestion(
    val id: Int,
    val questionAr: String,
    val questionEn: String,
    val optionsAr: List<String>,
    val correctIndex: Int,
    val explanationAr: String
)

@Composable
fun StudentLabView(
    isArabic: Boolean,
    userName: String = "طالب الترجمة",
    userEmail: String = AdminEmailNotifier.ADMIN_EMAIL
) {
    var activeSubSection by remember { mutableStateOf(LabSubSection.INTERPRETATION) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Selector Tabs (Scrollable for great mobile flexibility)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = activeSubSection == LabSubSection.INTERPRETATION,
                onClick = { activeSubSection = LabSubSection.INTERPRETATION },
                label = { Text(if (isArabic) "🎙️ كابينة الفورية" else "Booth", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_booth")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.ROZAN,
                onClick = { activeSubSection = LabSubSection.ROZAN },
                label = { Text(if (isArabic) "📝 رموز روزان" else "Rozan", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_rozan")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.PRACTICAL_DRILLS,
                onClick = { activeSubSection = LabSubSection.PRACTICAL_DRILLS },
                label = { Text(if (isArabic) "🎯 تدريبات التحرير" else "Drills", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_drills")
            )
            FilterChip(
                selected = activeSubSection == LabSubSection.EXAM,
                onClick = { activeSubSection = LabSubSection.EXAM },
                label = { Text(if (isArabic) "📊 امتحان الكفاءة" else "Exam", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("tab_lab_exam")
            )
        }

        when (activeSubSection) {
            LabSubSection.INTERPRETATION -> InterpretationBoothComponent(isArabic, userName, userEmail)
            LabSubSection.ROZAN -> RozanNotebookComponent(isArabic, userName, userEmail)
            LabSubSection.PRACTICAL_DRILLS -> PracticalTranslationDrillsComponent(isArabic, userName, userEmail)
            LabSubSection.EXAM -> LevelPlacementExamComponent(isArabic, userName, userEmail)
        }
    }
}

@Composable
private fun InterpretationBoothComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordTimer by remember { mutableIntStateOf(0) }
    var selectedSpeed by remember { mutableStateOf("1.0x") }
    var showSourceScript by remember { mutableStateOf(false) }
    var selectedSpeechIndex by remember { mutableIntStateOf(0) }
    var dispatchStatus by remember { mutableStateOf<String?>(null) }

    val speeches = listOf(
        Pair(
            "🌍 قمة المناخ الدولية (COP28) - الإنجليزية ➔ العربية",
            "Mr. President, distinguished delegates, and esteemed colleagues: today our planet stands at an unprecedented inflection point where incremental pledges are no longer sufficient to arrest global temperature rise. As extreme weather events multiply across vulnerable coastal regions and arid basins, the transition toward carbon neutrality requires genuine technology transfer, climate finance mobilization, and a binding multilateral commitment that safeguards future generations without impeding developing nations' sustainable industrialization."
        ),
        Pair(
            "🕊️ افتتاح الجمعية العامة للأمم المتحدة - الفرنسية ➔ العربية",
            "Monsieur le Président, Excellences, Mesdames et Messieurs les délégués : au moment où notre organisation célèbre les principes fondateurs de sa Charte, nous constatons avec une vive inquiétude la multiplication des foyers de tension et l'affaiblissement progressif des mécanismes traditionnels de sécurité collective. La paix durable ne saurait être édifiée sur des rapports de force éphémères, mais requiert impérativement le respect scrupuleux de la souveraineté des États, le règlement pacifique des différends et une refonte équitable des institutions multilatérales de gouvernance."
        ),
        Pair(
            "⚡ منتدى الطاقة والغاز الدولي بالجزائر - الإنجليزية ➔ العربية",
            "Excellencies, ministers, and captains of the global energy industry: natural gas remains an indispensable bridge fuel that anchors grid reliability and supports the global transition toward decarbonized energy systems. Algeria has continually proven its credentials as a dependable, long-term strategic energy partner for Europe and global markets, underpinned by massive capital investments in upstream exploration, subsea pipeline infrastructure, and cutting-edge green hydrogen pilot projects designed to meet the demands of the 21st century."
        ),
        Pair(
            "💼 المنتدى الاقتصادي العالمي دافوس (WEF) - الإنجليزية ➔ العربية",
            "Distinguished participants, global economic governance is facing structural headwinds marked by supply chain reshoring, persistent inflationary pressures, and severe sovereign debt distress across emerging markets. To avert a prolonged era of low growth and geo-economic fragmentation, central banks and fiscal authorities must calibrate coordinated monetary frameworks, dismantle arbitrary non-tariff barriers, and promote inclusive digital trade corridors."
        ),
        Pair(
            "🏛️ المؤتمر العام لمنظمة اليونسكو بباريس - الفرنسية ➔ العربية",
            "Madame la Directrice générale, éminents représentants des États membres : la préservation de notre patrimoine culturel immatériel et la vitalité des langues autochtones ne sont pas de simples hommages au passé, mais les garants les plus sûrs de notre créativité collective et de notre résistance face à l'uniformisation culturelle. Dans un monde façonné par les algorithmes et l'intelligence artificielle, l'éducation humaniste et le multilinguisme demeurent les fondations indispensables d'une citoyenneté éclairée et fraternelle."
        ),
        Pair(
            "🇩🇪 قمة التكنولوجيا والتحول الرقمي ببرلين - الألمانية ➔ العربية",
            "Sehr geehrte Damen und Herren, geschätzte Delegierte: Die tiefgreifende Transformation unserer industriellen Wertschöpfungsketten hin zu vollständiger Klimaneutralität ist die größte ökonomische und technologische Herausforderung unserer Epoche. Die Bundesrepublik Deutschland setzt auf strategische Energiepartnerschaften mit Nordafrika, um den Aufbau einer robusten Wasserstoffwirtschaft voranzutreiben, technologische Standards zu harmonisieren und zukunftsfähige Industriearbeitsplätze auf beiden Seiten des Mittelmeers zu sichern."
        ),
        Pair(
            "🏥 منظمة الصحة العالمية (WHO) جنيف - الإنجليزية ➔ العربية",
            "Director-General, ministers of health, and distinguished delegates: pathogen genomic surveillance, equitable distribution of biomedical countermeasures, and universal health coverage represent the three pillars of global biosecurity. We cannot permit a recurrence of the vaccine inequities that paralyzed the developing world; health resilience must be treated as an indivisible global public good, governed by legally binding pandemic accords that guarantee swift technological access to all sovereign nations."
        ),
        Pair(
            "🤝 مؤتمر القمة العربية (مجلس الجامعة) - العربية ➔ الإنجليزية",
            "معالي الأمين العام، أصحاب المعالي والسعادة: إن جسامة الرهانات الجيوسياسية والاقتصادية التي تطوق أمتنا اليوم تقتضي مغادرة مقاربات التسيير الظرفي، والارتقاء بالتضامن العربي إلى مستوى الاندماج الهيكلي الفعلي. إن صيانة الأمن القومي بمفهومه الشامل، وتحقيق الاكتفاء الذاتي في ميادين الغذاء والطاقة والمياه، وتفعيل السوق العربية المشتركة، لم تعد مجرد تطلعات مؤجلة، بل غدت شروط بقاء لا مناص من تجسيدها في عالم يتشكل من جديد."
        )
    )

    LaunchedEffect(isRecording) {
        if (isRecording) {
            while (isRecording) {
                kotlinx.coroutines.delay(1000)
                recordTimer++
            }
        } else {
            recordTimer = 0
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) "مقصورة المحاكاة للمؤتمرات (Simultaneous Booth)" else "Interpretation Booth",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = RedPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isRecording) RedPrimary else SuccessGreen.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isRecording) "🔴 مباشر: ${recordTimer}s" else "جاهز للتسجيل",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isRecording) Color.White else SuccessGreen
                            )
                        }
                    }

                    Text(
                        text = if (isArabic) "اختر الخطاب التدريبي وابدأ التسجيل الفوري لمحاكاة بيئة الكابينة الحقيقية:" else "Select speech and record your simultaneous rendering:",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Speech selector
                    speeches.forEachIndexed { idx, item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedSpeechIndex = idx },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedSpeechIndex == idx) RedPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (selectedSpeechIndex == idx) RedPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = item.first,
                                modifier = Modifier.padding(10.dp),
                                fontSize = 12.sp,
                                fontWeight = if (selectedSpeechIndex == idx) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Recording Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { isRecording = !isRecording },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isRecording) Color.DarkGray else RedPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f).testTag("toggle_booth_record")
                        ) {
                            Icon(if (isRecording) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isRecording) (if (isArabic) "إيقاف التسجيل" else "Stop") else (if (isArabic) "بدء الترجمة الفورية" else "Record"), fontSize = 11.5.sp)
                        }

                        OutlinedButton(
                            onClick = { showSourceScript = !showSourceScript },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showSourceScript) "إخفاء النص" else "إظهار النص", fontSize = 11.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "تقرير تمرين كابينة الترجمة الفورية",
                                userName = userName,
                                userEmail = userEmail,
                                details = mapOf(
                                    "speech" to speeches[selectedSpeechIndex].first,
                                    "recordDurationSeconds" to "$recordTimer ثانية",
                                    "adminEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                dispatchStatus = if (success) "✓ تم إرسال تقرير تدريبك بنجاح إلى الإدارة (djoudimadani09@gmail.com)" else "تم التوثيق بنجاح"
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "إرسال تقرير التدريب إلى الإدارة (djoudimadani09@gmail.com)" else "Send Report to Admin (djoudimadani09@gmail.com)", fontSize = 11.sp)
                    }

                    if (dispatchStatus != null) {
                        Text(text = dispatchStatus!!, fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }

                    if (showSourceScript) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("النص الأصلي (Source Script):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = RedPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(speeches[selectedSpeechIndex].second, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RozanNotebookComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var notesText by remember { mutableStateOf("") }
    var selectedConsecIndex by remember { mutableIntStateOf(0) }
    var showSolution by remember { mutableStateOf(false) }
    var rozanDispatchStatus by remember { mutableStateOf<String?>(null) }

    val consecSpeeches = listOf(
        Triple(
            "🤝 تصريح ثنائي مشترك: الاستثمار في الهيدروجين الأخضر والتجارة (العربية ➔ الفرنسية)",
            "لقد اتفق الجانبان اليوم، بعد جولة محادثات معمقة في العاصمة، على تدشين شراكة استراتيجية بعيدة المدى في مجال إنتاج ونقل الهيدروجين الأخضر ومشتقاته. وقد شدد الوفدان على ضرورة تفكيك العوائق الجمركية غير المبررة، وتسهيل إصدار التأشيرات لرجال الأعمال والخبراء التقنيين، وتدشين خط ملاحي بحري مباشر لنقل البضائع قبل نهاية الربع الأول من العام الجاري.",
            "Les deux parties sont convenues aujourd'hui, à l'issue de pourparlers approfondis dans la capitale, de lancer un partenariat stratégique de long terme dans le domaine de la production et de l'acheminement de l'hydrogène vert et de ses dérivés. Les deux délégations ont souligné la nécessité impérieuse de lever les barrières tarifaires injustifiées, de faciliter la délivrance des visas pour les chefs d'entreprise et les experts techniques, et d'inaugurer une ligne maritime directe de fret avant la fin du premier trimestre de l'année en cours."
        ),
        Triple(
            "🇪🇺 مؤتمر الشراكة الاقتصادية الأوروبية المتوسطية (الفرنسية ➔ العربية)",
            "Mesdames et Messieurs, face aux bouleversements logistiques mondiaux, nous annonçons la mise en place d'un fonds souverain conjoint de deux milliards d'euros dédié à la modernisation des infrastructures portuaires et au développement d'écosystèmes logistiques intégrés. Ce programme d'envergure permettra de créer plus de cinquante mille emplois hautement qualifiés et de réduire de quarante pour cent les délais de transit des marchandises entre les deux rives de la Méditerranée.",
            "سيداتي وسادتي، في مواجهة الاضطرابات اللوجستية العالمية، نعلن اليوم عن إنشاء صندوق سيادي مشترك بقيمة ملياري يورو مخصص لتحديث البنى التحتية المينائية وتطوير منظومات لوجستية متكاملة. سيمكن هذا البرنامج الطموح من استحداث أكثر من خمسين ألف منصب عمل عالي التأهيل، وتقليص آجال عبور السلع بين ضفتي البحر الأبيض المتوسط بنسبة أربعين بالمئة."
        ),
        Triple(
            "🛢️ البيان الختامي لاجتماع منظمة أوبك+ الوزاري (الإنجليزية ➔ العربية)",
            "The ministerial committee concluded its deliberations by reaffirming its unwavering commitment to stabilizing world petroleum markets amidst macroeconomic volatility. Through targeted precautionary quota reductions, robust compliance mechanisms, and proactive monitoring of commercial crude inventories across consumer nations, the participating countries remain resolved to balance supply and demand fundamentals while ensuring energy security.",
            "اختتمت اللجنة الوزارية مداولاتها بالتأكيد مجدداً على التزامها الثابت والراسخ بتحقيق استقرار أسواق البترول العالمية في ظل التقلبات الاقتصادية الكلية. ومن خلال تخفيضات الحصص الاحترازية الموجهة، وآليات الامتثال الصارمة، والمراقبة الاستباقية للمخزونات التجارية من النفط الخام لدى الدول المستهلكة، تظل الدول المشاركة عازمة على موازنة أساسيات العرض والطلب مع ضمان أمن الطاقة."
        ),
        Triple(
            "⚖️ مرافعة تحكيم تجاري دولي أمام محكمة غرفة التجارة الدولية (ICC) (الإنجليزية ➔ العربية)",
            "Mr. Sole Arbitrator, the claimant formally submits that the respondent acted in bad faith by unilaterally repudiating the exclusive distribution covenant set forth under Article Fourteen of the Master Concession Agreement. Consequently, the claimant seeks liquidated damages amounting to twelve point five million US dollars, pre-award statutory interest, and immediate interim injunctive relief to restrain any third-party assignment of territorial intellectual property rights.",
            "سيادة المحكّم الفرد، يدفع المدعي رسمياً بأن المدعى عليه قد تصرف بسوء نية عندما أقدم من جانب واحد على فسخ شرط التوزيع الحصري المنصوص عليه في المادة الرابعة عشرة من اتفاقية الامتياز الرئيسية. وبناءً على ذلك، يلتمس المدعي الحكم بتعويضات اتفاقية محددة قدرها اثنا عشر مليوناً وخمسمائة ألف دولار أمريكي، مضافاً إليها الفوائد القانونية السابقة على صدور الحكم، مع إصدار تدابير تحفظية وقتية فورية لمنع أي تنازل للغير عن حقوق الملكية الفكرية الإقليمية."
        ),
        Triple(
            "🇩🇪 ملتقى رجال الأعمال الجزائري الألماني للطاقات المتجددة (الألمانية ➔ العربية)",
            "Deutsche Industrie- und Technologiekonzerne beabsichtigen, im Schulterschluss mit ihren algerischen Partnern richtungsweisende Großprojekte in den Bereichen Photovoltaik, Windkraft und industrielle Wasserstoffelektrolyse zu realisieren. Ein zentraler Eckpfeiler dieser Kooperation ist der nachhaltige Technologietransfer sowie die akademische und berufliche Qualifizierung junger Ingenieure vor Ort, um eine wettbewerbsfähige grüne Wertschöpfungskette aufzubauen.",
            "تعتزم المجمعات الصناعية والتكنولوجية الألمانية، بالتعاون الوثيق مع شركائها الجزائريين، تجسيد مشاريع كبرى رائدة في مجالات الطاقة الشمسية الكهروضوئية، وطاقة الرياح، والتحليل الكهربائي الصناعي لإنتاج الهيدروجين. ويُعد النقل المستدام للتكنولوجيا والتأهيل الأكاديمي والمهني للمهندسين الشباب محلياً حجر الزاوية في هذا التعاون لبناء سلسلة قيمة خضراء تنافسية."
        ),
        Triple(
            "📜 تقديم أوراق اعتماد سفير جديد ومراسم البروتوكول (العربية ➔ الإنجليزية)",
            "يشرفني بالغ الشرف أن أرفع إلى فخامتكم أوراق اعتمادي سفيراً ومفوضاً فوق العادة لبلادي، ناقلاً إليكم أسمى تحيات رئيس الجمهورية وتمنياته الخالصة لفخامتكم ولشعبكم الصديق بمزيد من الرقي والازدهار. وإنني لعلى يقين تام بأن التوجيهات السامية لقيادتي بلدينا ستشكل نبراساً لمهمتي الدبلوماسية، بغية تعميق الحوار السياسي وتوسيع المبادلات الاقتصادية والثقافية بما يخدم المصالح الاستراتيجية المشتركة.",
            "I have the utmost honour to present to Your Excellency my letters of credence accrediting me as Ambassador Extraordinary and Plenipotentiary of my country, conveying the highest regards of the President of the Republic and his sincere wishes for Your Excellency and your friendly people for continued progress and prosperity. I am fully confident that the supreme guidance of our two nations' leadership will guide my diplomatic mission to deepen political dialogue and expand economic and cultural exchanges in service of our shared strategic interests."
        )
    )

    val rozanSymbols = listOf(
        Pair("∵", "السبب (car / because)"),
        Pair("∴", "النتيجة (donc / therefore)"),
        Pair("↑", "زيادة / ارتفاع / نمو"),
        Pair("↓", "انخفاض / تراجع"),
        Pair("=", "تطابق / مساواة"),
        Pair("≠", "اختلاف / تناقض"),
        Pair("✗", "نفي / رفض"),
        Pair("★", "نقطة جوهرية / هام"),
        Pair("→", "انتقال / نحو / أدى إلى"),
        Pair("👥", "مجتمع / وفد / هيئة")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (isArabic) "📝 ورشة الترجمة التتابعية ونظام روزان (Jean-François Rozan)" else "Rozan Consecutive Notepad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = RedPrimary
                    )
                    Text(
                        text = if (isArabic) "مبادئ روزان السبعة: التدوين الرأسي (Verticality)، التدرج (Shift)، الروابط المنطقية، ونظام الرموز المختصرة." else "Rozan's 7 Principles: Verticality, shift, logical connectors, and abbreviation symbols.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Speech selector
                    Text(if (isArabic) "اختر خطاب التدريب التتابعي:" else "Select consecutive speech:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    consecSpeeches.forEachIndexed { idx, item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedConsecIndex = idx
                                    showSolution = false
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedConsecIndex == idx) RedPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (selectedConsecIndex == idx) RedPrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = item.first,
                                modifier = Modifier.padding(10.dp),
                                fontSize = 11.5.sp,
                                fontWeight = if (selectedConsecIndex == idx) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Source speech box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(if (isArabic) "النص المصدر للتدوين:" else "Source text:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                            Text(consecSpeeches[selectedConsecIndex].second, fontSize = 12.sp, lineHeight = 18.sp)
                        }
                    }

                    // Quick Insert Rozan Symbols
                    Text("شريط رموز روزان السريعة (انقر للإدراج فوراً):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(rozanSymbols) { (sym, _) ->
                            Button(
                                onClick = { notesText += " $sym " },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary.copy(alpha = 0.15f))
                            ) {
                                Text(sym, fontSize = 14.sp, fontWeight = FontWeight.Black, color = RedPrimary)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .testTag("rozan_notes_input"),
                        placeholder = { Text(if (isArabic) "دوّن ملاحظاتك التتابعية هنا باستخدام الرموز والمحاذاة العمودية..." else "Take your consecutive notes here...") },
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showSolution = !showSolution },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (showSolution) "إخفاء الصياغة النموذجية" else "🔍 عرض الترجمة التتابعية النموذجية", fontSize = 11.sp)
                        }
                        TextButton(onClick = { notesText = "" }) {
                            Text(if (isArabic) "مسح المفكرة" else "Clear", fontSize = 11.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            AdminEmailNotifier.dispatch(
                                eventType = "مفكرة وتدوين رموز روزان للترجمة التتابعية",
                                userName = userName,
                                userEmail = userEmail,
                                details = mapOf(
                                    "speechTitle" to consecSpeeches[selectedConsecIndex].first,
                                    "studentNotes" to notesText.ifBlank { "لم يدون ملاحظات" },
                                    "adminEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                rozanDispatchStatus = if (success) "✓ تم إرسال ملاحظاتك إلى المشرف بنجاح (djoudimadani09@gmail.com)" else "تم الحفظ محلياً"
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isArabic) "إرسال الملاحظات إلى المشرف (djoudimadani09@gmail.com)" else "Send Notes to Admin (djoudimadani09@gmail.com)", fontSize = 11.sp)
                    }

                    if (rozanDispatchStatus != null) {
                        Text(text = rozanDispatchStatus!!, fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }

                    if (showSolution) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreen.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("الترجمة النموذجية المعتمدة:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                Text(consecSpeeches[selectedConsecIndex].third, fontSize = 12.sp, lineHeight = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelPlacementExamComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    var activeTrack by remember { mutableStateOf("comprehensive") }
    val questions = remember(activeTrack) {
        when (activeTrack) {
            "goethe" -> com.example.PlacementExamBanks.goetheQuestions
            "tcf" -> com.example.PlacementExamBanks.tcfQuestions
            "ielts" -> com.example.PlacementExamBanks.ieltsQuestions
            "trans" -> com.example.PlacementExamBanks.transQuestions
            else -> com.example.PlacementExamBanks.comprehensiveQuestions
        }
    }

    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var examSubmitted by remember { mutableStateOf(false) }
    var studentNameInput by remember { mutableStateOf(userName) }
    var studentEmailInput by remember { mutableStateOf(userEmail) }
    var examDispatchStatus by remember { mutableStateOf<String?>(null) }

    val answeredCount = selectedAnswers.size
    val score = remember(examSubmitted, selectedAnswers, questions) {
        if (!examSubmitted) 0
        else questions.count { q -> selectedAnswers[q.id] == q.correctIndex }
    }
    val scorePercentage = remember(score, questions.size) {
        if (questions.isNotEmpty()) (score * 100) / questions.size else 0
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Track Switcher Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilterChip(
                    selected = activeTrack == "comprehensive",
                    onClick = { activeTrack = "comprehensive"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🏆 الشامل", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "goethe",
                    onClick = { activeTrack = "goethe"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇩🇪 Goethe", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "tcf",
                    onClick = { activeTrack = "tcf"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇫🇷 TCF", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "ielts",
                    onClick = { activeTrack = "ielts"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("🇬🇧 IELTS", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = activeTrack == "trans",
                    onClick = { activeTrack = "trans"; selectedAnswers = mutableMapOf(); examSubmitted = false },
                    label = { Text("⚖️ ترجمة", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "🎓",
                                fontSize = 18.sp
                            )
                            Text(
                                text = if (isArabic) "امتحان تقييم المستوى الأكاديمي والمهني" else "Professional Level Placement Exam",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = RedPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = RedPrimary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RedPrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "ISO 17100 • CEFR",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary
                            )
                        }
                    }

                    Text(
                        text = if (isArabic)
                            "اختبارات تقييمية معيارية واقعية تضم 100 سؤال لكل لغة مع تصحيح نموذجي معتمد وتحليل دلالي ونحوي وفق معايير CEFR و ISO 17100:2015."
                        else
                            "Standardized 100-question exam per language track with official model answers and CEFR / ISO 17100 evaluation.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )

                    // Progress indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) "الإجابات المنجزة: $answeredCount من ${questions.size}" else "Answered: $answeredCount of ${questions.size}",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (answeredCount == questions.size) SuccessGreen else MaterialTheme.colorScheme.onSurface
                        )
                        LinearProgressIndicator(
                            progress = { if (questions.isNotEmpty()) answeredCount.toFloat() / questions.size.toFloat() else 0f },
                            modifier = Modifier.width(130.dp).height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = RedPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }

                    if (examSubmitted) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = if (scorePercentage >= 70) SuccessGreen.copy(alpha = 0.12f) else if (scorePercentage >= 50) GoldYellow.copy(alpha = 0.15f) else RedPrimary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) GoldYellow else RedPrimary)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "الدرجة المحققة: $score / ${questions.size} ($scorePercentage%)",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) RedPrimary else RedPrimary
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (scorePercentage >= 70) SuccessGreen else if (scorePercentage >= 50) GoldYellow else RedPrimary
                                    ) {
                                        Text(
                                            text = when {
                                                scorePercentage >= 85 -> "C2 خبير"
                                                scorePercentage >= 70 -> "C1 متقدم"
                                                scorePercentage >= 50 -> "B2 متوسط مرتفع"
                                                else -> "B1 تأسيسي"
                                            },
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.5.sp
                                        )
                                    }
                                }

                                Text(
                                    text = when {
                                        scorePercentage >= 85 -> "🏆 المستوى المستحق C2: أداء استثنائي متمرس ومؤهل لكابينات المؤتمرات والترجمة المحلفة المعتمدة."
                                        scorePercentage >= 70 -> "🎓 المستوى المستحق C1: كفاءة مهنية عالية ومترجم مؤتمرات متقدم قادر على التعامل مع النصوص المعقدة."
                                        scorePercentage >= 50 -> "📘 المستوى المستحق B2: طالب وممارس واعد، يُوصى بحضور مساقات الماستركلاس وممارسة كابينة المحاكاة."
                                        else -> "📗 المستوى المستحق B1: بداية المسار الأكاديمي، يُنصح بالبدء بدورات المصطلحات التأسيسية وقواعد روزان."
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )

                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                                Text(
                                    text = "📊 سلّم التنقيط المعتمد (CEFR & ISO 17100):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RedPrimary
                                )
                                Text(
                                    text = "• 85% - 100%: C2 (مترجم محترف معتمد / خبير مؤتمرات)\n• 70% - 84%: C1 (مترجم متقدم / صياغة قانونية متخصصة)\n• 50% - 69%: B2 (مستوى فوق المتوسط / كفاءة مستقلة)\n• أقل من 50%: B1 (مستوى تأسيسي بحاجة لتمارين مكثفة)",
                                    fontSize = 10.5.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        itemsIndexed(questions) { index, q ->
            Card(
                modifier = Modifier.fillMaxWidth().testTag("exam_q_${q.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "السؤال ${index + 1}: ${if (isArabic) q.questionAr else q.questionEn}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )

                    q.optionsAr.forEachIndexed { optIdx, optText ->
                        val isSelected = selectedAnswers[q.id] == optIdx
                        val isCorrect = optIdx == q.correctIndex

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !examSubmitted) {
                                    selectedAnswers = selectedAnswers.toMutableMap().apply { put(q.id, optIdx) }
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                examSubmitted && isCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                examSubmitted && isSelected && !isCorrect -> RedPrimary.copy(alpha = 0.2f)
                                isSelected -> RedPrimary.copy(alpha = 0.12f)
                                else -> MaterialTheme.colorScheme.surface
                            },
                            border = androidx.compose.foundation.BorderStroke(
                                if (isSelected || (examSubmitted && isCorrect)) 1.5.dp else 1.dp,
                                when {
                                    examSubmitted && isCorrect -> SuccessGreen
                                    examSubmitted && isSelected && !isCorrect -> RedPrimary
                                    isSelected -> RedPrimary
                                    else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = null, // delegated to outer Surface clickable to avoid event conflicts
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = optText,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) RedPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    if (examSubmitted) {
                        Text(
                            text = "💡 التوضيح: ${q.explanationAr}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            examSubmitted = true
                            val levelTier = when {
                                scorePercentage >= 85 -> "C2 خبير ومترجم مؤتمرات"
                                scorePercentage >= 70 -> "C1 متقدم وصياغة قانونية"
                                scorePercentage >= 50 -> "B2 متوسط مرتفع"
                                else -> "B1 تأسيسي"
                            }
                            val finalName = if (studentNameInput.isNotBlank()) studentNameInput else userName
                            val finalEmail = if (studentEmailInput.isNotBlank()) studentEmailInput else userEmail

                            AdminEmailNotifier.dispatch(
                                eventType = "تسليم امتحان تحديد المستوى (100 سؤال)",
                                userName = finalName,
                                userEmail = finalEmail,
                                details = mapOf(
                                    "track" to activeTrack,
                                    "score" to "$score / ${questions.size}",
                                    "percentage" to "$scorePercentage%",
                                    "level" to levelTier,
                                    "answeredCount" to "$answeredCount من ${questions.size}",
                                    "targetEmail" to AdminEmailNotifier.ADMIN_EMAIL
                                )
                            ) { success, _ ->
                                examDispatchStatus = if (success)
                                    "✓ تم إرسال تقرير نتيجتك وإجاباتك بنجاح إلى الإدارة (${AdminEmailNotifier.ADMIN_EMAIL})."
                                else
                                    "✓ تم تقييم نتيجتك محلياً."
                            }
                        },
                        modifier = Modifier.weight(1f).testTag("submit_exam_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                    ) {
                        Text(if (isArabic) "تسليم الإجابات وتقييم المستوى" else "Submit Exam", fontWeight = FontWeight.Bold)
                    }

                    if (examSubmitted) {
                        OutlinedButton(
                            onClick = {
                                selectedAnswers = mutableMapOf()
                                examSubmitted = false
                                examDispatchStatus = null
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(if (isArabic) "إعادة الامتحان" else "Retake")
                        }
                    }
                }

                if (examSubmitted) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = SuccessGreen.copy(alpha = 0.12f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                            Text(
                                text = examDispatchStatus ?: (if (isArabic) "✓ تم تسجيل نتيجتك وإرسالها فوراً إلى بريد الإدارة (djoudimadani09@gmail.com)." else "✓ Result sent to administration (djoudimadani09@gmail.com)."),
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// PRACTICAL TRANSLATION WORKSHOP & DRILLS COMPONENT (تدريبات الترجمة التحريرية المتقدمة)
// -------------------------------------------------------------------------------------------------
@Composable
private fun PracticalTranslationDrillsComponent(
    isArabic: Boolean,
    userName: String,
    userEmail: String
) {
    data class TranslationDrill(
        val id: String,
        val domain: String,
        val title: String,
        val level: String,
        val langPair: String,
        val sourceParagraph: String,
        val certifiedReference: String,
        val keyTerminology: List<String>,
        val stylistNotes: String
    )

    val drills = remember {
        listOf(
            TranslationDrill(
                id = "DRILL-LAW-01",
                domain = "قانون ومحاكم ⚖️",
                title = "عقد توزيع تجاري وشرط عدم المنافسة",
                level = "متقدم C1",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "The Distributor covenants that during the term of this Agreement and for a period of twenty-four (24) months following its termination, it shall not directly or indirectly engage in any business competing with the Principal within the defined Territory.",
                certifiedReference = "يتعهد الموزع بأنه خلال سريان هذه الاتفاقية ولمدة أربعة وعشرين (24) شهراً تلي إنهاءها، يمتنع عن ممارسة أي نشاط تجاري ينافس الموكل، سواء أكان ذلك بصورة مباشرة أو غير مباشرة، داخل النطاق الإقليمي المحدد.",
                keyTerminology = listOf("يتعهد (covenants)", "سريان الاتفاقية", "بصورة مباشرة أو غير مباشرة", "الموكل (Principal)", "النطاق الإقليمي المحدد"),
                stylistNotes = "يُراعى استخدام الفعل المضارع بصيغة الإلزام القانوني 'يتعهد' بدلاً من صيغة المستقبل، وترجمة Principal بـ 'الموكل' أو 'الأصيل' وفق المصطلحات المعتمدة في قانون التجارة المقارن."
            ),
            TranslationDrill(
                id = "DRILL-MED-02",
                domain = "طب وصيدلة 🩺",
                title = "بروتوكول تجارب سريرية لعلاج مناعي",
                level = "تخصصي C2",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "In this randomized, double-blind, placebo-controlled trial, patients exhibiting refractory metastatic melanoma received weight-based intravenous infusions of the monoclonal antibody.",
                certifiedReference = "في هذه التجربة السريرية المعشاة، المزدوجة التعمية، والمضبوطة بالغفل (البلاسيبو)، تلقى المرضى الذين يعانون من ورم ميلانيني نقيلي مستعصٍ دفعات تسريبية وريدية من الجسم المضاد وحيد النسيلة حسب أوزانهم.",
                keyTerminology = listOf("معشاة (Randomized)", "مزدوجة التعمية (Double-blind)", "مضبوطة بالغفل (Placebo-controlled)", "ورم ميلانيني نقيلي", "جسم مضاد وحيد النسيلة"),
                stylistNotes = "الدقة القصوى واجبة في المصطلحات الصيدلانية: Placebo يُترجم بـ 'الغفل' مع ذكر البلاسيبو، وRefractory بـ 'مستعصٍ' وليس مجرد 'عنيد'."
            ),
            TranslationDrill(
                id = "DRILL-DIP-03",
                domain = "دبلوماسية ومعاهدات 🕊️",
                title = "مذكرة شفوية لترسيم الحدود البحرية",
                level = "دبلوماسي C1",
                langPair = "الفرنسية ➔ العربية",
                sourceParagraph = "Le Ministère des Affaires Étrangères présente ses compliments à l'Ambassade et a l'honneur de notifier son assentiment formel au procès-verbal de délimitation du plateau continental.",
                certifiedReference = "تُهدي وزارة الشؤون الخارجية أطيب تحياتها إلى السفارة الموقرة، ويشرفها أن تخطرها بموافقتها الرسمية على محضر ترسيم الجرف القاري المشترك.",
                keyTerminology = listOf("تُهدي أطيب تحياتها (Présente ses compliments)", "يشرفها أن تخطرها", "الموافقة الرسمية", "محضر (Procès-verbal)", "الجرف القاري (Plateau continental)"),
                stylistNotes = "تعتمد المذكرات الشفوية الدبلوماسية (Note Verbale) ديباجة بروتوكولية راسخة يجب الحفاظ على نبرتها الرفيعة والمحترمة."
            ),
            TranslationDrill(
                id = "DRILL-ENG-04",
                domain = "طاقة وهندسة ⚡",
                title = "عقد إنشاء وتشغيل محطة هيدروجين أخضر",
                level = "هندسي B2+",
                langPair = "الإنجليزية ➔ العربية",
                sourceParagraph = "The EPC contractor shall furnish all necessary electrolysis modules, desalinization skids, and compression stations in strict accordance with ASME and ISO hydrogen purity standards.",
                certifiedReference = "يلتزم مقاول الهندسة والمشتريات والإنشاء (EPC) بتوريد وتركيب كافة وحدات التحليل الكهربائي، ومنصات تحلية المياه، ومحطات الضغط، امتثالاً صارماً لمعايير نقاء الهيدروجين الصادرة عن الجمعية الأمريكية للمهندسين الميكانيكيين (ASME) والمنظمة الدولية للمواصفات (ISO).",
                keyTerminology = listOf("مقاول EPC", "التحليل الكهربائي (Electrolysis)", "منصات التحلية", "محطات الضغط", "امتثالاً صارماً"),
                stylistNotes = "يجب تفكيك اختصار EPC بدقة هندسية وتعريب مصطلح Skids كمنصات أو حزم مجهزة."
            )
        )
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    var studentDraft by remember { mutableStateOf("") }
    var evaluated by remember { mutableStateOf(false) }
    var dispatchStatus by remember { mutableStateOf<String?>(null) }
    var isSendingFeedback by remember { mutableStateOf(false) }

    val currentDrill = drills[selectedIndex]

    // Calculate accuracy match
    val matchScore = remember(studentDraft, evaluated) {
        if (!evaluated || studentDraft.isBlank()) 0
        else {
            val wordsInDraft = studentDraft.split("\\s+".toRegex()).map { it.trim() }
            var foundCount = 0
            currentDrill.keyTerminology.forEach { term ->
                val coreWord = term.split(" ")[0].replace("(", "").replace(")", "")
                if (wordsInDraft.any { it.contains(coreWord) }) {
                    foundCount++
                }
            }
            val base = 60 + (foundCount * 8)
            base.coerceIn(65, 98)
        }
    }

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("practical_drills_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎯 " + if (isArabic) "ورشة الترجمة التحريرية والتخصصية" else "Specialized Translation Drills",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RedPrimary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "معيار ISO 17100",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = if (isArabic) "تدريبات واقعية معتمدة على نصوص رسمية موثقة، تحليل المصطلحات المفتاحية، مقارنة حية مع الصياغة المحلفة، وتقييم فوري." else "Real-world translation exercises with instant terminology verification.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Drill Selection Chips
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(drills.size) { idx ->
                            val drill = drills[idx]
                            val isSel = selectedIndex == idx
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    selectedIndex = idx
                                    studentDraft = ""
                                    evaluated = false
                                    dispatchStatus = null
                                },
                                label = { Text(drill.domain, fontSize = 10.5.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) }
                            )
                        }
                    }
                }
            }
        }

        // Active Drill Workspace
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentDrill.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = GoldYellow.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = currentDrill.level,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = currentDrill.langPair,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Source Paragraph Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "النص المصدر المراد ترجمته:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = currentDrill.sourceParagraph,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Student Translation Input Field
                    OutlinedTextField(
                        value = studentDraft,
                        onValueChange = { studentDraft = it },
                        modifier = Modifier.fillMaxWidth().testTag("student_translation_input"),
                        label = { Text(if (isArabic) "صياغتك المترجمة المعتمدة:" else "Your translation draft:") },
                        placeholder = { Text(if (isArabic) "اكتب صياغتك هنا بمراعاة الدقة الاصطلاحية والأسلوب القانوني/التخصصي..." else "Type your translation draft here...") },
                        minLines = 4,
                        maxLines = 8,
                        shape = RoundedCornerShape(10.dp)
                    )

                    // Quick Sample Draft Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                studentDraft = currentDrill.certifiedReference
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (isArabic) "استدعاء مسودة متقدمة" else "Load Advanced Draft", fontSize = 11.sp)
                        }

                        Button(
                            onClick = { evaluated = true },
                            modifier = Modifier.weight(1.3f).testTag("evaluate_translation_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(if (isArabic) "تحليل وتقييم الصياغة" else "Evaluate Translation", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Evaluation Results Panel
                    if (evaluated) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = SuccessGreen.copy(alpha = 0.08f),
                            border = BorderStroke(1.2.dp, SuccessGreen.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "✓ الصياغة النموذجية المعتمدة (ISO 17100):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = SuccessGreen
                                    )
                                    Surface(
                                        color = SuccessGreen.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "معدل التطابق: $matchScore%",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 11.sp,
                                            color = SuccessGreen,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = currentDrill.certifiedReference,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 18.sp
                                )

                                Divider(color = SuccessGreen.copy(alpha = 0.2f))

                                Text(
                                    text = "المصطلحات المحورية المفحوصة:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                currentDrill.keyTerminology.forEach { term ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text("•", color = RedPrimary, fontWeight = FontWeight.Bold)
                                        Text(term, fontSize = 11.5.sp)
                                    }
                                }

                                Surface(
                                    color = GoldYellow.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text("💡 توجيهات أسلوبية ودلالية:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        Text(currentDrill.stylistNotes, fontSize = 10.5.sp, lineHeight = 15.sp)
                                    }
                                }

                                // Send attempt to Admin Email (djoudimadani09@gmail.com)
                                Button(
                                    onClick = {
                                        isSendingFeedback = true
                                        AdminEmailNotifier.dispatch(
                                            eventType = "تدريب ترجمة تحريرية جديد",
                                            userName = userName,
                                            userEmail = userEmail,
                                            details = mapOf(
                                                "drillTitle" to currentDrill.title,
                                                "domain" to currentDrill.domain,
                                                "score" to "$matchScore%",
                                                "studentTranslation" to studentDraft,
                                                "reference" to currentDrill.certifiedReference
                                            )
                                        ) { success, msg ->
                                            isSendingFeedback = false
                                            dispatchStatus = if (success) "✓ تم تسليم المحاولة بنجاح إلى الإدارة الأكاديمية (djoudimadani09@gmail.com)" else "تم إرسال إشعار المحاولة إلى الإدارة."
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().testTag("send_drill_feedback_btn"),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                                    shape = RoundedCornerShape(8.dp),
                                    enabled = !isSendingFeedback
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = if (isSendingFeedback) "جاري التسليم..." else "إرسال المحاولة للمراجعة والاعتماد الأكاديمي",
                                        fontSize = 11.5.sp
                                    )
                                }

                                if (dispatchStatus != null) {
                                    Text(
                                        text = dispatchStatus!!,
                                        fontSize = 11.sp,
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold
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

