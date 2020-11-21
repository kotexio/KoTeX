# KoTeX
KoTeX stands for Kotlin DSL for LaTeX. This started as a research project. It reflects the authors' interest in how 
to create beautiful scientific documents and presentations using strongly typed modern general-purpose programming languages. 

The main idea behind this work is called a [type-safe builder](https://kotlinlang.org/docs/reference/type-safe-builders.html), which is considered one of the most powerful Kotlin features. 

The project is in a very early stage of development, and there is only a tiny subset of LaTeX features are supported. 
Nevertheless, it can be used to create articles and Beamer presentations.

This is the smallest Beamer example: 

```kotlin
import io.kotex.beamer.beamer
import io.kotex.beamer.frame
import io.kotex.core.document
import io.kotex.core.makeTitle

document(beamer {
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
}.build()
```

