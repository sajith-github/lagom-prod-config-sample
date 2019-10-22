package com.hello.impl

import com.hello.api._
import com.lightbend.lagom.scaladsl.api.ServiceCall
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends HelloService {

  override def hello(id: String) = ServiceCall { _ =>
    val result = externalService.countryList.invoke()

    result.map(response =>
      response.RestResponse.result.map {
        country =>
          Json.obj(
            "name" -> country.name,
            "code" -> country.alpha3_code
          )
      }
    )

    Future.successful(s"Hello $id")
  }

}
