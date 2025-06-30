package com.example.designsystemtemplate

import com.example.designsystemtemplate.network.ApiClient
import com.example.designsystemtemplate.network.ApiClientImpl
import com.example.designsystemtemplate.presentation.StoreFrontViewModel // Updated import
import io.ktor.client.engine.darwin.* // iOS specific engine

/**
 * Provides a ViewModel factory for iOS to instantiate [StoreFrontViewModel].
 * This factory ensures that the [StoreFrontViewModel] is created with the correct
 * platform-specific [ApiClient] (using Darwin engine for iOS).
 */
actual class ViewModelFactory {
    /**
     * Creates and returns an instance of [StoreFrontViewModel].
     *
     * @return A new instance of [StoreFrontViewModel].
     */
    actual fun createStoreFrontViewModel(): StoreFrontViewModel {
        // Initialize ApiClientImpl with the Darwin engine for iOS.
        val apiClient = ApiClientImpl(Darwin)
        return StoreFrontViewModel(apiClient)
    }
}
