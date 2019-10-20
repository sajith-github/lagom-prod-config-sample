package com.hello.impl

import com.hello.api.HelloService
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import org.apache.curator.x.discovery.{ServiceInstance, UriSpec}
import play.api.libs.ws.ahc.AhcWSComponents

class HelloLoader extends LagomApplicationLoader {

  val serviceAddress = "127.0.0.1"
  val serverhost = "127.0.0.1"
  val serverport = 2181
  val serverscheme = "http"
  val serverroute = "round-robin"
  val defaultConfigPath = "lagom.discovery.zookeeper"
  val defaultZKServicesPath = "/lagom/services"

  def testConfig(serverHostname: String = serviceAddress,
                 serverPort: Int = serverport,
                 scheme: String = "http",
                 routingPolicy: String = "round-robin",
                 zkServicesPath: String = defaultZKServicesPath): ZooKeeperServiceLocator.Config =
    ZooKeeperServiceLocator.Config(serverHostname = serverHostname,
      serverPort = serverPort,
      scheme = scheme,
      routingPolicy = routingPolicy,
      zkServicesPath = zkServicesPath)


  def newServiceInstance(serviceName: String, serviceId: String, servicePort: Int): ServiceInstance[String] = {
    ServiceInstance.builder[String]
      .name(serviceName)
      .id(serviceId)
      .address(serviceAddress)
      .port(servicePort)
      .uriSpec(new UriSpec("{scheme}://{serviceAddress}:{servicePort}"))
      .build
  }

  override def load(context: LagomApplicationContext): LagomApplication = {
    val application: HelloApplication = new HelloApplication(context) {
      val locator = new ZooKeeperServiceLocator(testConfig())
      val registry = new ZooKeeperServiceRegistry(s"$serverhost:$serverport", defaultZKServicesPath)
      registry.start()
      registry.register(newServiceInstance("hello2", "1", 3000))
      override def serviceLocator: ServiceLocator = locator
    }
    application
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
    val application: HelloApplication = new HelloApplication(context) {
      val locator = new ZooKeeperServiceLocator(testConfig())
      val registry = new ZooKeeperServiceRegistry(s"$serverhost:$serverport", defaultZKServicesPath)
      registry.start()
      registry.register(newServiceInstance("hello2", "1", 3000))
      override def serviceLocator: ServiceLocator = locator
    }
    application
  }

  override def describeService = Some(readDescriptor[HelloService])
}

abstract class HelloApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[HelloService](wire[HelloServiceImpl])

}
