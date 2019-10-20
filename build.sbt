import com.lightbend.lagom.core.LagomVersion

name := "lagom-hello"

version := "0.1"

scalaVersion := "2.12.8"

scalaVersion in ThisBuild := "2.12.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `hello` = (project in file("."))
  .aggregate(`hello-api`, `hello-impl`)


lazy val `hello-api` = (project in file("hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomJavadslApi
    )
  )

lazy val `hello-impl` = (project in file("hello-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      "com.lightbend.lagom" %% "lagom-scaladsl-akka-discovery-service-locator" % LagomVersion.current,
      // https://mvnrepository.com/artifact/org.apache.curator/curator-x-discovery
      "org.apache.curator" % "curator-x-discovery" % "2.12.0",
      guice,
      "com.google.guava" % "guava" % "19.0"
    )
  )
  .settings(lagomServiceHttpPort := 3000)
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-api`)

lagomKafkaEnabled in ThisBuild := false
lagomCassandraEnabled in ThisBuild := false

lagomServiceLocatorEnabled in ThisBuild := false

lagomServiceGatewayImpl in ThisBuild := "akka-http"

lagomServiceGatewayPort in ThisBuild := 8000

resolvers ++= Seq(
  "repo1" at "http://repo1.maven.org/maven2/"
)
