package io.ezard.manuscript.control

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.ManuscriptScope
import kotlin.reflect.KProperty

class Control<T>(
    val name: String,
    internal val state: MutableState<T>,
) {
    operator fun getValue(thisObj: Any?, property: KProperty<*>) = state.getValue(thisObj, property)

    operator fun setValue(thisObj: Any?, property: KProperty<*>, value: T) =
        state.setValue(thisObj, property, value)
}

@Composable
internal fun <T> Control(control: Control<T>, block: @Composable BoxScope.() -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(TextFieldDefaults.MinHeight),
    ) {
        Text(text = control.name, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            block()
        }
    }
}

context(ManuscriptScope)
@Composable
fun <T> control(name: String, defaultValue: T): Control<T> {
    val state = remember { mutableStateOf(defaultValue) }
    val data = LocalManuscriptData.current
    return remember {
        data.registerControl(
            Control(
                name = name,
                state = state,
            ),
        )
    }
}
