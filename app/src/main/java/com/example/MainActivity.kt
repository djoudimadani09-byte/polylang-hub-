    val allCourses = remember { CourseData.sampleCourses }
    val orders = remember {
        mutableStateListOf(
            TranslationOrder(
                id = "PL-8421",
                title = "عقد امتياز واستثمار دولي",
                category = "قانونية ورسمية",
                langPair = "الإنجليزية ⇄ العربية",
                wordCount = 2400,
                priceDzd = 8400,
                status = "قيد التدقيق المحلف",
                isInterpretation = false,
                clientName = "كريم حمداوي",
                clientEmail = "client@polylang.dz",
                clientPhone = "+213 555 12 34 56",
                attachedFileName = "Concession_Agreement_2026.pdf",
                date = "2026-10-11",
                notes = "عقد امتياز استثماري دولي مع شركة سوناطراك وشركاء أجانب."
            ),
            TranslationOrder(
                id = "PL-9032",
                title = "كتيب مواصفات توربينات الغاز",
                category = "تقنية وهندسية",
                langPair = "الفرنسية ⇄ العربية",
                wordCount = 1850,
                priceDzd = 7030,
                status = "مكتمل وتم التسليم",
                isInterpretation = false,
                clientName = "شركة المقاولات والأشغال الكبرى",
                clientEmail = "projects@polylang.dz",
                clientPhone = "+213 550 44 33 22",
                attachedFileName = "Turbine_Specs_Rev3.pdf",
                date = "2026-10-09",
                notes = "مواصفات هندسية دقيقة."
            ),
            TranslationOrder(
                id = "PL-3114",
                title = "كابينة الترجمة لقمة الطاقة بالجزائر",
                category = "ترجمة فورية للمؤتمرات",
                langPair = "الإنجليزية ⇄ الفرنسية",
                wordCount = 0,
                priceDzd = 45000,
                status = "مؤكد ومجدول",
                isInterpretation = true,
                clientName = "وزارة الطاقة والمناجم",
                clientEmail = "energy@polylang.dz",
                clientPhone = "+213 21 66 77 88",
                attachedFileName = "Agenda_Summit_2026.pdf",
                date = "2026-10-08",
                notes = "توفير مترجمين فوريين معتمدين لكابينة القاعة الكبرى."
            )
        )
    }
