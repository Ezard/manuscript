package io.ezard.manuscript.manuscript

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import io.ezard.manuscript.Action
import io.ezard.manuscript.Variant
import io.ezard.manuscript.control.Control
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow

interface ManuscriptScope {
    fun registerVariant(name: String, block: @Composable () -> Unit)

    fun <T> registerControl(control: Control<T>): Control<T>

    fun triggerAction(action: Action)
}

@Immutable
internal class ManuscriptScopeInstance : ManuscriptScope {
    internal val variants: MutableList<Variant> = mutableListOf()
    internal val controls: MutableList<Control<*>> = mutableListOf()
    internal val actions: MutableStateFlow<List<Pair<LocalDateTime, Action>>> =
        MutableStateFlow(emptyList())

    override fun registerVariant(name: String, block: @Composable () -> Unit) {
        val variant = Variant(
            name = name,
            block = block,
        )
        variants.add(variant)
    }

    override fun <T> registerControl(control: Control<T>): Control<T> {
        controls.add(control)
        return control
    }

    override fun triggerAction(action: Action) {
        actions.value = actions.value + (LocalDateTime.now() to action)
    }
}
