package org.github.verticle

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit.*

@RunWith(VertxUnitRunner::class)
class MyFirstVerticleTest {
  private lateinit var vertx: Vertx

  private val port = 10000

  @Before
  fun setUp(ctx: TestContext) {
    val vertxOptions = VertxOptions().apply {
      maxEventLoopExecuteTime = 1
      maxEventLoopExecuteTimeUnit = MILLISECONDS
    }
    vertx = Vertx.vertx(vertxOptions)!!
    vertx.deployVerticle(MyFirstVerticle(port), ctx.asyncAssertSuccess())
  }

  @After
  fun tearDown(ctx: TestContext) {
    vertx.close(ctx.asyncAssertSuccess())
  }

  @Test
  fun testVerticle(ctx: TestContext) {
    val async = ctx.async()!!

    val webClientOptions = WebClientOptions().apply { logActivity = true }
    val webClient = WebClient.create(vertx, webClientOptions)!!
    webClient
      .get(port, "localhost", "/")
      .send {
        ctx.assertTrue(it.succeeded())
        async.complete()
      }
  }
}
