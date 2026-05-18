ThisBuild / scalaVersion := "3.3.7"
ThisBuild / scalacOptions ++= Seq("-release:21")
ThisBuild / javacOptions ++= Seq("--release", "21")

lazy val root = (project in file("."))
  .settings(
    name := "hotmetal",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.3" % Test,
    // Tutorial.scala is narrative example code, not an executable MUnit suite.
    Test / unmanagedSources / excludeFilter := (Test / unmanagedSources / excludeFilter).value || "Tutorial.scala"
  )
