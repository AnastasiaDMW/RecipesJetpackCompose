package com.example.recipesjetpackcompose.presentation.screens.home

sealed class HomeScreenEvent {

    data class UpdateSearchTextField(val recipe: String): HomeScreenEvent()

    data object GetRecipes: HomeScreenEvent()

    data object ClearSearchTextField: HomeScreenEvent()
}