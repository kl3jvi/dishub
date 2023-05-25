package com.kl3jvi.recipe.models

import kotlinx.serialization.Serializable

@Serializable
data class NewRecipe(
    val title: String,
    val ingredients: List<String>,
    val image: String,
    val createdBy: Int
)
