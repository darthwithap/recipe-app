package me.darthwithap.recipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("pk")
    var id: Int? = null,
    var title: String? = null,
    var publisher: String? = null,
    @SerializedName("featured_image")
    var featuredImage: String? = null,
    var rating: Int? = null,
    @SerializedName("source_url")
    var sourceUrl: String? = null,
    var description: String? = null,
    @SerializedName("cooking_instructions")
    var cookingInstructions: List<String>? = null,
    var ingredients: List<String>? = null,
    @SerializedName("date_added")
    var dateAdded: String? = null,
    @SerializedName("date_updated")
    var dateUpdated: String? = null,
    @SerializedName("long_date_added")
    var longDateAdded: String? = null,
    @SerializedName("long_date_updated")
    var longDateUpdated: String? = null
)