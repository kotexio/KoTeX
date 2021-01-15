package io.kotex.bibtex

import io.kotex.core.Document
import io.kotex.core.TextElement
import java.io.File

sealed class Bibliography {
    abstract fun getPath(): File
}

class BibTeXFile(private val path: File): Bibliography() {
    override fun getPath(): File = path
}

class GeneratedBibliography : Bibliography() {
    private val tempFile = createTempFile(suffix = ".bib")

    override fun getPath(): File = tempFile
}

val Document.bibTexEntries: MutableList<Entry>
    get() = mutableListOf()

val Document.bibTexFiles: MutableList<Bibliography>
    get() = mutableListOf()

fun Document.cite(entry: Entry): String {
    bibTexEntries.add(entry)
    return "\\cite{${entry.id}"
}

fun Document.cite(bibTexRef: String): String =
    "\\cite{$bibTexRef}"

fun Document.bibliography(files: List<String> = listOf()) {
    bibTexFiles.addAll(files.map { BibTeXFile(File(it)) })

    if (bibTexEntries.isEmpty()) bibTexFiles.add(GeneratedBibliography())

    children.add(TextElement("\\bibliography{" + files.joinToString(",") + "}"))
}