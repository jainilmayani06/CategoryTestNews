// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://www.jitpack.io" )
        }

    }
    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

}