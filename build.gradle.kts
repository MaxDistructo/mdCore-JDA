import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    }
}
plugins {
  java
  idea
    `java-library`
    maven

  kotlin("jvm") version "1.3.41"
    //id "com.github.ben-manes.versions" version "0.20.0"
    //id 'com.sedmelluq.jdaction' version '1.0.2'
}
group = "com.gitlab.MaxDistructo"

repositories {
  jcenter()
  mavenCentral()
  maven{
    name = "JDA Bintray"
    url = uri("https://dl.bintray.com/dv8fromtheworld/maven")
  }
}

// In this section you declare the dependencies for your production and test code
dependencies {
  compile (group= "org.jetbrains.kotlin", name= "kotlin-stdlib-jdk8", version="1.3.41")
  compile (group= "org.jetbrains.kotlinx", name= "kotlinx-coroutines-core", version="1.0.1")
  compile (group= "org.json", name= "json", version="20180813")
  compile (group= "commons-io", name= "commons-io", version="2.6")
  compile (group= "ch.qos.logback", name= "logback-classic", version="1.2.3")
  compile (group = "net.dv8tion", name= "JDA", version = "4.BETA.0_22")
  compile (group = "club.minnced", name= "discord-webhooks", version= "0.1.7")
  //compile (group = "com.jagrosh", name = "jda-utilities", version = "2.1.5")
}

tasks {
    withType<KotlinCompile> {
        (kotlinOptions).apply {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
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

