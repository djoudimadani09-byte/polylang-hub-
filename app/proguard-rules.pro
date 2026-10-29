# Proguard rules for Polylang Hub Android
# Keeps Android Jetpack Compose, Coroutines, and Data Models

-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

-keep class com.example.Models** { *; }
-keep class com.example.Course** { *; }
-keep class com.example.BoothSpeech { *; }
-keep class com.example.ExamQuestion { *; }

# Coil image loading
-keep class coil.** { *; }

# Kotlin coroutines
-keepattributes *Annotation*, InnerClasses, EnclosingMethod
-keepclassmembers class kotlinx.coroutines.** { *; }
