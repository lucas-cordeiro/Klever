package br.com.lucascordeiro.klever.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Int.fromPx() : Dp {
    return (this /  LocalContext.current.resources.displayMetrics.density).roundToInt().dp
}