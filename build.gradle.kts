val ktor_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val kmongo_version: String by project
val logback_version: String by project
val logback_gelf_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    kotlin("plugin.serialization") version "1.7.21"
}

group = "io.github.tuguzd.ktor_krakend"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Core application
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-locations-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    // Dependency injection
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // MongoDB Driver
    implementation("org.litote.kmongo:kmongo:$kmongo_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("de.siegmar:logback-gelf:$logback_gelf_version")
    implementation("io.ktor:ktor-client-encoding:2.1.3")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
}
