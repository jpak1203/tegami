package com.jpakku.tegami.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = MainColor,
    onPrimary = SecondaryColor,
    secondary = SecondaryColor,
    onSecondary = MainColor,
    tertiary = AccentColor,
    onTertiary = SecondaryColor,
    onBackground = SecondaryColor,
    outline = SecondaryColor,
    onSurface = SecondaryColor,
    error = ErrorRed,
    onError = SecondaryColor

)

private val LightColorScheme = lightColorScheme(
    primary = MainColor,
    onPrimary = SecondaryColor,
    secondary = SecondaryColor,
    onSecondary = MainColor,
    tertiary = AccentColor,
    onTertiary = SecondaryColor,
    background = SecondaryColor,
    onBackground = Color.Black,
    outline = MainColor,
    surface = SecondaryColor,
    onSurface = MainColor,
    error = ErrorRed,
    onError = SecondaryColor
)

@Composable
fun TegamiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            false
        )
    } else{
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            true
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}