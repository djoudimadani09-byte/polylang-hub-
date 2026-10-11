#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Builds complete, production-grade 7-Language Localization for Polylang Hub
Accredited ISO 17100:2015.
Languages: ar (Arabic), en (English), fr (French), de (German), es (Spanish), it (Italian), ru (Russian)
"""

import re

def main():
    with open("index.html", "r", encoding="utf-8") as f:
        html = f.read()

    # Find the changeLanguage function block
    pattern = re.compile(r'function changeLanguage\(lang\) \{.*?(?=// Init on DOM ready)', re.DOTALL)

    new_i18n_code = """function changeLanguage(lang) {
      currentLang = lang;
      const isRtl = (lang === 'ar');
      document.documentElement.setAttribute('lang', lang);
      document.documentElement.setAttribute('dir', isRtl ? 'rtl' : 'ltr');
      document.body.style.direction = isRtl ? 'rtl' : 'ltr';

      const dict = {
        ar: {
          brandName: "منصة بوليلانغ للترجمة المعتمدة",
          brandSub: "المنصة الرائدة في الترجمة المحلفة وكابينات المؤتمرات والتدريب الاحترافي",
          heroHead: "مرحباً بكم في بوليلانغ هاب (Polylang Hub)",
          heroDesc: "خدمات الترجمة المحلفة المعتمدة دولياً بموجب معيار ISO 17100، كابينات الترجمة الفورية للمؤتمرات، ومساقات تدريبية احترافية معتمدة.",
          aboutBtn: "من نحن والمؤسس",
          audioTitle: "الترحيب الصوتي المباشر:",
          reqTrans: "طلب ترجمة معتمدة",
          academy: "الأكاديمية والمساقات",
          founder: "نبذة عن المؤسس",
          heroBoothBtn: "دخول مقصورة ومحاكي الترجمة (100 خطاب)",
          heroAcademyBtn: "مكتبة اللغات والماستركلاس",
          heroOrderBtn: "طلب ترجمة معتمدة",
          navHome: "الرئيسية",
          navBooth: "مقصورة الترجمة (100 خطاب)",
          navAcademy: "مكتبة اللغات والماستركلاس",
          navOrder: "طلب ترجمة معتمدة",
          navAghilas: "عرض ترانز اب (50%-)",
          navExam: "تحديد المستوى CEFR (100 سؤال)",
          navAbout: "من نحن والمؤسس",
          chipAdmin: "🔒 المشرف العام (Admin)",
          chipTranslator: "🎓 مترجم معتمد (Translator)",
          chipClient: "👤 عميل ترجمة (Client)",
          chipStudent: "📚 طلبة الترجمة (Students)",
          chipEnthusiast: "💡 متعلم لغات (Learner)",
          orderPreviewBtn: "معاينة نموذج الترجمة والاعتماد (Live Preview)",
          // Services Cards
          serv1Title: "الترجمة الرسمية والمحلفة (ISO 17100)",
          serv1Desc: "ترجمة معتمدة قانونياً للعقود التجارية، الشهادات والملفات القضائية مقبولة لدى السفارات والمحاكم والوزارات.",
          serv2Title: "كابينات الترجمة الفورية للمؤتمرات",
          serv2Desc: "محاكي واقعي وتجهيزات كابينات الترجمة الفورية للقمم الدولية بـ 7 لغات مع تدريب الفارق الزمني (Décalage).",
          serv3Title: "الترجمة التتابعية والمرافقة الدبلوماسية",
          serv3Desc: "مفكرة وتدوين رموز روزان (Jean-François Rozan) للمؤتمرات الثنائية والمفاوضات الدبلوماسية والوفود الرسمية.",
          serv4Title: "استوديو ترجمة المرئيات وملفات SRT",
          serv4Desc: "استوديو متكامل لدبلجة المرئيات وتوليد وتعديل ملفات الترجمة الزمنية بدقة متناهية متوافقة مع البث الرقمي.",
          serv5Title: "أكاديمية التدريب وفيديوهات الألمانية",
          serv5Desc: "17 مساقاً تخصصياً ومقاطع تدريبية عملية تشمل سلسلة دروس اللغة الألمانية وقواعدها ونطقها الصحيح.",
          serv6Title: "امتحان تحديد المستوى المعياري (100 سؤال)",
          serv6Desc: "امتحانات تقييم المستوى الأكاديمي والمهني CEFR (الامتحان الشامل، Goethe، TCF، IELTS، وترجمة المؤتمرات).",
          // Booth Localized
          boothTitle: "🎙️ محاكي كابينة المؤتمرات الدولية (100 خطاب تدريبي معتمد ISO 17100)",
          boothSub: "خطابات واقعية طويلة تحاكي القمم الدبلوماسية، قمم الطاقة، الاقتصاد، والمنظمات الدولية.",
          domainFilterLbl: "المجال التخصصي:",
          tierFilterLbl: "نوع الوصول / الباقة:",
          pairFilterLbl: "الزوج اللغوي:",
          searchFilterLbl: "بحث سريع في الخطابات:",
          selectSpeechLbl: "اختر الخطاب التدريبي من القائمة (110 خطابات واقعية طويلة):",
          playAudioBtn: "تشغيل صوت الخطاب",
          stopAudioBtn: "إيقاف",
          recordStatusBtn: "تسجيل صوتي فوري في الكابينة",
          showModelSolBtn: "إظهار الترجمة المؤسساتية النموذجية المعتمدة",
          sendReportBtn: "إرسال تقرير التمرين",
          sourceScriptLbl: "النص المصدر المنطوق في الكابينة:",
          userTransLbl: "صياغتك الفورية (التفريغ الشفهي أو الكتابي):",
          decalageLbl: "الفارق الزمني (Décalage):",
          // Rozan
          consecTitle: "📝 ورشة الترجمة التتابعية ونظام روزان (Jean-François Rozan)",
          consecSub: "مبادئ روزان السبعة: التدوين الرأسي (Verticality)، التدرج (Shift)، الروابط المنطقية، ونظام الرموز المختصرة.",
          consecSelectLbl: "اختر خطاب الترجمة التتابعية (خطابات دبلوماسية واقتصادية متخصصة):",
          rozanSymbolsTitle: "شريط رموز روزان السريعة (انقر للإدراج فوراً في الملاحظات):",
          consecSourceLbl: "الخطاب المصدر للاستماع:",
          rozanPadLbl: "مفكرة روزان التتابعية (Rozan Pad):",
          listenConsecBtn: "استمع للخطاب بصوت المتحدث",
          showConsecModelBtn: "مقارنة الصياغة مع الترجمة المؤسساتية المعتمدة ونموذج روزان",
          // Exam UI
          examTitle: "🎓 امتحان تقييم المستوى الأكاديمي والمهني (100 سؤالاً معتمداً)",
          examDesc: "اختبارات معيارية واقعية تضم 100 سؤال لكل مسار لغوي مع تصحيح نموذجي وتحليل دلالي ونحوي معتمد وفق معايير CEFR و ISO 17100.",
          examTrackLabel: "🎯 اختر مسار الاختبار المعياري المطلوب (100 سؤال لكل مسار):",
          btnExamComp: "🏆 الامتحان الشامل للترجمة واللغات (100 سؤال)",
          btnExamTrans: "⚖️ الترجمة المؤسساتية وكابينات المؤتمرات (100 سؤال)",
          btnExamTcf: "🇫🇷 TCF / DALF (الفرنسية - 100 سؤال)",
          btnExamIelts: "🇬🇧 IELTS Academic (الإنجليزية - 100 سؤال)",
          btnExamGoethe: "🇩🇪 Goethe-Zertifikat (الألمانية - 100 سؤال)",
          examPrevBtn: "السابق",
          examNextBtn: "التالي ➔",
          examExplainBtn: "💡 فحص الإجابة والتصحيح النموذجي",
          academySearchPlh: "🔍 ابحث في المراجع الأكاديمية والمساقات وفيديوهات الألمانية...",
          academyCounter: "عرض 17 مرجعاً ومساقاً وفيديوهات تعلم اللغة الألمانية المعتمدة",
          // TransUp Pricing
          pricingTitle: "💎 باقات التدريب والترجمة المحلفة (عرض ترانز اب الحصري)",
          pricingSub: "خصم حصري 50% لطلبة معاهد الترجمة والمترجمين المهنيين عبر كود TRANSUP50",
          plan1Title: "باقة الطالب التأسيسية",
          plan2Title: "باقة المترجم المحترف (عرض ترانز اب)",
          plan3Title: "باقة المؤسسات والشركات الكبرى",
          planSubscribeBtn: "اشترك الآن وتفعيل الباقة",
          // Account modal
          accountTitle: "👤 حساب المستخدم والملف الشخصي",
          accountRole: "الدور النشط:",
          accountStatus: "حالة الاشتراك:"
        },
        en: {
          brandName: "Polylang Hub Official Platform",
          brandSub: "Certified Sworn Translation, Conference Booths & Professional Masterclasses",
          heroHead: "Welcome to Polylang Hub",
          heroDesc: "ISO 17100 certified sworn translation, simultaneous conference interpretation booths, and accredited professional language training.",
          aboutBtn: "About Us & Founder",
          audioTitle: "Live Audio Welcome:",
          reqTrans: "Request Certified Translation",
          academy: "Academy & Courses",
          founder: "Founder Profile",
          heroBoothBtn: "Enter Interpretation Booth (100 Speeches)",
          heroAcademyBtn: "Languages Library & Masterclasses",
          heroOrderBtn: "Request Certified Translation",
          navHome: "Home",
          navBooth: "Interpretation Booth (100 Speeches)",
          navAcademy: "Languages & Masterclasses",
          navOrder: "Request Translation",
          navAghilas: "TransUp Offer (-50%)",
          navExam: "CEFR Placement Test (100 Qs)",
          navAbout: "About & Founder",
          chipAdmin: "🔒 Admin Portal",
          chipTranslator: "🎓 Certified Translator",
          chipClient: "👤 Client Portal",
          chipStudent: "📚 Student Lab",
          chipEnthusiast: "💡 Language Learner",
          orderPreviewBtn: "Live Translation & Document Preview",
          // Services Cards
          serv1Title: "Sworn Certified Translation (ISO 17100)",
          serv1Desc: "Legally certified translation for commercial contracts, civil records and legal filings, accredited by ministries and embassies.",
          serv2Title: "Simultaneous Conference Interpretation Booths",
          serv2Desc: "Realistic conference simulator and equipment in 7 languages with Décalage ear-voice span acoustic calibration.",
          serv3Title: "Consecutive & Diplomatic Interpretation",
          serv3Desc: "Jean-François Rozan symbols and verticality notepad for bilateral summits, trade delegations and high-level talks.",
          serv4Title: "SRT Video Subtitling & Dubbing Studio",
          serv4Desc: "Comprehensive studio for timecoded SRT generation, multi-track alignment and broadcast-compliant audiovisual translation.",
          serv5Title: "Training Academy & German Video Lessons",
          serv5Desc: "17 accredited masterclasses and video tutorials including German grammar, pronunciation capsules and Goethe exam prep.",
          serv6Title: "Standardized CEFR Level Exam (100 Questions)",
          serv6Desc: "Academic and professional evaluation tracks (Comprehensive, Goethe German, TCF French, IELTS Academic, Conference Translation).",
          // Booth Localized
          boothTitle: "🎙️ International Conference Booth Simulator (100 Verified Speeches - ISO 17100)",
          boothSub: "Authentic long-form speeches simulating UN General Assembly, Energy Summits, Davos, and International Organizations.",
          domainFilterLbl: "Specialized Domain:",
          tierFilterLbl: "Access Tier / Plan:",
          pairFilterLbl: "Language Pair:",
          searchFilterLbl: "Quick Search in Speeches:",
          selectSpeechLbl: "Select Conference Speech (110 Long Realistic Speeches):",
          playAudioBtn: "Play Speech Audio",
          stopAudioBtn: "Stop",
          recordStatusBtn: "Record Live in Booth",
          showModelSolBtn: "Show Official Accredited Model Translation",
          sendReportBtn: "Send Drill Report",
          sourceScriptLbl: "Source Speech Spoken in Booth:",
          userTransLbl: "Your Simultaneous Rendering (Oral / Written):",
          decalageLbl: "Acoustic Delay (Décalage):",
          // Rozan
          consecTitle: "📝 Consecutive Interpretation & Rozan Notepad (Jean-François Rozan)",
          consecSub: "Rozan's 7 Core Principles: Verticality, Shift, Logical Connectors, and Symbol Abbreviations.",
          consecSelectLbl: "Select Consecutive Speech (Diplomatic & Economic Focus):",
          rozanSymbolsTitle: "Rozan Quick Symbols Toolbar (Click to insert into notes):",
          consecSourceLbl: "Source Speech for Listening:",
          rozanPadLbl: "Rozan Consecutive Notepad (Rozan Pad):",
          listenConsecBtn: "Listen to Speaker's Voice",
          showConsecModelBtn: "Compare with Model Translation & Rozan Notes",
          // Exam UI
          examTitle: "🎓 Standardized 100-Questions Level Placement Examination",
          examDesc: "Realistic proficiency exam featuring 100 questions per language track with official answer keys, detailed rationale, and CEFR/ISO 17100 grading.",
          examTrackLabel: "🎯 Choose Target Language Examination Track (100 Questions each):",
          btnExamComp: "🏆 Comprehensive Translation & Languages (100 Qs)",
          btnExamTrans: "⚖️ Institutional Translation & Conference Booths (100 Qs)",
          btnExamTcf: "🇫🇷 TCF / DALF French Proficiency (100 Qs)",
          btnExamIelts: "🇬🇧 IELTS Academic English Proficiency (100 Qs)",
          btnExamGoethe: "🇩🇪 Goethe-Zertifikat German Proficiency (100 Qs)",
          examPrevBtn: "Previous",
          examNextBtn: "Next ➔",
          examExplainBtn: "💡 Check Answer & Model Explanation",
          academySearchPlh: "🔍 Search academic lectures, masterclasses, and German videos...",
          academyCounter: "Displaying 17 accredited courses, references & German learning videos",
          // TransUp Pricing
          pricingTitle: "💎 Training & Certified Translation Plans (Exclusive TransUp Offer)",
          pricingSub: "Exclusive 50% discount for translation students and professional translators with code TRANSUP50",
          plan1Title: "Student Foundation Plan",
          plan2Title: "Pro Translator Plan (TransUp Offer -50%)",
          plan3Title: "Enterprise & Institutional VIP Plan",
          planSubscribeBtn: "Subscribe Now & Activate Plan",
          // Account modal
          accountTitle: "👤 User Account & Profile",
          accountRole: "Active Role:",
          accountStatus: "Subscription Status:"
        },
        fr: {
          brandName: "Plateforme Officielle Polylang Hub",
          brandSub: "Traduction Assermentée, Cabines d'Interprétation & Académie Masterclass",
          heroHead: "Bienvenue sur Polylang Hub",
          heroDesc: "Traduction assermentée certifiée ISO 17100, cabines d'interprétation simultanée de conférence et formation professionnelle d'excellence.",
          aboutBtn: "À Propos & Fondateur",
          audioTitle: "Accueil Audio en Direct :",
          reqTrans: "Demander une Traduction",
          academy: "Académie & Formations",
          founder: "Profil du Fondateur",
          heroBoothBtn: "Simulateur Cabine d'Interprétation (100 Discours)",
          heroAcademyBtn: "Bibliothèque de Langues & Masterclass",
          heroOrderBtn: "Demander une Traduction Assermentée",
          navHome: "Accueil",
          navBooth: "Cabine d'Interprétation (100 Discours)",
          navAcademy: "Langues & Masterclass",
          navOrder: "Commander Traduction",
          navAghilas: "Offre TransUp (-50%)",
          navExam: "Test de Niveau CECR (100 Qs)",
          navAbout: "À Propos & Fondateur",
          chipAdmin: "🔒 Administration",
          chipTranslator: "🎓 Traducteur Assermenté",
          chipClient: "👤 Espace Client",
          chipStudent: "📚 Espace Étudiants",
          chipEnthusiast: "💡 Apprenant Langues",
          orderPreviewBtn: "Aperçu en Direct de la Traduction",
          serv1Title: "Traduction Juridique & Assermentée (ISO 17100)",
          serv1Desc: "Actes notariés, contrats commerciaux et jugements certifiés conformes, recevables auprès des ministères et consulats.",
          serv2Title: "Cabines d'Interprétation Simultanée de Conférence",
          serv2Desc: "Simulateur réaliste de cabines de conférence en 7 langues avec étalonnage du décalage acoustique (Ear-Voice Span).",
          serv3Title: "Interprétation Consécutive & Système Rozan",
          serv3Desc: "Prise de notes Jean-François Rozan pour sommets bilatéraux, délégations d'affaires et négociations diplomatiques.",
          serv4Title: "Studio de Sous-titrage SRT & Doublage Vidéo",
          serv4Desc: "Génération et synchronisation fine de fichiers SRT pour plateformes numériques et médias audiovisuels.",
          serv5Title: "Académie & Leçons Vidéo d'Allemand",
          serv5Desc: "17 formations certifiées incluant la série d'apprentissage de l'allemand, grammaire et préparation aux examens.",
          serv6Title: "Examen Standardisé de Niveau (100 Questions)",
          serv6Desc: "Évaluation CECR complète (Test Général, Goethe Allemand, TCF Français, IELTS Anglais, Traduction de Conférence).",
          boothTitle: "🎙️ Simulateur de Cabine de Conférence Internationale (100 Discours)",
          boothSub: "Discours intégraux simulant l'Assemblée Générale de l'ONU, les sommets de l'énergie et Davos.",
          domainFilterLbl: "Domaine Spécialisé :",
          tierFilterLbl: "Niveau d'Accès :",
          pairFilterLbl: "Combinaison Linguistique :",
          searchFilterLbl: "Recherche Rapide :",
          selectSpeechLbl: "Sélectionnez le Discours de Conférence (110 Discours Riches) :",
          playAudioBtn: "Écouter le Discours",
          stopAudioBtn: "Arrêter",
          recordStatusBtn: "Enregistrer en Cabine",
          showModelSolBtn: "Afficher le Corrigé Institutionnel Modèle",
          sendReportBtn: "Envoyer le Rapport",
          sourceScriptLbl: "Texte Source Prononcé en Cabine :",
          userTransLbl: "Votre Rendu Simultané (Oral / Écrit) :",
          decalageLbl: "Décalage Acoustique :",
          consecTitle: "📝 Atelier d'Interprétation Consécutive & Bloc-notes Rozan",
          consecSub: "Les 7 principes de Rozan : verticalité, décalage, liens logiques et symboles abrégés.",
          consecSelectLbl: "Choisissez le discours consécutif (diplomatique et économique) :",
          rozanSymbolsTitle: "Barre d'outils symboles Rozan (cliquez pour insérer) :",
          consecSourceLbl: "Discours Source pour Écoute :",
          rozanPadLbl: "Bloc-notes Rozan :",
          listenConsecBtn: "Écouter la Voix de l'Orateur",
          showConsecModelBtn: "Comparer avec le Modèle Officiel & Notes Rozan",
          examTitle: "🎓 Examen Standardisé de Positionnement Linguistique (100 Questions)",
          examDesc: "Évaluation rigoureuse comprenant 100 questions par filière linguistique, corrigé officiel détaillé et certification CECR/ISO 17100.",
          examTrackLabel: "🎯 Choisissez le parcours d'évaluation (100 questions par filière) :",
          btnExamComp: "🏆 Examen Général Traduction & Langues (100 Qs)",
          btnExamTrans: "⚖️ Traduction Institutionnelle & Cabines (100 Qs)",
          btnExamTcf: "🇫🇷 Test TCF / DALF Français (100 Qs)",
          btnExamIelts: "🇬🇧 IELTS Academic Anglais (100 Qs)",
          btnExamGoethe: "🇩🇪 Goethe-Zertifikat Allemand (100 Qs)",
          examPrevBtn: "Précédent",
          examNextBtn: "Suivant ➔",
          examExplainBtn: "💡 Vérifier la Réponse & Corrigé Modèle",
          academySearchPlh: "🔍 Rechercher parmi les masterclasses et vidéos d'allemand...",
          academyCounter: "Affichage de 17 cours, références et vidéos d'allemand certifiées",
          pricingTitle: "💎 Forfaits de Formation & Traduction Assermentée (Offre TransUp)",
          pricingSub: "Remise exclusive de 50% pour étudiants et traducteurs professionnels avec le code TRANSUP50",
          plan1Title: "Forfait Étudiant Fondamental",
          plan2Title: "Forfait Traducteur Pro (Offre TransUp -50%)",
          plan3Title: "Forfait Entreprises & VIP",
          planSubscribeBtn: "S'abonner & Activer le Forfait",
          accountTitle: "👤 Compte Utilisateur & Profil",
          accountRole: "Rôle Actif :",
          accountStatus: "Statut d'Abonnement :"
        },
        de: {
          brandName: "Polylang Hub Offizielle Plattform",
          brandSub: "Beglaubigte Übersetzungen, Dolmetscherkabinen & Masterclass-Akademie",
          heroHead: "Willkommen bei Polylang Hub",
          heroDesc: "ISO 17100 zertifizierte beglaubigte Übersetzungen, Konferenzdolmetscherkabinen und professionelle Sprachweiterbildung.",
          aboutBtn: "Über Uns & Gründer",
          audioTitle: "Audiobegrüßung :",
          reqTrans: "Übersetzung anfordern",
          academy: "Akademie & Kurse",
          founder: "Gründerprofil",
          heroBoothBtn: "Dolmetscherkabinen-Simulator (100 Reden)",
          heroAcademyBtn: "Sprachbibliothek & Masterclass",
          heroOrderBtn: "Beglaubigte Übersetzung beauftragen",
          navHome: "Startseite",
          navBooth: "Dolmetscherkabine (100 Reden)",
          navAcademy: "Sprachen & Masterclass",
          navOrder: "Übersetzung beauftragen",
          navAghilas: "TransUp Rabatt (-50%)",
          navExam: "CEFR Einstufungstest (100 Fragen)",
          navAbout: "Über Uns & Gründer",
          chipAdmin: "🔒 Admin-Bereich",
          chipTranslator: "🎓 Vereidigter Übersetzer",
          chipClient: "👤 Kundenportal",
          chipStudent: "📚 Studentenbereich",
          chipEnthusiast: "💡 Sprachlernende",
          orderPreviewBtn: "Live-Vorschau der Übersetzung",
          serv1Title: "Beglaubigte Fachübersetzungen (ISO 17100)",
          serv1Desc: "Rechtsgültige Übersetzungen von Handelsverträgen, Urkunden und Gerichtsdokumenten für Botschaften und Behörden.",
          serv2Title: "Simultandolmetscherkabinen für Konferenzen",
          serv2Desc: "Realistischer Konferenzsimulator in 7 Sprachen mit akustischer Décalage-Messung für professionelle Dolmetscher.",
          serv3Title: "Konsekutivdolmetschen & Rozan-Notizentechnik",
          serv3Desc: "Strukturierte Rozan-Symboltechnik für bilaterale Verhandlungen, Wirtschaftsdelegationen und diplomatische Tagungen.",
          serv4Title: "SRT-Untertitelung & Audiovisuelles Studio",
          serv4Desc: "Präzise zeitcodierte Untertitelung und Sprachsynchronisation nach modernsten Sendestandards.",
          serv5Title: "Akademie & Deutsch-Lernvideos",
          serv5Desc: "17 zertifizierte Masterclasses inklusive Deutsch-Lernvideoreihe, Grammatik und Goethe-Zertifikatsvorbereitung.",
          serv6Title: "Standardisierte CEFR-Einstufungsprüfung (100 Fragen)",
          serv6Desc: "Akademische Leistungsbewertung (Gesamtexamen, Goethe Deutsch, TCF Französisch, IELTS Englisch, Konferenzdolmetschen).",
          boothTitle: "🎙️ Internationaler Konferenzkabinen-Simulator (100 Reden)",
          boothSub: "Reale lange Konferenzreden aus UN-Vollversammlung, Energieforen, Davos und Weltorganisationen.",
          domainFilterLbl: "Fachbereich :",
          tierFilterLbl: "Zugangsstufe :",
          pairFilterLbl: "Sprachenpaar :",
          searchFilterLbl: "Schnellsuche in Reden :",
          selectSpeechLbl: "Konferenzrede auswählen (110 authentische Reden) :",
          playAudioBtn: "Rede abspielen",
          stopAudioBtn: "Stopp",
          recordStatusBtn: "Live in Kabine aufnehmen",
          showModelSolBtn: "Musterübersetzung anzeigen",
          sendReportBtn: "Übungsbericht senden",
          sourceScriptLbl: "Gesprochener Quelltext in der Kabine :",
          userTransLbl: "Ihre Simultanübersetzung :",
          decalageLbl: "Zeitversatz (Décalage) :",
          consecTitle: "📝 Konsekutivdolmetschen & Rozan-Notizblock",
          consecSub: "Die 7 Rozan-Prinzipien: Vertikalität, Versatz, logische Konnektoren und Kurzzeichen.",
          consecSelectLbl: "Konsekutivrede wählen (diplomatisch & wirtschaftlich) :",
          rozanSymbolsTitle: "Rozan-Symbolleiste (Klicken zum Einfügen) :",
          consecSourceLbl: "Quellrede zum Anhören :",
          rozanPadLbl: "Rozan-Notizblock :",
          listenConsecBtn: "Originalstimme anhören",
          showConsecModelBtn: "Mit Musterübersetzung & Rozan-Notizen vergleichen",
          examTitle: "🎓 Standardisierte Einstufungsprüfung (100 Prüfungsfragen)",
          examDesc: "Reale akademische Prüfung mit 100 Fragen pro Sprache, Musterlösungen und detaillierter Begründung nach CEFR und ISO 17100.",
          examTrackLabel: "🎯 Wählen Sie den Prüfungsbereich (100 Fragen je Fachbereich) :",
          btnExamComp: "🏆 Gesamtexamen Übersetzung & Sprachen (100 Fragen)",
          btnExamTrans: "⚖️ Fachübersetzung & Konferenzdolmetschen (100 Fragen)",
          btnExamTcf: "🇫🇷 Französisch TCF / DALF (100 Fragen)",
          btnExamIelts: "🇬🇧 Englisch IELTS Academic (100 Fragen)",
          btnExamGoethe: "🇩🇪 Deutsch Goethe-Zertifikat (100 Fragen)",
          examPrevBtn: "Zurück",
          examNextBtn: "Weiter ➔",
          examExplainBtn: "💡 Antwort prüfen & Mustererklärung",
          academySearchPlh: "🔍 Vorlesungen, Kurse und Deutsch-Lernvideos durchsuchen...",
          academyCounter: "17 zertifizierte Masterclasses und Deutsch-Lernvideos verfügbar",
          pricingTitle: "💎 Weiterbildung & Beglaubigte Übersetzungen (TransUp Angebot)",
          pricingSub: "Exklusiver 50% Rabatt für Übersetzungsstudenten und Dolmetscher mit Code TRANSUP50",
          plan1Title: "Studenten-Grundpaket",
          plan2Title: "Pro Übersetzer-Paket (TransUp -50%)",
          plan3Title: "Institutionelles & Unternehmens-Paket",
          planSubscribeBtn: "Jetzt abonnieren & Paket aktivieren",
          accountTitle: "👤 Benutzerkonto & Profil",
          accountRole: "Aktive Rolle :",
          accountStatus: "Abonnementstatus :"
        },
        es: {
          brandName: "Polylang Hub Plataforma Oficial",
          brandSub: "Traducción Jurada, Cabinas de Interpretación y Academia Masterclass",
          heroHead: "Bienvenido a Polylang Hub",
          heroDesc: "Traducción jurada certificada ISO 17100, cabinas de conferencias y formación lingüística profesional de excelencia.",
          aboutBtn: "Sobre Nosotros",
          audioTitle: "Saludo en Audio :",
          reqTrans: "Solicitar Traducción",
          academy: "Academia y Cursos",
          founder: "Perfil del Fundador",
          heroBoothBtn: "Simulador de Cabina (100 Discursos)",
          heroAcademyBtn: "Biblioteca de Idiomas y Masterclass",
          heroOrderBtn: "Solicitar Traducción Jurada",
          navHome: "Inicio",
          navBooth: "Cabina de Interpretación (100 Discursos)",
          navAcademy: "Academia y Masterclass",
          navOrder: "Solicitar Traducción",
          navAghilas: "Oferta TransUp (-50%)",
          navExam: "Test de Nivel MCER (100 Preguntas)",
          navAbout: "Sobre Nosotros",
          chipAdmin: "🔒 Administración",
          chipTranslator: "🎓 Traductor Jurado",
          chipClient: "👤 Portal de Cliente",
          chipStudent: "📚 Estudiantes",
          chipEnthusiast: "💡 Estudiante de Idiomas",
          orderPreviewBtn: "Vista Previa de Traducción en Vivo",
          serv1Title: "Traducción Jurada Oficial (ISO 17100)",
          serv1Desc: "Contratos mercantiles, escrituras notariales y sentencias judiciales con validez ante consulados y ministerios.",
          serv2Title: "Cabinas de Interpretación Simultánea",
          serv2Desc: "Simulador de conferencias en 7 idiomas con medición del desfase acústico (Décalage / Ear-Voice Span).",
          serv3Title: "Interpretación Consecutiva y Sistema Rozan",
          serv3Desc: "Toma de notas Jean-François Rozan para cumbres bilaterales, negociaciones diplomáticas y misiones comerciales.",
          serv4Title: "Estudio de Subtitulado SRT y Doblaje",
          serv4Desc: "Generación precisa de subtítulos sincronizados compatibles con estándares internacionales de difusión.",
          serv5Title: "Academia y Vídeos de Aprendizaje de Alemán",
          serv5Desc: "17 clases magistrales y vídeos prácticos de gramática, fonética y preparación de exámenes Goethe.",
          serv6Title: "Examen Estandarizado de Nivel (100 Preguntas)",
          serv6Desc: "Evaluación según el MCER (Examen Integral, Goethe Alemán, TCF Francés, IELTS Inglés, Interpretación).",
          boothTitle: "🎙️ Simulador de Cabina de Conferencias (100 Discursos)",
          boothSub: "Discursos extensos y reales de la Asamblea General de la ONU, energía, economía y foros mundiales.",
          domainFilterLbl: "Área Especializada :",
          tierFilterLbl: "Tipo de Acceso :",
          pairFilterLbl: "Combinación Lingüística :",
          searchFilterLbl: "Búsqueda Rápida :",
          selectSpeechLbl: "Seleccione Discurso de Conferencia (110 Discursos) :",
          playAudioBtn: "Reproducir Discurso",
          stopAudioBtn: "Detener",
          recordStatusBtn: "Grabar en Cabina",
          showModelSolBtn: "Mostrar Traducción Modelo Homologada",
          sendReportBtn: "Enviar Informe",
          sourceScriptLbl: "Texto Fuente Pronunciado en Cabina :",
          userTransLbl: "Su Interpretación Simultánea :",
          decalageLbl: "Desfase Temporal (Décalage) :",
          consecTitle: "📝 Taller de Consecutiva y Cuaderno Rozan",
          consecSub: "Principios de Rozan: verticalidad, escalonamiento, enlaces lógicos y abreviaturas.",
          consecSelectLbl: "Elija discurso consecutivo :",
          rozanSymbolsTitle: "Barra de símbolos Rozan (clic para insertar) :",
          consecSourceLbl: "Discurso Fuente para Escucha :",
          rozanPadLbl: "Cuaderno Rozan :",
          listenConsecBtn: "Escuchar Orador",
          showConsecModelBtn: "Comparar con Traducción Modelo y Notas Rozan",
          examTitle: "🎓 Examen Estandarizado de Nivel Lingüístico (100 Preguntas)",
          examDesc: "Evaluación realista con 100 preguntas por idioma, respuestas modelo y justificación lingüística completa según MCER e ISO 17100.",
          examTrackLabel: "🎯 Seleccione el examen correspondiente (100 preguntas por itinerario) :",
          btnExamComp: "🏆 Examen Integral de Traducción e Idiomas (100 P.)",
          btnExamTrans: "⚖️ Traducción Institucional y Cabinas (100 P.)",
          btnExamTcf: "🇫🇷 TCF / DALF Francés (100 P.)",
          btnExamIelts: "🇬🇧 IELTS Academic Inglés (100 P.)",
          btnExamGoethe: "🇩🇪 Goethe-Zertifikat Alemán (100 P.)",
          examPrevBtn: "Anterior",
          examNextBtn: "Siguiente ➔",
          examExplainBtn: "💡 Comprobar Respuesta y Explicación Modelo",
          academySearchPlh: "🔍 Buscar conferencias, cursos y vídeos de alemán...",
          academyCounter: "Mostrando 17 cursos, referencias y vídeos de alemán certificados",
          pricingTitle: "💎 Planes de Formación y Traducción Jurada (Oferta TransUp)",
          pricingSub: "Descuento exclusivo del 50% con el código TRANSUP50 para estudiantes y profesionales",
          plan1Title: "Plan Inicial de Estudiante",
          plan2Title: "Plan Traductor Profesional (TransUp -50%)",
          plan3Title: "Plan Corporativo y VIP",
          planSubscribeBtn: "Suscribirse y Activar Plan",
          accountTitle: "👤 Cuenta de Usuario y Perfil",
          accountRole: "Rol Activo :",
          accountStatus: "Estado de Suscripción :"
        },
        it: {
          brandName: "Piattaforma Ufficiale Polylang Hub",
          brandSub: "Traduzione Giurata, Cabine d'Interpretariato e Formazione d'Eccellenza",
          heroHead: "Benvenuto su Polylang Hub",
          heroDesc: "Traduzione giurata certificata ISO 17100, cabine per conferenze e formazione linguistica d'eccellenza.",
          aboutBtn: "Chi Siamo",
          audioTitle: "Saluto Audio :",
          reqTrans: "Richiedi Traduzione",
          academy: "Accademia e Corsi",
          founder: "Profilo Fondatore",
          heroBoothBtn: "Simulatore Cabina Interpretariato (100 Discorsi)",
          heroAcademyBtn: "Libreria Linguistica & Masterclass",
          heroOrderBtn: "Richiedi Traduzione Giurata",
          navHome: "Home",
          navBooth: "Cabina Interpretariato (100 Discorsi)",
          navAcademy: "Accademia e Corsi",
          navOrder: "Richiedi Traduzione",
          navAghilas: "Offerta TransUp (-50%)",
          navExam: "Test di Livello QCER (100 Domande)",
          navAbout: "Chi Siamo & Fondatore",
          chipAdmin: "🔒 Amministrazione",
          chipTranslator: "🎓 Traduttore Giurato",
          chipClient: "👤 Portale Clienti",
          chipStudent: "📚 Laboratorio Studenti",
          chipEnthusiast: "💡 Studente di Lingue",
          orderPreviewBtn: "Anteprima Traduzione dal Vivo",
          serv1Title: "Traduzione Giurata Ufficiale (ISO 17100)",
          serv1Desc: "Contratti societari, atti legali e documenti ufficiali asseverati per tribunali e consolati.",
          serv2Title: "Cabine per Interpretariato Simultaneo",
          serv2Desc: "Simulatore di cabine per vertici internazionali in 7 lingue con monitoraggio del décalage acustico.",
          serv3Title: "Interpretariato Consecutivo & Sistema Rozan",
          serv3Desc: "Presa d'appunti Jean-François Rozan per incontri bilaterali e trattative diplomatiche ad alto livello.",
          serv4Title: "Studio Sottotitolaggio SRT & Doppiaggio",
          serv4Desc: "Generazione di sottotitoli temporizzati precisi conformi agli standard televisivi e web.",
          serv5Title: "Accademia & Video Lezioni di Tedesco",
          serv5Desc: "17 masterclass e video formativi pratici con fonetica, grammatica ed esami Goethe.",
          serv6Title: "Esame di Posizionamento QCER (100 Domande)",
          serv6Desc: "Valutazione rigorosa conforme al QCER (Esame Globale, Goethe Tedesco, TCF Francese, IELTS Inglese).",
          boothTitle: "🎙️ Simulatore Cabina Conferenze (100 Discorsi)",
          boothSub: "Discorsi autentici dall'Assemblea ONU, vertici sull'energia, Davos e organizzazioni mondiali.",
          domainFilterLbl: "Settore Specialistico :",
          tierFilterLbl: "Tipo di Accesso :",
          pairFilterLbl: "Combinazione Linguistica :",
          searchFilterLbl: "Ricerca Rapida :",
          selectSpeechLbl: "Seleziona Discorso di Conferenza (110 Discorsi) :",
          playAudioBtn: "Ascolta Discorso",
          stopAudioBtn: "Ferma",
          recordStatusBtn: "Registra in Cabina",
          showModelSolBtn: "Mostra Traduzione Modello Ufficiale",
          sendReportBtn: "Invia Report",
          sourceScriptLbl: "Testo Fonte Orale in Cabina :",
          userTransLbl: "Resa Simultanea :",
          decalageLbl: "Ritardo Acustico (Décalage) :",
          consecTitle: "📝 Laboratorio di Consecutiva & Note Rozan",
          consecSub: "I principi di Rozan: verticalità, scarto, connettori logici e abbreviazioni simboliche.",
          consecSelectLbl: "Seleziona discorso consecutivo :",
          rozanSymbolsTitle: "Simboli Rozan (clicca per inserire) :",
          consecSourceLbl: "Discorso Fonte da Ascoltare :",
          rozanPadLbl: "Taccuino Rozan :",
          listenConsecBtn: "Ascolta la Voce dell'Oratore",
          showConsecModelBtn: "Confronta con la Traduzione Modello & Note Rozan",
          examTitle: "🎓 Esame Standardizzato di Livello Linguistico (100 Domande)",
          examDesc: "Test realistico con 100 domande per lingua, risposte modello e motivazione grammaticale secondo gli standard QCER e ISO 17100.",
          examTrackLabel: "🎯 Scegli il percorso d'esame desiderato (100 domande per sezione) :",
          btnExamComp: "🏆 Esame Completo di Traduzione e Lingue (100 Dom.)",
          btnExamTrans: "⚖️ Traduzione Istituzionale e Cabine (100 Dom.)",
          btnExamTcf: "🇫🇷 TCF / DALF Francese (100 Dom.)",
          btnExamIelts: "🇬🇧 IELTS Academic Inglese (100 Dom.)",
          btnExamGoethe: "🇩🇪 Goethe-Zertifikat Tedesco (100 Dom.)",
          examPrevBtn: "Precedente",
          examNextBtn: "Avanti ➔",
          examExplainBtn: "💡 Controlla Risposta e Spiegazione Modello",
          academySearchPlh: "🔍 Cerca corsi, conferenze e video di lingua tedesca...",
          academyCounter: "Visualizzazione di 17 corsi, risorse e video di tedesco certificati",
          pricingTitle: "💎 Piani di Formazione e Traduzione Giurata (Offerta TransUp)",
          pricingSub: "Sconto esclusivo del 50% con codice promozionale TRANSUP50 per studenti e professionisti",
          plan1Title: "Piano Base Studente",
          plan2Title: "Piano Traduttore Pro (TransUp -50%)",
          plan3Title: "Piano Aziendale e VIP",
          planSubscribeBtn: "Abbonati Ora & Attiva Piano",
          accountTitle: "👤 Profilo Utente & Account",
          accountRole: "Ruolo Attivo :",
          accountStatus: "Stato Abbonamento :"
        },
        ru: {
          brandName: "Официальная Платформа Polylang Hub",
          brandSub: "Сертифицированный перевод, кабины синхронного перевода и академия",
          heroHead: "Добро пожаловать в Polylang Hub",
          heroDesc: "Сертифицированный перевод ISO 17100, кабины синхронного перевода конференций и профессиональное обучение.",
          aboutBtn: "О Нас",
          audioTitle: "Голосовое приветствие :",
          reqTrans: "Заказать перевод",
          academy: "Академия и курсы",
          founder: "Профиль основателя",
          heroBoothBtn: "Симулятор кабины синхронного перевода (100 Речей)",
          heroAcademyBtn: "Библиотека языков и мастер-классы",
          heroOrderBtn: "Заказать сертифицированный перевод",
          navHome: "Главная",
          navBooth: "Кабина синхронного перевода (100 Речей)",
          navAcademy: "Академия и мастер-классы",
          navOrder: "Заказать перевод",
          navAghilas: "Скидка TransUp (-50%)",
          navExam: "Тест уровня CEFR (100 вопросов)",
          navAbout: "О нас и основателе",
          chipAdmin: "🔒 Панель администратора",
          chipTranslator: "🎓 Присяжный переводчик",
          chipClient: "👤 Клиентский портал",
          chipStudent: "📚 Студенческая лаборатория",
          chipEnthusiast: "💡 Изучение языков",
          orderPreviewBtn: "Предпросмотр перевода",
          serv1Title: "Присяжный и судебный перевод (ISO 17100)",
          serv1Desc: "Заверенные переводы договоров, нотариальных актов и судебных документов для посольств и министерств.",
          serv2Title: "Кабины синхронного перевода конференций",
          serv2Desc: "Реалистичный симулятор конференций на 7 языках с тренировкой акустического интервала Décalage.",
          serv3Title: "Последовательный перевод и система Розана",
          serv3Desc: "Система символов Жана-Франсуа Розана для двусторонних переговоров и дипломатических делегаций.",
          serv4Title: "Студия субтитрования SRT и дубляжа",
          serv4Desc: "Профессиональная генерация и синхронизация субтитров SRT для вещания и цифровых платформ.",
          serv5Title: "Академия и видеоуроки немецкого языка",
          serv5Desc: "17 сертифицированных курсов, включая грамматику немецкого языка и подготовку к экзаменам Goethe.",
          serv6Title: "Стандартизированный экзамен CEFR (100 вопросов)",
          serv6Desc: "Комплексное тестирование уровня (Общий экзамен, Goethe, TCF, IELTS, конференц-перевод).",
          boothTitle: "🎙️ Международный симулятор кабины синхронного перевода (100 Речей)",
          boothSub: "Аутентичные длинные речи Генеральной Ассамблеи ООН, энергетических саммитов и Давоса.",
          domainFilterLbl: "Специализация :",
          tierFilterLbl: "Уровень доступа :",
          pairFilterLbl: "Языковая пара :",
          searchFilterLbl: "Быстрый поиск :",
          selectSpeechLbl: "Выберите речь конференции (110 аутентичных речей) :",
          playAudioBtn: "Воспроизвести речь",
          stopAudioBtn: "Стоп",
          recordStatusBtn: "Запись в кабине",
          showModelSolBtn: "Показать эталонный перевод",
          sendReportBtn: "Отправить отчет",
          sourceScriptLbl: "Исходный текст речи :",
          userTransLbl: "Ваш синхронный перевод :",
          decalageLbl: "Интервал задержки (Décalage) :",
          consecTitle: "📝 Мастерская последовательного перевода и блокнот Розана",
          consecSub: "7 принципов Розана: вертикальность, сдвиг, логические связки и символические сокращения.",
          consecSelectLbl: "Выберите речь для последовательного перевода :",
          rozanSymbolsTitle: "Панель символов Розана (нажмите для вставки) :",
          consecSourceLbl: "Исходная речь для прослушивания :",
          rozanPadLbl: "Блокнот Розана :",
          listenConsecBtn: "Прослушать голос оратора",
          showConsecModelBtn: "Сравнить с эталонным переводом и заметками Розана",
          examTitle: "🎓 Стандартизированный квалификационный экзамен (100 вопросов)",
          examDesc: "Реалистичный экзамен из 100 вопросов по каждому направлению с эталонными ответами и оценкой по CEFR и ISO 17100.",
          examTrackLabel: "🎯 Выберите направление экзамена (100 вопросов в каждом) :",
          btnExamComp: "🏆 Комплексный экзамен по переводу и языкам (100 вопр.)",
          btnExamTrans: "⚖️ Институциональный перевод и кабины (100 вопр.)",
          btnExamTcf: "🇫🇷 Французский TCF / DALF (100 вопр.)",
          btnExamIelts: "🇬🇧 Английский IELTS Academic (100 вопр.)",
          btnExamGoethe: "🇩🇪 Немецкий Goethe-Zertifikat (100 вопр.)",
          examPrevBtn: "Назад",
          examNextBtn: "Далее ➔",
          examExplainBtn: "💡 Проверить ответ и эталонное объяснение",
          academySearchPlh: "🔍 Поиск академических лекций, курсов и видео немецкого языка...",
          academyCounter: "Отображение 17 курсов, справочников и видео уроков немецкого",
          pricingTitle: "💎 Пакеты обучения и присяжного перевода (Предложение TransUp)",
          pricingSub: "Эксклюзивная скидка 50% по промокоду TRANSUP50 для студентов и профессиональных переводчиков",
          plan1Title: "Базовый студенческий пакет",
          plan2Title: "Пакет Про-переводчик (Скидка TransUp -50%)",
          plan3Title: "Корпоративный VIP-пакет",
          planSubscribeBtn: "Оформить подписку и активировать",
          accountTitle: "👤 Профиль пользователя и аккаунт",
          accountRole: "Активная роль :",
          accountStatus: "Статус подписки :"
        }
      };

      const t = dict[lang] || dict['ar'];
      const setEl = (id, val) => {
        const el = document.getElementById(id);
        if (el && val) el.textContent = val;
      };

      // Header & Brand
      setEl('i18n-brand-name', t.brandName);
      setEl('i18n-brand-sub', t.brandSub);
      setEl('i18n-hero-head', t.heroHead);
      setEl('i18n-hero-desc', t.heroDesc);
      setEl('i18n-about-btn', t.aboutBtn);
      setEl('i18n-audio-title', t.audioTitle);
      setEl('i18n-btn-req-trans', t.reqTrans);
      setEl('i18n-btn-academy', t.academy);
      setEl('i18n-btn-founder', t.founder);

      setEl('hero-btn-booth', t.heroBoothBtn);
      setEl('hero-btn-academy', t.heroAcademyBtn);
      setEl('hero-btn-order', t.heroOrderBtn);

      // Nav pills
      setEl('txt-nav-home', t.navHome);
      setEl('txt-nav-booth', t.navBooth);
      setEl('txt-nav-academy', t.navAcademy);
      setEl('txt-nav-order', t.navOrder);
      setEl('txt-nav-aghilas', t.navAghilas);
      setEl('txt-nav-exam', t.navExam);
      setEl('txt-nav-about', t.navAbout);

      // Role chips
      setEl('txt-chip-admin', t.chipAdmin);
      setEl('txt-chip-translator', t.chipTranslator);
      setEl('txt-chip-client', t.chipClient);
      setEl('txt-chip-student', t.chipStudent);
      setEl('txt-chip-enthusiast', t.chipEnthusiast);

      // Service Cards
      setEl('serv-1-title', t.serv1Title);
      setEl('serv-1-desc', t.serv1Desc);
      setEl('serv-2-title', t.serv2Title);
      setEl('serv-2-desc', t.serv2Desc);
      setEl('serv-3-title', t.serv3Title);
      setEl('serv-3-desc', t.serv3Desc);
      setEl('serv-4-title', t.serv4Title);
      setEl('serv-4-desc', t.serv4Desc);
      setEl('serv-5-title', t.serv5Title);
      setEl('serv-5-desc', t.serv5Desc);
      setEl('serv-6-title', t.serv6Title);
      setEl('serv-6-desc', t.serv6Desc);

      // Booth Section
      setEl('txt-booth-title', t.boothTitle);
      setEl('txt-booth-subtitle', t.boothSub);
      setEl('lbl-domain-filter', t.domainFilterLbl);
      setEl('lbl-tier-filter', t.tierFilterLbl);
      setEl('lbl-pair-filter', t.pairFilterLbl);
      setEl('lbl-search-filter', t.searchFilterLbl);
      setEl('lbl-select-speech', t.selectSpeechLbl);
      setEl('txt-play-audio', t.playAudioBtn);
      setEl('txt-stop-audio', t.stopAudioBtn);
      setEl('txt-record-status', t.recordStatusBtn);
      setEl('txt-show-model-solution', t.showModelSolBtn);
      setEl('txt-send-report', t.sendReportBtn);
      setEl('lbl-source-script', t.sourceScriptLbl);
      setEl('lbl-user-transcription', t.userTransLbl);
      setEl('txt-decalage-label', t.decalageLbl);

      // Rozan Section
      setEl('txt-consec-title', t.consecTitle);
      setEl('txt-consec-sub', t.consecSub);
      setEl('lbl-consec-select', t.consecSelectLbl);
      setEl('txt-rozan-symbols-title', t.rozanSymbolsTitle);
      setEl('lbl-consec-source', t.consecSourceLbl);
      setEl('lbl-rozan-pad', t.rozanPadLbl);
      setEl('txt-listen-consec', t.listenConsecBtn);
      setEl('txt-show-consec-model', t.showConsecModelBtn);

      // Order Preview
      setEl('order-preview-toggle-text', t.orderPreviewBtn);

      // Exam UI
      setEl('btn-exam-comprehensive', t.btnExamComp);
      setEl('btn-exam-trans', t.btnExamTrans);
      setEl('btn-exam-tcf', t.btnExamTcf);
      setEl('btn-exam-ielts', t.btnExamIelts);
      setEl('btn-exam-goethe', t.btnExamGoethe);
      setEl('exam-prev-btn', t.examPrevBtn);
      setEl('exam-instant-explain-btn', t.examExplainBtn);

      // Academy search input placeholder and status
      const searchInp = document.getElementById('academy-search');
      if (searchInp && t.academySearchPlh) searchInp.placeholder = t.academySearchPlh;
      setEl('academy-status-counter', t.academyCounter);

      // TransUp Pricing Plans
      setEl('txt-pricing-title', t.pricingTitle);
      setEl('txt-pricing-sub', t.pricingSub);
      setEl('plan-1-title', t.plan1Title);
      setEl('plan-2-title', t.plan2Title);
      setEl('plan-3-title', t.plan3Title);

      // Account modal
      setEl('txt-account-title', t.accountTitle);
      setEl('lbl-account-role', t.accountRole);
      setEl('lbl-account-status', t.accountStatus);
    }
"""

    if pattern.search(html):
        html = pattern.sub(new_i18n_code, html, count=1)
        print("Replaced changeLanguage with comprehensive 7-language localization.")
    else:
        print("Warning: pattern for changeLanguage not found.")

    # Also add applyBoothFilters() to window.addEventListener('DOMContentLoaded', ...)
    if "applyBoothFilters();" not in html:
        dom_target = "window.addEventListener('DOMContentLoaded', () => {"
        dom_replacement = "window.addEventListener('DOMContentLoaded', () => {\n      applyBoothFilters();"
        html = html.replace(dom_target, dom_replacement, 1)
        print("Added applyBoothFilters() to DOMContentLoaded.")

    with open("index.html", "w", encoding="utf-8") as f:
        f.write(html)

    # Sync to public/index.html and app/src/main/assets/index.html
    with open("public/index.html", "w", encoding="utf-8") as f:
        f.write(html)
    with open("app/src/main/assets/index.html", "w", encoding="utf-8") as f:
        f.write(html)

    print("Synced updated index.html to public/ and app/src/main/assets/.")

if __name__ == "__main__":
    main()
