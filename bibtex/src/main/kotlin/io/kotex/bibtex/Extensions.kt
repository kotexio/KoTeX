package io.kotex.bibtex

import io.kotex.core.Document

val Document.bibEntries: MutableList<Entry>
    get() = mutableListOf()

fun Document.cite(entry: Entry): String {
    bibEntries.add(entry)
    return "\\cite{${entry.id}"
}

fun Document.cite(bibTexRef: String): String =
    "\\cite{$bibTexRef}"