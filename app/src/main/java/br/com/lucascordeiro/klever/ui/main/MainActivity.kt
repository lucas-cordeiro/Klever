package br.com.lucascordeiro.klever.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCase
import br.com.lucascordeiro.klever.theme.KleverTheme
import br.com.lucascordeiro.klever.theme.PurpleDark
import br.com.lucascordeiro.klever.theme.PurpleLight
import br.com.lucascordeiro.klever.ui.home.Home
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val getBankAccountUseCase: GetBankAccountUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KleverTheme {
                // A surface container using the 'background' color from the theme
                ProvideWindowInsets {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    0f to PurpleLight,
                                    1f to PurpleDark,
                                )
                            )
                            .systemBarsPadding()
                    ) {
                        Home()
                    }

                }
            }
        }

        lifecycleScope.launch(IO) {
            getBankAccountUseCase.getBankAccount().collect { bankAccount ->
                Timber.tag("BUG").d("BankAccount: $bankAccount")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KleverTheme {
        Greeting("Android")
    }
}