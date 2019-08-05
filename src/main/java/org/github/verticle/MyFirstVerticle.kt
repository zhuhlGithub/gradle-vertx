package org.github.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerOptions
import org.slf4j.LoggerFactory

class MyFirstVerticle: AbstractVerticle() {
  override fun start() {
    val httpServerOptions = HttpServerOptions().apply { logActivity = true }
    val port = 10000
    vertx
      .createHttpServer(httpServerOptions)
      .requestHandler {
        it.response().apply {
          putHeader("content-type", "text/plain")
          end("Hello world!")
        }
      }
      .listen(port) {
        if(it.succeeded()) {
          log.info("HTTP服务器启动成功，监听端口：$port")
        } else {
          log.warn("HTTP服务器启动失败，监听端口：$port", it.cause())
        }
      }
  }

  companion object {
    /** log. */
    private val log = LoggerFactory.getLogger(MyFirstVerticle::class.java)!!
  }
}
