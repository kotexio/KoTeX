# KoTeX
KoTeX stands for Kotlin DSL for LaTeX. This started as a research project. It reflects the authors' interest in how 
to create beautiful scientific documents and presentations using a strongly typed modern general-purpose programming language. 

The main idea behind this work is called a [type-safe builder](https://kotlinlang.org/docs/reference/type-safe-builders.html), which is considered one of the most powerful Kotlin features. 

The project is in a very early stage of development, and there is only a tiny subset of LaTeX features is supported. 
Nevertheless, it can be used to create articles and Beamer presentations.

This is the smallest Beamer example: 

```kotlin
import io.kotex.beamer.beamer
import io.kotex.beamer.frame
import io.kotex.core.document
import io.kotex.core.makeTitle

val doc = document(beamer {
    title = "A minimal example"
    author = "John Doe" / "Centre for Modern Beamer Themes"
    theme = "metropolis"
    date = today()
}) {
    makeTitle()
    section("First Section") {
        frame("First Frame") {
            +"Hello world!"
        }
    }
}

println(doc.toTex())
```

## Usage

Declare the needed dependencies in the `build.gradle(.kts)` file:

```kotlin
dependencies {
    implementation("io.kotex:kotex-core:$kotexVersion")    // main classes
    implementation("io.kotex:kotex-bibtex:$kotexVersion")  // bibtex support
    implementation("io.kotex:kotex-beamer:$kotexVersion")  // beamer support
}
```

KoTeX isn't published anywhere yet, so before usage, you need to clone the KoTeX repo and do `:publishToMavenLocal`
task.
