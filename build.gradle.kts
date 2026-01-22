plugins {
    kotlin("jvm") version "2.2.21"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

application {
    mainClass.set("com.oadultradeepfield.wordlecli.MainKt")
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
