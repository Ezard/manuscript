package should_fail_when_control_is_not_annotated_with_composable

import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control

@ManuscriptControl(type = String::class)
fun StringControl(control: Control<String>) {
}
