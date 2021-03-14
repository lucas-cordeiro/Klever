package br.com.lucascordeiro.klever.data.network.service

import br.com.lucascordeiro.klever.KleverServiceGrpcKt
import io.grpc.ManagedChannel

interface KleverService {
    fun getService(): KleverServiceGrpcKt.KleverServiceCoroutineStub
}