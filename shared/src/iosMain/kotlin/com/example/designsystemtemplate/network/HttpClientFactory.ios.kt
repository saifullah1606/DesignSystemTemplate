package com.example.designsystemtemplate.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin


actual fun createNetworkClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    val darwinEngine = Darwin.create {
        //ios specific configs
        configureSession {
            allowsCellularAccess = true
            timeoutIntervalForRequest = 15.0
            timeoutIntervalForResource = 30.0
        }
    }

    return HttpClient(darwinEngine) {
        applyCommonConfig(enableLogging = true)
        config()
    }
}

