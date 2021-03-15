package br.com.lucascordeiro.klever.components.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lucascordeiro.klever.components.DialogNeutral

@Composable
fun ErrorDialogState(
    state: ViewState,
    resetState: () -> Unit,
    modifier: Modifier = Modifier
){
    val showDialog = remember(state) { state is ViewState.Error && !state.message.isNullOrBlank() }
    if(showDialog){
        state as ViewState.Error
        DialogNeutral(
                text = {
                    Column() {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Normal
                            ),
                            color = MaterialTheme.colors.onSurface,
                        )
                        if(state.code?:0 > 0)
                            Text(
                                text = "#${state.code.toString().padStart(4, '0')}",
                                style = MaterialTheme.typography.body2.copy(
                                    color =  MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                    }
                },
                onDismissRequest = resetState,
                onNeutralButtonClick = resetState,
                modifier = modifier,
        )
    }
}
