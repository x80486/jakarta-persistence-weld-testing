pluginManagement {
  plugins {
    id("io.freefair.lombok").version("8.10")
  }

  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

rootProject.name = "jakarta-persistence-weld-testing"
