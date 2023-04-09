import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishBasePlugin
import com.vanniktech.maven.publish.SonatypeHost

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.maven.publish) apply false
}

subprojects {
    plugins.withType(MavenPublishBasePlugin::class.java).configureEach {
        project.extensions.getByType<MavenPublishBaseExtension>().apply {
            @Suppress("UnstableApiUsage")
            coordinates("io.ezard.manuscript", project.name, VERSION)
            publishToMavenCentral(SonatypeHost.S01)

            @Suppress("UnstableApiUsage")
            pom {
                name.set("Manuscript")
                description.set("Component library creator for Jetpack Compose")
                inceptionYear.set("2022")
                url.set("https://github.com/Ezard/manuscript")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ezard")
                        name.set("Ben Ezard")
                        url.set("https://github.com/Ezard")
                    }
                }
                scm {
                    url.set("https://github.com/Ezard/manuscript")
                    connection.set("scm:git:git://github.com/Ezard/manuscript.git")
                    developerConnection.set("scm:git:ssh://git@github.com/Ezard/manuscript.git")
                }
            }
        }
    }
}
