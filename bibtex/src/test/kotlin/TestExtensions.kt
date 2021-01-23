import io.kotex.bibtex.bibliography
import io.kotex.bibtex.cite
import io.kotex.bibtex.generateBibTex
import io.kotex.core.article
import io.kotex.core.document
import org.junit.Test
import io.kotex.bibtex.techReport
import io.kotex.core.write
import org.junit.Assert.*

class TestExtensions {
    private val testEntry = techReport(
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
            bibliography("plain")
        }
        val tempDir = createTempDir()
        println("$tempDir")
        val texFile = tempDir / "test.tex"
        doc.write(texFile)
        val bibFile = tempDir / "test.bib"

        assertTrue(bibFile.exists())
        assertTrue(bibFile.readText().contains("@techreport{testEntry"))
        assertTrue(texFile.readText().contains("\\bibliography{test}"))
    }
}