import pl.project13.scala.sbt.JmhPlugin

ThisBuild / scalaVersion := "3.3.7"
ThisBuild / scalacOptions ++= Seq("-release:21")
ThisBuild / javacOptions ++= Seq("--release", "21")

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "hotmetal",
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.3" % Test,
    // Tutorial.scala is narrative example code, not an executable MUnit suite.
    Test / unmanagedSources / excludeFilter := (Test / unmanagedSources / excludeFilter).value || "Tutorial.scala"
  )

lazy val bench = (project in file("benchmarks"))
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(
    name := "hotmetal-benchmarks",
    publish / skip := true,
    fork := true
  )
