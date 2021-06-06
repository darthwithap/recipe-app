package me.darthwithap.recipeapp.network.responses

import com.google.gson.annotations.SerializedName
import me.darthwithap.recipeapp.network.model.RecipeDto

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,
    @SerializedName("results")
    var recipes: List<RecipeDto>
)