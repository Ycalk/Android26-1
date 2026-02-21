plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.8.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}