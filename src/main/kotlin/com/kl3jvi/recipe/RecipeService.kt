package com.kl3jvi.recipe

import com.kl3jvi.database.DbController
import com.kl3jvi.recipe.models.NewRecipe
import com.kl3jvi.recipe.models.RecipeData

class RecipeService(private val dbController: DbController) {

    fun getAllRecipes(): List<RecipeData> {
        return dbController.getAllRecipes()
    }

    fun addRecipe(newRecipe: NewRecipe): RecipeData? {
        val recipeId = dbController.addRecipe(newRecipe)
        return dbController.getRecipeById(recipeId)
    }

    fun getRecipeById(id: Int): RecipeData? {
        return dbController.getRecipeById(id)
    }
}