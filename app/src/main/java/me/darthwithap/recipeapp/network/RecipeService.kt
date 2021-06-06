package me.darthwithap.recipeapp.network

import me.darthwithap.recipeapp.network.responses.RecipeSearchResponse
import me.darthwithap.recipeapp.network.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<RecipeSearchResponse>

    @GET("get")
    suspend fun getRecipe(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<RecipeDto>
}