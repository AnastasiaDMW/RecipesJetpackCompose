package com.example.recipesjetpackcompose.presentation.screens.detail

sealed class DetailScreenEvent {

    data class UpdateRecipeId(val recipeId: Int) : DetailScreenEvent()

    data object RetryLoading : DetailScreenEvent()
}