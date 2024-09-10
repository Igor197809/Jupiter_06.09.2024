package com.example.jupiter_06092024.ui

import androidx.compose.foundation.layout.* // Для работы с Layout
import androidx.compose.foundation.lazy.LazyColumn // Для ленивой загрузки данных
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text // Импортируем Text из material
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp // Для отступов

@Composable
fun TableScreen(data: List<List<Any>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(data) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                row.forEach { cell ->
                    Text(
                        text = cell.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
