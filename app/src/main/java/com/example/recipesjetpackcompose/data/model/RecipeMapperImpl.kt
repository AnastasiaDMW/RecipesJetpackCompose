package com.example.recipesjetpackcompose.data.model

import com.example.recipesjetpackcompose.data.interfaces.RecipeMapper
import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.ExtendedIngredient
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.ExtendedIngredients
import com.example.recipesjetpackcompose.domain.model.Recipe
import com.example.recipesjetpackcompose.presentation.model.RecipeDetail

class RecipeMapperImpl: RecipeMapper {

    override fun recipeResponseDataToDomain(recipeResponse: RecipeResponse): List<Recipe> =
        recipeResponse.recipeResults?.mapNotNull { recipe ->
                recipe.takeIf {
                    it.id != null
                }?.let {
                    Recipe(
                        id = it.id ?: -1,
                        image = it.image.orEmpty(),
                        title = it.title.orEmpty(),
                        totalResults = recipeResponse.totalResults ?: -1
                    )
                }
            } ?: emptyList()

    override fun detailRecipeResponseDataToDomain(detailRecipeResponse: DetailRecipeResponse): DetailRecipe =
        with(detailRecipeResponse) {
            DetailRecipe(
                id = id ?: -1,
                image = image.orEmpty(),
                title = title.orEmpty(),
                glutenFree = glutenFree == true,
                vegan = vegetarian == true,
                cookingMinutes = cookingMinutes ?: 0,
                healthScore = healthScore ?: 0.0,
                servings = servings ?: 0,
                instructions = instructions.orEmpty(),
                extendedIngredients = extendedIngredients.toDomainIngredients()
            )
        }

    private fun List<ExtendedIngredient>?.toDomainIngredients(): List<ExtendedIngredients> =
        this?.mapNotNull { it.toDomainIngredient() } ?: emptyList()

    private fun ExtendedIngredient.toDomainIngredient(): ExtendedIngredients? =
        takeIf { id != null }?.let {
            ExtendedIngredients(
                id = id ?: -1,
                image = image.orEmpty(),
                name = name.orEmpty(),
                amount = amount ?: 0.0,
                unit = unit.orEmpty()
            )
        }
}