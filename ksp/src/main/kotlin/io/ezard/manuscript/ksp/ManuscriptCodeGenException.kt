package io.ezard.manuscript.ksp

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

private fun getFunctionLocation(function: KSFunctionDeclaration): String {
    val functionName = function.simpleName.asString()
    val fileName = function.containingFile?.fileName ?: return functionName
    val lineNumber = (function.location as? FileLocation)?.lineNumber ?: return functionName
    return "$fileName:$lineNumber"
}

class ManuscriptCodeGenException(
    message: String,
    function: KSFunctionDeclaration,
) : Exception("$message (${getFunctionLocation(function)})")
