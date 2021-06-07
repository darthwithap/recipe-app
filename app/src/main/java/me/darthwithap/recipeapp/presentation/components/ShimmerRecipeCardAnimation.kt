package me.darthwithap.recipeapp.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import me.darthwithap.recipeapp.presentation.components.util.recipeCardShimmerColors

@Composable
fun ShimmerRecipeCardAnimation() {
    val transition = rememberInfiniteTransition()
    val translation by transition.animateFloat(

        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(

            tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    ShimmerRecipeCardItem(
        colors = recipeCardShimmerColors,
        cardHeight = 240.dp,
        translation = translation
    )
}