package io.kotex.bibtex

import java.lang.StringBuilder

sealed class Entry(val id: String) {
    abstract fun toBibTeX(): String
}

class EntryBuilder(type: String, id: String) {
    private val builder = StringBuilder()
            .append('@')
            .append(type)
            .append('{')
            .append(id)

    fun add(field: String, value: Any?): EntryBuilder {
        value?.let {
            builder.appendLine(',')
                    .append(field)
                    .append(" = \"")
                    .append(value)
                    .append('"')
        }
        return this
    }

    override fun toString(): String {
        return builder
                .appendLine()
                .appendLine("}")
                .toString()
    }
}

class Article(
        id: String,
        val author: String,
        val title: String,
        val journal: String,
        val year: Int,
        val volume: Int? = null,
        val number: Int? = null,
        val pages: String? = null,
        val month: String? = null,
        val note: String? = null,
        val doi: String? = null
) : Entry(id) {
    override fun toBibTeX(): String {
        return EntryBuilder("article", id)
                .add("author", author)
                .add("title", title)
                .add("journal", journal)
                .add("year", year)
                .add("volume", volume)
                .add("number", number)
                .add("pages", pages)
                .add("month", month)
                .add("note", note)
                .add("doi", doi)
                .toString()
    }
}

class Book(id: String,
           val author: String? = null,
           val editor: String? = null,
           val title: String,
           val publisher: String,
           val year: Int,
           val volume: Int? = null,
           val number: Int? = null,
           val series: String? = null,
           val address: String? = null,
           val edition: String? = null,
           val month: String? = null,
           val note: String? = null,
           val doi: String? = null
): Entry(id) {
    override fun toBibTeX(): String {
        return EntryBuilder("book", id)
                .add("author",author)
                .add("editor", editor)
                .add("title", title)
                .add("publisher", publisher)
                .add("year", year)
                .add("volume", volume)
                .add("number", number)
                .add("series", series)
                .add("address", address)
                .add("edition", edition)
                .add("month", month)
                .add("note", note)
                .add("doi", doi)
                .toString()
    }
}





