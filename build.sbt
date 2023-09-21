ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.33"
libraryDependencies += "org.apache.poi" % "poi" % "3.17"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.17"


/*
* ""
"org.apache.poi" % "poi-ooxml" % "3.17"
* */
lazy val root = (project in file("."))
  .settings(
    name := "scala_mysql"
  )
