package me.darthwithap.recipeapp.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    onPrimary = White,
    secondary = Secondary,
    secondaryVariant = SecondaryVariant,
    onSecondary = Black,
    error = ErrorRedDark,
    onError = White,
    background = FaintGray,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

private val DarkThemeColors = darkColors(
    primary = Pink300,
    primaryVariant = PrimaryVariant,
    onPrimary = Black,
    secondary = Secondary,
    secondaryVariant = SecondaryVariant,
    onSecondary = Black,
    error = ErrorRedLight,
    onError = Black,
    background = JustBlack,
    onBackground = White,
    surface = JustBlack,
    onSurface = White
)

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        content()
    }
}