package com.example

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class VisitorSessionLog(
    val id: String,
    val timestamp: String,
    val userName: String,
    val userEmail: String,
    val role: String,
    val actionType: String,
    val details: String,
    val isVerified: Boolean = true
)

data class StudentExerciseRecord(
    val id: String,
    val timestamp: String,
    val studentName: String,
    val studentEmail: String,
    val exerciseType: String,
    val exerciseTitle: String,
    val score: String,
    val decalageSeconds: Double? = null,
    val wpm: Int? = null,
    val status: String = "مكتمل وموثق"
)

object PlatformTelemetry {
    val sessionLogs = mutableStateListOf<VisitorSessionLog>()
    val exerciseRecords = mutableStateListOf<StudentExerciseRecord>()

    init {
        // Seed initial synthetic demo audit records
        sessionLogs.addAll(
            listOf(
                VisitorSessionLog(
                    id = "LOG-1049",
                    timestamp = "2026-10-30 08:45:12",
                    userName = "مشرف تجريبي (Demo Admin)",
                    userEmail = "admin@example.com",
                    role = "مشرف عام (Admin)",
                    actionType = "تسجيل دخول إداري",
                    details = "جلسة فحص أمني وتدقيق منظومة الترجمة المحلفة"
                ),
                VisitorSessionLog(
                    id = "LOG-1048",
                    timestamp = "2026-10-30 08:32:04",
                    userName = "مترجم تجريبي (Demo Translator)",
                    userEmail = "translator@example.com",
                    role = "مترجم محلف (Translator)",
                    actionType = "اعتماد ترجمة",
                    details = "ختم واعتماد مشروع تجريبي رقم DEMO-001"
                ),
                VisitorSessionLog(
                    id = "LOG-1047",
                    timestamp = "2026-10-30 08:15:30",
                    userName = "طالب تجريبي 1 (Demo Student 1)",
                    userEmail = "student@example.com",
                    role = "طالبة ترجمة (Student)",
                    actionType = "تمرين كابينة",
                    details = "إكمال تمرين كابينة تجريبي بفارق زمني 3.1 ثوانٍ"
                ),
                VisitorSessionLog(
                    id = "LOG-1046",
                    timestamp = "2026-10-30 07:55:18",
                    userName = "متعلم تجريبي (Demo Learner)",
                    userEmail = "learner@example.com",
                    role = "متعلم لغات (Learner)",
                    actionType = "لعبة لغات",
                    details = "إنهاء تحدي سباق السرعة المصطلحي بنتيجة 940 نقطة"
                ),
                VisitorSessionLog(
                    id = "LOG-1045",
                    timestamp = "2026-10-30 07:20:00",
                    userName = "عميل تجريبي (Demo Client)",
                    userEmail = "client@example.com",
                    role = "عميل (Client)",
                    actionType = "طلب ترجمة",
                    details = "إيداع ملف تجريبي للترجمة والختم"
                )
            )
        )

        exerciseRecords.addAll(
            listOf(
                StudentExerciseRecord(
                    id = "EX-301",
                    timestamp = "2026-10-30 08:15",
                    studentName = "طالب تجريبي 1 (Demo Student 1)",
                    studentEmail = "student@example.com",
                    exerciseType = "كابينة 100 خطاب",
                    exerciseTitle = "خطاب قمة المناخ COP28 - كلمة رئيس الوفد",
                    score = "96%",
                    decalageSeconds = 3.1,
                    wpm = 138
                ),
                StudentExerciseRecord(
                    id = "EX-302",
                    timestamp = "2026-10-30 07:55",
                    studentName = "متعلم تجريبي (Demo Learner)",
                    studentEmail = "learner@example.com",
                    exerciseType = "12 لعبة لغات",
                    exerciseTitle = "سباق السرعة للمصطلحات القانونية والدبلوماسية",
                    score = "940 نقطة",
                    wpm = 95
                ),
                StudentExerciseRecord(
                    id = "EX-303",
                    timestamp = "2026-10-29 19:40",
                    studentName = "طالب تجريبي 2 (Demo Student 2)",
                    studentEmail = "student@example.com",
                    exerciseType = "امتحان تحديد مستوى CEFR",
                    exerciseTitle = "امتحان TCF الفرنسي الشامل (مستوى C1)",
                    score = "88/100"
                ),
                StudentExerciseRecord(
                    id = "EX-304",
                    timestamp = "2026-10-29 16:10",
                    studentName = "طالب تجريبي 3 (Demo Student 3)",
                    studentEmail = "student@example.com",
                    exerciseType = "كابينة 100 خطاب",
                    exerciseTitle = "جلسة مجلس الأمن حول الأمن الغذائي الدولي",
                    score = "92%",
                    decalageSeconds = 3.4,
                    wpm = 142
                )
            )
        )
    }

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun logSession(userName: String, userEmail: String, role: String, actionType: String, details: String) {
        val now = timeFormat.format(Date())
        val logId = "LOG-${(1050..9999).random()}"
        sessionLogs.add(0, VisitorSessionLog(logId, now, userName, userEmail, role, actionType, details))
    }

    fun logExercise(studentName: String, studentEmail: String, type: String, title: String, score: String, decalage: Double? = null, wpm: Int? = null) {
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val exId = "EX-${(305..9999).random()}"
        exerciseRecords.add(0, StudentExerciseRecord(exId, now, studentName, studentEmail, type, title, score, decalage, wpm))
    }
}
