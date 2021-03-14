package br.com.lucascordeiro.klever.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.GetBankAccountUseCase
import br.com.lucascordeiro.klever.theme.KleverTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
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
                ProvideWindowInsets() {
                    Surface(color = MaterialTheme.colors.background) {
                        Greeting("Android")
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