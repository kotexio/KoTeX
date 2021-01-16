import java.io.File

operator fun File.div(file: File): File = File(this.absolutePath + File.separatorChar + file.absolutePath)

operator fun File.div(path: String): File = File(this.absolutePath + File.separatorChar + path)