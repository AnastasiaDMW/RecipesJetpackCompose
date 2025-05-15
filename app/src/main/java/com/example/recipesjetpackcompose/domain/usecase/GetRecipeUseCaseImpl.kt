package com.example.recipesjetpackcompose.domain.usecase

import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeUseCase
import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.domain.model.Result

class GetRecipeUseCaseImpl(
    private val recipeRepository: RecipeRepository,
): GetRecipeUseCase {

    override suspend fun execute(offset: Int): List<Recipe> {
        return recipeRepository.getRecipes(offset)
    }
}