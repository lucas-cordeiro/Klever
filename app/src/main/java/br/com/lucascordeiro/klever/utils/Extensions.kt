package br.com.lucascordeiro.klever.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

fun Int.fromPx(context: Context) : Dp {
    return (this /  context.resources.displayMetrics.density).roundToInt().dp
}

fun Float.fromPx(context: Context) : Dp {
    return this.roundToInt().fromPx(context)
}

fun Dp.toPx(context: Context): Float = value * context.resources.displayMetrics.density