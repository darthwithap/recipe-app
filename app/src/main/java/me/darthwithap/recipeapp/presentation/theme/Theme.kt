package me.darthwithap.recipeapp.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.darthwithap.recipeapp.presentation.components.CircularIndeterminateProgressBar
import me.darthwithap.recipeapp.presentation.components.DefaultSnackbar

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
    isDark: Boolean,
    isProgressBarShowing: Boolean,
    scaffoldState: ScaffoldState,
    snackbarAlignment: Alignment,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDark) DarkThemeColors else LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (isDark) JustBlack else FaintGray
                )
        ) {
            content()
            CircularIndeterminateProgressBar(isProgressBarShowing, 0.3f)
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier.align(snackbarAlignment)
            ) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}