package com.example.fetchrewardassessment.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define the custom colors
private val PrimaryColor = Color(0xFFDAA89B) // #DAA89B
private val SecondaryColor = Color(0xFFAE847E) // #AE847E
private val BackgroundColor = Color(0xFF2C0E37) // #2C0E37
private val AccentColor = Color(0xFFCB429F) // #CB429F

// Define light color scheme
private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.Black,
    tertiary = AccentColor
)

// Define dark color scheme
private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    tertiary = AccentColor
)

@Composable
fun FetchRewardAssessmentTheme(
    darkTheme: Boolean = false, // Switch to true for dark mode
    content: @Composable () -> Unit
) {
    val colors: ColorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
//        shapes = Shapes,
        content = content
    )
}
