package com.tanexc.imagetool

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun PlatformHttpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Darwin) {
    config()
}