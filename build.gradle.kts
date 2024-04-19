import net.minecraftforge.gradle.common.util.RunConfig
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

val minecraftVersion: String by project
val minecraftVersionRange: String by project
val forgeVersion: String by project
val forgeVersionRange: String by project
val modLoader: String by project
val modLoaderVersionRange: String by project
val minecraftMappingChannel: String by project
val minecraftMappingVersion: String by project
val aquacultureVersionRange: String by project
val modId: String by project
val modName: String by project
val modLicense: String by project
val modVersion: String by project
val modGroupId: String by project
val modAuthors: String by project
val modCredits: String by project
val modDescription: String by project

val shade: Configuration by configurations.creating

plugins {
    java
    eclipse
    idea
    id("maven-publish")
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

repositories {
    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    maven("jitpack") {
        url = uri("https://jitpack.io")
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Minecraft
    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")

    // Aquaculture2
    implementation(fg.deobf("curse.maven:aquaculture-60028:4921323"))

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    kapt("org.projectlombok:lombok:1.18.30")

    // Jable
    minecraftLibrary("com.github.dsx137:jable:1.0.8")
    shade("com.github.dsx137:jable:1.0.6")

    // Kotlin
    minecraftLibrary("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    minecraftLibrary("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    minecraftLibrary("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    shade("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    shade("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    shade("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

val javaVersion = JavaLanguageVersion.of(17)

version = "$minecraftVersion-$modVersion"
group = modGroupId

base {
    archivesName.set(modId)
}

java {
    toolchain.languageVersion.set(javaVersion)
}

kapt {
    keepJavacAnnotationProcessors = true
}

minecraft {
    mappings(minecraftMappingChannel, minecraftMappingVersion)

    copyIdeResources = true

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    fun createMinecraftRun(vararg names: String, additionalConfig: RunConfig.() -> Unit = { }) {
        for (name in names) {
            minecraft.runs.create(name) {
                workingDirectory(project.file("run"))

                property("forge.logging.markers", "REGISTRIES")
                property("forge.logging.console.level", "debug")

                this.additionalConfig()

                mods {
                    create(modId) {
                        source(sourceSets["main"])
                    }
                }
            }
        }
    }

    createMinecraftRun("client", "server", "gameTestServer") {
        property("forge.enabledGameTestNamespaces", modId)
    }

    createMinecraftRun("data") {
        args(
            "--mod",
            modId,
            "--all",
            "--output",
            file("src/generated/resources/"),
            "--existing",
            file("src/main/resources/")
        )
    }
}

val props = mapOf(
    "minecraft_version" to minecraftVersion,
    "minecraft_version_range" to minecraftVersionRange,
    "forge_version" to forgeVersion,
    "forge_version_range" to forgeVersionRange,
    "mod_loader" to modLoader,
    "mod_loader_version_range" to modLoaderVersionRange,
    "mod_id" to modId,
    "mod_name" to modName,
    "mod_license" to modLicense,
    "mod_version" to modVersion,
    "mod_authors" to modAuthors,
    "mod_description" to modDescription,
    "aquaculture_version_range" to aquacultureVersionRange,
    "mod_credits" to modCredits
)

val generateTemplates by tasks.registering(Copy::class) {
    val src = file("src/main/templates/java")
    val dst = layout.buildDirectory.dir("generated/sources/templates/java")
    inputs.properties(props)
    outputs.upToDateWhen { false }

    from(src)
    into(dst)
    expand(props)
}
sourceSets["main"].java.srcDirs(generateTemplates.map { it.destinationDir })
rootProject.idea.project.settings.taskTriggers.afterSync(generateTemplates)
project.eclipse.synchronizationTasks(generateTemplates)

tasks.processResources {
    val targets = listOf("META-INF/mods.toml", "pack.mcmeta")

    inputs.properties(props)
//    replaceProperties["project"] = project

    filesMatching(targets) {
        expand(props)
    }
}
sourceSets["main"].resources.srcDirs("src/generated/resources")

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to modId,
                "Specification-Vendor" to modAuthors,
                "Specification-Version" to "1", // We are version 1 of ourselves
                "Implementation-Title" to project.name,
                "Implementation-Version" to modVersion,
                "Implementation-Vendor" to modAuthors,
                "Implementation-Timestamp" to DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                    .format(Date().toInstant().atOffset(ZoneOffset.UTC))
            )
        )
    }
}

tasks.shadowJar {
    minimize()
    configurations = listOf(shade)
    relocate("org.jetbrains", "${modGroupId}.shadowed.org.jetbrains")
    relocate("club.asynclab", "${modGroupId}.shadowed.club.asynclab")
}

val reobfShadowJar = reobf.create("shadowJar") {
}

tasks.build {
    dependsOn("reobfShadowJar")
}
