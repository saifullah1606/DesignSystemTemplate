package com.example.designsystemtemplate

import com.example.designsystemtemplate.network.ApiClient
import com.example.designsystemtemplate.network.ApiClientImpl
import com.example.designsystemtemplate.presentation.StoreFrontViewModel
import io.ktor.client.engine.okhttp.OkHttp

/**
 * Provides a ViewModel factory for Android to instantiate [StoreFrontViewModel].
 * This factory ensures that the [StoreFrontViewModel] is created with the correct
 * platform-specific [ApiClient] (using OkHttp engine for Android).
 */
//actual class ViewModelFactory {
//    /**
//     * Creates and returns an instance of [StoreFrontViewModel].
//     *
//     * @return A new instance of [StoreFrontViewModel].
//     */
//    actual fun createStoreFrontViewModel(): StoreFrontViewModel {
//        // Initialize ApiClientImpl with the OkHttp engine for Android.
////        val apiClient = ApiClientImpl(OkHttp)
////        return StoreFrontViewModel(apiClient)
//    }
//}
