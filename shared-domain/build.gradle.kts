plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

// The two independent builds use different Kotlin versions; keep their outputs separate.
layout.buildDirectory.set(rootProject.layout.buildDirectory.dir("shared-domain"))

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    jvmToolchain(11)
    js(IR) { browser() }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            }
        }
    }
}