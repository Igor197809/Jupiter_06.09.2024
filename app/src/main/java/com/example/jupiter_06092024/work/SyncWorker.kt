package com.example.jupiter_06092024.work

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.jupiter_06092024.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params), KoinComponent {

    private val viewModel: MainViewModel by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Запуск синхронизации базы данных
                viewModel.syncDatabase()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}
