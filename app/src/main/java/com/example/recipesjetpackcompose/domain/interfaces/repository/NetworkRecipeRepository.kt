package com.example.recipesjetpackcompose.domain.interfaces.repository

import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import com.example.recipesjetpackcompose.domain.model.Result

interface NetworkRecipeRepository {

    suspend fun getRecipes(offset: Int): Result<RecipeResponse>

    suspend fun getRecipeDetail(id: Int): Result<DetailRecipeResponse>
}