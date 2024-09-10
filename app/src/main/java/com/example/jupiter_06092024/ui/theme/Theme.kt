package com.example.jupiter_06092024.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

// Импорты цветов и шейпов
import com.example.jupiter_06092024.ui.theme.Purple200
import com.example.jupiter_06092024.ui.theme.Purple500
import com.example.jupiter_06092024.ui.theme.Purple700
import com.example.jupiter_06092024.ui.theme.Teal200
import com.example.jupiter_06092024.ui.theme.Shapes

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun Jupiter_06092024Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes, // Теперь Shapes определен
        content = content
    )
}
