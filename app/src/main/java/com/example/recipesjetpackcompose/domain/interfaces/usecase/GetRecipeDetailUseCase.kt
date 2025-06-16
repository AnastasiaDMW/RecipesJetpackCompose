package com.example.recipesjetpackcompose.domain.interfaces.usecase

import com.example.recipesjetpackcompose.domain.model.DetailRecipe

interface GetRecipeDetailUseCase {

    suspend fun execute(recipeId: Int): DetailRecipe
}