pluginManagement {
    repositories {
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://maven.parchmentmc.org") }
        gradlePluginPortal()
        mavenCentral()
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
