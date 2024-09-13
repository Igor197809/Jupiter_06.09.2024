package com.example.jupiter_06092024

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Приватная переменная для хранения данных
    private val _sheetData = MutableLiveData<List<String>>()

    // Публичная переменная LiveData для наблюдения
    val sheetData: LiveData<List<String>> get() = _sheetData

    // Инициализация Jackson и Google API
    private val jsonFactory = JacksonFactory.getDefaultInstance()
    private val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

    // Загрузка данных с Google Sheets API
    fun loadSheetData() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = fetchDataFromGoogleSheets()  // Вызов функции для загрузки данных
            _sheetData.postValue(data)
        }
    }

    // Пример функции для получения данных с Google Sheets
    private fun fetchDataFromGoogleSheets(): List<String> {
        // Здесь должна быть логика загрузки данных
        return listOf("Data 1", "Data 2", "Data 3")
    }
}
