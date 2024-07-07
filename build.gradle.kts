// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.parcelize) apply false
}

buildscript{
    repositories{
        google()
    }
    dependencies{
        classpath(libs.safe.args)
    }
}