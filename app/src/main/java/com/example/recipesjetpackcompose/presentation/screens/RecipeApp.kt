package com.example.recipesjetpackcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipesjetpackcompose.presentation.navigation.RecipeNavGraph

@Composable
fun RecipeApp(
    navController: NavHostController = rememberNavController()
) {
    RecipeNavGraph(navController = navController)
}