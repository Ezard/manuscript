package io.ezard.manuscript.manuscript

import androidx.compose.runtime.Composable
import io.ezard.manuscript.controls.*
import io.ezard.manuscript.manuscript.InternalManuscriptScope
import io.ezard.manuscript.manuscript.ManuscriptScope

@Composable
fun Manuscript(darkTheme: Boolean? = null, block: @Composable ManuscriptScope.() -> Unit) {
    with(object : InternalManuscriptScope {}) {
        Manuscript(darkTheme = darkTheme) {
            Control(type = kotlin.String::class) { control -> should_work.StringControl(control = control) }
            block()
        }
    }
}
