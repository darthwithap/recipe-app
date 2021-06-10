package me.darthwithap.recipeapp.presentation.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.R
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.presentation.components.util.ShimmerRecipeCardAnimation
import me.darthwithap.recipeapp.presentation.components.util.SnackbarController
import me.darthwithap.recipeapp.presentation.util.RecipeListEvent
import me.darthwithap.recipeapp.util.PAGE_SIZE
import me.darthwithap.recipeapp.util.RECIPE_CARD_HEIGHT

private const val TAG = "RecipeList"

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onRecipeScrollPositionChanged: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController,
    isDark: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (loading && recipes.isEmpty()) ShimmerRecipeCardAnimation(
            RECIPE_CARD_HEIGHT
        )

        LazyColumn {
            itemsIndexed(
                items = recipes
            ) { index, recipe ->
                onRecipeScrollPositionChanged(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    onNextPage(RecipeListEvent.NextPageEvent)
                }
                recipeCard(recipe) {
                    Log.d(TAG, "RecipeList: CLICKED")
                    if (recipe.id != null) {
                        val bundle = Bundle()
                        bundle.putInt("recipeId", recipe.id)
                        bundle.putBoolean("isDark", isDark)

                        navController.navigate(R.id.viewRecipe, bundle)
                    } else {
                        snackbarController.getScope().launch {
                            snackbarController.showSnackbar(scaffoldState, "Recipe Error", "Ok")
                        }
                    }
                }
            }
        }

    }
}