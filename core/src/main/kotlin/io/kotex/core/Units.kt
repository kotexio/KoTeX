package io.kotex.core

enum class Units(private val repr: String) {
    PT("pt"),
    MM("mm"),
    CM("cm"),
    IN("in"),
    EX("ex"),
    EM("em"),
    MU("mu");

    override fun toString(): String = repr
}

interface Length {
    operator fun times(fraction: Number): Length
}

data class MeasuredLength(val length: Number, val units: Units): Length {
    override operator fun times(fraction: Number) =
        MeasuredLength(fraction.toDouble() * length.toDouble(), units)

    override fun toString(): String = "$length$units"
}

data class SpecialLength(val length: String, val fraction: Number = 1.0): Length {
    override fun times(fraction: Number) =
        SpecialLength(length, fraction.toDouble() * this.fraction.toDouble())

    override fun toString(): String = "${if (fraction.toDouble() == 1.0) "" else "$fraction"}$length"
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

fun textWidth() = SpecialLength("\\textwidth")


