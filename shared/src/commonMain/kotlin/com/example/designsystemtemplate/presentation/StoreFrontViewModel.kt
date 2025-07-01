package com.example.designsystemtemplate.presentation

import com.example.designsystemtemplate.network.ApiClient
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoreFrontViewModel(private val apiClient: ApiClient) :
    ViewModel() {

    private val _uiState = MutableStateFlow<StoreFrontUiState>(StoreFrontUiState.Loading)
    val uiState: StateFlow<StoreFrontUiState> = _uiState.asStateFlow()

    init {
        loadStoreFrontData()
    }

    fun loadStoreFrontData() {
        viewModelScope.launch {
            _uiState.value = StoreFrontUiState.Loading // Set state to Loading
            try {
                val response = apiClient.getStoreFrontData()
                _uiState.value = StoreFrontUiState.Success(response.mainCategoryList)
            } catch (e: Exception) {
                _uiState.value =
                    StoreFrontUiState.Error("Failed to load store front data: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}