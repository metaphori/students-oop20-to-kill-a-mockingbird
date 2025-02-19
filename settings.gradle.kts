import org.danilopianini.VersionAliases.justAdditionalAliases
buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.danilopianini:refreshversions-aliases:+")
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.10.0"
}

refreshVersions {
    extraArtifactVersionKeyRules = justAdditionalAliases
}

rootProject.name = "oop-reporting-machinery-to-kill-a-mocking-bird"
