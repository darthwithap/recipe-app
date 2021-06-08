package me.darthwithap.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeCardItem(
    colors: List<Color>,
    cardHeight: Dp,
    padding: Dp,
    xTranslation: Float,
    yTranslation: Float,
    gradientWidth: Float
) {
    val brush = Brush.linearGradient(
        colors,
        start = Offset(xTranslation - gradientWidth / 2, yTranslation - gradientWidth / 2),
        end = Offset(xTranslation + gradientWidth / 2, yTranslation + gradientWidth / 2)
    )

    Column(modifier = Modifier.padding(padding)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(cardHeight)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(24.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}