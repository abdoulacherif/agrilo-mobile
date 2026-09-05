# Obfuscation activée en release (voir build.gradle.kts isMinifyEnabled).
# Ça rend la rétro-ingénierie de l'APK plus difficile.

-keepattributes Signature
-keepattributes *Annotation*
-keep class kotlinx.serialization.** { *; }
-keep class com.agrilo.shared.domain.** { *; }