package io.ezard.manuscript.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.ezard.manuscript.library.Component
import io.ezard.manuscript.library.Group
import io.ezard.manuscript.library.ManuscriptLibrary

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // this sets up a navigable UI for your components
            ManuscriptLibrary {
                // components can be referenced individually
                Component(name = "Buttons") {
                    ButtonManuscript()
                }

                // components can be grouped together
                Group(name = "Common") {
                    Component(name = "Buttons") {
                        ButtonManuscript()
                    }
                    Component(name = "Checkboxes") {
                        // other Manuscript composable here
                    }
                    Component(name = "Sliders") {
                        // other Manuscript composable here
                    }
                }
            }
        }
    }
}
