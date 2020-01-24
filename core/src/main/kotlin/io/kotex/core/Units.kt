package io.kotex.core

enum class Units(private val repr: String) {
    PT("pt"),
    MM("mm"),
    CM("cm"),
    IN("in"),
    EX("ex"),
    EM("em"),
    MU("mu");

    override fun toString(): String {
        return repr
    }
}

open class Length(private val value: String) {
    override fun toString(): String = value
}

data class MeasuredLength(val length: Double, val units: Units): Length("$length$units")

fun pt(length: Double) = MeasuredLength(length, Units.PT)

fun mm(length: Double) = MeasuredLength(length, Units.MM)

fun cm(length: Double) = MeasuredLength(length, Units.CM)

fun inch(length: Double) = MeasuredLength(length, Units.IN)

fun ex(length: Double) = MeasuredLength(length, Units.EX)

fun em(length: Double) = MeasuredLength(length, Units.EM)

fun mu(length: Double) = MeasuredLength(length, Units.MU)

fun textWidth(fraction: Double) = Length("$fraction\\textwidth")

fun textWidth() = Length("\\textwidth")


