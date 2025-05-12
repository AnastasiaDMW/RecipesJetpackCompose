package com.example.recipesjetpackcompose.domain.interfaces.repository

import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.domain.model.Result

interface RecipeRepository {

    suspend fun getRecipes(offset: Int): Result<List<Recipe>>

    suspend fun getRecipeDetailById(id: Int): Result<DetailRecipe>
}