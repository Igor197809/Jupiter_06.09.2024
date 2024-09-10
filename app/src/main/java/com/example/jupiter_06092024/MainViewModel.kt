package com.example.jupiter_06092024

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import kotlinx.coroutines.*
import java.io.InputStream
import com.google.auth.http.HttpCredentialsAdapter

class MainViewModel(private val context: Context) : ViewModel() {

    private val _loadingProgress = MutableLiveData<Float>()
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetData = MutableLiveData<Map<String, List<List<Any>>>>()
    val sheetData: LiveData<Map<String, List<List<Any>>>> = _sheetData

    private val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"

    init {
        viewModelScope.launch {
            syncDatabase()
        }
    }

    suspend fun syncDatabase() {
        withContext(Dispatchers.IO) {
            try {
                val tabs = listOf("Sheet1!A1:D10", "Sheet2!A1:D10")
                val credentialsStream: InputStream = context.assets.open("jupiter_credentials.json")
                val credential = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))

                val sheetsService = Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    HttpCredentialsAdapter(credential)  // Преобразование в HttpRequestInitializer
                ).setApplicationName("Jupiter App")
                    .build()

                val data = mutableMapOf<String, List<List<Any>>>()

                withTimeoutOrNull(5000L) {
                    tabs.forEachIndexed { index, tab ->
                        val response = sheetsService.spreadsheets().values()
                            .get(spreadsheetId, tab)
                            .execute()
                        val values = response.getValues()
                        if (values != null) {
                            data[tab] = values
                            _loadingProgress.postValue((index + 1) / tabs.size.toFloat())
                        }
                    }
                }

                if (data.isNotEmpty()) {
                    _sheetData.postValue(data)
                    Log.d("MainViewModel", "Данные загружены успешно")
                } else {
                    Log.e("MainViewModel", "Не удалось загрузить данные за 5 секунд, используем локальные данные")
                    loadFromLocalDatabase()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка синхронизации: ${e.message}")
                loadFromLocalDatabase()
            }
        }
    }

    private fun loadFromLocalDatabase() {
        Log.d("MainViewModel", "Загрузка данных из локальной базы")
    }
}
