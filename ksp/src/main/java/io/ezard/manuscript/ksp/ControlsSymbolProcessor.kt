package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import io.ezard.manuscript.annotations.ManuscriptControl

private fun functionDeclarationToTypeName(functionDeclaration: KSFunctionDeclaration): ControlData {
    val annotationSimpleName = ManuscriptControl::class.simpleName
    val typeDeclaration = (functionDeclaration.annotations
        .toList()
        .firstOrNull { annotation -> annotation.shortName.asString() == annotationSimpleName }
        ?.arguments
        ?.firstOrNull { argument -> argument.name?.asString() == ManuscriptControl::type.name }
        ?.value as? KSType)
        ?.declaration
        ?: throw Exception()

    val file = functionDeclaration.containingFile
    val type =
        "${typeDeclaration.packageName.asString()}.${typeDeclaration.simpleName.asString()}"
    val function =
        "${functionDeclaration.packageName.asString()}.${functionDeclaration.simpleName.asString()}"
    return ControlData(
        file = file,
        type = type,
        function = function,
    )
}

class ControlsSymbolProcessor(
    private val manuscriptGenerator: ManuscriptGenerator,
) : SymbolProcessor {
    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        } else {
            invoked = true
        }

        val annotationName = ManuscriptControl::class.qualifiedName ?: return emptyList()

        val controls = resolver
            .getSymbolsWithAnnotation(annotationName)
            .toList()
            .filter(KSAnnotated::validate)
            .filterIsInstance<KSFunctionDeclaration>()
            .map(::functionDeclarationToTypeName)

        manuscriptGenerator.generate(controls)

        return emptyList()
    }
}
