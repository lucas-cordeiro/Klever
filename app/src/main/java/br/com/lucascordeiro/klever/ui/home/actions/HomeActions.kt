package br.com.lucascordeiro.klever.ui.home.actions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.components.helper.getViewModel
import br.com.lucascordeiro.klever.components.state.ErrorDialogState
import br.com.lucascordeiro.klever.components.state.SuccessDialogState
import br.com.lucascordeiro.klever.components.state.ViewState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeActions(
    balance: Double,
    modifier: Modifier = Modifier,
    viewModel: HomeActionsViewModel = getViewModel(),
){
    val state by viewModel.state.collectAsState()

    Column(modifier) {
        Text(
            text = "Actions",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Medium,
            )
        )

        AnimatedVisibility(visible = state !is ViewState.Loading) {
            Row(Modifier.padding(top = 8.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    onClick = { viewModel.addTransaction(balance, true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Buy",
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onPrimary,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    ),
                    onClick = { viewModel.addTransaction(balance, false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Sell",
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onPrimary,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.TrendingDown,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
        AnimatedVisibility(visible = state is ViewState.Loading) {
            Box(Modifier.fillMaxWidth()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }

    ErrorDialogState(state = state, resetState = { viewModel.resetState() })
    SuccessDialogState(state = state, resetState = { viewModel.resetState() })
}