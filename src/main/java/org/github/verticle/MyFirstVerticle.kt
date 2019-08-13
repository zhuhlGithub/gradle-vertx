package org.github.verticle

import io.netty.handler.codec.http.HttpHeaderNames.*
import io.netty.handler.codec.http.HttpHeaderValues.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.handler.StaticHandler
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit.*

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
    StaticHandler.create()
    httpServer = vertx.createHttpServer(httpServerOptions)
      .requestHandler(this::handle)
      .listen(port) {
        if(it.succeeded()) {
          log.info("HTTP服务器启动成功，监听端口：$port")
        } else {
          log.warn("HTTP服务器启动失败，监听端口：$port", it.cause())
        }
      }
  }

  private fun handle(req: HttpServerRequest) {
    log.info("${req.method()} ${req.path()}")
    //    Promise
    //      .promise<Unit>()
    //      .future()
    //      .apply {
    //        vertx.executeBlocking(Handler<Promise<Unit>> {
    //          try {
    //            SECONDS.sleep(5)
    //            it.complete()
    //          } catch(e: Exception) {
    //            it.fail(e)
    //          }
    //        }, this)
    //      }
    //      .setHandler {
    //        if(it.succeeded()) {
    //          req.response().apply {
    //            putHeader(CONTENT_TYPE, APPLICATION_JSON)
    //            end("{}")
    //          }
    //        } else {
    //          val e = it.cause()!!
    //          log.error(e.message, e)
    //        }
    //      }
    vertx.setTimer(SECONDS.toMillis(5)) {
      req.response().apply {
        putHeader(CONTENT_TYPE, APPLICATION_JSON)
        end("{}")
      }
    }
  }

  companion object {
    /** log. */
    private val log = LoggerFactory.getLogger(MyFirstVerticle::class.java)!!
  }
}
