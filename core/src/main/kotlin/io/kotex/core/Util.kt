import java.io.File

operator fun File.div(file: File): File = File(this.absolutePath + File.separatorChar + file.absolutePath)

operator fun File.div(path: String): File = File(this.absolutePath + File.separatorChar + path)

inline fun <reified K, V> K.backingField(map: MutableMap<K, V>, default: () -> V) =
    map[this] ?: run {
        val result = default()
        map[this] = result
        result
    }