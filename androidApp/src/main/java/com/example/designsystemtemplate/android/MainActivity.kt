package com.example.designsystemtemplate.android

//import androidx.lifecycle.ViewModelProvider
//import com.example.designsystemtemplate.ViewModelFactory
//import com.example.designsystemtemplate.data.MainCategory
//import com.example.designsystemtemplate.data.SubCategory
//import com.example.designsystemtemplate.presentation.StoreFrontUiState
//import com.example.designsystemtemplate.presentation.StoreFrontViewModel

//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.designsystemtemplate.Greeting

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    GreetingView(Greeting().greet())
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun GreetingView(text: String) {
//    Text(text = text)
//}
//
//@Preview
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        GreetingView("Hello, Android!")
//    }
//}


//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystemtemplate.data.MainCategory
import com.example.designsystemtemplate.data.SubCategory
import com.example.designsystemtemplate.presentation.StoreFrontUiState
import com.example.designsystemtemplate.presentation.StoreFrontViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: StoreFrontViewModel = koinViewModel()
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    StoreFrontScreen(uiState = uiState, viewModel = viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreFrontScreen(
    uiState: StoreFrontUiState,
    viewModel: StoreFrontViewModel
) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState) {
                is StoreFrontUiState.Loading -> {
                    // Display a progress indicator while loading
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading store front data...", fontSize = 18.sp)
                }

                is StoreFrontUiState.Success -> {
                    // Display the list of main categories
                    if (uiState.mainCategories.isEmpty()) {
                        Text("No main categories found.", fontSize = 18.sp)
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.mainCategories) { mainCategory ->
                                MainCategoryCard(
                                    mainCategory = mainCategory,
                                    viewModel = viewModel
                                ) // Pass viewModel
                            }
                        }
                    }
                }

                is StoreFrontUiState.Error -> {
                    // Display an error message
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
                    // Add a retry button if needed, using the viewModel
                    Button(onClick = { viewModel.loadStoreFrontData() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}


@Composable
fun MainCategoryCard(
    mainCategory: MainCategory,
    viewModel: StoreFrontViewModel
) { // Add viewModel parameter
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = {
                    Text(
                        text = mainCategory.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                supportingContent = {
                    mainCategory.deepLink?.let { link ->
                        Text("Link: $link", fontSize = 12.sp)
                    }
                },
                trailingContent = {
                    // Optional expand/collapse icon if there are subcategories
                    if (!mainCategory.subCategoryList.isNullOrEmpty()) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !mainCategory.subCategoryList.isNullOrEmpty()) {
                        expanded = !expanded
                    }
            )

            // Conditionally display subcategories if expanded
            if (expanded && !mainCategory.subCategoryList.isNullOrEmpty()) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                )
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                    mainCategory.subCategoryList!!.forEach { subCategory ->
                        // If SubCategoryItem also needs viewModel, pass it here
                        SubCategoryItem(subCategory = subCategory)
                    }
                }
            }
        }
    }
}


@Composable
fun SubCategoryItem(subCategory: SubCategory) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = subCategory.name ?: "Unnamed Subcategory",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        subCategory.description?.let { desc ->
            Text(
                text = desc,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
        // You can add more UI elements here to display cards within subcategories
        // For simplicity, we'll just show a count of cards for now.
        subCategory.cards?.let { cards ->
            if (cards.isNotEmpty()) {
                Text(
                    text = "Cards: ${cards.size}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
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
