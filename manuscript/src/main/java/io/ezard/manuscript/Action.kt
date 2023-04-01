package io.ezard.manuscript

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.Manuscript
import io.ezard.manuscript.manuscript.ManuscriptScope
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A reference to an action that can be triggered by a component
 *
 * Call [trigger] to cause an instance of this action to appear in the "Actions" section of Manuscript's bottom sheet
 */
class Action(
    val name: String,
    private val onTrigger: (Action) -> Unit,
) {
    /**
     * Trigger an instance of this action to appear in the "Actions" section of Manuscript's bottom sheet
     */
    context(ManuscriptScope)
    fun trigger() {
        onTrigger(this)
    }
}

/**
 * Register an action for this component
 *
 * Calling [trigger][io.ezard.manuscript.Action.trigger] on the returned action will cause an instance of it to appear in "Actions" section of Manuscript's bottom sheet
 *
 * @param [name] the name of the action; this will be displayed to the user when the action is triggered
 *
 * @sample [io.ezard.manuscript.ActionSample]
 */
context(ManuscriptScope)
@Composable
fun action(name: String): Action {
    val data = LocalManuscriptData.current
    return Action(name = name, onTrigger = data::triggerAction)
}

private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

@Composable
internal fun Action(action: Pair<LocalDateTime, Action>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = DATE_TIME_FORMATTER.format(action.first),
            style = MaterialTheme.typography.overline,
        )
        Text(text = action.second.name)
    }
}

@Composable
private fun ActionSample() {
    Manuscript {
        val onClick = action("onClick")

        Variant("Button") {
            Button(onClick = { onClick.trigger() }) {
                Text(text = "Click me!")
            }
        }
    }
}
