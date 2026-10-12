# -*- coding: utf-8 -*-
import json

# Read existing speeches
with open('booth_100_speeches_data.js', 'r', encoding='utf-8') as js_file:
    js_content = js_file.read()

# Clean up to parse JSON
prefix = 'const BOOTH_100_SPEECHES = '
if prefix in js_content:
    raw_json = js_content.split(prefix)[1].strip()
    if raw_json.endswith(';'):
        raw_json = raw_json[:-1].strip()
    speeches = json.loads(raw_json)
else:
    print('Error: Prefix not found')
    exit(1)

print('Current speeches count:', len(speeches))

new_speeches = [
    # --- DEUTSCH (German -> Arabic) 4 new long speeches ---
    {
        "id": "SP-111",
        "num": 111,
        "domain_id": "energy",
        "domain_ar": "الطاقة والمناخ والغاز والهيدروجين",
        "domain_en": "Energy, Climate & Hydrogen",
        "icon": "⚡",
        "title_ar": "التحول الطاقوي الألماني والشراكة الأورومتوسطية للهيدروجين الأخضر",
        "title_en": "German Energiewende & Euro-Mediterranean Green Hydrogen Corridor",
        "speaker": "وزير الاقتصاد وحماية المناخ الاتحادي (برلين)",
        "mode": "simultaneous",
        "tier": "free",
        "pair": "الألمانية ➔ العربية",
        "audio_lang": "de-DE",
        "source": "Sehr geehrte Damen und Herren, die Dekarbonisierung unserer industriellen Wertschöpfungsketten stellt nicht nur eine ökologische Notwendigkeit dar, sondern ist das Fundament der zukünftigen europäischen Wettbewerbsfähigkeit. Die Bundesregierung treibt den massiven Ausbau der erneuerbaren Energien und den raschen Hochlauf der Wasserstoffwirtschaft mit höchster Priorität voran. Dabei setzen wir auf strategische Partnerschaften mit den Ländern des südlichen Mittelmeerraums, insbesondere Algerien, um den Süd-Korridor für grünen Wasserstoff zu realisieren. Wir müssen regulatorische Hürden abbauen, verlässliche Abnahmeverträge garantieren und modernste Elektrolysetechnologien grenzüberschreitend skalieren, damit unsere Volkswirtschaften resilient und klimaneutral bleiben.",
        "solution": "سيداتي وسادتي، إن إزالة الكربون من سلاسل القيمة الصناعية لدينا لا تمثل مجرد ضرورة بيئية ملحة فحسب، بل هي الركيزة الأساسية للقدرة التنافسية الأوروبية في المستقبل. تضع الحكومة الاتحادية الألمانية التوسع الهائل في الطاقات المتجددة والتسريع العاجل لاقتصاد الهيدروجين على رأس أولوياتها القصوى. وفي هذا السياق، نعتمد على شراكات استراتيجية وثيقة مع دول جنوب البحر الأبيض المتوسط، ولا سيما الجزائر، لإنجاز الممر الجنوبي للهيدروجين الأخضر. يتعين علينا إزالة العقبات التنظيمية، وضمان عقود شراء موثوقة وطويلة الأجل، وتوسيع نطاق أحدث تقنيات التحليل الكهربائي عبر الحدود، بما يضمن بقاء اقتصاداتنا الوطنية مرنة ومحايدة مناخياً.",
        "terms": ["Dekarbonisierung (إزالة الكربون)", "Industrielle Wertschöpfungsketten (سلاسل القيمة الصناعية)", "Wasserstoffwirtschaft (اقتصاد الهيدروجين)", "Süd-Korridor für grünen Wasserstoff (الممر الجنوبي للهيدروجين الأخضر)", "Elektrolysetechnologien (تقنيات التحليل الكهربائي)"],
        "rozan": "Energy De : Decarbonize industry = eco + compete. Germ Gov: fast-track renew + H2. Strategic South Med (Alg) S-Corridor. Req: cut red-tape + long-term contracts."
    },
    {
        "id": "SP-112",
        "num": 112,
        "domain_id": "tech",
        "domain_ar": "الذكاء الاصطناعي والأمن السيبراني",
        "domain_en": "AI, Tech & Cybersecurity",
        "icon": "🤖",
        "title_ar": "حوكمة الذكاء الاصطناعي والأخلاقيات الرقمية في الاتحاد الأوروبي",
        "title_en": "European AI Act, Digital Sovereignty & Algorithmic Accountability",
        "speaker": "رئيسة لجنة الرقمنة والابتكار في البوندستاغ (برلين)",
        "mode": "simultaneous",
        "tier": "paid",
        "pair": "الألمانية ➔ العربية",
        "audio_lang": "de-DE",
        "source": "Verehrte Abgeordnete, mit dem europäischen KI-Gesetz betreten wir globales Neuland bei der Regulierung künstlicher Intelligenz. Unser Anspruch ist eindeutig: Wir wollen eine vertrauenswürdige, menschenzentrierte Technologieentwicklung fördern, ohne Innovationskraft und Unternehmergeist im Keim zu ersticken. Hochrisiko-Systeme müssen strengen Transparenz-, Sicherheits- und Diskriminierungsprüfungen unterliegen, während grundlegende Bürgerrechte, informationelle Selbstbestimmung und der Schutz geistigen Eigentums unantastbar bleiben. Europa wird zum globalen Standardsetzer für ethische digitale Technologien.",
        "solution": "حضرات النواب المحترمين، مع إقرار قانون الذكاء الاصطناعي الأوروبي، فإننا نلج آفاقاً عالمية غير مسبوقة في مجال تنظيم وتأطير الذكاء الاصطناعي. إن مبتغانا واضح المعالم لا لبس فيه: نحن نسعى لدعم وتطوير تقنيات موثوقة ومتمحورة حول الإنسان، دون خنق طاقات الابتكار وروح المبادرة الاستثمارية في مهدها. يجب أن تخضع الأنظمة عالية المخاطر لتدقيق صارم في مجالات الشفافية والأمان ومنع التمييز، مع الحفاظ على حرمة الحقوق المدنية الأساسية وتقرير المصير المعلوماتي وحماية الملكية الفكرية دون أي مساس. إن أوروبا تغدو المرجع القياسي العالمي في إرساء أخلاقيات التقنيات الرقمية.",
        "terms": ["Europäisches KI-Gesetz (قانون الذكاء الاصطناعي الأوروبي)", "Menschenzentrierte Technologie (تكنولوجيا متمحورة حول الإنسان)", "Hochrisiko-Systeme (الأنظمة عالية المخاطر)", "Informationelle Selbstbestimmung (تقرير المصير المعلوماتي)"],
        "rozan": "Tech De: EU AI Act = world 1st rule. Human-centric + protect innovate. High-risk = strict audit + transparency. Keep citizen data rights safe."
    },
    {
        "id": "SP-113",
        "num": 113,
        "domain_id": "economic",
        "domain_ar": "الاقتصاد الكلي والبنوك ودافوس",
        "domain_en": "Macroeconomics, Banking & Davos",
        "icon": "💼",
        "title_ar": "السياسة النقدية للبنك الاتحادي الألماني والتضخم في منطقة اليورو",
        "title_en": "Bundesbank Monetary Policy, Fiscal Discipline & Eurozone Resilience",
        "speaker": "رئيس البنك الاتحادي الألماني (دويتشه بوندسبانك - فرانكفورت)",
        "mode": "consecutive",
        "tier": "free",
        "pair": "الألمانية ➔ العربية",
        "audio_lang": "de-DE",
        "source": "Meine Damen und Herren, die Preisstabilität bleibt das unabdingbare Primärziel des Eurosystems. Trotz der allmählichen Normalisierung der Lieferketten erfordern anhaltende Zweitrundeneffekte bei Löhnen und Dienstleistungspreisen eine wachsame und datengetriebene Geldpolitik. Eine voreilige Lockerung der Zinspolitik birgt erhebliche Risiken für die langfristige Glaubwürdigkeit unserer Währung. Wir fordern zugleich eine Rückkehr zu solider Haushaltspolitik und konsequenter Haushaltsdisziplin in den Mitgliedstaaten, um die Widerstandskraft der gemeinsamen Währungsunion nachhaltig zu sichern.",
        "solution": "سيداتي وسادتي، يظل استقرار الأسعار الهدف الأساسي الذي لا غنى عنه لنظام اليورو المصرفي. وعلى الرغم من الانفراج التدريجي في سلاسل التوريد، فإن استمرار آثار الجولة الثانية على الأجور وأسعار الخدمات يستدعي سياسة نقدية يقظة ومستندة بدقة إلى البيانات الميدانية. إن أي تيسير متسرع لأسعار الفائدة ينطوي على مخاطر جسيمة تمس المصداقية طويلة الأمد لعملتنا الموحدة. ونطالب في الوقت ذاته بالعودة الصارمة إلى السياسات المالية الرشيدة والانضباط الميزانياتي في الدول الأعضاء، لضمان صمود اتحادنا النقدي المشترك على المدى المستدام.",
        "terms": ["Preisstabilität (استقرار الأسعار)", "Zweitrundeneffekte (آثار الجولة الثانية)", "Datengetriebene Geldpolitik (سياسة نقدية مستندة للبيانات)", "Haushaltsdisziplin (الانضباط الميزانياتي)"],
        "rozan": "Econ De : Price stability = primary goal. Alert on wages inflation. No rush to cut interest rate. Req: Strict fiscal budget control in Eurozone."
    },
    {
        "id": "SP-114",
        "num": 114,
        "domain_id": "legal",
        "domain_ar": "القانون والعقود والتحكيم الدولي",
        "domain_en": "Legal, Contracts & Arbitration",
        "icon": "⚖️",
        "title_ar": "التحكيم التجاري الدولي في النزاعات العابرة للحدود وقواعد فيينا للمصالحة",
        "title_en": "International Commercial Arbitration & Cross-Border Dispute Adjudication",
        "speaker": "رئيس محكمة التحكيم الألمانية الدولية (DIS - هامبورغ)",
        "mode": "simultaneous",
        "tier": "paid",
        "pair": "الألمانية ➔ العربية",
        "audio_lang": "de-DE",
        "source": "Sehr geehrte Kolleginnen und Kollegen, das Vertrauen des internationalen Handels ruht maßgeblich auf der Vorhersehbarkeit und Vollstreckbarkeit schiedsgerichtlicher Entscheidungen. Das New Yorker Übereinkommen von 1958 bildet den unerschütterlichen Pfeiler dieser Rechtsordnung. In Zeiten geopolitischer Fragmentierung und protektionistischer Tendenzen müssen Schiedsinstitutionen höchste Standards bezüglich Unabhängigkeit, prozessualer Fairness und Effizienz gewährleisten. Nur so bleibt das internationale Schiedsverfahren der bevorzugte Weg zur Beilegung komplexer grenzüberschreitender Wirtschaftsstreitigkeiten.",
        "solution": "زميلاتي وزملائي الأعزاء، إن ثقة التجارة الدولية تستند بشكل حاسم إلى إمكانية التنبؤ بالأحكام التحكيمية وقابليتها للتنفيذ الفعلي. وتشكل اتفاقية نيويورك لعام 1958 الركيزة الثابتة التي لا تتزعزع لهذا النظام القانوني الدولي. وفي ظل التشرذم الجيوسياسي الراهن وتصاعد النزعات الحمائية، يتعين على مؤسسات التحكيم ضمان أعلى المعايير المتعلقة بالاستقلالية والعدالة الإجرائية والنجاعة الزمنية؛ فبذلك وحده يظل التحكيم الدولي المسار المفضل لتسوية المنازعات التجارية والاقتصادية المعقدة العابرة للحدود.",
        "terms": ["Vollstreckbarkeit (القابلية للتنفيذ)", "New Yorker Übereinkommen (اتفاقية نيويورك 1958)", "Prozessuale Fairness (العدالة الإجرائية)", "Schiedsinstitutionen (مؤسسات وهيئات التحكيم)"],
        "rozan": "Law De : Trade trust = enforce arb rulings. NY Conv 1958 pillar. Institutions must ensure: Indep + fairness + speed to solve cross-border disputes."
    },

    # --- ESPAÑOL (Spanish -> Arabic) 3 new long speeches ---
    {
        "id": "SP-115",
        "num": 115,
        "domain_id": "diplomatic",
        "domain_ar": "القمم الدبلوماسية والأمم المتحدة",
        "domain_en": "Diplomacy & UN Summits",
        "icon": "🌐",
        "title_ar": "القمة الإيبيرو-أمريكية: التضامن الإقليمي والتنمية المستدامة للأجيال القادمة",
        "title_en": "Ibero-American Summit: Regional Multilateralism, Equity & Sustainable Development",
        "speaker": "وزير الشؤون الخارجية والاتحاد الأوروبي والتعاون (مدريد)",
        "mode": "simultaneous",
        "tier": "free",
        "pair": "الإسبانية ➔ العربية",
        "audio_lang": "es-ES",
        "source": "Señoras y señores ministros, delegados distinguidos: la comunidad iberoamericana se encuentra ante una encrucijada histórica donde la cooperación multilateral y el multilateralismo inclusivo ya no son una opción diplomática, sino un imperativo de supervivencia colectiva. La brecha estructural de desigualdad que afecta a nuestras sociedades exige una reformulación profunda de los mecanismos de financiación para el desarrollo sostenible. No podemos tolerar que las crisis climáticas y financieras golpeen con mayor saña a las poblaciones más vulnerables. Reafirmamos nuestro compromiso inquebrantable con el Estado de derecho, la justicia social y el fortalecimiento de nuestras instituciones democráticas compartidas.",
        "solution": "حضرات الوزراء والسفراء، المندوبون الموقرون: تقف أسرة الدول الإيبيرو-أمريكية اليوم أمام مفترق طرق تاريخي، حيث لم تعد الشراكة متعددة الأطراف والتعاون الشامل خياراً دبلوماسياً قابلاً للتأجيل، بل غدا واجباً حتمياً للبقاء المشترك. إن الفجوة الهيكلية للتفاوت الاقتصادي والاجتماعي التي تلقي بظلالها على مجتمعاتنا تفرض إعادة صياغة جذرية لآليات تمويل التنمية المستدامة. لا يمكننا القبول بأن تضرب الأزمات المناخية والمالية الفئات الأكثر هشاشة بأشد درجات القسوة. ونؤكد مجدداً التزامنا الثابت والراسخ بسيادة القانون، والعدالة الاجتماعية، وترسيخ مؤسساتنا الديمقراطية المشتركة.",
        "terms": ["Comunidad iberoamericana (المجموعة الإيبيرو-أمريكية)", "Multilateralismo inclusivo (العمل التعددي الشامل)", "Brecha estructural (الفجوة الهيكلية)", "Financiación para el desarrollo (تمويل التنمية)", "Estado de derecho (دولة القانون)"],
        "rozan": "Dip Es : Ibero-Am Summit: Multilat = survival must. Fix gap in wealth + finance for dev. Protect poor from climate hit. Support law + democracy."
    },
    {
        "id": "SP-116",
        "num": 116,
        "domain_id": "culture",
        "domain_ar": "اليونسكو والتراث والتنوع اللغوي",
        "domain_en": "UNESCO, Heritage & Linguistic Diversity",
        "icon": "🏛️",
        "title_ar": "المؤتمر الدولي لليونسكو: حماية التراث غير المادي والتنوع الثقافي في العصر الرقمي",
        "title_en": "UNESCO World Forum: Intangible Heritage Safeguarding & Plurilingualism",
        "speaker": "المدير العام المساعد للثقافة لدى منظمة اليونسكو",
        "mode": "simultaneous",
        "tier": "paid",
        "pair": "الإسبانية ➔ العربية",
        "audio_lang": "es-ES",
        "source": "Estimados participantes, el patrimonio cultural inmaterial no es una reliquia inerte del pasado, sino un testimonio vivo de la resiliencia y creatividad de los pueblos del mundo. En esta era de hiperconectividad digital y algoritmos homogeneizadores, la diversidad lingüística y las tradiciones orales corren el riesgo de extinguirse si no desplegamos políticas públicas audaces de preservación y transmisión intergeneracional. El idioma no es meramente un instrumento de comunicación técnica, sino el alma misma de la cosmovisión y memoria histórica de cada comunidad. Defender la pluralidad lingüística es defender la dignidad humana universal.",
        "solution": "أيها المشاركون الكرام، إن التراث الثقافي غير المادي ليس مجرد أثر متحفي ساكن من مخلفات الماضي، بل هو شاهد حي نابض على صمود وإبداع شعوب المعمورة. وفي عصرنا الحالي الذي يتسم بالترابط الرقمي الفائق والخوارزميات التي تكرس النمطية الأحادية، بات التنوع اللغوي والتقاليد الشفهية مهددة بالاندثار ما لم نطلق سياسات عامة جريئة تكفل حمايتها ونقلها عبر الأجيال المتعاقبة. إن اللغة ليست مجرد أداة تواصل تقنية بحتة، بل هي جوهر الرؤية الوجودية والذاكرة التاريخية لكل أمة. وإن الذود عن التعددية اللغوية هو ذود أصيل عن الكرامة الإنسانية العالمية.",
        "terms": ["Patrimonio cultural inmaterial (التراث الثقافي غير المادي)", "Diversidad lingüística (التنوع اللغوي)", "Transmisión intergeneracional (التناقل بين الأجيال)", "Cosmovisión (الرؤية الوجودية للعالم)"],
        "rozan": "Cult Es : UNESCO Forum: Intangible heritage = living memory. Algorithms risk wiping local tongues. Lang = soul + worldview. Protect multilingual diversity."
    },
    {
        "id": "SP-117",
        "num": 117,
        "domain_id": "logistics",
        "domain_ar": "التجارة الدولية وسلاسل الإمداد",
        "domain_en": "Global Trade & Supply Chains",
        "icon": "🚢",
        "title_ar": "منتدى النقل واللوجستيات الأورومتوسطي: توسعة الموانئ وسلاسل التوريد الخضراء",
        "title_en": "Euro-Med Ports & Maritime Logistics Summit: Green Shipping Corridors",
        "speaker": "رئيس هيئة الموانئ واللوجستيات البحرية (فالنسيا)",
        "mode": "consecutive",
        "tier": "free",
        "pair": "الإسبانية ➔ العربية",
        "audio_lang": "es-ES",
        "source": "Distinguidos colegas, la cuenca mediterránea ha sido históricamente la arteria vital del comercio entre tres continentes. Hoy, frente a las tensiones en las rutas marítimas globales y las exigencias de descarbonización portuaria, estamos obligados a reinventar nuestra infraestructura logística. La electrificación de los muelles, la digitalización aduanera integral y el desarrollo de corredores verdes no son proyectos futuristas, sino realidades inmediatas que determinan la viabilidad de nuestros puertos. La cooperación interportuaria entre las orillas norte y sur del Mediterráneo es la única garantía para cadenas de suministro ágiles, seguras y respetuosas con el medio ambiente.",
        "solution": "زملائي الموقرين، لطالما كان حوض البحر الأبيض المتوسط تاريخياً الشريان الحيوي النابض لحركة التجارة بين القارات الثلاث. واليوم، في مواجهة التوترات التي تعصف بطرق الملاحة البحرية العالمية والتحديات الإلزامية لخفض الانبعاثات الكربونية في الموانئ، نجد أنفسنا ملزمين بإعادة ابتكار البنية التحتية اللوجستية كلياً. إن تزويد الأرصفة بالطاقة الكهربائية، والرقمنة الجمركية الشاملة، واستحداث ممرات شحن بحرية خضراء ليست مشاريع مستقبلية مؤجلة، بل هي ضرورات ملحة تحدد مدى بقاء موانئنا وتنافسيتها. إن التنسيق الوثيق بين موانئ ضفتي المتوسط الشمالية والجنوبية هو الضمان الأوحد لتدفق سلاسل إمداد مرنة، آمنة وصديقة للبيئة.",
        "terms": ["Cuenca mediterránea (حوض البحر الأبيض المتوسط)", "Descarbonización portuaria (خفض الكربون في الموانئ)", "Electrificación de muelles (كهربة أرصفة الموانئ)", "Corredores verdes (الممرات الخضراء)"],
        "rozan": "Trade Es : Med ports = vital artery 3 continents. Need green shipping + electrify docks + digi customs. North-South Med port alliance = secure supply line."
    },

    # --- ITALIANO (Italian -> Arabic) 3 new long speeches ---
    {
        "id": "SP-118",
        "num": 118,
        "domain_id": "diplomatic",
        "domain_ar": "القمم الدبلوماسية والأمم المتحدة",
        "domain_en": "Diplomacy & UN Summits",
        "icon": "🌐",
        "title_ar": "قمة روما للأمن الغذائي والتعاون الأورومتوسطي: استراتيجية الشراكة مع إفريقيا",
        "title_en": "Rome International Summit on Food Security & Euro-African Strategic Partnership",
        "speaker": "وكيل وزارة الشؤون الخارجية والتعاون الدولي الإيطالي (فارنيسينا - روما)",
        "mode": "simultaneous",
        "tier": "free",
        "pair": "الإيطالية ➔ العربية",
        "audio_lang": "it-IT",
        "source": "Signore e signori, la sicurezza alimentare e l'accesso equo alle risorse idriche costituiscono i pilastri irrinunciabili della stabilità geopolitica nell'area mediterranea e nel continente africano. Il Piano Mattei per l'Africa, promosso dal governo italiano, rifiuta ogni logica predatoria o assistenzialista, puntando invece su investimenti infrastrutturali condivisi, trasferimento tecnologico nel settore agroalimentare e sviluppo del capitale umano locale. Dobbiamo costruire partenariati paritari che trasformino le sfide migratorie ed economiche in concrete opportunità di crescita reciproca, pace duratura e prosperità condivisa.",
        "solution": "سيداتي وسادتي، يمثل الأمن الغذائي والحصول العادل على الموارد المائية الركيزتين اللتين لا غنى عنهما للاستقرار الجيوسياسي في الحوض المتوسط والقارة الإفريقية برمتها. وتتبنى خطة ماتي لأجل إفريقيا، التي أطلقتها الحكومة الإيطالية، نهجاً يرفض قطعياً منطق الاستنزاف أو الإعانات التبعية، مركزة عوضاً عن ذلك على استثمارات بنيوية مشتركة، ونقل التكنولوجيا الزراعية والغذائية، وتأهيل رأس المال البشري المحلي. يتعين علينا إرساء شراكات متكافئة قائمة على الند للند، تحول تحديات الهجرة والأزمات الاقتصادية إلى فرص حقيقية للنمو المتبادل، والسلام الدائم، والازدهار المشترك.",
        "terms": ["Sicurezza alimentare (الأمن الغذائي)", "Piano Mattei per l'Africa (خطة ماتي لأجل إفريقيا)", "Investimenti infrastrutturali (الاستثمارات في البنية التحتية)", "Partenariati paritari (شراكات قائمة على الند للند)"],
        "rozan": "Dip It : Rome Summit: Food security + water = geo stability. Mattei Plan Africa: no exploit, equal partner. Build farm tech + human capital."
    },
    {
        "id": "SP-119",
        "num": 119,
        "domain_id": "energy",
        "domain_ar": "الطاقة والمناخ والغاز والهيدروجين",
        "domain_en": "Energy, Climate & Hydrogen",
        "icon": "⚡",
        "title_ar": "مؤتمر الطاقة العالمي: استراتيجيات الغاز الطبيعي وأنابيب البحر الأبيض المتوسط",
        "title_en": "World Energy Forum: Mediterranean Gas Infrastructure & South H2 Pipeline",
        "speaker": "الرئيس التنفيذي لمجموعة الطاقة الإيطالية (إيني ENI - ميلانو)",
        "mode": "simultaneous",
        "tier": "paid",
        "pair": "الإيطالية ➔ العربية",
        "audio_lang": "it-IT",
        "source": "Cari delegati, la transizione energetica deve essere gestita con pragmatico realismo industriale. Il gas naturale rimane il combustibile ponte fondamentale per garantire la sicurezza degli approvvigionamenti mentre acceleriamo lo sviluppo delle fonti rinnovabili e delle reti a idrogeno. I gasdotti sottomarini transmediterranei, che collegano da decenni l'Algeria all'Italia, rappresentano la spina dorsale di questa sicurezza energetica. La nostra visione strategica è quella di trasformare questa consolidata infrastruttura in un hub energetico bidirezionale, capace di trasportare idrogeno verde verso il cuore dell'Europa industriale.",
        "solution": "أيها المندوبون الأعزاء، إن عملية الانتقال الطاقوي يجب أن تدار بواقعية صناعية وبراغماتية متوازنة. ويظل الغاز الطبيعي الوقود الجسري المحوري الذي لا بديل عنه لضمان أمن الإمدادات تزامناً مع تسريع وتيرة الطاقات المتجددة وشبكات الهيدروجين الواعدة. وتمثل خطوط أنابيب الغاز العابرة للمتوسط تحت قاع البحر، والتي تربط منذ عقود بين الجزائر وإيطاليا، العمود الفقري للأمن الطاقوي الأوروبي. وإن رؤيتنا الاستراتيجية ترتكز على تحويل هذه المنظومة البنيوية الراسخة إلى محور طاقوي ثنائي الاتجاه، قادر على ضخ ونقل الهيدروجين الأخضر مستقبلاً إلى قلب المراكز الصناعية في أوروبا.",
        "terms": ["Transizione energetica (الانتقال الطاقوي)", "Combustibile ponte (الوقود الجسري الانتقالي)", "Gasdotti sottomarini (أنابيب الغاز البحرية)", "Hub energetico (محور / مركز طاقوي)"],
        "rozan": "Energy It : Realist energy shift. Gas = bridge fuel. Trans-Med subsea pipes Alg-Italy = backbone. Vision: Upgrade pipes to carry Green H2 to EU."
    },
    {
        "id": "SP-120",
        "num": 120,
        "domain_id": "legal",
        "domain_ar": "القانون والعقود والتحكيم الدولي",
        "domain_en": "Legal, Contracts & Arbitration",
        "icon": "⚖️",
        "title_ar": "المؤتمر السنوي للغرف القضائية والتحكيمية: حماية الاستثمارات الأجنبية في عقود الطاقة",
        "title_en": "Chamber of Arbitration Milan: Foreign Investment Protection in Energy Concessions",
        "speaker": "الأمين العام لغرفة التحكيم في ميلانو (CAM - إيطاليا)",
        "mode": "consecutive",
        "tier": "free",
        "pair": "الإيطالية ➔ العربية",
        "audio_lang": "it-IT",
        "source": "Illustri partecipanti, la certezza del diritto e la stabilità delle clausole contrattuali sono le condizioni sine qua non per qualsiasi flusso duraturo di investimenti diretti esteri. Negli investimenti energetici ad altissima intensità di capitale, le clausole di stabilizzazione e il ricorso all'arbitrato internazionale secondo le regole UNCITRAL offrono agli operatori economici la tutela necessaria contro modifiche normative unilaterali o espropriazioni indirette. Un quadro regolatorio trasparente ed equo costituisce il migliore incentivo per attrarre capitali privati verso infrastrutture strategiche a lungo termine.",
        "solution": "أيها الحضور الأفاضل، إن اليقين القانوني واستقرار الشروط والبنود التعاقدية يمثلان الشرط الأساسي الذي لا غنى عنه لضمان استمرار تدفق الاستثمارات الأجنبية المباشرة. وفي الاستثمارات الطاقوية ذات الكثافة الرأسمالية الفائقة، فإن بنود الاستقرار التشريعي واللجوء إلى التحكيم الدولي بموجب قواعد الأونسيترال يمنحان المستثمرين والشركات الحماية القانونية اللازمة ضد أي تعديلات تنظيمية أحادية الجانب أو نزع ملكية مقنع وغير مباشر. إن وجود إطار تنظيمي شفاف وعادل يعد أفضل حافز لاستقطاب الرساميل الخاصة نحو مشاريع البنية التحتية الاستراتيجية طويلة الأجل.",
        "terms": ["Certezza del diritto (اليقين القانوني)", "Investimenti diretti esteri (الاستثمارات الأجنبية المباشرة)", "Clausole di stabilizzazione (بنود الاستقرار التشريعي)", "Regole UNCITRAL (قواعد الأونسيترال للتحكيم)"],
        "rozan": "Law It : Legal certainty = key for FDI. Big energy capital needs: stabilization clauses + UNCITRAL arbitration vs unilateral change. Fair rules attract funds."
    }
]

speeches.extend(new_speeches)
print('Total speeches after expansion:', len(speeches))

header = '// booth_100_speeches_data.js - 120 Authentic Conference Interpretation Speeches & Texts\n// Polylang Hub | ISO 17100:2015 Accredited Multi-Language Conference Simulator\n\n'
var_decl = 'var BOOTH_100_SPEECHES = ' + json.dumps(speeches, ensure_ascii=False, indent=2) + ';\n\n'
attach = 'if (typeof window !== "undefined") { window.BOOTH_100_SPEECHES = BOOTH_100_SPEECHES; }\nif (typeof global !== "undefined") { global.BOOTH_100_SPEECHES = BOOTH_100_SPEECHES; }\n'

with open('booth_100_speeches_data.js', 'w', encoding='utf-8') as out_f:
    out_f.write(header + var_decl + attach)

print('Done writing booth_100_speeches_data.js!')
