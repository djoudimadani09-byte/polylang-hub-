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
        )
    )
}
