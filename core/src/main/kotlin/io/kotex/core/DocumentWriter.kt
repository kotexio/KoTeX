package io.kotex.core

import java.io.File

typealias WriteAction = (File) -> Unit

class DocumentWriter(private val document: Document) {
    private val writeChain = mutableListOf<WriteAction>()

    fun add(writeAction: WriteAction) {
        writeChain.add(writeAction)
    }

    fun write(file: File) {
        writeChain.forEach { it.invoke(file) }
        file.writeText(document.toTex())
    }
}

val Document.writer: DocumentWriter
    get() = DocumentWriter(this)

fun Document.write(file: File) = writer.write(file)

fun Document.write(filename: String) = write(File(filename))