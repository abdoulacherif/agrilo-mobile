import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) load(f.inputStream())
}

android {
    namespace = "com.agrilo.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.agrilo.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        // Les clés viennent de local.properties (dev) ou des secrets CI
        // (prod), jamais codées en dur dans le code source.
        buildConfigField(
            "String", "SUPABASE_URL",
            "\"${localProps.getProperty("SUPABASE_URL", System.getenv("SUPABASE_URL") ?: "")}\""
        )
        buildConfigField(
            "String", "SUPABASE_ANON_KEY",
            "\"${localProps.getProperty("SUPABASE_ANON_KEY", System.getenv("SUPABASE_ANON_KEY") ?: "")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
}