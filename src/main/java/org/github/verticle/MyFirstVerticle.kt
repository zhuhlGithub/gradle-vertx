package org.github.verticle

import io.netty.handler.codec.http.HttpHeaderNames.*
import io.netty.handler.codec.http.HttpHeaderValues.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import org.slf4j.LoggerFactory

class MyFirstVerticle(private val port: Int): AbstractVerticle() {
  /** HTTP服务器. */
  private lateinit var httpServer: HttpServer

  override fun start() {
    val httpServerOptions = HttpServerOptions().apply {
      logActivity = true
      isCompressionSupported = true
      isHandle100ContinueAutomatically = true
      isDecompressionSupported = true
    }
    httpServer = vertx.createHttpServer(httpServerOptions)
      .requestHandler {
        log.info("${it.method()} ${it.path()}")
        it.response().apply {
          putHeader(CONTENT_TYPE, APPLICATION_JSON)
          end("{}")
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
