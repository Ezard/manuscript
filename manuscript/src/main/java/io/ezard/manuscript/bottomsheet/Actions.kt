package io.ezard.manuscript.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.Action
import java.time.LocalDateTime

@Composable
internal fun Actions(actions: List<Pair<LocalDateTime, Action>>) {
    if (actions.isEmpty()) {
        Placeholder(text = "No recorded actions yet")
    } else {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
        ) {
            itemsIndexed(actions) { index, action ->
                Action(action = action)
                if (index < actions.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
