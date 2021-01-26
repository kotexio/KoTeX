package io.kotex.beamer

import io.kotex.core.*
import java.lang.StringBuilder

enum class AspectRatio(private val value: String) {
    RATIO_169("169"),
    RATIO_1610("1610"),
    RATIO_149("149"),
    RATIO_54("54"),
    RATIO_43("43"),
    RATIO_32("32");

    override fun toString(): String = value
}

class Beamer: Preamble("beamer") {
    var theme: String = "default"
    var aspectRatio: AspectRatio? = null
        set(value) {
            opts.add("aspectratio=$value")
            field = value
        }

    override fun render(builder: StringBuilder) {
        super.render(builder)
        builder.appendLine("\\usetheme{$theme}")
    }
}

fun beamer(init: Beamer.() -> Unit): Beamer {
    val beamer = Beamer()
    beamer.init()
    return beamer
}

class Frame(title: String): Environment("frame", title) {
    fun footnote(text: String) = "\\footnote[frame]{$text}"

    fun String.footnote(note: String) = this + this@Frame.footnote(note)

    fun verbatim(text: String): Verbatim {
        fun oldVerbatim(tag: Tag, text: String) = tag.verbatim(text)
        fragile()
        return oldVerbatim(this, text)
    }

    fun verb(text: String): String {
        fragile()
        return getDocument().verb(text)
    }

    fun fragile() = addOption("fragile")

    fun standout() = addOption("standout")

    fun allowFrameBreaks() = addOption("allowframebreaks")

    fun Tag.url(ref: String): String {
        return "\\url{$ref}"
    }
}

fun Tag.frame(title: String, init: Frame.() -> Unit): Frame = initTag(Frame(title), init)

class Columns: Environment("columns")

fun Frame.columns(init: Columns.() -> Unit): Columns = initTag(Columns(), init)

class Column(width: Length): Environment("column", width.toString())

fun Columns.column(width: Length, init: Column.() -> Unit): Column = initTag(Column(width), init)