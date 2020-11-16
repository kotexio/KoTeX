package io.kotex.bibtex

import java.lang.StringBuilder

class Entry(val id: String, private val body: String) {
    override fun toString(): String = body
}

class EntryBuilder(type: String, val id: String) {
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

    fun build(): Entry {
        return Entry(id, builder
                .appendLine()
                .appendLine("}")
                .toString())
    }
}

fun article(
        id: String,
        author: String,
        title: String,
        journal: String,
        year: Int,
        volume: Int? = null,
        number: Int? = null,
        pages: String? = null,
        month: String? = null,
        note: String? = null,
        doi: String? = null
): Entry = EntryBuilder("article", id)
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
        .build()

fun book(id: String,
         author: String? = null,
         editor: String? = null,
         title: String,
         publisher: String,
         year: Int,
         volume: Int? = null,
         number: Int? = null,
         series: String? = null,
         address: String? = null,
         edition: String? = null,
         month: String? = null,
         note: String? = null,
         doi: String? = null
): Entry = EntryBuilder("book", id)
        .add("author", author)
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
        .build()

fun booklet(id: String,
            title: String,
            author: String?,
            howPublished: String?,
            address: String?,
            year: Int?,
            month: String?,
            note: String?
): Entry = EntryBuilder("booklet", id)
        .add("title", title)
        .add("author", author)
        .add("howpublished", howPublished)
        .add("address", address)
        .add("month", month)
        .add("year", year)
        .add("note", note)
        .build()

fun inBook(id: String,
           author: String? = null,
           editor: String? = null,
           title: String,
           chapter: String? = null,
           pages: String? = null,
           publisher: String,
           year: Int,
           volume: Int? = null,
           number: Int? = null,
           series: String? = null,
           type: String? = null,
           address: String? = null,
           edition: String? = null,
           month: Int? = null,
           note: String? = null
): Entry = EntryBuilder("inbook", id)
        .add("author", author)
        .add("editor", editor)
        .add("title", title)
        .add("chapter", chapter)
        .add("pages", pages)
        .add("publisher", publisher)
        .add("year", year)
        .add("volume", volume)
        .add("number", number)
        .add("series", series)
        .add("type", type)
        .add("address", address)
        .add("edition", edition)
        .add("month", month)
        .add("note", note)
        .build()

fun inCollection(id: String,
                 author: String,
                 title: String,
                 bookTitle: String,
                 publisher: String,
                 year: Int,
                 editor: String? = null,
                 volume: Int? = null,
                 number: Int? = null,
                 series: String? = null,
                 type: String? = null,
                 chapter: Int? = null,
                 pages: String? = null,
                 address: String? = null,
                 edition: String? = null,
                 month: String? = null,
                 note: String? = null
): Entry = EntryBuilder("incollection", id)
        .add("author", author)
        .add("title", title)
        .add("booktitle", bookTitle)
        .add("publisher", publisher)
        .add("year", year)
        .add("editor", editor)
        .add("volume", volume)
        .add("number", number)
        .add("series", series)
        .add("type", type)
        .add("chapter", chapter)
        .add("pages", pages)
        .add("address", address)
        .add("edition", edition)
        .add("month", month)
        .add("note", note)
        .build()

fun inProceedings(id: String,
                  author: String,
                  title: String,
                  bookTitle: String,
                  year: Int,
                  editor: String? = null,
                  volume: Int? = null,
                  number: Int? = null,
                  series: String? = null,
                  pages: String? = null,
                  address: String? = null,
                  month: Int? = null,
                  organization: String? = null,
                  publisher: String? = null,
                  note: String? = null
): Entry = EntryBuilder("inproceedings", id)
        .add("author", author)
        .add("title", title)
        .add("booktitle", bookTitle)
        .add("year", year)
        .add("editor", editor)
        .add("volume", volume)
        .add("number", number)
        .add("series", series)
        .add("pages", pages)
        .add("address", address)
        .add("month", month)
        .add("organization", organization)
        .add("publisher", publisher)
        .add("note", note)
        .build()

fun manual(id: String,
           title: String,
           author: String? = null,
           organization: String? = null,
           edition: String? = null,
           month: String? = null,
           year: Int? = null,
           note: String? = null
): Entry = EntryBuilder("manual", id)
        .add("title", title)
        .add("author", author)
        .add("organization", organization)
        .add("edition", edition)
        .add("month", month)
        .add("year", year)
        .add("note", note)
        .build()

fun mastersThesis(id: String,
                  author: String,
                  title: String,
                  school: String,
                  year: Int,
                  type: String?,
                  address: String?,
                  month: String?,
                  note: String?
): Entry = EntryBuilder("mastersthesis", id)
        .add("author", author)
        .add("title", title)
        .add("school", school)
        .add("year", year)
        .add("type", type)
        .add("address", address)
        .add("month", month)
        .add("note", note)
        .build()

fun misc(id: String,
         author: String? = null,
         title: String? = null,
         howPublished: String? = null,
         month: String? = null,
         year: Int? = null,
         note: String? = null
): Entry = EntryBuilder("misc", id)
        .add("author", author)
        .add("title", title)
        .add("howpublished", howPublished)
        .add("month", month)
        .add("year", year)
        .add("note", note)
        .build()

fun phdThesis(id: String,
              author: String,
              title: String,
              school: String,
              year: Int,
              type: String? = null,
              address: String? = null,
              month: String? = null,
              note: String? = null
): Entry = EntryBuilder("phdthesis", id)
        .add("author", author)
        .add("title", title)
        .add("school", school)
        .add("year", year)
        .add("type", type)
        .add("address", address)
        .add("month", month)
        .add("note", note)
        .build()

fun proceedings(id: String,
                title: String,
                year: Int,
                editor: String? = null,
                volume: Int? = null,
                number: Int? = null,
                series: String? = null,
                address: String? = null,
                publisher: String? = null,
                month: Int? = null,
                organization: String? = null,
                note: String? = null
): Entry = EntryBuilder("proceedings", id)
        .add("title", title)
        .add("year", year)
        .add("editor", editor)
        .add("volume", volume)
        .add("number", number)
        .add("series", series)
        .add("address", address)
        .add("publisher", publisher)
        .add("month", month)
        .add("organization", organization)
        .add("note", note)
        .build()

fun techReport(id: String,
               author: String,
               title: String,
               institution: String,
               year: Int,
               type: String? = null,
               number: Int? = null,
               address: String? = null,
               month: String? = null,
               note: String? = null
): Entry = EntryBuilder("techreport", id)
        .add("author", author)
        .add("title", title)
        .add("institution", institution)
        .add("year", year)
        .add("type", type)
        .add("number", number)
        .add("address", address)
        .add("month", month)
        .add("note", note)
        .build()

fun unpublished(id: String,
                author: String,
                title: String,
                note: String,
                year: Int? = null,
                month: String? = null
): Entry = EntryBuilder("unpublished", id)
        .add("author", author)
        .add("title", title)
        .add("note", note)
        .add("year", year)
        .add("month", month)
        .build()
