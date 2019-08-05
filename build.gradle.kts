import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.JavaVersion.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val benmanes: String by System.getProperties()
  val kotlin: String by System.getProperties()
  val shadow: String by System.getProperties()

  id("com.github.johnrengelman.shadow") version shadow
  id("org.jetbrains.kotlin.jvm") version kotlin
  id("com.github.ben-manes.versions") version benmanes
}

group = "org.github"
version = "1.0-SNAPSHOT"

repositories {
  maven { url = uri("https://maven.aliyun.com/repository/public") }
  maven { url = uri("https://maven.aliyun.com/repository/spring") }
  maven { url = uri("https://maven.aliyun.com/repository/google") }

  mavenCentral()
}

val logback: String by System.getProperties()
val groovy: String by System.getProperties()
val guava: String by System.getProperties()
val vertx: String by System.getProperties()
val junit: String by System.getProperties()

dependencies {
  implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertx")
  implementation("io.vertx:vertx-lang-kotlin:$vertx")
  implementation("io.vertx:vertx-web-client:$vertx")
  implementation("io.vertx:vertx-web:$vertx")
  implementation("com.google.guava:guava:$guava")
  implementation("org.codehaus.groovy:groovy:$groovy")
  implementation("ch.qos.logback:logback-classic:$logback")

  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  testImplementation("junit:junit:$junit")
  testImplementation("io.vertx:vertx-unit:$vertx")
  compileOnly("io.vertx:vertx-codegen:$vertx")
}

configure<JavaPluginConvention> {
  sourceCompatibility = VERSION_1_8
  targetCompatibility = VERSION_1_8
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
  kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
}

tasks.withType<ShadowJar> {
  archiveFileName.set("vertx-boot.jar")
  manifest {
    attributes(mapOf("Main-Class" to "org.github.MainKt"))
  }
}

tasks.withType<Test> {
  jvmArgs = listOf("-ea", "-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory")
}
