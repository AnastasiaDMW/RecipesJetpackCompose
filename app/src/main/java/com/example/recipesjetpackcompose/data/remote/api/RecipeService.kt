package com.example.recipesjetpackcompose.data.remote.api

import com.example.recipesjetpackcompose.BuildConfig
import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

    @GET("recipes/complexSearch?offset={offset}&number=1000&apiKey=${BuildConfig.API_KEY}")
    suspend fun getRecipes(@Path("offset") offset: Int): RecipeResponse

    @GET("recipes/{id}/information?apiKey=${BuildConfig.API_KEY}")
    suspend fun getRecipeDetail(@Path("id") id: Int): DetailRecipeResponse
}