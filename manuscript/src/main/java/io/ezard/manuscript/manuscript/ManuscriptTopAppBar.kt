package io.ezard.manuscript.manuscript

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import io.ezard.manuscript.theme.LocalManuscriptComponentName
import io.ezard.manuscript.theme.LocalManuscriptComponentTheme

private fun getChangeThemeImageVector(darkTheme: Boolean) = when (darkTheme) {
    true -> Icons.Default.LightMode
    false -> Icons.Default.DarkMode
}

@Composable
internal fun ManuscriptTopAppBar(
    onBackPressed: () -> Unit,
    onDarkThemeChange: (darkTheme: Boolean) -> Unit = {},
) {
    val navigationIcon: (@Composable () -> Unit)? = when (LocalManuscriptComponentName.current) {
        null -> null
        else -> {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                    )
                }
            }
        }
    }
    val action: @Composable RowScope.() -> Unit = {
        val darkTheme = LocalManuscriptComponentTheme.current.darkTheme
        IconButton(onClick = { onDarkThemeChange(!darkTheme) }) {
            Icon(
                imageVector = getChangeThemeImageVector(darkTheme),
                contentDescription = "",
            )
        }
    }
    TopAppBar(
        navigationIcon = navigationIcon,
        title = { Text(text = LocalManuscriptComponentName.current ?: "Component") },
        actions = action,
    )
}
