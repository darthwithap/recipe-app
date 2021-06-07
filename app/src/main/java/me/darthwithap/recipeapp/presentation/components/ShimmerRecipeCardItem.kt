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
    translation: Float
) {
    val brush = Brush.linearGradient(
        colors,
        start = Offset(translation - 80f, translation - 80f),
        end = Offset(translation + 80f, translation + 80f)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(cardHeight)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(28.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}