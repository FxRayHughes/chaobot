import io.izzel.taboolib.gradle.*

plugins {
    id("io.izzel.taboolib") version "2.0.12"
    kotlin("jvm") version "1.9.24"
    application
}

taboolib {
    env {
        install(APPLICATION)
        install(CONFIGURATION)
        install(EXPANSION_COMMAND_HELPER)
        // 依赖下载目录
        fileLibs = "libraries"
        // 资源下载目录
        fileAssets = "assets"
    }
    version {
        taboolib = "6.1.1"
        coroutines = "1.7.3"
        skipKotlinRelocate = true
        skipTabooLibRelocate = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    taboo("io.javalin:javalin:6.1.3")
    taboo("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.24")
    taboo("org.slf4j:slf4j-simple:2.0.10")
    taboo("com.alibaba.fastjson2:fastjson2-kotlin:2.0.50")
    testImplementation(kotlin("test"))
    taboo(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "top.maplex.chaobot.Main")
    }
}

application {
    mainClass.set("top.maplex.chaobot.Main")
}