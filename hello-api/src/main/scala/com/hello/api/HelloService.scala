package com.hello.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

object HelloService {
  val TOPIC_NAME = "greetings"
}

trait HelloService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("hello")
      .withCalls(
        pathCall("/api/hello/:id", hello _)
      )
      .withAutoAcl(true)
    // @formatter:on

  }
}
