package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

class MainViewModel : ViewModel() {
    var sheetData by mutableStateOf<List<List<Any>>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = accessGoogleSheet()
            withContext(Dispatchers.Main) {
                sheetData = data
            }
        }
    }

    private suspend fun accessGoogleSheet(): List<List<Any>> {
        // Логика доступа к Google Sheets
        return emptyList() // замените реальной логикой
    }
}
