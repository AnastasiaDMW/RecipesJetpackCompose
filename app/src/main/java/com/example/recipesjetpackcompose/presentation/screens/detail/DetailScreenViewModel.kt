package com.example.recipesjetpackcompose.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeDetailUseCase
import com.example.recipesjetpackcompose.domain.model.DetailRecipe
import com.example.recipesjetpackcompose.domain.model.Result
import com.example.recipesjetpackcompose.presentation.TAG
import com.example.recipesjetpackcompose.presentation.model.ExtendedIngredients
import com.example.recipesjetpackcompose.presentation.model.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val recipeDetailUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState: StateFlow<DetailScreenState> = _uiState

//    private val testData = RecipeDetail(
//        id = 715415,
//        image = "https://img.spoonacular.com/recipes/715415-556x370.jpg",
//        title = "Red Lentil Soup with Chicken and Turnips",
//        glutenFree = true,
//        vegan = false,
//        cookingMinutes = 45,
//        healthScore = 100.0,
//        servings = 8,
//        instructions = "To a large dutch oven or soup pot, heat the olive oil over medium heat. Add the " +
//                "onion, carrots and celery and cook for 8-10 minutes or until tender, stirring occasionally." +
//                " Add the garlic and cook for an additional 2 minutes, or until fragrant. Season conservatively" +
//                " with a pinch of salt and black pepper.To the pot, add the tomatoes, turnip and red lentils. Stir" +
//                " to combine. Stir in the vegetable stock and increase the heat on the stove to high. Bring the soup" +
//                " to a boil and then reduce to a simmer. Simmer for 20 minutes or until the turnips are tender and the" +
//                " lentils are cooked through. Add the chicken breast and parsley. Cook for an additional 5 minutes. " +
//                "Adjust seasoning to taste.Serve the soup immediately garnished with fresh parsley and any additional " +
//                "toppings. Enjoy!",
//        extendedIngredients = listOf(
//            ExtendedIngredients(
//                id = 9037,
//                image = "avocado.jpg",
//                name = "additional toppings: avocado",
//                amount =  8.0,
//                unit = "servings"
//            ),
//            ExtendedIngredients(
//                id = 11124,
//                image = "sliced-carrot.png",
//                name = "additional toppings: avocado",
//                amount =  3.0,
//                unit = "medium"
//            ),
//        )
//    )
//
//    init {
//        initTestData()
//    }

    fun handleEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.UpdateRecipeId -> getDetailDataForRecipe(event.recipeId)
        }
    }

    private fun getDetailDataForRecipe(recipeId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = recipeDetailUseCase.execute(recipeId)) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            recipeDetail = detailRecipeDomainToPresenter(result.data)
                        )
                    }
                    is Result.ConnectionError -> onError(result.message)
                    is Result.ServerError -> onError(message = "${result.message} ${result.errorCode}")
                    is Result.TimeoutError -> onError(result.message)
                    is Result.UnknownError -> onError(result.message)
                }
            }
        }
    }

    private fun detailRecipeDomainToPresenter(detail: DetailRecipe): RecipeDetail {
        return RecipeDetail(
            id = detail.id,
            image = detail.image,
            title = detail.title,
            glutenFree = detail.glutenFree,
            vegan = detail.vegan,
            cookingMinutes = detail.cookingMinutes,
            healthScore = detail.healthScore,
            servings = detail.servings,
            instructions = detail.instructions,
            extendedIngredients = extendedIngredientsDomainToPresenter(detail.extendedIngredients)
        )
    }

    private fun extendedIngredientsDomainToPresenter(
        ingredient: List<com.example.recipesjetpackcompose.domain.model.ExtendedIngredients>
    ): List<ExtendedIngredients> {
        return ingredient.map {
            ExtendedIngredients(
                id = it.id,
                image = it.image,
                name = it.name,
                amount = it.amount,
                unit = it.unit
            )
        }
    }

    private fun onError(message: String) {
        Log.d(TAG, message)
    }

//    private fun initTestData() {
//        _uiState.value = _uiState.value.copy(
//            recipeDetail = testData
//        )
//    }
}