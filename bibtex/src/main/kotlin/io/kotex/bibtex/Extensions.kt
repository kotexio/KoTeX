package io.kotex.bibtex

import div
import io.kotex.core.Document
import io.kotex.core.TextElement
import io.kotex.core.writer

val Document.bibTexEntries: MutableList<Entry>
    get() = mutableListOf()

val Document.bibTexFiles: MutableList<String>
    get() = mutableListOf()

fun Document.cite(entry: Entry): String {
    bibTexEntries.add(entry)
    return "\\cite{${entry.id}"
}

fun Document.cite(bibTexRef: String): String =
    "\\cite{$bibTexRef}"

fun Document.bibliography(files: List<String> = listOf()) {
    bibTexFiles.addAll(files)
    children.add(TextElement("\\bibliography{" + files.joinToString(",") + "}"))
}

fun Document.generateBibTex(filename: String) {
    writer.add { file ->
        val bibFile = file.parentFile / filename
        val builder = StringBuilder()
        bibTexEntries.forEach {
            builder.appendLine(it.toString())
        }
        bibFile.writeText(builder.toString())
    }
    bibTexFiles.add(filename)
}