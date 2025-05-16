package com.example.recipesjetpackcompose.presentation.screens.home

import com.example.recipesjetpackcompose.presentation.model.Recipe

data class HomeScreenState(
    val isCloseIconVisible: Boolean = true,
    val isLoading: Boolean = false,
)