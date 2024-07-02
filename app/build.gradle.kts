plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.inventoryappflutterwaveassessment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.inventoryappflutterwaveassessmentt"
        minSdk = 29
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlin {
        jvmToolchain(8)
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.bundles.default)

    androidTestImplementation(libs.bundles.androidTestImplementation)

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:2.6.0")

    ksp("androidx.room:room-compiler:2.6.0")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")

}