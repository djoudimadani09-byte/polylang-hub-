package com.example

import com.example.ui.screens.ExamQuestion

object PlacementExamBanks {

    // COMPREHENSIVE TRACK (100 QUESTIONS)
    val comprehensiveQuestions = listOf(
        ExamQuestion(
            id = 1,
            questionAr = "ما هي الترجمة المؤسساتية المعتمدة لمصطلح 'Plenipotentiary' في المعاهدات والاتفاقيات الدولية؟",
            questionEn = "المعجم الدبلوماسي والدولي",
            optionsAr = listOf("مفوض فوق العادة ومطلق الصلاحية", "وزير مفوض مؤقت", "مبعوث استطلاعي خاص", "ملحق ثقافي معتمد"),
            correctIndex = 0,
            explanationAr = "اللقب الدبلوماسي الرسمي 'Plenipotentiary' يترجم حصراً بـ 'مفوض فوق العادة ومطلق الصلاحية'."
        ),
        ExamQuestion(
            id = 2,
            questionAr = "في العقود التجارية الدولية، ما الفرق الجوهري الدقيق بين 'Force Majeure' و 'Hardship'؟",
            questionEn = "الصياغة القانونية والعقود",
            optionsAr = listOf("Force Majeure تجعل التنفيذ مستحيلاً تماماً، بينما Hardship تجعله مرهقاً ومختلاً اقتصادياً بصورة غير متوقعة", "Hardship تطبق فقط في النزاعات الجنائية", "كلاهما يعني دفع غرامة تأخير بدون إعفاء", "Force Majeure تشترط خطأ أحد الأطراف"),
            correctIndex = 0,
            explanationAr = "القوة القاهرة تعدم إمكانية التنفيذ، بينما الظروف الطارئة (Hardship) تخل بالتوازن الاقتصادي دون استحالته."
        ),
        ExamQuestion(
            id = 3,
            questionAr = "ما المقابل العربي الأدق للعبارة القانونية اللاتينية 'Mutatis Mutandis' في نصوص المراسيم والاتفاقيات؟",
            questionEn = "المصطلحات القانونية اللاتينية",
            optionsAr = listOf("مع مراعاة التعديلات اللازمة / مع ما يقتضيه الفارق", "إلى الأبد وبدون قيد أو شرط", "بحسن نية مطلقة", "بأثر رجعي فوري"),
            correctIndex = 0,
            explanationAr = "العبارة اللاتينية تعني تطبيق نفس الأحكام مع إجراء التغييرات الطفيفة اللازمة لاختلاف السياق."
        ),
        ExamQuestion(
            id = 4,
            questionAr = "ما هو المدلول القانوني الدقيق لمصطلح 'Ultra Vires' في ممارسات الهيئات الإدارية؟",
            questionEn = "القانون الإداري والمؤسساتي",
            optionsAr = listOf("تجاوز حدود الاختصاص والسلطة المخولة قانوناً (مما يبطل التصرف)", "التصرف بحسن نية كاملة", "تفويض الصلاحيات لموظف أدنى", "المصادقة الإجماعية على القرار"),
            correctIndex = 0,
            explanationAr = "التصرف 'Ultra Vires' هو عمل يقع خارج النطاق القانوني أو الدستوري الممنوح للمؤسسة ويعد باطلاً."
        ),
        ExamQuestion(
            id = 5,
            questionAr = "ما المقابل العربي الرسمي لمصطلح 'Letters of Credence' في التمثيل الخارجي؟",
            questionEn = "البروتوكول الدبلوماسي",
            optionsAr = listOf("أوراق الاعتماد", "رسائل التوصية", "جوازات السفر الدبلوماسية", "وثائق المعاهدات"),
            correctIndex = 0,
            explanationAr = "أوراق الاعتماد هي الوثيقة الرسمية التي يقدمها السفير الجديد لرئيس الدولة المعتمد لديها."
        ),
        ExamQuestion(
            id = 6,
            questionAr = "ما الفرق في الصياغة القانونية الإنجليزية بين 'Shall' و 'May'؟",
            questionEn = "الصياغة التشريعية الإنجليزية",
            optionsAr = listOf("Shall تفيد الوجوب والإلزام الصارم، بينما May تفيد الجواز والإذن التقديري", "Shall تفيد المستقبل البسيط فقط", "كلاهما متطابق في القوة الإلزامية", "May تستخدم فقط للأفراد و Shall للدول"),
            correctIndex = 0,
            explanationAr = "في العقود الدولية: Shall تفرض التزاماً قانونياً واجباً، و May تمنح خياراً أو رخصة جوازية."
        ),
        ExamQuestion(
            id = 7,
            questionAr = "ما هي الترجمة القانونية الصحيحة لمصطلح 'Without Prejudice' في المراسلات القضائية والتسويات؟",
            questionEn = "المصطلحات القانونية",
            optionsAr = listOf("مع عدم الإخلال بالحقوق / دون مساس بالدعوى الأصلية", "بدون أي تحيز عرقي", "بحكم قضائي بات", "وفقاً للعرف السائد"),
            correctIndex = 0,
            explanationAr = "عبارة 'دون إخلال' تحمي حقوق الطرف المفاوض وتمنع استخدام التنازلات المقترحة كإقرار إدانة ضده أمام المحكمة."
        ),
        ExamQuestion(
            id = 8,
            questionAr = "ما معنى مصطلح 'Persona non grata' في العلاقات الدبلوماسية الدولية؟",
            questionEn = "الأعراف الدبلوماسية",
            optionsAr = listOf("شخص غير مرغوب فيه (يطلب مغادرته لأراضي الدولة المعتمد لديها)", "دبلوماسي ذو حصانة مطلقة", "مترجم كابينة معتمد", "رئيس بعثة استثنائي"),
            correctIndex = 0,
            explanationAr = "إجراء بموجب اتفاقية فيينا للعلاقات الدبلوماسية تطلب بموجبه الدولة المستقبلة طرد الدبلوماسي دون إبداء الأسباب."
        ),
        ExamQuestion(
            id = 9,
            questionAr = "ما المقابل الدقيق لمصطلح 'Ipso Facto' في القرارات التعاقدية؟",
            questionEn = "المصطلحات اللاتينية",
            optionsAr = listOf("بحكم الواقع ذاته / تلقائياً بمجرد وقوع الفعل", "بأثر رجعي مدته سنة", "وفقاً لرغبة الطرفين", "بعد موافقة المحكمة"),
            correctIndex = 0,
            explanationAr = "العبارة تعني حدوث النتيجة القانونية تلقائياً بقوة الواقعة نفسها دون حاجة لإجراء إضافي."
        ),
        ExamQuestion(
            id = 10,
            questionAr = "في المعاهدات الدولية، ما الفرق بين 'Ratification' و 'Accession'؟",
            questionEn = "قانون المعاهدات الدولية",
            optionsAr = listOf("Ratification مصادقة دولة موقعة سابقاً، بينما Accession انضمام دولة لم تكن موقعة أصلاً على نص المعاهدة", "كلاهما يعني رفض المعاهدة", "Ratification للتحالفات العسكرية فقط", "لا يوجد أي فرق"),
            correctIndex = 0,
            explanationAr = "المصادقة تكون لمن وقع النص خلال مهلة التوقيع، بينما الانضمام لدولة لم توقع مسبقاً وتلتحق بالمعاهدة لاحقاً."
        ),
        ExamQuestion(
            id = 11,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Jus Cogens' في القانون الدولي العام؟",
            questionEn = "القواعد الآمرة",
            optionsAr = listOf("القواعد الآمرة في القانون الدولي (لا يجوز الاتفاق على مخالفتها)", "القوانين الوطنية المؤقتة", "الأعراف التجارية المحلية", "أحكام المحاكم الابتدائية"),
            correctIndex = 0,
            explanationAr = "Jus Cogens هي القواعد الدولية الآمرة القطعية (مثل حظر الإبادة الجماعية والقرصنة والرق)."
        ),
        ExamQuestion(
            id = 12,
            questionAr = "ما هي الترجمة القانونية الصحيحة لمصطلح 'Liquidated Damages' في عقود الفيدك (FIDIC)؟",
            questionEn = "عقود الإنشاءات والمقاولات",
            optionsAr = listOf("التعويضات الاتفاقية المحددة مسبقاً / الشرط الجزائي المتفق عليه", "تصفية الأصول المفلسة", "أضرار بيئية غير مباشرة", "تكاليف الشحن البحري"),
            correctIndex = 0,
            explanationAr = "التعويض المقدر سلفاً في العقد عن كل يوم أو أسبوع تأخير في إنجاز المشروع دون حاجة لإثبات الضرر."
        ),
        ExamQuestion(
            id = 13,
            questionAr = "ما معنى عبارة 'Ad Referendum' عند توقيع المفاوض على مسودة اتفاقية؟",
            questionEn = "إجراءات التوقيع الدولي",
            optionsAr = listOf("توقيع مشروط بمراجعة السلطة العليا المختصة وإقرارها", "توقيع نهائي غير قابل للطعن", "توقيع بالنيابة عن دولة ثالثة", "توقيع سري غير معلن"),
            correctIndex = 0,
            explanationAr = "التوقيع بشرط الرجوع للسلطة السياسية للتثبت من عدم تجاوز صلاحيات الوفد المفاوض."
        ),
        ExamQuestion(
            id = 14,
            questionAr = "ما هو المقابل الاصطلاحي العربي لمصطلح 'De Facto' مقابل 'De Jure'؟",
            questionEn = "المصطلحات المؤسساتية",
            optionsAr = listOf("بحكم الأمر الواقع مقابل بحكم القانون والشرعية الرسمية", "سري مقابل علني", "مؤقت مقابل دائم", "محلي مقابل دولي"),
            correctIndex = 0,
            explanationAr = "De Facto هو الواقع الفعلي المفروض، بينما De Jure هو الوضع الشرعي المعترف به قانوناً."
        ),
        ExamQuestion(
            id = 15,
            questionAr = "ما الترجمة الدقيقة لمصطلح 'Joint and Several Liability' في المعاملات المالية؟",
            questionEn = "المسؤولية القانونية",
            optionsAr = listOf("المسؤولية التضامنية والتكافلية (كل طرف مسؤول عن الكل وعن نصيبه)", "المسؤولية المحدودة بالأسهم فقط", "المسؤولية الجزائية المشروطة", "انعدام المسؤولية المالية"),
            correctIndex = 0,
            explanationAr = "تضامن المدينين بحيث يحق للدائن مطالبة أي طرف بكامل الدين أو مطالبة الجميع مجتمعين."
        ),
        ExamQuestion(
            id = 16,
            questionAr = "ما المقصود بـ 'Expressed Warranty' في عقود البيع التجاري الدولي؟",
            questionEn = "الضمانات العقدية",
            optionsAr = listOf("الضمان الصريح المنصوص عليه بوضوح في بنود العقد", "الضمان الضمني المفترض عرفاً", "الضمان الشفهي غير المسجل", "الضمان المنتهي الصلاحية"),
            correctIndex = 0,
            explanationAr = "الضمان الصريح هو التعهد التعاقدي المكتوب بوضوح خلافاً للضمان الضمني (Implied Warranty)."
        ),
        ExamQuestion(
            id = 17,
            questionAr = "ما المعنى الدقيق لعبارة 'Inter Alia' في التقارير الدولية؟",
            questionEn = "اللاتينية المؤسساتية",
            optionsAr = listOf("من بين أمور أخرى / من جملتها (دون حصر)", "في غياب الأطراف", "بناءً على طلب الرئيس", "في نهاية الجلسة"),
            correctIndex = 0,
            explanationAr = "عبارة تفيد التمثيل لا الحصر، أي أن القائمة المذكورة ليست على سبيل الإحاطة التامة."
        ),
        ExamQuestion(
            id = 18,
            questionAr = "ما المقابل العربي لمصطلح 'Indemnification Clause' في عقود الاستثمار؟",
            questionEn = "شروط التعويض",
            optionsAr = listOf("بند التعويض ودرء الضرر / بند جبر الخسائر", "بند الفائدة المصرفية", "بند تسليم البضائع", "بند الإعفاء الضريبي"),
            correctIndex = 0,
            explanationAr = "البند الذي يلزم أحد الطرفين بتعويض الآخر والدفاع عنه ضد أي مطالبات أو خسائر ناتجة عن خرق العقد."
        ),
        ExamQuestion(
            id = 19,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Arbitration Clause' في النزاعات العابرة للحدود؟",
            questionEn = "التحكيم الدولي",
            optionsAr = listOf("شرط التحكيم (إحالة أي نزاع لمحكمين بدلاً من القضاء الوطني)", "شرط الوساطة الودية فقط", "شرط الصلح الجنائي", "شرط مراجعة الأسعار"),
            correctIndex = 0,
            explanationAr = "بند بموجبه يتفق المتعاقدون على حل الخلافات عبر هيئات التحكيم الدولية (مثل ICC أو LCIA)."
        ),
        ExamQuestion(
            id = 20,
            questionAr = "ما المدلول الدقيق لـ 'Ex Gratia Payment' في التعويضات المالية؟",
            questionEn = "التعويضات التقديرية",
            optionsAr = listOf("مبلغ مدفوع طواعية وتفضلاً دون إقرار بأي مسؤولية قانونية", "مبلغ محكوم به قضائياً", "قرض مصرفي إجباري", "غرامة تهرب جمركي"),
            correctIndex = 0,
            explanationAr = "دفعة تسوية تُمنح تفضلاً من الدولة أو الشركة لجبر خواطر المتضررين دون الاعتراف بالذنب قانوناً."
        ),
        ExamQuestion(
            id = 21,
            questionAr = "ما المقابل الرسمي لمصطلح 'Procès-Verbal' في الاجتماعات والتحقيقات الرسمية؟",
            questionEn = "المصطلحات التوثيقية",
            optionsAr = listOf("محضر اجتماع رسمي / محضر ضبط قضائي", "تقرير مالي ختامي", "دعوة لحضور مؤتمر", "جدول الأعمال المقترح"),
            correctIndex = 0,
            explanationAr = "الوثيقة الإجرائية الموثقة لوقائع جلسة رسمية أو ضبط مخالفة قانونية."
        ),
        ExamQuestion(
            id = 22,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Sovereign Immunity' في القانون الدولي؟",
            questionEn = "الحصانة الدولية",
            optionsAr = listOf("الحصانة السيادية للدول (عدم خضوع الدولة وقضائها لمحاكم دولة أخرى)", "الحصانة الجمركية للركاب", "حق الفيتو في مجلس الأمن", "الحق في إعلان الحرب"),
            correctIndex = 0,
            explanationAr = "المبدأ الذي يمنع مقاضاة دولة ذات سيادة أو مصادرة أصولها في المحاكم المحلية لدولة أجنبية."
        ),
        ExamQuestion(
            id = 23,
            questionAr = "ما المعنى الدقيق لمصطلح 'Bilateral Agreement'؟",
            questionEn = "أنواع الاتفاقيات",
            optionsAr = listOf("اتفاقية ثنائية (بين طرفين أو دولتين فقط)", "اتفاقية ثلاثية الأطراف", "معاهدة دولية مفتوحة لجميع الأمم", "عقد عمل فردي"),
            correctIndex = 0,
            explanationAr = "الاتفاق الثنائي المبرم بين كيانين أو دولتين متعاقدتين فقط."
        ),
        ExamQuestion(
            id = 24,
            questionAr = "ما معنى مصطلح 'Subpoena' في النظام القضائي الأنجلو-أمريكي (Common Law)؟",
            questionEn = "الإجراءات القضائية",
            optionsAr = listOf("أمر استدعاء قضائي ملزم للمثول أو لتقديم مستندات تحت طائلة العقوبة", "حكم براءة نهائي", "عقد كفالة مالية", "مذكرة دفاع استئنافية"),
            correctIndex = 0,
            explanationAr = "أمر تصدره المحكمة يلزم الشخص بالشهادة أو إبراز مستندات تحت طائلة تهمة ازدراء المحكمة."
        ),
        ExamQuestion(
            id = 25,
            questionAr = "ما المقابل العربي الدقيق لعبارة 'In Witness Whereof' التي تسبق تواقيع العقود؟",
            questionEn = "خواتيم العقود القانونية",
            optionsAr = listOf("وإثباتاً لما تقدم / وإشهاداً على ما تقدم (وقع الطرفان)", "بناءً على طلب القاضي", "مع فائق التقدير والاحترام", "حسب الشريعة والقانون"),
            correctIndex = 0,
            explanationAr = "الصيغة البروتوكولية الختامية المعتمدة تاريخياً قبل تواقيع الأطراف والشهود في العقود والاتفاقيات."
        ),
        ExamQuestion(
            id = 26,
            questionAr = "ما الأداة الصحيحة للاسم المذكر في حالة المفعول به (Akkusativ)؟ 'Ich kaufe ___ Tisch.'",
            questionEn = "أدوات التعريف والإعراب",
            optionsAr = listOf("den", "der", "das", "dem"),
            correctIndex = 0,
            explanationAr = "الاسم Tisch مذكر (der Tisch)، وفي حالة النصب (Akkusativ) تتحول der إلى den."
        ),
        ExamQuestion(
            id = 27,
            questionAr = "اختر التصريف الصحيح للفعل 'haben' مع الضمير 'ihr':",
            questionEn = "تصريف الأفعال الأساسية",
            optionsAr = listOf("habt", "haben", "hast", "hat"),
            correctIndex = 0,
            explanationAr = "تصريف فعل haben مع ضمير الجمع المخاطب ihr هو habt."
        ),
        ExamQuestion(
            id = 28,
            questionAr = "ما هو حرف الجر المناسب للوقت: 'Der Unterricht beginnt ___ 9:00 Uhr.'؟",
            questionEn = "حروف الجر الزمنية",
            optionsAr = listOf("um", "am", "im", "an"),
            correctIndex = 0,
            explanationAr = "مع الساعات المحددة نستخدم دائماً حرف الجر um (um 9 Uhr)."
        ),
        ExamQuestion(
            id = 29,
            questionAr = "ما هو حرف الجر المناسب للأيام: 'Ich habe einen Termin ___ Montag.'؟",
            questionEn = "حروف الجر الزمنية",
            optionsAr = listOf("am", "um", "im", "in"),
            correctIndex = 0,
            explanationAr = "مع أيام الأسبوع وأوقات اليوم نستخدم حرف الجر am (am Montag)."
        ),
        ExamQuestion(
            id = 30,
            questionAr = "أي الجمل التالية تحتوي على فعل منفصل (Trennbares Verb) مركب بشكل صحيح؟",
            questionEn = "الأفعال المنفصلة",
            optionsAr = listOf("Er steht jeden Tag um 7 Uhr auf.", "Er aufsteht jeden Tag um 7 Uhr.", "Er steht auf jeden Tag um 7 Uhr.", "Er jeden Tag um 7 Uhr aufsteht."),
            correctIndex = 0,
            explanationAr = "في الجملة الرئيسية، يُصرّف أصل الفعل في الموقع الثاني وتذهب السابقة (auf) إلى نهاية الجملة."
        ),
        ExamQuestion(
            id = 31,
            questionAr = "في صيغة الماضي (Perfekt)، ما الفعل المساعد المناسب مع فعل الحركة 'fahren'؟",
            questionEn = "صيغة الماضي التام Perfekt",
            optionsAr = listOf("sein (ist gefahren)", "haben (hat gefahren)", "werden (wird gefahren)", "tun (tut gefahren)"),
            correctIndex = 0,
            explanationAr = "أفعال الحركة والانتقال المكاني تأخذ الفعل المساعد sein في الماضي التام Perfekt."
        ),
        ExamQuestion(
            id = 32,
            questionAr = "ما هي أداة الاستفهام الصحيحة للسؤال عن المكان الثابت (Wo)؟ '___ wohnst du?'",
            questionEn = "أدوات الاستفهام W-Fragen",
            optionsAr = listOf("Wo", "Wohin", "Woher", "Wann"),
            correctIndex = 0,
            explanationAr = "للسؤال عن موقع السكن أو التواجد نستخدم Wo (أين؟)."
        ),
        ExamQuestion(
            id = 33,
            questionAr = "ما هو جمع كلمة 'das Buch' (الكتاب) بالألمانية؟",
            questionEn = "صيغ الجموع",
            optionsAr = listOf("die Bücher", "die Buche", "die Büchern", "die Buchs"),
            correctIndex = 0,
            explanationAr = "جمع كلمة das Buch هو die Bücher مع إضافة أوملاوت ولحقة er."
        ),
        ExamQuestion(
            id = 34,
            questionAr = "اختر النهاية الصحيحة للصفة بعد أداة التنكير في الرفع المذكر: 'Das ist ein neu___ Wagen.'",
            questionEn = "نهايات الصفات Adjektivdeklination",
            optionsAr = listOf("neuer", "neues", "neuen", "neuem"),
            correctIndex = 0,
            explanationAr = "بعد ein في الرفع المذكر تأخذ الصفة النهاية -er لتدل على جنس الاسم (ein neuer Wagen)."
        ),
        ExamQuestion(
            id = 35,
            questionAr = "ما الأداة المناسبة للاسم 'die Frau' في حالة المجرور (Dativ)؟ 'Ich helfe ___ Frau.'",
            questionEn = "حالة المجرور Dativ",
            optionsAr = listOf("der", "die", "den", "dem"),
            correctIndex = 0,
            explanationAr = "تتحول أداة المؤنث die في حالة Dativ إلى der."
        ),
        ExamQuestion(
            id = 36,
            questionAr = "أي فعل من الأفعال التالية يطلب دائماً حالة المجرور (Dativ)؟",
            questionEn = "أفعال تطلب Dativ",
            optionsAr = listOf("helfen", "sehen", "kaufen", "fragen"),
            correctIndex = 0,
            explanationAr = "الفعل helfen يطلب مفعولاً به مجروراً بالـ Dativ (Ich helfe dir)."
        ),
        ExamQuestion(
            id = 37,
            questionAr = "ما الضمير الملكي للمخاطب الرسمي (Sie): 'Ist das ___ Pass?'",
            questionEn = "ضمائر الملكية",
            optionsAr = listOf("Ihr", "ihr", "dein", "sein"),
            correctIndex = 0,
            explanationAr = "ضمير الملكية للمخاطب بصيغة الاحترام Sie هو Ihr بحرف كبير."
        ),
        ExamQuestion(
            id = 38,
            questionAr = "كيف تصاغ صيغة المقارنة (Komparativ) من الصفة 'schnell'؟",
            questionEn = "المقارنة والتفضيل",
            optionsAr = listOf("schneller", "am schnellsten", "mehr schnell", "schnellste"),
            correctIndex = 0,
            explanationAr = "يصاغ اسم التفضيل والمقارنة بإضافة اللاحقة -er للصفة (schneller)."
        ),
        ExamQuestion(
            id = 39,
            questionAr = "ما هو نفي الجملة التالية باستخدام 'nicht': 'Er arbeitet heute.'؟",
            questionEn = "قواعد النفي",
            optionsAr = listOf("Er arbeitet heute nicht.", "Er nicht arbeitet heute.", "Er arbeitet nicht heute.", "Er arbeitet kein heute."),
            correctIndex = 0,
            explanationAr = "توضع nicht في نهاية الجملة لنفي الفعل بالكامل (Er arbeitet heute nicht)."
        ),
        ExamQuestion(
            id = 40,
            questionAr = "متى نستخدم أداة النفي 'kein' بدلاً من 'nicht'؟",
            questionEn = "قواعد النفي",
            optionsAr = listOf("لنفي الأسماء النكرة أو غير المسبوقة بأداة", "لنفي الأفعال فقط", "لنفي الصفات فقط", "لنفي الجمل الاستفهامية فقط"),
            correctIndex = 0,
            explanationAr = "تُستخدم kein لنفي الأسماء النكرة أو الأسماء المجردة من الأدوات."
        ),
        ExamQuestion(
            id = 41,
            questionAr = "اختر التصريف الصحيح لفعل 'können' مع 'er':",
            questionEn = "أفعال الكيفية Modalverben",
            optionsAr = listOf("kann", "könnt", "kannst", "können"),
            correctIndex = 0,
            explanationAr = "في أفعال الكيفية، يتطابق تصريف المفرد الغائب (er) مع المتكلم (ich): ich kann, er kann."
        ),
        ExamQuestion(
            id = 42,
            questionAr = "ما المعنى الدقيق لفعل الكيفية 'müssen'؟",
            questionEn = "أفعال الكيفية",
            optionsAr = listOf("الإلزام والضرورة المطلقة", "القدرة والاستطاعة", "الرغبة والإرادة", "السماح والإذن"),
            correctIndex = 0,
            explanationAr = "الفعل müssen يعبر عن الوجوب والضرورة الحتمية الخارجية."
        ),
        ExamQuestion(
            id = 43,
            questionAr = "ما هو حرف الجر المناسب للسؤال عن جهة القدوم: 'Ich komme ___ Algerien.'؟",
            questionEn = "حروف الجر المكانية",
            optionsAr = listOf("aus", "nach", "in", "von"),
            correctIndex = 0,
            explanationAr = "للدلالة على الانحدار أو القدوم من دولة أو مدينة نستخدم aus."
        ),
        ExamQuestion(
            id = 44,
            questionAr = "مع الدول المؤنثة مثل 'die Schweiz'، كيف نقول 'أنا مسافر إلى سويسرا'؟",
            questionEn = "حروف الجر مع الدول",
            optionsAr = listOf("in die Schweiz", "nach Schweiz", "zu der Schweiz", "aus die Schweiz"),
            correctIndex = 0,
            explanationAr = "الدول ذات الأدوات لا تأخذ nach بل تأخذ حرف الجر in مع حالة النصب Akkusativ (in die Schweiz)."
        ),
        ExamQuestion(
            id = 45,
            questionAr = "اختر الضمير المتصل المناسب: 'Siehst du ___ (هو في حالة النصب)؟'",
            questionEn = "ضمائر المفعول به",
            optionsAr = listOf("ihn", "ihm", "er", "sein"),
            correctIndex = 0,
            explanationAr = "ضمير الغائب المذكر er يصبح ihn في حالة النصب Akkusativ."
        ),
        ExamQuestion(
            id = 46,
            questionAr = "ما معنى الرابط 'weil' وموقعه في الجملة؟",
            questionEn = "الروابط الثانوية Nebensätze",
            optionsAr = listOf("يعني 'لأن' ويُرسل الفعل المصرف إلى نهاية الجملة", "يعني 'لكن' ولا يغير ترتيب الجملة", "يعني 'أو' ويأتي في الموقع الأول", "يعني 'لذلك' ويأتي بعده الفعل مباشرة"),
            correctIndex = 0,
            explanationAr = "الرابط weil يربط جملة سببية ثانوية ويرسل الفعل المصرف إلى آخر الجملة تماماً."
        ),
        ExamQuestion(
            id = 47,
            questionAr = "أي الجمل التالية صحيحة مع الرابط 'dass' (أنّ)؟",
            questionEn = "الروابط الثانوية",
            optionsAr = listOf("Ich weiß, dass er Deutsch lernt.", "Ich weiß, dass er lernt Deutsch.", "Ich weiß, dass lernt er Deutsch.", "Ich weiß, er lernt dass Deutsch."),
            correctIndex = 0,
            explanationAr = "في الجمل الثانوية بعد dass يذهب الفعل المصرف (lernt) إلى نهاية الجملة."
        ),
        ExamQuestion(
            id = 48,
            questionAr = "ما تصريف الفعل 'werden' في الماضي البسيط (Präteritum) مع 'wir'؟",
            questionEn = "الماضي البسيط Präteritum",
            optionsAr = listOf("wurden", "worden", "wurde", "würden"),
            correctIndex = 0,
            explanationAr = "تصريف فعل werden في الماضي البسيط Präteritum مع wir هو wurden."
        ),
        ExamQuestion(
            id = 49,
            questionAr = "ما هو الترتيب الصحيح للجملة: 'Morgen / ich / nach Berlin / fahre'؟",
            questionEn = "بنية الجملة Satzbau",
            optionsAr = listOf("Morgen fahre ich nach Berlin.", "Morgen ich fahre nach Berlin.", "Ich morgen fahre nach Berlin.", "Fahre morgen ich nach Berlin."),
            correctIndex = 0,
            explanationAr = "القاعدة الذهبية في اللغة الألمانية: الفعل المصرف يحتل دائماً الموقع الثاني (Position 2)."
        ),
        ExamQuestion(
            id = 50,
            questionAr = "اختر صيغة الأمر المناسبة للمخاطب المفرد (du) من فعل 'lesen':",
            questionEn = "صيغة الأمر Imperativ",
            optionsAr = listOf("Lies!", "Lese!", "Liest!", "Lesen!"),
            correctIndex = 0,
            explanationAr = "الأفعال التي يتغير أصلها من e إلى ie تحتفظ بهذا التغيير في صيغة الأمر للمفرد (Lies!)."
        ),
        ExamQuestion(
            id = 51,
            questionAr = "Choisissez l'article défini contracté correct: 'Je vais ___ cinéma ce soir.'",
            questionEn = "Articles contractés",
            optionsAr = listOf("au", "à le", "du", "en"),
            correctIndex = 0,
            explanationAr = "La préposition 'à' fusionne avec l'article masculin 'le' pour former 'au' (à + le = au)."
        ),
        ExamQuestion(
            id = 52,
            questionAr = "Quelle est la conjugaison correcte du verbe 'avoir' avec 'nous' au présent?",
            questionEn = "Conjugaison du présent",
            optionsAr = listOf("avons", "avez", "ont", "sommes"),
            correctIndex = 0,
            explanationAr = "Au présent de l'indicatif, le verbe avoir se conjugue: nous avons."
        ),
        ExamQuestion(
            id = 53,
            questionAr = "Choisissez la préposition correcte pour un pays féminin: 'Elle habite ___ France.'",
            questionEn = "Prépositions géographiques",
            optionsAr = listOf("en", "au", "à la", "dans"),
            correctIndex = 0,
            explanationAr = "Devant les noms de pays féminins se terminant par un 'e', on utilise la préposition 'en'."
        ),
        ExamQuestion(
            id = 54,
            questionAr = "Quelle est la forme du féminin de l'adjectif 'heureux'?",
            questionEn = "Féminin des adjectifs",
            optionsAr = listOf("heureuse", "heureusse", "heureux", "heureue"),
            correctIndex = 0,
            explanationAr = "Les adjectifs masculins se terminant par -eux font leur féminin en -euse."
        ),
        ExamQuestion(
            id = 55,
            questionAr = "Quel auxiliaire utilise-t-on au passé composé pour le verbe 'aller'?",
            questionEn = "Passé composé",
            optionsAr = listOf("être (est allé)", "avoir (a allé)", "faire (fait allé)", "aller (va aller)"),
            correctIndex = 0,
            explanationAr = "Le verbe 'aller' fait partie des verbes de mouvement qui se conjuguent avec l'auxiliaire être."
        ),
        ExamQuestion(
            id = 56,
            questionAr = "Choisissez l'accord correct du participe passé: 'Les lettres que j'ai ___.'",
            questionEn = "Accord du participe passé",
            optionsAr = listOf("écrites", "écrit", "écrite", "écrits"),
            correctIndex = 0,
            explanationAr = "Avec l'auxiliaire avoir, le participe passé s'accorde avec le COD placé avant (les lettres = fém. pluriel)."
        ),
        ExamQuestion(
            id = 57,
            questionAr = "Quelle phrase exprime correctement la négation au présent?",
            questionEn = "La négation",
            optionsAr = listOf("Il ne mange pas de viande.", "Il ne mange point la viande pas.", "Il mange ne pas de viande.", "Il pas mange de viande."),
            correctIndex = 0,
            explanationAr = "La structure de négation standard entoure le verbe conjugué: ne + verbe + pas."
        ),
        ExamQuestion(
            id = 58,
            questionAr = "Quel est le pronom personnel COD remplaçant 'le livre': 'Tu lis le livre? - Oui, je ___ lis.'",
            questionEn = "Pronoms COD",
            optionsAr = listOf("le", "lui", "en", "y"),
            correctIndex = 0,
            explanationAr = "Le nom masculin singulier 'le livre' est un COD direct remplacé par le pronom 'le'."
        ),
        ExamQuestion(
            id = 59,
            questionAr = "Quel est le pronom possessif correct: 'C'est la voiture de Marie. C'est ___ voiture.'",
            questionEn = "Adjectifs possessifs",
            optionsAr = listOf("sa", "son", "ses", "leur"),
            correctIndex = 0,
            explanationAr = "Devant un nom féminin singulier commençant par une consonne, on utilise 'sa'."
        ),
        ExamQuestion(
            id = 60,
            questionAr = "Quelle est l'interrogation correcte avec inversion du sujet?",
            questionEn = "Structure interrogative",
            optionsAr = listOf("Parlez-vous français?", "Vous parlez français-vous?", "Est-ce vous parlez français?", "Parlez vous-le français?"),
            correctIndex = 0,
            explanationAr = "L'inversion formelle du sujet place le verbe suivi d'un trait d'union et du pronom personnel."
        ),
        ExamQuestion(
            id = 61,
            questionAr = "Choisissez l'adjectif démonstratif devant un mot masculin débutant par une voyelle: '___ arbre est grand.'",
            questionEn = "Adjectifs démonstratifs",
            optionsAr = listOf("Cet", "Ce", "Cette", "Ces"),
            correctIndex = 0,
            explanationAr = "Devant un nom masculin commençant par une voyelle ou un h muet, on emploie 'cet'."
        ),
        ExamQuestion(
            id = 62,
            questionAr = "Quelle est la forme correcte du futur proche pour 'partir' avec 'ils'?",
            questionEn = "Futur proche",
            optionsAr = listOf("ils vont partir", "ils partent", "ils partiront", "ils sont partis"),
            correctIndex = 0,
            explanationAr = "Le futur proche se forme avec le verbe aller au présent + infinitif (vont partir)."
        ),
        ExamQuestion(
            id = 63,
            questionAr = "Quel est le pluriel du mot 'le journal'?",
            questionEn = "Pluriel des noms en -al",
            optionsAr = listOf("les journaux", "les journals", "les journau", "les journauxes"),
            correctIndex = 0,
            explanationAr = "La plupart des noms masculins en -al forment leur pluriel en -aux."
        ),
        ExamQuestion(
            id = 64,
            questionAr = "Choisissez la préposition d'heure correcte: 'Le train part ___ 14 heures.'",
            questionEn = "Prépositions temporelles",
            optionsAr = listOf("à", "en", "dans", "vers"),
            correctIndex = 0,
            explanationAr = "On utilise la préposition 'à' pour indiquer une heure précise."
        ),
        ExamQuestion(
            id = 65,
            questionAr = "Quelle est la forme correcte de l'impératif pour 'prendre' avec 'tu'?",
            questionEn = "Mode impératif",
            optionsAr = listOf("Prends!", "Prend!", "Prennes!", "Prenez!"),
            correctIndex = 0,
            explanationAr = "À la 2e personne du singulier des verbes du 3e groupe en -dre, la terminaison conserve le -s (Prends)."
        ),
        ExamQuestion(
            id = 66,
            questionAr = "Comment dit-on 'yesterday' en français?",
            questionEn = "Vocabulaire temporel",
            optionsAr = listOf("hier", "demain", "aujourd'hui", "maintenant"),
            correctIndex = 0,
            explanationAr = "'Hier' signifie le jour précédant aujourd'hui."
        ),
        ExamQuestion(
            id = 67,
            questionAr = "Quel est le contraire de 'toujours' dans une phrase négative?",
            questionEn = "Négation complexe",
            optionsAr = listOf("ne ... jamais", "ne ... rien", "ne ... plus", "ne ... personne"),
            correctIndex = 0,
            explanationAr = "L'antonyme négatif de 'toujours' ou 'souvent' est 'ne ... jamais'."
        ),
        ExamQuestion(
            id = 68,
            questionAr = "Choisissez la préposition correcte: 'Je viens ___ Algérie.'",
            questionEn = "Prépositions de provenance",
            optionsAr = listOf("d'", "de l'", "du", "en"),
            correctIndex = 0,
            explanationAr = "Devant un nom de pays féminin commençant par une voyelle, la provenance s'exprime par 'd''."
        ),
        ExamQuestion(
            id = 69,
            questionAr = "Quelle phrase exprime une obligation impersonnelle?",
            questionEn = "Obligation impersonnelle",
            optionsAr = listOf("Il faut étudier sérieusement.", "Je dois étudier.", "Tu étudies.", "Nous voulons étudier."),
            correctIndex = 0,
            explanationAr = "'Il faut' + infinitif exprime une obligation générale et impersonnelle."
        ),
        ExamQuestion(
            id = 70,
            questionAr = "Quel est le participe passé du verbe 'faire'?",
            questionEn = "Participe passé",
            optionsAr = listOf("fait", "fais", "faisant", "faites"),
            correctIndex = 0,
            explanationAr = "Le participe passé du verbe irrégulier faire est 'fait'."
        ),
        ExamQuestion(
            id = 71,
            questionAr = "Choisissez le pronom tonique convenable: 'Ce cadeau est pour ___ (je).' ",
            questionEn = "Pronoms toniques",
            optionsAr = listOf("moi", "je", "me", "m'"),
            correctIndex = 0,
            explanationAr = "Après une préposition (pour, avec, sans), on emploie les pronoms toniques (moi, toi, lui...)."
        ),
        ExamQuestion(
            id = 72,
            questionAr = "Quelle forme exprime la durée terminée dans le passé?",
            questionEn = "Expressions de temps",
            optionsAr = listOf("pendant 3 ans", "depuis 3 ans", "en 3 ans", "dans 3 ans"),
            correctIndex = 0,
            explanationAr = "'Pendant' indique une durée complètement délimitée et achevée dans le passé."
        ),
        ExamQuestion(
            id = 73,
            questionAr = "Quelle expression exprime une action continue dans le présent qui a débuté dans le passé?",
            questionEn = "Expressions de temps",
            optionsAr = listOf("depuis deux ans", "pendant deux ans", "pour deux ans", "dans deux ans"),
            correctIndex = 0,
            explanationAr = "'Depuis' indique une action qui a commencé dans le passé et se poursuit dans le présent."
        ),
        ExamQuestion(
            id = 74,
            questionAr = "Quel est le comparatif de supériorité de l'adjectif 'bon'?",
            questionEn = "Comparatifs irréguliers",
            optionsAr = listOf("meilleur", "plus bon", "mieux", "le bon"),
            correctIndex = 0,
            explanationAr = "Le comparatif de supériorité de l'adjectif 'bon' est l'adjectif irrégulier 'meilleur'."
        ),
        ExamQuestion(
            id = 75,
            questionAr = "Quelle est la terminaison de l'imparfait pour 'ils / elles'?",
            questionEn = "Imparfait de l'indicatif",
            optionsAr = listOf("-aient", "-aientent", "-ont", "-èrent"),
            correctIndex = 0,
            explanationAr = "La désinence de l'imparfait pour la 3e personne du pluriel est invariablement -aient."
        ),
        ExamQuestion(
            id = 76,
            questionAr = "Choose the correct verb form: 'By the time the delegates arrived, the keynote speech ___.'",
            questionEn = "Past Perfect and Narrative Tenses",
            optionsAr = listOf("had already concluded", "has already concluded", "was already concluded", "already concluded"),
            correctIndex = 0,
            explanationAr = "The past perfect (had concluded) expresses an action completed before another past event (delegates arrived)."
        ),
        ExamQuestion(
            id = 77,
            questionAr = "Select the correct preposition: 'The research committee is composed ___ seven distinguished scholars.'",
            questionEn = "Prepositional Collocations",
            optionsAr = listOf("of", "from", "with", "by"),
            correctIndex = 0,
            explanationAr = "The standard academic adjective collocation is 'composed of' (consisting of)."
        ),
        ExamQuestion(
            id = 78,
            questionAr = "Which sentence correctly utilizes the first conditional for academic forecasting?",
            questionEn = "Conditional Sentences",
            optionsAr = listOf("If investment continues to decline, unemployment will inevitably rise.", "If investment will decline, unemployment would rise.", "If investment declined, unemployment will rise.", "If investment declines, unemployment would rise."),
            correctIndex = 0,
            explanationAr = "First conditional standard pattern: If + present simple -> will + base verb."
        ),
        ExamQuestion(
            id = 79,
            questionAr = "Choose the accurate passive form: 'Scientists are currently investigating the environmental impact.'",
            questionEn = "Passive Voice Transformation",
            optionsAr = listOf("The environmental impact is currently being investigated by scientists.", "The environmental impact is currently investigated by scientists.", "The environmental impact has been currently investigated.", "The environmental impact was currently being investigated."),
            correctIndex = 0,
            explanationAr = "Present continuous passive requires: is/are + being + past participle (is currently being investigated)."
        ),
        ExamQuestion(
            id = 80,
            questionAr = "Select the appropriate quantifier: 'There is ___ evidence to support this theoretical claim.'",
            questionEn = "Quantifiers in Academic Prose",
            optionsAr = listOf("little (meaning scarce/insufficient)", "few", "many", "a few"),
            correctIndex = 0,
            explanationAr = "'Evidence' is an uncountable noun; 'little' indicates a scarce or negative degree."
        ),
        ExamQuestion(
            id = 81,
            questionAr = "Identify the correctly formed indirect question:",
            questionEn = "Embedded and Indirect Questions",
            optionsAr = listOf("Could you clarify what the proposed methodology entails?", "Could you clarify what does the proposed methodology entail?", "Could you clarify what entails the proposed methodology?", "Could you clarify that what the proposed methodology entails?"),
            correctIndex = 0,
            explanationAr = "In embedded questions, standard subject-verb word order is maintained without auxiliary inversion."
        ),
        ExamQuestion(
            id = 82,
            questionAr = "Which modal verb accurately expresses a logical deduction based on clear evidence?",
            questionEn = "Modal Verbs of Deduction",
            optionsAr = listOf("The experimental results must be valid, given the rigorous controls.", "The results should to be valid.", "The results can to be valid.", "The results must to be valid."),
            correctIndex = 0,
            explanationAr = "'Must' followed by bare infinitive expresses strong logical certainty or deduction."
        ),
        ExamQuestion(
            id = 83,
            questionAr = "Choose the correct relative pronoun: 'The university, ___ reputation is worldwide, announced new grants.'",
            questionEn = "Relative Clauses",
            optionsAr = listOf("whose", "which", "whom", "that"),
            correctIndex = 0,
            explanationAr = "'Whose' denotes possession for both people and institutional entities in non-defining clauses."
        ),
        ExamQuestion(
            id = 84,
            questionAr = "Select the appropriate linking word indicating a contrasting perspective:",
            questionEn = "Discourse Markers of Contrast",
            optionsAr = listOf("However", "Furthermore", "Consequently", "Similarly"),
            correctIndex = 0,
            explanationAr = "'However' introduces a counter-argument or contrastive observation."
        ),
        ExamQuestion(
            id = 85,
            questionAr = "Choose the correct gerund/infinitive pattern: 'The committee decided ___ the publication until further review.'",
            questionEn = "Verb Complementation",
            optionsAr = listOf("to postpone", "postponing", "postpone", "for postponing"),
            correctIndex = 0,
            explanationAr = "The verb 'decide' takes a to-infinitive complement (decided to postpone)."
        ),
        ExamQuestion(
            id = 86,
            questionAr = "Identify the correct form for expressing regret about an unfulfilled past action:",
            questionEn = "Past Modals",
            optionsAr = listOf("We should have consulted the legal department earlier.", "We must have consult the legal department earlier.", "We ought consult the legal department earlier.", "We should had consulted the legal department earlier."),
            correctIndex = 0,
            explanationAr = "'Should have + past participle' expresses unfulfilled obligation or retrospective criticism."
        ),
        ExamQuestion(
            id = 87,
            questionAr = "Which sentence demonstrates correct subject-verb agreement with a collective subject?",
            questionEn = "Subject-Verb Agreement",
            optionsAr = listOf("The majority of participants were satisfied with the workshop.", "The majority of participants was being satisfied.", "The majority of participants is satisfied.", "The majority of participants has satisfied."),
            correctIndex = 0,
            explanationAr = "When followed by an 'of + plural noun' phrase, collective quantifiers like 'majority' take plural agreement."
        ),
        ExamQuestion(
            id = 88,
            questionAr = "Choose the accurate academic collocation for 'carry out': 'The laboratory will ___ an extensive trial.'",
            questionEn = "Academic Collocations",
            optionsAr = listOf("conduct", "make", "commit", "fabricate"),
            correctIndex = 0,
            explanationAr = "In formal academic writing, 'conduct a trial/study/survey' is the preferred standard collocation."
        ),
        ExamQuestion(
            id = 89,
            questionAr = "Select the correct comparative structure:",
            questionEn = "Comparative Structures",
            optionsAr = listOf("The second hypothesis is far more plausible than the first.", "The second hypothesis is far much plausible than the first.", "The second hypothesis is more plausible that the first.", "The second hypothesis is plausible than the first."),
            correctIndex = 0,
            explanationAr = "'Far more + adjective + than' is the accurate modifier for emphatic comparison."
        ),
        ExamQuestion(
            id = 90,
            questionAr = "Identify the accurate preposition: 'The economic crisis resulted ___ severe budgetary cuts.'",
            questionEn = "Verb + Preposition Collocations",
            optionsAr = listOf("in", "from", "at", "to"),
            correctIndex = 0,
            explanationAr = "'Result in' signifies 'lead to / cause', whereas 'result from' indicates origin/cause."
        ),
        ExamQuestion(
            id = 91,
            questionAr = "Choose the correct adverbial placement: 'The findings ___ confirm previous clinical observations.'",
            questionEn = "Adverb Position in Sentences",
            optionsAr = listOf("clearly", "clear", "clarity", "clearness"),
            correctIndex = 0,
            explanationAr = "Adverbs of manner placed between subject and main verb modify the assertion (clearly confirm)."
        ),
        ExamQuestion(
            id = 92,
            questionAr = "Select the appropriate conjunction indicating unexpected concession:",
            questionEn = "Concessive Connectors",
            optionsAr = listOf("Although the sample size was modest, the conclusions remain compelling.", "Despite the sample size was modest.", "In spite the sample size was modest.", "Because the sample size was modest."),
            correctIndex = 0,
            explanationAr = "'Although' introduces a subordinate clause containing a finite verb (was modest)."
        ),
        ExamQuestion(
            id = 93,
            questionAr = "Which sentence correctly employs the third conditional?",
            questionEn = "Hypothetical Conditionals",
            optionsAr = listOf("If the data had been verified earlier, the error would have been averted.", "If the data was verified earlier, the error would be averted.", "If the data had been verified, the error will be averted.", "If the data would have been verified, the error had been averted."),
            correctIndex = 0,
            explanationAr = "Third conditional structure: If + had + past participle -> would have + past participle."
        ),
        ExamQuestion(
            id = 94,
            questionAr = "Choose the correct phrase expressing tentative hedging in research:",
            questionEn = "Hedging in Academic Writing",
            optionsAr = listOf("The figures appear to indicate a downward trend.", "The figures definitely prove beyond all doubt.", "The figures always show without error.", "The figures never fail to prove."),
            correctIndex = 0,
            explanationAr = "Hedging verbs such as 'appear to indicate / tend to suggest' soften claims to maintain academic rigor."
        ),
        ExamQuestion(
            id = 95,
            questionAr = "Select the correct noun form: 'The ___ of the treaty took several months of negotiation.'",
            questionEn = "Nominalization in Academic Style",
            optionsAr = listOf("ratification", "ratify", "ratified", "ratifyingly"),
            correctIndex = 0,
            explanationAr = "Nominalization transforms verbal actions into abstract noun concepts (ratification)."
        ),
        ExamQuestion(
            id = 96,
            questionAr = "Identify the correct preposition: 'She is renowned ___ her pioneering work in sociolinguistics.'",
            questionEn = "Adjective Prepositions",
            optionsAr = listOf("for", "with", "about", "by"),
            correctIndex = 0,
            explanationAr = "The adjective 'renowned' collocates with the preposition 'for' (famous for)."
        ),
        ExamQuestion(
            id = 97,
            questionAr = "Which sentence correctly avoids a comma splice?",
            questionEn = "Punctuation in Academic English",
            optionsAr = listOf("The report was finished; therefore, the team adjourned the meeting.", "The report was finished, therefore the team adjourned the meeting.", "The report was finished therefore, the team adjourned.", "The report was finished, and therefore the team adjourned."),
            correctIndex = 0,
            explanationAr = "Two independent clauses linked by a conjunctive adverb require a semicolon before and a comma after."
        ),
        ExamQuestion(
            id = 98,
            questionAr = "Choose the antonym of 'superficial' in an academic analysis:",
            questionEn = "Academic Vocabulary",
            optionsAr = listOf("profound / in-depth", "shallow", "cursory", "trivial"),
            correctIndex = 0,
            explanationAr = "'Profound' or 'in-depth' denotes deep, thorough analytical rigor, contrary to 'superficial'."
        ),
        ExamQuestion(
            id = 99,
            questionAr = "Select the correct phrasal verb meaning 'account for / comprise': 'Renewable sources ___ 30% of energy.'",
            questionEn = "Phrasal Verbs in Data Commentary",
            optionsAr = listOf("make up", "bring about", "take down", "give off"),
            correctIndex = 0,
            explanationAr = "'Make up / account for' is the standard terminology for explaining constituent proportions in charts."
        ),
        ExamQuestion(
            id = 100,
            questionAr = "Choose the correct form: 'Neither the author nor the editors ___ responsible for third-party citations.'",
            questionEn = "Correlative Conjunctions",
            optionsAr = listOf("are", "is", "were being", "am"),
            correctIndex = 0,
            explanationAr = "With 'neither... nor', the verb agrees with the closer subject ('editors' = plural -> are)."
        )
    )

