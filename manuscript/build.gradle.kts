import org.jetbrains.dokka.gradle.DokkaTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.ezard.manuscript"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    publishing {
        multipleVariants {
            allVariants()
        }
    }
}

mavenPublishing {
    signAllPublications()
}

dependencies {
    api(project(":annotations"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    coreLibraryDesugaring(libs.android.tools.desugar)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.tooling)
    implementation(libs.compose.tooling.preview)
}

tasks.withType(DokkaTask::class.java) {
    moduleName.set("Manuscript")
    moduleVersion.set(VERSION)
    outputDirectory.set(file("docs"))
}
