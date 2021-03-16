package br.com.lucascordeiro.klever.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.utils.fromPx

@Composable
fun SwitchController(
    value: String?,
    options: List<String>,
    onValueChange: (String?) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.switchColors(),
    acceptNull: Boolean = true,
) {

    val shape = MaterialTheme.shapes.medium

    Surface(
        shape = shape,
        color = colors.backgroundColor(enabled = true).value,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp),
        ) {
            options.forEach { option ->
                val checked = remember(value) {option == value  }
                val state = remember(checked) { if (checked) SwitchState.Enable else SwitchState.Disable }

                SwitchImpl(
                    value = state,
                    onBackgroundColor = colors.onBackgroundColor(enabled = true).value,
                    accentBrush =  colors.accentBrush(enabled = true).value,
                    contentColor =  colors.contentColor(enabled = true).value,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            onClick = { onValueChange(if (checked && acceptNull) null else option) },
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                        )
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.body2.copy(
                            color = it,
                            fontWeight = if(checked) FontWeight.Bold else FontWeight.Medium,
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun SwitchImpl(
    value: SwitchState,
    onBackgroundColor: Color,
    accentBrush: Brush,
    contentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(color: Color) -> Unit,
) {
    val transition = updateTransition(value)
    val colorDrawFactory by transition.animateColor(
        transitionSpec = {
            when {
                initialState == SwitchState.Disable -> tween(
                    SWITCH_ANIMATION_DURATION,
                    easing = FastOutSlowInEasing
                )
                targetState == SwitchState.Disable -> tween(SWITCH_ANIMATION_DURATION)
                else -> spring()
            }
        }
    ) {
        when (it) {
            SwitchState.Enable -> contentColor
            SwitchState.Disable -> onBackgroundColor
        }
    }
    val backgroundDrawFactory by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == SwitchState.Disable -> tween(
                    SWITCH_ANIMATION_DURATION,
                    easing = FastOutSlowInEasing
                )
                targetState == SwitchState.Disable -> tween(SWITCH_ANIMATION_DURATION)
                else -> spring()
            }
        }
    ) {
        when (it) {
            SwitchState.Enable -> 1f
            SwitchState.Disable -> 0f
        }
    }

    Box(
        modifier = modifier
    ) {
        var contentSize: Offset by remember{ mutableStateOf(Offset(10f, 10f)) }
        val context = LocalContext.current

        Spacer(modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .alpha(backgroundDrawFactory)
            .background(brush = accentBrush)
            .width(contentSize.x.fromPx(context).plus(8.dp))
            .height(contentSize.y.fromPx(context).plus(8.dp))
        )

        Box(Modifier.padding(horizontal = 4.dp, vertical = 4.dp).onGloballyPositioned {
            contentSize = Offset(it.size.width.toFloat(), it.size.height.toFloat())
        }) {
            content(colorDrawFactory)
        }
    }
}

sealed class SwitchState {
    object Enable: SwitchState()
    object Disable: SwitchState()
}

@Stable
interface SwitchColors {
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>
    @Composable
    fun onBackgroundColor(enabled: Boolean): State<Color>
    @Composable
    fun accentBrush(enabled: Boolean): State<Brush>
    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

object SwitchDefaults {
    @Composable
    fun switchColors(
        backgroundColor: Color = MaterialTheme.colors.surface,
        onBackgroundColor: Color = MaterialTheme.colors.onSurface,
        accentBrush: Brush = SolidColor(MaterialTheme.colors.primary),
        contentColor: Color = MaterialTheme.colors.onPrimary
    ) : SwitchColors = DefaultSwitchColors(backgroundColor, onBackgroundColor, accentBrush, contentColor)
}

private class DefaultSwitchColors (
    private val backgroundColor: Color,
    private val onBackgroundColor: Color,
    private val accentBrush: Brush,
    private val contentColor: Color,
) : SwitchColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else backgroundColor.copy(alpha = ContentAlpha.disabled))
    }

    @Composable
    override fun onBackgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) onBackgroundColor else onBackgroundColor.copy(alpha = ContentAlpha.disabled))
    }

    @Composable
    override fun accentBrush(enabled: Boolean): State<Brush> {
        return rememberUpdatedState(if (enabled) accentBrush else accentBrush)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else contentColor.copy(alpha = ContentAlpha.disabled))
    }
}


private const val SWITCH_ANIMATION_DURATION = 300
