package com.example.recipesjetpackcompose.presentation.screens.detail

import com.example.recipesjetpackcompose.presentation.model.RecipeDetail

data class DetailScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val recipeDetail: RecipeDetail = RecipeDetail()
)