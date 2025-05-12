package com.example.recipesjetpackcompose.data.interfaces

import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.presentation.model.RecipeDetail

interface RecipeMapper {

    fun recipeResponseDataToDomain(recipeResponse: RecipeResponse): List<Recipe>

    fun detailRecipeResponseDataToDomain(detailRecipeResponse: DetailRecipeResponse): DetailRecipe
}