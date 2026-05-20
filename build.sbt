import pl.project13.scala.sbt.JmhPlugin

ThisBuild / scalaVersion := "3.3.7"
ThisBuild / scalacOptions ++= Seq("-release:21")
ThisBuild / javacOptions ++= Seq("--release", "21")

ThisBuild / organization := "com.github.aboisvert"
ThisBuild / homepage := Some(url("https://github.com/aboisvert/hotmetal"))
ThisBuild / licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/aboisvert/hotmetal"),
    "scm:git:git@github.com:aboisvert/hotmetal.git"
  )
)
ThisBuild / developers := List(
  Developer("aboisvert", "Alain Boisvert", "", url("https://github.com/aboisvert"))
)
ThisBuild / versionScheme := Some("early-semver")

val githubPackagesHost = "maven.pkg.github.com"
val githubPackagesUrl = s"https://$githubPackagesHost/aboisvert/hotmetal"

def githubPackagesCredentials(token: String): Credentials =
  Credentials(
    "GitHub Package Registry",
    githubPackagesHost,
    sys.env.getOrElse("GITHUB_ACTOR", sys.env.getOrElse("GITHUB_USERNAME", "github")),
    token
  )

ThisBuild / publishTo := Some("GitHub Packages" at githubPackagesUrl)
ThisBuild / publishMavenStyle := true
ThisBuild / credentials ++= sys.env.get("GITHUB_TOKEN").toList.map(githubPackagesCredentials)

lazy val commonSettings = Seq()

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
  .dependsOn(core % "compile->compile;test->test")
  .settings(commonSettings)
  .settings(testSettings)
  .settings(
    name := "hotmetal-samples",
    publish / skip := true
  )

lazy val bench = (project in file("benchmarks"))
  .dependsOn(core)
  .dependsOn(samples)
  .enablePlugins(JmhPlugin)
  .settings(commonSettings)
  .settings(
    name := "hotmetal-benchmarks",
    publish / skip := true,
    fork := true
  )

lazy val root = (project in file("."))
  .aggregate(core, samples, bench)
  .settings(
    publish / skip := true
  )
