package com.kl3jvi.recipe.models

import com.kl3jvi.auth.models.User
import com.kl3jvi.comments.models.Comment

data class RecipeData(
    val id: Int,
    val title: String,
    val ingredients: List<String>,
    val image: String,
    val createdBy: User,
    val likes: Int,
    val comments: List<Comment>
)