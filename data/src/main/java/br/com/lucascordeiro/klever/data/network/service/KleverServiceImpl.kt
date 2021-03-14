package br.com.lucascordeiro.klever.data.network.service

import br.com.lucascordeiro.klever.KleverServiceGrpcKt
import br.com.lucascordeiro.klever.data.network.httpurl.HttpUrlType
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.net.URL

class KleverServiceImpl(
    private val httpUrlApp: HttpUrlType.HttpUrlApp,
) : KleverService{

    private val httpUrl = httpUrlApp.httpUrl

    private fun channel(): ManagedChannel {
        val url = URL(httpUrl.url)
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

    override fun getService(): KleverServiceGrpcKt.KleverServiceCoroutineStub {
        return kleverService
    }
}