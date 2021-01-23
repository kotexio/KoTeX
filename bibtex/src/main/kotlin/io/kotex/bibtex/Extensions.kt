package io.kotex.bibtex

import backingField
import div
import io.kotex.core.*

private val bibTexEntriesMap = mutableMapOf<Document, MutableList<Entry>>()

private val bibTexFileMap = mutableMapOf<Document, MutableList<String>>()

val Document.bibTexEntries: MutableList<Entry>
    get() = this.backingField(bibTexEntriesMap) { mutableListOf() }

val Document.bibTexFiles: MutableList<String>
    get() = this.backingField(bibTexFileMap) { mutableListOf() }

fun Document.cite(entry: Entry): String {
    bibTexEntries.add(entry)
    return "\\cite{${entry.id}}"
}

fun Document.cite(bibTexRef: String): String =
    "\\cite{$bibTexRef}"

fun Tag.bibliography(style: String, files: List<String> = listOf()) {
    val doc = this.getDocument()
    doc.bibTexFiles.addAll(files)
    children.add(TextElement("\\bibliographystyle{$style}"))
    children.add(TextElement("\\bibliography{" + doc.bibTexFiles.joinToString(",") + "}"))
}

fun Document.generateBibTex(name: String) {
    writer.add { file ->
        val bibFile = file.parentFile / "$name.bib"
        val builder = StringBuilder()
        bibTexEntries.forEach {
            builder.appendLine(it.toString())
        }
        if (!bibFile.parentFile.exists()) bibFile.parentFile.mkdirs()
        bibFile.writeText(builder.toString())
    }
    bibTexFiles.add(name)
}