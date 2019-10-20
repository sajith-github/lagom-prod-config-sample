package com.hello.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object HelloService {
  val TOPIC_NAME = "greetings"
}

trait HelloService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("hello2")
      .withCalls(
        pathCall("/api/hello/:id", hello _)
      )
      .withAutoAcl(true)
    // @formatter:on

  }
}

case class GreetingMessage(message: String)

object GreetingMessage {

  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}

case class GreetingMessageChanged(name: String, message: String)

object GreetingMessageChanged {

  implicit val format: Format[GreetingMessageChanged] = Json.format[GreetingMessageChanged]
}
