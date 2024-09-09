package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme
import com.example.jupiter_06092024.ui.WelcomeScreen
import com.example.jupiter_06092024.ui.TableScreen
import com.example.jupiter_06092024.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JupiterApp()
        }
    }
}

@Composable
fun JupiterApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable(route = "welcome") {
            WelcomeScreen(
                onContinue = { navController.navigate(route = "table") },
                loadingPercentage = 0.8f // Замените на динамическое значение
            )
        }
        composable(route = "table") {
            val viewModel: MainViewModel = viewModel()
            TableScreen(data = viewModel.sheetData.value)

        }
    }
}
