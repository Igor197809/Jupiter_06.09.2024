package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.*
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme
import com.example.jupiter_06092024.work.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jupiter_06092024Theme {
                // Здесь размещен код для загрузки экрана и навигации
            }
        }

        // Запуск WorkManager для фоновой синхронизации
        scheduleBackgroundSync()
    }

    // Функция для запуска WorkManager
    private fun scheduleBackgroundSync() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS) // Интервал синхронизации 1 час
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // Синхронизация только при доступе к интернету
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWork",
            ExistingPeriodicWorkPolicy.KEEP, // Не перезапускать если уже выполняется
            syncRequest
        )
    }
}
