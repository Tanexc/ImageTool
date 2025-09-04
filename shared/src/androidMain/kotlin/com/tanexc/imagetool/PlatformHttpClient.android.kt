package com.tanexc.imagetool

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

actual fun PlatformHttpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config()
}