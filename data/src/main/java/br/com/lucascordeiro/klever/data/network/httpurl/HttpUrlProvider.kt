package br.com.lucascordeiro.klever.data.network.httpurl

import br.com.lucascordeiro.klever.data.BuildConfig
import java.security.InvalidParameterException

fun provideHttpUrlApp() : HttpUrlType.HttpUrlApp {
    val httpUrl = HttpUrl(
        port = 443,
        url = if(BuildConfig.BUILD_TYPE == "debug") "https://klever-grpc-nl4j4nguma-uc.a.run.app" else "https://klever-grpc-nl4j4nguma-uc.a.run.app"
    )
    return HttpUrlType.HttpUrlApp(httpUrl)
}