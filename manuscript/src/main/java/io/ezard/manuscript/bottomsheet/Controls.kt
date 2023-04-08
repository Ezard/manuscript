package io.ezard.manuscript.bottomsheet

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.controls.*
import io.ezard.manuscript.manuscript.LocalManuscriptData

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
            val data = LocalManuscriptData.current
            controls.forEachIndexed { index, control ->
                val value = control.state.value ?: return@forEachIndexed
                val customControl = data.controlComponents
                    .firstOrNull { (type) -> type == value::class }
                    ?.second
                if (customControl == null) {
                    Log.w("Manuscript", "No control found for ${value::class}")
                } else {
                    ControlWrapper(control = control) {
                        customControl(control = control)
                    }
                    if (index < controls.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
