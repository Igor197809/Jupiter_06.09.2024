package com.example.jupiter_06092024

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class MainViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val _loadingProgress = MutableLiveData(0f)
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetData = MutableLiveData<Map<String, List<List<Any>>>>() // Изменено на List<List<Any>>
    val sheetData: LiveData<Map<String, List<List<Any>>>> = _sheetData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = withTimeoutOrNull(5000) { // Таймаут на 5 секунд
                // Симуляция загрузки данных
                fetchDataFromGoogleSheets()
            }

            if (data != null) {
                _sheetData.postValue(data)
                _loadingProgress.postValue(1f) // Загрузка завершена
            } else {
                // Загрузка кэшированных данных
                val cachedData = loadCachedData()
                _sheetData.postValue(cachedData)
            }
        }
    }

    private suspend fun fetchDataFromGoogleSheets(): Map<String, List<List<Any>>> {
        // Здесь должна быть реализация загрузки данных с Google Sheets
        return mapOf("Sheet1" to listOf(
            listOf("Данные 1", "Данные 2", "Данные 3") as List<Any>,  // Пример данных
            listOf("Данные 4", "Данные 5", "Данные 6") as List<Any>
        ))
    }

    private fun loadCachedData(): Map<String, List<List<Any>>> {
        // Логика загрузки кэшированных данных
        return mapOf("Sheet1" to listOf(
            listOf("Кэшированные данные 1", "Кэшированные данные 2") as List<Any>
        ))
    }
}
