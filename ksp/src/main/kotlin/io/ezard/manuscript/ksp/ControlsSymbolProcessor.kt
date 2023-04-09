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

private fun checkFunctionHasControlParameter(function: KSFunctionDeclaration): Boolean {
    val baseMessage =
        "Functions annotated with @ManuscriptControl must have a single parameter, called 'control', of type io.ezard.manuscript.controls.Control"
    val functionLocation = getFunctionLocation(function)
    when {
        function.parameters.isEmpty() -> throw IllegalStateException("$baseMessage: this function has zero parameters ($functionLocation)")
        function.parameters.size > 1 -> throw IllegalStateException("$baseMessage: this function has more than 1 parameter ($functionLocation)")
        function.parameters.first().name?.asString() != "control" -> throw IllegalStateException("$baseMessage: this function's parameter is not called 'control' ($functionLocation)")
        function.parameters.first().type.resolve().declaration.qualifiedName?.asString() != "io.ezard.manuscript.controls.Control" -> throw IllegalStateException(
            "$baseMessage: this function's parameter is not of type io.ezard.manuscript.controls.Control ($functionLocation)",
        )
        else -> return true
    }
}

private fun functionDeclarationToControlData(function: KSFunctionDeclaration): ControlData {
    val annotation = function.annotations.firstOrNull { annotation ->
        annotation.shortName.asString() == "ManuscriptControl"
    } ?: throw Exception()
    val type = (annotation.arguments
        .firstOrNull { argument -> argument.name?.asString() == "type" }
        ?.value as? KSType)
        ?.declaration
        ?: throw Exception()

    val file = function.containingFile
    return ControlData(
        file = file,
        type = type.qualifiedName?.asString().orEmpty(),
        function = function.qualifiedName?.asString().orEmpty(),
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
            .asSequence()
            .filter(KSAnnotated::validate)
            .filterIsInstance<KSFunctionDeclaration>()
            .filter(::checkFunctionIsComposable)
            .filter(::checkFunctionHasControlParameter)
            .map(::functionDeclarationToControlData)
            .toList()

        manuscriptGenerator.generate(controls)

        return emptyList()
    }
}