    // GOETHE TRACK (100 QUESTIONS)
    val goetheQuestions = listOf(
        ExamQuestion(
            id = 1,
            questionAr = "ما الأداة الصحيحة للاسم المذكر في حالة المفعول به (Akkusativ)؟ 'Ich kaufe ___ Tisch.'",
            questionEn = "أدوات التعريف والإعراب",
            optionsAr = listOf("den", "der", "das", "dem"),
            correctIndex = 0,
            explanationAr = "الاسم Tisch مذكر (der Tisch)، وفي حالة النصب (Akkusativ) تتحول der إلى den."
        ),
        ExamQuestion(
            id = 2,
            questionAr = "اختر التصريف الصحيح للفعل 'haben' مع الضمير 'ihr':",
            questionEn = "تصريف الأفعال الأساسية",
            optionsAr = listOf("habt", "haben", "hast", "hat"),
            correctIndex = 0,
            explanationAr = "تصريف فعل haben مع ضمير الجمع المخاطب ihr هو habt."
        ),
        ExamQuestion(
            id = 3,
            questionAr = "ما هو حرف الجر المناسب للوقت: 'Der Unterricht beginnt ___ 9:00 Uhr.'؟",
            questionEn = "حروف الجر الزمنية",
            optionsAr = listOf("um", "am", "im", "an"),
            correctIndex = 0,
            explanationAr = "مع الساعات المحددة نستخدم دائماً حرف الجر um (um 9 Uhr)."
        ),
        ExamQuestion(
            id = 4,
            questionAr = "ما هو حرف الجر المناسب للأيام: 'Ich habe einen Termin ___ Montag.'؟",
            questionEn = "حروف الجر الزمنية",
            optionsAr = listOf("am", "um", "im", "in"),
            correctIndex = 0,
            explanationAr = "مع أيام الأسبوع وأوقات اليوم نستخدم حرف الجر am (am Montag)."
        ),
        ExamQuestion(
            id = 5,
            questionAr = "أي الجمل التالية تحتوي على فعل منفصل (Trennbares Verb) مركب بشكل صحيح؟",
            questionEn = "الأفعال المنفصلة",
            optionsAr = listOf("Er steht jeden Tag um 7 Uhr auf.", "Er aufsteht jeden Tag um 7 Uhr.", "Er steht auf jeden Tag um 7 Uhr.", "Er jeden Tag um 7 Uhr aufsteht."),
            correctIndex = 0,
            explanationAr = "في الجملة الرئيسية، يُصرّف أصل الفعل في الموقع الثاني وتذهب السابقة (auf) إلى نهاية الجملة."
        ),
        ExamQuestion(
            id = 6,
            questionAr = "في صيغة الماضي (Perfekt)، ما الفعل المساعد المناسب مع فعل الحركة 'fahren'؟",
            questionEn = "صيغة الماضي التام Perfekt",
            optionsAr = listOf("sein (ist gefahren)", "haben (hat gefahren)", "werden (wird gefahren)", "tun (tut gefahren)"),
            correctIndex = 0,
            explanationAr = "أفعال الحركة والانتقال المكاني تأخذ الفعل المساعد sein في الماضي التام Perfekt."
        ),
        ExamQuestion(
            id = 7,
            questionAr = "ما هي أداة الاستفهام الصحيحة للسؤال عن المكان الثابت (Wo)؟ '___ wohnst du?'",
            questionEn = "أدوات الاستفهام W-Fragen",
            optionsAr = listOf("Wo", "Wohin", "Woher", "Wann"),
            correctIndex = 0,
            explanationAr = "للسؤال عن موقع السكن أو التواجد نستخدم Wo (أين؟)."
        ),
        ExamQuestion(
            id = 8,
            questionAr = "ما هو جمع كلمة 'das Buch' (الكتاب) بالألمانية؟",
            questionEn = "صيغ الجموع",
            optionsAr = listOf("die Bücher", "die Buche", "die Büchern", "die Buchs"),
            correctIndex = 0,
            explanationAr = "جمع كلمة das Buch هو die Bücher مع إضافة أوملاوت ولحقة er."
        ),
        ExamQuestion(
            id = 9,
            questionAr = "اختر النهاية الصحيحة للصفة بعد أداة التنكير في الرفع المذكر: 'Das ist ein neu___ Wagen.'",
            questionEn = "نهايات الصفات Adjektivdeklination",
            optionsAr = listOf("neuer", "neues", "neuen", "neuem"),
            correctIndex = 0,
            explanationAr = "بعد ein في الرفع المذكر تأخذ الصفة النهاية -er لتدل على جنس الاسم (ein neuer Wagen)."
        ),
        ExamQuestion(
            id = 10,
            questionAr = "ما الأداة المناسبة للاسم 'die Frau' في حالة المجرور (Dativ)؟ 'Ich helfe ___ Frau.'",
            questionEn = "حالة المجرور Dativ",
            optionsAr = listOf("der", "die", "den", "dem"),
            correctIndex = 0,
            explanationAr = "تتحول أداة المؤنث die في حالة Dativ إلى der."
        ),
        ExamQuestion(
            id = 11,
            questionAr = "أي فعل من الأفعال التالية يطلب دائماً حالة المجرور (Dativ)؟",
            questionEn = "أفعال تطلب Dativ",
            optionsAr = listOf("helfen", "sehen", "kaufen", "fragen"),
            correctIndex = 0,
            explanationAr = "الفعل helfen يطلب مفعولاً به مجروراً بالـ Dativ (Ich helfe dir)."
        ),
        ExamQuestion(
            id = 12,
            questionAr = "ما الضمير الملكي للمخاطب الرسمي (Sie): 'Ist das ___ Pass?'",
            questionEn = "ضمائر الملكية",
            optionsAr = listOf("Ihr", "ihr", "dein", "sein"),
            correctIndex = 0,
            explanationAr = "ضمير الملكية للمخاطب بصيغة الاحترام Sie هو Ihr بحرف كبير."
        ),
        ExamQuestion(
            id = 13,
            questionAr = "كيف تصاغ صيغة المقارنة (Komparativ) من الصفة 'schnell'؟",
            questionEn = "المقارنة والتفضيل",
            optionsAr = listOf("schneller", "am schnellsten", "mehr schnell", "schnellste"),
            correctIndex = 0,
            explanationAr = "يصاغ اسم التفضيل والمقارنة بإضافة اللاحقة -er للصفة (schneller)."
        ),
        ExamQuestion(
            id = 14,
            questionAr = "ما هو نفي الجملة التالية باستخدام 'nicht': 'Er arbeitet heute.'؟",
            questionEn = "قواعد النفي",
            optionsAr = listOf("Er arbeitet heute nicht.", "Er nicht arbeitet heute.", "Er arbeitet nicht heute.", "Er arbeitet kein heute."),
            correctIndex = 0,
            explanationAr = "توضع nicht في نهاية الجملة لنفي الفعل بالكامل (Er arbeitet heute nicht)."
        ),
        ExamQuestion(
            id = 15,
            questionAr = "متى نستخدم أداة النفي 'kein' بدلاً من 'nicht'؟",
            questionEn = "قواعد النفي",
            optionsAr = listOf("لنفي الأسماء النكرة أو غير المسبوقة بأداة", "لنفي الأفعال فقط", "لنفي الصفات فقط", "لنفي الجمل الاستفهامية فقط"),
            correctIndex = 0,
            explanationAr = "تُستخدم kein لنفي الأسماء النكرة أو الأسماء المجردة من الأدوات."
        ),
        ExamQuestion(
            id = 16,
            questionAr = "اختر التصريف الصحيح لفعل 'können' مع 'er':",
            questionEn = "أفعال الكيفية Modalverben",
            optionsAr = listOf("kann", "könnt", "kannst", "können"),
            correctIndex = 0,
            explanationAr = "في أفعال الكيفية، يتطابق تصريف المفرد الغائب (er) مع المتكلم (ich): ich kann, er kann."
        ),
        ExamQuestion(
            id = 17,
            questionAr = "ما المعنى الدقيق لفعل الكيفية 'müssen'؟",
            questionEn = "أفعال الكيفية",
            optionsAr = listOf("الإلزام والضرورة المطلقة", "القدرة والاستطاعة", "الرغبة والإرادة", "السماح والإذن"),
            correctIndex = 0,
            explanationAr = "الفعل müssen يعبر عن الوجوب والضرورة الحتمية الخارجية."
        ),
        ExamQuestion(
            id = 18,
            questionAr = "ما هو حرف الجر المناسب للسؤال عن جهة القدوم: 'Ich komme ___ Algerien.'؟",
            questionEn = "حروف الجر المكانية",
            optionsAr = listOf("aus", "nach", "in", "von"),
            correctIndex = 0,
            explanationAr = "للدلالة على الانحدار أو القدوم من دولة أو مدينة نستخدم aus."
        ),
        ExamQuestion(
            id = 19,
            questionAr = "مع الدول المؤنثة مثل 'die Schweiz'، كيف نقول 'أنا مسافر إلى سويسرا'؟",
            questionEn = "حروف الجر مع الدول",
            optionsAr = listOf("in die Schweiz", "nach Schweiz", "zu der Schweiz", "aus die Schweiz"),
            correctIndex = 0,
            explanationAr = "الدول ذات الأدوات لا تأخذ nach بل تأخذ حرف الجر in مع حالة النصب Akkusativ (in die Schweiz)."
        ),
        ExamQuestion(
            id = 20,
            questionAr = "اختر الضمير المتصل المناسب: 'Siehst du ___ (هو في حالة النصب)؟'",
            questionEn = "ضمائر المفعول به",
            optionsAr = listOf("ihn", "ihm", "er", "sein"),
            correctIndex = 0,
            explanationAr = "ضمير الغائب المذكر er يصبح ihn في حالة النصب Akkusativ."
        ),
        ExamQuestion(
            id = 21,
            questionAr = "ما معنى الرابط 'weil' وموقعه في الجملة؟",
            questionEn = "الروابط الثانوية Nebensätze",
            optionsAr = listOf("يعني 'لأن' ويُرسل الفعل المصرف إلى نهاية الجملة", "يعني 'لكن' ولا يغير ترتيب الجملة", "يعني 'أو' ويأتي في الموقع الأول", "يعني 'لذلك' ويأتي بعده الفعل مباشرة"),
            correctIndex = 0,
            explanationAr = "الرابط weil يربط جملة سببية ثانوية ويرسل الفعل المصرف إلى آخر الجملة تماماً."
        ),
        ExamQuestion(
            id = 22,
            questionAr = "أي الجمل التالية صحيحة مع الرابط 'dass' (أنّ)؟",
            questionEn = "الروابط الثانوية",
            optionsAr = listOf("Ich weiß, dass er Deutsch lernt.", "Ich weiß, dass er lernt Deutsch.", "Ich weiß, dass lernt er Deutsch.", "Ich weiß, er lernt dass Deutsch."),
            correctIndex = 0,
            explanationAr = "في الجمل الثانوية بعد dass يذهب الفعل المصرف (lernt) إلى نهاية الجملة."
        ),
        ExamQuestion(
            id = 23,
            questionAr = "ما تصريف الفعل 'werden' في الماضي البسيط (Präteritum) مع 'wir'؟",
            questionEn = "الماضي البسيط Präteritum",
            optionsAr = listOf("wurden", "worden", "wurde", "würden"),
            correctIndex = 0,
            explanationAr = "تصريف فعل werden في الماضي البسيط Präteritum مع wir هو wurden."
        ),
        ExamQuestion(
            id = 24,
            questionAr = "ما هو الترتيب الصحيح للجملة: 'Morgen / ich / nach Berlin / fahre'؟",
            questionEn = "بنية الجملة Satzbau",
            optionsAr = listOf("Morgen fahre ich nach Berlin.", "Morgen ich fahre nach Berlin.", "Ich morgen fahre nach Berlin.", "Fahre morgen ich nach Berlin."),
            correctIndex = 0,
            explanationAr = "القاعدة الذهبية في اللغة الألمانية: الفعل المصرف يحتل دائماً الموقع الثاني (Position 2)."
        ),
        ExamQuestion(
            id = 25,
            questionAr = "اختر صيغة الأمر المناسبة للمخاطب المفرد (du) من فعل 'lesen':",
            questionEn = "صيغة الأمر Imperativ",
            optionsAr = listOf("Lies!", "Lese!", "Liest!", "Lesen!"),
            correctIndex = 0,
            explanationAr = "الأفعال التي يتغير أصلها من e إلى ie تحتفظ بهذا التغيير في صيغة الأمر للمفرد (Lies!)."
        ),
        ExamQuestion(
            id = 26,
            questionAr = "ما الفرق بين حروف الجر المكانية 'nach' و 'zu'؟",
            questionEn = "حروف الجر المكانية",
            optionsAr = listOf("nach للمدن والبلدان، و zu للأشخاص والأماكن المحددة داخل المدينة", "nach للأشخاص و zu للمدن", "كلاهما متطابق ولا فرق بينهما", "zu تستخدم فقط في الماضي"),
            correctIndex = 0,
            explanationAr = "تُستخدم nach مع المدن والبلدان بدون أداة، بينما zu تطلب Dativ وتُستخدم مع الأشخاص والمؤسسات."
        ),
        ExamQuestion(
            id = 27,
            questionAr = "ما هي الأداة الموصولة الصحيحة للاسم المذكر في حالة المجرور Dativ؟ 'Der Mann, ___ ich helfe.'",
            questionEn = "الجمل الموصولة Relativsätze",
            optionsAr = listOf("dem", "der", "den", "dessen"),
            correctIndex = 0,
            explanationAr = "الاسم الموصوف مذكر والصلة تطلب حالة Dativ بسبب الفعل helfen، فتكون الأداة dem."
        ),
        ExamQuestion(
            id = 28,
            questionAr = "أي الجمل التالية تستخدم رابط التناقض 'obwohl' (رغم أن) بشكل سليم؟",
            questionEn = "الروابط التناقضية",
            optionsAr = listOf("Obwohl es regnet, gehen wir spazieren.", "Obwohl es regnet, wir gehen spazieren.", "Wir gehen spazieren, obwohl regnet es.", "Obwohl regnet es, gehen wir spazieren."),
            correctIndex = 0,
            explanationAr = "الرابط obwohl يرسل الفعل لنهاية جملته، وفي الجملة الرئيسية يبدأ الترتيب بالفعل المصرف."
        ),
        ExamQuestion(
            id = 29,
            questionAr = "ما المعنى الدقيق لحرف الجر 'trotz' وحالته الإعرابية المعتمدة؟",
            questionEn = "حروف الجر وحالات الإعراب",
            optionsAr = listOf("يعني 'على الرغم من' ويطلب حالة المضاف إليه (Genitiv)", "يعني 'بسبب' ويطلب Dativ", "يعني 'من أجل' ويطلب Akkusativ", "يعني 'بدون' ويطلب Nominativ"),
            correctIndex = 0,
            explanationAr = "حرف الجر trotz يعني بالرغم من ويطلب فصحوياً حالة المضاف إليه Genitiv."
        ),
        ExamQuestion(
            id = 30,
            questionAr = "كيف نصوغ صيغة الشرط والتمني (Konjunktiv II) لفعل 'haben' في الحاضر؟",
            questionEn = "صيغة الشرط Konjunktiv II",
            optionsAr = listOf("hätte", "hatte", "habe", "würde haben"),
            correctIndex = 0,
            explanationAr = "صيغة Konjunktiv II الأصلية لفعل haben مع ich/er هي hätte مع أوملاوت."
        ),
        ExamQuestion(
            id = 31,
            questionAr = "كيف نصوغ صيغة الشرط والتمني (Konjunktiv II) لفعل 'sein'؟",
            questionEn = "صيغة الشرط Konjunktiv II",
            optionsAr = listOf("wäre", "war", "sei", "würde sein"),
            correctIndex = 0,
            explanationAr = "صيغة Konjunktiv II لفعل sein مع ich/er هي wäre."
        ),
        ExamQuestion(
            id = 32,
            questionAr = "ما البنية الصحيحة لتركيب المصدر مع 'um... zu' للغايات والأهداف؟",
            questionEn = "صيغ المصدر Infinitivkonstruktionen",
            optionsAr = listOf("Er lernt viel, um die Prüfung zu bestehen.", "Er lernt viel, um zu die Prüfung bestehen.", "Er lernt viel, um die Prüfung bestehen zu.", "Er lernt viel, zu um die Prüfung bestehen."),
            correctIndex = 0,
            explanationAr = "في تركيب um... zu يأتي المفعول به أولاً ثم zu تليها مباشرة الصيغة المصدرية في آخر الجملة."
        ),
        ExamQuestion(
            id = 33,
            questionAr = "أي الأفعال التالية من أفعال الانعكاس الإلزامية (Echtes reflexives Verb)؟",
            questionEn = "الأفعال المنعكسة Reflexivverben",
            optionsAr = listOf("sich freuen", "waschen", "anziehen", "sehen"),
            correctIndex = 0,
            explanationAr = "الفعل sich freuen (يفرح/يتطلع لـ) منعكس إلزامي مع ضمير الانعكاس sich."
        ),
        ExamQuestion(
            id = 34,
            questionAr = "ما الفرق بين 'wenn' و 'als' في الدلالة الزمنية الماضية؟",
            questionEn = "الروابط الزمنية als vs wenn",
            optionsAr = listOf("als لحدث فريد وقع مرة واحدة في الماضي، و wenn للأحداث المتكررة أو الحاضرة", "als للحاضر و wenn للماضي فقط", "كلاهما يستعملان للمستقبل فقط", "wenn تستخدم فقط للأشخاص"),
            correctIndex = 0,
            explanationAr = "القاعدة: als لحدث ماضٍ وقع مرة واحدة محددة، بينما wenn للشرط أو التكرار."
        ),
        ExamQuestion(
            id = 35,
            questionAr = "ما هو مضاف إليه الاسم المذكر والمحايد في حالة Genitiv؟",
            questionEn = "حالة المضاف إليه Genitiv",
            optionsAr = listOf("يضاف للاسم لاحقة -(e)s وتتحول الأداة إلى des", "تتحول الأداة إلى dem بدون إضافة للاسم", "تتحول الأداة إلى den", "لا يتغير شكل الاسم بتاتاً"),
            correctIndex = 0,
            explanationAr = "في حالة Genitiv، تتحول der و das إلى des مع إضافة -s أو -es للاسم المفرد."
        ),
        ExamQuestion(
            id = 36,
            questionAr = "اختر حرف الجر المناسب للفعل 'warten' (ينتظر):",
            questionEn = "أفعال مرتبطة بحروف جر",
            optionsAr = listOf("warten auf (+ Akkusativ)", "warten an (+ Dativ)", "warten für (+ Akkusativ)", "warten über (+ Akkusativ)"),
            correctIndex = 0,
            explanationAr = "الفعل warten يرتبط دائماً بحرف الجر auf مع حالة النصب Akkusativ."
        ),
        ExamQuestion(
            id = 37,
            questionAr = "اختر حرف الجر المناسب للفعل 'interessieren' في صيغته المنعكسة:",
            questionEn = "أفعال مرتبطة بحروف جر",
            optionsAr = listOf("sich interessieren für (+ Akkusativ)", "sich interessieren an (+ Dativ)", "sich interessieren auf (+ Akkusativ)", "sich interessieren mit (+ Dativ)"),
            correctIndex = 0,
            explanationAr = "التركيب هو sich interessieren für (+ Akkusativ) للاهتمام بشيء ما."
        ),
        ExamQuestion(
            id = 38,
            questionAr = "ما الرابط المزدوج الذي يفيد الإثبات المزدوج (هذا وذاك أيضاً)؟",
            questionEn = "الروابط المزدوجة",
            optionsAr = listOf("sowohl ... als auch", "weder ... noch", "entweder ... oder", "zwar ... aber"),
            correctIndex = 0,
            explanationAr = "الرابط sowohl ... als auch يعني 'كلا الأمرين معاً' (سواء كذا وكذلك كذا)."
        ),
        ExamQuestion(
            id = 39,
            questionAr = "ما الرابط المزدوج الذي يفيد النفي التام للطرفين؟",
            questionEn = "الروابط المزدوجة",
            optionsAr = listOf("weder ... noch", "sowohl ... als auch", "nicht nur ... sondern auch", "einerseits ... andererseits"),
            correctIndex = 0,
            explanationAr = "الرابط weder ... noch يعني 'لا هذا ولا ذاك' (نفي الخيارين)."
        ),
        ExamQuestion(
            id = 40,
            questionAr = "ما هو التصريف الصحيح لفعل 'denken' في الماضي التام (Perfekt)؟",
            questionEn = "تصريف الأفعال الشاذة",
            optionsAr = listOf("hat gedacht", "hat gedenkt", "ist gedacht", "hat gedenken"),
            correctIndex = 0,
            explanationAr = "فعل denken شاذ مختلط: الماضي التام hat gedacht."
        ),
        ExamQuestion(
            id = 41,
            questionAr = "كيف يصاغ المبني للمجهول في الحاضر (Vorgangspassiv Präsens)؟",
            questionEn = "المبني للمجهول Passiv",
            optionsAr = listOf("werden المصرف + Partizip II في نهاية الجملة", "sein المصرف + Partizip II", "haben المصرف + Partizip II", "werden + Infinitiv"),
            correctIndex = 0,
            explanationAr = "يصاغ المبني للمجهول الحركي عبر فعل werden مصرفاً في الموقع 2 و اسم المفعول Partizip II في النهاية."
        ),
        ExamQuestion(
            id = 42,
            questionAr = "تحويل الجملة 'Der Arzt operiert den Patienten' إلى المبني للمجهول:",
            questionEn = "المبني للمجهول Passiv",
            optionsAr = listOf("Der Patient wird vom Arzt operiert.", "Der Patient ist vom Arzt operiert.", "Der Patient hat operiert werden.", "Vom Arzt der Patient operiert wird."),
            correctIndex = 0,
            explanationAr = "يتحول المفعول به إلى فاعل (Der Patient) ويُستعمل فعل werden وفاعلية الفاعل عبر von + Dativ."
        ),
        ExamQuestion(
            id = 43,
            questionAr = "ما الأداة المناسبة لجمع الأسماء في حالة المجرور Dativ؟",
            questionEn = "حالة المجرور Dativ للجمع",
            optionsAr = listOf("den (مع إضافة -n للاسم إن أمكن)", "die", "der", "dem"),
            correctIndex = 0,
            explanationAr = "أداة الجمع في Dativ هي دائماً den مع إضافة حرف -n لنهاية الاسم الجمع ما لم ينته بـ s أو n."
        ),
        ExamQuestion(
            id = 44,
            questionAr = "ما معنى الفعل 'aufhören mit'؟",
            questionEn = "الأفعال الاصطلاحية",
            optionsAr = listOf("التوقف عن أو إنهاء عمل ما", "البدء في نشاط جديد", "الاستماع بتركيز", "الارتفاع إلى أعلى"),
            correctIndex = 0,
            explanationAr = "الفعل aufhören mit يعني الكف عن أو التوقف عن ممارسة شيء ما."
        ),
        ExamQuestion(
            id = 45,
            questionAr = "اختر التعبير السليم للتعبير عن الرأي بالألمانية:",
            questionEn = "التعبير عن الرأي",
            optionsAr = listOf("Meiner Meinung nach (+ فعل مصرف)", "Nach meine Meinung", "In meiner Meinung", "Für meine Meinung"),
            correctIndex = 0,
            explanationAr = "التعبير المعياري هو: Meiner Meinung nach ويليه مباشرة الفعل المصرف."
        ),
        ExamQuestion(
            id = 46,
            questionAr = "ما الفرق بين 'kennen' و 'wissen'؟",
            questionEn = "الفروق الدلالية",
            optionsAr = listOf("kennen للأشخاص والأماكن، و wissen للحقائق والمعلومات المجردة", "wissen للأشخاص و kennen للحقائق", "كلاهما يستعملان للمستقبل فقط", "لا يوجد أي فرق بينهما"),
            correctIndex = 0,
            explanationAr = "الفعل kennen يعني يعرف شخصاً أو مكاناً بالمعاينة، بينما wissen يعني يعلم حقيقة أو معلومة."
        ),
        ExamQuestion(
            id = 47,
            questionAr = "ما هو حرف الجر المناسب للفعل 'teilnehmen' (يشارك في)؟",
            questionEn = "أفعال بحروف جر",
            optionsAr = listOf("teilnehmen an (+ Dativ)", "teilnehmen in (+ Dativ)", "teilnehmen bei (+ Dativ)", "teilnehmen auf (+ Akkusativ)"),
            correctIndex = 0,
            explanationAr = "الفعل teilnehmen يرتبط بحرف الجر an مع حالة المجرور Dativ."
        ),
        ExamQuestion(
            id = 48,
            questionAr = "ما الدلالة الزمنية لصيغة المستقبل الأولى (Futur I) عبر فعل 'werden'؟",
            questionEn = "صيغة المستقبل Futur I",
            optionsAr = listOf("التوقع أو الوعد أو التخطيط المستقبلي", "الندم على الماضي", "الطلب المهذب في الحاضر", "النفي المطلق"),
            correctIndex = 0,
            explanationAr = "صيغة Futur I (werden + Infinitiv) تدل على التنبؤ، النوايا المستقبلية أو التخمين في الحاضر."
        ),
        ExamQuestion(
            id = 49,
            questionAr = "كيف تصاغ صفة التفضيل المطلق (Superlativ) من 'gut'؟",
            questionEn = "المقارنة والتفضيل",
            optionsAr = listOf("am besten", "am gutsten", "besser", "am meisten gut"),
            correctIndex = 0,
            explanationAr = "صفة gut شاذة: gut -> besser -> am besten."
        ),
        ExamQuestion(
            id = 50,
            questionAr = "ما معنى اللاحقة '-los' عند اتصالها بالأسماء (مثل arbeitslos, kostenlos)؟",
            questionEn = "اشتقاق الكلمات",
            optionsAr = listOf("تفيد انعدام أو خلو الشيء (بدون عمل / مجاني بدون تكلفة)", "تفيد الكثرة والوفرة", "تفيد الإمكانية والقابلية", "تفيد الانتماء لمهنة"),
            correctIndex = 0,
            explanationAr = "اللاحقة -los تفيد النفي والافتقار للشيء المقترن بالاسم (بدون عمل، عديم التكلفة)."
        ),
        ExamQuestion(
            id = 51,
            questionAr = "ما هي الصيغة البديلة للمبني للمجهول (Passiversatzform) المساوية لـ 'Das Buch lässt sich leicht lesen'؟",
            questionEn = "بدائل المبني للمجهول",
            optionsAr = listOf("Das Buch kann leicht gelesen werden.", "Das Buch muss leicht gelesen werden.", "Das Buch soll leicht gelesen werden.", "Das Buch wird leicht gelesen haben."),
            correctIndex = 0,
            explanationAr = "التركيب 'sich lassen + Infinitiv' يفيد إمكانية الفعل ويكافئ تماماً 'können + Passiv'."
        ),
        ExamQuestion(
            id = 52,
            questionAr = "ما معنى التركيب 'sein + zu + Infinitiv' في الجملة: 'Diese Aufgabe ist bis morgen zu erledigen'؟",
            questionEn = "بدائل المبني للمجهول",
            optionsAr = listOf("يجب إنجاز هذه المهمة (ضرورة وإلزام مكافئ لـ müssen + Passiv)", "يمكن إهمال هذه المهمة", "تم إنجاز المهمة بالأمس", "من المستحيل إنجاز المهمة"),
            correctIndex = 0,
            explanationAr = "التركيب sein + zu + Infinitiv يعبر في سياق الواجبات عن الوجوب الحتمي (muss erledigt werden)."
        ),
        ExamQuestion(
            id = 53,
            questionAr = "ما المعنى الدقيق للتعبير الاصطلاحي الفعلي (Funktionsverbgefüge): 'eine Entscheidung treffen'؟",
            questionEn = "تراكيب الأفعال الوظيفية",
            optionsAr = listOf("يتخذ قراراً (entscheiden)", "يتردد في الاختيار", "يؤجل القرار للأسبوع القادم", "يرفض قراراً سابقاً"),
            correctIndex = 0,
            explanationAr = "هذا التركيب الوظيفي يعني ببساطة: entscheiden (أن يتخذ قراراً رسمياً)."
        ),
        ExamQuestion(
            id = 54,
            questionAr = "ما المقابل الفعلي لتركيب: 'in Betracht ziehen'؟",
            questionEn = "تراكيب الأفعال الوظيفية",
            optionsAr = listOf("berücksichtigen (يأخذ بعين الاعتبار)", "ablehnen (يرفض)", "kritisieren (ينتقد)", "vergessen (ينسى)"),
            correctIndex = 0,
            explanationAr = "التركيب 'etwas in Betracht ziehen' يعني أخذ الشيء بعين الحسبان والاعتبار."
        ),
        ExamQuestion(
            id = 55,
            questionAr = "كيف يُصاغ المبني للمجهول في الماضي التام (Passiv Perfekt)؟",
            questionEn = "المبني للمجهول في الماضي",
            optionsAr = listOf("ist + Partizip II + worden", "hat + Partizip II + geworden", "wurde + Partizip II + sein", "war + Partizip II + worden"),
            correctIndex = 0,
            explanationAr = "في المبني للمجهول الماضي التام نستخدم sein مع Partizip II وكلمة worden (وليس geworden)."
        ),
        ExamQuestion(
            id = 56,
            questionAr = "ما الفرق بين المبني للمجهول الحركي (Vorgangspassiv) والمبني للمجهول الوصفي (Zustandspassiv)؟",
            questionEn = "أنواع المبني للمجهول",
            optionsAr = listOf("الحركي يصف وقوع الفعل (werden)، والوصفي يصف النتيجة المستقرة بعد انتهاء الفعل (sein)", "الحركي للحاضر والوصفي للماضي فقط", "لا يوجد فرق بينهما في المعنى", "الوصفي يستخدم فقط مع أفعال الحركة"),
            correctIndex = 0,
            explanationAr = "مثال: Die Tür wird geschlossen (تُغلق الآن - حدث) مقابل Die Tür ist geschlossen (الباب مغلق - حالة)."
        ),
        ExamQuestion(
            id = 57,
            questionAr = "ما معنى صيغة الشرط في الماضي (Konjunktiv II Vergangenheit): 'Ich hätte das tun sollen'؟",
            questionEn = "صيغة الشرط في الماضي",
            optionsAr = listOf("كان ينبغي عليّ فعل ذلك (لكنني لم أفعله في الواقع)", "فعلت ذلك بنجاح تام", "سأفعل ذلك بالتأكيد غداً", "يستحيل عليّ فعل ذلك الآن"),
            correctIndex = 0,
            explanationAr = "الصيغة تعبر عن لوم أو حسرة على عدم إتمام فعل كان مفروضاً في الماضي."
        ),
        ExamQuestion(
            id = 58,
            questionAr = "اختر حرف الجر الصحيح وحالته الإعرابية مع 'abhängen' (يعتمد على):",
            questionEn = "أفعال مرتبطة بحروف جر",
            optionsAr = listOf("abhängen von (+ Dativ)", "abhängen an (+ Dativ)", "abhängen auf (+ Akkusativ)", "abhängen über (+ Akkusativ)"),
            correctIndex = 0,
            explanationAr = "الفعل abhängen يتطلب حرف الجر von ومجروره بالـ Dativ."
        ),
        ExamQuestion(
            id = 59,
            questionAr = "ما إعراب ما بعد حرف الجر 'während' في النصوص الأكاديمية والرسمية؟",
            questionEn = "حروف الجر المتقدمة",
            optionsAr = listOf("Genitiv (مضاف إليه)", "Dativ فقط", "Akkusativ فقط", "Nominativ"),
            correctIndex = 0,
            explanationAr = "حرف الجر während يطلب معيارياً وفصحوياً حالة المضاف إليه Genitiv (während des Vortrags)."
        ),
        ExamQuestion(
            id = 60,
            questionAr = "ما هي الأداة الموصولة المناسبة في الجملة: 'Das ist alles, ___ ich weiß.'؟",
            questionEn = "الجمل الموصولة المتقدمة",
            optionsAr = listOf("was", "das", "welches", "dass"),
            correctIndex = 0,
            explanationAr = "بعد الكلمات الدالة على العموم المحايد (alles, etwas, nichts, vieles) نستخدم أداة الوصل was."
        ),
        ExamQuestion(
            id = 61,
            questionAr = "ما المعنى الدقيق لفعل الكيفية 'sollen' عند استخدامه لنقل كلام الآخرين (Subjektive Modalverben)؟ 'Er soll sehr reich sein.'",
            questionEn = "الاستخدام الذاتي لأفعال الكيفية",
            optionsAr = listOf("يقال عنه ويشاع أنه ثري جداً (Gerücht / نقل كلام)", "يجب عليه أن يصبح ثرياً فوراً", "هو يريد أن يصبح ثرياً", "كان ثرياً في الماضي فقط"),
            correctIndex = 0,
            explanationAr = "الاستخدام الذاتي لـ sollen يفيد نقل شائعة أو قول من طرف ثالث (Man sagt, dass er sehr reich ist)."
        ),
        ExamQuestion(
            id = 62,
            questionAr = "ما دلالة 'müssen' في: 'Er muss krank sein, denn er sieht blass aus.'؟",
            questionEn = "الاستخدام الذاتي لأفعال الكيفية",
            optionsAr = listOf("استنتاج وشبه يقين عقلي مؤكد (95% Wahrscheinlichkeit)", "إلزام قانوني بضرورة المرض", "رغبة ملحة من الشخص", "شك ضعيف جداً"),
            correctIndex = 0,
            explanationAr = "تفيد müssen هنا الاستنتاج الحتمي شبه المؤكد المبني على قرائن واضحة."
        ),
        ExamQuestion(
            id = 63,
            questionAr = "أي الجمل التالية تمثل صياغة نعتية بالاسم المفعول (Partizipialattribut) متقدمة؟",
            questionEn = "التراكيب النعتية Partizipialattribute",
            optionsAr = listOf("Die von der Regierung beschlossenen Maßnahmen treten morgen in Kraft.", "Die Maßnahmen, die die Regierung beschloss, treten morgen in Kraft.", "Die Maßnahmen beschloss die Regierung morgen.", "Die Regierung beschloss die Maßnahmen für morgen."),
            correctIndex = 0,
            explanationAr = "هذا أسلوب وصفي متقدم يضع شبه الجملة المحصورة بين الأداة والاسم الموصوف."
        ),
        ExamQuestion(
            id = 64,
            questionAr = "كيف نحول التركيب النعتي 'das brennende Haus' إلى جملة موصلة مكافئة؟",
            questionEn = "اسم الفاعل Partizip I",
            optionsAr = listOf("das Haus, das brennt (الحاضر المستمر)", "das Haus, das gebrannt hat", "das Haus, das brennen wird", "das Haus, das verbrannt ist"),
            correctIndex = 0,
            explanationAr = "اسم الفاعل (Partizip I بزيادة d) يعبر عن حدث متزامن ومستمر في الحاضر (das brennt)."
        ),
        ExamQuestion(
            id = 65,
            questionAr = "ما الرابط المزدوج المناسب للتعبير عن تناسب طردي: '___ mehr man lernt, ___ klüger wird man.'؟",
            questionEn = "الروابط التناسبية",
            optionsAr = listOf("Je ... desto (oder umso)", "Nicht nur ... sondern auch", "Weder ... noch", "So ... wie"),
            correctIndex = 0,
            explanationAr = "التعبير التناسبي المقارن يصاغ حصرياً بـ: Je (جملة ثانوية) ... desto/umso (جملة رئيسية تبدأ بالفعل)."
        ),
        ExamQuestion(
            id = 66,
            questionAr = "ما الفرق بين 'anstatt dass' و 'ohne dass'؟",
            questionEn = "الروابط المقارنة المتقدمة",
            optionsAr = listOf("anstatt dass تفيد الاستبدال وفعل شيء آخر، و ohne dass تفيد نفي حدوث النتيجة المصاحبة", "كلاهما يستعملان للشرط الإيجابي", "ohne dass تفيد المستقبل فقط", "anstatt dass تفيد الماضي فقط"),
            correctIndex = 0,
            explanationAr = "anstatt dass تعني بدلاً من أن، بينما ohne dass تعني دون أن يرافق ذلك حدث ما."
        ),
        ExamQuestion(
            id = 67,
            questionAr = "ما هي اللواحق التي تشير غالباً إلى أن الاسم الأكاديمي مؤنث (die)؟",
            questionEn = "تحديد جنس الأسماء الألمانية",
            optionsAr = listOf("-ung, -heit, -keit, -schaft, -tät, -tion", "-ling, -ismus, -or, -ner", "-chen, -lein, -ment, -tum", "-er, -en, -el"),
            correctIndex = 0,
            explanationAr = "جميع الأسماء المنتهية بهذه اللواحق هي أسماء مؤنثة دائماً (die) دون استثناء."
        ),
        ExamQuestion(
            id = 68,
            questionAr = "ما إعراب كلمة 'Kollege' في جملة: 'Ich spreche mit meinem neu___ Kolleg___'؟",
            questionEn = "الأسماء الضعيفة N-Deklination",
            optionsAr = listOf("neuen Kollegen (اسم مذكر ضعيف يضاف له -en في غير الرفع)", "neuem Kollege", "neuer Kollege", "neuen Kolleger"),
            correctIndex = 0,
            explanationAr = "الاسم Kollege مذكر ضعيف (N-Deklination) يكتسب لاحقة -n أو -en في سائر الحالات الإعرابية عدا الرفع."
        ),
        ExamQuestion(
            id = 69,
            questionAr = "ما المعنى الدقيق لفعل 'überweisen' في المعاملات المالية؟",
            questionEn = "المصطلحات الاقتصادية",
            optionsAr = listOf("يحول مبلغاً مالياً عبر الحساب المصرفي", "يسحب نقوداً يدوياً", "يقترض بفائدة", "يدفع نقداً بالعملة الورقية"),
            correctIndex = 0,
            explanationAr = "الفعل überweisen يعني إجراء تحويل مصرفي لحساب شخص آخر."
        ),
        ExamQuestion(
            id = 70,
            questionAr = "ما هو نقيض كلمة 'zustimmen' (يوافق على) بالألمانية؟",
            questionEn = "المتضادات اللغوية",
            optionsAr = listOf("ablehnen / widersprechen", "bejahen", "befürworten", "akzeptieren"),
            correctIndex = 0,
            explanationAr = "الفعل ablehnen (يرفض) أو widersprechen (يعارض) هما نقيض الموافقة zustimmen."
        ),
        ExamQuestion(
            id = 71,
            questionAr = "ما المقصود بـ 'falsche Freunde' في حقل اللغويات والترجمة؟",
            questionEn = "المصطلحات اللغوية",
            optionsAr = listOf("كلمات تتشابه في النطق مع لغة أخرى لكن يختلف معناها تماماً", "أصدقاء غير مخلصين في العمل", "أخطاء مطبعية غير مقصودة", "أفعال شاذة لا تصرف"),
            correctIndex = 0,
            explanationAr = "الأصدقاء الزائفون (False Friends) هي الألفاظ المتشابهة شكلاً بين لغتين مع تباين تام في المعنى."
        ),
        ExamQuestion(
            id = 72,
            questionAr = "ما المعنى الفعلي للكلمة الألمانية 'Gift' للتحذير من الخلط مع الإنجليزية؟",
            questionEn = "الأصدقاء الزائفون Falsche Freunde",
            optionsAr = listOf("السم القاتل (وليس هدية)", "الهدية الثمينة", "الموهبة الموسيقية", "الحلوى اللذيذة"),
            correctIndex = 0,
            explanationAr = "الكلمة الألمانية das Gift تعني السمّ، بينما في الإنجليزية gift تعني الهدية!"
        ),
        ExamQuestion(
            id = 73,
            questionAr = "ما هو الفعل الصحيح الذي يعني 'يقدم طلباً رسمياً أو استمارة ترشح'؟",
            questionEn = "المصطلحات الإدارية",
            optionsAr = listOf("einen Antrag stellen / sich bewerben", "einen Antrag fallen", "einen Antrag nehmen", "einen Antrag bringen"),
            correctIndex = 0,
            explanationAr = "التركيب الاصطلاحي هو: einen Antrag stellen (يقدم طلباً رسمياً للجهات الإدارية)."
        ),
        ExamQuestion(
            id = 74,
            questionAr = "ما معنى حرف الجر المتقدم 'bezüglich' وحالته الإعرابية؟",
            questionEn = "حروف الجر المتقدمة",
            optionsAr = listOf("بخصوص / فيما يتعلق بـ ويطلب حالة Genitiv", "بعيداً عن ويطلب Dativ", "بدون ويطلب Akkusativ", "قريباً من ويطلب Nominativ"),
            correctIndex = 0,
            explanationAr = "حرف الجر bezüglich يعني فيما يتعلق بـ ويطلب حالة المضاف إليه Genitiv."
        ),
        ExamQuestion(
            id = 75,
            questionAr = "كيف يصاغ المصدر الاسمي (Nominalisierung) من الفعل 'entscheiden'؟",
            questionEn = "التحويل الاسمي Nominalisierung",
            optionsAr = listOf("die Entscheidung", "das Entscheidenheit", "die Entscheidschaft", "die Entscheidtung"),
            correctIndex = 0,
            explanationAr = "الاسم المشتق المعياري هو die Entscheidung (القرار)."
        ),
        ExamQuestion(
            id = 76,
            questionAr = "ما الاستخدام النحوي الحصري لصيغة 'Konjunktiv I' في اللغة الألمانية المعيارية؟",
            questionEn = "صيغة نقل الكلام Konjunktiv I",
            optionsAr = listOf("نقل الكلام غير المباشر في الصحافة والتقارير الرسمية (Indirekte Rede)", "التعبير عن الأوامر الصارمة", "التعبير عن التمني المستحيل في الماضي", "وصف أفعال الشرط المستقبلية فقط"),
            correctIndex = 0,
            explanationAr = "تُستخدم صيغة Konjunktiv I أساساً في الصحافة والإعلام لنقل تصريحات الآخرين بحياد تام وبدون تبنٍ للرأي."
        ),
        ExamQuestion(
            id = 77,
            questionAr = "في الجملة المنقولة: 'Der Minister sagte, er ___ zuversichtlich.', ما الشكل الدقيق لـ Konjunktiv I لفعل sein؟",
            questionEn = "صيغة نقل الكلام Konjunktiv I",
            optionsAr = listOf("sei", "wäre", "ist", "seien"),
            correctIndex = 0,
            explanationAr = "صيغة Konjunktiv I لفعل sein مع الغائب المفرد er هي 'sei' حصراً."
        ),
        ExamQuestion(
            id = 78,
            questionAr = "متى يتم استبدال Konjunktiv I بصيغة Konjunktiv II أو تركيب 'würde' في نقل الكلام؟",
            questionEn = "قواعد Konjunktiv I",
            optionsAr = listOf("عندما يتطابق شكل Konjunktiv I تماماً مع الحاضر الإخباري Indikativ Präsens", "عندما يكون المتحدث غاضباً", "فقط في الجمل الاستفهامية", "لا يتم استبداله أبداً"),
            correctIndex = 0,
            explanationAr = "إذا تطابقت صيغة Konjunktiv I مع الحاضر الواقعي (كما في sie haben)، يُستبدل وجوباً بـ Konjunktiv II لتفادي الالتباس."
        ),
        ExamQuestion(
            id = 79,
            questionAr = "ما هي وظيفة أسلوب 'Nominalstil' مقارنة بـ 'Verbalstil' في النصوص القانونية والأكاديمية الألمانية؟",
            questionEn = "الأساليب اللغوية المتقدمة",
            optionsAr = listOf("تكثيف المعنى وضغط المعلومات وتحقيق الطابع الموضوعي غير الشخصي", "جعل النص طفولياً وسهل القراءة", "إلغاء الحاجة لعلامات الترقيم", "تسريع القراءة الشفهية فقط"),
            correctIndex = 0,
            explanationAr = "الأسلوب الاسمي (Nominalstil) سمة النصوص العلمية والرسمية لتركيز المعلومات وحذف الفاعل غير الضروري."
        ),
        ExamQuestion(
            id = 80,
            questionAr = "حوّل الجملة الفعلية 'Weil die Rohstoffpreise stiegen' إلى أسلوب اسمي (Nominalstil):",
            questionEn = "التحويل للأسلوب الاسمي",
            optionsAr = listOf("Aufgrund des Anstiegs der Rohstoffpreise", "Obwohl die Rohstoffpreise steigen", "Indem die Rohstoffpreise steigen", "Beim Steigen Preise"),
            correctIndex = 0,
            explanationAr = "تتحول weil إلى حرف جر سببي (aufgrund + Genitiv) ويتحول الفعل stiegen إلى الاسم des Anstiegs."
        ),
        ExamQuestion(
            id = 81,
            questionAr = "ما الدلالة الذاتية لفعل 'wollen' في: 'Er will den Vertrag bereits unterschrieben haben.'؟",
            questionEn = "المعنى الذاتي لأفعال الكيفية",
            optionsAr = listOf("يدعي ويزعم بنفسه ذلك (Behauptung des Subjekts)", "أجبره القاضي على التوقيع", "يشاع في الجرائد أنه وقع", "يرفض توقيع العقد"),
            correctIndex = 0,
            explanationAr = "استخدام wollen بصيغة ذاتية يفيد ادعاء الفاعل لنفسه شيئاً يشكك فيه المتكلم."
        ),
        ExamQuestion(
            id = 82,
            questionAr = "ما معنى التعبير الاصطلاحي الراقي: 'etwas ad acta legen' في السياق المؤسساتي؟",
            questionEn = "التعابير الاصطلاحية الراقية",
            optionsAr = listOf("حفظ الموضوع وإغلاق الملف نهائياً لاعتباره منتهياً", "البدء الفوري في التحقيق", "ترجمة الملف إلى لغة ثانية", "إرسال الملف إلى المحكمة الجنائية"),
            correctIndex = 0,
            explanationAr = "التعبير اللاتيني-الألماني يعني حفظ المعاملة ووضعها في الأرشيف لانتهاء الحاجة إليها."
        ),
        ExamQuestion(
            id = 83,
            questionAr = "ما المعنى الدقيق للتعبير: 'ins Gewicht fallen' في التحليلات الاقتصادية؟",
            questionEn = "المصطلحات الاقتصادية C1",
            optionsAr = listOf("يكون له ثقل وتأثير بالغ الأهمية في النتيجة", "يزداد وزنه الفيزيائي", "يفقد قيمته النقدية تماماً", "يسقط في الإفلاس"),
            correctIndex = 0,
            explanationAr = "التعبير يعني: von ausschlaggebender Bedeutung sein (أن يكون له وزن حاسم)."
        ),
        ExamQuestion(
            id = 84,
            questionAr = "ما الفرق بين الكلمتين: 'scheinbar' و 'anscheinend' في الدقة اللغوية C2؟",
            questionEn = "الفروق الدلالية الدقيقة C2",
            optionsAr = listOf("scheinbar تعني ظاهرياً فقط وفي الحقيقة ليس كذلك، بينما anscheinend تعني كما يبدو استناداً لقرائن راجحة", "كلاهما مترادفان تماماً", "scheinbar تفيد اليقين التام", "anscheinend تفيد الكذب المتعمد"),
            correctIndex = 0,
            explanationAr = "هذا فارق دلالي جوهري: scheinbar = خادع لا حقيقة له، بينما anscheinend = يبدو صادقاً وفق الأدلة."
        ),
        ExamQuestion(
            id = 85,
            questionAr = "ما المعنى الدقيق لمصطلح 'höhere Gewalt' في قانون العقود الألماني؟",
            questionEn = "المصطلحات القانونية المعتمدة",
            optionsAr = listOf("القوة القاهرة (Force Majeure)", "سلطة المحكمة العليا", "غرامة التأخير المالية", "الشرط الجزائي الرادع"),
            correctIndex = 0,
            explanationAr = "höhere Gewalt هي الترجمة الألمانية القانونية المعتمدة للقوة القاهرة غير المتوقعة (Force Majeure)."
        ),
        ExamQuestion(
            id = 86,
            questionAr = "ما المقصود بـ 'Funktionsverbgefüge' في النحو الألماني المتقدم؟",
            questionEn = "تراكيب الأفعال الوظيفية",
            optionsAr = listOf("تركيب يجمع اسماً مجروراً مع فعل فارغ الدلالة يعبران معاً عن معنى فعلي موحد", "فعل مركب من ثلاث كلمات لا تصرف", "أفعال لا تستخدم إلا مع الحيوانات", "أفعال منقرضة من العصور الوسطى"),
            correctIndex = 0,
            explanationAr = "هي تراكيب تجمع اسماً مع فعل وظيفي مثل: in Gang setzen (يشغل) أو zur Verfügung stehen (يكون متاحاً)."
        ),
        ExamQuestion(
            id = 87,
            questionAr = "ما هو نفي التركيب 'sowohl ... als auch' في الصياغة المتقدمة؟",
            questionEn = "الروابط البلاغية",
            optionsAr = listOf("weder ... noch", "nicht nur ... sondern auch", "entweder ... oder", "zwar ... nicht"),
            correctIndex = 0,
            explanationAr = "نفي التماثل المزدوج يكون حصراً عبر: weder ... noch (لا هذا ولا ذاك)."
        ),
        ExamQuestion(
            id = 88,
            questionAr = "ما وظيفة الظرف 'zwar' عندما يقترن بـ 'aber'؟",
            questionEn = "روابط التنازل والاستدراك",
            optionsAr = listOf("التنازل والإقرار الجزئي بنقطة معينة قبل تقديم الاستدراك المعارض", "التأكيد المطلق على رأي واحد", "نفي الجملتين معاً", "الربط الزمني البحت"),
            correctIndex = 0,
            explanationAr = "zwar تفيد التنازل والاعتراف بصحة الجزء الأول تمهيداً للاستدراك الجوهري بـ aber."
        ),
        ExamQuestion(
            id = 89,
            questionAr = "ما المعنى الدقيق لكلمة 'die Verhältnismäßigkeit' في القانون الدستوري والإداري الألماني؟",
            questionEn = "المصطلحات الدستورية",
            optionsAr = listOf("مبدأ التناسب وملاءمة التدبير المتخذ للهدف المنشود", "نسبة التصويت في البرلمان", "توزيع الميزانية العامة", "تفاضل الرواتب الشهرية"),
            correctIndex = 0,
            explanationAr = "مبدأ التناسب (Verhältnismäßigkeitsprinzip) من أسمى المبادئ الدستورية لتقييد سلطة الدولة."
        ),
        ExamQuestion(
            id = 90,
            questionAr = "اختر الصيغة الصحيحة للجملة التنازلية المتقدمة: 'So schwer die Aufgabe auch sein mag, ___'",
            questionEn = "الجمل التنازلية المتقدمة C1",
            optionsAr = listOf("wir werden sie lösen.", "werden wir sie lösen.", "wir sie lösen werden.", "dass wir sie lösen."),
            correctIndex = 0,
            explanationAr = "بعد التراكيب التنازلية المصدرة بـ So ... auch تبدأ الجملة الرئيسية بالفاعل ثم الفعل في الأسلوب الأدبي الحديث."
        ),
        ExamQuestion(
            id = 91,
            questionAr = "ما معنى اللاحقة '-widrig' عند اتصالها بالأسماء (مثل rechtswidrig, verfassungswidrig)؟",
            questionEn = "اشتقاق المصطلحات القانونية",
            optionsAr = listOf("مخالف أو منافٍ لـ (مخالف للقانون، غير دستوري)", "مؤيد وموافق لـ", "مشتق من أصل تاريخي", "قابل للتعديل بسهولة"),
            correctIndex = 0,
            explanationAr = "اللاحقة -widrig تفيد المخالفة والمنافاة (مخالف للقانون، منافٍ للأخلاق)."
        ),
        ExamQuestion(
            id = 92,
            questionAr = "ما التركيب الاسمي المعياري الذي يعني 'يعرب عن شكره وامتنانه'؟",
            questionEn = "الأسلوب الرسمي الراقي",
            optionsAr = listOf("seinen Dank zum Ausdruck bringen", "seinen Dank machen", "seinen Dank finden", "seinen Dank stehen"),
            correctIndex = 0,
            explanationAr = "الصياغة الوظيفية الفصحى: seinen Dank zum Ausdruck bringen."
        ),
        ExamQuestion(
            id = 93,
            questionAr = "ما المعنى الدقيق لفعل 'kündigen' عند اقترانه بعقد عمل أو إيجار؟",
            questionEn = "المصطلحات التعاقدية",
            optionsAr = listOf("يفسخ العقد أو ينهي العلاقة التعاقدية بإخطار رسمي", "يجدد العقد تلقائياً", "يرفع قيمة العقد المالية", "يترجم العقد إلى لغة أجنبية"),
            correctIndex = 0,
            explanationAr = "الفعل kündigen يعني فسخ أو إنهاء العقد بصورة نظامية بعد إرسال الإشعار القانوني."
        ),
        ExamQuestion(
            id = 94,
            questionAr = "ما المعنى الدقيق لعبارة 'Gefahr im Verzug' في الإجراءات الجنائية الألمانية؟",
            questionEn = "المصطلحات القضائية والجنائية",
            optionsAr = listOf("خطر وشيك ناجم عن أي تأخير يبرر التدخل الفوري دون إذن قضائي مسبق", "خطر زال بالكامل", "حالة تأخير في دفع الغرامة", "تأجيل جلسة المحاكمة"),
            correctIndex = 0,
            explanationAr = "مصطلح قانوني شهير يبرر تصرف الشرطة الفوري في حالات الطوارئ القصوى خشية ضياع الأدلة."
        ),
        ExamQuestion(
            id = 95,
            questionAr = "ما الفرق بين 'der Ertrag' و 'der Umsatz' في المحاسبة والمالية الألمانية؟",
            questionEn = "المصطلحات الاقتصادية والمالية",
            optionsAr = listOf("Umsatz هو إجمالي حجم المبيعات والإيرادات، بينما Ertrag هو العائد والقيمة المكتسبة الشاملة", "كلاهما يعني الخسارة الفادحة", "Umsatz للديون و Ertrag للضرائب", "لا فرق بينهما بتاتاً"),
            correctIndex = 0,
            explanationAr = "Umsatz يعبر عن حجم المبيعات النقدية الخام، بينما Ertrag يشمل كافة العوائد والأرباح الناتجة عن النشاط."
        ),
        ExamQuestion(
            id = 96,
            questionAr = "ما معنى صيغة النعت المسبوق بـ 'zu' (Gerundiv): 'die zu lösenden Probleme'؟",
            questionEn = "صيغة Gerundiv المتقدمة C2",
            optionsAr = listOf("المشكلات التي يجب أو ينبغي حلها (ضرورة كامنة)", "المشكلات التي حُلت في الماضي", "المشكلات التي يستحيل حلها", "المشكلات التي لا تهم أحداً"),
            correctIndex = 0,
            explanationAr = "تركيب d + zu + Partizip I يعمل كصفة تفيد وجوب الفعل: die gelöst werden müssen."
        ),
        ExamQuestion(
            id = 97,
            questionAr = "اختر الحرف المناسب للتعبير عن السبب مع الاسم في سياق الامتنان: '___ Ihrer Hilfe haben wir gesiegt.'",
            questionEn = "حروف الجر المتقدمة",
            optionsAr = listOf("Dank (+ Genitiv oder Dativ)", "Wegen", "Trotz", "Anstatt"),
            correctIndex = 0,
            explanationAr = "حرف الجر Dank يعبر عن السبب الإيجابي (بفضل مساعدتكم / nhờ vào)."
        ),
        ExamQuestion(
            id = 98,
            questionAr = "ما المقابل الألماني الدقيق للمصطلح الدبلوماسي 'Persona non grata'؟",
            questionEn = "المصطلحات الدبلوماسية",
            optionsAr = listOf("Unerwünschte Person", "Diplomatischer Vertreter", "Botschaftsrat", "Bevollmächtigter Minister"),
            correctIndex = 0,
            explanationAr = "الترجمة الرسمية المعتمدة لـ Persona non grata بالألمانية هي unerwünschte Person (شخص غير مرغوب فيه)."
        ),
        ExamQuestion(
            id = 99,
            questionAr = "ما التركيب الذي يفيد 'وضع شيئاً ما قيد المناقشة والمداولة'؟",
            questionEn = "تراكيب الأفعال الوظيفية C1",
            optionsAr = listOf("zur Debatte / Diskussion stellen", "in Diskussion sein", "aus Debatte fallen", "an Diskussion gehen"),
            correctIndex = 0,
            explanationAr = "الصياغة المعتمدة: etwas zur Debatte stellen (يطرح موضوعاً للنقاش العام)."
        ),
        ExamQuestion(
            id = 100,
            questionAr = "ما المعنى الدقيق لعبارة 'Im Zweifelsfall' في القرارات القانونية والتنظيمية؟",
            questionEn = "المصطلحات القانونية",
            optionsAr = listOf("عند الشك والتردد (يُفسر لصالح المتهم أو الأضعف)", "في حالة اليقين المطلق", "عند ثبوت الإدانة قطيعة", "بعد صدور الحكم النهائي"),
            correctIndex = 0,
            explanationAr = "تطبيق للقاعدة اللاتينية الشهيرة 'In dubio pro reo': في حال الشك يُقضى لصالح الأصل."
        )
    )

