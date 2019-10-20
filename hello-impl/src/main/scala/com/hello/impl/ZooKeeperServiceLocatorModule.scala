package com.hello.impl

import com.hello.api._
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import javax.inject.Singleton
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.x.discovery.{ServiceDiscoveryBuilder, ServiceInstance, UriSpec}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}


/**
  * This module binds the ServiceLocator interface from Lagom to the `ZooKeeperServiceLocator`.
  * The `ZooKeeperServiceLocator` is only bound if the application has been started in `Prod` mode.
  * In `Dev` mode the embedded service locator of Lagom is used.
  */
class ZooKeeperServiceLocatorModule extends Module {

  //  val localAddress = "127.0.0.1"
  val serviceAddress = "127.0.0.1"

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    println("+++++++++++++++++++++++++++++++Prod started+++++++++++++++++++++++++++++++")
    //
//    val config = ZooKeeperServiceLocator.fromConfiguration(configuration)
//    //    //
//    val registry = new ZooKeeperServiceRegistry(config.zkUri, config.zkServicesPath)
//    val zkClient = CuratorFrameworkFactory.newClient(config.zkUri, new ExponentialBackoffRetry(1000, 3))
//    //    zkClient.start
//
//    val serviceDiscovery = ServiceDiscoveryBuilder.builder(classOf[String]).client(zkClient).basePath(config.zkServicesPath).build
//
//    try {
//      registry.start()
//      serviceDiscovery.start()
//
//      registry.register(newServiceInstance("hello", "1", 9000))
//
//
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }

    Seq(bind[ZooKeeperServiceLocator.Config].toInstance(ZooKeeperServiceLocator
      .fromConfigurationWithPath(configuration)).in[Singleton]) ++
      Seq(bind[ServiceLocator].to[ZooKeeperServiceLocator]) ++
      Seq(bind[HelloService].to[HelloServiceImpl])
  }

  def newServiceInstance(serviceName: String, serviceId: String, servicePort: Int): ServiceInstance[String] = {
    ServiceInstance.builder[String]
      .name(serviceName)
      .id(serviceId)
      .address(serviceAddress)
      .port(servicePort)
      .uriSpec(new UriSpec("{scheme}://{serviceAddress}:{servicePort}"))
      .build
  }


}

