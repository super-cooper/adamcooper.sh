plugins {
    kotlin("multiplatform") version "1.7.10"
    application
}

group = "sh.adamcooper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm("java") {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("src/common/main")
            resources.srcDir("src/common/main/resources")
        }
        val commonTest by getting {
            kotlin.srcDir("src/common/test")
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val javaMain by getting {
            kotlin.srcDir("src/java/main")
            resources.srcDir("src/java/main/resources")
            dependencies {
                implementation("io.ktor:ktor-server-cio:2.0.1")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")

                implementation("io.ktor:ktor-server-call-logging:2.0.1")
                implementation("ch.qos.logback:logback-classic:1.3.0-beta0")
            }
        }
        val javaTest by getting {
            kotlin.srcDir("src/java/test")
        }
        val jsMain by getting {
            resources.srcDir("src/java/main/resources")
            kotlin.srcDir("src/js/main")
        }
        val jsTest by getting {
            kotlin.srcDir("src/js/test")
        }
    }
}

application {
    mainClass.set("sh.adamcooper.MainKt")
}

tasks.named<Copy>("javaProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("javaJar"))
    classpath(tasks.named<Jar>("javaJar"))
}
