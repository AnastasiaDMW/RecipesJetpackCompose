package com.example.recipesjetpackcompose.di

import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeDetailUseCase
import com.example.recipesjetpackcompose.domain.interfaces.usecase.GetRecipeUseCase
import com.example.recipesjetpackcompose.domain.usecase.GetRecipeDetailUseCaseImpl
import com.example.recipesjetpackcompose.domain.usecase.GetRecipeUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(recipeRepository: RecipeRepository): GetRecipeUseCase {
        return GetRecipeUseCaseImpl(recipeRepository = recipeRepository)
    }

    @Singleton
    @Provides
    fun provideGetRecipeDetailUseCase(recipeRepository: RecipeRepository): GetRecipeDetailUseCase {
        return GetRecipeDetailUseCaseImpl(recipeRepository = recipeRepository)
    }
}