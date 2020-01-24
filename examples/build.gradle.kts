plugins {
    kotlin("jvm")
}

dependencies {
    compile(kotlin("stdlib"))
    compile(project(":core"))
    compile(project(":beamer"))
    implementation(kotlin("script-runtime"))
}