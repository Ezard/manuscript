package io.ezard.manuscript.ksp

import com.google.devtools.ksp.symbol.KSFile

data class ControlData(
    val file: KSFile?,
    val type: String,
    val function: String,
)
