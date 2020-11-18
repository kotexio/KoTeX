package io.kotex.core

class Article: Preamble("article")

fun article(init: Article.() -> Unit): Article {
    val beamer = Article()
    beamer.init()
    return beamer
}