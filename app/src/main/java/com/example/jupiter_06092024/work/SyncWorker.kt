package com.example.jupiter_06092024.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.util.Log
import com.example.jupiter_06092024.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Логика синхронизации данных
            Log.d("SyncWorker", "Начало синхронизации данных")

            withContext(Dispatchers.IO) {
                val viewModel = MainViewModel(applicationContext)
                viewModel.syncDatabase()  // Вызов метода синхронизации
            }

            Log.d("SyncWorker", "Синхронизация завершена успешно")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Ошибка при синхронизации данных: ${e.message}")
            Result.retry()
        }
    }
}
