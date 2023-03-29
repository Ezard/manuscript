package io.ezard.manuscript.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.control.*

@Suppress("UNCHECKED_CAST")
@Composable
internal fun Controls(controls: List<Control<*>>) {
    if (controls.isEmpty()) {
        Placeholder(text = "No controls available")
    } else {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .verticalScroll(state = scrollState),
        ) {
            controls.forEachIndexed { index, control ->
                when (control.state.value) {
                    is Boolean -> BooleanControl(control = control as Control<Boolean>)
                    is Double -> DoubleControl(control = control as Control<Double>)
                    is Float -> FloatControl(control = control as Control<Float>)
                    is Int -> IntControl(control = control as Control<Int>)
                    is String -> StringControl(control = control as Control<String>)
                }
                if (index < controls.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
