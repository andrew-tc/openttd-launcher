import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.10"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    implementation("io.reactivex.rxjava2:rxjava:2.2.3")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")

    implementation("org.whitesource:pecoff4j:0.0.2.1")
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("com.github.kittinunf.fuel:fuel:1.16.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.antimo.openttdlauncher.app.MainKt"
}



