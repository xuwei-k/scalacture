import sbtcrossproject.CrossPlugin.autoImport.crossProject

val project = "scalacture"

val basicSettings = Seq(
  version := "0.1.1-SNAPSHOT",
  scalaVersion := "2.11.11",
  crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.2")
)

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform).in(file(s"$project-core")).settings(
  basicSettings,
  name := s"$project-core"
).platformsSettings(JVMPlatform, JSPlatform)(
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.3" % "test",
    "org.scalacheck" %%% "scalacheck" % "1.13.5" % "test"
  )
)

val coreJVM = core.jvm
val coreJS = core.js
val coreNative = core.native

lazy val scalaz = crossProject(JSPlatform, JVMPlatform, NativePlatform).in(file(s"$project-scalaz")).settings(
  basicSettings,
  scalapropsCoreSettings,
  scalapropsNativeSettings,
  name := s"$project-scalaz",
  scalapropsVersion := "0.4.4-SNAPSHOT",
  libraryDependencies ++= Seq(
    "org.scalaz" %%% "scalaz-core" % "7.2.13",
    "com.github.scalaprops" %%% "scalaprops" % scalapropsVersion.value % "test",
    "com.github.scalaprops" %%% "scalaprops-scalazlaws" % scalapropsVersion.value % "test"
  )
).dependsOn(
  core
)

val scalazJVM = scalaz.jvm
val scalazJS = scalaz.js
val scalazNative = scalaz.native

lazy val experimental = crossProject(JSPlatform, JVMPlatform, NativePlatform).in(file(s"$project-experimental")).settings(
  basicSettings,
  name := s"$project-experimental"
).dependsOn(
  core
)

val experimentalJVM = experimental.jvm
val experimentalJS = experimental.js
val experimentalNative = experimental.native

