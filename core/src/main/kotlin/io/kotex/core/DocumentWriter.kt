package io.kotex.core

import backingField
import java.io.File

typealias WriteAction = (File) -> Unit

class DocumentWriter(private val document: Document) {
    private val writeChain = mutableListOf<WriteAction>()

    fun add(writeAction: WriteAction) {
        writeChain.add(writeAction)
    }

    fun write(file: File) {
        writeChain.forEach { it.invoke(file) }
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        file.writeText(document.toTex())
    }
}

val writerMap = mutableMapOf<Document, DocumentWriter>()

val Document.writer: DocumentWriter
    get() = backingField(writerMap) { DocumentWriter(this) }

fun Document.write(file: File) = writer.write(file)

fun Document.write(filename: String) = write(File(filename))