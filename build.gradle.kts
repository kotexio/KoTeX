plugins {
    base
    kotlin("jvm") version "1.3.61" apply false
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