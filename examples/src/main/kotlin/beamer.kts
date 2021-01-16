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