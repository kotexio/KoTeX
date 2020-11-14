package io.kotex.core

import java.lang.StringBuilder

abstract class Preamble(private val documentClass: String, val opts: MutableList<String> = mutableListOf()) {
    var title: String? = null
    var subtitle: String? = null
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
    private val packages: MutableList<Pair<String, List<String>>> = mutableListOf()

    operator fun String.unaryPlus() = Author(this)

    operator fun String.div(other: String) = Author(this, other)

    fun today() = "\\today"

    open fun render(builder: StringBuilder) {
        builder.append("\\documentclass")

        opts.forEach {
            builder.append("[$it]")
        }

        builder.appendLine("{$documentClass}")

        packages.forEach { (packageName, opts) ->
            val options = opts.joinToString(separator = ",")
            builder.appendLine("\\usepackage[$options]{$packageName}")
        }

        builder.appendLine("\\title{$title}")

        subtitle?.let { subtitle ->
            builder.appendLine("\\subtitle{$subtitle}")
        }

        author?.let { author ->
            builder.appendLine("\\author{${author.name}}")
            author.affiliation?.let {
                builder.appendLine("\\institute{${it}}")
            }
        }
        //builder.appendln("\\author{${author!!.name}}")
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }

    fun usePackage(packageName: String, opts: List<String> = listOf()) {
        if (packages.find { it.first == packageName } == null) packages.add(packageName to opts)
    }
}
