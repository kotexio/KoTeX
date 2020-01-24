package io.kotex.core

import java.lang.StringBuilder

abstract class Element {
    var parent: Element? = null
        internal set

    abstract fun render(builder: StringBuilder, indent: String)
}

abstract class Tag(val name: String) : Element() {

    val children = arrayListOf<Element>()
    fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        tag.parent = this
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        children.forEach {
            it.render(builder, "$indent  ")
        }
        after()
    }

    open fun after() {}
}

class TextElement(val text: String) : Element() {
    override fun render(builder: StringBuilder, indent: String) {
        builder.appendln("$indent$text")
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    open operator fun String.unaryPlus() {
        children.add(TextElement(this.trimIndent()))
    }

    fun itemize(init: Itemize.() -> Unit) = initTag(Itemize(), init)
    fun enumerate(init: Enumerate.() -> Unit) = initTag(Enumerate(), init)
}

abstract class SingleTitledTag(name: String, val title: String, val opts: List<String> = listOf()) : TagWithText(name) {
    override fun render(builder: StringBuilder, indent: String) {
        val options = opts.joinToString(separator = "") { o -> "[$o]" }
        builder.append("$indent\\$name$options{$title}\n")
        super.render(builder, indent)
    }
}

abstract class Environment(name: String, val params: MutableList<String> = mutableListOf(), val opts: MutableList<String> = mutableListOf()) : TagWithText(name) {
    constructor(name: String, param: String) : this(name, mutableListOf(param))

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent\\begin{$name}")
        opts.forEach { builder.append("[$it]") }
        params.forEach { builder.append("{$it}") }
        builder.appendln()
        renderContent(builder, indent)
        builder.appendln("$indent\\end{$name}")
    }

    protected open fun renderContent(builder: StringBuilder, indent: String) {
        super.render(builder, indent)
    }
}

class Document(val preamble: Preamble) : Environment("document") {
//    fun abstract(init: Abstract.() -> Unit): Abstract = initTag(Abstract(), init)

    fun section(title: String, init: Section.() -> Unit): Section = initTag(Section(title), init)

    fun toTex(): String {
        val builder = StringBuilder()
        render(builder, "")
        return preamble.toString() + builder.toString()
    }

    fun build() {
        println(toTex())
    }
}

open class AbstractListTag(name: String) : Environment(name) {
    operator fun String.unaryMinus() {
        children.add(TextElement("\\item ${this.trimIndent()}"))
    }
}

class Itemize : AbstractListTag("itemize")
class Enumerate : AbstractListTag("enumerate")

class Abstract : Environment("abstract")
class Section(title: String) : SingleTitledTag("section", title) {
    fun subsection(title: String, init: SubSection.() -> Unit): SubSection = initTag(SubSection(title), init)
}

class SubSection(title: String) : SingleTitledTag("subsection", title)

data class Author(val name: String, val affiliation: String? = null)

abstract class Preamble(private val documentClass: String, val opts: MutableList<String> = mutableListOf()) {
    var title: String? = null
    var authors: List<Author> = listOf()
    var author: Author?
        set(value) {
            if (value != null)
                authors = listOf(value)
        }
        get() {
            return if (authors.size == 1) authors[0] else null
        }

    var date: String? = null
    private val packages: MutableList<String> = mutableListOf()

    operator fun String.unaryPlus() = Author(this)

    operator fun String.div(other: String) = Author(this, other)

    fun today() = "\\today"

    open fun render(builder: StringBuilder) {
        builder.append("\\documentclass")
        opts.forEach {
            builder.append("[$it]")
        }
        builder.appendln("{$documentClass}")
        packages.forEach { packageName ->
            builder.appendln("\\usepackage{$packageName}")
        }
        builder.appendln("\\title{$title}")
        author?.let { author ->
            builder.appendln("\\author{${author.name}}")
            author.affiliation?.let {
                builder.appendln("\\institute{${it}}")
            }
        }
        //builder.appendln("\\author{${author!!.name}}")
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }

    fun usePackage(packageName: String) {
        if (!packages.contains(packageName)) packages.add(packageName)
    }
}

class Article : Preamble("article")

fun article(body: Article.() -> Unit): Article {
    TODO()
}

fun Document.makeTitle() = +"\\maketitle"

fun document(preamble: Preamble, init: Document.() -> Unit): Document {
    val document = Document(preamble)
    document.init()
    return document
}

tailrec fun Element.getDocument(): Document {
    return if (this is Document) this else parent!!.getDocument()
}

fun Tag.usePackage(packageName: String) {
    val doc = getDocument()
    doc.preamble.usePackage(packageName)
}

class Label(label: String): SingleTitledTag("label", label) {
    override fun toString(): String = "\\ref{$title}"
}

fun Tag.label(name: String): Label = initTag(Label(name)) {}

fun footnote(note: String): String = "\\footnote{$note}"

fun footnote1(note: String) = footnote(note)

fun String.footnote(note: String) = this + footnote1(note)


fun String.textbf() = "\\textbf{$this}"

fun String.bold() = textbf()

@JvmName("boldText")
fun Document.bold(text: String) = text.textbf()

@JvmName("textBf")
fun Document.textbf(text: String) = text.textbf()

val Document.space: String
    get() = "\\,"

//fun escapeText(text: String): String = text.map { ch -> if ("&%\$#_{}~^\\".contains(ch)) "\\$ch" else "$ch" }.joinToString(separator = "")