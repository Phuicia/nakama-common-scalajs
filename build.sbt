
enablePlugins(ScalaJSPlugin)

name := "nakama-common-scalajs"
scalaVersion := "3.2.2"
version := "0.1.0-SNAPSHOT"
scalacOptions ++= Seq("-source:future")


//scalaJSUseMainModuleInitializer := true
scalaJSLinkerConfig ~= {
  import org.scalajs.linker.interface.*
  _.withSourceMap(false)
    .withOutputPatterns(
      _.withJSFile("index.js")
    )
    .withESFeatures(
      _.withESVersion(ESVersion.ES2015)
        .withAvoidClasses(false)
        .withAvoidLetsAndConsts(false)
    )
}