    // TCF TRACK (100 QUESTIONS)
    val tcfQuestions = listOf(
        ExamQuestion(
            id = 1,
            questionAr = "Choisissez l'article défini contracté correct: 'Je vais ___ cinéma ce soir.'",
            questionEn = "Articles contractés",
            optionsAr = listOf("au", "à le", "du", "en"),
            correctIndex = 0,
            explanationAr = "La préposition 'à' fusionne avec l'article masculin 'le' pour former 'au' (à + le = au)."
        ),
        ExamQuestion(
            id = 2,
            questionAr = "Quelle est la conjugaison correcte du verbe 'avoir' avec 'nous' au présent?",
            questionEn = "Conjugaison du présent",
            optionsAr = listOf("avons", "avez", "ont", "sommes"),
            correctIndex = 0,
            explanationAr = "Au présent de l'indicatif, le verbe avoir se conjugue: nous avons."
        ),
        ExamQuestion(
            id = 3,
            questionAr = "Choisissez la préposition correcte pour un pays féminin: 'Elle habite ___ France.'",
            questionEn = "Prépositions géographiques",
            optionsAr = listOf("en", "au", "à la", "dans"),
            correctIndex = 0,
            explanationAr = "Devant les noms de pays féminins se terminant par un 'e', on utilise la préposition 'en'."
        ),
        ExamQuestion(
            id = 4,
            questionAr = "Quelle est la forme du féminin de l'adjectif 'heureux'?",
            questionEn = "Féminin des adjectifs",
            optionsAr = listOf("heureuse", "heureusse", "heureux", "heureue"),
            correctIndex = 0,
            explanationAr = "Les adjectifs masculins se terminant par -eux font leur féminin en -euse."
        ),
        ExamQuestion(
            id = 5,
            questionAr = "Quel auxiliaire utilise-t-on au passé composé pour le verbe 'aller'?",
            questionEn = "Passé composé",
            optionsAr = listOf("être (est allé)", "avoir (a allé)", "faire (fait allé)", "aller (va aller)"),
            correctIndex = 0,
            explanationAr = "Le verbe 'aller' fait partie des verbes de mouvement qui se conjuguent avec l'auxiliaire être."
        ),
        ExamQuestion(
            id = 6,
            questionAr = "Choisissez l'accord correct du participe passé: 'Les lettres que j'ai ___.'",
            questionEn = "Accord du participe passé",
            optionsAr = listOf("écrites", "écrit", "écrite", "écrits"),
            correctIndex = 0,
            explanationAr = "Avec l'auxiliaire avoir, le participe passé s'accorde avec le COD placé avant (les lettres = fém. pluriel)."
        ),
        ExamQuestion(
            id = 7,
            questionAr = "Quelle phrase exprime correctement la négation au présent?",
            questionEn = "La négation",
            optionsAr = listOf("Il ne mange pas de viande.", "Il ne mange point la viande pas.", "Il mange ne pas de viande.", "Il pas mange de viande."),
            correctIndex = 0,
            explanationAr = "La structure de négation standard entoure le verbe conjugué: ne + verbe + pas."
        ),
        ExamQuestion(
            id = 8,
            questionAr = "Quel est le pronom personnel COD remplaçant 'le livre': 'Tu lis le livre? - Oui, je ___ lis.'",
            questionEn = "Pronoms COD",
            optionsAr = listOf("le", "lui", "en", "y"),
            correctIndex = 0,
            explanationAr = "Le nom masculin singulier 'le livre' est un COD direct remplacé par le pronom 'le'."
        ),
        ExamQuestion(
            id = 9,
            questionAr = "Quel est le pronom possessif correct: 'C'est la voiture de Marie. C'est ___ voiture.'",
            questionEn = "Adjectifs possessifs",
            optionsAr = listOf("sa", "son", "ses", "leur"),
            correctIndex = 0,
            explanationAr = "Devant un nom féminin singulier commençant par une consonne, on utilise 'sa'."
        ),
        ExamQuestion(
            id = 10,
            questionAr = "Quelle est l'interrogation correcte avec inversion du sujet?",
            questionEn = "Structure interrogative",
            optionsAr = listOf("Parlez-vous français?", "Vous parlez français-vous?", "Est-ce vous parlez français?", "Parlez vous-le français?"),
            correctIndex = 0,
            explanationAr = "L'inversion formelle du sujet place le verbe suivi d'un trait d'union et du pronom personnel."
        ),
        ExamQuestion(
            id = 11,
            questionAr = "Choisissez l'adjectif démonstratif devant un mot masculin débutant par une voyelle: '___ arbre est grand.'",
            questionEn = "Adjectifs démonstratifs",
            optionsAr = listOf("Cet", "Ce", "Cette", "Ces"),
            correctIndex = 0,
            explanationAr = "Devant un nom masculin commençant par une voyelle ou un h muet, on emploie 'cet'."
        ),
        ExamQuestion(
            id = 12,
            questionAr = "Quelle est la forme correcte du futur proche pour 'partir' avec 'ils'?",
            questionEn = "Futur proche",
            optionsAr = listOf("ils vont partir", "ils partent", "ils partiront", "ils sont partis"),
            correctIndex = 0,
            explanationAr = "Le futur proche se forme avec le verbe aller au présent + infinitif (vont partir)."
        ),
        ExamQuestion(
            id = 13,
            questionAr = "Quel est le pluriel du mot 'le journal'?",
            questionEn = "Pluriel des noms en -al",
            optionsAr = listOf("les journaux", "les journals", "les journau", "les journauxes"),
            correctIndex = 0,
            explanationAr = "La plupart des noms masculins en -al forment leur pluriel en -aux."
        ),
        ExamQuestion(
            id = 14,
            questionAr = "Choisissez la préposition d'heure correcte: 'Le train part ___ 14 heures.'",
            questionEn = "Prépositions temporelles",
            optionsAr = listOf("à", "en", "dans", "vers"),
            correctIndex = 0,
            explanationAr = "On utilise la préposition 'à' pour indiquer une heure précise."
        ),
        ExamQuestion(
            id = 15,
            questionAr = "Quelle est la forme correcte de l'impératif pour 'prendre' avec 'tu'?",
            questionEn = "Mode impératif",
            optionsAr = listOf("Prends!", "Prend!", "Prennes!", "Prenez!"),
            correctIndex = 0,
            explanationAr = "À la 2e personne du singulier des verbes du 3e groupe en -dre, la terminaison conserve le -s (Prends)."
        ),
        ExamQuestion(
            id = 16,
            questionAr = "Comment dit-on 'yesterday' en français?",
            questionEn = "Vocabulaire temporel",
            optionsAr = listOf("hier", "demain", "aujourd'hui", "maintenant"),
            correctIndex = 0,
            explanationAr = "'Hier' signifie le jour précédant aujourd'hui."
        ),
        ExamQuestion(
            id = 17,
            questionAr = "Quel est le contraire de 'toujours' dans une phrase négative?",
            questionEn = "Négation complexe",
            optionsAr = listOf("ne ... jamais", "ne ... rien", "ne ... plus", "ne ... personne"),
            correctIndex = 0,
            explanationAr = "L'antonyme négatif de 'toujours' ou 'souvent' est 'ne ... jamais'."
        ),
        ExamQuestion(
            id = 18,
            questionAr = "Choisissez la préposition correcte: 'Je viens ___ Algérie.'",
            questionEn = "Prépositions de provenance",
            optionsAr = listOf("d'", "de l'", "du", "en"),
            correctIndex = 0,
            explanationAr = "Devant un nom de pays féminin commençant par une voyelle, la provenance s'exprime par 'd''."
        ),
        ExamQuestion(
            id = 19,
            questionAr = "Quelle phrase exprime une obligation impersonnelle?",
            questionEn = "Obligation impersonnelle",
            optionsAr = listOf("Il faut étudier sérieusement.", "Je dois étudier.", "Tu étudies.", "Nous voulons étudier."),
            correctIndex = 0,
            explanationAr = "'Il faut' + infinitif exprime une obligation générale et impersonnelle."
        ),
        ExamQuestion(
            id = 20,
            questionAr = "Quel est le participe passé du verbe 'faire'?",
            questionEn = "Participe passé",
            optionsAr = listOf("fait", "fais", "faisant", "faites"),
            correctIndex = 0,
            explanationAr = "Le participe passé du verbe irrégulier faire est 'fait'."
        ),
        ExamQuestion(
            id = 21,
            questionAr = "Choisissez le pronom tonique convenable: 'Ce cadeau est pour ___ (je).' ",
            questionEn = "Pronoms toniques",
            optionsAr = listOf("moi", "je", "me", "m'"),
            correctIndex = 0,
            explanationAr = "Après une préposition (pour, avec, sans), on emploie les pronoms toniques (moi, toi, lui...)."
        ),
        ExamQuestion(
            id = 22,
            questionAr = "Quelle forme exprime la durée terminée dans le passé?",
            questionEn = "Expressions de temps",
            optionsAr = listOf("pendant 3 ans", "depuis 3 ans", "en 3 ans", "dans 3 ans"),
            correctIndex = 0,
            explanationAr = "'Pendant' indique une durée complètement délimitée et achevée dans le passé."
        ),
        ExamQuestion(
            id = 23,
            questionAr = "Quelle expression exprime une action continue dans le présent qui a débuté dans le passé?",
            questionEn = "Expressions de temps",
            optionsAr = listOf("depuis deux ans", "pendant deux ans", "pour deux ans", "dans deux ans"),
            correctIndex = 0,
            explanationAr = "'Depuis' indique une action qui a commencé dans le passé et se poursuit dans le présent."
        ),
        ExamQuestion(
            id = 24,
            questionAr = "Quel est le comparatif de supériorité de l'adjectif 'bon'?",
            questionEn = "Comparatifs irréguliers",
            optionsAr = listOf("meilleur", "plus bon", "mieux", "le bon"),
            correctIndex = 0,
            explanationAr = "Le comparatif de supériorité de l'adjectif 'bon' est l'adjectif irrégulier 'meilleur'."
        ),
        ExamQuestion(
            id = 25,
            questionAr = "Quelle est la terminaison de l'imparfait pour 'ils / elles'?",
            questionEn = "Imparfait de l'indicatif",
            optionsAr = listOf("-aient", "-aientent", "-ont", "-èrent"),
            correctIndex = 0,
            explanationAr = "La désinence de l'imparfait pour la 3e personne du pluriel est invariablement -aient."
        ),
        ExamQuestion(
            id = 26,
            questionAr = "Quelle est la valeur de l'imparfait dans: 'Quand j'étais petit, je lisais tous les soirs'?",
            questionEn = "Valeurs des temps du passé",
            optionsAr = listOf("L'habitude et la répétition dans le passé", "Une action soudaine et ponctuelle", "Une action future certaine", "Un ordre impératif"),
            correctIndex = 0,
            explanationAr = "L'imparfait est employé pour décrire les habitudes, routines et décors d'arrière-plan dans le passé."
        ),
        ExamQuestion(
            id = 27,
            questionAr = "Complétez avec le pronom COI: 'Je téléphone à mon père' -> 'Je ___ téléphone.'",
            questionEn = "Pronoms COI",
            optionsAr = listOf("lui", "le", "en", "y"),
            correctIndex = 0,
            explanationAr = "Le COI singulier désignant une personne introduit par 'à' est remplacé par 'lui'."
        ),
        ExamQuestion(
            id = 28,
            questionAr = "Complétez avec le pronom 'en': 'Tu as des stylos? - Oui, j'___ ai trois.'",
            questionEn = "Le pronom en",
            optionsAr = listOf("en", "y", "les", "leur"),
            correctIndex = 0,
            explanationAr = "Le pronom 'en' remplace les compléments introduits par un article partitif, indéfini ou une quantité."
        ),
        ExamQuestion(
            id = 29,
            questionAr = "Complétez avec le pronom 'y': 'Tu penses à ton avenir? - Oui, j'___ pense souvent.'",
            questionEn = "Le pronom y",
            optionsAr = listOf("y", "en", "lui", "le"),
            correctIndex = 0,
            explanationAr = "Le pronom 'y' remplace un complément introduit par 'à' désignant une idée, une chose ou un lieu."
        ),
        ExamQuestion(
            id = 30,
            questionAr = "Choisissez le pronom relatif approprié: 'Voici l'ami ___ m'a aidé.'",
            questionEn = "Pronoms relatifs simples",
            optionsAr = listOf("qui", "que", "dont", "où"),
            correctIndex = 0,
            explanationAr = "'Qui' remplace le sujet du verbe subordonné (m'a aidé)."
        ),
        ExamQuestion(
            id = 31,
            questionAr = "Choisissez le pronom relatif approprié: 'Le livre ___ tu m'as recommandé est passionnant.'",
            questionEn = "Pronoms relatifs simples",
            optionsAr = listOf("que", "qui", "dont", "où"),
            correctIndex = 0,
            explanationAr = "'Que' remplace le complément d'objet direct (tu m'as recommandé quoi? -> le livre)."
        ),
        ExamQuestion(
            id = 32,
            questionAr = "Choisissez le pronom relatif pour un complément avec 'de': 'C'est le projet ___ nous parlions hier.'",
            questionEn = "Pronoms relatifs",
            optionsAr = listOf("dont", "que", "qui", "auquel"),
            correctIndex = 0,
            explanationAr = "'Dont' remplace un complément introduit par la préposition 'de' (parler de)."
        ),
        ExamQuestion(
            id = 33,
            questionAr = "Complétez l'hypothèse au présent: 'Si j'avais de l'argent, j'___ un voyage.'",
            questionEn = "Hypothèse avec Si",
            optionsAr = listOf("achèterais (conditionnel)", "achète (présent)", "achèterai (futur)", "avais acheté"),
            correctIndex = 0,
            explanationAr = "La structure de l'hypothèse réalisable ou imaginaire au présent est: Si + imparfait -> conditionnel présent."
        ),
        ExamQuestion(
            id = 34,
            questionAr = "Quel mode verbal suit obligatoirement la locution conjonctive 'bien que'?",
            questionEn = "Emploi du subjonctif",
            optionsAr = listOf("Le subjonctif", "L'indicatif", "Le conditionnel", "L'infinitif"),
            correctIndex = 0,
            explanationAr = "'Bien que' exprime la concession et exige impérativement le mode subjonctif."
        ),
        ExamQuestion(
            id = 35,
            questionAr = "Conjuguez au subjonctif présent: 'Il faut que vous ___ (savoir) la vérité.'",
            questionEn = "Subjonctif présent",
            optionsAr = listOf("sachiez", "savez", "sacherez", "suiviez"),
            correctIndex = 0,
            explanationAr = "Le verbe savoir a un radical irrégulier au subjonctif: que vous sachiez."
        ),
        ExamQuestion(
            id = 36,
            questionAr = "Conjuguez au subjonctif présent: 'Je souhaite qu'il ___ (venir) demain.'",
            questionEn = "Subjonctif présent",
            optionsAr = listOf("vienne", "vient", "venira", "voulût"),
            correctIndex = 0,
            explanationAr = "Le verbe venir fait: qu'il vienne au subjonctif présent."
        ),
        ExamQuestion(
            id = 37,
            questionAr = "Transformez au discours indirect: Il m'a dit: 'Je suis fatigué.' -> Il m'a dit qu'il ___ fatigué.",
            questionEn = "Concordance des temps",
            optionsAr = listOf("était (imparfait)", "est (présent)", "serait (conditionnel)", "fut"),
            correctIndex = 0,
            explanationAr = "Quand le verbe introducteur est au passé, le présent du discours direct devient imparfait au discours indirect."
        ),
        ExamQuestion(
            id = 38,
            questionAr = "Quelle phrase exprime une cause neutre et objective?",
            questionEn = "L'expression de la cause",
            optionsAr = listOf("Comme il pleut, le match est annulé.", "Grâce à la pluie le match est annulé.", "Pourtant il pleut.", "Bien qu'il pleuve."),
            correctIndex = 0,
            explanationAr = "'Comme' placé en tête de phrase exprime une relation de cause à effet évidente et constatée."
        ),
        ExamQuestion(
            id = 39,
            questionAr = "Quelle locution exprime une cause positive avec gratitude?",
            questionEn = "L'expression de la cause",
            optionsAr = listOf("Grâce à", "À cause de", "Faute de", "Sous prétexte de"),
            correctIndex = 0,
            explanationAr = "'Grâce à' s'emploie exclusivement pour une cause produisant un résultat bénéfique."
        ),
        ExamQuestion(
            id = 40,
            questionAr = "Quelle locution prépositionnelle exprime le but?",
            questionEn = "L'expression du but",
            optionsAr = listOf("Afin de", "À cause de", "Parce que", "Malgré"),
            correctIndex = 0,
            explanationAr = "'Afin de' suivi de l'infinitif marque l'intention, le dessein et la finalité."
        ),
        ExamQuestion(
            id = 41,
            questionAr = "Quel est le superlatif de supériorité de l'adverbe 'bien'?",
            questionEn = "Adverbes comparatifs",
            optionsAr = listOf("le mieux", "le meilleur", "le plus bien", "mieux"),
            correctIndex = 0,
            explanationAr = "L'adverbe 'bien' a pour superlatif irrégulier 'le mieux' (à ne pas confondre avec l'adjectif bon/le meilleur)."
        ),
        ExamQuestion(
            id = 42,
            questionAr = "Quelle est la forme correcte de la voix passive: 'Le comité approuve la décision'?",
            questionEn = "La voix passive",
            optionsAr = listOf("La décision est approuvée par le comité.", "La décision était approuvée par le comité.", "La décision sera approuvé.", "Le comité est approuvé par la décision."),
            correctIndex = 0,
            explanationAr = "Au présent passif: auxiliaire être au présent + participe passé accordé avec le sujet passif féminin."
        ),
        ExamQuestion(
            id = 43,
            questionAr = "Quel temps exprime une action antérieure à une autre action passée?",
            questionEn = "Plus-que-parfait",
            optionsAr = listOf("Le plus-que-parfait", "Le passé composé", "L'imparfait", "Le futur antérieur"),
            correctIndex = 0,
            explanationAr = "Le plus-que-parfait exprime l'antériorité temporelle dans le passé (avait fini avant de partir)."
        ),
        ExamQuestion(
            id = 44,
            questionAr = "Choisissez la forme convenable: 'Nous nous sommes ___ (rencontrer) à Paris.'",
            questionEn = "Accord des verbes pronominaux",
            optionsAr = listOf("rencontrés", "rencontré", "rencontrée", "rencontrer"),
            correctIndex = 0,
            explanationAr = "Verbe pronominel réciproque avec COD placé avant (nous = COD pluriel masculin)."
        ),
        ExamQuestion(
            id = 45,
            questionAr = "Quelle locution conjonctive exprime la conséquence?",
            questionEn = "Expression de la conséquence",
            optionsAr = listOf("Par conséquent", "Pourtant", "Tandis que", "Puisque"),
            correctIndex = 0,
            explanationAr = "'Par conséquent' introduit logiquement la conséquence directe d'une proposition."
        ),
        ExamQuestion(
            id = 46,
            questionAr = "Quel est le sens du verbe pronominal passif: 'Ce vin se boit très frais'?",
            questionEn = "Sens passif des pronominaux",
            optionsAr = listOf("On doit boire ce vin très frais", "Ce vin boit lui-même", "Le vin est alcoolisé", "Ce vin est frais"),
            correctIndex = 0,
            explanationAr = "La forme pronominale à valeur passive remplace une tournure passive impersonnelle."
        ),
        ExamQuestion(
            id = 47,
            questionAr = "Comment exprime-t-on la condition sans 'si' avec le conditionnel?",
            questionEn = "Condition et hypothèse",
            optionsAr = listOf("Au cas où vous auriez besoin d'aide", "À condition que vous veniez", "Pourvu que", "Sans doute"),
            correctIndex = 0,
            explanationAr = "'Au cas où' est obligatoirement suivi du mode conditionnel."
        ),
        ExamQuestion(
            id = 48,
            questionAr = "Quelle est la nuance de l'adjectif avant le nom: 'un grand homme' vs 'un homme grand'?",
            questionEn = "Place des adjectifs et nuances",
            optionsAr = listOf("'un grand homme' = illustre/célèbre; 'un homme grand' = de haute taille", "Aucune différence de sens", "Les deux signifient très vieux", "Le premier indique la taille physique"),
            correctIndex = 0,
            explanationAr = "Placé avant le nom, 'grand' a un sens figuré moral ou historique; après le nom, il indique la taille physique."
        ),
        ExamQuestion(
            id = 49,
            questionAr = "Quelle préposition introduit la matière d'un objet?",
            questionEn = "Prépositions de matière",
            optionsAr = listOf("en (en soie, en bois)", "à", "vers", "par"),
            correctIndex = 0,
            explanationAr = "La préposition 'en' désigne traditionnellement la matière constitutive (une table en chêne)."
        ),
        ExamQuestion(
            id = 50,
            questionAr = "Quel connecteur exprime l'opposition pure entre deux faits concomitants?",
            questionEn = "Opposition",
            optionsAr = listOf("Tandis que", "Puisque", "Afin que", "Parce que"),
            correctIndex = 0,
            explanationAr = "'Tandis que' ou 'alors que' soulignent le contraste simultané entre deux situations parallèles."
        ),
        ExamQuestion(
            id = 51,
            questionAr = "Quelle proposition comporte un pronom relatif composé correct?",
            questionEn = "Pronoms relatifs composés",
            optionsAr = listOf("L'entreprise à laquelle j'ai postulé est internationale.", "L'entreprise auquel j'ai postulé.", "L'entreprise de laquelle je parle qui.", "L'entreprise avec lequel je travaille."),
            correctIndex = 0,
            explanationAr = "Le pronom relatif composé s'accorde en genre et en nombre avec son antécédent (entreprise = fém. sing -> à laquelle)."
        ),
        ExamQuestion(
            id = 52,
            questionAr = "Quel est le pronom relatif composé contracté masculin singulier avec 'de'?",
            questionEn = "Pronoms relatifs composés",
            optionsAr = listOf("duquel", "auquel", "lequel", "desquels"),
            correctIndex = 0,
            explanationAr = "La contraction de 'de + lequel' donne 'duquel' (le problème au sujet duquel nous débattons)."
        ),
        ExamQuestion(
            id = 53,
            questionAr = "Complétez avec le subjonctif passé: 'Je doute qu'il ___ (terminer) son rapport à temps.'",
            questionEn = "Subjonctif passé",
            optionsAr = listOf("ait terminé", "a terminé", "aura terminé", "avait terminé"),
            correctIndex = 0,
            explanationAr = "Le subjonctif passé se compose de l'auxiliaire avoir au subjonctif présent (ait) + participe passé."
        ),
        ExamQuestion(
            id = 54,
            questionAr = "Identifiez la forme correcte du gérondif exprimant la manière ou la simultanéité:",
            questionEn = "Le gérondif",
            optionsAr = listOf("en travaillant régulièrement", "pour travailler régulièrement", "travaillé régulièrement", "à travailler régulièrement"),
            correctIndex = 0,
            explanationAr = "Le gérondif se forme avec la préposition 'en' suivie du participe présent en -ant."
        ),
        ExamQuestion(
            id = 55,
            questionAr = "Quelle phrase illustre un accord erroné du participe passé d'un verbe pronominal?",
            questionEn = "Accord des participes pronominaux",
            optionsAr = listOf("Elles se sont téléphonées hier. (Erreur: se est COI, téléphoner à)", "Elles se sont vues au musée.", "Elles se sont lavé les mains.", "Elles se sont rencontrées."),
            correctIndex = 0,
            explanationAr = "On téléphone 'à' quelqu'un; le pronom 'se' est donc COI: le participe passé reste invariable (téléphoné)."
        ),
        ExamQuestion(
            id = 56,
            questionAr = "Complétez la concordance des temps: 'S'ils avaient suivi nos conseils, ils n'___ pas de difficultés.'",
            questionEn = "Conditionnel passé et hypothèse",
            optionsAr = listOf("auraient pas eu (cond. passé)", "ont pas eu", "avaient pas eu", "auront pas eu"),
            correctIndex = 0,
            explanationAr = "Si + plus-que-parfait exige dans l'apodose le conditionnel passé (action non réalisée dans le passé)."
        ),
        ExamQuestion(
            id = 57,
            questionAr = "Quel connecteur concessif exige l'indicatif contrairement à 'bien que'?",
            questionEn = "Concession et opposition",
            optionsAr = listOf("Même si", "Quoique", "Encore que", "Pour peu que"),
            correctIndex = 0,
            explanationAr = "'Même si' exprime l'opposition ou la concession mais commande rigoureusement le mode indicatif."
        ),
        ExamQuestion(
            id = 58,
            questionAr = "Quelle tournure permet d'éviter la voix passive lourde en français soutenu?",
            questionEn = "Tournures stylistiques",
            optionsAr = listOf("L'emploi du pronom indéfini 'on' ou la forme pronominale", "L'ajout de trois adjectifs qualificatifs", "La répétition du sujet", "L'emploi exclusif du subjonctif"),
            correctIndex = 0,
            explanationAr = "Le français privilégie les tournures actives avec 'on' ou pronominales passives pour plus d'élégance et de fluidité."
        ),
        ExamQuestion(
            id = 59,
            questionAr = "Quelle est la valeur temporelle du futur antérieur?",
            questionEn = "Futur antérieur",
            optionsAr = listOf("L'antériorité par rapport à un moment ou une action future", "Une incertitude passée", "Un souhait irréalisable", "Une habitude présente"),
            correctIndex = 0,
            explanationAr = "Le futur antérieur exprime une action qui sera achevée avant une autre action future (dès que j'aurai fini, je partirai)."
        ),
        ExamQuestion(
            id = 60,
            questionAr = "Dans la correspondance administrative formelle, quelle formule clôture une lettre?",
            questionEn = "Formules de politesse épistolaire",
            optionsAr = listOf("Veuillez agréer, Madame, Monsieur, l'expression de mes salutations distinguées.", "À bientôt et merci.", "Je vous salue amicalement.", "Recevez mes meilleurs bisous."),
            correctIndex = 0,
            explanationAr = "C'est la formule protocolaire standard et soignée de la correspondance institutionnelle en langue française."
        ),
        ExamQuestion(
            id = 61,
            questionAr = "Quel temps verbal emploie-t-on après 'dès que' dans une phrase au passé?",
            questionEn = "Concordance temporelle",
            optionsAr = listOf("Le passé antérieur ou le plus-que-parfait", "Le subjonctif imparfait", "Le conditionnel présent", "Le présent de l'indicatif"),
            correctIndex = 0,
            explanationAr = "Après les conjonctions temporelles d'immédiateté au passé (dès que, aussitôt que), on emploie le passé antérieur."
        ),
        ExamQuestion(
            id = 62,
            questionAr = "Quel terme désigne la clause contractuelle dégageant les parties en cas d'événement imprévisible?",
            questionEn = "Droit des contrats et affaires",
            optionsAr = listOf("La force majeure", "La mise en demeure", "L'usufruit", "La nullité absolue"),
            correctIndex = 0,
            explanationAr = "La 'force majeure' est l'événement extérieur, imprévisible et irrésistible exonérant le débiteur."
        ),
        ExamQuestion(
            id = 63,
            questionAr = "Quelle est la signification de l'expression diplomatique 'statu quo ante'?",
            questionEn = "Locutions juridiques et diplomatiques",
            optionsAr = listOf("L'état des choses prévalant avant le conflit ou la modification", "Un nouvel accord préliminaire", "La rupture immédiate des relations", "Un cessez-le-feu permanent"),
            correctIndex = 0,
            explanationAr = "Expression latine consacrée désignant le rétablissement de la situation juridique antérieure."
        ),
        ExamQuestion(
            id = 64,
            questionAr = "Choisissez le connecteur logique marquant la transition argumentative:",
            questionEn = "Connecteurs logiques B2",
            optionsAr = listOf("D'ailleurs / En outre", "Parce que", "Pendant que", "De sorte à"),
            correctIndex = 0,
            explanationAr = "'En outre' ou 'de surcroît' introduisent un argument supplémentaire de même orientation."
        ),
        ExamQuestion(
            id = 65,
            questionAr = "Quelle est la nuance apportée par le subjonctif dans une proposition relative: 'Je cherche un traducteur qui ___ (connaître) le droit maritime.'?",
            questionEn = "Nuances du subjonctif",
            optionsAr = listOf("qui connaisse (l'existence du traducteur est incertaine ou hypothétique)", "qui connaît (fait certain avéré)", "qui connaîtra", "qui connaissait"),
            correctIndex = 0,
            explanationAr = "Le subjonctif dans une relative traduit une recherche idéale dont la réalité n'est pas garantie."
        ),
        ExamQuestion(
            id = 66,
            questionAr = "Quel adjectif verbal correspond au verbe 'différer'?",
            questionEn = "Adjectifs verbaux vs participes",
            optionsAr = listOf("différent (avec -ent)", "différant (participe présent)", "différemment", "différentiel"),
            correctIndex = 0,
            explanationAr = "L'adjectif verbal s'orthographie 'différent', tandis que le participe présent est 'différant'."
        ),
        ExamQuestion(
            id = 67,
            questionAr = "Dans un compte rendu professionnel, quel style est requis?",
            questionEn = "Style et registre professionnel",
            optionsAr = listOf("Un style objectif, concis et rigoureusement structuré", "Un style lyrique et imagé", "Un style familier direct", "Un style poétique en vers"),
            correctIndex = 0,
            explanationAr = "Le compte rendu institutionnel exige clarté synthétique, neutralité et précision terminologique."
        ),
        ExamQuestion(
            id = 68,
            questionAr = "Que signifie l'adverbe 'notamment' dans une énumération juridique?",
            questionEn = "Terminologie juridique",
            optionsAr = listOf("En particulier / entre autres (énumération non limitative)", "Exclusivement et uniquement", "Sans exception possible", "À l'exclusion de"),
            correctIndex = 0,
            explanationAr = "'Notamment' signale des exemples emblématiques au sein d'une catégorie sans exclure les autres éléments."
        ),
        ExamQuestion(
            id = 69,
            questionAr = "Quelle forme verbale traduit l'éventualité dans une clause résolutoire: 'Si le cocontractant ___ à ses obligations'?",
            questionEn = "Langage contractuel",
            optionsAr = listOf("venait à manquer", "manquera", "a manqué", "ayant manqué"),
            correctIndex = 0,
            explanationAr = "La tournure 'venir à + infinitif' exprime une éventualité redoutée ou hypothétique dans les contrats."
        ),
        ExamQuestion(
            id = 70,
            questionAr = "Quel terme désigne la compensation financière allouée par un tribunal en réparation d'un préjudice?",
            questionEn = "Vocabulaire judiciaire",
            optionsAr = listOf("Les dommages-intérêts", "Les honoraires", "Les dividendes", "La caution solidaire"),
            correctIndex = 0,
            explanationAr = "Les dommages-intérêts constituent la réparation indemnitaire pécuniaire due à la victime d'un préjudice."
        ),
        ExamQuestion(
            id = 71,
            questionAr = "Identifiez le pléonasme vicieux à proscrire dans un texte soutenu:",
            questionEn = "Correction de la langue",
            optionsAr = listOf("'monter en haut' ou 'collaborer ensemble'", "'travailler avec diligence'", "'agir en conformité'", "'rédiger une synthèse'"),
            correctIndex = 0,
            explanationAr = "'Monter en haut' et 'collaborer ensemble' sont des pléonasmes redondants contraires à l'économie de la langue."
        ),
        ExamQuestion(
            id = 72,
            questionAr = "Que signifie l'abréviation protocolaire 'S.E.' devant le nom d'un ambassadeur?",
            questionEn = "Protocole diplomatique",
            optionsAr = listOf("Son Excellence", "Sa Sainteté", "Secrétaire Exécutif", "Société Européenne"),
            correctIndex = 0,
            explanationAr = "'Son Excellence' est le prédicat protocolaire réservé aux ambassadeurs extraordinaires et plénipotentiaires."
        ),
        ExamQuestion(
            id = 73,
            questionAr = "Quelle préposition régit le verbe 'pallier' en bon usage?",
            questionEn = "Régime des verbes",
            optionsAr = listOf("Aucune: il est transitif direct (pallier une difficulté)", "à (pallier à)", "de (pallier de)", "sur (pallier sur)"),
            correctIndex = 0,
            explanationAr = "En français normatif soigné, 'pallier' est transitif direct: on pallie un manque (et non pallier à)."
        ),
        ExamQuestion(
            id = 74,
            questionAr = "Quelle est la nature du mot 'leur' dans: 'Ces documents, je leur ai transmis hier'?",
            questionEn = "Grammaire et syntaxe",
            optionsAr = listOf("Pronom personnel COI pluriel invariable", "Adjectif possessif", "Adverbe de lieu", "Pronom relatif"),
            correctIndex = 0,
            explanationAr = "En tant que pronom personnel COI remplaçant 'à eux/elles', 'leur' ne prend jamais de -s."
        ),
        ExamQuestion(
            id = 75,
            questionAr = "Quel temps traduit le futur dans le passé au discours indirect?",
            questionEn = "Concordance des temps",
            optionsAr = listOf("Le conditionnel présent", "Le futur simple", "L'imparfait", "Le subjonctif"),
            correctIndex = 0,
            explanationAr = "Le conditionnel présent joue le rôle fondamental de futur dans le passé par concordance temporelle."
        ),
        ExamQuestion(
            id = 76,
            questionAr = "Dans un traité multilatéral, que signifie la formule 'fait foi' dans la clause finale?",
            questionEn = "Droit des traités et langues officielles",
            optionsAr = listOf("Le texte dans cette langue fait autorité juridique souveraine en cas de divergence", "Le texte est simplement une traduction informative", "Le document est certifié par un notaire local", "Le texte expire dans 10 ans"),
            correctIndex = 0,
            explanationAr = "La clause d'authenticité linguistique établit quelle version linguistique a valeur juridique probante et officielle."
        ),
        ExamQuestion(
            id = 77,
            questionAr = "Quelle inversion du sujet est obligatoire en tête de phrase après un adverbe modalisateur?",
            questionEn = "Inversion stylistique C1",
            optionsAr = listOf("Peut-être a-t-il accepté la proposition.", "Peut-être il a accepté la proposition.", "A-t-il accepté peut-être la proposition.", "Il a peut-être accepté que oui."),
            correctIndex = 0,
            explanationAr = "En tête de phrase, les adverbes 'peut-être', 'à peine', 'sans doute', 'aussi' commandent l'inversion du sujet."
        ),
        ExamQuestion(
            id = 78,
            questionAr = "Quelle phrase illustre l'emploi du subjonctif imparfait dans un texte juridique ou littéraire classique?",
            questionEn = "Subjonctif imparfait C2",
            optionsAr = listOf("Quoiqu'il en coûtât à l'État, la mesure fut adoptée.", "Quoiqu'il en coûte à l'État.", "Bien qu'il en a coûté.", "Pourtant il en coûta."),
            correctIndex = 0,
            explanationAr = "Le subjonctif imparfait (coûtât) assure la concordance des temps au passé dans le registre juridique et classique."
        ),
        ExamQuestion(
            id = 79,
            questionAr = "Quel est l'accord correct du verbe avec le collectif 'la plupart' employé seul?",
            questionEn = "Accord des collectifs",
            optionsAr = listOf("La plupart ont voté pour la résolution (accord au pluriel).", "La plupart a voté pour la résolution.", "La plupart votait seul.", "La plupart ayant voté."),
            correctIndex = 0,
            explanationAr = "'La plupart' sans complément commande toujours l'accord au pluriel du verbe."
        ),
        ExamQuestion(
            id = 80,
            questionAr = "Dans la rédaction des résolutions de l'ONU, quel mode et temps caractérisent les paragraphes dispositifs?",
            questionEn = "Rédaction diplomatique internationale",
            optionsAr = listOf("Le présent de l'indicatif à la 3e personne singulier (Décide, Prie, Réaffirme)", "L'impératif direct (Faites, Décidez)", "Le conditionnel passé", "L'infinitif passé"),
            correctIndex = 0,
            explanationAr = "Le protocole onusien exige des verbes d'action au présent à la 3e personne du singulier: Décide, Déclare, Invite."
        ),
        ExamQuestion(
            id = 81,
            questionAr = "Que désigne le terme 'lettres de créance' (Letters of Credence) dans les relations interétatiques?",
            questionEn = "Droit diplomatique et consulaire",
            optionsAr = listOf("Le document officiel par lequel un chef d'État accrédite son ambassadeur auprès d'un autre chef d'État", "Une garantie bancaire internationale", "Un visa d'affaires prioritaire", "Un passeport de service"),
            correctIndex = 0,
            explanationAr = "Document solennel remis en audience officielle par l'ambassadeur au chef de l'État accréditaire."
        ),
        ExamQuestion(
            id = 82,
            questionAr = "Quelle figure de style consiste à affirmer une idée en niant son contraire ('Ce n'est pas mal' pour 'C'est excellent')?",
            questionEn = "Rhétorique et stylistique",
            optionsAr = listOf("La litote", "La métaphore", "L'hyperbole", "L'oxymore"),
            correctIndex = 0,
            explanationAr = "La litote est une figure de modération et d'atténuation classique très prisée dans la langue diplomatique."
        ),
        ExamQuestion(
            id = 83,
            questionAr = "Quel faux-ami anglais-français est fréquemment commis avec le mot 'actuellement'?",
            questionEn = "Faux-amis traductologiques",
            optionsAr = listOf("'Actuellement' signifie 'en ce moment' (at present), alors que l'anglais 'actually' signifie 'en fait / en réalité'", "'Actuellement' signifie 'effectivement'", "'Actuellement' n'existe pas en français", "Aucune différence entre les deux"),
            correctIndex = 0,
            explanationAr = "Confusion classique: 'actually' = en réalité/en fait, tandis qu''actuellement' = currently/at present."
        ),
        ExamQuestion(
            id = 84,
            questionAr = "Dans la norme ISO 17100:2015, que désigne précisément la phase de 'Révision' (Revision)?",
            questionEn = "Normes professionnelles ISO 17100",
            optionsAr = listOf("L'examen bilingue comparatif exhaustif du texte cible par rapport au texte source par un second linguiste", "La correction typographique superficielle", "La relecture par le client lui-même", "L'alignement automatique par logiciel TAO"),
            correctIndex = 0,
            explanationAr = "L'ISO 17100 définit la révision comme l'examen bilingue obligatoire effectué par un réviseur distinct du traducteur."
        ),
        ExamQuestion(
            id = 85,
            questionAr = "Quelle est la valeur de l'adjectif 'réfragable' dans le domaine de la preuve judiciaire?",
            questionEn = "Terminologie du droit probatoire",
            optionsAr = listOf("Une présomption qui peut être combattue par la preuve contraire", "Une présomption irrévocable et absolue", "Un jugement sans appel possible", "Un témoin irrecevable"),
            correctIndex = 0,
            explanationAr = "Une présomption réfragable admet la preuve contraire, par opposition à la présomption irréfragable."
        ),
        ExamQuestion(
            id = 86,
            questionAr = "Quel est le pluriel du mot composé 'un compte-rendu'?",
            questionEn = "Pluriel des mots composés",
            optionsAr = listOf("des comptes-rendus", "des compte-rendus", "des compte-rendu", "des comptes-rendu"),
            correctIndex = 0,
            explanationAr = "Composé d'un nom et d'un adjectif (participe), les deux éléments prennent la marque du pluriel: des comptes rendus."
        ),
        ExamQuestion(
            id = 87,
            questionAr = "Identifiez l'accord correct du participe passé dans: 'Les cinq minutes que cette épreuve a ___'?",
            questionEn = "Règles subtiles du participe passé",
            optionsAr = listOf("duré (invariable car verbe de mesure/durée sans COD)", "durée", "durées", "durés"),
            correctIndex = 0,
            explanationAr = "Les verbes exprimant la mesure, le prix, la durée (peser, coûter, durer, valoir) ne prennent pas d'accord avec ce complément."
        ),
        ExamQuestion(
            id = 88,
            questionAr = "Que signifie l'adverbe juridique 'nonobstant'?",
            questionEn = "Vocabulaire juridique d'ancien droit",
            optionsAr = listOf("Malgré / en dépit de", "Par conséquent", "Conformément à", "Pendant la durée de"),
            correctIndex = 0,
            explanationAr = "'Nonobstant' est une préposition ou adverbe soutenu signifiant en dépit de / sans égard à."
        ),
        ExamQuestion(
            id = 89,
            questionAr = "Quelle formule exprime une réserve solennelle lors d'une signature diplomatique?",
            questionEn = "Pratique des traités",
            optionsAr = listOf("Ad referendum (sous réserve d'approbation ultérieure par l'autorité souveraine)", "Ipso facto", "De jure", "In limine litis"),
            correctIndex = 0,
            explanationAr = "La signature 'ad referendum' conditionne la validité de l'engagement à la confirmation expresse du gouvernement."
        ),
        ExamQuestion(
            id = 90,
            questionAr = "Comment qualifie-t-on la traduction orale réalisée chuchotée à l'oreille d'un ou deux délégués?",
            questionEn = "Modes d'interprétation",
            optionsAr = listOf("Le chuchotage", "L'interprétation consécutive", "La traduction à vue", "Le relais de cabine"),
            correctIndex = 0,
            explanationAr = "Le chuchotage est une modalité de simultanée sans matériel acoustique lourd, réservée à un auditoire très restreint."
        ),
        ExamQuestion(
            id = 91,
            questionAr = "Quelle est l'antonyme soutenu et juridique de 'tacite'?",
            questionEn = "Antonymes spécialisés",
            optionsAr = listOf("Expresse / explicite", "Implicite", "Occulte", "Différée"),
            correctIndex = 0,
            explanationAr = "Ce qui est 'exprès' est formellement formulé, par opposition à ce qui est 'tacite' (induit du silence ou du comportement)."
        ),
        ExamQuestion(
            id = 92,
            questionAr = "Quelle est l'orthographe correcte de l'adverbe dérivé de l'adjectif 'assidu'?",
            questionEn = "Formation des adverbes en -ment",
            optionsAr = listOf("assidûment (avec accent circonflexe sur le u)", "assidument (sans accent)", "assiduellement", "assidement"),
            correctIndex = 0,
            explanationAr = "L'adverbe 'assidûment' conserve historiquement l'accent circonflexe sur le 'u'."
        ),
        ExamQuestion(
            id = 93,
            questionAr = "Que sanctionne le principe juridique 'Nemo auditur propriam turpitudinem allegans'?",
            questionEn = "Maximes juridiques fondamentales",
            optionsAr = listOf("Nul ne peut se prévaloir de sa propre turpitude pour fonder une action en justice", "Tout accusé est présumé innocent", "Le doute profite toujours à l'accusé", "La loi ne dispose que pour l'avenir"),
            correctIndex = 0,
            explanationAr = "Adage classique empêchant une partie d'invoquer sa propre faute immorale ou illégale pour réclamer un droit."
        ),
        ExamQuestion(
            id = 94,
            questionAr = "Quel est le terme technique désignant l'intervalle de décalage en cabine d'interprétation simultanée?",
            questionEn = "Théorie de l'interprétation de conférence",
            optionsAr = listOf("Le décalage acoustique ou Ear-Voice Span (EVS)", "La modulation terminologique", "Le temps de latence cognitive passive", "La redondance sémiotique"),
            correctIndex = 0,
            explanationAr = "Le décalage (EVS) est le laps de temps (2 à 4 secondes) séparant l'émission de l'orateur et la restitution de l'interprète."
        ),
        ExamQuestion(
            id = 95,
            questionAr = "Quel est le pluriel régulier des noms étrangers comme 'le scénario' selon les rectifications orthographiques?",
            questionEn = "Orthographe moderne",
            optionsAr = listOf("les scénarios (recommandé) ou les scenarii (traditionnel)", "les scénariaes", "les scénariox", "invariable"),
            correctIndex = 0,
            explanationAr = "L'usage contemporain francise régulièrement le pluriel en 'scénarios' tout en admettant la forme latine 'scenarii'."
        ),
        ExamQuestion(
            id = 96,
            questionAr = "Dans un contrat de cession, quelle clause protège l'acheteur contre la concurrence du vendeur?",
            questionEn = "Clauses contractuelles",
            optionsAr = listOf("La clause de non-concurrence", "La clause d'attribution de compétence", "La clause compromissoire", "La clause résolutoire"),
            correctIndex = 0,
            explanationAr = "La clause de non-concurrence interdit au cédant de détourner la clientèle cédée dans un rayon et un temps déterminés."
        ),
        ExamQuestion(
            id = 97,
            questionAr = "Que signifie l'expression 'faire grief' dans un contentieux administratif?",
            questionEn = "Contentieux et procédure",
            optionsAr = listOf("Causer un préjudice direct et certain justifiant un recours", "Demander des excuses publiques", "Déposer une plainte pénale", "Annuler un décret"),
            correctIndex = 0,
            explanationAr = "Un acte ne peut faire l'objet d'un recours pour excès de pouvoir que s'il modifie l'ordonnancement juridique et 'fait grief'."
        ),
        ExamQuestion(
            id = 98,
            questionAr = "Quelle est la vitesse de lecture maximale préconisée en sous-titrage professionnel (CPS)?",
            questionEn = "Sous-titrage et traduction audiovisuelle",
            optionsAr = listOf("15 à 17 caractères par seconde (CPS)", "35 à 45 caractères par seconde", "5 à 8 caractères par seconde", "Aucune limite technique"),
            correctIndex = 0,
            explanationAr = "Les standards de l'audiovisuel européen préconisent un rythme de 15 à 17 CPS (soit environ 160 à 180 mots par minute)."
        ),
        ExamQuestion(
            id = 99,
            questionAr = "Quelle est la règle concernant la césure d'une ligne de sous-titre?",
            questionEn = "Normes de sous-titrage",
            optionsAr = listOf("Respecter les unités syntaxiques et grammaticales naturelles (ne pas couper sujet/verbe)", "Couper exactement au milieu de la phrase au caractère près", "Couper après chaque adjectif", "Ne jamais utiliser deux lignes"),
            correctIndex = 0,
            explanationAr = "La rupture de ligne doit impérativement respecter l'intégrité des syntagmes pour préserver la lisibilité de l'œil."
        ),
        ExamQuestion(
            id = 100,
            questionAr = "Que consacre le concept de 'transposition' dans la stylistique comparée de Vinay et Darbelnet?",
            questionEn = "Théories de la traduction",
            optionsAr = listOf("Le remplacement d'une catégorie grammaticale par une autre sans altérer le sens du message", "La traduction mot à mot exacte", "Le changement de point de vue conceptuel", "L'omission pure et simple d'un terme"),
            correctIndex = 0,
            explanationAr = "La transposition consiste à changer de partie du discours (ex. traduire un verbe par un nom ou vice-versa) en gardant le sens."
        )
    )

