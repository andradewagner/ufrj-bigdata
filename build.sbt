name := "WordcountApp"

version := "1.0"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.4.3",
  "org.apache.spark" %% "spark-sql" % "2.4.3",
  "org.elasticsearch" %% "elasticsearch-spark-20" % "6.3.2"
  )