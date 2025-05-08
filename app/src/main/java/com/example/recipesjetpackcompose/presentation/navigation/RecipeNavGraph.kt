package com.example.recipesjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipesjetpackcompose.presentation.screens.detail.DetailScreen
import com.example.recipesjetpackcompose.presentation.screens.home.HomeScreen

@Composable
fun RecipeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                onRecipeClick = { recipeId ->
                    navController.navigate(DetailDestination.createRoute(recipeId))
                }
            )
        }

        composable(
            route = DetailDestination.route,
            arguments = listOf(
                navArgument(DetailDestination.RECIPE_ID_ARG) { type = NavType.IntType }
            )
        ) { navBackStack ->
            val recipeId = navBackStack.arguments?.getInt(DetailDestination.RECIPE_ID_ARG)

            DetailScreen(
                recipeId = recipeId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}