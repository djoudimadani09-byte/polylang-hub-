package com.example

object CourseData {
    val sampleCourses = listOf(
        // --- 1. لغات بالعربية (Languages in Arabic) ---
        MasterclassCourse(
            id = 1,
            title = "كورس شامل لتعلم قواعد اللغة الإنجليزية من الصفر للمبتدئين",
            category = "الإنجليزية بالعربية",
            instructor = "",
            duration = "48 دقيقة",
            level = "مبتدئ A1",
            desc = "شرح كامل وتفاعلي لقواعد اللغة الإنجليزية وتراكيب الجمل الشائعة مع أمثلة حية وتمارين نطق مصممة للناطقين بالعربية."
        ),
        MasterclassCourse(
            id = 2,
            title = "أهم 1000 كلمة في اللغة الإنجليزية واستخدامها في محادثات حية",
            category = "الإنجليزية بالعربية",
            instructor = "",
            duration = "65 دقيقة",
            level = "محادثة وتأسيس",
            desc = "مفردات المحادثة اليومية والعملية بالإنجليزية مع اللفظ السليم باللكنة الأمريكية وكيفية ربط الجمل دون تردد."
        ),
        MasterclassCourse(
            id = 3,
            title = "تعلم اللغة الفرنسية من الصفر: النطق السليم والحروف والمحادثة",
            category = "الفرنسية بالعربية",
            instructor = "",
            duration = "44 دقيقة",
            level = "مبتدئ A1",
            desc = "إتقان الأبجدية الفرنسية، الحروف الصوتية والأنفية المركبة، وتكوين أول حوار تعارف متكامل باللغة الفرنسية."
        ),
        MasterclassCourse(
            id = 4,
            title = "أهم 300 جملة وتعبير في اللغة الفرنسية للحياة اليومية والسفر",
            category = "الفرنسية بالعربية",
            instructor = "",
            duration = "52 دقيقة",
            level = "متوسط A2",
            desc = "تراكيب التحدث السريع في المقاهي والمطارات والمواقف اليومية بفرنسا مع النطق النموذجي والترجمة العربية."
        ),
        MasterclassCourse(
            id = 5,
            title = "دورة اللغة الإسبانية الكاملة للمبتدئين بالعربية من الصفر",
            category = "الإسبانية بالعربية",
            instructor = "",
            duration = "58 دقيقة",
            level = "مبتدئ A1",
            desc = "مدخل شامل للأبجدية الإسبانية، التحيات، أفعال الكينونة Ser و Estar، وبناء جمل المحادثة اليومية في إسبانيا وأمريكا اللاتينية."
        ),
        MasterclassCourse(
            id = 6,
            title = "تعلم اللغة الألمانية بالعربية: نطق الحروف وتركيب الجملة الألمانية",
            category = "الألمانية بالعربية",
            instructor = "",
            duration = "50 دقيقة",
            level = "مبتدئ A1",
            desc = "مدخل ميسر لفهم تراكيب الجملة الألمانية، أدوات التعريف (der, die, das) وقواعد النطق الصوتي الصحيح مع أمثلة تطبيقية."
        ),
        MasterclassCourse(
            id = 7,
            title = "الحالات الإعرابية الألمانية (Nominativ, Akkusativ, Dativ)",
            category = "الألمانية بالعربية",
            instructor = "",
            duration = "46 دقيقة",
            level = "متوسط B1",
            desc = "تفكيك عقدة الإعراب الألماني وجداول الأدوات والضمائر بأمثلة مقارنة باللغة العربية لتسهيل الفهم والترجمة."
        ),
        MasterclassCourse(
            id = 8,
            title = "تعلم اللغة الإيطالية من الصفر: التحيات والحوارات اليومية",
            category = "الإيطالية بالعربية",
            instructor = "",
            duration = "36 دقيقة",
            level = "مبتدئ A1",
            desc = "التعرف على النغمات الموسيقية للحروف الإيطالية، المفردات اليومية الأساسية، وتصريف الأفعال المنتظمة في المحادثة."
        ),
        MasterclassCourse(
            id = 9,
            title = "تعلم اللغة التركية بالعربية: التوافق الصوتي وبناء الجمل باللواحق",
            category = "التركية بالعربية",
            instructor = "",
            duration = "42 دقيقة",
            level = "مبتدئ ومتوسط",
            desc = "أسرار اللواحق في اللغة التركية وقاعدة التوافق الصوتي الثنائي والرباعي لتكوين جمل متناسقة وسلسة في الحياة اليومية."
        ),
        MasterclassCourse(
            id = 10,
            title = "أساسيات اللغة الروسية: قراءة الحروف السيريلية والمفردات التأسيسية",
            category = "الروسية بالعربية",
            instructor = "",
            duration = "41 دقيقة",
            level = "مبتدئ A1",
            desc = "إتقان الأبجدية السيريلية بالصوت والصورة، نبر الكلمات الروسية، وأهم عبارات التعارف والترحيب الروسية."
        ),

        // --- 2. لغات أجنبية مع ناطقين أصليين (Native Foreign Languages) ---
        MasterclassCourse(
            id = 11,
            title = "Advanced English Vocabulary & How to Sound Like a Native Speaker",
            category = "English Native",
            instructor = "",
            duration = "26 دقيقة",
            level = "متقدم C1-C2",
            desc = "Master high-level English idioms, natural expressions, and polite British conversational phrases used in professional environments."
        ),
        MasterclassCourse(
            id = 12,
            title = "Real Life English: Fast Spoken Speech & Connected Pronunciation",
            category = "English Native",
            instructor = "",
            duration = "22 دقيقة",
            level = "متوسط B2",
            desc = "Learn why native speakers sound so fast, how words link together naturally, and how to train your ear for spontaneous conversations."
        ),
        MasterclassCourse(
            id = 13,
            title = "Dialogues en Français Réel: Écoute Active et Vocabulaire du Quotidien",
            category = "Français Natif",
            instructor = "",
            duration = "30 دقيقة",
            level = "متوسط B1-B2",
            desc = "Immersion complète en français parlé avec sous-titres, tournures familières et amélioration de la compréhension orale."
        ),
        MasterclassCourse(
            id = 14,
            title = "Español Real para Extranjeros: Conversaciones Cotidianas",
            category = "Español Nativo",
            instructor = "",
            duration = "25 دقيقة",
            level = "متوسط B1",
            desc = "Aprende cómo hablan los hispanohablantes en situaciones reales, modismos habituales y trucos para sonar con total naturalidad."
        ),
        MasterclassCourse(
            id = 15,
            title = "Easy German: Street Interviews in Berlin with Dual Subtitles",
            category = "Deutsch Muttersprachler",
            instructor = "",
            duration = "21 دقيقة",
            level = "A2-B2 Deutsch",
            desc = "Authentic German language learning from the streets of Berlin with dual German/English transcripts for natural listening comprehension."
        ),

        // --- 3. فنون وعلوم الترجمة المعتمدة (Translation Masterclasses) ---
        MasterclassCourse(
            id = 16,
            title = "أسرار الترجمة الفورية والتحكم في الـ Décalage بكابينات المؤتمرات",
            category = "فورية ودبلوماسية",
            instructor = "",
            duration = "60 دقيقة",
            level = "احترافي خبير",
            desc = "تقنيات التحكم في الفارق الزمني (Décalage) بين الاستماع والتحدث في كابينة المؤتمرات الدولية دون إجهاد ذهني وضمان دقة نقل المعنى."
        ),
        MasterclassCourse(
            id = 17,
            title = "القواعد السبعة لنظام روزان لتدوين الملاحظات في الترجمة التتابعية",
            category = "فورية ودبلوماسية",
            instructor = "",
            duration = "50 دقيقة",
            level = "متوسط إلى متقدم",
            desc = "التطبيق العملي للقواعد السبعة لنظام جان فرانسوا روزان (Rozan 7 Rules)، الرموز البصرية للروابط المنطقية، والتسلسل الرأسي للملاحظات."
        ),
        MasterclassCourse(
            id = 18,
            title = "الترجمة القانونية وصياغة العقود التجارية الدولية المقارنة",
            category = "عقود وقانون",
            instructor = "",
            duration = "45 دقيقة",
            level = "متقدم C1",
            desc = "دراسة تحليلية لصياغة شروط القوة القاهرة (Force Majeure) وبنود إبراء الذمة والتعويض (Indemnity) في العقود الإنجليزية المعتمدة."
        ),
        MasterclassCourse(
            id = 19,
            title = "معايير الترجمة المرئية (SRT) الاحترافية وضوابط نتفليكس العالمية",
            category = "ترجمة مرئية",
            instructor = "",
            duration = "40 دقيقة",
            level = "متوسط",
            desc = "حساب سرعة القراءة (CPS)، عدد الحروف في السطر (CPL)، قواعد تجزئة الجمل نحوياً وتوقيت الظهور والاختفاء بدقة الإطار الواحد."
        ),
        MasterclassCourse(
            id = 20,
            title = "صناعة الذاكرات الترجمية وقواعد المصطلحات الآلية ببرنامج Trados",
            category = "تقنية وأنظمة CAT",
            instructor = "",
            duration = "45 دقيقة",
            level = "متوسط إلى متقدم",
            desc = "إنشاء وصيانة ملفات TMX وTBX، وضبط خوارزميات المطابقة التقريبية (Fuzzy Match)، وضمان الاتساق المصطلحي للمشاريع الضخمة."
        ),
        MasterclassCourse(
            id = 21,
            title = "ضمان الجودة ومطابقة المواصفة القياسية الدولية للترجمة ISO 17100:2015",
            category = "ضمان الجودة",
            instructor = "",
            duration = "35 دقيقة",
            level = "شامل",
            desc = "الإجراءات الإلزامية للمعيار الدولي ISO 17100:2015: التدقيق المستقل المزدوج (Four-Eyes Principle)، إدارة المصطلحات، وتوثيق المشاريع."
        )
    )
}
