package io.kotex.core

open class Symbol(val tag: Tag, val body: String) {
    override fun toString(): String = body
}

fun Tag.symbol(body: String): Symbol = Symbol(this, body)

fun Symbol.mathbf(): Symbol {
    tag.usePackage("amsfonts")
    return Symbol(tag,"\\mathbf{${this.body}}")
}

fun Symbol.mathcal(): Symbol{
    tag.usePackage("amsfonts")
    return Symbol(tag,"\\mathcal{${this.body}}")
}

fun Symbol.mathbb(): Symbol {
    tag.usePackage("amsfonts")
    return Symbol(tag,"\\mathbb{${this.body}}")
}

fun Symbol.mathfrak(): Symbol {
    tag.usePackage("amsfonts")
    return Symbol(tag, "\\mathfrak(${this.body}}")
}