package com.example.recipesjetpackcompose.domain.usecase

import com.example.recipesjetpackcompose.BuildConfig
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeDetailUseCase
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Result

class GetRecipeDetailUseCaseImpl(
    private val recipeRepository: RecipeRepository,
) : GetRecipeDetailUseCase {

    override suspend fun execute(recipeId: Int): DetailRecipe {
        val result = recipeRepository.getRecipeDetailById(recipeId)
        return modifyIngredientImages(result)
    }

    private fun modifyIngredientImages(recipe: DetailRecipe): DetailRecipe {
        return recipe.copy(
            extendedIngredients = recipe.extendedIngredients.map { ingredient ->
                ingredient.copy(
                    image = "${BuildConfig.BASE_IMAGE_URL}${ingredient.image}?apiKey=${BuildConfig.API_KEY}"
                )
            }
        )
    }
}