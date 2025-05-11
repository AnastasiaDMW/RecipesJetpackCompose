package com.example.recipesjetpackcompose.data.repository

import com.example.recipesjetpackcompose.data.remote.api.RecipeService
import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import com.example.recipesjetpackcompose.data.remote.safeApiCall
import com.example.recipesjetpackcompose.domain.interfaces.repository.NetworkRecipeRepository
import com.example.recipesjetpackcompose.domain.model.Result

class NetworkRecipeRepositoryImpl(
    private val recipeService: RecipeService
): NetworkRecipeRepository {

    override suspend fun getRecipes(offset: Int): Result<RecipeResponse> {
        return safeApiCall {
            recipeService.getRecipes(offset = offset)
        }
    }

    override suspend fun getRecipeDetail(id: Int): Result<DetailRecipeResponse> {
        return safeApiCall {
            recipeService.getRecipeDetail(id = id)
        }
    }
}