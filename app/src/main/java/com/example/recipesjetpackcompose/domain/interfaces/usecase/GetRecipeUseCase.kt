package com.example.recipesjetpackcompose.domain.interfaces.usecase

import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.domain.model.Result

interface GetRecipeUseCase {

    suspend fun execute(offset: Int): List<Recipe>
}