package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jupiter_06092024.ui.WelcomeScreen
import com.example.jupiter_06092024.ui.TableScreen
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme
import androidx.work.*
import com.example.jupiter_06092024.work.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jupiter_06092024Theme {
                val viewModel: MainViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory(application))
                val navController = rememberNavController()

                NavHost(navController, startDestination = "welcome") {
                    composable("welcome") {
                        val loadingPercentage = viewModel.loadingProgress.observeAsState(0f)
                        WelcomeScreen(
                            onContinue = { navController.navigate("table") },
                            loadingPercentage = loadingPercentage.value
                        )
                    }
                    composable("table") {
                        val data = viewModel.sheetData.observeAsState(emptyMap())
                        val sheetData = data.value["Sheet1"] // Получаем данные по ключу вкладки
                        TableScreen(data = sheetData ?: emptyList()) // Передаем данные или пустой список
                    }
                }
            }
        }

        // Запуск WorkManager для фоновой синхронизации
        scheduleBackgroundSync()
    }

    private fun scheduleBackgroundSync() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
}
