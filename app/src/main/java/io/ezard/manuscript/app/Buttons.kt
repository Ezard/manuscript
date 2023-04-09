package io.ezard.manuscript.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
private fun Button(text: String, color: Color, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .background(color)
            .clickable(onClick = onClick),
    )
}

@Composable
fun RedButton(text: String, onClick: () -> Unit) {
    Button(text = text, color = Color.Red, onClick = onClick)
}

@Composable
fun GreenButton(text: String, onClick: () -> Unit) {
    Button(text = text, color = Color.Blue, onClick = onClick)
}

@Composable
fun BlueButton(text: String, onClick: () -> Unit) {
    Button(text = text, color = Color.Green, onClick = onClick)
}
