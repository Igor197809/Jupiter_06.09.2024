package com.example.jupiter_06092024.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TableScreen(data: List<List<Any>>) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (data.isNotEmpty()) {
            data.take(5).forEach { row ->
                Text(text = row.joinToString(separator = ", "))
            }
        } else {
            Text(text = "Данные не найдены.")
        }
    }
}
