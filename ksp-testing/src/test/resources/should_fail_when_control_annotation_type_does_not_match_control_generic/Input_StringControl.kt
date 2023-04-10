package should_fail_when_annotation_type_does_not_match_control_generic

import androidx.compose.runtime.Composable
import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control

@ManuscriptControl(type = String::class)
@Composable
fun StringControl(control: Control<Int>) {
}
