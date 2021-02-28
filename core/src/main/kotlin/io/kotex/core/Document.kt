package io.kotex.core

abstract class Element {
    var parent: Element? = null
        internal set

    abstract fun render(builder: StringBuilder, indent: String)
}

abstract class Tag(val name: String) : Element() {
    val children = arrayListOf<Element>()

    fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.parent = this
        tag.init()
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
        builder.appendLine("$indent$text")
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
        builder.appendLine()
        renderContent(builder, indent)
        builder.appendLine("$indent\\end{$name}")
    }

    protected open fun renderContent(builder: StringBuilder, indent: String) {
        super.render(builder, indent)
    }

    protected fun addOption(option: String) {
        if (!opts.contains(option)) opts.add(option)
    }
}

class Document(val preamble: Preamble) : Environment("document") {
    fun abstract(init: Abstract.() -> Unit): Abstract = initTag(Abstract(), init)

    fun section(title: String, init: Section.() -> Unit): Section = initTag(Section(title), init)

    fun toTex(): String {
        val builder = StringBuilder()
        render(builder, "")
        return preamble.toString() + builder.toString()
    }
}

open class AbstractListTag(name: String) : Environment(name) {
    operator fun String.unaryMinus() {
        children.add(TextElement("\\item ${this.trimIndent()}"))
    }
}

class Itemize : AbstractListTag("itemize") {
    operator fun String.minus(lhs: String) {
        children.add(TextElement("\\item[$this] $lhs"))
    }
}

class Enumerate : AbstractListTag("enumerate")

class Abstract : Environment("abstract")

class Section(title: String) : SingleTitledTag("section", title) {
    fun subsection(title: String, init: SubSection.() -> Unit): SubSection = initTag(SubSection(title), init)
}

class SubSection(title: String) : SingleTitledTag("subsection", title)

data class Author(val name: String, val affiliation: String? = null)

fun Document.makeTitle() = +"\\maketitle"

fun document(preamble: Preamble, init: Document.() -> Unit): Document {
    val document = Document(preamble)
    document.init()
    return document
}

tailrec fun Element.getDocument(): Document {
    return if (this is Document) this else parent!!.getDocument()
}

fun Tag.usePackage(packageName: String, opts: List<String> = listOf()) {
    val doc = getDocument()
    doc.preamble.usePackage(packageName, opts)
}

class Label(label: String) : SingleTitledTag("label", label) {
    override fun toString(): String = "\\ref{$title}"
}

fun Tag.label(name: String): Label = initTag(Label(name)) {}

fun footnote(note: String): String = "\\footnote{$note}"

fun footnote1(note: String) = footnote(note)

fun String.footnote(note: String) = this + footnote1(note)

fun String.textbf() = "\\textbf{$this}"

fun String.emph() = "\\emph{$this}"

fun String.bold() = textbf()

fun String.italic() = emph()

@JvmName("boldText")
fun Document.bold(text: String) = text.textbf()

@JvmName("textBf")
fun Document.textbf(text: String) = text.textbf()

fun Document.emph(text: String) = text.emph()

fun Document.italic(text: String) = text.italic()

val Document.space: String
    get() = "\\,"

fun Tag.appendix() {
    children.add(TextElement("\\appendix"))
}

fun Tag.url(ref: String): String {
    usePackage("hyperref", opts = listOf("unicode"))
    return "\\url{$ref}"
}