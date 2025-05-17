import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins { alias(libs.plugins.kotlin.multiplatform) }

group = "sh.adamcooper"

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    jvm("java") {
        compilerOptions { jvmTarget = JvmTarget.JVM_23 }
        testRuns["test"].executionTask.configure { useJUnitPlatform() }
        binaries { executable { mainClass.set("sh.adamcooper.MainKt") } }
    }
    js(IR) {
        binaries.executable()
        browser { commonWebpackConfig { cssSupport { enabled = true } } }
    }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("src/common/main")
            resources.srcDir("src/common/main/resources")
        }
        val commonTest by getting {
            kotlin.srcDir("src/common/test")
            dependencies { implementation(kotlin("test")) }
        }
        val javaMain by getting {
            kotlin.srcDir("src/java/main")
            resources.srcDir("src/java/main/resources")
            dependencies {
                implementation(libs.bundles.apps)
                implementation(libs.bundles.db)
                implementation(libs.bundles.logging)
                implementation(libs.bundles.kotlin.datetime)
                implementation(libs.bundles.kotlin.web)
                implementation(libs.bundles.ktor.server)
            }
        }
        val javaTest by getting { kotlin.srcDir("src/java/test") }
        val jsMain by getting {
            resources.srcDir("src/js/main/resources")
            kotlin.srcDir("src/js/main")
            dependencies { implementation(libs.kotlin.css) }
        }
        val jsTest by getting { kotlin.srcDir("src/js/test") }
    }
}

tasks.named<Copy>("javaProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<Jar>("javaJar") {
    // Include compiled JS in JAR
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    dependsOn("jsProductionExecutableCompileSync")
    manifest { attributes["Main-Class"] = "sh.adamcooper.MainKt" }
    from("${layout.buildDirectory}/js/packages/adamcooper-sh/kotlin") { into("static/js") }

    // Include all runtime dependencies in the JAR
    val javaMainCompilation = kotlin.targets["java"].compilations["main"]
    from(javaMainCompilation.output)
    dependsOn(javaMainCompilation.compileDependencyFiles)

    // Include all dependencies in the JAR
    from(
        configurations.getByName("javaRuntimeClasspath").map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}

tasks.named<JavaExec>("runJava") {
    dependsOn(tasks.named<Jar>("javaJar"))
    classpath(tasks.named<Jar>("javaJar"))
}
