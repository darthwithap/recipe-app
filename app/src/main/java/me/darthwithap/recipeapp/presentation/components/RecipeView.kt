package me.darthwithap.recipeapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.util.RECIPE_VIEW_HEIGHT

@Composable
fun RecipeView(
    recipe: Recipe
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        recipe.featuredImage?.let {
            FoodImage(url = it, height = RECIPE_VIEW_HEIGHT)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                recipe.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                }
                recipe.rating?.toString()?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            recipe.publisher?.let {
                Text(
                    text = recipe.dateUpdated?.let { updated ->
                        "Updated on $updated by $it"
                    } ?: recipe.dateAdded?.let { added ->
                        "Added on $added by $it"
                    } ?: "By $it",
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(4.dp)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.caption
                )
            }
            recipe.ingredients.forEach {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}