package com.example.jupiter_06092024.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TableScreen(data: List<List<Any>>) {
    Column(Modifier) {
        LazyColumn {
            items(data) { row ->
                Text(row.joinToString(" | ")) // Отображение строки данных
            }
        }
    }
}
