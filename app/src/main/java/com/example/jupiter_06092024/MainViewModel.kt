package com.example.jupiter_06092024

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingProgress = MutableLiveData(0f)
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetNames = MutableLiveData<List<String>>()
    val sheetNames: LiveData<List<String>> = _sheetNames

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val names = withTimeoutOrNull(5000) {
                getSheetNames()
            }

            if (names != null) {
                _sheetNames.postValue(names)
                _loadingProgress.postValue(1f) // Загрузка завершена
            } else {
                // Обработка ошибки или использование кэшированных данных
                _sheetNames.postValue(emptyList()) // Кэширование пока не реализовано
            }
        }
    }

    // Функция для получения имен листов в Google Sheets API
    private suspend fun getSheetNames(): List<String> {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        // Получаем OAuth 2.0 учетные данные
        val credentials = getCredentials(getApplication(), "jupiter_credentials.json")

        // Преобразуем GoogleCredentials в HttpRequestInitializer через HttpCredentialsAdapter
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"

        // Запрашиваем информацию о всех листах
        val spreadsheet = service.spreadsheets().get(spreadsheetId).execute()

        // Получаем имена листов
        return spreadsheet.sheets.map { it.properties.title }
    }

    // Функция для получения OAuth 2.0 учетных данных с использованием GoogleCredentials
    private fun getCredentials(context: android.content.Context, credentialsFilePath: String): GoogleCredentials {
        val stream = context.assets.open(credentialsFilePath)  // Убедитесь, что файл jupiter_credentials.json находится в assets
        return GoogleCredentials.fromStream(stream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets.readonly"))
    }
}
