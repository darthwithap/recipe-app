package me.darthwithap.recipeapp.repository

import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.network.RecipeService
import me.darthwithap.recipeapp.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(
            recipeService.search(
                token, page, query
            ).body()!!.recipes
        )
    }

    override suspend fun getRecipe(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(
            recipeService.getRecipe(
                token,
                id
            ).body()!!
        )
    }
}