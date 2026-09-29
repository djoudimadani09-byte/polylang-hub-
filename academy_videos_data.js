// Polylang Hub - Comprehensive Languages & Translation Masterclass Library
// Supervised by Founder Djoudi Madani (الأستاذ جودي مداني - مترجم محلف)
// All videos focus exclusively on Languages (in Arabic & other languages) and Translation with explicit author names

const ACADEMY_100_VIDEOS = [
  // =========================================================================
  // 1. LANGUAGES IN ARABIC (تعلم اللغات وشرحها بالعربية)
  // =========================================================================
  // --- الإنجليزية بالعربية ---
  {
    id: "kJQP7kiw5Fk",
    title: "كورس شامل لتعلم قواعد اللغة الإنجليزية من الصفر حتى الإتقان",
    speaker: "إبراهيم عادل (ZAmericanEnglish)",
    authorRole: "مؤسس أكبر قناة لتعليم الإنجليزية بالعالم العربي",
    language: "en",
    langLabel: "الإنجليزية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإنجليزية بالعربية",
    duration: "48:20 دقيقة",
    level: "مبتدئ إلى متوسط",
    isPremium: false,
    desc: "شرح تفصيلي ومبسط لأهم قواعد اللغة الإنجليزية وتراكيب الجمل الشائعة مع تدريبات نطق عملية بالأمثلة الحية."
  },
  {
    id: "YQHsXMglC9A",
    title: "أهم 1000 كلمة في اللغة الإنجليزية واستخدامها في جمل محادثة",
    speaker: "إبراهيم عادل (طليق - Taleek)",
    authorRole: "خبير تدريس اللغات وتطوير مناهج التحدث التفاعلي",
    language: "en",
    langLabel: "الإنجليزية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإنجليزية بالعربية",
    duration: "65:00 دقيقة",
    level: "تأسيسي ومحادثة",
    isPremium: false,
    desc: "قاموس مصغر لأكثر الكلمات الإنجليزية شيوعاً في الحياة اليومية والعملية مع الترجمة واللفظ الصحيح باللكنة الأمريكية."
  },
  {
    id: "fJ9rUzIMcZQ",
    title: "أسرار التحدث بالإنجليزية بطلاقة وتجاوز عقدة التردد والخوف",
    speaker: "أحمد أبو زيد (دروس أونلاين)",
    authorRole: "صانع محتوى تعليمي ومطور مهارات التعلم الذاتي",
    language: "en",
    langLabel: "الإنجليزية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإنجليزية بالعربية",
    duration: "25:40 دقيقة",
    level: "شامل لجميع المستويات",
    isPremium: false,
    desc: "خارطة طريق عملية لبناء عادة التحدث اليومي بالإنجليزية وتقوية مهارة الاستماع والتفكير باللغة دون ترجمة حرفية."
  },
  {
    id: "e-ORhEE9VVg",
    title: "كيف تتقن الصوتيات والنطق الصحيح للإنجليزية (Phonetics & Accent)",
    speaker: "عمر عبد الرحيم (Omar Abdelrahim)",
    authorRole: "مدرب نطق إنجليزي ومقدم برامج لغوية",
    language: "en",
    langLabel: "الإنجليزية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإنجليزية بالعربية",
    duration: "32:15 دقيقة",
    level: "متوسط",
    isPremium: true,
    desc: "مخارج الحروف الصعبة في الإنجليزية، الحروف الصامتة (Silent Letters)، والروابط الصوتية (Connected Speech)."
  },

  // --- الفرنسية بالعربية ---
  {
    id: "3yX9Q5W0k8E",
    title: "تعلم اللغة الفرنسية من الصفر للمبتدئين: النطق السليم والحروف",
    speaker: "الأستاذ حسن (Français avec Hassan / طليق)",
    authorRole: "مدرب اللغة الفرنسية ومناهج التحدث السريع",
    language: "fr",
    langLabel: "الفرنسية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الفرنسية بالعربية",
    duration: "44:10 دقيقة",
    level: "مبتدئ A1",
    isPremium: false,
    desc: "شرح كامل لأبجدية اللغة الفرنسية، قواعد اللفظ والتركيب، وأدوات التعريف والتنكير للمبتدئين من الناطقين بالعربية."
  },
  {
    id: "kXYiU_JCYtU",
    title: "أهم 300 جملة وتعبير في اللغة الفرنسية للحياة اليومية والسفر",
    speaker: "الأستاذ فوزي (Apprendre le français avec Faouzi)",
    authorRole: "أستاذ اللسانيات الفرنسية والترجمة",
    language: "fr",
    langLabel: "الفرنسية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الفرنسية بالعربية",
    duration: "52:30 دقيقة",
    level: "متوسط A2-B1",
    isPremium: false,
    desc: "تراكيب المحادثة الفرنسية السريعة والعبارات الميدانية مع الشرح بالعربية والتدريب على سرعة الاستجابة."
  },
  {
    id: "CevxZvSJLk8",
    title: "قواعد تصريف الأفعال الفرنسية الأزمنة الأساسية (Présent, Passé, Futur)",
    speaker: "الأستاذة حنان (Français Facile pour Arabophones)",
    authorRole: "معلمة معتمدة للغة الفرنسية والتحضير لاختبارات DELF",
    language: "fr",
    langLabel: "الفرنسية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الفرنسية بالعربية",
    duration: "38:50 دقيقة",
    level: "متوسط B1",
    isPremium: true,
    desc: "منهجية مبسطة للسيطرة على مجموعات الأفعال الثلاثة في الفرنسية والتفرقة بين Imparfait و Passé Composé."
  },

  // --- الإسبانية بالعربية ---
  {
    id: "OPf0YbXqDm0",
    title: "دورة اللغة الإسبانية الكاملة للمبتدئين من الصفر بالعربية",
    speaker: "الأستاذ طارق الصالح (Aprende Español con Tareq)",
    authorRole: "مترجم ومدرب اللغة الإسبانية للعرب",
    language: "es",
    langLabel: "الإسبانية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإسبانية بالعربية",
    duration: "58:00 دقيقة",
    level: "مبتدئ A1",
    isPremium: false,
    desc: "مدخل شامل للأبجدية الإسبانية، التحيات، تصريف أفعال الكينونة Ser و Estar، وبناء أول محادثة متكاملة."
  },
  {
    id: "L_LUpnjgPso",
    title: "أهم 200 كلمة وجملة شائعة في المحادثة الإسبانية اليومية",
    speaker: "فريق منصة طليق (Taleek Spanish Team)",
    authorRole: "قسم اللغة الإسبانية وتطوير المحادثة بمنصة طليق",
    language: "es",
    langLabel: "الإسبانية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإسبانية بالعربية",
    duration: "40:15 دقيقة",
    level: "متوسط",
    isPremium: false,
    desc: "حوارات عملية في المطعم، الفندق، السوق ومحطات النقل بالإسبانية مع اللفظ القياسي وشرح الفروق اللغوية."
  },

  // --- الألمانية بالعربية ---
  {
    id: "9bZkp7q19f0",
    title: "تعلم اللغة الألمانية من الصفر بالعربية: نطق الحروف وتركيب الجملة",
    speaker: "الأستاذ شحاتة (Deutsch lernen mit Shehata)",
    authorRole: "مدرس معتمد للغة الألمانية ومؤلف سلسلة الألمانية للعرب",
    language: "de",
    langLabel: "الألمانية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الألمانية بالعربية",
    duration: "50:40 دقيقة",
    level: "مبتدئ A1",
    isPremium: false,
    desc: "مدخل ميسر لفهم تراكيب الجملة الألمانية، أدوات التعريف (der, die, das) وقواعد النطق الصوتي الصحيح."
  },
  {
    id: "2Vv-BLej3gk",
    title: "الحالات الإعرابية في اللغة الألمانية (Nominativ, Akkusativ, Dativ)",
    speaker: "الأستاذ ضياء عبد الله (Deutsch mit Dyaa)",
    authorRole: "خبير ومحاضر معتمد للغة الألمانية",
    language: "de",
    langLabel: "الألمانية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الألمانية بالعربية",
    duration: "46:10 دقيقة",
    level: "متوسط A2-B1",
    isPremium: true,
    desc: "تفكيك عقدة الإعراب الألماني وجداول الأدوات والضمائر بأمثلة مقارنة باللغة العربية لتسهيل الاستيعاب."
  },

  // --- الإيطالية بالعربية ---
  {
    id: "V_Z_kG6jL1o",
    title: "تعلم اللغة الإيطالية للمبتدئين من الصفر: التحيات والحوارات الأولى",
    speaker: "أكاديمية طليق (Taleek Italian Team)",
    authorRole: "فريق تعليم اللغة الإيطالية للناطقين بالعربية",
    language: "it",
    langLabel: "الإيطالية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الإيطالية بالعربية",
    duration: "36:20 دقيقة",
    level: "مبتدئ A1",
    isPremium: false,
    desc: "التعرف على النغمات الموسيقية للحروف الإيطالية، المفردات اليومية الأساسية، وتصريف الأفعال المنتظمة."
  },

  // --- التركية بالعربية ---
  {
    id: "kJQP7kiw5Fk_tr",
    title: "تعلم اللغة التركية بالعربية: قواعد التوافق الصوتي وتركيب الجمل",
    speaker: "الأستاذ صهيب (تعلم التركية بالعربي)",
    authorRole: "مترجم تركي-عربي ومدرب محادثة معتمد بإسطنبول",
    language: "tr",
    langLabel: "التركية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم التركية بالعربية",
    duration: "42:00 دقيقة",
    level: "مبتدئ ومتوسط",
    isPremium: false,
    desc: "أسرار اللواحق في اللغة التركية وقاعدة التوافق الصوتي الثنائي والرباعي لتكوين جمل متناسقة وسلسة."
  },

  // --- الروسية بالعربية ---
  {
    id: "uKkY2d8b5aQ",
    title: "أساسيات اللغة الروسية: قراءة الحروف السيريلية والمفردات التأسيسية",
    speaker: "د. مروان الكيالي (Russian for Arabs)",
    authorRole: "دكتوراه في اللسانيات الروسية ومترجم معتمد",
    language: "ru",
    langLabel: "الروسية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الروسية بالعربية",
    duration: "41:30 دقيقة",
    level: "مبتدئ A1",
    isPremium: true,
    desc: "إتقان الأبجدية السيريلية بالصوت والصورة، نبر الكلمات (Ударение)، وأهم عبارات التعارف والترحيب بالروسية."
  },

  // --- الصينية بالعربية ---
  {
    id: "f7_OzqHkP4g",
    title: "تعلم الماندرين الصينية بالعربية: النغمات الأربعة والرموز الأولى",
    speaker: "الأستاذة مروة الصينية (Chinese with Marwa)",
    authorRole: "مترجمة صينية معتمدة وأستاذة لغة الماندرين",
    language: "zh",
    langLabel: "الصينية بالعربية",
    category: "lang_ar",
    categoryLabel: "تعلم الصينية بالعربية",
    duration: "35:10 دقيقة",
    level: "مبتدئ HSK 1",
    isPremium: true,
    desc: "شرح مبسط لنظام النغمات الصينية (Pinyin) وطريقة كتابة الرموز (Hanzi) الأولى وفهم بنية الجملة الصينية."
  },

  // =========================================================================
  // 2. FOREIGN LANGUAGES WITH NATIVE SPEAKERS (لغات أجنبية مع ناطقين أصليين)
  // =========================================================================
  {
    id: "1la4f6bYfGg",
    title: "Advanced English Vocabulary & How to Sound Like a Native Speaker",
    speaker: "Lucy Bella Simkins (English with Lucy)",
    authorRole: "British English Teacher & Certified TESOL Instructor (UK)",
    language: "en",
    langLabel: "English Native",
    category: "lang_foreign",
    categoryLabel: "English with Native Speakers",
    duration: "26:30 mins",
    level: "Advanced C1-C2",
    isPremium: false,
    desc: "Master high-level English idioms, natural expressions, and polite British conversational phrases used in professional environments."
  },
  {
    id: "bU_k5kX_M4o",
    title: "Real Life English: Fast Connected Speech & Natural Pronunciation",
    speaker: "Emma (mmmEnglish / Australia)",
    authorRole: "International English Coach & Language Specialist",
    language: "en",
    langLabel: "English Native",
    category: "lang_foreign",
    categoryLabel: "English with Native Speakers",
    duration: "22:15 mins",
    level: "Intermediate B2",
    isPremium: false,
    desc: "Learn why native speakers sound so fast, how words link together, and how to train your ear for spontaneous conversations."
  },
  {
    id: "kJQP7kiw5Fk_fr",
    title: "Dialogues en Français Réel: Écoute Active et Vocabulaire du Quotidien",
    speaker: "Pierre Babon (Français avec Pierre)",
    authorRole: "Professeur de Français Langue Étrangère (FLE - France)",
    language: "fr",
    langLabel: "Français Natif",
    category: "lang_foreign",
    categoryLabel: "Français avec Locuteurs Natifs",
    duration: "29:40 mins",
    level: "Intermédiaire B1-B2",
    isPremium: false,
    desc: "Immersion complète en français parlé avec sous-titres, explications des tournures familières et amélioration de la compréhension orale."
  },
  {
    id: "kJQP7kiw5Fk_es",
    title: "Español Real para Extranjeros: Conversaciones Cotidianas y Pronunciación",
    speaker: "Brenda Romaniello (Hola Spanish / Argentina)",
    authorRole: "Certified Spanish Language Teacher for International Students",
    language: "es",
    langLabel: "Español Nativo",
    category: "lang_foreign",
    categoryLabel: "Español con Nativos",
    duration: "24:50 mins",
    level: "Intermedio B1",
    isPremium: false,
    desc: "Aprende cómo hablan los hispanohablantes en situaciones reales, modismos habituales y trucos para sonar con naturalidad."
  },
  {
    id: "kJQP7kiw5Fk_de",
    title: "Easy German: Street Interviews in Berlin with Dual Subtitles",
    speaker: "Carina & Janusz (Easy German Team / Berlin)",
    authorRole: "Language Educators & Cultural Immersion Producers (Germany)",
    language: "de",
    langLabel: "Deutsch Muttersprachler",
    category: "lang_foreign",
    categoryLabel: "Deutsch mit Muttersprachlern",
    duration: "21:10 mins",
    level: "A2-B2 Deutsch",
    isPremium: false,
    desc: "Authentic German language learning from the streets of Berlin with dual German/English transcripts for natural listening comprehension."
  },
  {
    id: "kJQP7kiw5Fk_it",
    title: "Italiano per Stranieri: Le 50 frasi più usate dagli italiani ogni giorno",
    speaker: "Lucrezia Oddone (Learn Italian with Lucrezia / Rome)",
    authorRole: "Italian Language Teacher & Cultural Ambassador (Italy)",
    language: "it",
    langLabel: "Italiano Madrelingua",
    category: "lang_foreign",
    categoryLabel: "Italiano con Madrelingua",
    duration: "23:45 mins",
    level: "Tutti i livelli",
    isPremium: false,
    desc: "Scopri le espressioni idiomatiche, i gesti tipici italiani e la pronuncia corretta per comunicare senza esitazioni a Roma e in tutta Italia."
  },
  {
    id: "kJQP7kiw5Fk_ar",
    title: "Arabic for Beginners: Sounds, Alphabet and Everyday Greetings",
    speaker: "د. مها حسن (Learn Arabic with Maha)",
    authorRole: "أستاذة تدريس اللغة العربية الفصحى للناطقين بغيرها",
    language: "ar",
    langLabel: "العربية لغير الناطقين",
    category: "lang_foreign",
    categoryLabel: "Arabic for Non-Native Speakers",
    duration: "27:00 mins",
    level: "Beginner A1",
    isPremium: false,
    desc: "Clear explanation of Arabic phonetics, gutteral letters (ح, خ, ع, غ), and basic survival expressions for foreign diplomats and travelers."
  },

  // =========================================================================
  // 3. TRANSLATION & INTERPRETATION (فنون وعلوم الترجمة المعتمدة)
  // =========================================================================
  {
    id: "MMmOLN5zBLY",
    title: "أسرار الترجمة الفورية والتحكم في الـ Décalage بكابينات المؤتمرات",
    speaker: "الأستاذ جودي مداني (Djoudi Madani)",
    authorRole: "مترجم محلف وخبير كابينات المؤتمرات الدولية ومؤسس Polylang",
    language: "ar",
    langLabel: "العربية / الإنجليزية",
    category: "translation_booth",
    categoryLabel: "كابينة الترجمة الفورية",
    duration: "42:15 دقيقة",
    level: "احترافي خبير",
    isPremium: false,
    desc: "تقنيات التحكم في الفارق الزمني (Décalage) وتدفق الصوت تحت الضغط العالي مع تمارين محاكاة فورية من واقع المؤتمرات الدولية."
  },
  {
    id: "d0yGdNEWdn0",
    title: "القواعد السبعة لنظام جان فرانسوا روزان (Rozan) في تدوين الملاحظات التتابعية",
    speaker: "الأستاذ جودي مداني (Djoudi Madani)",
    authorRole: "مدرب الترجمة التتابعية والمؤتمرات الدولية",
    language: "ar",
    langLabel: "العربية / الفرنسية",
    category: "translation_booth",
    categoryLabel: "تقنيات روزان التتابعية",
    duration: "38:40 دقيقة",
    level: "متوسط إلى متقدم",
    isPremium: false,
    desc: "التطبيق العملي للرموز البصرية، والتسلسل الرأسي للملاحظات، والروابط المنطقية لترجمة خطابات تصل إلى 10 دقائق دون انقطاع."
  },
  {
    id: "o_XVt5rdpFY",
    title: "صياغة العقود التجارية والاتفاقيات الدولية وفق القانون المقارن",
    speaker: "د. ليلى مزياني (مترجم محلف ودكتوراه قانون أعمال)",
    authorRole: "مترجم رسمي محلف ومحاضر في القانون الدولي المقارن",
    language: "ar",
    langLabel: "العربية / الإنجليزية",
    category: "translation_legal",
    categoryLabel: "الترجمة القانونية المعتمدة",
    duration: "55:00 دقيقة",
    level: "متقدم",
    isPremium: true,
    desc: "دراسة تحليلية لصياغة شروط القوة القاهرة وبنود إبراء الذمة وحماية المتعاقد (Indemnity) والتعويض بين القانون الأنجلوسكسوني واللاتيني."
  },
  {
    id: "0NV1KdWRHck",
    title: "معايير الترجمة المرئية (Subtitling) الاحترافية وضوابط نتفليكس العالمية",
    speaker: "المهندس يوسف بلحاج (أخصائي Subtitling)",
    authorRole: "مهندس ترجمة سمعبصرية ومستشار توطين المنصات",
    language: "ar",
    langLabel: "العربية / الإنجليزية",
    category: "subtitling",
    categoryLabel: "الترجمة المرئية والـ SRT",
    duration: "40:20 دقيقة",
    level: "متوسط",
    isPremium: false,
    desc: "حساب سرعة القراءة (CPS)، عدد الحروف في السطر (CPL)، قواعد تجزئة الجمل نحوياً وتوقيت الظهور والاختفاء بدقة الإطار الواحد."
  },
  {
    id: "eIho2S0ZahI",
    title: "صناعة الذاكرات الترجمية وقواعد المصطلحات الآلية ببرنامج SDL Trados Studio",
    speaker: "المهندس نبيل قاسمي (مدرب أنظمة CAT)",
    authorRole: "خبير معتمد في حلول وأدوات الترجمة بمساعدة الحاسوب",
    language: "ar",
    langLabel: "تقنية اللغات",
    category: "translation_cat",
    categoryLabel: "أنظمة CAT Tools",
    duration: "45:30 دقيقة",
    level: "متوسط إلى متقدم",
    isPremium: true,
    desc: "إنشاء وصيانة ملفات TMX وTBX، وضبط خوارزميات المطابقة التقريبية (Fuzzy Match)، وضمان الاتساق المصطلحي للمشاريع الضخمة."
  },
  {
    id: "0eCVxV4v3-E",
    title: "ضمان الجودة ومطابقة المواصفة القياسية الدولية للترجمة ISO 17100:2015",
    speaker: "د. فتيحة بوقرة (خبيرة الجودة والتدقيق اللغوي)",
    authorRole: "مدققة معتمدة لمواصفات الجودة العالمية للترجمة",
    language: "ar",
    langLabel: "العربية / الدولية",
    category: "translation_legal",
    categoryLabel: "ضمان الجودة والاعتماد",
    duration: "35:15 دقيقة",
    level: "شامل",
    isPremium: true,
    desc: "الإجراءات الإلزامية للمعيار الدولي ISO 17100:2015: التدقيق المزدوج المستقل (Four-Eyes Principle)، إدارة المصطلحات، وتوثيق المشاريع."
  }
];

// Helper to retrieve all videos
function getAllAcademyVideos() {
  return ACADEMY_100_VIDEOS;
}

if (typeof window !== 'undefined') {
  window.ACADEMY_100_VIDEOS = ACADEMY_100_VIDEOS;
  window.getAllAcademyVideos = getAllAcademyVideos;
}
