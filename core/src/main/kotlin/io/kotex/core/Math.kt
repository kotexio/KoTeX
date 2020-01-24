package io.kotex.core

import java.lang.StringBuilder

class Equation(val expr: Expr): Environment("equation") {
    override fun renderContent(builder: StringBuilder, indent: String) {
        builder.appendln("$indent${expr.toTex()}")
    }
}

fun TagWithText.equation(init: () -> Expr): Equation {
    val equation = Equation(init())
    children.add(equation)
    return equation
}

fun TagWithText.`$`(init: () -> Expr) {
    val equation = E(init())
    children.add(equation)
//    return equation
}


class E(val body: Expr): Tag("[") {
    override fun render(builder: StringBuilder, indent: String) {
        builder.appendln("$indent\\[")
        builder.appendln("$indent${body.toTex()}")
        builder.appendln("$indent\\]")
    }

//    private fun renderContent(builder: StringBuilder, indent: String) {
//        super.render(builder, indent)
//    }
}


abstract class Expr {
    operator fun plus(other: Expr): Expr = Plus(this, other)
    operator fun minus(other: Expr): Expr = Minus(this, other)
    infix fun eq(other: Expr) = Eq(this, other)
    infix fun `=` (other: Expr) = Eq(this, other)

    fun sup(y: Expr, vararg ys: Expr): Expr =
        Superscript(this, y, ys)
    fun sup(y: Number, vararg ys: Expr): Expr =
        Superscript(this, Symbol(y.toString()), ys)
//    fun `^`(y: Number, vararg ys: Expr): Expr =
//        Superscript(this, Symbol(y.toString()), ys)
    infix fun `^`(y: Number): Expr =
        Superscript(this, Symbol(y.toString()), arrayOf())

    infix fun `^`(y: Expr): Expr =
        Superscript(this, y, arrayOf())

    infix fun `_`(y: Number): Expr =
        Subscript(this, Symbol(y.toString()), arrayOf())

    infix fun `_`(y: Expr): Expr =
        Subscript(this, y, arrayOf())


    fun sub(y: Expr, vararg ys: Expr): Expr =
        Subscript(this, y, ys)
    fun sub(y: Number, vararg ys: Expr): Expr =
        Subscript(this, Symbol(y.toString()), ys)

    abstract fun toTex(): String
    override fun toString(): String = "$ ${toTex()} $"

    // parenthesis, braces and brackets
    operator fun get(expr: Expr): Expr =
        Mul(this, Brackets(expr))
    operator fun invoke(expr: Expr): Expr =
        Mul(this, Parenthesis(expr))
    fun angle(expr: Expr): Expr = Mul(this, AngleBrackets(expr))

    companion object {
        operator fun get(expr: Expr): Expr = Brackets(expr)
        operator fun invoke(expr: Expr): Expr = Parenthesis(expr)
    }
}

operator fun Number.minus(expr: Expr) = Minus(Symbol(this.toString()), expr)

abstract class GeneralBrackets(val expr: Expr, val left: String, val right: String): Expr() {
    override fun toTex(): String = "$left${expr.toTex()}$right"
}

class Brackets(expr: Expr): GeneralBrackets(expr, "[", "]")

class Parenthesis(expr: Expr): GeneralBrackets(expr, "(", ")")

class AngleBrackets(expr: Expr): GeneralBrackets(expr, "\\langle", "\\rangle")

class DecoratedBrackets(val brackets: GeneralBrackets, val decorator: String): Expr() {
    override fun toTex(): String = "$decorator${brackets.left}${brackets.expr.toTex()}$decorator${brackets.right}"
}

fun big(brackets: GeneralBrackets) = DecoratedBrackets(brackets, "\\big")
fun bigg(brackets: GeneralBrackets) = DecoratedBrackets(brackets, "\\bigg")

class Symbol(val body: String): Expr() {
    override fun toTex(): String = body
}

abstract class Infix(val x: Expr, val y: Expr, val op: String): Expr() {
    override fun toTex(): String = "${x.toTex()} $op ${y.toTex()}"
}

class Plus(x: Expr, y: Expr): Infix(x, y, "+")
class Minus(x: Expr, y: Expr): Infix(x, y, "-")
class Mul(x: Expr, y: Expr): Infix(x, y, " ")
class Eq(x: Expr, y: Expr): Infix(x, y, "=")

class Superscript(val x: Expr, val y: Expr, val ys: Array<out Expr>): Expr() {
    override fun toTex(): String {
        return if (ys.isEmpty()) "${x.toTex()} ^ ${y.toTex()}"
        else "${x.toTex()} ^ {${y.toTex()}" + ys.map { ", ${it.toTex()}" } + "}"
    }
}

class Subscript(val x: Expr, val y: Expr, val ys: Array<out Expr>): Expr() {
    override fun toTex(): String {
        return if (ys.isEmpty()) "${x.toTex()}_${y.toTex()}"
        else "${x.toTex()}_{${y.toTex()}" + ys.map { ", ${it.toTex()}" } + "}"
    }
}

class MathBold(val expr: Expr): Expr() {
    override fun toTex(): String = "\\mathbf{${expr.toTex()}}"
}

fun Expr.bold(): MathBold = MathBold(this)