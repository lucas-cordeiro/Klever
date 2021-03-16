package br.com.lucascordeiro.klever.ui.home.balance

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.lucascordeiro.klever.components.LinearChart
import br.com.lucascordeiro.klever.components.SwitchController
import br.com.lucascordeiro.klever.components.SwitchDefaults
import br.com.lucascordeiro.klever.components.helper.getViewModel
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.TransactionRange
import br.com.lucascordeiro.klever.domain.utils.currency
import br.com.lucascordeiro.klever.domain.utils.formattedDay
import br.com.lucascordeiro.klever.domain.utils.getDayOfMonth
import br.com.lucascordeiro.klever.domain.utils.getMonth
import br.com.lucascordeiro.klever.domain.utils.getWeek
import br.com.lucascordeiro.klever.domain.utils.round
import br.com.lucascordeiro.klever.domain.utils.toLabel
import br.com.lucascordeiro.klever.theme.FontLight
import br.com.lucascordeiro.klever.theme.PurpleMedium
import br.com.lucascordeiro.klever.utils.fromPx
import br.com.lucascordeiro.klever.utils.toPx
import kotlinx.coroutines.delay
import timber.log.Timber
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

@Composable
fun HomeBalance(
    bankAccount: BankAccount,
    modifier: Modifier = Modifier,
    viewModel: HomeBalanceViewModel = getViewModel(),
){

    LaunchedEffect(bankAccount) {
        if ((bankAccount.balance ?: 0.0) > 0.0)
            viewModel.collectTransactions(bankAccount.balance ?: 0.0)
    }

    val transactions by viewModel.transactions.collectAsState()
    val range by viewModel.transactionRange.collectAsState()

    Column(modifier) {
        Balance(
            bankAccount,
            modifier = Modifier
        )
        TransactionsChart(
            balance = bankAccount.balance?:0.0,
            transactionsData = transactions,
            range = range,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
        )

        Divider(
            color = FontLight.copy(alpha = ContentAlpha.medium),
            modifier =  Modifier
                .padding(top = 4.dp)
        )

        SwitchTransactionRange(
            currentRange = range,
            onUpdateRange = { viewModel.updateTransactionRange(it) },
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun Balance(
    bankAccount: BankAccount,
    modifier: Modifier = Modifier
){
    Box(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Rounded.Computer,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(16.dp)
            )
        }

        Crossfade(targetState = bankAccount, Modifier.padding(top = 20.dp)) {
            ConstraintLayout() {
                val (balance, label) = createRefs()

                Text(
                    text = it.balance.currency(),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onBackground
                    ),
                    modifier = Modifier.constrainAs(balance){
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "Total wealth",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Normal,
                        color = FontLight,
                    ),
                    modifier = Modifier.constrainAs(label){
                        baseline.linkTo(balance.baseline)
                        start.linkTo(balance.end, 8.dp)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsChart(
    balance: Double,
    transactionsData: Triple<List<BankAccountTransaction>, Double, Double>,
    range: TransactionRange,
    modifier: Modifier = Modifier
) {
    var chartData: Triple<List<Double>, List<String>, List<Pair<String, Double?>>> by remember {
        mutableStateOf(
            Triple(
                emptyList(),
                emptyList(),
                emptyList()
            )
        )
    }

    LaunchedEffect(balance, transactionsData, range) {
        if (transactionsData.first.isNullOrEmpty()) {
            chartData = Triple(emptyList(),  emptyList(), emptyList())
        } else {
            val transactions = transactionsData.first
            var maxAmount = transactionsData.second
            var minAmount = transactionsData.third
            if(balance > maxAmount) maxAmount = balance
            if(balance < minAmount) minAmount = balance
            val diff = maxAmount - minAmount

            val legendBottom = transactions.asReversed().map {
                when(range){
                    TransactionRange.Day -> it.transferDate?.formattedDay()
                    TransactionRange.Week -> it.transferDate?.getWeek()
                    TransactionRange.Month -> it.transferDate?.getDayOfMonth()?.toString()
                    else -> it.transferDate?.getMonth()
                }
            }.filterNotNull().toMutableList()
            legendBottom.removeLast()
            legendBottom.add("Now")


            val legendEnd: MutableList<Pair<String, Double?>> = ArrayList()
            legendEnd.add(Pair(maxAmount.roundToInt().toLabel(), null))
            legendEnd.add(Pair(balance.roundToInt().toLabel(), (balance - minAmount) / diff))
            legendEnd.add(Pair(minAmount.roundToInt().toLabel(), null))

            chartData = Triple(transactions.map { it.balancePercent?:0.0 }.asReversed(), legendBottom, legendEnd)
        }
    }

    val chartScrollState = rememberScrollState()

    LaunchedEffect(chartScrollState.maxValue){
        chartScrollState.animateScrollTo(
            value = chartScrollState.maxValue,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing,
            )
        )
    }

   Crossfade(targetState = chartData, modifier) { data ->
       BoxWithConstraints() {
           val context = LocalContext.current

           val chartHeight = remember { 200.dp }
           val columnWidth = remember(this.constraints.maxWidth, data.first) {  if(data.first.isNotEmpty() && constraints.maxWidth >= data.first.size * 70.dp.toPx(context)) maxWidth.div(data.first.size) else 70.dp }

           LinearChart(
               data = data.first,
               legendBottom = data.second,
               legendEnd = data.third,
               columnWidth = columnWidth,
               gridVerticalLinesCount = data.first.size,
               modifier = Modifier
                   .horizontalScroll(chartScrollState)
                   .width(columnWidth.times(data.first.size))
                   .height(chartHeight)
           )
       }
   }
}

@Composable
private fun SwitchTransactionRange(
    currentRange: TransactionRange,
    onUpdateRange: (TransactionRange) -> Unit,
    modifier: Modifier = Modifier
){
    SwitchController(
        value = currentRange.label,
        options = listOf(TransactionRange.Day.label, TransactionRange.Week.label, TransactionRange.Month.label, TransactionRange.Year.label),
        onValueChange = { onUpdateRange(when(it){
            TransactionRange.Day.label -> TransactionRange.Day
            TransactionRange.Week.label -> TransactionRange.Week
            TransactionRange.Month.label -> TransactionRange.Month
            else -> TransactionRange.Year
        }) },
        colors = SwitchDefaults.switchColors(
            accentBrush = Brush.linearGradient(
                0f to MaterialTheme.colors.secondary,
                1f to MaterialTheme.colors.primary,
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            ),
            contentColor = MaterialTheme.colors.onPrimary,
            backgroundColor = Color.Transparent,
            onBackgroundColor = FontLight
        ),
        acceptNull = false,
        modifier = modifier,
    )
}

