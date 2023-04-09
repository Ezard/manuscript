package io.ezard.manuscript.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.manuscript.ManuscriptScope
import io.ezard.manuscript.variant.Variant
import kotlin.reflect.KClass
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
internal fun <T> ControlWrapper(control: Control<T>, block: @Composable BoxScope.() -> Unit) {
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
 * Register a custom control for handling a data type that Manuscript doesn't natively handle
 *
 * @param [type] the class that this control should be used for
 * @param [control] composable function to
 *
 * NOTE: if you think that there's a common data type that Manuscript should handle that it doesn't currently handle, then create an issue on the GitHub repo!
 *
 * @sample [io.ezard.manuscript.controls.ColourControl]
 * @sample [io.ezard.manuscript.controls.CustomControlSample]
 */
@Suppress("unused")
@Composable
fun <T : Any> ManuscriptScope.Control(
    type: KClass<T>,
    control: @Composable BoxScope.(control: Control<T>) -> Unit,
) {
    val data = LocalManuscriptData.current
    remember {
        data.registerControlComponent(type = type, component = control)
    }
}

@Composable
private fun ColourControl(control: Control<Color>) {
    Row {
        var color by control

        val red = (color.red * 255).toInt().toString()
        val green = (color.green * 255).toInt().toString()
        val blue = (color.blue * 255).toInt().toString()

        TextField(
            value = red,
            onValueChange = { r -> color = color.copy(red = r.toFloat() / 255) },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextField(
            value = green,
            onValueChange = { g -> color = color.copy(green = g.toFloat() / 255) },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextField(
            value = blue,
            onValueChange = { b -> color = color.copy(blue = b.toFloat() / 255) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CustomControlSample() {
    Manuscript {
        Control(
            type = Color::class,
            control = { control -> ColourControl(control = control) },
        )

        val colour by control(name = "Colour", defaultValue = Color.Red)

        Variant("Button") {
            Button(onClick = {}) {
                Text(text = "Click me!", modifier = Modifier.background(colour))
            }
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
 * @sample [io.ezard.manuscript.controls.ControlSample]
 */
@Composable
fun <T> ManuscriptScope.control(name: String, defaultValue: T): Control<T> {
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
