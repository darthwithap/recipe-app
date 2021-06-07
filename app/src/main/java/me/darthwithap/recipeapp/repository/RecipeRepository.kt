package me.darthwithap.recipeapp.repository

import me.darthwithap.recipeapp.domain.model.Recipe

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List<Recipe>
    suspend fun getRecipe(token: String, id: Int): Recipe
}