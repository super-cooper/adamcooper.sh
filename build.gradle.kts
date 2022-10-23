val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val kotlinxCSSVersion = "1.0.0-pre.407"

plugins {
    kotlin("multiplatform") version "1.7.20"
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
            kotlinOptions.jvmTarget = "16"
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
                cssSupport {
                    enabled = true
                }
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
                implementation("io.ktor:ktor-server-cio:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.412")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                implementation(project(":apps:wordle"))
                implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
                implementation("mysql:mysql-connector-java:8.0.30")
            }
        }
        val javaTest by getting {
            kotlin.srcDir("src/java/test")
        }
        val jsMain by getting {
            resources.srcDir("src/js/main/resources")
            kotlin.srcDir("src/js/main")
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:$kotlinxCSSVersion")
            }
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

tasks.named<Jar>("javaJar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("javaJar"))
    classpath(tasks.named<Jar>("javaJar"))
}
