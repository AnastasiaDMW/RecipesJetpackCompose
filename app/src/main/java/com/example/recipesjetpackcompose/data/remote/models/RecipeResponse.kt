package com.example.recipesjetpackcompose.data.remote.models

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("offset")
    val offset: Int? = null,
    @SerializedName("results")
    val recipeResults: List<RecipeResult>? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null,
)