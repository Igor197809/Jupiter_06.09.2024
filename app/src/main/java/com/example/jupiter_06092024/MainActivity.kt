package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    // Получаем ViewModel через delegate viewModels
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Загружаем данные с Google Sheets API
        viewModel.loadSheetData()

        // Наблюдаем за данными листов (sheetData) и обновляем UI при изменениях
        viewModel.sheetData.observe(this, Observer<List<String>> { sheetData ->
            if (sheetData != null && sheetData.isNotEmpty()) {
                println("Данные листа: ${sheetData.joinToString()}")
            } else {
                println("Данные не найдены.")
            }
        })
    }
}
