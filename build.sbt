name := "akka"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq (
  "com.typesafe.akka" %% "akka-http"   % "10.1.1",
  "com.typesafe.akka" %% "akka-stream" % "2.5.11"
)