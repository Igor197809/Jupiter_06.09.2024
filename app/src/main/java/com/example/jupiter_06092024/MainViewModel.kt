package com.example.jupiter_06092024

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingProgress = MutableLiveData(0f)
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetData = MutableLiveData<List<List<Any>>>()
    val sheetData: LiveData<List<List<Any>>> = _sheetData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = withTimeoutOrNull(5000) { // Таймаут на 5 секунд
                accessGoogleSheet()
            }

            if (data != null) {
                _sheetData.postValue(data)
                _loadingProgress.postValue(1f) // Загрузка завершена
            } else {
                // Обработка ошибки или использование кэшированных данных
                _sheetData.postValue(emptyList()) // Кэширование пока не реализовано
            }
        }
    }

    // Пример функции для доступа к Google Sheets
    private suspend fun accessGoogleSheet(): List<List<Any>> {
        val httpTransport = com.google.api.client.http.javanet.NetHttpTransport()
        val jsonFactory = com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance()
        val credentials = getCredentials(getApplication(), "jupiter_credentials.json")

        val service = com.google.api.services.sheets.v4.Sheets.Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
        val range = "Sheet1!A1:N20" // Пример диапазона, который нужно загрузить

        val response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()

        return response.getValues() ?: emptyList()
    }

    // Функция для получения OAuth 2.0 учетных данных
    private fun getCredentials(context: android.content.Context, credentialsFilePath: String): GoogleCredential {
        val stream = context.assets.open(credentialsFilePath)  // Убедитесь, что файл jupiter_credentials.json находится в assets
        return GoogleCredential.fromStream(stream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets.readonly"))
    }
}
