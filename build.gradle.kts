import java.nio.charset.StandardCharsets

plugins {
  id("io.freefair.lombok")
  id("org.gradle.eclipse")
  id("org.gradle.idea")
  id("org.gradle.java")
}

group = "org.acme"
version = "0.1.0"
description = "Jakarta Persistence"

dependencies {
  implementation(enforcedPlatform("jakarta.platform:jakarta.jakartaee-bom:11.0.0-M4"))
  testImplementation(enforcedPlatform("org.junit:junit-bom:5.11.0"))

  implementation("jakarta.enterprise:jakarta.enterprise.cdi-api")
  implementation("jakarta.inject:jakarta.inject-api")
  implementation("org.eclipse.persistence:eclipselink:4.0.4")
  implementation("jakarta.transaction:jakarta.transaction-api")

  runtimeOnly("com.h2database:h2:2.3.232")

  testImplementation("org.assertj:assertj-core:3.26.3")
  testImplementation("org.jboss.weld:weld-junit5:4.0.3.Final")
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation("org.testcontainers:postgresql:1.20.1")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

repositories {
  mavenCentral()
}

configure<JavaPluginExtension> {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks {
  withType<JavaCompile>().configureEach {
    options.apply {
      compilerArgs = listOf("-parameters")
      encoding = StandardCharsets.UTF_8.name()
    }
  }

  withType<Test>().configureEach {
    jvmArgs = listOf("-Duser.timezone=UTC")
    testLogging {
      showCauses = true
      showExceptions = true
      showStackTraces = true
    }
    useJUnitPlatform()
  }
}
