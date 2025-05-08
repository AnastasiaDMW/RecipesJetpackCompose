package com.example.recipesjetpackcompose.presentation.navigation

import com.example.recipesjetpackcompose.R

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

object DetailDestination : NavigationDestination {
    override val route: String = "detail/{recipeId}"
    override val titleRes = R.string.detail_screen
    const val RECIPE_ID_ARG = "recipeId"

    fun createRoute(recipeId: Int) = route.replace("{$RECIPE_ID_ARG}", recipeId.toString())
}