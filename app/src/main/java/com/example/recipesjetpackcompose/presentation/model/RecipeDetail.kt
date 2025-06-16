package com.example.recipesjetpackcompose.presentation.model

data class RecipeDetail(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    val glutenFree: Boolean = false,
    val vegan: Boolean = false,
    val cookingMinutes: Int = 0,
    val healthScore: Double = 0.0,
    val servings: Int = 0,
    val instructions: String = "",
    val extendedIngredients: List<ExtendedIngredients> = emptyList(),
)
