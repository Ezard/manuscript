package io.ezard.manuscript.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

interface ManuscriptLibraryScope {
    fun onComponentSelected(pair: Pair<String, @Composable () -> Unit>?)
}

@Immutable
internal class ManuscriptLibraryScopeInstance(
    private val listener: (pair: Pair<String, @Composable () -> Unit>?) -> Unit,
) : ManuscriptLibraryScope {
    override fun onComponentSelected(pair: Pair<String, @Composable () -> Unit>?) {
        listener(pair)
    }
}

internal val LocalManuscriptLibraryScope =
    compositionLocalOf<ManuscriptLibraryScope> { ManuscriptLibraryScopeInstance {} }
