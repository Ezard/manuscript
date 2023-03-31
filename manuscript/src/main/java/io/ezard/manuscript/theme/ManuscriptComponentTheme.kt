package io.ezard.manuscript.theme

import androidx.compose.runtime.compositionLocalOf

internal data class ManuscriptComponentTheme(val darkTheme: Boolean? = null)

internal val LocalManuscriptComponentTheme = compositionLocalOf { ManuscriptComponentTheme() }
