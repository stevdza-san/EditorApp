import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "com.stevdza.san"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set(
                "Web app that allows you to paste, apply colors and export your code as a beautiful image." +
                        " Which you can use later to share with anyone on social media."
            )

            head.add {
                script {
                    async = true
                    src = "https://www.googletagmanager.com/gtag/js?id=G-M6NQ3C6B4S"
                }
                script {
                    src = "gtag.js"
                }
                script {
                    src = "dom-to-image.js"
                }
                script {
                    src = "FileSaver.js"
                }
                script {
                    src = "highlight.min.js"
                }
                script {
                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
                }
                link {
                    rel = "stylesheet"
                    href = "lightfair.css"
                }
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("san", includeServer = true)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
                implementation(libs.kobwebx.markdown)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kobweb.api)
            }
        }
    }
}