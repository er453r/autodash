import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    val kvisionVersion: String by System.getProperties()

    kotlin("plugin.serialization") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion

    id("io.kvision") version kvisionVersion
    id("org.openapi.generator") version "7.8.0"
    id("com.github.ben-manes.versions") version "0.51.0"
}

openApiGenerate {
    generatorName = "kotlin"
    inputSpec = "utils/api.json"
    configOptions = mapOf(
        "library" to "multiplatform",
        "dateLibrary" to "kotlinx-datetime",
    )
}

version = "0.0.1"
group = "com.er453r"

repositories {
    mavenCentral()
}

val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()
val ktorVersion: String by System.getProperties()

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
            }
            runTask {
                devServerProperty = KotlinWebpackConfig.DevServer(
                    static = mutableListOf("${layout.buildDirectory.asFile.get()}/processedResources/js/main")
                )
            }
        }

        binaries.executable()
    }

    sourceSets{
        jsMain{
            dependencies {
                implementation("io.kvision:kvision:$kvisionVersion")
                implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
                implementation("io.kvision:kvision-state:$kvisionVersion")
                implementation("io.kvision:kvision-routing-navigo-ng:$kvisionVersion")

                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            }

            kotlin.srcDir("build/generate-resources-fixed/main/src/main")
        }
    }
}

tasks.named("compileKotlinJs") {
    dependsOn(tasks.openApiGenerate)
    dependsOn(tasks.named("replaceWordInSources"))
}

tasks.register<Copy>("replaceWordInSources") {
    dependsOn(tasks.openApiGenerate)

    from("build/generate-resources") {
        include("**/*.kt")            // include all Kotlin files
        filter { line ->
            line.replace("`data`: kotlin.String,", "`data`: kotlinx.serialization.json.JsonObject,")
        }
    }
    into("build/generate-resources-fixed")
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
