package io.ezard.manuscript.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

@Immutable
internal class ManuscriptLibraryData(
    val defaultLibraryDarkTheme: Boolean?,
    private val listener: (pair: Pair<String, @Composable () -> Unit>?) -> Unit,
) {
    fun onComponentSelected(pair: Pair<String, @Composable () -> Unit>?) {
        listener(pair)
    }
}

internal val LocalManuscriptLibraryData = compositionLocalOf {
    ManuscriptLibraryData(
        defaultLibraryDarkTheme = null,
        listener = {},
    )
}
