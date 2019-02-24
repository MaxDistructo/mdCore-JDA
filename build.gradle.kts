buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.21")
    }
}

plugins {
  java
  idea
  application
  maven  

  kotlin("jvm") version "1.3.21"
    //id "com.github.ben-manes.versions" version "0.20.0"
    //id 'com.sedmelluq.jdaction' version '1.0.2'
}

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
  compile (group= "org.jetbrains.kotlin", name= "kotlin-stdlib-jdk8", version="1.3.21")
  compile (group= "org.jetbrains.kotlinx", name= "kotlinx-coroutines-core", version="1.0.1")
  compile (group= "org.json", name= "json", version="20180813")
  compile (group= "commons-io", name= "commons-io", version="2.6")
  compile (group= "ch.qos.logback", name= "logback-classic", version="1.2.3")
  compile (group = "net.dv8tion", name= "JDA", version = "3.8.3_460")
  compile (group = "com.jagrosh", name = "jda-utilities", version = "2.1.4")
}