package com.example.recipesjetpackcompose.data.remote.api

import com.example.recipesjetpackcompose.BuildConfig
import com.example.recipesjetpackcompose.data.remote.models.DetailRecipeResponse
import com.example.recipesjetpackcompose.data.remote.models.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("recipes/complexSearch?number=100&apiKey=${BuildConfig.API_KEY}")
    suspend fun getRecipes(@Query("offset") offset: Int): RecipeResponse

    @GET("recipes/complexSearch?number=100&apiKey=${BuildConfig.API_KEY}")
    suspend fun getSearchRecipes(
        @Query("offset") offset: Int,
        @Query("titleMatch") titleMatch: String,
    ): RecipeResponse

    @GET("recipes/{id}/information?apiKey=${BuildConfig.API_KEY}")
    suspend fun getRecipeDetail(@Path("id") id: Int): DetailRecipeResponse
}