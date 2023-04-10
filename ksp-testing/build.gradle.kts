plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("de.mannodermaus.android-junit5") version "1.8.2.1"
}

android {
    namespace = "io.ezard.manuscript.ksp.testing"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    testImplementation(project(":ksp"))
    testImplementation(project(":manuscript"))
    testImplementation(libs.compose.ui)
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine)
    testImplementation(libs.kotlinCompileTesting.ksp)
}
