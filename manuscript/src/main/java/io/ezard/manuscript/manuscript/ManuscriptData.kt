package io.ezard.manuscript.manuscript

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import io.ezard.manuscript.actions.Action
import io.ezard.manuscript.controls.Control
import io.ezard.manuscript.variant.Variant
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow

@Immutable
internal class ManuscriptData {
    internal val variants: MutableList<Variant> = mutableListOf()
    internal val controls: MutableList<Control<*>> = mutableListOf()
    internal val actions: MutableStateFlow<List<Pair<LocalDateTime, Action>>> =
        MutableStateFlow(emptyList())

    fun registerVariant(name: String, block: @Composable () -> Unit) {
        val variant = Variant(
            name = name,
            block = block,
        )
        variants.add(variant)
    }

    fun <T> registerControl(control: Control<T>): Control<T> {
        controls.add(control)
        return control
    }

    fun triggerAction(action: Action) {
        actions.value = actions.value + (LocalDateTime.now() to action)
    }
}

internal val LocalManuscriptData = compositionLocalOf { ManuscriptData() }
