package io.ezard.manuscript

import androidx.compose.runtime.Composable
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.ManuscriptScope

internal class Variant(
    val name: String,
    val block: @Composable () -> Unit,
)

context(ManuscriptScope)
@Composable
fun Variant(name: String, block: @Composable () -> Unit) {
    LocalManuscriptData.current.registerVariant(name, block)
}
