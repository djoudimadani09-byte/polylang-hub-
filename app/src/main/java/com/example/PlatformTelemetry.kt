package com.example

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

// Demo telemetry data intentionally sanitized for public repository visibility.
object PlatformTelemetry {
    val visitorSessionLogs = mutableStateListOf<VisitorSessionLog>()
    val exerciseRecords = mutableStateListOf<StudentExerciseRecord>()

    init {
        visitorSessionLogs.addAll(
            listOf(
                VisitorSessionLog(
                    id = "LOG-1046",
                    timestamp = "2026-10-30 07:55:18",
                    userName = "يوسف قادري",
                    userEmail = "learner@polylang.dz",
                    role = "متعلم لغات (Learner)",
                    actionType = "لعبة لغات",
                    details = "إنهاء تحدي سباق السرعة المصطلحي بنتيجة 940 نقطة"
                ),
                VisitorSessionLog(
                    id = "LOG-1045",
                    timestamp = "2026-10-30 07:20:00",
                    userName = "كريم حمداوي",
                    userEmail = "client@polylang.dz",
                    role = "عميل (Client)",
                    actionType = "طلب ترجمة",
                    details = "إيداع ملف عقد استثماري رسمي للترجمة والختم"
                )
            )
        )

        exerciseRecords.addAll(
            listOf(
                StudentExerciseRecord(
                    id = "EX-301",
                    timestamp = "2026-10-30 08:15",
                    studentName = "سارة بن سالم",
                    studentEmail = "student@polylang.dz",
                    exerciseType = "كابينة 100 خطاب",
                    exerciseTitle = "خطاب قمة المناخ COP28 - كلمة رئيس الوفد",
                    score = "96%",
                    decalageSeconds = 3.1,
                    wpm = 138
                ),
                StudentExerciseRecord(
                    id = "EX-302",
                    timestamp = "2026-10-30 07:55",
                    studentName = "يوسف قادري",
                    studentEmail = "learner@polylang.dz",
                    exerciseType = "12 لعبة لغات",
                    exerciseTitle = "سباق السرعة للمصطلحات القانونية والدبلوماسية",
                    score = "940 نقطة",
                    wpm = 95
                ),
                StudentExerciseRecord(
                    id = "EX-303",
                    timestamp = "2026-10-29 19:40",
                    studentName = "أمين بوحفص",
                    studentEmail = "student@polylang.dz",
                    exerciseType = "امتحان تحديد مستوى CEFR",
                    exerciseTitle = "امتحان TCF الفرنسي الشامل (مستوى C1)",
                    score = "88/100"
                ),
                StudentExerciseRecord(
                    id = "EX-304",
                    timestamp = "2026-10-29 16:10",
                    studentName = "مريم زروقي",
                    studentEmail = "student@polylang.dz",
                    exerciseType = "كابينة 100 خطاب",
                    exerciseTitle = "جلسة مجلس الأمن حول الأمن الغذائي الدولي",
                    score = "92%",
                    wpm = 119
                )
            )
        )
    }
}
