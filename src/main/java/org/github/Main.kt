package org.github

import io.vertx.core.Vertx
import org.github.verticle.MyFirstVerticle

fun main() {
  val vertx = Vertx.vertx()!!
  vertx.deployVerticle(MyFirstVerticle())
}
