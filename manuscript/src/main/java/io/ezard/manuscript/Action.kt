package io.ezard.manuscript

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.ezard.manuscript.manuscript.LocalManuscriptData
import io.ezard.manuscript.manuscript.ManuscriptScope
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Action(
    val name: String,
    private val onTrigger: (Action) -> Unit,
) {
    context(ManuscriptScope)
    fun trigger() {
        onTrigger(this)
    }
}

context(ManuscriptScope)
@Composable
fun action(name: String): Action {
    val data = LocalManuscriptData.current
    return Action(name = name, onTrigger = data::triggerAction)
}

private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

@Composable
fun Action(action: Pair<LocalDateTime, Action>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = DATE_TIME_FORMATTER.format(action.first),
            style = MaterialTheme.typography.overline,
        )
        Text(text = action.second.name)
    }
}
