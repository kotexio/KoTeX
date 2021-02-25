package io.kotex.core

class IeeeTran : Preamble("IEEEtran")

fun ieeeTran(initializer: IeeeTran.() -> Unit): Preamble {
    val ieeeTran = IeeeTran()
    ieeeTran.initializer()
    return ieeeTran
}
