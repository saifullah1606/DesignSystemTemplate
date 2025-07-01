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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystemtemplate.android.storefront.StoreFrontScreen
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