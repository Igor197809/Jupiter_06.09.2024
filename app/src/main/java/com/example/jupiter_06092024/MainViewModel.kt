package com.example.jupiter_06092024

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.io.InputStream

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
                _sheetData.postValue(emptyList()) // Обработка ошибки или использование кэшированных данных
            }
        }
    }

    // Пример функции для доступа к Google Sheets
    private suspend fun accessGoogleSheet(): List<List<Any>> {
        val httpTransport = NetHttpTransport() // Используем стандартный HTTP транспорт
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val credentials = getCredentials(getApplication(), "jupiter_credentials.json")

        // Используем HttpCredentialsAdapter для преобразования GoogleCredentials в HttpRequestInitializer
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = com.google.api.services.sheets.v4.Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
        val range = "'ПК 882+37-1100+00'!A1:N21"  // Ensure this matches your sheet's name and cell range


        val response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()

        return response.getValues() ?: emptyList()
    }

    // Функция для получения OAuth 2.0 учетных данных
    private fun getCredentials(context: Context, credentialsFilePath: String): GoogleCredentials {
        val stream: InputStream = context.assets.open(credentialsFilePath)
        return GoogleCredentials.fromStream(stream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets.readonly"))
    }
}
