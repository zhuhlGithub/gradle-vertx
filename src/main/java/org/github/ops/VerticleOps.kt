@file:Suppress("UNCHECKED_CAST")

package org.github.ops

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("ops")!!

val arh = Handler<AsyncResult<*>> { if(it.failed()) log.error(it.cause().message, it.cause()) }

val arhString = arh as Handler<AsyncResult<String>>
