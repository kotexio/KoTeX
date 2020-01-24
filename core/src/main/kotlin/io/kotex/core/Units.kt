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
    open operator fun times(fraction: Number) = Length("$fraction$value")
    override fun toString(): String = value
}

data class MeasuredLength(val length: Number, val units: Units): Length("$length$units") {
    override operator fun times(fraction: Number) =
        MeasuredLength(fraction.toDouble() * length.toDouble(), units)
}

operator fun Number.times(length: Length): Length {
    return length.times(this)
}

val Number.pt: MeasuredLength
    get() = MeasuredLength(this, Units.PT)

val Number.mm: MeasuredLength
    get() = MeasuredLength(this, Units.MM)

val Number.cm: MeasuredLength
    get() = MeasuredLength(this, Units.CM)

val Number.inch: MeasuredLength
    get() = MeasuredLength(this, Units.IN)

val Number.ex: MeasuredLength
    get() = MeasuredLength(this, Units.EX)

val Number.em: MeasuredLength
    get() = MeasuredLength(this, Units.EM)

val Number.mu: MeasuredLength
    get() = MeasuredLength(this, Units.MU)

fun textWidth() = Length("\\textwidth")


