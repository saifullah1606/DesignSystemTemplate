package com.example.designsystemtemplate.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * A base ViewModel class for Kotlin Multiplatform Mobile (KMM) projects.
 * It provides a [viewModelScope] for launching coroutines that are automatically
 * cancelled when the ViewModel is cleared (e.g., on Android's onCleared(), or when
 * the iOS lifecycle dictates).
 *
 * This helps in managing coroutine lifecycles and preventing memory leaks.
 */
abstract class KmmViewModel {

    /**
     * The [CoroutineScope] associated with this ViewModel.
     * It uses [Dispatchers.Main] for UI-related coroutines and a [SupervisorJob]
     * to ensure that child coroutine failures do not cancel the entire scope.
     */
    val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /**
     * Called when the ViewModel is no longer needed and is being cleared.
     * This function cancels all coroutines launched within the [viewModelScope],
     * releasing any resources held by them.
     */
    fun clear() {
        viewModelScope.cancel()
    }
}
