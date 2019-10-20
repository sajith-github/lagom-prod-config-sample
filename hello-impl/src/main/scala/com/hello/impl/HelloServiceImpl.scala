package com.hello.impl

import com.hello.api._
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl() extends HelloService {

  override def hello(id: String) = ServiceCall { _ =>
    Future.successful(s"Hello $id")
  }

}
