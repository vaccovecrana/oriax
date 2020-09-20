buildscript {
  repositories { jcenter(); gradlePluginPortal(); maven { name = "VaccoOss"; setUrl("https://dl.bintray.com/vaccovecrana/vacco-oss") } }
  dependencies { classpath("io.vacco:common-build-gradle-plugin:0.5.0") }
}
apply{plugin(io.vacco.common.CbPlugin::class.java)}

group = "io.vacco.oriax"
version = "0.1.0"

configure<io.vacco.common.CbPluginProfileExtension> {
  addJ8Spec(); addPmd(); addSpotBugs()
  setPublishingUrlTransform { repo -> "${repo.url}/${project.name}" }
  sharedLibrary()
}

configure<JavaPluginExtension> {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}
