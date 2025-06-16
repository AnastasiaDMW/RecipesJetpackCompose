package com.example.recipesjetpackcompose.data.remote.models

import com.google.gson.annotations.SerializedName

data class RecipeResult(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("title")
    val title: String? = null,
)