package me.darthwithap.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.google.accompanist.coil.rememberCoilPainter
import me.darthwithap.recipeapp.R

@Composable
fun FoodImage(
    url: String,
    height: Dp
) {
    val painter = rememberCoilPainter(
        request = url,
        requestBuilder = {
            placeholder(R.drawable.food_img_placeholder)
        },
        fadeIn = true
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentScale = ContentScale.Crop
    )
}