package io.ezard.manuscript.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.Action
import io.ezard.manuscript.control.Control
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAB_INDEX_CONTROLS = 0
private const val TAB_INDEX_ACTIONS = 1
private val TABS = listOf(
    "Controls" to TAB_INDEX_CONTROLS,
    "Actions" to TAB_INDEX_ACTIONS,
)

@OptIn(ExperimentalMaterialApi::class)
private fun onTabSelected(
    coroutineScope: CoroutineScope,
    bottomSheetState: BottomSheetState,
    oldIndex: Int,
    newIndex: Int,
) {
    coroutineScope.launch {
        if (bottomSheetState.isCollapsed) {
            bottomSheetState.expand()
        } else if (newIndex == oldIndex) {
            bottomSheetState.collapse()
        }
    }
}

@Composable
fun BottomSheetTab(
    name: String,
    index: Int,
    selectedIndex: Int,
    onSelected: (oldIndex: Int, newIndex: Int) -> Unit,
) {
    Tab(
        text = { Text(text = name) },
        selected = index == selectedIndex,
        onClick = {
            onSelected(selectedIndex, index)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BottomSheet(
    controls: List<Control<*>>,
    actions: List<Pair<LocalDateTime, Action>>,
    bottomSheetState: BottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(0.dp)) {
        var selectedTabIndex by remember { mutableStateOf(TAB_INDEX_CONTROLS) }
        TabRow(selectedTabIndex = selectedTabIndex) {
            TABS.forEach { tab ->
                BottomSheetTab(
                    name = tab.first,
                    index = tab.second,
                    selectedIndex = selectedTabIndex,
                    onSelected = { oldIndex, newIndex ->
                        selectedTabIndex = newIndex
                        onTabSelected(
                            coroutineScope = coroutineScope,
                            bottomSheetState = bottomSheetState,
                            oldIndex = oldIndex,
                            newIndex = selectedTabIndex,
                        )
                    },
                )
            }
        }
        when (selectedTabIndex) {
            TAB_INDEX_CONTROLS -> Controls(controls = controls)
            TAB_INDEX_ACTIONS -> Actions(actions = actions)
        }
    }
}
