package io.ezard.manuscript.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

internal val LightColours = lightColors(
    primary = Black,
    onPrimary = White,
    secondary = Accent,
    onSecondary = Black,
    surface = White,
    onSurface = Black,
    background = White,
    onBackground = Black,
    error = Red,
    onError = White,
)

internal val DarkColours = darkColors(
    primary = Grey400,
    onPrimary = White,
    secondary = Accent,
    onSecondary = Black,
    surface = Grey400,
    onSurface = White,
    background = Black,
    onBackground = White,
    error = Red,
    onError = White,
)

@Composable
internal fun ManuscriptTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colours = when {
        darkTheme -> DarkColours
        else -> LightColours
    }
    MaterialTheme(
        colors = colours,
        content = content,
    )
}
