package com.example.jupiter_06092024

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MainViewModel(context: Context) : ViewModel() {

    private val _loadingProgress = MutableLiveData<Float>()
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetData = MutableLiveData<List<List<Any>>>()
    val sheetData: LiveData<List<List<Any>>> = _sheetData

    private val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
    private val range = "Sheet1!A1:D10" // Диапазон данных, которые мы хотим загрузить

    init {
        Log.e("MainViewModel", "Creating MainViewModel")
        viewModelScope.launch {
            loadDataFromGoogleSheets(context)
        }
    }

    private suspend fun loadDataFromGoogleSheets(context: Context) {
        withContext(Dispatchers.IO) {
            Log.d("MainViewModel", "Loading data from Google Sheets")

            // Чтение учетных данных из assets
            val credentialsStream: InputStream = context.assets.open("jupiter_credentials.json")
            val credential = GoogleCredential.fromStream(credentialsStream)
                .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))

            // Инициализация Google Sheets API
            val sheetsService = Sheets.Builder(
                AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
            )
                .setApplicationName("Jupiter App")
                .build()

            // Запрос к Google Sheets API
            val response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()

            val values = response.getValues()

            if (values != null && values.isNotEmpty()) {
                Log.d("MainViewModel", "Data retrieved successfully")
                _sheetData.postValue(values)
            } else {
                Log.e("MainViewModel", "No data found")
            }

            _loadingProgress.postValue(1.0f) // Загрузка завершена
        }
    }
}
