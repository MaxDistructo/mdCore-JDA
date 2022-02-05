import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}
plugins {
  java
  idea
  `java-library`
  maven
  kotlin("jvm") version "1.6.10"
    //id "com.github.ben-manes.versions" version "0.20.0"
    //id 'com.sedmelluq.jdaction' version '1.0.2'
}
group = "com.gitlab.MaxDistructo"

repositories {
  jcenter()
  mavenCentral()
  maven("https://m2.dv8tion.net/releases")
  maven("https://m2.chew.pro/releases")
}

//val compile by configurations.creating

// In this section you declare the dependencies for your production and test code
dependencies {
  compile (group= "org.jetbrains.kotlin", name= "kotlin-stdlib", version="1.6.10")
  compile (group= "org.jetbrains.kotlinx", name= "kotlinx-coroutines-core", version="1.6.0")
  compile (group= "org.json", name= "json", version="20211205")
  compile (group= "commons-io", name= "commons-io", version="2.7")
  //normally we'd ignore this dependency's updates but with log4shell, better safe than sorry and update this
  compile (group= "ch.qos.logback", name= "logback-classic", version="1.2.10")
  compile (group = "net.dv8tion", name= "JDA", version = "5.0.0-alpha.5")
  compile (group = "club.minnced", name= "discord-webhooks", version= "0.7.5")
  compile (group = "pw.chew", name = "jda-chewtils", version = "1.24.1")
}

tasks {
    withType<KotlinCompile> {
        (kotlinOptions).apply {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
    val copyToLib by registering(Copy::class) {
        into("$buildDir/lib")
        from(configurations.compile)
    }
    val stage by registering {
        dependsOn("build", copyToLib)
    }
}

