package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate

private fun getFunctionLocation(function: KSFunctionDeclaration): String {
    val functionName = function.simpleName.asString()
    val fileName = function.containingFile?.fileName ?: return functionName
    val lineNumber = (function.location as? FileLocation)?.lineNumber ?: return functionName
    return "$fileName:$lineNumber"
}

private fun checkFunctionIsComposable(function: KSFunctionDeclaration): Boolean {
    val isComposable = function.annotations.any { annotation ->
        annotation.shortName.asString() == "Composable"
    }
    if (isComposable) {
        return true
    } else {
        val functionLocation = getFunctionLocation(function)
        throw IllegalStateException("Functions annotated with @ManuscriptControl must also be annotated with @Composable ($functionLocation)")
    }
}

private fun functionDeclarationToControlData(functionDeclaration: KSFunctionDeclaration): ControlData {
    val typeDeclaration = (functionDeclaration.annotations
        .firstOrNull { annotation -> annotation.shortName.asString() == "ManuscriptControl" }
        ?.arguments
        ?.firstOrNull { argument -> argument.name?.asString() == "type" }
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

        val controls = resolver
            .getSymbolsWithAnnotation("io.ezard.manuscript.annotations.ManuscriptControl")
            .toList()
            .filter(KSAnnotated::validate)
            .filterIsInstance<KSFunctionDeclaration>()
            .filter(::checkFunctionIsComposable)
            .map(::functionDeclarationToControlData)

        manuscriptGenerator.generate(controls)

        return emptyList()
    }
}
