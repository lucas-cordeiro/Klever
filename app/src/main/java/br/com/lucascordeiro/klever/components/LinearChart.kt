package br.com.lucascordeiro.klever.components

import android.graphics.CornerPathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toComposePathEffect
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun LinearChart(
    data: List<Double>,
    modifier: Modifier = Modifier,
    dataColor: Color = MaterialTheme.colors.primary,
    dataWidth: Dp = 2.dp,
    dataCorners: Dp = 16.dp,
    gridColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
    gridWidth: Float =  Stroke.HairlineWidth,
    gridHorizontalLinesCount: Int = 11,
){
    Canvas(modifier){
        val path = Path()
        val path2 = Path()

        path2.moveTo(0f, size.height)


        for(x in data.indices) {
            val spaceWidth = (size.width / (data.size - 1)) * x

            if(x == 0) {
                path.moveTo(spaceWidth, (size.height - (size.height * data[x])).toFloat())
                path2.lineTo(spaceWidth, (size.height - (size.height * data[x])).toFloat())
            }else if(x == data.lastIndex){
                path2.lineTo(spaceWidth, (size.height - (size.height * data[x])).toFloat())
            }

            path2.lineTo(spaceWidth, (size.height - (size.height * data[x])).toFloat())
            path.lineTo(spaceWidth, (size.height - (size.height * data[x])).toFloat())
            drawLine(
                color = gridColor,
                start = Offset(spaceWidth, 0f),
                end = Offset(spaceWidth, size.height),
                strokeWidth = gridWidth,
            )
        }

        path2.lineTo(size.width, size.height)

        for(y in 0 until gridHorizontalLinesCount) {
            val spaceHeight= (size.height / (gridHorizontalLinesCount-1)) * y
            drawLine(
                color = gridColor,
                start = Offset(0f, spaceHeight),
                end =Offset(size.width, spaceHeight),
                strokeWidth = gridWidth,
            )
        }



        drawIntoCanvas {
            val paint = Paint()

            paint.color = dataColor
            paint.shader = LinearGradientShader(
                from = Offset(size.width/2,0f),
                to = Offset(size.width/2, size.height),
                colors = listOf(dataColor.copy(alpha = 0.5f), Color.Transparent),
            )
            paint.style = PaintingStyle.Fill
            paint.pathEffect = PathEffect.cornerPathEffect(dataCorners.toPx())
            paint.strokeJoin = StrokeJoin.Round
            it.drawPath(path2, paint)
        }

        drawPath(
            path = path,
            style = Stroke(
                width = dataWidth.toPx(),
                pathEffect = PathEffect.cornerPathEffect(dataCorners.toPx()),
                join = StrokeJoin.Round,
                miter = dataCorners.toPx(),
            ),
            color = dataColor,
        )
    }
}

private class LinearChartShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}