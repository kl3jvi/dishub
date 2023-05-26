package com.kl3jvi.database

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.auth.models.User
import com.kl3jvi.recipe.models.NewRecipe
import com.kl3jvi.recipe.models.RecipeData
import org.koin.core.component.KoinComponent

class DbService(private val dbController: DbController) : KoinComponent {

    fun createUser(newUser: NewUser): User = dbController.createUser(newUser)

    fun findUser(username: String): User? = dbController.findUser(username)

    fun getAllRecipes() = dbController.getAllRecipes()

    fun addRecipe(newRecipe: NewRecipe) = dbController.addRecipe(newRecipe)

    fun getRecipeById(recipeId: Int): RecipeData? = dbController.getRecipeById(recipeId)

    fun getAllUsers(): List<User> = dbController.getAllUsers()
}
