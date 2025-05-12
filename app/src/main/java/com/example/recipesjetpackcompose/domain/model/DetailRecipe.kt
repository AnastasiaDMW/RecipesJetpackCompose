package com.example.recipesjetpackcompose.domain.model


data class DetailRecipe(
    val id: Int,
    val image: String,
    val title: String,
    val glutenFree: Boolean,
    val vegan: Boolean,
    val cookingMinutes: Int,
    val healthScore: Double,
    val servings: Int,
    val instructions: String,
    val extendedIngredients: List<ExtendedIngredients>,
)
