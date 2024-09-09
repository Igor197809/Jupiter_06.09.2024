package com.example.jupiter_06092024

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.jupiter_06092024.ui.TableScreen
import com.example.jupiter_06092024.ui.WelcomeScreen
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jupiter_06092024Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable(route = "welcome") {
                        WelcomeScreen(
                            onContinue = { navController.navigate(route = "table") },
                            loadingPercentage = 0.8f // замените на динамическое значение
                        )
                    }
                    composable(route = "table") {
                        TableScreen(data = viewModel.sheetData.value)
                    }
                }
            }
        }
    }
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
