package com.example.jupiter_06092024

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TableScreen(data: List<List<Any>>) {
    Column(modifier = Modifier.padding(16.dp)) {
        data.take(5).forEach { row ->
            Text(text = row.joinToString(", "), modifier = Modifier.padding(8.dp))
        }
    }
}
