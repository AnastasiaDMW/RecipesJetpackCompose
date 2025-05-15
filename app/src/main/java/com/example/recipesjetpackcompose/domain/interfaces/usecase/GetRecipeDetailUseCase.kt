package com.example.recipesjetpackcompose.domain.interfaces.usecase

import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Result

interface GetRecipeDetailUseCase {

    suspend fun execute(recipeId: Int): DetailRecipe
}