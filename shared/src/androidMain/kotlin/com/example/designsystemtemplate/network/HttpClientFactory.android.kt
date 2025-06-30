package com.example.designsystemtemplate.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

actual fun createNetworkClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    val okHttpEngine: HttpClientEngine = OkHttp.create {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(15, TimeUnit.SECONDS)
            callTimeout(30, TimeUnit.SECONDS)
        }
    }

    return HttpClient(okHttpEngine) {
        applyCommonConfig(enableLogging = true)
        config()
    }
}