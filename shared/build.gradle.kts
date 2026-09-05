plugins {
    kotlin("multiplatform")
    kotlin("android")
    id("com.android.library")
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.jan-tennert.supabase:postgrest-kt:2.6.0")
                implementation("io.github.jan-tennert.supabase:gotrue-kt:2.6.0")
                implementation("io.ktor:ktor-client-core:2.3.12")
                implementation("io.github.russhwolf:multiplatform-settings:1.1.1")
                implementation("io.github.russhwolf:multiplatform-settings-no-arg:1.1.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.12")
                implementation("androidx.security:security-crypto:1.1.0-alpha06")
            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.12")
            }
        }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
    }
}

android {
    namespace = "com.agrilo.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}