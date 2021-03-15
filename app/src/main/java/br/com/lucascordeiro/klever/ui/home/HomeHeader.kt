package br.com.lucascordeiro.klever.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun HomeHeader(
    bankAccount: BankAccount,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
    ) {
        val circleColor = MaterialTheme.colors.secondary

        var badge by remember { mutableStateOf(true) }
        Crossfade(
            targetState = badge,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            modifier = Modifier.align(Alignment.CenterStart)
        ) { badgeState ->
            Canvas(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { badge = !badgeState })
                    .padding(vertical = 13.dp, horizontal = 10.dp)

            ) {

                val lineHeight = this.size.div(5f).height
                val circleSize = Size(this.size.div(3f).height, this.size.div(3f).height)

                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(0f, (circleSize.width - lineHeight) / 2),
                    size = Size(size.width / 3, lineHeight),
                    cornerRadius = CornerRadius(circleSize.width / 2, circleSize.width / 2)
                )

                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(
                        0f,
                        (circleSize.width - lineHeight) / 2 + (lineHeight * 2)
                    ),
                    size = Size(size.width, lineHeight),
                    cornerRadius = CornerRadius(circleSize.width / 2, circleSize.width / 2)
                )

                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(
                        0f,
                        (circleSize.width - lineHeight) / 2 + (lineHeight * 4)
                    ),
                    size = Size((size.width / 3) * 2, lineHeight),
                    cornerRadius = CornerRadius(circleSize.width / 2, circleSize.width / 2)
                )

                if (badgeState)
                    drawCircle(
                        color = circleColor,
                        radius = circleSize.width / 2,
                        center = Offset(
                            (size.width / 3) + circleSize.width,
                            circleSize.height / 2
                        ),
                    )
            }
        }

        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.align(Alignment.Center)
        )


        Box(
            Modifier
                .clip(CircleShape)
                .size(36.dp)
                .align(Alignment.CenterEnd)
        ) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.align(Alignment.Center).padding(12.dp)
            )
            CoilImage(
                data = bankAccount.profilePicUrl ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}