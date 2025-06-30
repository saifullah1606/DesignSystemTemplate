package com.example.designsystemtemplate.presentation

import com.example.designsystemtemplate.data.MainCategory

/**
 * A sealed interface representing the different UI states for store front data.
 * This helps to manage the rendering logic based on the data fetching process.
 */
sealed class StoreFrontUiState {
    /**
     * Represents the initial or loading state, indicating that data is being fetched.
     */
    data object Loading : StoreFrontUiState()

    /**
     * Represents the success state, containing the fetched list of main categories.
     * @property mainCategories The list of [MainCategory] objects successfully retrieved.
     */
    data class Success(val mainCategories: List<MainCategory>) : StoreFrontUiState()

    /**
     * Represents an error state, containing an error message.
     * @property message The error message describing what went wrong.
     */
    data class Error(val message: String) : StoreFrontUiState()
}
