package io.ezard.manuscript

import androidx.compose.runtime.Composable
import io.ezard.manuscript.manuscript.ManuscriptScope

class Variant(
    val name: String,
    val block: @Composable () -> Unit,
)

fun ManuscriptScope.variant(name: String, block: @Composable () -> Unit) {
    registerVariant(name, block)
}
