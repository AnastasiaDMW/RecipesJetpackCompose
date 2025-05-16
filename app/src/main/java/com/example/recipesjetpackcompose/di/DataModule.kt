package com.example.recipesjetpackcompose.di

import com.example.recipesjetpackcompose.BuildConfig
import com.example.recipesjetpackcompose.data.interfaces.RecipeMapper
import com.example.recipesjetpackcompose.data.model.RecipeMapperImpl
import com.example.recipesjetpackcompose.data.remote.api.RecipeService
import com.example.recipesjetpackcompose.data.remote.interceptor.HeadersInterceptor
import com.example.recipesjetpackcompose.data.repository.RecipeRepositoryImpl
import com.example.recipesjetpackcompose.domain.interfaces.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @LoggingInterceptor
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
        }
    }

    @HeaderInterceptor
    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return HeadersInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @HeaderInterceptor headersInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(BuildConfig.TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
            .writeTimeout(BuildConfig.TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
            .readTimeout(BuildConfig.TIMEOUT_READ, TimeUnit.MILLISECONDS)
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideServer1Retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeMapper,
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeService = recipeService,
            recipeMapper = recipeMapper
        )
    }

    @Singleton
    @Provides
    fun provideRecipeMapper(): RecipeMapper {
        return RecipeMapperImpl()
    }
}