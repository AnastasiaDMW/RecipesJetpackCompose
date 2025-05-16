package com.example.recipesjetpackcompose.data.remote.models

import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("amount")
    val amount: Double? = null,
    @SerializedName("unit")
    val unit: String? = null,
)