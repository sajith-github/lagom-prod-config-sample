package com.hello.impl

import akka.actor.CoordinatedShutdown
import com.lightbend.lagom.internal.scaladsl.registry.ServiceRegistration
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeServiceLocatorComponents

trait CustomLocatorComponets extends LagomDevModeServiceLocatorComponents {
  def coordinatedShutdown: CoordinatedShutdown

  new ServiceRegistration(serviceInfo, coordinatedShutdown, config, serviceRegistry)(executionContext)
}

