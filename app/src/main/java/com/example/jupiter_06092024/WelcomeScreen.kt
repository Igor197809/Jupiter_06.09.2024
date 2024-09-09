package com.example.jupiter_06092024.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jupiter_06092024.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


@Composable
fun WelcomeScreen(onContinue: () -> Unit, loadingPercentage: Float) {
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
            Text(text = "Загрузка данных...", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Загрузка на $loadingPercentage%", color = Color.White)
        }
    }
}
