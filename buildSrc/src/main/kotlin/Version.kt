private fun getVersion(): String {
    val process = Runtime.getRuntime().exec("git describe --tags --abbrev=0")
    process.waitFor()
    val result = process.inputStream.bufferedReader().lineSequence().first()
    return result.ifBlank { "0.0.0" }
}

val VERSION by lazy { getVersion() }
