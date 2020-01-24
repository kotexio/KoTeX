plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    compile(kotlin("stdlib"))
    compile(project(":core"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.kotex"
            artifactId = "kotex-beamer"
            version = "0.1-SNAPSHOT"

            from(components["kotlin"])
        }
    }
}