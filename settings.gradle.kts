pluginManagement {
  repositories {
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { url = uri("https://maven.aliyun.com/repository/spring-plugin") }
    gradlePluginPortal()
  }
}

rootProject.name = "gradle-vertx"
