package com.example.jupiter_06092024.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TableScreen(data: List<List<Any>>) {
    Log.d("TableScreen", "TableScreen запущен с данными: $data")
    Column {
        data.forEach { row ->
            Text(text = row.joinToString(", "))
        }
    }
}
