package io.ezard.manuscript.app

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.annotations.ManuscriptControl
import io.ezard.manuscript.controls.Control
import java.time.LocalDate

// register this composable to be used when you create a control of the specified type within a Manuscript
// e.g. val date by control(name = "Date", defaultValue = LocalDate.now())
@ManuscriptControl(type = LocalDate::class)
@Composable
fun DateControl(control: Control<LocalDate>) {
    // get the current value of the control
    // when you update the value of the control, make sure you replace the whole value instead of just changing a field inside it, otherwise the change won't be detected!
    var date by control

    Row {
        // input field for day of month
        TextField(
            value = date.dayOfMonth.toString(),
            onValueChange = { value ->
                // update value of the control with a new date with a modified day of month
                date = date.withDayOfMonth(value.toInt())
            },
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.width(4.dp))

        // input field for month
        TextField(
            value = date.month.toString(),
            onValueChange = { value ->
                // update value of the control with a new date with a modified month
                date = date.withMonth(value.toInt())
            },
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.width(4.dp))

        // input field for year
        TextField(
            value = date.year.toString(),
            onValueChange = { value ->
                // update value of the control with a new date with a modified year
                date = date.withYear(value.toInt())
            },
            modifier = Modifier.weight(2f),
        )
    }
}
