plugins {
    kotlin("jvm") version "2.2.21"
}

group = "com.oadultradeepfield.wordlecli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}