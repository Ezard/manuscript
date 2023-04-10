package should_fail_when_control_has_a_single_parameter_not_of_type_control

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl

@ManuscriptControl(type = String::class)
@Composable
fun StringControl(control: String) {
}
