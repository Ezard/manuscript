package should_fail_when_control_has_more_than_one_parameter

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control

@ManuscriptControl(type = String::class)
@Composable
fun StringControl(control: Control<String>, text: String) {
}
