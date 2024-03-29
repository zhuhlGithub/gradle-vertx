package org.github

import io.vertx.core.DeploymentOptions
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.web.common.WebEnvironment.*
import org.github.ops.arhString
import org.github.verticle.MyFirstVerticle
import java.lang.System.*
import java.util.concurrent.TimeUnit.*
import java.util.function.Supplier

fun main() {
  setProperty(SYSTEM_PROPERTY_NAME, "dev")

  val vertxOptions = VertxOptions().apply {
    maxEventLoopExecuteTime = 1
    maxEventLoopExecuteTimeUnit = MILLISECONDS
  }
  val vertx = Vertx.vertx(vertxOptions)!!
  val deploymentOptions = DeploymentOptions().apply {
    instances = vertxOptions.eventLoopPoolSize
  }

  vertx.deployVerticle(Supplier<Verticle> { MyFirstVerticle(10000) }, deploymentOptions, arhString)
}
