package com.example.recipesjetpackcompose.domain.model

data class Recipe(
    val id: Int,
    val image: String,
    val title: String,
    val totalResults: Int,
)
