pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}


// Opt in with -PmediovynlibPath=../mediovynlib to test unpublished decoder changes.
providers.gradleProperty("mediovynlibPath").orNull?.let { mediovynlibPath ->
    includeBuild(mediovynlibPath) {
        dependencySubstitution {
            substitute(module("io.github.riteshbonthalakoti:mediovynlib-media3ext")).using(project(":media3ext"))
            substitute(module("io.github.riteshbonthalakoti:mediovynlib-mediainfo")).using(project(":mediainfo"))
        }
    }
}

rootProject.name = "Mediovyn"
include(":app")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:domain")
include(":core:media")
include(":core:model")
include(":core:ui")
include(":feature:network")
include(":feature:playlist")
include(":feature:player")
include(":feature:settings")
include(":feature:videopicker")
