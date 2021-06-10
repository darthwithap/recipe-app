package me.darthwithap.recipeapp.presentation.util

sealed class RecipeEvent {
    data class GetRecipeEvent(val id: Int) : RecipeEvent()
}
