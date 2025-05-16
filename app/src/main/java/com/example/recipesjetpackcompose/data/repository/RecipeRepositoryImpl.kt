package com.example.recipesjetpackcompose.data.repository

import com.example.recipesjetpackcompose.data.interfaces.RecipeMapper
import com.example.recipesjetpackcompose.data.remote.api.RecipeService
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Recipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeMapper: RecipeMapper,
) : RecipeRepository {

    override suspend fun getRecipes(offset: Int): List<Recipe> {
        val response = recipeService.getRecipes(offset = offset)
        return recipeMapper.recipeResponseDataToDomain(response)
    }

    override suspend fun getSearchRecipes(
        offset: Int,
        ingredient: String
    ): List<Recipe> {
        val response = recipeService.getSearchRecipes(offset = offset, titleMatch = ingredient)
        return recipeMapper.recipeResponseDataToDomain(response)
    }

    override suspend fun getRecipeDetailById(id: Int): DetailRecipe {
        val response = recipeService.getRecipeDetail(id = id)
        return recipeMapper.detailRecipeResponseDataToDomain(response)
    }
}