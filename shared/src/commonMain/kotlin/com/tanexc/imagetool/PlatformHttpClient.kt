package com.tanexc.imagetool

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun PlatformHttpClient(
    config: HttpClientConfig<*>.() -> Unit = {}
): HttpClient