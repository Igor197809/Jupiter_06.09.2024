package com.example.jupiter_06092024

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background



@Composable
fun WelcomeScreen(onContinue: () -> Unit, loadingPercentage: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),  // Установка черного фона
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

            Text(text = "Загрузка данных...", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = loadingPercentage,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onContinue) {
                Text(text = "Продолжить")
            }
        }
    }
}