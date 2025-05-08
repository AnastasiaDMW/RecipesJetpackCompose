package com.example.recipesjetpackcompose.presentation.screens.home

import com.example.recipesjetpackcompose.presentation.model.Recipe

data class HomeScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val searchText: String = "",
    val recipes: List<Recipe> = emptyList(),
)