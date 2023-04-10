package should_work

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control

@ManuscriptControl(type = String::class)
@Composable
fun StringControl(control: Control<String>) {
}
