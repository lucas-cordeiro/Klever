package br.com.lucascordeiro.klever

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import br.com.lucascordeiro.klever.ui.theme.KleverTheme
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private fun channel(): ManagedChannel {
        val url = URL("https://klever-grpc-nl4j4nguma-uc.a.run.app")
        val port = 443


        val builder = ManagedChannelBuilder.forAddress(url.host, port)
        if (url.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }

    private val kleverService by lazy { KleverServiceGrpcKt.KleverServiceCoroutineStub(channel()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KleverTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }

        lifecycleScope.launch(IO) {
            val request = GetBankAccountRequest.newBuilder().setBankAccountId(1L).build()
            kleverService.getBankAccount(request).collect { bankAccount ->
                Log.d("BUG","BankAccount: $bankAccount")
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