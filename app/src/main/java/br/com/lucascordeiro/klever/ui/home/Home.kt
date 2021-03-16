package br.com.lucascordeiro.klever.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.components.helper.getViewModel
import br.com.lucascordeiro.klever.theme.PurpleDark
import br.com.lucascordeiro.klever.theme.PurpleLight
import br.com.lucascordeiro.klever.ui.home.actions.HomeActions
import br.com.lucascordeiro.klever.ui.home.balance.HomeBalance
import br.com.lucascordeiro.klever.ui.home.coins.HomeCoins
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = getViewModel(),
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        val bankAccount by homeViewModel.bankAccount.collectAsState()

        HomeHeader(
            bankAccount = bankAccount,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        )

        HomeBalance(
            bankAccount = bankAccount,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .fillMaxWidth(),
        )

        HomeCoins(
            openBankAccountCoin = { /*TODO abrir tela de bankAccountCoin */},
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        HomeActions(
            balance = bankAccount.balance?:0.0,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
    }
}