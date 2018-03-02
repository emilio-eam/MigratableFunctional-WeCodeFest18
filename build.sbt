val ModuleName = "MigratableFunctionalCodeDemo"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.jinq" % "jinq-jpa-scala" % "1.8.21",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.11.Final",
  "org.apache.spark" %% "spark-sql" % "1.6.0",
  "org.scalatest" %% "scalatest" % "2.2.6",
  "com.h2database" % "h2" % "1.4.193" % "test",
  "org.liquibase" % "liquibase-core" % "3.5.5" % "test"
)

dependencyOverrides ++= Seq(
  "commons-net" % "commons-net" % "3.1",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "com.google.guava" % "guava"  % "14.0.1"
)