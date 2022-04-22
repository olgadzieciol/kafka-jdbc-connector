import sbt._
import sbt.Keys._

lazy val `kafka-jdbc-connector` =
  (project in file("."))
    .settings(
      name := "kafka-jdbc-connector",
      version := "1.2.0",
      organization := "com.agoda",
      scalaVersion := "2.12.2",
      crossScalaVersions := Seq("2.11.7", "2.12.2"),
      libraryDependencies ++= Dependencies.Compile.kafkaJdbcConnector ++ Dependencies.Test.kafkaJdbcConnector,
      fork in Test := true
    )
    .enablePlugins(BuildInfoPlugin)
    .settings(
      buildInfoKeys := Seq[BuildInfoKey](version),
      buildInfoPackage := organization.value
    )
    .settings(
      test in assembly := {},
      assemblyJarName in assembly := s"kafka-jdbc-connector-${version.value}.jar"
    )
    .settings(
      useGpg := true,
      pgpPublicRing := file("~/.sbt/gpg/pubring.asc"),
      pgpSecretRing := file("~/.sbt/gpg/secring.asc"),
      publishTo := Some(
        if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
        else Opts.resolver.sonatypeStaging
      ),
      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
      publishMavenStyle := true,
      licenses := Seq("Apache 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
      homepage := Some(url("https://github.com/agoda-com/kafka-jdbc-connector")),
      scmInfo := Some(
        ScmInfo(
          url("https://github.com/agoda-com/kafka-jdbc-connector"),
          "scm:git@github.com:agoda-com/kafka-jdbc-connector.git"
        )
      ),
      developers := List(
        Developer(
          id="arpanchaudhury",
          name="Arpan Chaudhury",
          email="arpan.chaudhury@agoda.com",
          url=url("https://github.com/arpanchaudhury")
        )
      )
    )
    .settings(
      coverageExcludedPackages := Seq(
        "com.agoda.BuildInfo",
        "com.agoda.kafka.connector.jdbc.JdbcSourceConnector",
        "com.agoda.kafka.connector.jdbc.JdbcSourceTask",
        "com.agoda.kafka.connector.jdbc.utils.Version"
      ).mkString(";")
    )
