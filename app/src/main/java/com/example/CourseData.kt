package com.example

object CourseData {
    val sampleCourses = listOf(
        MasterclassCourse(
            id = 1,
            title = "محاضرات في علم الترجمة: قضايا ومقاربات (PDF)",
            category = "محاضرات أكاديمية (PDF)",
            duration = "وثيقة أكاديمية شاملة",
            instructor = "",
            level = "جامعي وتخصصي",
            desc = "محاضرات أكاديمية موثقة في علم الترجمة تتناول القضايا والمقاربات النظرية والتطبيقية، ومناهج التحليل والترجمة التخصصية لطلبة الدراسات العليا والمترجمين.",
            videoUrl = "https://fac.umc.edu.dz/fll/images/cours/CHENI%20FAIROUZ.pdf"
        ),
        MasterclassCourse(
            id = 2,
            title = "مدخل إلى علم الترجمة (ملف على Scribd)",
            category = "دراسات ومراجع الترجمة",
            duration = "مرجع دراسي تأصيلي",
            instructor = "",
            level = "تأسيسي وأكاديمي",
            desc = "مرجع علمي تأسيسي يقدم مدخلاً مفصلاً لأسس ونظريات علم الترجمة والمدارس اللسانية والترجمية وتطبيقاتها العلمية في نقل النصوص.",
            videoUrl = "https://www.scribd.com/document/600337087/مدخل-إلى-علم-الترجمة"
        ),
        MasterclassCourse(
            id = 3,
            title = "الدرس الأول من سلسلة “أساسيات الترجمة”",
            category = "سلاسل دروس الترجمة",
            duration = "درس تأسيسي تفاعلي",
            instructor = "",
            level = "مبتدئ إلى متوسط",
            desc = "درس تطبيقي منهجي يشرح الركائز الأساسية لنقل المعنى وتفادي الترجمة الحرفية وضبط التراكيب النحوية والأسلوبية بين اللغات.",
            videoUrl = "https://www.facebook.com/kogee.elrishi/posts/8070308722982424"
        ),
        MasterclassCourse(
            id = 4,
            title = "عرض تقديمي PowerPoint: مدخل إلى علم الترجمة (جامعة الملك سعود)",
            category = "عروض تقديمية جامعية (PPTX)",
            duration = "شرائح عرض أكاديمية",
            instructor = "",
            level = "جامعي معتمد",
            desc = "عرض تقديمي تفصيلي صادر عن كلية اللغات والترجمة بجامعة الملك سعود يوضح مفاهيم علم الترجمة، أدوات المترجم، وأهم استراتيجيات المعالجة اللغوية.",
            videoUrl = "https://fac.ksu.edu.sa/sites/default/files/_1.pptx"
        ),
        MasterclassCourse(
            id = 5,
            title = "دورات ترجمة مجانية عبر منصة Coursera",
            category = "دورات عالمية عبر الإنترنت",
            duration = "مساقات تدريبية متعددة",
            instructor = "",
            level = "متعدد المستويات",
            desc = "باقة منتقاة من المساقات والورش التدريبية المتخصصة في الترجمة واللغات من جامعات دولية رائدة عبر منصة كورسيرا العالمية مع شهادات إتمام.",
            videoUrl = "https://www.coursera.org/courses?query=translation"
        ),
        MasterclassCourse(
            id = 6,
            title = "شهادة الترجمة والوساطة اللغوية – جامعة ماساتشوستس Amherst",
            category = "شهادات جامعية دولية",
            duration = "برنامج شهادة مهنية",
            instructor = "",
            level = "شهادة مهنية احترافية",
            desc = "برنامج أكاديمي ومهني معتمد عبر الإنترنت في الترجمة التحريرية والتفسير الشفوي والوساطة اللغوية من كلية الآداب بجامعة ماساتشوستس أمهرست.",
            videoUrl = "https://www.umass.edu/languages-literatures-cultures/academics/online-certificate-professional-translation-interpreting"
        ),
        MasterclassCourse(
            id = 7,
            title = "ماجستير في الترجمة والتفسير – جامعة نيويورك (NYU SPS)",
            category = "دراسات عليا وماجستير",
            duration = "درجة الماجستير المهني (MS)",
            instructor = "",
            level = "دراسات عليا متقدمة",
            desc = "برنامج درجة الماجستير في علوم الترجمة التحريرية والفورية من كلية الدراسات المهنية بجامعة نيويورك، يؤهل للعمل الدبلوماسي والمؤسسات الدولية.",
            videoUrl = "https://www.sps.nyu.edu/homepage/academics/masters-degrees/ms-in-translation.html"
        ),
        MasterclassCourse(
            id = 8,
            title = "شهادة دراسات الترجمة – كلية Hunter (CUNY)",
            category = "شهادات جامعية دولية",
            duration = "برنامج دبلوم تطبيقي",
            instructor = "",
            level = "دبلوم مهني متقدم",
            desc = "برنامج شهادة دراسات الترجمة من التعليم المستمر بكلية هانتر في جامعة مدينة نيويورك، يركز على مهارات الصياغة التحريرية والمعايير الاحترافية.",
            videoUrl = "https://continuing-ed.hunter.cuny.edu/certificate-in-translation-studies/"
        ),
        MasterclassCourse(
            id = 9,
            title = "منصة TranslaStars – دورات احترافية في الترجمة أونلاين",
            category = "منصات تدريب احترافية",
            duration = "دورات تدريبية متخصصة",
            instructor = "",
            level = "مهني تطبيقي",
            desc = "أكاديمية تدريب مهنية متخصصة تقدم دورات عملية للمترجمين في برامج الكات (CAT Tools) وترجمة الفيديو والدبلجة والترجمة الطبية والقانونية والتسويقية.",
            videoUrl = "https://www.translastars.com/"
        ),
        MasterclassCourse(
            id = 10,
            title = "الدرس التأسيسي في الألمانية: تراكيب الجملة والمحادثة اليومية (Deutsch lernen)",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "فيديو تدريبي تفاعلي",
            instructor = "",
            level = "A1 - A2 تأسيسي",
            desc = "شرح مبسط ومباشر لبناء الجملة الأساسية في اللغة الألمانية وتصريف الأفعال الشائعة مع تدريب على النطق ومخارج الحروف السليمة لمتعلمي اللغات.",
            videoUrl = "https://www.facebook.com/share/v/1BtueGUSXV/"
        ),
        MasterclassCourse(
            id = 11,
            title = "كبسولة المحادثة الألمانية السريعة: العبارات الحيوية والنطق الأصيل",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "مقطع تدريبي سريع (Reel)",
            instructor = "",
            level = "A1 للمبتدئين",
            desc = "تدريب مكثف على العبارات اليومية والمواقف الحياتية في الشارع والمطعم والمطار بألمانيا وكيفية الرد التلقائي دون تردد.",
            videoUrl = "https://www.facebook.com/share/r/1DL41mc34o/"
        ),
        MasterclassCourse(
            id = 12,
            title = "قواعد الألمانية ببساطة: ضبط أدوات التعريف وتراكيب الـ Dativ والـ Akkusativ",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "درس قواعد مركز (Reel)",
            instructor = "",
            level = "A2 - B1 متوسط",
            desc = "تفكيك عقدة أدوات التعريف (der, die, das) وحالات الإعراب الألمانية (Nominativ, Akkusativ, Dativ) عبر أمثلة عملية سريعة الحفظ والتطبيق.",
            videoUrl = "https://www.facebook.com/share/r/14ux22YaTvK/"
        ),
        MasterclassCourse(
            id = 13,
            title = "المصطلحات الألمانية للمترجمين ومتعلمي اللغات: الفروق الدلالية الدقيقة",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "مقطع دلالي ولساني (Reel)",
            instructor = "",
            level = "B1 - B2 متقدم",
            desc = "التمييز بين الأفعال المركبة الألمانية واللواحق والتعبيرات الاصطلاحية وتفادي أخطاء الترجمة الحرفية بين العربية والألمانية.",
            videoUrl = "https://www.facebook.com/share/r/1Ho4u33jNk/"
        ),
        MasterclassCourse(
            id = 14,
            title = "مهارة الاستماع والاستيعاب الشفهي في الألمانية (Hörverstehen Mastery)",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "تمرين استماع تطبيقي (Reel)",
            instructor = "",
            level = "A2 - B1 متوسط",
            desc = "تقنيات تدريب الأذن على النبرة الألمانية وسرعة المتحدثين الأصليين واستخراج المعلومات الأساسية من الحوارات الشفهية لاجتياز اختبارات Goethe وTelc.",
            videoUrl = "https://www.facebook.com/share/r/1CEm7LEw8Y/"
        ),
        MasterclassCourse(
            id = 15,
            title = "محادثات العمل والمكاتب والمراسلات الرسمية بالألمانية (Geschäftsdeutsch)",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "مقطع عملي ومهني (Reel)",
            instructor = "",
            level = "B1 - B2 للمحترفين",
            desc = "صيغ الاحترام والمخاطبة الرسمية في بيئة العمل الألمانية، وكتابة الرسائل الإدارية، وإجراء المقابلات الشفهية والاجتماعات بنجاح.",
            videoUrl = "https://www.facebook.com/share/r/1F6uREvqc5/"
        ),
        MasterclassCourse(
            id = 16,
            title = "محاضرة الألمانية الشاملة: أسرار الطلاقة وتجاوز حواجز التحدث (Fließend Deutsch)",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "محاضرة تفاعلية كاملة",
            instructor = "",
            level = "A1 إلى B2 شامل",
            desc = "محاضرة توجيهية شاملة تشرح استراتيجيات بناء الحصيلة اللغوية، وتنظيم وقت المذاكرة اليومي، وتطبيق أسلوب التظليل الصوتي (Shadowing) في إتقان اللغة الألمانية.",
            videoUrl = "https://www.facebook.com/share/v/1Hpwcf1i38/"
        ),
        MasterclassCourse(
            id = 17,
            title = "خلاصة القواعد والنطق السليم في الألمانية: تجنب الأفخاخ الشائعة",
            category = "تعلم اللغة الألمانية 🇩🇪",
            duration = "ملخص تطبيقي مركز (Reel)",
            instructor = "",
            level = "A2 - B1 شامل",
            desc = "دليل سريع لتصحيح الأخطاء الشائعة بين المبتدئين في مخارج حروف (ch, st, sp, ä, ö, ü) وتثبيت صيغ الماضي (Perfekt و Präteritum) بثقة واحتراف.",
            videoUrl = "https://www.facebook.com/share/r/14uNxRvyKVQ/"
        )
    )
}
