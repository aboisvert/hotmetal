import pl.project13.scala.sbt.JmhPlugin

ThisBuild / scalaVersion := "3.3.7"
ThisBuild / scalacOptions ++= Seq("-release:21")
ThisBuild / javacOptions ++= Seq("--release", "21")

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT"
)

lazy val testSettings = Seq(
  libraryDependencies += "org.scalameta" %% "munit" % "1.2.3" % Test
)

lazy val core = (project in file("core"))
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    name := "hotmetal",
    // Tutorial.scala is narrative example code, not an executable MUnit suite.
    Test / unmanagedSources / excludeFilter := (Test / unmanagedSources / excludeFilter).value || "Tutorial.scala"
  )

lazy val samples = (project in file("samples"))
  .dependsOn(core)
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    name := "hotmetal-samples",
    publish / skip := true
  )

lazy val bench = (project in file("benchmarks"))
  .dependsOn(core)
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(
    name := "hotmetal-benchmarks",
    publish / skip := true,
    fork := true
  )

lazy val root = (project in file("."))
  .aggregate(core, samples, bench)