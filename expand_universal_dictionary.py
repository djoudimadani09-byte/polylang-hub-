# -*- coding: utf-8 -*-
import json

with open('full_i18n_dictionary.json', 'r', encoding='utf-8') as f:
    DICT = json.load(f)

NEW_ENTRIES = {
    "الأستاذ جودي مداني (Djoudi Madani)": {
        "en": "Prof. Djoudi Madani", "fr": "Prof. Djoudi Madani", "de": "Prof. Djoudi Madani", "es": "Prof. Djoudi Madani", "it": "Prof. Djoudi Madani", "ru": "Проф. Джуди Мадани"
    },
    "مؤسس ورئيس المنصة": {
        "en": "Founder & Platform Director", "fr": "Fondateur & Président", "de": "Gründer & Plattformleiter", "es": "Fundador y Presidente", "it": "Fondatore e Presidente", "ru": "Основатель и президент"
    },
    "الجزائر العاصمة": {
        "en": "Algiers", "fr": "Alger", "de": "Algier", "es": "Argel", "it": "Algeri", "ru": "Алжир"
    },
    "+ إضافة مقطع زمني": {
        "en": "+ Add Subtitle Cue", "fr": "+ Ajouter un Segment Temporel", "de": "+ Untertitel-Segment Hinzufügen", "es": "+ Añadir Segmento Temporal", "it": "+ Aggiungi Segmento Temporale", "ru": "+ Добавить временной сегмент"
    },
    "💾 حفظ المسودة (Save Draft)": {
        "en": "💾 Save Draft", "fr": "💾 Enregistrer le Brouillon", "de": "💾 Entwurf Speichern", "es": "💾 Guardar Borrador", "it": "💾 Salva Bozza", "ru": "💾 Сохранить черновик"
    },
    "🔄 استعادة القالب": {
        "en": "🔄 Restore Template", "fr": "🔄 Restaurer le Modèle", "de": "🔄 Vorlage Wiederherstellen", "es": "🔄 Restaurar Plantilla", "it": "🔄 Ripristina Modello", "ru": "🔄 Восстановить шаблон"
    },
    "📥 تحميل ملف SRT المعتمد": {
        "en": "📥 Download Certified SRT", "fr": "📥 Télécharger le SRT Certifié", "de": "📥 Zertifizierte SRT Herunterladen", "es": "📥 Descargar SRT Certificado", "it": "📥 Scarica SRT Certificato", "ru": "📥 Скачать сертифицированный SRT"
    },
    "وقت البداية": { "en": "Start Time", "fr": "Début", "de": "Startzeit", "es": "Tiempo Inicial", "it": "Inizio", "ru": "Начало" },
    "وقت النهاية": { "en": "End Time", "fr": "Fin", "de": "Endzeit", "es": "Tiempo Final", "it": "Fine", "ru": "Конец" },
    "النص المترجم": { "en": "Translated Subtitle", "fr": "Sous-titre Traduit", "de": "Übersetzter Untertitel", "es": "Subtítulo Traducido", "it": "Sottotitolo Tradotto", "ru": "Текст перевода" },
    "معاينة": { "en": "Preview", "fr": "Aperçu", "de": "Vorschau", "es": "Vista previa", "it": "Anteprima", "ru": "Предпросмотр" },
    "حذف": { "en": "Delete", "fr": "Supprimer", "de": "Löschen", "es": "Eliminar", "it": "Elimina", "ru": "Удалить" },
    "إغلاق": { "en": "Close", "fr": "Fermer", "de": "Schließen", "es": "Cerrar", "it": "Chiudi", "ru": "Закрыть" },
    "إعادة الامتحان 🔄": { "en": "Restart Exam 🔄", "fr": "Recommencer l'Examen 🔄", "de": "Prüfung Wiederholen 🔄", "es": "Reiniciar Examen 🔄", "it": "Ricomincia Esame 🔄", "ru": "Пересдать экзамен 🔄" },
    "طباعة الشهادة 🖨️": { "en": "Print Certificate 🖨️", "fr": "Imprimer le Certificat 🖨️", "de": "Zertifikat Drucken 🖨️", "es": "Imprimir Certificado 🖨️", "it": "Stampa Certificato 🖨️", "ru": "Распечатать сертификат 🖨️" },
    "الإشعارات والتنبيهات المباشرة 🔔": { "en": "Live Notifications & Alerts 🔔", "fr": "Notifications & Alertes en Direct 🔔", "de": "Live-Benachrichtigungen 🔔", "es": "Notificaciones en Vivo 🔔", "it": "Notifiche Live 🔔", "ru": "Уведомления в реальном времени 🔔" },
    "الاسم الكامل أو المؤسسة:": { "en": "Full Name / Organization:", "fr": "Nom Complet / Organisation:", "de": "Vollständiger Name / Organisation:", "es": "Nombre Completo / Organización:", "it": "Nome Completo / Organizzazione:", "ru": "Полное имя / Организация:" },
    "البريد الإلكتروني المباشر:": { "en": "Direct Email:", "fr": "Email Direct:", "de": "Direkte E-Mail:", "es": "Correo Electrónico:", "it": "Email Diretta:", "ru": "Электронная почта:" },
    "البريد الإلكتروني المعتمد:": { "en": "Registered Email:", "fr": "Email Enregistré:", "de": "Registrierte E-Mail:", "es": "Correo Registrado:", "it": "Email Registrata:", "ru": "Зарегистрированная почта:" },
    "كلمة المرور:": { "en": "Password:", "fr": "Mot de passe:", "de": "Passwort:", "es": "Contraseña:", "it": "Password:", "ru": "Пароль:" },
    "نسيت كلمة المرور؟": { "en": "Forgot password?", "fr": "Mot de passe oublié?", "de": "Passwort vergessen?", "es": "¿Olvidó su contraseña?", "it": "Password dimenticata?", "ru": "Забыли пароль?" },
    "نوع الحساب والغرض من التسجيل:": { "en": "Account Role & Purpose:", "fr": "Rôle du Compte & Objectif:", "de": "Kontotyp & Zweck:", "es": "Tipo de Cuenta:", "it": "Ruolo Account & Scopo:", "ru": "Тип аккаунта и цель:" },
    "تسجيل الدخول الآمن 🚀": { "en": "Secure Sign In 🚀", "fr": "Connexion Sécurisée 🚀", "de": "Sicher Anmelden 🚀", "es": "Iniciar Sesión Seguro 🚀", "it": "Accesso Sicuro 🚀", "ru": "Безопасный вход 🚀" },
    "🔑 الدخول المباشر بالبريد": { "en": "🔑 Direct Email Sign In", "fr": "🔑 Connexion par Email", "de": "🔑 Direkte E-Mail-Anmeldung", "es": "🔑 Acceso por Correo", "it": "🔑 Accesso Diretto Email", "ru": "🔑 Вход по электронной почте" },
    "⚡ حسابات تجريبية سريعة": { "en": "⚡ Quick Demo Profiles", "fr": "⚡ Profils Démo Rapides", "de": "⚡ Schnelle Demo-Profile", "es": "⚡ Perfiles de Prueba Rápidos", "it": "⚡ Profili Demo Rapidi", "ru": "⚡ Быстрые демо-профили" },
    "المقر الرئيسي:": { "en": "Headquarters:", "fr": "Siège Principal:", "de": "Hauptsitz:", "es": "Sede Principal:", "it": "Sede Principale:", "ru": "Штаб-квартира:" },
    "بوابة متخصصة:": { "en": "Specialized Portal:", "fr": "Portail Spécialisé:", "de": "Fachportal:", "es": "Portal Especializado:", "it": "Portale Specializzato:", "ru": "Специализированный портал:" },
    "لطلبة الترجمة ومتعلمي اللغات": { "en": "For translation students & language learners", "fr": "Pour étudiants en traduction & apprenants", "de": "Für Übersetzungsstudenten & Sprachlerner", "es": "Para estudiantes de traducción e idiomas", "it": "Per studenti di traduzione e lingue", "ru": "Для студентов перевода и изучающих языки" },
    "خدمات الترجمة:": { "en": "Translation Services:", "fr": "Services de Traduction:", "de": "Übersetzungsdienste:", "es": "Servicios de Traducción:", "it": "Servizi di Traduzione:", "ru": "Услуги перевода:" },
    "معتمدة للمؤسسات والأفراد": { "en": "Certified for institutions & individuals", "fr": "Certifiées pour entreprises & particuliers", "de": "Zertifiziert für Institutionen & Privatkunden", "es": "Certificadas para empresas y particulares", "it": "Certificate per istituzioni e privati", "ru": "Сертифицировано для компаний и частных лиц" },
    "لغات التخصص:": { "en": "Working Languages:", "fr": "Langues de Spécialisation:", "de": "Arbeitssprachen:", "es": "Idiomas de Trabajo:", "it": "Lingue di Lavoro:", "ru": "Рабочие языки:" },
    "عربي • فرنسي • ألماني • إنجليزي": { "en": "Arabic • French • German • English", "fr": "Arabe • Français • Allemand • Anglais", "de": "Arabisch • Französisch • Deutsch • Englisch", "es": "Árabe • Francés • Alemán • Inglés", "it": "Arabo • Francese • Tedesco • Inglese", "ru": "Арабский • Французский • Немецкий • Английский" },
    "إرسال وتأكيد الطلب 🚀": { "en": "Submit & Confirm Order 🚀", "fr": "Envoyer et Confirmer la Commande 🚀", "de": "Auftrag Absenden & Bestätigen 🚀", "es": "Enviar y Confirmar Pedido 🚀", "it": "Invia e Conferma Ordine 🚀", "ru": "Отправить и подтвердить заказ 🚀" },
    "تفاصيل وملاحظات المستند:": { "en": "Document Details & Instructions:", "fr": "Détails & Instructions du Document:", "de": "Dokumentdetails & Anweisungen:", "es": "Detalles e Instrucciones:", "it": "Dettagli e Istruzioni Documento:", "ru": "Детали и примечания к документу:" },
    "ارفاق المستند (PDF أو صورة عالية الجودة):": { "en": "Attach Document (PDF or High-Res Image):", "fr": "Joindre le Document (PDF ou Image):", "de": "Dokument Anhängen (PDF oder Bild):", "es": "Adjuntar Documento (PDF o Imagen):", "it": "Allega Documento (PDF o Immagine):", "ru": "Прикрепить документ (PDF или фото):" },
    "رقم الهاتف أو واتساب المباشر:": { "en": "Phone / WhatsApp:", "fr": "Téléphone / WhatsApp:", "de": "Telefon / WhatsApp:", "es": "Teléfono / WhatsApp:", "it": "Telefono / WhatsApp:", "ru": "Телефон / WhatsApp:" },
    "اختر خطاب الترجمة التتابعية (خطابات دبلوماسية واقتصادية متخصصة):": {
        "en": "Select Consecutive Speech (Diplomatic & Economic Speeches):",
        "fr": "Choisir le Discours Consécutif (Discours Diplomatiques et Économiques):",
        "de": "Konsekutivrede Wählen (Diplomatische & Wirtschaftliche Reden):",
        "es": "Seleccionar Discurso Consecutivo (Discursos Diplomáticos y Económicos):",
        "it": "Seleziona Discorso Consecutivo (Diplomatico ed Economico):",
        "ru": "Выберите речь для последовательного перевода (дипломатия и экономика):"
    },
    "النص المصدر للتدوين:": {
        "en": "Source Speech for Notation:", "fr": "Discours Source pour Prise de Notes:", "de": "Quellrede für Notizennahme:", "es": "Discurso Fuente para Toma de Notas:", "it": "Discorso Fonte per Presa Note:", "ru": "Исходная речь для конспектирования:"
    },
    "مفكرة روزان لتدوين الملاحظات (Verticality + Shift):": {
        "en": "Rozan Notepad (Verticality + Shift):", "fr": "Bloc-notes Rozan (Verticalité + Décalage):", "de": "Rozan-Notizblock (Vertikalität + Versatz):", "es": "Cuaderno Rozan (Verticalidad + Desplazamiento):", "it": "Blocco Note Rozan (Verticalità + Shift):", "ru": "Блокнот Розана (Вертикальность + Сдвиг):"
    },
    "الاستماع إلى المتحدث 🔊": {
        "en": "Listen to Speaker 🔊", "fr": "Écouter l'Orateur 🔊", "de": "Sprecher Anhören 🔊", "es": "Escuchar al Orador 🔊", "it": "Ascolta Oratore 🔊", "ru": "Слушать оратора 🔊"
    },
    "عرض المقارنة مع الترجمة النموذجية ورموز روزان 🔍": {
        "en": "Compare with Model Solution & Rozan Symbols 🔍",
        "fr": "Comparer avec la Solution Modèle & Symboles Rozan 🔍",
        "de": "Mit Musterlösung & Rozan-Symbolen Vergleichen 🔍",
        "es": "Comparar con Solución Modelo y Símbolos Rozan 🔍",
        "it": "Confronta con Soluzione Modello & Simboli Rozan 🔍",
        "ru": "Сравнить с эталоном и символами Розана 🔍"
    },
    "الرموز النموذجية بنظام روزان (Rozan Model Notation):": {
        "en": "Rozan Model Symbol Notation:", "fr": "Prise de Notes Modèle Rozan:", "de": "Muster-Notizen nach Rozan-System:", "es": "Toma de Notas Modelo Rozan:", "it": "Notazione Simboli Modello Rozan:", "ru": "Эталонные символы по системе Розана:"
    }
}

DICT.update(NEW_ENTRIES)
print(f"Updated dictionary now has {len(DICT)} entries")

with open('full_i18n_dictionary.json', 'w', encoding='utf-8') as f:
    json.dump(DICT, f, ensure_ascii=False, indent=2)

# Update index.html UNIVERSAL_I18N_DICT
with open('index.html', 'r', encoding='utf-8') as f:
    html = f.read()

dict_marker = 'const UNIVERSAL_I18N_DICT = '
if dict_marker in html:
    prefix = html.split(dict_marker)[0] + dict_marker
    rest = html.split(dict_marker)[1]
    # find ending of dict object
    end_marker = ';\n\n    function translateEntireDOM('
    if end_marker in rest:
        suffix = end_marker + rest.split(end_marker, 1)[1]
        new_dict_json = json.dumps(DICT, ensure_ascii=False)
        html = prefix + new_dict_json + suffix
        with open('index.html', 'w', encoding='utf-8') as out_f:
            out_f.write(html)
        print("Updated UNIVERSAL_I18N_DICT in index.html successfully!")
    else:
        print("Could not find end marker in index.html")
else:
    print("Could not find dict_marker in index.html")
