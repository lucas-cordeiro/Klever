package br.com.lucascordeiro.klever.ui.home.balance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.lucascordeiro.klever.components.LinearChart
import br.com.lucascordeiro.klever.components.helper.getViewModel
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.utils.currency
import br.com.lucascordeiro.klever.domain.utils.round
import br.com.lucascordeiro.klever.theme.FontLight
import java.math.RoundingMode
import kotlin.math.roundToInt

@Composable
fun HomeBalance(
    bankAccount: BankAccount,
    modifier: Modifier = Modifier,
    viewModel: HomeBalanceViewModel = getViewModel(),
){
    val transactions by viewModel.transactions.collectAsState()

    Column(modifier) {
        Balance(
            bankAccount,
            modifier = Modifier
        )
        TransactionsChart(
            balance = bankAccount.balance?:0.0,
            transactions = transactions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
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

        ConstraintLayout(Modifier.padding(top = 20.dp)) {
            val (balance, label) = createRefs()

            Text(
                text = bankAccount.balance.currency(),
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

@Composable
private fun TransactionsChart(
    balance: Double,
    transactions: List<BankAccountTransaction>,
    modifier: Modifier = Modifier
){
    var chartData:Triple<List<Double>, Int, List<Int>> by remember{ mutableStateOf(Triple(emptyList(), 11, emptyList()))}

    LaunchedEffect(balance, transactions){
        if(transactions.isNullOrEmpty()){
            chartData = Triple(emptyList(), 11, emptyList())
        }else{
            var currentBalance = balance

            val balancePerTransaction = transactions.mapIndexed { index, transaction ->
                currentBalance += (transaction.amount?:0.0) * -1
                currentBalance
            }.toMutableList()

            balancePerTransaction.add(0, balance)

            val minAmount = balancePerTransaction.minOrNull().round(-2, RoundingMode.DOWN)
            val maxAmount = balancePerTransaction.maxOrNull().round(-2, RoundingMode.UP)
            val diff = maxAmount - minAmount

            val percentBalancePerTransaction = balancePerTransaction.map {
                (it - minAmount) / diff
            }

            val legendCount = (diff/100).roundToInt()+1
            val legend: MutableList<Int> = ArrayList()
            for(i in 0 .. legendCount+1){
                legend.add((maxAmount - (100 * i)).roundToInt())
            }

            chartData = Triple(percentBalancePerTransaction.asReversed(), legendCount, legend)
        }
    }

   Row(modifier) {
       val chartHeight = remember { 200.dp }
       LinearChart(
           data = chartData.first,
           gridHorizontalLinesCount = chartData.second,
           modifier = Modifier
               .padding(top = 7.dp)
               .weight(1f)
               .height(chartHeight)
       )

       if(chartData.first.isNotEmpty() || chartData.third.isNotEmpty()){
           Box(Modifier.padding(start = 6.dp)) {
               val legendSpace = chartHeight.div(chartData.second - 1)

               for(i in 0 until chartData.second){
                   Text(
                       text = "${chartData.third[i]}",
                       style = MaterialTheme.typography.body2.copy(
                           fontWeight = FontWeight.Normal,
                           color = FontLight
                       ),
                       modifier = Modifier
                           .offset(y = legendSpace.times(i))
                   )
               }
           }
       }
   }
}