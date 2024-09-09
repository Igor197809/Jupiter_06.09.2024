package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme
import kotlinx.coroutines.launch

import com.example.jupiter_06092024.ui.WelcomeScreen


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
        composable("welcome") {
            WelcomeScreen(onContinue = { navController.navigate("table") })
        }
        composable("table") {
            val viewModel: MainViewModel = viewModel()
            TableScreen(data = viewModel.sheetData)
        }
    }
}
