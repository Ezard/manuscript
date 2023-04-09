package io.ezard.manuscript.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ezard.manuscript.actions.action
import io.ezard.manuscript.controls.control
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.variant.Variant

@Preview
@Composable
fun ButtonManuscript() {
    Manuscript {
        // create controls to allow data to be changed in realtime when viewing the component
        val text by control(name = "Text", defaultValue = "Click me!")

        // create actions to register when certain interactions with the component occur
        val onClick = action(name = "onClick")

        // set up 1 or more variants of the component
        Variant(name = "Red") {
            RedButton(text = text, onClick = onClick::trigger)
        }
        Variant(name = "Green") {
            GreenButton(text = text, onClick = onClick::trigger)
        }
        Variant(name = "Blue") {
            BlueButton(text = text, onClick = onClick::trigger)
        }
    }
}
