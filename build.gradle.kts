import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.spotless)
}

group = "sh.adamcooper"

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    jvm("java") {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_23
            freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
        }
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
            resources.setSrcDirs(listOf("src/java/main/resources", tasks.named("jsBrowserDistribution")))
            dependencies {
                implementation(libs.bundles.apps)
                implementation(libs.bundles.db)
                implementation(libs.bundles.logging)
                implementation(libs.bundles.kotlin.coroutines)
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

spotless {
    kotlin {
        ktfmt("0.56").kotlinlangStyle()
    }
}

tasks.named<Copy>("javaProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution) { into("/static/scripts") }
}

tasks.named<Jar>("javaJar") {
    manifest { attributes["Main-Class"] = "sh.adamcooper.MainKt" }

    // Include compiled JS in JAR
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn("jsProductionExecutableCompileSync")
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
