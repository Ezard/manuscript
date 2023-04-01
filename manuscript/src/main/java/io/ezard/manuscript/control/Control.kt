package io.ezard.manuscript.control

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.Variant
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.manuscript.ManuscriptScope
import kotlin.reflect.KProperty

/**
 * A reference to a control for a component
 */
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

/**
 * Register a control for this component
 *
 * This allows the user to change the data that the component is using, and see updates to it in realtime
 *
 * Currently supported data types for controls are:
 * - Boolean
 * - Double
 * - Float
 * - Int
 * - String
 *
 * If a control with an unsupported data type is registered then it will not appear in Manuscript's bottom sheet, and the value of the control will always be the default value
 *
 * @param [name] the name of the control; this will be displayed alongside the input field in Manuscript's bottom sheet
 * @param [defaultValue] the initial value of the control
 *
 * @sample [io.ezard.manuscript.control.ControlSample]
 */
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

@Composable
private fun ControlSample() {
    Manuscript {
        val text by control(name = "Text", defaultValue = "Click me!")

        Variant(name = "Button") {
            Button(onClick = {}) {
                Text(text = text)
            }
        }
    }
}
