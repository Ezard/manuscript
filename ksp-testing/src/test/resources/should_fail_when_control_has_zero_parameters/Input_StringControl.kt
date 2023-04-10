package should_fail_when_control_has_zero_parameters

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl

@ManuscriptControl(type = String::class)
@Composable
fun StringControl() {
}
