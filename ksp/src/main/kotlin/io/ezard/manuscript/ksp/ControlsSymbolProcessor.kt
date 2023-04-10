package io.ezard.manuscript.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate

private fun checkFunctionIsComposable(function: KSFunctionDeclaration): Boolean {
    val isComposable = function.annotations.any { annotation ->
        annotation.shortName.asString() == "Composable"
    }
    if (isComposable) {
        return true
    } else {
        throw ManuscriptCodeGenException(
            "Functions annotated with @ManuscriptControl must also be annotated with @Composable",
            function,
        )
    }
}

private fun checkFunctionHasControlParameter(function: KSFunctionDeclaration): Boolean {
    val baseMessage =
        "Functions annotated with @ManuscriptControl must have a single parameter, called 'control', of type io.ezard.manuscript.controls.Control"
    when {
        function.parameters.isEmpty() -> throw ManuscriptCodeGenException(
            "$baseMessage: this function has zero parameters",
            function,
        )
        function.parameters.size > 1 -> throw ManuscriptCodeGenException(
            "$baseMessage: this function has more than 1 parameter",
            function,
        )
        function.parameters.first().name?.asString() != "control" -> throw ManuscriptCodeGenException(
            "$baseMessage: this function's parameter is not called 'control'",
            function,
        )
        function.parameters.first().type.resolve().declaration.qualifiedName?.asString() != "io.ezard.manuscript.controls.Control" -> throw ManuscriptCodeGenException(
            "$baseMessage: this function's parameter is not of type io.ezard.manuscript.controls.Control",
            function,
        )
        else -> return true
    }
}

private fun getAnnotationTypeParameterFullyQualifiedName(function: KSFunctionDeclaration): String {
    val annotation = function.annotations.firstOrNull { annotation ->
        annotation.shortName.asString() == "ManuscriptControl"
    } ?: throw Exception()
    val type = (annotation.arguments
        .firstOrNull { argument -> argument.name?.asString() == "type" }
        ?.value as? KSType)
        ?.declaration
        ?: throw Exception()
    return type.qualifiedName?.asString().orEmpty()
}

private fun getControlParameterGenericTypeFullyQualifiedName(function: KSFunctionDeclaration): String {
    val parameter = function.parameters.first().type.resolve()
    return parameter
        .arguments
        .first()
        .type
        ?.resolve()
        ?.declaration
        ?.qualifiedName
        ?.asString()
        .orEmpty()
}

private fun checkControlTypesMatch(function: KSFunctionDeclaration): Boolean {
    val annotationType = getAnnotationTypeParameterFullyQualifiedName(function)
    val parameterType = getControlParameterGenericTypeFullyQualifiedName(function)
    if (annotationType == parameterType) {
        return true
    } else {
        throw ManuscriptCodeGenException(
            "The class passed to @ManuscriptControl must match the type passed as the generic to the Control parameter",
            function,
        )
    }
}

private fun functionDeclarationToControlData(function: KSFunctionDeclaration): ControlData {
    val type = getAnnotationTypeParameterFullyQualifiedName(function)

    val file = function.containingFile
    return ControlData(
        file = file,
        type = type,
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
            .filter(::checkControlTypesMatch)
            .map(::functionDeclarationToControlData)
            .toList()

        manuscriptGenerator.generate(controls)

        return emptyList()
    }
}
