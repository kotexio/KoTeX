package io.kotex.core

class IncludeGraphics(path: String, width: Length):
    SingleTitledTag("includegraphics", path, listOf("width=$width")) {
    override fun after() {
        usePackage("graphicx")
    }
}

fun Tag.includeGraphics(path: String, width: Length): IncludeGraphics = initTag(IncludeGraphics(path, width)) { }