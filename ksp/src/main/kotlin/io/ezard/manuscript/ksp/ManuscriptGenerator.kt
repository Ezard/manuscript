package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

class ManuscriptGenerator(
    private val environment: SymbolProcessorEnvironment,
) {

    fun generate(controls: List<ControlData>) {
        val controlStatements = controls
            .joinToString(System.lineSeparator().padEnd(26, ' ')) { control ->
                "Control(type = ${control.type}::class) { control -> ${control.function}(control = control) }"
            }
        val code = """
            import androidx.compose.runtime.Composable
            import io.ezard.manuscript.controls.*
            import io.ezard.manuscript.manuscript.InternalManuscriptScope
            import io.ezard.manuscript.manuscript.ManuscriptScope

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
                packageName = "io.ezard.manuscript.manuscript",
                fileName = "Manuscript",
            )
            .writer()
            .append(code)
            .close()
    }
}
