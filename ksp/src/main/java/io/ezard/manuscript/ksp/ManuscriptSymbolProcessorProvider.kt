package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ManuscriptSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val manuscriptGenerator = ManuscriptGenerator(environment)
        return ControlsSymbolProcessor(manuscriptGenerator)
    }
}
