package io.ezard.manuscript.variant

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.manuscript.ManuscriptScope

internal class Variant(
    val name: String,
    val block: @Composable () -> Unit,
)

/**
 * Register a component variant
 *
 * The variant API allows you to group together closely-related components (e.g. button & button with icon)
 *
 * @param [name] the name of the variant; this is displayed to the user as a tab
 *
 * @sample [io.ezard.manuscript.variant.VariantSample]
 */
context(ManuscriptScope)
@Composable
fun Variant(name: String, block: @Composable () -> Unit) {
    LocalManuscriptData.current.registerVariant(name, block)
}

@Composable
private fun VariantSample() {
    Manuscript {
        Variant(name = "Button") {
            Button(onClick = {}) {
                Text(text = "Click me!")
            }
        }
        Variant(name = "Button With Icon") {
            Button(onClick = {}) {
                Row {
                    Text(text = "Click me!")
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_delete),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}
