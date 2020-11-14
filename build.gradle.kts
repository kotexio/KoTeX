plugins {
    base
    kotlin("jvm") version "1.4.0" apply false
}

allprojects {
    group = "io.kotex"
    version = "0.1-SNAPSHOT"

    repositories {
        jcenter()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}