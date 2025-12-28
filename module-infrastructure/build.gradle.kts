plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    kotlin("plugin.noarg") version "2.2.21"
    id("org.springframework.boot") version "4.0.2-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.7"
    `java-library`
}

group = "io.doriball"
version = "0.0.1-SNAPSHOT"
description = "module-infrastructure"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(project(":module-core"))
    api("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<org.gradle.api.tasks.bundling.Jar>("jar") {
    enabled = true
}

noArg {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

allOpen {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}