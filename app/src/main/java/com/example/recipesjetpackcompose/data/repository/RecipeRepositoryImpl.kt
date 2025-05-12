package com.example.recipesjetpackcompose.data.repository

import com.example.recipesjetpackcompose.data.interfaces.RecipeMapper
import com.example.recipesjetpackcompose.data.remote.api.RecipeService
import com.example.recipesjetpackcompose.data.remote.safeApiCall
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.domain.model.Result
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeMapper: RecipeMapper,
): RecipeRepository {

    override suspend fun getRecipes(offset: Int): Result<List<Recipe>> {
        return safeApiCall {
            val response = recipeService.getRecipes(offset = offset)
            recipeMapper.recipeResponseDataToDomain(response)
        }
    }

    override suspend fun getRecipeDetailById(id: Int): Result<DetailRecipe> {
        return safeApiCall {
            val response = recipeService.getRecipeDetail(id = id)
            recipeMapper.detailRecipeResponseDataToDomain(response)
        }
    }
}