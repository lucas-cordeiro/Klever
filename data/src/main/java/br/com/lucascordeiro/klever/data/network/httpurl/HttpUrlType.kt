package br.com.lucascordeiro.klever.data.network.httpurl

sealed class HttpUrlType {
    data class HttpUrlApp(val httpUrl: HttpUrl) : HttpUrlType()
}