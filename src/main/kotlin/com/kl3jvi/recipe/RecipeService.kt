package com.kl3jvi.recipe

import com.kl3jvi.database.DbService
import com.kl3jvi.recipe.models.NewRecipe
import com.kl3jvi.recipe.models.RecipeData

class RecipeService(private val dbService: DbService) {

    fun getAllRecipes(): List<RecipeData> {
        return dbService.getAllRecipes()
    }

    fun addRecipe(newRecipe: NewRecipe): RecipeData? {
        val recipeId = dbService.addRecipe(newRecipe)
        return dbService.getRecipeById(recipeId)
    }

    fun getRecipeById(id: Int): RecipeData? {
        return dbService.getRecipeById(id)
    }
}