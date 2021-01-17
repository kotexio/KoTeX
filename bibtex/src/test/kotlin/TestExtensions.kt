import io.kotex.bibtex.bibliography
import io.kotex.bibtex.cite
import io.kotex.bibtex.generateBibTex
import io.kotex.core.article
import io.kotex.core.document
import org.junit.Test
import io.kotex.bibtex.techReport
import io.kotex.core.write

class TestExtensions {
    val testEntry = techReport(
        "testEntry",
        author = "Test Author",
        title = "Test Title",
        institution = "MyUniv",
        year = 2020
    )

    @Test
    fun testGenerateBibTex() {
        val doc = document(article {
            title = "A minimal example"
            author = "Vitaly" / "Vitaly's Home"
            date = today()
        }) {
            +"Hello all${cite(testEntry)}"
            generateBibTex("test")
            bibliography()
        }
        val tempDir = createTempDir()
        println("$tempDir")
        doc.write(tempDir / "test.tex")
    }
}