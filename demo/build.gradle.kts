import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktfmt)
    id("androidx.navigation.safeargs")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "eu.autopay.pay.demo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "eu.autopay.pay.demo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = 35
        versionCode =
            libs.versions.versionMajor.get().toInt().times(100 * 100 * 100) +
                libs.versions.versionMinor.get().toInt().times(100 * 100) +
                libs.versions.versionPatch.get().toInt().times(100) +
                libs.versions.versionAlpha.get().toInt()
        versionName =
            "${libs.versions.versionMajor.get()}.${libs.versions.versionMinor.get()}.${libs.versions.versionPatch.get()}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    flavorDimensions.add("type")

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_11 } }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

ktfmt { kotlinLangStyle() }

dependencies {
    implementation(libs.autopay.sdk.ocr)
    // or
    //implementation(libs.autopay.sdk)

    implementation(libs.androidx.core.ktx)
    implementation(libs.android.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.compose.navigation)
    implementation(libs.jackson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
