package br.com.lucascordeiro.klever.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.theme.FontLight
import br.com.lucascordeiro.klever.utils.fromPx
import br.com.lucascordeiro.klever.utils.toPx
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun LinearChart(
    data: List<Double>,
    modifier: Modifier = Modifier,
    legendBottom: List<String> = emptyList(),
    legendEnd: List<Pair<String,Double?>> = emptyList(),
    dataColor: Color = MaterialTheme.colors.primary,
    dataWidth: Dp = 2.dp,
    dataCorners: Dp = 16.dp,
    gridColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
    gridWidth: Float =  Stroke.HairlineWidth,
    columnWidth: Dp = 30.dp,
    gridHorizontalLinesCount: Int = if(legendEnd.isNotEmpty()) legendEnd.size else 5,
    gridVerticalLinesCount: Int = data.size,
){
    BoxWithConstraints(modifier) {
        val dataColorSecondary = MaterialTheme.colors.secondary

        var path by remember { mutableStateOf(Path()) }
        var path2  by remember { mutableStateOf(Path()) }

        val context = LocalContext.current

        val dataWidthInPx = remember(dataWidth) { dataWidth.toPx(context) }

        val bottomSpace = remember { 20.dp }
        val bottomSpaceInPx = remember(bottomSpace) { bottomSpace.toPx(context) }

        var legendLabels: List<Pair<String,Offset>> by remember { mutableStateOf(emptyList()) }

        val endSpace = remember { 20.dp }
        val endSpaceInPx = remember(bottomSpace) { endSpace.toPx(context) }

        val columnWidthInPx = remember(columnWidth) { columnWidth.toPx(context) }

        val height = remember(constraints.maxHeight, bottomSpaceInPx) { this.constraints.maxHeight.toFloat() - bottomSpaceInPx }
        val width = remember(columnWidthInPx, gridVerticalLinesCount, endSpaceInPx) { ((columnWidthInPx * (gridVerticalLinesCount)) - endSpaceInPx).coerceAtLeast(10f) }

        LaunchedEffect(data, gridHorizontalLinesCount, gridVerticalLinesCount){
            val drawPath = Path()
            val drawPath2 = Path()

            val tempLegendLabels: MutableList<Pair<String,Offset>> = ArrayList()

            drawPath2.moveTo(0f, height)

            for(x in 0 until gridVerticalLinesCount) {//Bottom Labels
                val spaceWidth = columnWidthInPx * x

                if(x == 0) {
                    drawPath.moveTo(spaceWidth, (height - (height * data[x])).toFloat())
                    drawPath2.lineTo(spaceWidth, (height - (height * data[x])).toFloat())
                }else if(x == data.lastIndex){
                    drawPath2.lineTo(spaceWidth, (height - (height * data[x])).toFloat())
                }

                drawPath2.lineTo(spaceWidth, (height - (height * data[x])).toFloat())
                if(x == data.lastIndex){
                    drawPath2.lineTo(spaceWidth, height)
                }

                drawPath.lineTo(spaceWidth, (height - (height * data[x])).toFloat())

                try{
                    tempLegendLabels.add(Pair(legendBottom[x], Offset(spaceWidth, height)))
                }catch (t: Throwable){
                    //Ignorar erro caso não exista o label naquela posição
                }
            }

            for(y in 0 until gridHorizontalLinesCount) {//End Labels
                val spaceHeight= (height / (gridHorizontalLinesCount-1)) * y

                try{
                    val legend = legendEnd[y]
                    val legendY = if(legend.second?:0.0> 0.0){
                        height - (height * (legend.second?:0.0).toFloat())
                    } else spaceHeight
                    tempLegendLabels.add(Pair(legend.first, Offset(width - endSpaceInPx, legendY - if(y > 0) ((bottomSpaceInPx / 5) * 3).roundToInt() else 0)))
                }catch (t: Throwable){
                    //Ignorar erro caso não exista o label naquela posição
                }
            }

            path = drawPath
            path2 = drawPath2
            legendLabels = tempLegendLabels
        }

        Canvas(
            Modifier
                .padding(
                    end = endSpace,
                    bottom = bottomSpace,
                )
                .width(width.fromPx(context))
                .fillMaxHeight()){

            for(x in 0 until gridVerticalLinesCount) {
                val spaceWidth = columnWidthInPx * x

                drawLine(
                    color = gridColor,
                    start = Offset(spaceWidth, 0f),
                    end = Offset(spaceWidth, size.height),
                    strokeWidth = gridWidth,
                )
            }

            for(y in 0 until gridHorizontalLinesCount) {
                val spaceHeight = if(legendEnd.isNotEmpty() && legendEnd.size >= y && legendEnd[y].second?:0.0 > 0.0){
                    size.height - (size.height * (legendEnd[y].second?:0.0).toFloat())
                } else (size.height / (gridHorizontalLinesCount-1)) * y
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
                    colors = listOf(dataColor.copy(alpha = 0.6f), dataColor.copy(alpha = 0.4f), dataColorSecondary.copy(alpha = 0.3f)),
                    colorStops = listOf(0f, 0.5f, 1f)
                )
                paint.style = PaintingStyle.Fill
                paint.pathEffect = PathEffect.cornerPathEffect(dataCorners.toPx())
                paint.strokeMiterLimit = dataCorners.toPx()
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
                brush = Brush.verticalGradient(
                    0f to dataColor,
                    0.5f to dataColor.copy(alpha = 0.5f),
                    1f to dataColorSecondary
                ),
            )
        }

        legendLabels.forEach { bottomLabel ->
            val (label, offset) = bottomLabel
            val offsetX = remember(offset){ offset.x.fromPx(context) }
            val offsetY = remember(offset){ offset.y.fromPx(context) }

            Text(
                text = label,
                style = MaterialTheme.typography.body2.copy(
                    color = FontLight,
                ),
                modifier = Modifier
                    .absoluteOffset(
                        offsetX,
                        offsetY,
                    )
            )
        }
    }
}