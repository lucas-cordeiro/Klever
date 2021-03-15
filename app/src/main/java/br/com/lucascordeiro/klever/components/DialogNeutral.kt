package br.com.lucascordeiro.klever.components

import androidx.compose.material.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DialogNeutral(
    text: @Composable()() -> Unit,
    onDismissRequest: () -> Unit,
    onNeutralButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = onDismissRequest,
            text = text,
            buttons = {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val button = createRef()
                    Text(
                            text = "Ok",
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(onClick = onNeutralButtonClick)
                                    .padding(8.dp)
                                    .constrainAs(button) {
                                        end.linkTo(parent.end, 8.dp)
                                        bottom.linkTo(parent.bottom, 8.dp)
                                    }
                    )
                }
            },
            modifier = modifier
    )
}