    // IELTS TRACK (100 QUESTIONS)
    val ieltsQuestions = listOf(
        ExamQuestion(
            id = 1,
            questionAr = "Choose the correct verb form: 'By the time the delegates arrived, the keynote speech ___.'",
            questionEn = "Past Perfect and Narrative Tenses",
            optionsAr = listOf("had already concluded", "has already concluded", "was already concluded", "already concluded"),
            correctIndex = 0,
            explanationAr = "The past perfect (had concluded) expresses an action completed before another past event (delegates arrived)."
        ),
        ExamQuestion(
            id = 2,
            questionAr = "Select the correct preposition: 'The research committee is composed ___ seven distinguished scholars.'",
            questionEn = "Prepositional Collocations",
            optionsAr = listOf("of", "from", "with", "by"),
            correctIndex = 0,
            explanationAr = "The standard academic adjective collocation is 'composed of' (consisting of)."
        ),
        ExamQuestion(
            id = 3,
            questionAr = "Which sentence correctly utilizes the first conditional for academic forecasting?",
            questionEn = "Conditional Sentences",
            optionsAr = listOf("If investment continues to decline, unemployment will inevitably rise.", "If investment will decline, unemployment would rise.", "If investment declined, unemployment will rise.", "If investment declines, unemployment would rise."),
            correctIndex = 0,
            explanationAr = "First conditional standard pattern: If + present simple -> will + base verb."
        ),
        ExamQuestion(
            id = 4,
            questionAr = "Choose the accurate passive form: 'Scientists are currently investigating the environmental impact.'",
            questionEn = "Passive Voice Transformation",
            optionsAr = listOf("The environmental impact is currently being investigated by scientists.", "The environmental impact is currently investigated by scientists.", "The environmental impact has been currently investigated.", "The environmental impact was currently being investigated."),
            correctIndex = 0,
            explanationAr = "Present continuous passive requires: is/are + being + past participle (is currently being investigated)."
        ),
        ExamQuestion(
            id = 5,
            questionAr = "Select the appropriate quantifier: 'There is ___ evidence to support this theoretical claim.'",
            questionEn = "Quantifiers in Academic Prose",
            optionsAr = listOf("little (meaning scarce/insufficient)", "few", "many", "a few"),
            correctIndex = 0,
            explanationAr = "'Evidence' is an uncountable noun; 'little' indicates a scarce or negative degree."
        ),
        ExamQuestion(
            id = 6,
            questionAr = "Identify the correctly formed indirect question:",
            questionEn = "Embedded and Indirect Questions",
            optionsAr = listOf("Could you clarify what the proposed methodology entails?", "Could you clarify what does the proposed methodology entail?", "Could you clarify what entails the proposed methodology?", "Could you clarify that what the proposed methodology entails?"),
            correctIndex = 0,
            explanationAr = "In embedded questions, standard subject-verb word order is maintained without auxiliary inversion."
        ),
        ExamQuestion(
            id = 7,
            questionAr = "Which modal verb accurately expresses a logical deduction based on clear evidence?",
            questionEn = "Modal Verbs of Deduction",
            optionsAr = listOf("The experimental results must be valid, given the rigorous controls.", "The results should to be valid.", "The results can to be valid.", "The results must to be valid."),
            correctIndex = 0,
            explanationAr = "'Must' followed by bare infinitive expresses strong logical certainty or deduction."
        ),
        ExamQuestion(
            id = 8,
            questionAr = "Choose the correct relative pronoun: 'The university, ___ reputation is worldwide, announced new grants.'",
            questionEn = "Relative Clauses",
            optionsAr = listOf("whose", "which", "whom", "that"),
            correctIndex = 0,
            explanationAr = "'Whose' denotes possession for both people and institutional entities in non-defining clauses."
        ),
        ExamQuestion(
            id = 9,
            questionAr = "Select the appropriate linking word indicating a contrasting perspective:",
            questionEn = "Discourse Markers of Contrast",
            optionsAr = listOf("However", "Furthermore", "Consequently", "Similarly"),
            correctIndex = 0,
            explanationAr = "'However' introduces a counter-argument or contrastive observation."
        ),
        ExamQuestion(
            id = 10,
            questionAr = "Choose the correct gerund/infinitive pattern: 'The committee decided ___ the publication until further review.'",
            questionEn = "Verb Complementation",
            optionsAr = listOf("to postpone", "postponing", "postpone", "for postponing"),
            correctIndex = 0,
            explanationAr = "The verb 'decide' takes a to-infinitive complement (decided to postpone)."
        ),
        ExamQuestion(
            id = 11,
            questionAr = "Identify the correct form for expressing regret about an unfulfilled past action:",
            questionEn = "Past Modals",
            optionsAr = listOf("We should have consulted the legal department earlier.", "We must have consult the legal department earlier.", "We ought consult the legal department earlier.", "We should had consulted the legal department earlier."),
            correctIndex = 0,
            explanationAr = "'Should have + past participle' expresses unfulfilled obligation or retrospective criticism."
        ),
        ExamQuestion(
            id = 12,
            questionAr = "Which sentence demonstrates correct subject-verb agreement with a collective subject?",
            questionEn = "Subject-Verb Agreement",
            optionsAr = listOf("The majority of participants were satisfied with the workshop.", "The majority of participants was being satisfied.", "The majority of participants is satisfied.", "The majority of participants has satisfied."),
            correctIndex = 0,
            explanationAr = "When followed by an 'of + plural noun' phrase, collective quantifiers like 'majority' take plural agreement."
        ),
        ExamQuestion(
            id = 13,
            questionAr = "Choose the accurate academic collocation for 'carry out': 'The laboratory will ___ an extensive trial.'",
            questionEn = "Academic Collocations",
            optionsAr = listOf("conduct", "make", "commit", "fabricate"),
            correctIndex = 0,
            explanationAr = "In formal academic writing, 'conduct a trial/study/survey' is the preferred standard collocation."
        ),
        ExamQuestion(
            id = 14,
            questionAr = "Select the correct comparative structure:",
            questionEn = "Comparative Structures",
            optionsAr = listOf("The second hypothesis is far more plausible than the first.", "The second hypothesis is far much plausible than the first.", "The second hypothesis is more plausible that the first.", "The second hypothesis is plausible than the first."),
            correctIndex = 0,
            explanationAr = "'Far more + adjective + than' is the accurate modifier for emphatic comparison."
        ),
        ExamQuestion(
            id = 15,
            questionAr = "Identify the accurate preposition: 'The economic crisis resulted ___ severe budgetary cuts.'",
            questionEn = "Verb + Preposition Collocations",
            optionsAr = listOf("in", "from", "at", "to"),
            correctIndex = 0,
            explanationAr = "'Result in' signifies 'lead to / cause', whereas 'result from' indicates origin/cause."
        ),
        ExamQuestion(
            id = 16,
            questionAr = "Choose the correct adverbial placement: 'The findings ___ confirm previous clinical observations.'",
            questionEn = "Adverb Position in Sentences",
            optionsAr = listOf("clearly", "clear", "clarity", "clearness"),
            correctIndex = 0,
            explanationAr = "Adverbs of manner placed between subject and main verb modify the assertion (clearly confirm)."
        ),
        ExamQuestion(
            id = 17,
            questionAr = "Select the appropriate conjunction indicating unexpected concession:",
            questionEn = "Concessive Connectors",
            optionsAr = listOf("Although the sample size was modest, the conclusions remain compelling.", "Despite the sample size was modest.", "In spite the sample size was modest.", "Because the sample size was modest."),
            correctIndex = 0,
            explanationAr = "'Although' introduces a subordinate clause containing a finite verb (was modest)."
        ),
        ExamQuestion(
            id = 18,
            questionAr = "Which sentence correctly employs the third conditional?",
            questionEn = "Hypothetical Conditionals",
            optionsAr = listOf("If the data had been verified earlier, the error would have been averted.", "If the data was verified earlier, the error would be averted.", "If the data had been verified, the error will be averted.", "If the data would have been verified, the error had been averted."),
            correctIndex = 0,
            explanationAr = "Third conditional structure: If + had + past participle -> would have + past participle."
        ),
        ExamQuestion(
            id = 19,
            questionAr = "Choose the correct phrase expressing tentative hedging in research:",
            questionEn = "Hedging in Academic Writing",
            optionsAr = listOf("The figures appear to indicate a downward trend.", "The figures definitely prove beyond all doubt.", "The figures always show without error.", "The figures never fail to prove."),
            correctIndex = 0,
            explanationAr = "Hedging verbs such as 'appear to indicate / tend to suggest' soften claims to maintain academic rigor."
        ),
        ExamQuestion(
            id = 20,
            questionAr = "Select the correct noun form: 'The ___ of the treaty took several months of negotiation.'",
            questionEn = "Nominalization in Academic Style",
            optionsAr = listOf("ratification", "ratify", "ratified", "ratifyingly"),
            correctIndex = 0,
            explanationAr = "Nominalization transforms verbal actions into abstract noun concepts (ratification)."
        ),
        ExamQuestion(
            id = 21,
            questionAr = "Identify the correct preposition: 'She is renowned ___ her pioneering work in sociolinguistics.'",
            questionEn = "Adjective Prepositions",
            optionsAr = listOf("for", "with", "about", "by"),
            correctIndex = 0,
            explanationAr = "The adjective 'renowned' collocates with the preposition 'for' (famous for)."
        ),
        ExamQuestion(
            id = 22,
            questionAr = "Which sentence correctly avoids a comma splice?",
            questionEn = "Punctuation in Academic English",
            optionsAr = listOf("The report was finished; therefore, the team adjourned the meeting.", "The report was finished, therefore the team adjourned the meeting.", "The report was finished therefore, the team adjourned.", "The report was finished, and therefore the team adjourned."),
            correctIndex = 0,
            explanationAr = "Two independent clauses linked by a conjunctive adverb require a semicolon before and a comma after."
        ),
        ExamQuestion(
            id = 23,
            questionAr = "Choose the antonym of 'superficial' in an academic analysis:",
            questionEn = "Academic Vocabulary",
            optionsAr = listOf("profound / in-depth", "shallow", "cursory", "trivial"),
            correctIndex = 0,
            explanationAr = "'Profound' or 'in-depth' denotes deep, thorough analytical rigor, contrary to 'superficial'."
        ),
        ExamQuestion(
            id = 24,
            questionAr = "Select the correct phrasal verb meaning 'account for / comprise': 'Renewable sources ___ 30% of energy.'",
            questionEn = "Phrasal Verbs in Data Commentary",
            optionsAr = listOf("make up", "bring about", "take down", "give off"),
            correctIndex = 0,
            explanationAr = "'Make up / account for' is the standard terminology for explaining constituent proportions in charts."
        ),
        ExamQuestion(
            id = 25,
            questionAr = "Choose the correct form: 'Neither the author nor the editors ___ responsible for third-party citations.'",
            questionEn = "Correlative Conjunctions",
            optionsAr = listOf("are", "is", "were being", "am"),
            correctIndex = 0,
            explanationAr = "With 'neither... nor', the verb agrees with the closer subject ('editors' = plural -> are)."
        ),
        ExamQuestion(
            id = 26,
            questionAr = "Identify the correct sentence using negative inversion for rhetorical emphasis:",
            questionEn = "Negative Inversion C1",
            optionsAr = listOf("Seldom have we witnessed such rapid geopolitical shifts.", "Seldom we have witnessed such rapid geopolitical shifts.", "Seldom have witnessed we such rapid geopolitical shifts.", "Seldom we witnessed have such rapid geopolitical shifts."),
            correctIndex = 0,
            explanationAr = "Negative adverbials placed sentence-initially trigger auxiliary-subject inversion (Seldom have we witnessed)."
        ),
        ExamQuestion(
            id = 27,
            questionAr = "Which sentence correctly employs a cleft sentence to emphasize the primary cause?",
            questionEn = "Cleft Sentences for Focus",
            optionsAr = listOf("It was the lack of institutional oversight that precipitated the crisis.", "What precipitated the crisis it was the lack of oversight.", "The lack of oversight was it that precipitated the crisis.", "That precipitated the crisis was the oversight lack."),
            correctIndex = 0,
            explanationAr = "It-cleft structure: It + be + focused element + that/who + subordinate clause."
        ),
        ExamQuestion(
            id = 28,
            questionAr = "Choose the correct participle clause replacing 'Because they had reviewed the literature':",
            questionEn = "Participle Clauses",
            optionsAr = listOf("Having reviewed the literature, the authors formulated their core hypothesis.", "Reviewing the literature, the hypothesis was formulated.", "Reviewed the literature, they formulated the hypothesis.", "To review the literature, the hypothesis formed."),
            correctIndex = 0,
            explanationAr = "The perfect participle (Having reviewed) correctly denotes an action completed prior to the main clause verb."
        ),
        ExamQuestion(
            id = 29,
            questionAr = "Select the correct subjunctive mood form in formal British/American academic English:",
            questionEn = "Subjunctive Mood in Recommendations",
            optionsAr = listOf("It is essential that the protocol be adhered to strictly.", "It is essential that the protocol is adhered to strictly.", "It is essential that the protocol was adhered to strictly.", "It is essential that the protocol being adhered strictly."),
            correctIndex = 0,
            explanationAr = "Mandative subjunctive requires the base form of the verb (be adhered to) regardless of the subject."
        ),
        ExamQuestion(
            id = 30,
            questionAr = "Identify the correct inversion after 'Not only':",
            questionEn = "Correlative Inversion",
            optionsAr = listOf("Not only did the project meet its objectives, but it also secured additional funding.", "Not only the project met its objectives, but it also secured.", "Not only met the project its objectives, but also it secured.", "Not only the project did meet, but also secured."),
            correctIndex = 0,
            explanationAr = "'Not only' at the beginning of a clause requires auxiliary inversion (did the project meet)."
        ),
        ExamQuestion(
            id = 31,
            questionAr = "Which sentence demonstrates correct parallelism in formal academic listing?",
            questionEn = "Parallel Structure in Prose",
            optionsAr = listOf("The initiative aims to reduce emissions, foster innovation, and promote sustainability.", "The initiative aims to reduce emissions, fostering innovation, and sustainability.", "The initiative aims at reduction, to foster innovation, and promoting.", "The initiative aims reducing emissions, foster innovation, and promote."),
            correctIndex = 0,
            explanationAr = "Parallelism requires coordinated elements to share the identical grammatical pattern (reduce, foster, promote)."
        ),
        ExamQuestion(
            id = 32,
            questionAr = "Choose the correct prepositional phrase meaning 'in terms of / regarding':",
            questionEn = "Discourse Connectors",
            optionsAr = listOf("With respect to the legislative framework, significant reforms are underway.", "In respect with the legislative framework.", "Respecting to the legislative framework.", "On respect of the legislative framework."),
            correctIndex = 0,
            explanationAr = "The established formal idiom is 'with respect to' or 'in respect of'."
        ),
        ExamQuestion(
            id = 33,
            questionAr = "Select the nuanced academic adjective meaning 'present everywhere / widespread':",
            questionEn = "Academic Lexis C1",
            optionsAr = listOf("ubiquitous", "ephemeral", "tangential", "esoteric"),
            correctIndex = 0,
            explanationAr = "'Ubiquitous' describes phenomena that are omnipresent or universally pervasive."
        ),
        ExamQuestion(
            id = 34,
            questionAr = "Identify the correct conditional inversion replacing 'If the board should approve':",
            questionEn = "Conditional Inversion",
            optionsAr = listOf("Should the board approve the merger, implementation will commence immediately.", "Had the board approved the merger, implementation commences.", "Were the board to approve the merger, implementation had commenced.", "Could the board approve, implementation will commence."),
            correctIndex = 0,
            explanationAr = "'Should + subject + bare infinitive' is the formal inverted equivalent of a first conditional with 'should'."
        ),
        ExamQuestion(
            id = 35,
            questionAr = "Choose the correct mixed conditional expressing past action with present consequence:",
            questionEn = "Mixed Conditionals",
            optionsAr = listOf("If they had implemented the security patches, the system would not be vulnerable now.", "If they implemented the patches, the system would not have been vulnerable now.", "If they had implemented the patches, the system had not been vulnerable.", "If they would implement the patches, the system is not vulnerable now."),
            correctIndex = 0,
            explanationAr = "Mixed conditional: past condition (If + had + pp) with ongoing present result (would not be vulnerable now)."
        ),
        ExamQuestion(
            id = 36,
            questionAr = "Select the accurate collocation: 'The committee voiced ___ concerning the validity of the data.'",
            questionEn = "Formal Collocations",
            optionsAr = listOf("grave reservations", "heavy doubtings", "deep cancellations", "big negatives"),
            correctIndex = 0,
            explanationAr = "'To voice/express grave reservations' is the idiomatic academic formula for profound scholarly concern."
        ),
        ExamQuestion(
            id = 37,
            questionAr = "Which sentence correctly employs a non-defining relative clause with proper punctuation?",
            questionEn = "Punctuation of Relative Clauses",
            optionsAr = listOf("The treaty, which was drafted in Geneva, encompasses thirty member states.", "The treaty which was drafted in Geneva encompasses thirty member states.", "The treaty, that was drafted in Geneva, encompasses thirty states.", "The treaty which was drafted in Geneva, encompasses thirty states."),
            correctIndex = 0,
            explanationAr = "Non-defining clauses provide supplementary non-essential information and must be enclosed in commas; 'that' is excluded."
        ),
        ExamQuestion(
            id = 38,
            questionAr = "Choose the word meaning 'to worsen or aggravate an existing dispute':",
            questionEn = "Sophisticated Vocabulary",
            optionsAr = listOf("exacerbate", "ameliorate", "alleviate", "mitigate"),
            correctIndex = 0,
            explanationAr = "'Exacerbate' means to make a problem, disease, or hostile situation significantly worse."
        ),
        ExamQuestion(
            id = 39,
            questionAr = "Select the antonym of 'mitigate' in risk management discourse:",
            questionEn = "Academic Lexis Antonyms",
            optionsAr = listOf("intensify / aggravate", "moderate", "attenuate", "diminish"),
            correctIndex = 0,
            explanationAr = "'Mitigate' means to alleviate or lessen; its direct antonym is 'intensify / aggravate'."
        ),
        ExamQuestion(
            id = 40,
            questionAr = "Identify the correct formal connector replacing 'Because of this':",
            questionEn = "Formal Transition Words",
            optionsAr = listOf("Consequently / Hence", "Besides", "Anyhow", "For why"),
            correctIndex = 0,
            explanationAr = "'Consequently' and 'hence' are high-register adverbials expressing direct logical deduction."
        ),
        ExamQuestion(
            id = 41,
            questionAr = "Choose the sentence containing an accurate fronted adverbial of place:",
            questionEn = "Fronting and Information Packaging",
            optionsAr = listOf("Under the ancient pillars lay the ceremonial diplomatic scrolls.", "Under the ancient pillars the ceremonial scrolls lay down.", "Lay under the ancient pillars the scrolls.", "The ceremonial scrolls lay, under the ancient pillars."),
            correctIndex = 0,
            explanationAr = "Locative inversion (Prepositional phrase + verb + subject) is used in literary and formal descriptive prose."
        ),
        ExamQuestion(
            id = 42,
            questionAr = "Select the formal adjective meaning 'temporary / lasting for a brief duration':",
            questionEn = "Academic Lexis",
            optionsAr = listOf("ephemeral / transient", "immutable", "perpetual", "enduring"),
            correctIndex = 0,
            explanationAr = "'Ephemeral' or 'transient' denotes short-lived phenomena, opposed to permanent or immutable."
        ),
        ExamQuestion(
            id = 43,
            questionAr = "Which phrase correctly expresses 'in accordance with established rules'?",
            questionEn = "Formal Legal Expressions",
            optionsAr = listOf("in compliance with", "in compliant to", "in complying of", "with compliance at"),
            correctIndex = 0,
            explanationAr = "The standard institutional idiom is 'in compliance with' statutory standards."
        ),
        ExamQuestion(
            id = 44,
            questionAr = "Choose the correct grammatical form: 'The minister was accused of having ___ his official powers.'",
            questionEn = "Verb Choice in Official Accusations",
            optionsAr = listOf("exceeded / abused", "overpassed", "outgrown", "surmounted"),
            correctIndex = 0,
            explanationAr = "'Exceeded his powers' (acting ultra vires) or 'abused' represents standard legal and diplomatic phraseology."
        ),
        ExamQuestion(
            id = 45,
            questionAr = "Select the correct rhetorical device: 'Not that I loved Caesar less, but that I loved Rome more.'",
            questionEn = "Rhetorical Structures",
            optionsAr = listOf("Antithesis", "Tautology", "Anacoluthon", "Euphemism"),
            correctIndex = 0,
            explanationAr = "Antithesis places balanced contrasting ideas in grammatical juxtaposition for persuasive emphasis."
        ),
        ExamQuestion(
            id = 46,
            questionAr = "Identify the correct form of nominal relative clause:",
            questionEn = "Wh- Clauses as Subjects",
            optionsAr = listOf("What remains unclear is the mechanism of neural transmission.", "That remains unclear is the mechanism.", "Which remains unclear is the mechanism.", "Whichever remains unclear is the mechanism."),
            correctIndex = 0,
            explanationAr = "Nominal relative 'What remains unclear' functions as the subject noun phrase of the copular verb 'is'."
        ),
        ExamQuestion(
            id = 47,
            questionAr = "Choose the precise legal term for an unalterable, non-appealable verdict:",
            questionEn = "Judicial Terminology",
            optionsAr = listOf("irrevocable / definitive ruling", "tentative verdict", "preliminary finding", "contingent decision"),
            correctIndex = 0,
            explanationAr = "An 'irrevocable' or 'definitive' judgment represents a res judicata decision that cannot be reopened."
        ),
        ExamQuestion(
            id = 48,
            questionAr = "Select the appropriate academic phrase denoting scholarly consensus:",
            questionEn = "Corpus Collocations",
            optionsAr = listOf("The prevailing consensus among historians suggests that...", "The dominant agreement among historians tells that...", "The popular voting among historians indicates that...", "The crowd view among historians says that..."),
            correctIndex = 0,
            explanationAr = "'Prevailing consensus' is the attested academic collocation in peer-reviewed historiography."
        ),
        ExamQuestion(
            id = 49,
            questionAr = "Identify the correct passive infinitive: 'The protocol requires all personnel ___ prior to admission.'",
            questionEn = "Passive Infinitives",
            optionsAr = listOf("to be vetted", "to vet", "vetting", "to have vetting"),
            correctIndex = 0,
            explanationAr = "Subject receives the action of security screening: requires [someone] to be vetted."
        ),
        ExamQuestion(
            id = 50,
            questionAr = "Which sentence demonstrates accurate end-weight principle in English syntax?",
            questionEn = "Information Structure & End-Weight",
            optionsAr = listOf("The delegates rejected the proposal, which had been compiled without consulting grassroots leaders.", "The delegates the proposal which had been compiled without consulting grassroots leaders rejected.", "Without consulting grassroots leaders compiled proposal the delegates rejected.", "Rejected the delegates the long proposal."),
            correctIndex = 0,
            explanationAr = "The principle of end-weight places long, syntactically complex subordinate clauses at the end of the sentence."
        ),
        ExamQuestion(
            id = 51,
            questionAr = "In treaty drafting, what distinguishes the legal force of 'shall' from 'may'?",
            questionEn = "Normative Modality in Treaty Drafting",
            optionsAr = listOf("'Shall' imposes a mandatory obligation, whereas 'may' confers discretionary authorization.", "'Shall' expresses a tentative wish, while 'may' is compulsory.", "'Shall' applies only to individuals, not sovereign states.", "Both are legally interchangeable synonyms."),
            correctIndex = 0,
            explanationAr = "In statutory and international treaty drafting, 'shall' signifies a binding obligation (must), and 'may' permissive discretion."
        ),
        ExamQuestion(
            id = 52,
            questionAr = "What is the primary stylistic hazard of 'nominalization' when overused in academic writing?",
            questionEn = "Academic Stylistics and Readability",
            optionsAr = listOf("It creates bureaucratic obscurity and obscures the human agent performing the action.", "It makes sentences too colloquial and informal.", "It violates subject-verb agreement rules.", "It forces the text into rhyme."),
            correctIndex = 0,
            explanationAr = "Excessive nominalization (noun stacks) causes stylistic heaviness ('bureaucratese') by burying vigorous verbs in abstract nouns."
        ),
        ExamQuestion(
            id = 53,
            questionAr = "Identify the correct use of the subjunctive with 'lest' in formal prose:",
            questionEn = "Archaic and Formal Subjunctive",
            optionsAr = listOf("The envoy checked the cipher keys lest any transmission be intercepted.", "The envoy checked the keys lest any transmission was intercepted.", "The envoy checked the keys lest any transmission will be intercepted.", "The envoy checked the keys lest any transmission is intercepted."),
            correctIndex = 0,
            explanationAr = "The conjunction 'lest' (for fear that) takes a mandative subjunctive bare infinitive ('be intercepted') in high-register English."
        ),
        ExamQuestion(
            id = 54,
            questionAr = "Which term denotes the intentional omission of words understood from context?",
            questionEn = "Linguistic Cohesion",
            optionsAr = listOf("Ellipsis", "Anaphora", "Cataphora", "Metonymy"),
            correctIndex = 0,
            explanationAr = "Ellipsis is the grammatical omission of elements readily recoverable from surrounding co-text without loss of clarity."
        ),
        ExamQuestion(
            id = 55,
            questionAr = "What does the Latin phrase 'inter alia' signify when inserted in a diplomatic communique?",
            questionEn = "Diplomatic Latinisms",
            optionsAr = listOf("Among other things / matters", "In the absence of parties", "By right of sovereignty", "To the very end"),
            correctIndex = 0,
            explanationAr = "'Inter alia' is standard diplomatic formula indicating that an enumeration is non-exhaustive."
        ),
        ExamQuestion(
            id = 56,
            questionAr = "Choose the precise synonym for 'lucid and persuasive' in discourse analysis:",
            questionEn = "Stylistic Lexis C2",
            optionsAr = listOf("cogent / compelling", "diffuse", "incoherent", "nebulous"),
            correctIndex = 0,
            explanationAr = "A 'cogent' argument is logically organized, clear, and intellectually compelling."
        ),
        ExamQuestion(
            id = 57,
            questionAr = "What is meant by the linguistic term 'register' in translation and sociolinguistics?",
            questionEn = "Linguistic Terminology",
            optionsAr = listOf("The variety of language determined by the social context, communicative purpose, and audience", "The speed at which an interpreter speaks in the booth", "The pitch of an orator's voice", "The alphabet in which a document is encoded"),
            correctIndex = 0,
            explanationAr = "Register designates the situational appropriateness and formality level of linguistic choices (formal, frozen, consultative)."
        ),
        ExamQuestion(
            id = 58,
            questionAr = "Select the sentence illustrating correct usage of 'scarcely ... when':",
            questionEn = "Correlative Inversion Patterns",
            optionsAr = listOf("Scarcely had the treaty been ratified when fresh hostilities erupted.", "Scarcely the treaty had been ratified than hostilities erupted.", "Scarcely had the treaty been ratified then hostilities erupted.", "Scarcely was the treaty ratified so hostilities erupted."),
            correctIndex = 0,
            explanationAr = "The correlative pair is 'Scarcely had + subject + pp ... when + past simple'."
        ),
        ExamQuestion(
            id = 59,
            questionAr = "In academic argument, what constitutes a 'straw man' fallacy?",
            questionEn = "Fallacies in Critical Reasoning",
            optionsAr = listOf("Misrepresenting an opponent's argument in a weakened form to refute it easily", "Attacking the speaker's personal character rather than their evidence", "Assuming as true the very premise that requires proof", "Drawing a conclusion from an unrepresentative sample"),
            correctIndex = 0,
            explanationAr = "The straw man fallacy involves distorting or exaggerating an opposing viewpoint to render it vulnerable to easy attack."
        ),
        ExamQuestion(
            id = 60,
            questionAr = "Identify the correct meaning of 'unprecedented' in historical analysis:",
            questionEn = "Lexical Precision",
            optionsAr = listOf("Never having happened or existed before in documented history", "Occurring repeatedly in cycles", "Unimportant and negligible", "Widely celebrated by the public"),
            correctIndex = 0,
            explanationAr = "'Unprecedented' denotes an event or development devoid of prior historical parallel or benchmark."
        ),
        ExamQuestion(
            id = 61,
            questionAr = "What does 'pacta sunt servanda' mandate in customary international law?",
            questionEn = "Core Legal Maxims",
            optionsAr = listOf("Agreements and treaties must be respected and fulfilled in good faith.", "Treaties expire upon the change of government.", "Only written agreements carry binding force.", "All disputes must be settled by armed intervention."),
            correctIndex = 0,
            explanationAr = "The cornerstone rule of the Vienna Convention on the Law of Treaties (Art. 26): treaties in force are binding upon parties."
        ),
        ExamQuestion(
            id = 62,
            questionAr = "Choose the correct adjective describing a conclusion that logically follows from premises:",
            questionEn = "Epistemological Lexis",
            optionsAr = listOf("deductive / valid", "fallacious", "spurious", "tautological"),
            correctIndex = 0,
            explanationAr = "A deductively valid conclusion inevitably follows from true premises without contradiction."
        ),
        ExamQuestion(
            id = 63,
            questionAr = "Select the word meaning 'to delineate the boundaries or limits of a concept':",
            questionEn = "Scholarly Verbs",
            optionsAr = listOf("demarcate / delimit", "amalgamate", "conflate", "obfuscate"),
            correctIndex = 0,
            explanationAr = "'Demarcate' or 'delimit' means to set distinct borders, categories, or functional parameters."
        ),
        ExamQuestion(
            id = 64,
            questionAr = "What is the rhetorical function of 'chiasmus' (e.g., 'Never let a fool kiss you or a kiss fool you')?",
            questionEn = "Rhetorical Figures C2",
            optionsAr = listOf("Inverting the grammatical structure of corresponding phrases in reverse parallelism", "Repeating the same consonant sound", "Comparing two things using 'like' or 'as'", "Exaggerating for comic relief"),
            correctIndex = 0,
            explanationAr = "Chiasmus creates memorable balanced inversion (AB : BA pattern) to stimulate intellectual reflection."
        ),
        ExamQuestion(
            id = 65,
            questionAr = "Identify the precise meaning of 'ex gratia' in financial settlements:",
            questionEn = "Legal Latin Terminology",
            optionsAr = listOf("As a gesture of goodwill, without admitting any legal liability or obligation", "Mandated by criminal statute", "Ordered by an international court", "Payable under insurance penalty"),
            correctIndex = 0,
            explanationAr = "An 'ex gratia' payment is granted out of benevolence or moral duty, without legal acknowledgement of fault."
        ),
        ExamQuestion(
            id = 66,
            questionAr = "In discourse analysis, what characterizes 'cohesion' as opposed to 'coherence'?",
            questionEn = "Text Linguistics",
            optionsAr = listOf("Cohesion refers to surface grammatical and lexical links; coherence refers to underlying conceptual unity.", "Cohesion is spoken; coherence is written.", "Cohesion is for vocabulary; coherence is for phonetics.", "Both terms denote identical linguistic properties."),
            correctIndex = 0,
            explanationAr = "Cohesion pertains to syntactic and lexical ties (pronouns, connectors); coherence is the conceptual intelligibility of the discourse."
        ),
        ExamQuestion(
            id = 67,
            questionAr = "Choose the sophisticated idiom meaning 'to evade the central issue under discussion':",
            questionEn = "Idiomatic Fluency C2",
            optionsAr = listOf("to beat around the bush / prevaricate", "to hit the nail on the head", "to take the bull by the horns", "to burn bridges"),
            correctIndex = 0,
            explanationAr = "'To prevaricate' or 'beat about the bush' signifies speaking evasively to avoid a direct truth or confrontation."
        ),
        ExamQuestion(
            id = 68,
            questionAr = "What does the adjective 'mercurial' convey when describing a diplomatic leader?",
            questionEn = "Character Description Lexis",
            optionsAr = listOf("Unpredictably volatile in mood, policy, or temperament", "Deeply grounded and stable", "Slow to make decisions", "Strictly obedient to international law"),
            correctIndex = 0,
            explanationAr = "'Mercurial' describes rapid, unpredictable fluctuations in temperament or political demeanor."
        ),
        ExamQuestion(
            id = 69,
            questionAr = "Identify the meaning of the formal verb 'conflate':",
            questionEn = "Conceptual Analysis Verbs",
            optionsAr = listOf("To mistakenly blend or merge two distinct concepts into one", "To clarify a complex argument", "To expand a budget", "To discard obsolete data"),
            correctIndex = 0,
            explanationAr = "'To conflate' is to combine two separate ideas or datasets errantly, obscuring their vital differences."
        ),
        ExamQuestion(
            id = 70,
            questionAr = "In phonetic transcription, what is the 'schwa' sound (/ə/)?",
            questionEn = "Phonology and Articulation",
            optionsAr = listOf("The mid-central neutral unaccented vowel sound common in unstressed English syllables", "The open back rounded vowel", "A nasalized consonant", "A voiced dental fricative"),
            correctIndex = 0,
            explanationAr = "The schwa (/ə/) is the most frequent unstressed reduced vowel sound in the English language (e.g., about, father)."
        ),
        ExamQuestion(
            id = 71,
            questionAr = "What distinguishes an 'aphorism' from a general statement?",
            questionEn = "Literary & Rhetorical Forms",
            optionsAr = listOf("A concise, pithy formulation of a universal truth or moral principle", "A long narrative poem", "A legal disclaimer on contracts", "A statistical summary in tables"),
            correctIndex = 0,
            explanationAr = "An aphorism is an epigrammatic, memorable distillation of philosophical insight (e.g., Bacon, Hippocrates)."
        ),
        ExamQuestion(
            id = 72,
            questionAr = "Select the correct preposition in: 'The ambassador expressed deference ___ the host country's customs.'",
            questionEn = "Noun Collocations",
            optionsAr = listOf("to", "for", "with", "from"),
            correctIndex = 0,
            explanationAr = "The noun 'deference' (respectful submission) collocates with the preposition 'to'."
        ),
        ExamQuestion(
            id = 73,
            questionAr = "What is the function of 'fronting' in communicative grammar?",
            questionEn = "Stylistic Inversion & Thematization",
            optionsAr = listOf("Moving a non-subject clause element to the absolute beginning to establish topical prominence", "Placing the verb at the very end of the paragraph", "Eliminating vowels from formal acronyms", "Writing in capital letters for emphasis"),
            correctIndex = 0,
            explanationAr = "Fronting thematicizes information by shifting objects, complements, or adverbials to initial position for discourse emphasis."
        ),
        ExamQuestion(
            id = 74,
            questionAr = "Identify the precise antonym of 'parsimonious' in economic policy:",
            questionEn = "Economic and Character Lexis",
            optionsAr = listOf("prodigal / lavish / munificent", "frugal", "prudent", "austere"),
            correctIndex = 0,
            explanationAr = "'Parsimonious' means excessively frugal or stingy; its antonyms are 'prodigal', 'lavish', or 'munificent'."
        ),
        ExamQuestion(
            id = 75,
            questionAr = "In formal interpretation, what does the technique of 'salami phrasing' accomplish?",
            questionEn = "Conference Interpretation Strategy",
            optionsAr = listOf("Slicing long, complex source sentences into short, coherent target utterances to manage cognitive load", "Speaking very slowly in a monotone", "Omitting adjectives systematically", "Repeating every word twice for clarity"),
            correctIndex = 0,
            explanationAr = "The salami technique segments unwieldy embedded source structures into digestible, punchy sentences, preserving clarity in real time."
        ),
        ExamQuestion(
            id = 76,
            questionAr = "What does the Latin legal phrase 'ultra vires' indicate when applied to an executive decree?",
            questionEn = "Constitutional & Administrative Law",
            optionsAr = listOf("Beyond the legal power or authority granted to that body", "Within the ordinary scope of duty", "Valid under constitutional review", "Signed under military duress"),
            correctIndex = 0,
            explanationAr = "An action taken 'ultra vires' exceeds statutory or constitutional authority and is legally void ab initio."
        ),
        ExamQuestion(
            id = 77,
            questionAr = "Select the sophisticated academic verb meaning 'to explain or interpret the meaning of a complex text':",
            questionEn = "Hermeneutic Lexis C2",
            optionsAr = listOf("explicate / elucidate", "obviate", "vitiate", "interpolate"),
            correctIndex = 0,
            explanationAr = "'Explicate' signifies analyzing and explaining text or ideology in fine, scholarly detail."
        ),
        ExamQuestion(
            id = 78,
            questionAr = "Choose the correct adjective for an agreement that cannot be breached or violated:",
            questionEn = "Legal & Treaty Adjectives",
            optionsAr = listOf("inviolable / sacrosanct", "malleable", "contingent", "revocable"),
            correctIndex = 0,
            explanationAr = "'Inviolable' describes rights, treaties, or territory that must be respected without infraction."
        ),
        ExamQuestion(
            id = 79,
            questionAr = "What is the precise grammatical status of 'having said that' in academic discourse?",
            questionEn = "Participial Discourse Markers",
            optionsAr = listOf("A concessive transitional phrase functioning as a disjunct", "A restrictive relative clause", "A gerundial direct object", "An adverb of frequency"),
            correctIndex = 0,
            explanationAr = "'Having said that' is a fixed discourse marker signaling a shift to a concessive counter-statement."
        ),
        ExamQuestion(
            id = 80,
            questionAr = "Identify the correct spelling of the formal adjective meaning 'excessively flattering or obsequious':",
            questionEn = "Orthography and Advanced Lexis",
            optionsAr = listOf("sycophantic", "sicophantic", "sycofantic", "psycophantic"),
            correctIndex = 0,
            explanationAr = "'Sycophantic' (from Greek sycophant) describes servile flattery aimed at gaining advantage."
        ),
        ExamQuestion(
            id = 81,
            questionAr = "What does 'epistemology' study in philosophical inquiry?",
            questionEn = "Philosophical and Academic Terminology",
            optionsAr = listOf("The nature, origin, and limits of human knowledge and justified belief", "The moral principles of conduct", "The physical origins of the universe", "The syntax of ancient languages"),
            correctIndex = 0,
            explanationAr = "Epistemology investigates the grounds, validity, and scope of human knowledge."
        ),
        ExamQuestion(
            id = 82,
            questionAr = "Select the correct preposition in: 'The policy was enacted pursuant ___ the ministerial directive.'",
            questionEn = "Prepositional Idioms in Law",
            optionsAr = listOf("to", "of", "with", "for"),
            correctIndex = 0,
            explanationAr = "The established legal locution is 'pursuant to' (in accordance with)."
        ),
        ExamQuestion(
            id = 83,
            questionAr = "Which term denotes the state of two legal provisions being mutually contradictory and irreconcilable?",
            questionEn = "Legal Theory and Antinomy",
            optionsAr = listOf("antinomy / incompatibility", "synonymy", "complementarity", "jurisprudence"),
            correctIndex = 0,
            explanationAr = "An 'antinomy' represents an unresolvable contradiction between two equally authoritative legal principles."
        ),
        ExamQuestion(
            id = 84,
            questionAr = "Choose the accurate academic noun for 'the act of attributing human emotions to non-human entities':",
            questionEn = "Literary & Analytical Vocabulary",
            optionsAr = listOf("anthropomorphism / pathetic fallacy", "alliteration", "hyperbole", "synecdoche"),
            correctIndex = 0,
            explanationAr = "Anthropomorphism or the pathetic fallacy projects human sentiments onto nature or objects."
        ),
        ExamQuestion(
            id = 85,
            questionAr = "What is the function of an 'inchoate' offense in criminal law theory?",
            questionEn = "Specialized Legal Lexis",
            optionsAr = listOf("An offense that has been begun or attempted but not yet consummated (e.g. conspiracy, attempt)", "A crime committed without motive", "A misdemeanor punishable by fine", "A verdict acquitted on appeal"),
            correctIndex = 0,
            explanationAr = "Inchoate offenses (attempts, solicitations, conspiracies) criminalize conduct preparatory to harm."
        ),
        ExamQuestion(
            id = 86,
            questionAr = "Select the nuanced adjective meaning 'seemingly reasonable, but actually misleading or deceptive':",
            questionEn = "Critical Reasoning Lexis",
            optionsAr = listOf("specious / fallacious", "veracious", "axiomatic", "cogent"),
            correctIndex = 0,
            explanationAr = "A 'specious' argument appears superficially plausible but is fundamentally flawed or deceitful."
        ),
        ExamQuestion(
            id = 87,
            questionAr = "Identify the rhetorical term for using a part of something to represent the whole (e.g., 'all hands on deck'):",
            questionEn = "Figures of Speech",
            optionsAr = listOf("synecdoche", "metaphor", "apostrophe", "irony"),
            correctIndex = 0,
            explanationAr = "Synecdoche is the figure of speech where a part stands for the whole or vice-versa."
        ),
        ExamQuestion(
            id = 88,
            questionAr = "What does the Latin term 'ipso facto' signify in evidentiary documentation?",
            questionEn = "Legal Evidentiary Latinisms",
            optionsAr = listOf("By that very fact or act itself", "In accordance with custom", "Pending further investigation", "Without prejudice to claims"),
            correctIndex = 0,
            explanationAr = "'Ipso facto' denotes that a condition arises automatically by the very occurrence of a specific act."
        ),
        ExamQuestion(
            id = 89,
            questionAr = "Choose the correct adjective for a dispute that is purely academic and has no practical legal effect:",
            questionEn = "Judicial Pragmatics",
            optionsAr = listOf("moot / academic", "substantive", "actionable", "precedential"),
            correctIndex = 0,
            explanationAr = "A 'moot' question is hypothetical or no longer live, having lost practical consequence."
        ),
        ExamQuestion(
            id = 90,
            questionAr = "What constitutes 'verisimilitude' in critical textual analysis?",
            questionEn = "Literary & Discourse Theory",
            optionsAr = listOf("The appearance or semblance of truth, authenticity, and reality", "The musical meter of verse", "The grammatical error count", "The historical date of writing"),
            correctIndex = 0,
            explanationAr = "Verisimilitude is the quality of plausibility that renders a narrative credible and lifelike."
        ),
        ExamQuestion(
            id = 91,
            questionAr = "Select the formal verb meaning 'to invalidate or destroy the legal efficacy of a contract':",
            questionEn = "Contractual Terminology C2",
            optionsAr = listOf("vitiate / annul", "ratify", "indemnify", "subrogate"),
            correctIndex = 0,
            explanationAr = "'To vitiate' a contract is to corrupt its validity through fraud, mistake, or duress."
        ),
        ExamQuestion(
            id = 92,
            questionAr = "Which term describes the practice of balancing competing interests to achieve proportional justice?",
            questionEn = "Legal Philosophy and Balancing",
            optionsAr = listOf("teleological balancing / proportionality test", "arbitrary discretion", "literalism", "originalism"),
            correctIndex = 0,
            explanationAr = "Proportionality review balances competing rights against public interests to avoid excessive curtailment."
        ),
        ExamQuestion(
            id = 93,
            questionAr = "Choose the precise antonym of 'equivocal' in diplomatic statements:",
            questionEn = "Diplomatic Lexis",
            optionsAr = listOf("univocal / unambiguous / explicit", "nebulous", "evasive", "ambivalent"),
            correctIndex = 0,
            explanationAr = "'Equivocal' means open to multiple misleading interpretations; its antonym is 'unambiguous / explicit'."
        ),
        ExamQuestion(
            id = 94,
            questionAr = "What is the primary role of an 'amicus curiae' in appellate jurisprudence?",
            questionEn = "Judicial Procedure C2",
            optionsAr = listOf("A non-party 'friend of the court' offering expertise or perspectives to assist the bench", "The chief presiding judge", "The sworn court stenographer", "The defense attorney of record"),
            correctIndex = 0,
            explanationAr = "An amicus curiae submits briefs to provide specialized legal insight on complex matters."
        ),
        ExamQuestion(
            id = 95,
            questionAr = "Identify the meaning of the formal adjective 'ineluctable':",
            questionEn = "Scholarly Lexis",
            optionsAr = listOf("Unavoidable, inescapable, and bound to happen", "Easily resolved", "Transparent to observe", "Extremely fragile"),
            correctIndex = 0,
            explanationAr = "'Ineluctable' describes outcomes or historical forces that cannot be escaped or resisted."
        ),
        ExamQuestion(
            id = 96,
            questionAr = "What does 'hermeneutics' encompass in linguistic and philosophical studies?",
            questionEn = "Linguistic and Philosophical Disciplines",
            optionsAr = listOf("The theory and methodology of interpretation of texts and symbolic systems", "The physical study of sound waves", "The classification of dictionaries", "The translation of computer code"),
            correctIndex = 0,
            explanationAr = "Hermeneutics explores the principles, conditions, and philosophy of textual interpretation."
        ),
        ExamQuestion(
            id = 97,
            questionAr = "Select the accurate collocation: 'The commission took ___ of the ongoing cross-border developments.'",
            questionEn = "Institutional Collocations",
            optionsAr = listOf("cognizance", "noticeable", "knowing", "intellection"),
            correctIndex = 0,
            explanationAr = "The formal institutional idiom is 'to take cognizance of' (to take official judicial/statutory notice)."
        ),
        ExamQuestion(
            id = 98,
            questionAr = "What distinguishes 'relational autonomy' in modern socio-legal scholarship?",
            questionEn = "Theoretical Jurisprudence",
            optionsAr = listOf("Understanding individual freedom as fundamentally shaped and enabled by social relationships", "Absolute isolation of the individual", "Total state control over speech", "The elimination of private property"),
            correctIndex = 0,
            explanationAr = "Relational autonomy posits that genuine human agency develops within interpersonal and social contexts."
        ),
        ExamQuestion(
            id = 99,
            questionAr = "In UN multilateral diplomacy, what is the role of the 'depository' of a treaty?",
            questionEn = "Vienna Convention on the Law of Treaties",
            optionsAr = listOf("The state or international body entrusted with custody of the authentic treaty text and instruments of ratification", "The translation agency printing copies", "The media covering the signing ceremony", "The bank holding the diplomatic fees"),
            correctIndex = 0,
            explanationAr = "The depository (often the UN Secretary-General) receives and registers all formal treaty ratifications."
        ),
        ExamQuestion(
            id = 100,
            questionAr = "What is the precise term for the certified English translation of a foreign public document?",
            questionEn = "Official Certified Translation Standards",
            optionsAr = listOf("Sworn / Certified Translation bearing the translator's official jurat and stamp", "Draft translation", "Machine pre-translation", "Subtitled summary"),
            correctIndex = 0,
            explanationAr = "A sworn or certified translation includes the formal declaration (jurat), registration number, and seal attesting to full fidelity."
        )
    )

