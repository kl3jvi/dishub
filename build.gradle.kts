val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "com.kl3jvi"
version = "0.0.1"
application {
    mainClass.set("com.kl3jvi.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.2.4")
    implementation("io.ktor:ktor-server-netty:2.2.4")
    implementation("io.ktor:ktor-auth:1.6.8")
    implementation("io.ktor:ktor-auth-jwt:1.6.8")
    implementation("io.ktor:ktor-serialization:2.2.4")
    implementation("io.ktor:ktor-server-tests:2.2.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("io.insert-koin:koin-ktor:3.3.1")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.jetbrains.exposed:exposed-java-time:0.40.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:2.2.4")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.0")
}
