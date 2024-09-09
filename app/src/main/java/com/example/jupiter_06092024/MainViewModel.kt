package com.example.jupiter_06092024

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(context: Context) : ViewModel() {

    private val _loadingProgress = MutableLiveData<Float>()
    val loadingProgress: LiveData<Float> = _loadingProgress

    private val _sheetData = MutableLiveData<List<List<Any>>>()
    val sheetData: LiveData<List<List<Any>>> = _sheetData

    init {
        Log.e("MainViewModel", "Creating MainViewModel")
        viewModelScope.launch {
            loadDataFromGoogleSheets()  // Длительные задачи выполняются в фоновом потоке
        }
    }

    private suspend fun loadDataFromGoogleSheets() {
        withContext(Dispatchers.IO) {
            Log.d("MainViewModel", "Loading data from Google Sheets")

            // Эмулируем процесс загрузки с обновлением прогресса
            _loadingProgress.postValue(0.1f) // 10% загрузки
            delay(1000) // Эмуляция задержки

            _loadingProgress.postValue(0.5f) // 50% загрузки
            delay(1000) // Эмуляция задержки

            // Завершаем загрузку
            _sheetData.postValue(
                listOf(
                    listOf("Row1Column1", "Row1Column2"),
                    listOf("Row2Column1", "Row2Column2")
                )
            )
            _loadingProgress.postValue(1.0f) // 100% загрузки
        }
    }
}
