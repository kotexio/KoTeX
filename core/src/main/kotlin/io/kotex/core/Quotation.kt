package io.kotex.core

class Quotation: Environment("quotation")
class Quote: Environment("quote")

// FIXME: quotation is not allowed everywhere
fun Tag.quotation(init: Quotation.() -> Unit): Quotation = initTag(Quotation(), init)
fun Tag.quote(init: Quote.() -> Unit): Quote = initTag(Quote(), init)
