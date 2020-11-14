plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib"))
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