package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

class ManuscriptGenerator(
    private val environment: SymbolProcessorEnvironment,
) {
    companion object {
        private const val PACKAGE_NAME = "io.ezard.manuscript.manuscript"
    }

    fun generate(controls: List<ControlData>) {
        val controlStatements = controls
            .joinToString(System.lineSeparator().padEnd(26, ' ')) { control ->
                "Control(type = ${control.type}::class) { control -> ${control.function}(control = control) }"
            }
        val code = """
            package $PACKAGE_NAME

            import androidx.compose.runtime.Composable
            import io.ezard.manuscript.controls.*

            @Composable
            fun Manuscript(darkTheme: Boolean? = null, block: @Composable ManuscriptScope.() -> Unit) {
                with(object : InternalManuscriptScope {}) {
                    Manuscript(darkTheme = darkTheme) {
                        $controlStatements
                        block()
                    }
                }
            }
        """.trimIndent()

        val sources = controls
            .mapNotNull(ControlData::file)
            .toTypedArray()
        environment.codeGenerator
            .createNewFile(
                dependencies = Dependencies(aggregating = true, sources = sources),
                packageName = PACKAGE_NAME,
                fileName = "Manuscript",
            )
            .writer()
            .append(code)
            .close()
    }
}
