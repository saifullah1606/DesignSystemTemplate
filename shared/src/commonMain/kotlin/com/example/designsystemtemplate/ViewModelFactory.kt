package com.example.designsystemtemplate

import com.example.designsystemtemplate.presentation.StoreFrontViewModel

/**
 * Provides a ViewModel factory for iOS to instantiate [StoreFrontViewModel].
 * This factory ensures that the [StoreFrontViewModel] is created with the correct
 * platform-specific [ApiClient] (using Darwin engine for iOS).
 */
//expect class ViewModelFactory {
//    /**
//     * Creates and returns an instance of [StoreFrontViewModel].
//     *
//     * @return A new instance of [StoreFrontViewModel].
//     */
//    fun createStoreFrontViewModel(): StoreFrontViewModel
//}