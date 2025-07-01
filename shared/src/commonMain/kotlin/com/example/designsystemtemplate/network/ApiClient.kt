package com.example.designsystemtemplate.network

import com.example.designsystemtemplate.data.StoreFrontResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header

interface ApiClient {
    suspend fun getStoreFrontData(): StoreFrontResponse
}

const val BASE_URL = "https://apwat.virginmobile.sa:4447"

class ApiClientImpl(private val httpClient: HttpClient) : ApiClient {

    private val configuredClient = httpClient.config {
        defaultRequest {
            url("$BASE_URL")
            header("Virgin-Is-Push-Notification-Blocked-By-User", "false")
            header("virgin-brand", "virgin")
            header("Virgin-Device-Id", "1EA759C3-60F0-4756-BC4D-D94801554B13")
            header("Accept-Version", "v26")
            header("Virgin-Device-Os", "iOS")
            header("Accept", "*/*")
            header("Accept-Language", "en-PK;q=1.0, ur-PK;q=0.9")
            header("Accept-Encoding", "br;q=1.0, gzip;q=0.9, deflate;q=0.8")
            header("Virgin-App-Version", "4.3.0")
            header("Session-Id", "19c914aa-f7a3-4a4c-b614-8d5d4d6dafac")
            header(
                "User-Agent",
                "Virgin Mobile/4.3.0 (sa.virginmobile.vm; build:1280; iOS 18.4.0) Alamofire/5.4.4"
            )
            header("Virgin-Api-Version", "21.0.0")
            header("Virgin-Product-Code", "1")
            header(
                "Cookie",
                "TS01e7631c=01ea17b3e381f437d62f33b4594dc29b330f3ddfa617837014d0c72db01539a0a342133b29220051030b4bf40cf196df113abf7e47"
            )
            header("Virgin-Lang", "en")
        }
    }

    override suspend fun getStoreFrontData(): StoreFrontResponse {
        return configuredClient.get("store_front/categories.json") {}.body()
    }
}
