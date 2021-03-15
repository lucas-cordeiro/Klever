package br.com.lucascordeiro.klever.components.state

import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import br.com.lucascordeiro.klever.components.DialogNeutral

@Composable
fun SuccessDialogState(
    state: ViewState,
    resetState: () -> Unit,
    modifier: Modifier = Modifier
){
    val showDialog = remember(state) { state is ViewState.Success && !state.message.isNullOrBlank() }
    if(showDialog){
        DialogNeutral(
                text = {
                    Text(
                            text = (state as ViewState.Success).message,
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Normal
                            )
                    )
                },
                onDismissRequest = resetState,
                onNeutralButtonClick = resetState,
                modifier = modifier,
        )
    }
}
