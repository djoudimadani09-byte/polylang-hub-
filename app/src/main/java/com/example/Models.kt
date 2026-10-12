package com.example

enum class UserRole(val labelAr: String, val labelEn: String) {
    ADMIN("المشرف العام (Admin)", "Platform Administrator"),
    TRANSLATOR("مترجم معتمد", "Certified Translator"),
    STUDENT("طلبة الترجمة", "Translation Students"),
    LEARNER("متعلم لغات", "Language Learner"),
    CLIENT("عميل ترجمة", "Translation Client")
}

enum class AppTab(val titleAr: String, val titleEn: String) {
    HOME("الرئيسية", "Home"),
    SERVICES("خدمات الترجمة", "Services"),
    SUBTITLING("محرر SRT", "Subtitling"),
    ACADEMY("الأكاديمية", "Academy"),
    PRICING("الأسعار والباقات", "Pricing"),
    DASHBOARD("لوحة التحكم", "Dashboard")
}

enum class AppCurrency(val symbol: String, val rateToDzd: Double) {
    DZD("د.ج", 1.0),
    USD("$", 0.0074),
    EUR("€", 0.0068);

    val code: String get() = this.name

    fun format(amountDzd: Int): String {
        return when (this) {
            DZD -> "$amountDzd د.ج"
            USD -> String.format("%.2f $", amountDzd * rateToDzd)
            EUR -> String.format("%.2f €", amountDzd * rateToDzd)
        }
    }
}

data class MasterclassCourse(
    val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val instructor: String,
    val level: String,
    val desc: String,
    val videoUrl: String = ""
)

data class TranslationOrder(
    val id: String,
    val title: String,
    val category: String,
    val langPair: String,
    val wordCount: Int,
    val priceDzd: Int,
    var status: String,
    val isInterpretation: Boolean = false,
    val clientName: String = "كريم حمداوي",
    val clientEmail: String = "karim@polylang.dz",
    val clientPhone: String = "+213 555 12 34 56",
    val attachedFileName: String = "document.pdf",
    val date: String = "2026-10-12",
    val notes: String = "يرجى اعتماد الختم الرسمي وفق معيار ISO 17100"
)

data class SrtCue(
    val id: Int,
    val start: String,
    val end: String,
    val sourceText: String,
    val subtitleText: String
)

data class TermCard(
    val id: Int,
    val text: String,
    val isArabic: Boolean,
    var isSelected: Boolean = false,
    var isMatched: Boolean = false
)
