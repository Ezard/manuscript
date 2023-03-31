package io.ezard.manuscript.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

internal class ManuscriptComponentTheme(
    private val isDarkTheme: Boolean? = null,
) {
    val darkTheme: Boolean
        @Composable get() = isDarkTheme ?: isSystemInDarkTheme()
}

internal val LocalManuscriptComponentTheme = compositionLocalOf { ManuscriptComponentTheme() }
