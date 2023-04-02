package io.ezard.manuscript.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.manuscript.ManuscriptScope
import io.ezard.manuscript.variant.Variant
import kotlin.reflect.KClass

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
        data.registerControl(type = type, control = control)
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
