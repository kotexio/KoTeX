plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    compile(kotlin("stdlib"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.kotex"
            artifactId = "kotex-core"
            version = "0.1-SNAPSHOT"

            from(components["kotlin"])
        }
    }
}