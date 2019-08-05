def out = "%d %5level --- [%20.20thread] %40.40logger : %msg%n"
def dir = "logs"

appender("console", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = out
  }
}

appender("file", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = out
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${dir}/%d/netty.log"
  }
}

logger("org.github", TRACE)
logger("io.netty", TRACE)
logger("io.vertx", TRACE)
root(DEBUG, ["console", "file"])
