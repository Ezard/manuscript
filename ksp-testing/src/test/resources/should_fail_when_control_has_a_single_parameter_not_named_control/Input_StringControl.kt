package should_fail_when_control_has_a_single_parameter_not_named_control

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control

@ManuscriptControl(type = String::class)
@Composable
fun StringControl(foo: Control<String>) {
}
