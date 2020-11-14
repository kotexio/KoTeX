package io.kotex.core

open class Symbol(val body: String) {
    override fun toString(): String = body
}

fun Symbol.mathbf() = Symbol("\\mathbf{${this.body}}")

fun Symbol.mathcal() = Symbol("\\mathcal{${this.body}}")

fun Symbol.mathbb() = Symbol("\\mathbb{${this.body}}")

fun Symbol.mathfrak() = Symbol("\\mathfrak(${this.body}}")