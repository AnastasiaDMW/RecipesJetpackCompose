package com.example.recipesjetpackcompose.data.remote.models

import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("servings")
    val servings: Int? = null,
    @SerializedName("vegetarian")
    val vegetarian: Boolean? = null,
    @SerializedName("glutenFree")
    val glutenFree: Boolean? = null,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int? = null,
    @SerializedName("healthScore")
    val healthScore: Double? = null,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>? = null,
    @SerializedName("instructions")
    val instructions: String? = null,
)