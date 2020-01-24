package io.kotex.core

import java.lang.StringBuilder

class Verbatim: Environment("verbatim") {
    override fun after() {
        usePackage("verbatim")
    }

    override fun renderContent(builder: StringBuilder, indent: String) {
        children.forEach {
            it.render(builder, "")
        }
        after()
    }
}

fun Tag.verbatim(text: String): Verbatim = initTag(Verbatim()) { +text }
fun verb(text: String) = "\\verb|$text|"