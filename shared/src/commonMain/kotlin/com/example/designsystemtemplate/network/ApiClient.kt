package com.example.designsystemtemplate.network

import com.example.designsystemtemplate.data.StoreFrontResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface ApiClient {
    suspend fun getStoreFrontData(): StoreFrontResponse
}

class ApiClientImpl(private val httpClient: HttpClient) : ApiClient {

    // Base URL for the API
    private val BASE_URL = "https://apwat.virginmobile.sa:4447"

    /**
     * Suspended function to fetch store front data from the API.
     * It makes a GET request and expects a [StoreFrontResponse] object.
     * @return [StoreFrontResponse] containing main categories and their nested data.
     * @throws Exception if the network request fails.
     */
    override suspend fun getStoreFrontData(): StoreFrontResponse {
        return httpClient.get {
            // No specific path needed here as the full URL is set in defaultRequest
        }.body() // Deserialize the response body to StoreFrontResponse
    }
}
