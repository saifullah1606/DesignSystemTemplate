package com.example.designsystemtemplate.android.storefront

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystemtemplate.android.MyApplicationTheme
import com.example.designsystemtemplate.data.MainCategory
import com.example.designsystemtemplate.data.SubCategory
import com.example.designsystemtemplate.presentation.StoreFrontUiState
import com.example.designsystemtemplate.presentation.StoreFrontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreFrontScreen(
    uiState: StoreFrontUiState,
    viewModel: StoreFrontViewModel
) {
    var selectedCategoryId by rememberSaveable { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ktor KMM Store Front", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (uiState) {
                is StoreFrontUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading store front data...", fontSize = 18.sp)
                }

                is StoreFrontUiState.Success -> {
                    val mainCategories = uiState.mainCategories

                    if (mainCategories.isEmpty()) {
                        Text("No main categories found.", fontSize = 18.sp)
                    } else {
                        if (selectedCategoryId == null) {
                            selectedCategoryId = mainCategories.first().id
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            items(mainCategories) { category ->
                                CategoryChip(
                                    category = category,
                                    isSelected = category.id == selectedCategoryId,
                                    onClick = { selectedCategoryId = category.id }
                                )
                            }
                        }

                        val selectedCategory = mainCategories.find { it.id == selectedCategoryId }

                        selectedCategory?.subCategoryList?.let { subcategories ->
                            if (subcategories.isEmpty()) {
                                Text("No subcategories available.", fontSize = 16.sp)
                            } else {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.padding(top = 16.dp)
                                ) {
                                    items(subcategories) { subCategory ->
                                        SubCategoryCard(subCategory = subCategory)
                                    }
                                }
                            }
                        }
                    }
                }

                is StoreFrontUiState.Error -> {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Button(onClick = { viewModel.loadStoreFrontData() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreFrontScreenPreview() {
    MyApplicationTheme {
        StoreFrontScreen(
            uiState = StoreFrontUiState.Success(
                listOf(
                    MainCategory(
                        id = 1063,
                        name = "Postpaid",
                        order = 1,
                        subCategoryList = listOf(
                            SubCategory(
                                id = 1068,
                                name = "Switch Postpaid Plans",
                                order = 1,
                                cards = listOf()
                            ),
                            SubCategory(
                                id = 1065,
                                name = "Postpaid plans",
                                order = 2,
                                cards = listOf() // Simplified, as CardItem is complex
                            )
                        )
                    ),
                    MainCategory(
                        id = 1062,
                        name = "Prepaid",
                        order = 2,
                        subCategoryList = listOf(
                            SubCategory(
                                id = 32,
                                name = "Handpicked plans",
                                order = 1,
                                cards = listOf()
                            )
                        )
                    )
                )
            ),
            viewModel = StoreFrontViewModel(object :
                com.example.designsystemtemplate.network.ApiClient { // Mock ApiClient for preview
                override suspend fun getStoreFrontData() =
                    com.example.designsystemtemplate.data.StoreFrontResponse(emptyList())
            })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StoreFrontScreenLoadingPreview() {
    MyApplicationTheme {
        StoreFrontScreen(
            uiState = StoreFrontUiState.Loading,
            viewModel = StoreFrontViewModel(object :
                com.example.designsystemtemplate.network.ApiClient { // Mock ApiClient for preview
                override suspend fun getStoreFrontData() =
                    com.example.designsystemtemplate.data.StoreFrontResponse(emptyList())
            })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StoreFrontScreenErrorPreview() {
    MyApplicationTheme {
        StoreFrontScreen(
            uiState = StoreFrontUiState.Error("Failed to fetch store front data."),
            viewModel = StoreFrontViewModel(object :
                com.example.designsystemtemplate.network.ApiClient { // Mock ApiClient for preview
                override suspend fun getStoreFrontData() =
                    com.example.designsystemtemplate.data.StoreFrontResponse(emptyList())
            })
        )
    }
}
