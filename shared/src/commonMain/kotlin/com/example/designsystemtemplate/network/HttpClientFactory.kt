package com.example.designsystemtemplate.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//expect object PlatformLogger {
//    fun log(message: String)
//}

expect fun createNetworkClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

// Common configuration extension
fun HttpClientConfig<*>.applyCommonConfig(
    enableLogging: Boolean = true
) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }

    if (enableLogging) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }
}