plugins { id("io.vacco.oss.gitflow") version "1.8.3" }

group = "io.vacco.oriax"
version = "0.5.0"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
  addClasspathHell()
  sharedLibrary(true, false)
}
