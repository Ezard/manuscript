package io.ezard.manuscript.theme

import androidx.compose.runtime.compositionLocalOf

data class ManuscriptComponentTheme(val darkTheme: Boolean? = null)

val LocalManuscriptComponentTheme = compositionLocalOf { ManuscriptComponentTheme() }
