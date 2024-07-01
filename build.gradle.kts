// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false //https://stackoverflow.com/questions/77342860/how-to-resolve-the-gradle-build-error-java-lang-nosuchmethoderror#:~:text=You%20might%20need%20to%20bump
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.24" apply false
}


buildscript{
    repositories{
        google()
    }
    dependencies{
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}