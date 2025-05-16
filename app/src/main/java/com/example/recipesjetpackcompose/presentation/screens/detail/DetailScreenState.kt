package com.example.recipesjetpackcompose.presentation.screens.detail

import com.example.recipesjetpackcompose.presentation.model.RecipeDetail

data class DetailScreenState(
    val isLoading: Boolean = false,
    val recipeDetail: RecipeDetail = RecipeDetail(),
    val error: String? = null,
    val currentRecipeId: Int = -1
)