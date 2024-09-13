package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()  // Убедитесь, что MainViewModel импортирован

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.sheetData.observe(this, Observer { sheetData ->
            if (sheetData != null && sheetData.isNotEmpty()) {
                println("Данные листа: ${sheetData.joinToString()}")
            } else {
                println("Данные не найдены.")
            }
        })
    }
}
