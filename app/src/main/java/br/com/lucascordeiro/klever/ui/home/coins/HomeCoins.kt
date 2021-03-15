package br.com.lucascordeiro.klever.ui.home.coins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucascordeiro.klever.components.helper.getViewModel
import br.com.lucascordeiro.klever.domain.model.bankaccount.coin.BankAccountCoin
import br.com.lucascordeiro.klever.domain.utils.currency
import br.com.lucascordeiro.klever.domain.utils.format
import br.com.lucascordeiro.klever.theme.GreenVibrant
import br.com.lucascordeiro.klever.theme.PurpleDark
import br.com.lucascordeiro.klever.theme.PurpleLight
import br.com.lucascordeiro.klever.theme.PurpleMedium
import dev.chrisbanes.accompanist.coil.CoilImage
import timber.log.Timber

@Composable
fun HomeCoins(
    openBankAccountCoin: (BankAccountCoin) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeCoinsViewModel = getViewModel(),
){
    Column(modifier) {
        val bankAccountCoins by viewModel.bankAccountCoins.collectAsState()

        Text(
            text = "Currency",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Medium,
            )
        )

        LazyRow(Modifier.padding(top = 8.dp)){
            itemsIndexed(bankAccountCoins){ index ,item ->
                CoinCard(
                    bankAccountCoin = item,
                    onClick = { openBankAccountCoin(item) },
                    modifier = Modifier
                        .padding(start = if(index > 0) 16.dp else 0.dp)
                        .width(160.dp)
                )
            }
        }
    }
}

@Composable
private fun CoinCard(
    bankAccountCoin: BankAccountCoin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
            .background(
                brush = Brush.verticalGradient(
                    0f to PurpleMedium,
                    1f to PurpleDark,
                ),
            )
    ) {
        Timber.tag("BUG").d("Coin: $bankAccountCoin")

        val coinBalance = remember(bankAccountCoin.amount, bankAccountCoin.coin?.price) {
            (bankAccountCoin.amount ?: 0.0) * (bankAccountCoin.coin?.price ?: 0.0)
        }
        val coinPercent = remember(bankAccountCoin.coin?.percent) { bankAccountCoin.coin?.percent ?: 0f }

        Column(
            Modifier.padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                CoilImage(
                    data = bankAccountCoin.coin?.iconUrl ?: "",
                    contentDescription = "${bankAccountCoin.coin?.name} icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = bankAccountCoin.coin?.shortName?.toUpperCase() ?: "",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.End,
                    ),
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            }
            Text(
                text = coinBalance.currency(),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier.padding(top = 30.dp)
            )
            if (coinPercent != 0f)
                Text(
                    text = "${if (coinPercent > 0f) "+" else "-"} ${(coinPercent * coinBalance).format(2)} (${coinPercent.format(2)}%)",
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 12.sp,
                        color = if (coinPercent > 0f) GreenVibrant else MaterialTheme.colors.secondary
                    )
                )
        }
    }
}