    // TRANS TRACK (100 QUESTIONS)
    val transQuestions = listOf(
        ExamQuestion(
            id = 1,
            questionAr = "ما هي الترجمة المؤسساتية المعتمدة لمصطلح 'Plenipotentiary' في المعاهدات والاتفاقيات الدولية؟",
            questionEn = "المعجم الدبلوماسي والدولي",
            optionsAr = listOf("مفوض فوق العادة ومطلق الصلاحية", "وزير مفوض مؤقت", "مبعوث استطلاعي خاص", "ملحق ثقافي معتمد"),
            correctIndex = 0,
            explanationAr = "اللقب الدبلوماسي الرسمي 'Plenipotentiary' يترجم حصراً بـ 'مفوض فوق العادة ومطلق الصلاحية'."
        ),
        ExamQuestion(
            id = 2,
            questionAr = "في العقود التجارية الدولية، ما الفرق الجوهري الدقيق بين 'Force Majeure' و 'Hardship'؟",
            questionEn = "الصياغة القانونية والعقود",
            optionsAr = listOf("Force Majeure تجعل التنفيذ مستحيلاً تماماً، بينما Hardship تجعله مرهقاً ومختلاً اقتصادياً بصورة غير متوقعة", "Hardship تطبق فقط في النزاعات الجنائية", "كلاهما يعني دفع غرامة تأخير بدون إعفاء", "Force Majeure تشترط خطأ أحد الأطراف"),
            correctIndex = 0,
            explanationAr = "القوة القاهرة تعدم إمكانية التنفيذ، بينما الظروف الطارئة (Hardship) تخل بالتوازن الاقتصادي دون استحالته."
        ),
        ExamQuestion(
            id = 3,
            questionAr = "ما المقابل العربي الأدق للعبارة القانونية اللاتينية 'Mutatis Mutandis' في نصوص المراسيم والاتفاقيات؟",
            questionEn = "المصطلحات القانونية اللاتينية",
            optionsAr = listOf("مع مراعاة التعديلات اللازمة / مع ما يقتضيه الفارق", "إلى الأبد وبدون قيد أو شرط", "بحسن نية مطلقة", "بأثر رجعي فوري"),
            correctIndex = 0,
            explanationAr = "العبارة اللاتينية تعني تطبيق نفس الأحكام مع إجراء التغييرات الطفيفة اللازمة لاختلاف السياق."
        ),
        ExamQuestion(
            id = 4,
            questionAr = "ما هو المدلول القانوني الدقيق لمصطلح 'Ultra Vires' في ممارسات الهيئات الإدارية؟",
            questionEn = "القانون الإداري والمؤسساتي",
            optionsAr = listOf("تجاوز حدود الاختصاص والسلطة المخولة قانوناً (مما يبطل التصرف)", "التصرف بحسن نية كاملة", "تفويض الصلاحيات لموظف أدنى", "المصادقة الإجماعية على القرار"),
            correctIndex = 0,
            explanationAr = "التصرف 'Ultra Vires' هو عمل يقع خارج النطاق القانوني أو الدستوري الممنوح للمؤسسة ويعد باطلاً."
        ),
        ExamQuestion(
            id = 5,
            questionAr = "ما المقابل العربي الرسمي لمصطلح 'Letters of Credence' في التمثيل الخارجي؟",
            questionEn = "البروتوكول الدبلوماسي",
            optionsAr = listOf("أوراق الاعتماد", "رسائل التوصية", "جوازات السفر الدبلوماسية", "وثائق المعاهدات"),
            correctIndex = 0,
            explanationAr = "أوراق الاعتماد هي الوثيقة الرسمية التي يقدمها السفير الجديد لرئيس الدولة المعتمد لديها."
        ),
        ExamQuestion(
            id = 6,
            questionAr = "ما الفرق في الصياغة القانونية الإنجليزية بين 'Shall' و 'May'؟",
            questionEn = "الصياغة التشريعية الإنجليزية",
            optionsAr = listOf("Shall تفيد الوجوب والإلزام الصارم، بينما May تفيد الجواز والإذن التقديري", "Shall تفيد المستقبل البسيط فقط", "كلاهما متطابق في القوة الإلزامية", "May تستخدم فقط للأفراد و Shall للدول"),
            correctIndex = 0,
            explanationAr = "في العقود الدولية: Shall تفرض التزاماً قانونياً واجباً، و May تمنح خياراً أو رخصة جوازية."
        ),
        ExamQuestion(
            id = 7,
            questionAr = "ما هي الترجمة القانونية الصحيحة لمصطلح 'Without Prejudice' في المراسلات القضائية والتسويات؟",
            questionEn = "المصطلحات القانونية",
            optionsAr = listOf("مع عدم الإخلال بالحقوق / دون مساس بالدعوى الأصلية", "بدون أي تحيز عرقي", "بحكم قضائي بات", "وفقاً للعرف السائد"),
            correctIndex = 0,
            explanationAr = "عبارة 'دون إخلال' تحمي حقوق الطرف المفاوض وتمنع استخدام التنازلات المقترحة كإقرار إدانة ضده أمام المحكمة."
        ),
        ExamQuestion(
            id = 8,
            questionAr = "ما معنى مصطلح 'Persona non grata' في العلاقات الدبلوماسية الدولية؟",
            questionEn = "الأعراف الدبلوماسية",
            optionsAr = listOf("شخص غير مرغوب فيه (يطلب مغادرته لأراضي الدولة المعتمد لديها)", "دبلوماسي ذو حصانة مطلقة", "مترجم كابينة معتمد", "رئيس بعثة استثنائي"),
            correctIndex = 0,
            explanationAr = "إجراء بموجب اتفاقية فيينا للعلاقات الدبلوماسية تطلب بموجبه الدولة المستقبلة طرد الدبلوماسي دون إبداء الأسباب."
        ),
        ExamQuestion(
            id = 9,
            questionAr = "ما المقابل الدقيق لمصطلح 'Ipso Facto' في القرارات التعاقدية؟",
            questionEn = "المصطلحات اللاتينية",
            optionsAr = listOf("بحكم الواقع ذاته / تلقائياً بمجرد وقوع الفعل", "بأثر رجعي مدته سنة", "وفقاً لرغبة الطرفين", "بعد موافقة المحكمة"),
            correctIndex = 0,
            explanationAr = "العبارة تعني حدوث النتيجة القانونية تلقائياً بقوة الواقعة نفسها دون حاجة لإجراء إضافي."
        ),
        ExamQuestion(
            id = 10,
            questionAr = "في المعاهدات الدولية، ما الفرق بين 'Ratification' و 'Accession'؟",
            questionEn = "قانون المعاهدات الدولية",
            optionsAr = listOf("Ratification مصادقة دولة موقعة سابقاً، بينما Accession انضمام دولة لم تكن موقعة أصلاً على نص المعاهدة", "كلاهما يعني رفض المعاهدة", "Ratification للتحالفات العسكرية فقط", "لا يوجد أي فرق"),
            correctIndex = 0,
            explanationAr = "المصادقة تكون لمن وقع النص خلال مهلة التوقيع، بينما الانضمام لدولة لم توقع مسبقاً وتلتحق بالمعاهدة لاحقاً."
        ),
        ExamQuestion(
            id = 11,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Jus Cogens' في القانون الدولي العام؟",
            questionEn = "القواعد الآمرة",
            optionsAr = listOf("القواعد الآمرة في القانون الدولي (لا يجوز الاتفاق على مخالفتها)", "القوانين الوطنية المؤقتة", "الأعراف التجارية المحلية", "أحكام المحاكم الابتدائية"),
            correctIndex = 0,
            explanationAr = "Jus Cogens هي القواعد الدولية الآمرة القطعية (مثل حظر الإبادة الجماعية والقرصنة والرق)."
        ),
        ExamQuestion(
            id = 12,
            questionAr = "ما هي الترجمة القانونية الصحيحة لمصطلح 'Liquidated Damages' في عقود الفيدك (FIDIC)؟",
            questionEn = "عقود الإنشاءات والمقاولات",
            optionsAr = listOf("التعويضات الاتفاقية المحددة مسبقاً / الشرط الجزائي المتفق عليه", "تصفية الأصول المفلسة", "أضرار بيئية غير مباشرة", "تكاليف الشحن البحري"),
            correctIndex = 0,
            explanationAr = "التعويض المقدر سلفاً في العقد عن كل يوم أو أسبوع تأخير في إنجاز المشروع دون حاجة لإثبات الضرر."
        ),
        ExamQuestion(
            id = 13,
            questionAr = "ما معنى عبارة 'Ad Referendum' عند توقيع المفاوض على مسودة اتفاقية؟",
            questionEn = "إجراءات التوقيع الدولي",
            optionsAr = listOf("توقيع مشروط بمراجعة السلطة العليا المختصة وإقرارها", "توقيع نهائي غير قابل للطعن", "توقيع بالنيابة عن دولة ثالثة", "توقيع سري غير معلن"),
            correctIndex = 0,
            explanationAr = "التوقيع بشرط الرجوع للسلطة السياسية للتثبت من عدم تجاوز صلاحيات الوفد المفاوض."
        ),
        ExamQuestion(
            id = 14,
            questionAr = "ما هو المقابل الاصطلاحي العربي لمصطلح 'De Facto' مقابل 'De Jure'؟",
            questionEn = "المصطلحات المؤسساتية",
            optionsAr = listOf("بحكم الأمر الواقع مقابل بحكم القانون والشرعية الرسمية", "سري مقابل علني", "مؤقت مقابل دائم", "محلي مقابل دولي"),
            correctIndex = 0,
            explanationAr = "De Facto هو الواقع الفعلي المفروض، بينما De Jure هو الوضع الشرعي المعترف به قانوناً."
        ),
        ExamQuestion(
            id = 15,
            questionAr = "ما الترجمة الدقيقة لمصطلح 'Joint and Several Liability' في المعاملات المالية؟",
            questionEn = "المسؤولية القانونية",
            optionsAr = listOf("المسؤولية التضامنية والتكافلية (كل طرف مسؤول عن الكل وعن نصيبه)", "المسؤولية المحدودة بالأسهم فقط", "المسؤولية الجزائية المشروطة", "انعدام المسؤولية المالية"),
            correctIndex = 0,
            explanationAr = "تضامن المدينين بحيث يحق للدائن مطالبة أي طرف بكامل الدين أو مطالبة الجميع مجتمعين."
        ),
        ExamQuestion(
            id = 16,
            questionAr = "ما المقصود بـ 'Expressed Warranty' في عقود البيع التجاري الدولي؟",
            questionEn = "الضمانات العقدية",
            optionsAr = listOf("الضمان الصريح المنصوص عليه بوضوح في بنود العقد", "الضمان الضمني المفترض عرفاً", "الضمان الشفهي غير المسجل", "الضمان المنتهي الصلاحية"),
            correctIndex = 0,
            explanationAr = "الضمان الصريح هو التعهد التعاقدي المكتوب بوضوح خلافاً للضمان الضمني (Implied Warranty)."
        ),
        ExamQuestion(
            id = 17,
            questionAr = "ما المعنى الدقيق لعبارة 'Inter Alia' في التقارير الدولية؟",
            questionEn = "اللاتينية المؤسساتية",
            optionsAr = listOf("من بين أمور أخرى / من جملتها (دون حصر)", "في غياب الأطراف", "بناءً على طلب الرئيس", "في نهاية الجلسة"),
            correctIndex = 0,
            explanationAr = "عبارة تفيد التمثيل لا الحصر، أي أن القائمة المذكورة ليست على سبيل الإحاطة التامة."
        ),
        ExamQuestion(
            id = 18,
            questionAr = "ما المقابل العربي لمصطلح 'Indemnification Clause' في عقود الاستثمار؟",
            questionEn = "شروط التعويض",
            optionsAr = listOf("بند التعويض ودرء الضرر / بند جبر الخسائر", "بند الفائدة المصرفية", "بند تسليم البضائع", "بند الإعفاء الضريبي"),
            correctIndex = 0,
            explanationAr = "البند الذي يلزم أحد الطرفين بتعويض الآخر والدفاع عنه ضد أي مطالبات أو خسائر ناتجة عن خرق العقد."
        ),
        ExamQuestion(
            id = 19,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Arbitration Clause' في النزاعات العابرة للحدود؟",
            questionEn = "التحكيم الدولي",
            optionsAr = listOf("شرط التحكيم (إحالة أي نزاع لمحكمين بدلاً من القضاء الوطني)", "شرط الوساطة الودية فقط", "شرط الصلح الجنائي", "شرط مراجعة الأسعار"),
            correctIndex = 0,
            explanationAr = "بند بموجبه يتفق المتعاقدون على حل الخلافات عبر هيئات التحكيم الدولية (مثل ICC أو LCIA)."
        ),
        ExamQuestion(
            id = 20,
            questionAr = "ما المدلول الدقيق لـ 'Ex Gratia Payment' في التعويضات المالية؟",
            questionEn = "التعويضات التقديرية",
            optionsAr = listOf("مبلغ مدفوع طواعية وتفضلاً دون إقرار بأي مسؤولية قانونية", "مبلغ محكوم به قضائياً", "قرض مصرفي إجباري", "غرامة تهرب جمركي"),
            correctIndex = 0,
            explanationAr = "دفعة تسوية تُمنح تفضلاً من الدولة أو الشركة لجبر خواطر المتضررين دون الاعتراف بالذنب قانوناً."
        ),
        ExamQuestion(
            id = 21,
            questionAr = "ما المقابل الرسمي لمصطلح 'Procès-Verbal' في الاجتماعات والتحقيقات الرسمية؟",
            questionEn = "المصطلحات التوثيقية",
            optionsAr = listOf("محضر اجتماع رسمي / محضر ضبط قضائي", "تقرير مالي ختامي", "دعوة لحضور مؤتمر", "جدول الأعمال المقترح"),
            correctIndex = 0,
            explanationAr = "الوثيقة الإجرائية الموثقة لوقائع جلسة رسمية أو ضبط مخالفة قانونية."
        ),
        ExamQuestion(
            id = 22,
            questionAr = "ما الترجمة المعتمدة لمصطلح 'Sovereign Immunity' في القانون الدولي؟",
            questionEn = "الحصانة الدولية",
            optionsAr = listOf("الحصانة السيادية للدول (عدم خضوع الدولة وقضائها لمحاكم دولة أخرى)", "الحصانة الجمركية للركاب", "حق الفيتو في مجلس الأمن", "الحق في إعلان الحرب"),
            correctIndex = 0,
            explanationAr = "المبدأ الذي يمنع مقاضاة دولة ذات سيادة أو مصادرة أصولها في المحاكم المحلية لدولة أجنبية."
        ),
        ExamQuestion(
            id = 23,
            questionAr = "ما المعنى الدقيق لمصطلح 'Bilateral Agreement'؟",
            questionEn = "أنواع الاتفاقيات",
            optionsAr = listOf("اتفاقية ثنائية (بين طرفين أو دولتين فقط)", "اتفاقية ثلاثية الأطراف", "معاهدة دولية مفتوحة لجميع الأمم", "عقد عمل فردي"),
            correctIndex = 0,
            explanationAr = "الاتفاق الثنائي المبرم بين كيانين أو دولتين متعاقدتين فقط."
        ),
        ExamQuestion(
            id = 24,
            questionAr = "ما معنى مصطلح 'Subpoena' في النظام القضائي الأنجلو-أمريكي (Common Law)؟",
            questionEn = "الإجراءات القضائية",
            optionsAr = listOf("أمر استدعاء قضائي ملزم للمثول أو لتقديم مستندات تحت طائلة العقوبة", "حكم براءة نهائي", "عقد كفالة مالية", "مذكرة دفاع استئنافية"),
            correctIndex = 0,
            explanationAr = "أمر تصدره المحكمة يلزم الشخص بالشهادة أو إبراز مستندات تحت طائلة تهمة ازدراء المحكمة."
        ),
        ExamQuestion(
            id = 25,
            questionAr = "ما المقابل العربي الدقيق لعبارة 'In Witness Whereof' التي تسبق تواقيع العقود؟",
            questionEn = "خواتيم العقود القانونية",
            optionsAr = listOf("وإثباتاً لما تقدم / وإشهاداً على ما تقدم (وقع الطرفان)", "بناءً على طلب القاضي", "مع فائق التقدير والاحترام", "حسب الشريعة والقانون"),
            correctIndex = 0,
            explanationAr = "الصيغة البروتوكولية الختامية المعتمدة تاريخياً قبل تواقيع الأطراف والشهود في العقود والاتفاقيات."
        ),
        ExamQuestion(
            id = 26,
            questionAr = "ما هو المدى الزمني المثالي للفارق الصوتي (Décalage / Ear-Voice Span) في كابينات المؤتمرات الفورية؟",
            questionEn = "الترجمة الفورية للمؤتمرات",
            optionsAr = listOf("بين 2.0 إلى 4.0 ثوانٍ (حسب بنية اللغة المستهدفة وموضع الفعل)", "15 إلى 25 ثانية لتجميع الفقرة كاملة", "0.1 ثانية مطابقة فورية تامة بدون تأخير", "دقيقة كاملة بعد انتهاء المتحدث"),
            correctIndex = 0,
            explanationAr = "المدى الزمني الدقيق (EVS) يتيح للمترجم استيعاب المعنى واكتشاف الفعل دون إرهاق الذاكرة قصيرة المدى."
        ),
        ExamQuestion(
            id = 27,
            questionAr = "ما هي تقنية 'Salami Technique' المتبعة في الترجمة الفورية للمؤتمرات؟",
            questionEn = "استراتيجيات الترجمة الفورية",
            optionsAr = listOf("تقطيع الجمل الطويلة والمعقدة إلى جمل قصيرة ومتماسكة لتخفيف العبء الذهني", "ترجمة كل كلمة بكلمة حرفياً", "حذف الصفات والظروف", "التوقف عن الترجمة كل دقيقتين"),
            correctIndex = 0,
            explanationAr = "استراتيجية تفكيك التراكيب المعقدة والمتداخلة في لغة المصدر إلى جمل قصيرة ومباشرة ومترابطة في لغة الهدف."
        ),
        ExamQuestion(
            id = 28,
            questionAr = "ما هو 'الترجمة التتابعية' (Consecutive Interpreting) مقارنة بالفورية؟",
            questionEn = "أنماط الترجمة الشفهية",
            optionsAr = listOf("تحدث المترجم بعد انتهاء المتحدث من مقطعه معتمداً على التدوين والذاكرة", "تحدث المترجم في نفس اللحظة عبر الميكروفون", "ترجمة النصوص المكتوبة فقط في المكتب", "ترجمة الأفلام الوثائقية"),
            correctIndex = 0,
            explanationAr = "في التتابعية يستمع المترجم ويدون الملاحظات، ثم يقدم ترجمته بعد توقف المتحدث عن الكلام."
        ),
        ExamQuestion(
            id = 29,
            questionAr = "ما هو 'Chuchotage' (الترجمة الوشوشية أو الهمسية)؟",
            questionEn = "أنماط الترجمة الشفهية",
            optionsAr = listOf("الترجمة الفورية همساً لأذن مستمع واحد أو اثنين بدون معدات صوتية معقدة", "الترجمة عبر الراديو الوطني", "الصياغة القانونية المكتوبة", "تشفير المحادثات الدبلوماسية"),
            correctIndex = 0,
            explanationAr = "Chuchotage هو نمط فوري بدون كابينة يقف فيه المترجم خلف المستمع أو بجانبه ويهمس بالترجمة مباشرة."
        ),
        ExamQuestion(
            id = 30,
            questionAr = "ما هو مفهوم 'Relay Interpreting' (الترجمة عبر لغة وسيطة / الكابينة المحورية)؟",
            questionEn = "كابينات المؤتمرات الفورية",
            optionsAr = listOf("استخدام لغة كابينة وسيطة (Pivot) عندما لا يجيد المترجم لغة المصدر مباشرة", "ترجمة المؤتمر بعد أسبوع", "ترجمة الحوار عبر الهاتف المحمول", "تبديل المترجمين كل ساعة"),
            correctIndex = 0,
            explanationAr = "تستمع كابينة اللغة النادرة إلى ترجمة كابينة وسيطة (كالفرنسية أو الإنجليزية) وتترجم منها للمستمعين."
        ),
        ExamQuestion(
            id = 31,
            questionAr = "ما هو الحد الأقصى الموصى به لعمل مترجم المؤتمرات الفورية المنفرد في الكابينة؟",
            questionEn = "معايير الأداء والصحة المهنية",
            optionsAr = listOf("من 20 إلى 30 دقيقة متواصلة كحد أقصى قبل التبديل مع زميل الكابينة", "4 ساعات متواصلة بدون توقف", "يوم كامل 8 ساعات متواصلة", "5 دقائق فقط"),
            correctIndex = 0,
            explanationAr = "الإجهاد الذهني الشديد وانخفاض التركيز يفرضان وجود مترجمين اثنين يتبادلان كل 20 إلى 30 دقيقة."
        ),
        ExamQuestion(
            id = 32,
            questionAr = "ما هو 'Anticipation' (الاستباق أو التوقع الذهني) في الترجمة الفورية للألمانية؟",
            questionEn = "الاستباق اللغوي",
            optionsAr = listOf("توقع موضع الفعل المنفي أو المصرف في نهاية الجملة الألمانية قبل نطق المتحدث له", "تخمين اسم المؤتمر القادم", "مقاطعة المتحدث لتصحيح خطئه", "تسريع إيقاع الصوت"),
            correctIndex = 0,
            explanationAr = "نظراً لأن الألمانية تضع الفعل في آخر الجملة الثانوية، يجب على المترجم استباق المعنى لتفادي التوقف الطويل."
        ),
        ExamQuestion(
            id = 33,
            questionAr = "ما الدور الحيوي لمترجم الكابينة الثاني غير المتحدث (The Passive Partner)؟",
            questionEn = "العمل التشاركي في الكابينة",
            optionsAr = listOf("تدوين الأرقام والأسماء والمصطلحات الصعبة ومساعدة الزميل في المعاجم", "مغادرة الكابينة لشرب القهوة", "الاستماع للموسيقى بسماعاته", "تغيير إعدادات الصوت للجمهور"),
            correctIndex = 0,
            explanationAr = "الشريك غير المتحدث يقدم دعماً حاسماً عبر رصد الأرقام الصعبة وتدوينها على ورقة أمام زميله المتحدث."
        ),
        ExamQuestion(
            id = 34,
            questionAr = "ما هو 'Acoustic Shock' (الصدمة الصوتية) وكيف يتفاداه مترجم الكابينة؟",
            questionEn = "الصحة المهنية للمترجمين",
            optionsAr = listOf("ارتفاع مفاجئ وحاد في الصوت في السماعة يؤذي السمع ويتفادى بمحددات الصوت الرقمية", "صدمة ناتجة عن انقطاع التيار الكهربائي", "خوف المترجم من الجمهور", "عطل في ميكروفون الكابينة"),
            correctIndex = 0,
            explanationAr = "صدمة صوتية ناتجة عن صفير الميكروفونات؛ تتطلب تجهيزات ISO 2603 المزودة بنظام كبت الذروات (Limiters)."
        ),
        ExamQuestion(
            id = 35,
            questionAr = "ما هو المعيار الدولي القياسي لكابينات الترجمة الفورية الثابتة والمحمولة؟",
            questionEn = "معايير الآيزو لكابينات المؤتمرات",
            optionsAr = listOf("ISO 2603 (الثابتة) و ISO 4043 (المتنقلة)", "ISO 9001 فقط", "ISO 14001 للبيئة", "ISO 22000 للأغذية"),
            correctIndex = 0,
            explanationAr = "ISO 2603 و ISO 4043 تحددان متطلبات العزل الصوتي والرؤية المباشرة والتهوية لكابينات الترجمة."
        ),
        ExamQuestion(
            id = 36,
            questionAr = "ما المقصود بـ 'Sight Translation' (الترجمة الفورية الشفهية للنص المكتوب)؟",
            questionEn = "أنماط الترجمة الشفهية",
            optionsAr = listOf("قراءة وثيقة مكتوبة وترجمتها فورياً بصوت مسموع ومباشر", "ترجمة الرسوم البيانية فقط", "حفظ النص غيباً قبل أسبوع", "ترجمة لغة الإشارة"),
            correctIndex = 0,
            explanationAr = "الترجمة على الشاشة أو من الورقة فوراً بصوت جهوري دون كتابة مسبقة للنص الهدف."
        ),
        ExamQuestion(
            id = 37,
            questionAr = "في الترجمة الفورية، ماذا يفعل المترجم عند مواجهة نكتة أو مثل شعبي غير قابل للنقل؟",
            questionEn = "الاستراتيجيات الثقافية في الكابينة",
            optionsAr = listOf("نقل المعنى الدلالي العام أو شرح مقصده سريعاً أو توظيف مثل مكافئ في ثقافة الهدف", "الصمت التام لدقيقة كاملة", "ترجمة الكلمات حرفياً بلا معنى", "الاعتذار للجمهور وإغلاق الميكروفون"),
            correctIndex = 0,
            explanationAr = "إما توظيف مثل مكافئ وظيفياً، أو نقل الغرض البلاغي (ضحك أو تلطيف الأجواء) بلباقة تامة."
        ),
        ExamQuestion(
            id = 38,
            questionAr = "ما هو 'Shadowing' (التظليل الصوتي) وما فائدته الأكاديمية؟",
            questionEn = "تدريب مترجمي الكابينات",
            optionsAr = listOf("تكرار كلام المتحدث في نفس اللحظة بنفس لغته لتطوير مرونة السمع والنطق المتزامن", "ترجمة الظلال في المسرح", "تسجيل الصوت في غرفة مظلمة", "إخفاء هوية المترجم"),
            correctIndex = 0,
            explanationAr = "تمرين تدريبي أساسي في مدارس الترجمة لفك الارتباط بين مهارة الاستماع ومهارة التعبير الصوتي المتزامن."
        ),
        ExamQuestion(
            id = 39,
            questionAr = "ما هو الترتيب المكاني الأمثل لموقع كابينة الترجمة في قاعة المؤتمر؟",
            questionEn = "هندسة قاعات المؤتمرات",
            optionsAr = listOf("مواجهة مباشرة للمنصة والشاشات مع رؤية بصرية غير محجوبة للمتحدثين", "خلف الجدران الخرسانية بدون نوافذ", "في الطابق السفلي تحت الأرض", "بجوار المطبخ وبوفيه الطعام"),
            correctIndex = 0,
            explanationAr = "تشترط معايير الاتحاد الدولي لمترجمي المؤتمرات (AIIC) رؤية بصرية كاملة ومباشرة للمتحدث وحركاته وإيماءاته."
        ),
        ExamQuestion(
            id = 40,
            questionAr = "ما وظيفة زر 'Mute / Cough Button' في وحدة تحكم مترجم الكابينة؟",
            questionEn = "تجهيزات كابينة الترجمة",
            optionsAr = listOf("كتم الميكروفون لحظياً للسعال أو شرب الماء دون وصول الصوت للمستمعين", "إيقاف ترجمة الزميل", "رفع مستوى الصوت في القاعة", "تسجيل الكلمة في الأرشيف"),
            correctIndex = 0,
            explanationAr = "زر الكتم اللحظي يمنع انتقال أصوات السعال أو النحنحة أو تنفس المترجم إلى سماعات المندوبين."
        ),
        ExamQuestion(
            id = 41,
            questionAr = "ما هو تصنيف اللغات في الاتحاد الدولي لمترجمي المؤتمرات (AIIC)؟",
            questionEn = "معايير AIIC المهنية",
            optionsAr = listOf("اللغة A (الأم)، واللغة B (اللغة النشطة الثانية)، واللغة C (اللغة السلبية للاستيعاب فقط)", "اللغة 1 واللغة 2 واللغة 3 حسب السعر", "لغات شرقية وغربية", "لغات رسمية ولغات عامية"),
            correctIndex = 0,
            explanationAr = "تصنيف AIIC: اللغة A ينقل إليها بحرية تامة؛ اللغة B ينقل إليها ومنها؛ اللغة C يفهمها وينقل منها فقط للغته الأم."
        ),
        ExamQuestion(
            id = 42,
            questionAr = "ما هو 'Retour Interpreting' (الترجمة العكسية)؟",
            questionEn = "أنماط الترجمة الشفهية",
            optionsAr = listOf("الترجمة من لغة المترجم الأم (A) إلى لغته النشطة الثانية (B)", "العودة للترجمة بعد التقاعد", "ترجمة المسودات القديمة", "تكرار الجملة بالعكس"),
            correctIndex = 0,
            explanationAr = "الترجمة العكسية (Retour) تعني ترجمة المترجم من لغته الأم إلى لغته الأجنبية الثانية المتقنة."
        ),
        ExamQuestion(
            id = 43,
            questionAr = "ما هي استراتيجية 'Reformulation' (إعادة الصياغة الذكية) في الترجمة الفورية؟",
            questionEn = "البلاغة الشفهية في الكابينة",
            optionsAr = listOf("إعادة التعبير عن الفكرة بكلمات موجزة وأسلوب سليم دون التقيد بالبنية التركيبية للأصل", "حذف الجملة الصعبة بالكامل", "تغيير موقف المتحدث ورأيه", "إضافة معلومات من الذاكرة الشخصية"),
            correctIndex = 0,
            explanationAr = "إعادة سبك المعنى بأسلوب سلس ومباشر يراعي فصاحة لغة الهدف ويوصل الرسالة بأقصر طريق."
        ),
        ExamQuestion(
            id = 44,
            questionAr = "ما المقصود بـ 'Décalage Management' عندما يتحدث الخطيب بسرعة فائقة غير اعتيادية؟",
            questionEn = "التحكم في سرعة الإلقاء",
            optionsAr = listOf("تكثيف الترجمة وتطبيق التلخيص الذكي والتركيز على الأفكار المحورية والمعطيات", "الصراخ في الميكروفون", "طلب خفض الإضاءة في القاعة", "التوقف عن متابعة الخطيب نهائياً"),
            correctIndex = 0,
            explanationAr = "عند السرعة الفائقة يلجأ المترجم إلى ضغط النص (Condensation) وحذف الحشو والإبقاء على جوهر الرسالة."
        ),
        ExamQuestion(
            id = 45,
            questionAr = "ما هو واجب 'السرية المهنية' (Professional Secrecy) لمترجم المؤتمرات؟",
            questionEn = "أخلاقيات مهنة الترجمة",
            optionsAr = listOf("الالتزام التام بعدم إفشاء أو استغلال أي معلومات تم الاطلاع عليها في الاجتماعات المغلقة", "إخبار وسائل الإعلام بما دار في الكواليس", "كتابة مذكرات شخصية عن أسرار الرؤساء", "بيع الوثائق للشركات المنافسة"),
            correctIndex = 0,
            explanationAr = "أخلاقيات AIIC و ISO تلزم المترجم بالصمت التام وكتمان أسرار الاجتماعات المغلقة طوال حياته المهنية."
        ),
        ExamQuestion(
            id = 46,
            questionAr = "ما المقصود بـ 'Simultaneous Interpretation with Text' في الكابينة؟",
            questionEn = "استراتيجيات الكابينة",
            optionsAr = listOf("متابعة النص المكتوب أمام المترجم مع الاستماع الحي لتصويبات المتحدث المرتجلة", "قراءة النص المطبوع بصوت آلي دون الاستماع للمتحدث", "ترجمة النص كتابياً على ورقة بيضاء", "تجاهل الصوت والاكتفاء بالنص"),
            correctIndex = 0,
            explanationAr = "المترجم يقرأ النص ويتابع بأذنيه ليرصد أي فقرات يتجاوزها المتحدث أو يضيفها بصورة مفاجئة."
        ),
        ExamQuestion(
            id = 47,
            questionAr = "ما هي 'Déformation Professionnelle' الشائعة لمترجمي الكابينات؟",
            questionEn = "علم النفس اللغوي للمترجمين",
            optionsAr = listOf("فرط الحساسية للأخطاء اللغوية في الحياة اليومية ومقاطعة المتحدثين تلقائياً", "فقدان القدرة على الكتابة بالقلم", "نسيان اللغة الأم بالكامل", "الرغبة في التحدث بالإنجليزية فقط"),
            correctIndex = 0,
            explanationAr = "ظاهرة نفسية يصبح فيها المترجم مفرط التدقيق في مخارج الحروف وصياغة الجمل في الحوارات العادية."
        ),
        ExamQuestion(
            id = 48,
            questionAr = "ما هو 'Cognitive Load' (العبء المعرفي) في الترجمة الشفهية؟",
            questionEn = "علم الأعصاب والترجمة",
            optionsAr = listOf("الضغط الذهني الناتج عن تزامن عمليات الاستماع والتحليل والتخزين والإنتاج في آن واحد", "وزن الكتب والقواميس الورقية", "تعب الحبال الصوتية فقط", "درجة حرارة الكابينة"),
            correctIndex = 0,
            explanationAr = "تعدد المهام المعرفية المتزامنة في أجزاء من الثانية يستهلك الطاقة الذهنية ويتطلب تركيزاً استثنائياً."
        ),
        ExamQuestion(
            id = 49,
            questionAr = "ما هو 'Ear-Voice Span Compression'؟",
            questionEn = "تقنيات الكابينة المتقدمة",
            optionsAr = listOf("تقليص الفارق الزمني عبر التوقع المعجمي عند قرب انتهاء الخطاب أو ضيق الوقت", "إغلاق إحدى الأذنين باليد", "خفض نبرة الصوت في السماعة", "التحدث دون توقف"),
            correctIndex = 0,
            explanationAr = "تقليص زمن الانتظار بذكاء لتفادي تراكم الكلام غير المترجم في ذاكرة العمل قصيرة المدى."
        ),
        ExamQuestion(
            id = 50,
            questionAr = "ما هي مواصفة الجودة المعتمدة دولياً لخدمات الترجمة الشفهية؟",
            questionEn = "معايير الآيزو الدولية",
            optionsAr = listOf("ISO 18841:2018 (Interpretation Services)", "ISO 9001 العامة", "ISO 27001 لأمن المعلومات", "ISO 45001 للسلامة"),
            correctIndex = 0,
            explanationAr = "ISO 18841:2018 هي المواصفة القياسية العالمية المتخصصة في المبادئ والمتطلبات الأساسية لخدمات الترجمة الشفهية."
        ),
        ExamQuestion(
            id = 51,
            questionAr = "ما هي القاعدة الذهبية الأولى لنظام روزان (Jean-François Rozan 1956) في التدوين التتابعي؟",
            questionEn = "نظام روزان للترجمة التتابعية",
            optionsAr = listOf("تدوين الفكرة والمعنى بدلاً من الكلمات الحرفية (Transposition de l'idée plutôt que du mot)", "كتابة كل كلمة كاملة دون اختصارات", "الاعتماد على التسجيل الصوتي بدون رموز", "ترجمة الجملة إلى الإنجليزية أولاً قبل التدوين"),
            correctIndex = 0,
            explanationAr = "المبدأ الأول لروزان: يجب تسجيل الفكرة المجردة برمز دال وليس اللفظ الحرفي المنطوق."
        ),
        ExamQuestion(
            id = 52,
            questionAr = "ما هو مبدأ 'Verticality' (العمودية) في قواعد روزان السبع للتدوين؟",
            questionEn = "نظام روزان للتدوين",
            optionsAr = listOf("كتابة عناصر الجملة رأسياً من الأعلى إلى الأسفل لتسهيل مسح العين وقراءة الفكرة بنظرة واحدة", "كتابة سطر واحد يمتد عبر كامل الصفحة", "تدوين الملاحظات بشكل دائري", "استخدام خطوط عمودية ملونة"),
            correctIndex = 0,
            explanationAr = "التدوين العمودي (Verticality) يفكك تداخل الكلمات ويجعل الفاعل والحدث والنتيجة تتدفق رأسياً."
        ),
        ExamQuestion(
            id = 53,
            questionAr = "ما هو مبدأ 'Shift' (الإزاحة أو التدرج الأفقي) عند روزان؟",
            questionEn = "نظام روزان للتدوين",
            optionsAr = listOf("إزاحة العناصر التابعة للسطر التالي نحو اليمين أو اليسار لبيان تبعيتها للعنصر الرئيس", "كتابة الكلمات بحجم خط مائل", "تغيير الدفتر كل خمس دقائق", "تحريك القلم بسرعة فائقة"),
            correctIndex = 0,
            explanationAr = "الإزاحة التدرجية توضح العلاقات التبعية كالصفات والظروف تحت المتبوع مباشرة كشجرة دلالية."
        ),
        ExamQuestion(
            id = 54,
            questionAr = "ما هو الرمز المعتمد عالمياً في التدوين لتمثيل مفهوم 'الزيادة أو النمو أو التصاعد'؟",
            questionEn = "رموز التدوين التتابعي",
            optionsAr = listOf("سهم مائل للأعلى (↗ أو ↑)", "علامة استفهام مقلوبة", "دائرة مغلقة", "حرف X كبير"),
            correctIndex = 0,
            explanationAr = "الأسهم من الرموز الأساسية عند روزان للدلالة على الاتجاه (صعود، هبوط، تبادل، تقدم)."
        ),
        ExamQuestion(
            id = 55,
            questionAr = "كيف يدون مترجم التتابعية روابط العطف والسببية (الروابط المنطقية) وفق نظام روزان؟",
            questionEn = "قواعد روزان السبع",
            optionsAr = listOf("تدوينها في أقصى هامش الصفحة الأيسر/الأيمن لبيان الانتقال المنطقي بوضوح", "كتابتها بحروف كبيرة في المنتصف", "تجاهلها تماماً لعدم أهميتها", "وضع نقطتين فقط"),
            correctIndex = 0,
            explanationAr = "الروابط المنطقية (Articulation) هي عصب الخطاب؛ وتوضع في الهامش لتبين التحول الذهني (لكن، إذن، بما أن)."
        ),
        ExamQuestion(
            id = 56,
            questionAr = "كيف يرمز روزان للأزمنة الماضية والمستقبلية في تدوين الأفعال؟",
            questionEn = "تدوين الأزمنة عند روزان",
            optionsAr = listOf("وضع خط مائل أو مؤشر فوق رمز الفعل للماضي، أو سهم أمامي للمستقبل", "كتابة السنة الكاملة بالأرقام", "رسم ساعة رملية", "استخدام لون أحمر للمستقبل"),
            correctIndex = 0,
            explanationAr = "التعبير عن الزمن برمز إشاري فوق رمز الفعل لتفادي كتابة صيغة التصريف الطويلة."
        ),
        ExamQuestion(
            id = 57,
            questionAr = "ما هو رمز النفي القياسي والسريع في دفتر التدوين التتابعي؟",
            questionEn = "رموز نظام روزان",
            optionsAr = listOf("شطب الرمز بخط مائل أو وضع دائرة بداخلها خط (Ø) أو علامة (NO)", "كتابة كلمة Not كاملة بخط عريض", "رسم سحابة سوداء", "ترك سطر فارغ في الدفتر"),
            correctIndex = 0,
            explanationAr = "الشطب المائل أو علامة الشطب تلغي المعنى مباشرة وتدل على الامتناع أو النفي القطعي."
        ),
        ExamQuestion(
            id = 58,
            questionAr = "ما نوع الدفتر الموصى به عالمياً لتدوين الترجمة التتابعية؟",
            questionEn = "تجهيزات مترجم التتابعية",
            optionsAr = listOf("دفتر حلزوني علوي بحجم الجيب (Bloc-notes à spirale supérieure) مقاس A5 أو B6", "دفتر جامعي كبير A4 بغلاف مقوى ثقيل", "أوراق بيضاء مفردة غير مثبتة", "دفتر مدرسي مسطر بالمربعات"),
            correctIndex = 0,
            explanationAr = "الدفتر الحلزوني العلوي يتيح قلب الصفحات بيد واحدة بسرعة فائقة دون إصدار أي صوت يشتت الخطاب."
        ),
        ExamQuestion(
            id = 59,
            questionAr = "ما المقصود بـ 'Non-verbal Note-taking' في مدارس الترجمة العليا (ESIT و FTI)؟",
            questionEn = "نظريات التدوين التتابعي",
            optionsAr = listOf("استخدام رموز تصويرية ومجردة مستقلة عن ألفاظ أي لغة لتسهيل القراءة بأي لغة هدف", "التحدث بالإشارات فقط دون كلام", "الرسم التشكيلي الزيتي", "التصوير الفوتوغرافي"),
            correctIndex = 0,
            explanationAr = "الرمز اللالفظي يعبر عن المفهوم المحض مما يحرر المترجم من فخ الترجمة الحرفية لألفاظ المصدر."
        ),
        ExamQuestion(
            id = 60,
            questionAr = "في نظام روزان، ما الرمز الدال على 'الدولة أو الأمة أو الشعب'؟",
            questionEn = "رموز التدوين التتابعي",
            optionsAr = listOf("دائرة بداخلها نقطة أو الرمز المعتمد للبلد (مثل DZ, FR, US)", "رسم شجرة خضراء", "كتابة رقم الهاتف", "مربع فارغ تماماً"),
            correctIndex = 0,
            explanationAr = "رموز الهيئات والشعوب تختصر بوضع رمز الدائرة أو كود الدولة الإيزو الدولي."
        ),
        ExamQuestion(
            id = 61,
            questionAr = "وفقاً للمواصفة القياسية العالمية ISO 17100:2015، ما هي مرحلة 'Revision' الإلزامية؟",
            questionEn = "معايير الجودة والاعتماد ISO",
            optionsAr = listOf("فحص ثنائي اللغة يقارن النص الهدف بالنص المصدر بواسطة مدقق ثانٍ مؤهل ومستقل", "التدقيق الإملائي الآلي عبر برنامج Word", "مراجعة المترجم نفسه لنصه بعد أسبوع", "طباعة الوثيقة وختمها مباشرة دون فحص"),
            correctIndex = 0,
            explanationAr = "المواصفة تشترط إلزامياً وجود مرحلة Revision يقوم بها مراجع ثانٍ مستقل ومؤهل يقارن النصين سطراً بسطر."
        ),
        ExamQuestion(
            id = 62,
            questionAr = "ما الفرق في ISO 17100 بين 'Revision' و 'Review'؟",
            questionEn = "مصطلحات معايير ISO 17100",
            optionsAr = listOf("Revision فحص مقارن ثنائي اللغة، بينما Review فحص أحادي للغة الهدف لتقييم ملاءمتها للغرض", "Revision للمترجم نفسه و Review للمدير", "كلاهما مترادفان تماماً", "Review تشمل التدقيق المطبعي فقط"),
            correctIndex = 0,
            explanationAr = "الريفيجن مقارنة ثنائية لغوية كاملة؛ أما الريفيو فهو تدقيق أحادي للغة الهدف في سياق تخصصي محدد."
        ),
        ExamQuestion(
            id = 63,
            questionAr = "ما هي المؤهلات الإلزامية التي تشترطها مواصفة ISO 17100 في المترجم المعتمد؟",
            questionEn = "كفاءة المترجمين في ISO 17100",
            optionsAr = listOf("شهادة جامعية معترف بها في الترجمة، أو شهادة في تخصص آخر مع سنتين خبرة، أو 5 سنوات خبرة مثبتة", "معرفة التحدث بلغة ثانية في السفر", "امتلاك حساب بنكي دولي", "إتقان الكتابة السريعة على لوحة المفاتيح"),
            correctIndex = 0,
            explanationAr = "تحدد المواصفة بدقة معايير الكفاءة اللغوية والترجمية والتقنية والبحثية للمترجمين المحترفين."
        ),
        ExamQuestion(
            id = 64,
            questionAr = "ماذا تعني مرحلة 'Proofreading' (التدقيق النهائي / تصحيح التجارب الطباعية) في الترجمة؟",
            questionEn = "مراحل الجودة في ISO 17100",
            optionsAr = listOf("فحص البروفة النهائية المنسقة قبل الطباعة والنشر لاكتشاف الأخطاء المطبعية والبصرية", "إعادة ترجمة النص بالكامل", "حذف الفقرات غير المهمة", "التفاوض مع العميل على السعر"),
            correctIndex = 0,
            explanationAr = "مرحلة فحص شكل الإخراج النهائي (Layout / Formatting) والتأكد من سلامة علامات الترقيم وخلوها من السهو."
        ),
        ExamQuestion(
            id = 65,
            questionAr = "ما هو 'Translation Memory' (ذاكرة الترجمة TM) في برمجيات الترجمة بمساعدة الحاسوب (CAT)؟",
            questionEn = "تقنيات الترجمة CAT Tools",
            optionsAr = listOf("قاعدة بيانات تخزن الجمل المصدر وما يقابلها في الهدف لإعادة استخدامها ومنع التكرار", "ذاكرة الوصول العشوائي للكمبيوتر RAM", "ذاكرة المترجم البشرية البيولوجية", "ملف نصي عادي يحفظ الأسماء"),
            correctIndex = 0,
            explanationAr = "قاعدة بيانات مقاطع (Segments) ثنائية اللغة تضمن الاتساق التام وتسرع ترجمة المشاريع المتكررة."
        ),
        ExamQuestion(
            id = 66,
            questionAr = "ما هو الفرق بين 'Translation Memory' (TM) و 'Termbase' (TB)؟",
            questionEn = "تقنيات الترجمة بمساعدة الحاسوب",
            optionsAr = listOf("TM تخزن جملاً ومقاطع كاملة، بينما TB تخزن مصطلحات ومفردات مفردة مع تعريفاتها", "TM مجانية و TB مدفوعة", "TM تستخدم للفرنسية فقط و TB للعربية", "لا يوجد أي فرق وظيفي"),
            correctIndex = 0,
            explanationAr = "ذاكرة الترجمة تخزن فقرات وجمل كاملة سابقة؛ وبنك المصطلحات يخزن مفاهيم ومعاجم ومصطلحات محددة."
        ),
        ExamQuestion(
            id = 67,
            questionAr = "في الترجمة القانونية المحلفة، ماذا يتضمن 'إقرار المترجم المحلف' (Sworn Translator's Jurat)؟",
            questionEn = "الترجمة المحلفة الرسمية",
            optionsAr = listOf("تصريح رسمي بالاسم والصفة واليمين القانونية يفيد مطابقة الترجمة للأصل المرفق بدقة وأمانة مع الختم", "فاتورة الحساب البنكي", "تاريخ ميلاد العميل", "شرح تفصيلي لقواعد النحو"),
            correctIndex = 0,
            explanationAr = "الإقرار القانوني المذيل بالختم الرسمي وتوقيع المترجم المسجل في المحكمة يمنح الوثيقة حجيتها القضائية الرسمية."
        ),
        ExamQuestion(
            id = 68,
            questionAr = "ما هي الصيغة الصحيحة للتعامل مع الاختصارات غير المعروفة في النص المصدر؟",
            questionEn = "منهجية الترجمة المهنية",
            optionsAr = listOf("البحث المعجمي والاتصال بالعميل أو وضع حاشية تفسيرية توضح المراد مع إبقاء الأصل إن لزم", "تجاهل الاختصار وحذفه دون إشعار", "تخمين معنى عشوائي", "استبدال الاختصار بكلمة عامة"),
            correctIndex = 0,
            explanationAr = "الأمانة تقتضي التثبت؛ وفي حال تعذر ذلك يتم إخطار العميل أو إيضاح السياق بحاشية المترجم (م. ت)."
        ),
        ExamQuestion(
            id = 69,
            questionAr = "ما هو 'Concordance' في برمجيات الترجمة الحديثة؟",
            questionEn = "أدوات البحث في CAT Tools",
            optionsAr = listOf("البحث السريع عن سياقات استخدام كلمة أو مصطلح محدد داخل جميع مشاريع ذاكرة الترجمة السابقة", "التدقيق الإملائي الآلي", "حساب عدد الكلمات", "إرسال ملف الترجمة للعميل"),
            correctIndex = 0,
            explanationAr = "أداة تبحث عن الكلمة داخل سياقات حقيقية في ذاكرة الترجمة لمعرفة الصياغة المعتمدة سابقاً."
        ),
        ExamQuestion(
            id = 70,
            questionAr = "ما هي مواصفة الجودة الدولية المعنية بإدارة مكاتب وشركات الترجمة؟",
            questionEn = "إدارة جودة الترجمة",
            optionsAr = listOf("ISO 17100:2015 لخدمات الترجمة", "ISO 22301 لإدارة الأزمات", "ISO 31000 للمخاطر", "ISO 50001 للطاقة"),
            correctIndex = 0,
            explanationAr = "ISO 17100:2015 هي المعيار الدولي الأعلى الذي يعتمد إدارة مشروعات الترجمة وتأهيل كوادرها."
        ),
        ExamQuestion(
            id = 71,
            questionAr = "في الترجمة السمعية البصرية، ما هي 'قاعدة السطرين' (Two-line limit) في الترجمة التحتية؟",
            questionEn = "معايير الترجمة السمعية البصرية",
            optionsAr = listOf("ألا يتجاوز السطرين لكل مقطع ترجمة تحتية لمنع حجب الرؤية عن المشهد البصري", "أن تكون الترجمة في سطرين فقط في الفيلم كامل", "أن يكتب المترجم سطرين في اليوم فقط", "أن يقرأ المترجم سطرين بصوت عال"),
            correctIndex = 0,
            explanationAr = "المعايير الدولية للترجمة التحتية تحظر كتابة أكثر من سطرين معاً في أسفل الشاشة للمحافظة على الرؤية."
        ),
        ExamQuestion(
            id = 72,
            questionAr = "ما هو الحد الأقصى لعدد الحروف في السطر الواحد في ترجمة شاشات السينما والتلفزيون؟",
            questionEn = "الترجمة التحتية الاحترافية",
            optionsAr = listOf("بين 37 إلى 42 حرفاً كحد أقصى للسطر الواحد (وفق معايير نتفليكس وبي بي سي)", "80 إلى 100 حرف في السطر", "10 حروف فقط", "لا يوجد أي قيود"),
            correctIndex = 0,
            explanationAr = "الحد القياسي الدولي يتراوح بين 37 و 42 حرفاً لضمان عدم امتداد النص خارج الشاشة وسهولة قراءته."
        ),
        ExamQuestion(
            id = 73,
            questionAr = "ماذا يعني مفهوم 'Transcreation' (الترجمة الإبداعية) في التسويق والإعلانات؟",
            questionEn = "الترجمة الإبداعية التسويقية",
            optionsAr = listOf("إعادة صياغة الرسالة الإعلانية بروح وثقافة جديدة تحقق نفس الأثر العاطفي والتسويقي", "الترجمة الحرفية الدقيقة لكل كلمة", "ترجمة النصوص التقنية البحرية", "ترجمة العقود القانونية"),
            correctIndex = 0,
            explanationAr = "Transcreation تمزج بين الترجمة والكتابة الإعلانية الإبداعية لمواءمة الحملات التجارية مع ثقافة المستهلك المحلي."
        ),
        ExamQuestion(
            id = 74,
            questionAr = "في تدقيق الجودة اللغوية (LQA)، ما هو تصنيف 'Critical Error' (الخطأ الجسيم)؟",
            questionEn = "تقييم الجودة اللغوية LQA",
            optionsAr = listOf("خطأ يغير معنى النص بالكامل، كقلب الإيجاب إلى نفي أو تحريف رقم بند قانوني أو شرط جزائي", "خطأ في فاصلة منقوطة", "اختيار كلمة فصيحة بدلاً من عامية", "تغيير طفيف في نوع الخط"),
            correctIndex = 0,
            explanationAr = "الخطأ الحرج (Critical Error) هو الخطأ الذي يترتب عليه ضرر قانوني أو مالي أو إخلال فادح بمعنى الرسالة."
        ),
        ExamQuestion(
            id = 75,
            questionAr = "ما هي القاعدة الذهبية لأخلاقيات المترجم عند تضارب المصالح (Conflict of Interest)؟",
            questionEn = "ميثاق الشرف وأخلاقيات المهنة",
            optionsAr = listOf("الإفصاح الفوري للعميل والاعتذار عن تولي المهمة أو الاستجابة لتوجيهات الإدارة القانونية المحايدة", "الاستمرار في الترجمة في سرية تامة", "مشاركة المعلومات مع الأصدقاء", "طلب أتعاب مضاعفة للتكتم"),
            correctIndex = 0,
            explanationAr = "النزاهة المهنية تقتضي إعلان أي تضارب محتمل للمصالح والامتناع عن المشاركة لضمان الحياد المطلق."
        ),
        ExamQuestion(
            id = 76,
            questionAr = "ما هي مواصفة الجودة الدولية الخاصة بالتحرير اللاحق للترجمة الآلية (MTPE)؟",
            questionEn = "معايير ISO للترجمة الآلية",
            optionsAr = listOf("ISO 18587:2017 (Post-editing of machine translation)", "ISO 9001", "ISO 14001", "ISO 27001"),
            correctIndex = 0,
            explanationAr = "ISO 18587 هي المواصفة القياسية العالمية المخصصة لعمليات ومتطلبات التحرير اللاحق لمخرجات الترجمة الآلية."
        ),
        ExamQuestion(
            id = 77,
            questionAr = "ما الفرق في ISO 18587 بين 'Full Post-Editing' و 'Light Post-Editing'؟",
            questionEn = "مستويات التحرير اللاحق MTPE",
            optionsAr = listOf("Full ينتج نصاً عالي الجودة يقارن بالبشري تماماً، بينما Light يركز فقط على قابلية الفهم وتصحيح المعنى الجسيم دون تحسين الأسلوب", "Full للترجمة الشفهية و Light للكتابية", "Light للمبتدئين و Full للخبراء", "كلاهما متطابق في التكلفة والوقت"),
            correctIndex = 0,
            explanationAr = "التحرير الكامل ينتج نصاً احترافياً مساوياً للترجمة البشرية، بينما الخفيف يكتفي بصحة المعنى الأساسي دون صقل أسلوبي."
        ),
        ExamQuestion(
            id = 78,
            questionAr = "ما هو 'Apostille' (الأبوستيل) في التصديق على الوثائق والمستندات الرسمية المترجمة؟",
            questionEn = "تصديق وتوثيق المستندات",
            optionsAr = listOf("شهادة تصديق دولية وفق اتفاقية لاهاي 1961 تلغي الحاجة للتصديق القنصلي المطول بين الدول الأعضاء", "ختم بنكي للتحويل المالي", "تأشيرة دخول سياحية", "شهادة ميلاد مترجمة"),
            correctIndex = 0,
            explanationAr = "شهادة الأبوستيل تضفي الشرعية القانونية الدولية المباشرة على الوثائق العامة الرسمية المترجمة بين الدول الأعضاء في لاهاي."
        ),
        ExamQuestion(
            id = 79,
            questionAr = "ما المقصود بـ 'Incoterms' (مصطلحات التجارة الدولية) في ترجمة عقود الاستيراد والتصدير؟",
            questionEn = "عقود التجارة والشحن الدولي",
            optionsAr = listOf("قواعد نموذجية صادرة عن غرفة التجارة الدولية (ICC) تحدد مسؤوليات ونفقات تسليم البضائع (مثل FOB, CIF, EXW)", "أسماء موانئ عالمية مشهورة", "قوانين التأمين الصحي للبحارة", "عملات إلكترونية مشفرة"),
            correctIndex = 0,
            explanationAr = "شروط تجارية قياسية دولية كـ FOB (التسليم على ظهر السفينة) و CIF (التكلفة والتأمين والشحن) تحسم مسؤولية المخاطر ونقاط التسليم."
        ),
        ExamQuestion(
            id = 80,
            questionAr = "في عقود الفيدك (FIDIC)، ما هو دور 'The Engineer' (المهندس المشرف) من منظور قانوني؟",
            questionEn = "عقود الهندسة والإنشاءات",
            optionsAr = listOf("طرف محايد تعينه جهة العمل لإدارة العقد ومراقبة الجودة وإصدار شهادات الدفع والبت الأولي في المطالبات", "عامل بناء بسيط", "محامي القضايا الجنائية", "سفير الدولة المضيفة"),
            correctIndex = 0,
            explanationAr = "المهندس في عقود FIDIC يمارس دوراً مزدوجاً كوكيل لرب العمل ومحكّم فني أولي مستقل بين المقاول وصاحب المشروع."
        ),
        ExamQuestion(
            id = 81,
            questionAr = "ما هي 'Severability Clause' (بند قابلية الفصل أو التجزئة) في صياغة العقود الدولية؟",
            questionEn = "بنود العقود النموذجية",
            optionsAr = listOf("بند ينص على أن بطلان أحد بنود العقد لا يؤدي إلى بطلان سائر البنود الأخرى التي تظل سارية المفعول", "بند يلزم الموظف بعدم الاستقالة", "بند لتقسيم الأرباح مناصفة", "بند يحدد لغة التحكيم"),
            correctIndex = 0,
            explanationAr = "بند جوهري يحمي بقاء العقد نافذاً إذا قضت المحكمة بعدم قانونية فقرة أو التزام فرعي محدد دون إسقاط كامل الاتفاق."
        ),
        ExamQuestion(
            id = 82,
            questionAr = "ما الترجمة الدقيقة لمصطلح 'Entire Agreement Clause' (بند الاتفاق الكامل أو النهائي)؟",
            questionEn = "الصياغة التعاقدية",
            optionsAr = listOf("بند يقر بأن هذا العقد يلغي ويجبّ كافة التفاهمات والمراسلات والمفاوضات السابقة ويشكل الاتفاق الحصري بين الطرفين", "بند يلزم بتوقيع عقد ثانٍ بعد أسبوع", "بند يشترط ترجمة العقد لكل لغات العالم", "بند ينفي وجود أي شهود"),
            correctIndex = 0,
            explanationAr = "يعرف أيضاً بـ 'Merger Clause' أو 'Four Corners Clause'؛ يمنع الاعتداد بأي وعود شفهية أو إيميلات سابقة خارج نص العقد النهائي."
        ),
        ExamQuestion(
            id = 83,
            questionAr = "ما المقصود بـ 'Term Extraction' (استخراج المصطلحات الآلي) في مشروعات الترجمة الضخمة؟",
            questionEn = "إدارة المصطلحات والمعاجم",
            optionsAr = listOf("تحليل النص المصدر برمجياً لتحديد المفردات والمفاهيم الفنية المتكررة لإنشاء بنك مصطلحات موحد قبل البدء", "حذف الكلمات الصعبة من الملف", "طباعة الفهرس الورقي", "حساب عدد الصفحات المترجمة"),
            correctIndex = 0,
            explanationAr = "مرحلة تحضيرية حاسمة تضمن استخراج الكلمات المفتاحية ومصطلحات العميل لتوحيدها مسبقاً بين فريق المترجمين والمراجعين."
        ),
        ExamQuestion(
            id = 84,
            questionAr = "ما هو 'Fuzzy Match' (المطابقة التقريبية) في برمجيات الترجمة التفاعلية؟",
            questionEn = "ذاكرات الترجمة CAT Tools",
            optionsAr = listOf("مقطع جديد يتطابق جزئياً مع مقطع مخزن سابقاً بنسبة مئوية (مثل 75% إلى 99%) مما يتطلب تعديلاً يدوياً طفيفاً", "مطابقة تامة 100%", "جملة غير مترجمة إطلاقاً 0%", "خطأ تقني في الشاشة"),
            correctIndex = 0,
            explanationAr = "المطابقة التقريبية تفيد أن الفكرة مترجمة بنسبة مئوية عالية، وتظهر للمترجم الفوارق باللون للتركيز على تعديل التباين فقط."
        ),
        ExamQuestion(
            id = 85,
            questionAr = "ما معنى عبارة 'Pacta Sunt Servanda' في فقه المعاهدات الدولية؟",
            questionEn = "أصول القانون الدولي العام",
            optionsAr = listOf("المعاهدات ملزمة لأطرافها ويجب تنفيذها بحسن نية مطلقة", "الاتفاقيات تلغى بتغير الحكومة", "المعاهدات الشفهية لا قيمة لها", "الحرب تلغي جميع الديون الدولية"),
            correctIndex = 0,
            explanationAr = "المادة 26 من اتفاقية فيينا لقانون المعاهدات: العقد شريعة المتعاقدين دولياً، والتنفيذ الأمين واجب مقدس بين الدول."
        ),
        ExamQuestion(
            id = 86,
            questionAr = "ما هو 'Corpus' (المدونة اللغوية الحاسوبية) في علم اللغويات والترجمة؟",
            questionEn = "اللغويات الحاسوبية والمعاجم",
            optionsAr = listOf("مجموعة رقمية ضخمة من النصوص الحقيقية المجمعة والمنسقة لتحليل أنماط الاستخدام الفعلي والمصاحبات اللفظية", "قاموس جيب صغير", "برنامج محادثة ذكاء اصطناعي", "ملف لتصميم الأغلفة"),
            correctIndex = 0,
            explanationAr = "المدونة (Corpus) تقدم للمترجم والباحث دليلاً إحصائياً وتطبيقياً على السياقات الطبيعية التي ترد فيها الألفاظ والتراكيب."
        ),
        ExamQuestion(
            id = 87,
            questionAr = "ما هو 'Localization' (التوطين اللغوي والبرمجي L10n) مقارنة بالترجمة البسيطة؟",
            questionEn = "صناعة الترجمة والتوطين",
            optionsAr = listOf("تكييف المنتج أو البرنامج ثقافياً وقانونياً وتقنياً (العملات، التواريخ، اتجاه الكتابة) ليلائم السوق المستهدفة تماماً", "ترجمة الأسماء العلمية فقط", "تصغير حجم الملف الرقمي", "تغيير لغة لوحة المفاتيح"),
            correctIndex = 0,
            explanationAr = "التوطين يتجاوز الترجمة اللغوية ليشمل تعديل الرسوميات وتنسيقات الأرقام والأبعاد الثقافية لتبدو السلعة محلية المنشأ."
        ),
        ExamQuestion(
            id = 88,
            questionAr = "ما معنى مصطلح 'Arbitral Award' الصادر عن هيئات التحكيم الدولية؟",
            questionEn = "التحكيم التجاري والدولي",
            optionsAr = listOf("حكم التحكيم الملزم الصادر عن هيئة المحكمين للفصل في النزاع (يقابل الحكم القضائي)", "جائزة شرفية للمحامي", "مكافأة مالية للشهود", "شهادة حضور المؤتمر"),
            correctIndex = 0,
            explanationAr = "حكم التحكيم (Arbitral Award) يتمتع بقوة النفاذ القضائي الدولي في أكثر من 160 دولة بموجب اتفاقية نيويورك 1958."
        ),
        ExamQuestion(
            id = 89,
            questionAr = "ما هي اتفاقية نيويورك لعام 1958 (New York Convention) وأهميتها للمترجم القانوني؟",
            questionEn = "التحكيم والاعتراف الدولي",
            optionsAr = listOf("اتفاقية الاعتراف بقرارات التحكيم الأجنبية وإنفاذها، وتشترط إرفاق ترجمة معتمدة لحكم التحكيم واتفاقه", "اتفاقية لحماية البيئة البحرية", "معاهدة لحظر الأسلحة النووية", "اتفاقية حقوق الطيران المدني"),
            correctIndex = 0,
            explanationAr = "تلزم المادة الرابعة من الاتفاقية الأطراف بتقديم ترجمة رسمية معتمدة لحكم التحكيم وعقد التحكيم بلغة المحكمة المنفذة."
        ),
        ExamQuestion(
            id = 90,
            questionAr = "ما هو 'Voice-Over' (التعليق الصوتي) في الترجمة السمعية البصرية؟",
            questionEn = "فنون الصوت والوسائط",
            optionsAr = listOf("تسجيل صوتي يؤديه معلق بلغة الهدف فوق الصوت الأصلي الذي يخفض مستواه دون إلغائه تماماً", "كتابة الحوار في شريط أسود", "مسح الموسيقى التصويرية", "ترجمة لغة الشفاه حرفياً"),
            correctIndex = 0,
            explanationAr = "تقنية مستخدمة في الوثائقيات والمقابلات يظل فيها صوت المتحدث الأصلي مسموعاً بخفوت في الخلفية لتعزيز المصداقية."
        ),
        ExamQuestion(
            id = 91,
            questionAr = "ما الفرق بين 'Dubbing' (الدبلجة) و 'Voice-Over' في التعريب السمعي البصري؟",
            questionEn = "الوسائط المتعددة والسينما",
            optionsAr = listOf("الدبلجة تستبدل الصوت الأصلي تماماً مع مطابقة حركة الشفاه (Lip-sync)، بينما Voice-Over يبقي الأصل في الخلفية", "الدبلجة للأخبار فقط", "Voice-Over يستعمل في أفلام الكرتون فقط", "لا يوجد أي فرق تقني"),
            correctIndex = 0,
            explanationAr = "الدبلجة تقتضي المزامنة الحركية التامة للشفاه (Lip-sync) ومسح المسار الصوتي الأصلي بالكامل لصالح الأداء التمثيلي الجديد."
        ),
        ExamQuestion(
            id = 92,
            questionAr = "ما هو 'Blind Review' (التقييم الأعمى) في مراجعة الجودة الأكاديمية والترجمة؟",
            questionEn = "تقييم الجودة الأكاديمية",
            optionsAr = listOf("فحص ومراجعة النص دون معرفة اسم المترجم أو مؤهلاته لضمان تجرد المراجع وحياده الموضوعي التام", "القراءة دون فتح الإضاءة", "المراجعة بواسطة الذكاء الاصطناعي فقط", "المراجعة الشفهية بدون نص"),
            correctIndex = 0,
            explanationAr = "منهجية حوكمة لغوية تمنع الانحياز المسبق؛ يراجع المدقق النص كمنتج مجرد عن أي اعتبارات شخصية."
        ),
        ExamQuestion(
            id = 93,
            questionAr = "ما هو مفهوم 'Skopos Theory' (نظرية الهدف في علم الترجمة) لـ هانز فيرمير (Hans Vermeer)؟",
            questionEn = "نظريات الترجمة الحديثة",
            optionsAr = listOf("أن الغرض الوظيفي للنص الهدف في سياقه التواصلي هو المعيار الأسمى الذي يحدد استراتيجيات وطريقة الترجمة", "أن الترجمة يجب أن تكون حرفية دائماً", "أن النص المصدر مقدس ولا يجوز تحويره", "أن السعر هو ما يحدد الجودة"),
            correctIndex = 0,
            explanationAr = "نظرية سكبوس الوظيفية تؤكد أن غرض الترجمة (Skopos) هو الموجه الأول لكيفية الصياغة والتصرف اللغوي."
        ),
        ExamQuestion(
            id = 94,
            questionAr = "ما المقصود بـ 'Back-Translation' (الترجمة العكسية للتحقق) في الأبحاث الدوائية والتجارب السريرية؟",
            questionEn = "الترجمة الطبية والسريرية",
            optionsAr = listOf("ترجمة النص الهدف مرة أخرى إلى لغة المصدر بواسطة مترجم مستقل لا يعرف الأصل للمقارنة وكشف أي لبس", "إرجاع الوثيقة للعميل دون ترجمة", "قراءة النص من اليسار إلى اليمين", "إعادة صياغة العناوين فقط"),
            correctIndex = 0,
            explanationAr = "إجراء إلزامي في بروتوكولات الأدوية واستبيانات المرضى المعتمدة من منظمة الصحة العالمية وهيئة الغذاء والدواء FDA."
        ),
        ExamQuestion(
            id = 95,
            questionAr = "ما هي 'Controlled Authoring / Language' (اللغة المضبوطة والمبسطة كـ Simplified Technical English)؟",
            questionEn = "الكتابة التقنية والترجمة الآلية",
            optionsAr = listOf("قواعد كتابة قياسية تقيد المفردات والتراكيب في لغة المصدر لتسهيل الفهم والترجمة الآلية الدقيقة", "لغة سرية لا يفهمها الجمهور", "التحدث بلغة الإشارة", "حذف كافة الأفعال من النص"),
            correctIndex = 0,
            explanationAr = "تقنية كتابة صناعية (مثل ASD-STE100) تمنع اللبس والغموض وتجعل الترجمة الآلية والبشرية بالغة الدقة وقليلة التكلفة."
        ),
        ExamQuestion(
            id = 96,
            questionAr = "ما معنى 'False Sense of Fluency' في تدقيق مخرجات الذكاء الاصطناعي التوليدي والترجمة الآلية؟",
            questionEn = "الذكاء الاصطناعي ومخاطر الترجمة",
            optionsAr = listOf("نص يبدو ظاهرياً فصيحاً وبليغاً جداً ولكنه يحتوي على هلوسة أو تحريف جسيم في المعنى الأصلي أو إسقاط بند حاسم", "نص مليء بالأخطاء الإملائية الواضحة", "نص بطيء في الطباعة", "نص خالي من الألوان"),
            correctIndex = 0,
            explanationAr = "أخطر فخ في الذكاء الاصطناعي؛ فصاحة الصياغة وجمال المفردات قد يخدعان القارئ ويخفيان هلوسة خطيرة أو حذفاً لأرقام جوهرية."
        ),
        ExamQuestion(
            id = 97,
            questionAr = "ما هي وظيفة الرمز '§' في الوثائق القانونية والتشريعات الألمانية والأوروبية؟",
            questionEn = "الرموز القانونية الرسمية",
            optionsAr = listOf("رمز الفقرة القانونية أو المادة التشريعية (Paragraph / Section)", "رمز العملة المشفرة", "علامة النفي المطلق", "رقم القضية الجنائية"),
            correctIndex = 0,
            explanationAr = "رمز Paragraphenzeichen (§) يستخدم للإشارة إلى المواد والفقرات القانونية في القوانين والمدونات المدنية."
        ),
        ExamQuestion(
            id = 98,
            questionAr = "ما المعنى الدقيق لعبارة 'Ab Initio' في فسخ وإلغاء العقود غير المشروعة؟",
            questionEn = "اللاتينية في فقه العقود",
            optionsAr = listOf("باطل من الأساس ومنذ البداية (كأنه لم ينعقد أصلاً بأثر رجعي تام)", "صحيح حتى إشعار آخر", "يبدأ سريانه بعد شهر", "مشروط بموافقة الشهود"),
            correctIndex = 0,
            explanationAr = "'Void ab initio' تعني أن العقد كان معيباً في أركانه الجوهرية بحيث يعتبر معدوماً قانوناً منذ لحظة إبرامه الأولى."
        ),
        ExamQuestion(
            id = 99,
            questionAr = "ما هي الرسالة الأخلاقية الأسمى لمترجم المؤتمرات والترجمة المحلفة المعتمدة عالمياً؟",
            questionEn = "رسالة المترجم الإنسانية والمهنية",
            optionsAr = listOf("أن يكون جسراً أميناً وموثوقاً للتفاهم البشري والعدالة وسيادة القانون دون تزييف أو تحريف", "تحقيق الأرباح المالية السريعة فقط", "استبدال آراء المتحدثين بآرائه الخاصة", "فرض ثقافته الشخصية على الآخرين"),
            correctIndex = 0,
            explanationAr = "الأمانة المطلقة، الدقة العلمية، والحياد الإنساني النبيل هي جوهر رسالة مهنة الترجمة عبر التاريخ البشري."
        ),
        ExamQuestion(
            id = 100,
            questionAr = "ما هي وظيفة الرقم المرجعي والتسلسلي الموحد في سجلات المترجم المحلف؟",
            questionEn = "حوكمة الترجمة المعتمدة",
            optionsAr = listOf("إثبات قيد الوثيقة في السجل الرسمي للمحكمة ومنع أي تلاعب أو تزوير في الوثائق المصدقة", "تحديد رقم الحساب البنكي", "تحديد تاريخ انتهاء جواز السفر", "إحصاء عدد الكلمات فقط"),
            correctIndex = 0,
            explanationAr = "كل وثيقة تترجم وتختم تسجل برقم تسلسلي غير قابل للتكرار في السجل الرسمي لتمكين السلطات من التحقق من صحتها."
        )
    )

}
