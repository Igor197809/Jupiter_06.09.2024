package com.example.jupiter_06092024.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text // Изменен импорт на стабильную версию material
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jupiter_06092024.R

@Composable
fun WelcomeScreen(onContinue: () -> Unit, loadingPercentage: Float) {
    Log.d("WelcomeScreen", "WelcomeScreen запущен с процентом загрузки: $loadingPercentage")

    var hasNavigated by remember { mutableStateOf(false) } // Флаг для предотвращения многократной навигации

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_start),
                contentDescription = "Логотип",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            )
            Log.d("WelcomeScreen", "Логотип отображен")

            Text(text = "Загрузка данных...", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Загрузка на ${loadingPercentage * 100}%", color = Color.White)
            Log.d("WelcomeScreen", "Статус загрузки обновлен: ${loadingPercentage * 100}%")
        }
    }

    // Когда процент загрузки достиг 100%, остановите загрузку и выполните переход
    if (loadingPercentage >= 1.0f && !hasNavigated) {
        Log.d("WelcomeScreen", "Загрузка завершена, выполнение onContinue()")
        hasNavigated = true // Переход выполнен, повторный вызов запрещён
        onContinue()
    } else {
        Log.d("WelcomeScreen", "Загрузка продолжается")
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onContinue = {}, loadingPercentage = 0.5f)
}
