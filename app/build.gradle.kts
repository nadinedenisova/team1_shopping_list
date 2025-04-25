plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrainsKsp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sqldelight)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "2.1.20"
}

android {
    namespace = "com.practicum.shoppinglist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.practicum.shoppinglist"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.androidx.runtime.android)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    ksp(libs.dagger.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.gson)

    val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
    implementation(composeBom)

    // Material Design 3
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // DB
    implementation(libs.sqldelight.driver)
    implementation(libs.sqldelight.coroutines)

    // Network
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)

    // Additional
    implementation(libs.kotlinx.serialization.json)

    // Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}

sqldelight {
    databases {
        create("ShoppingListDatabase") {
            packageName.set("com.practicum.shoppinglist")
        }
    }
}