package com.kl3jvi.recipe

import com.kl3jvi.recipe.models.NewRecipe
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RecipeController(
    private val recipeService: RecipeService
) {
    suspend fun getAllRecipes(call: ApplicationCall) {
        val result = recipeService.getAllRecipes()
        call.respond(HttpStatusCode.OK, result)
    }

    suspend fun addRecipe(call: ApplicationCall) {
        val newRecipe = call.receive<NewRecipe>()
        val recipeId = recipeService.addRecipe(newRecipe)
        if (recipeId != null)
            call.respond(HttpStatusCode.Created, newRecipe)
        else
            call.respond(HttpStatusCode.NotFound)
    }

    fun getRecipeById(id: Int) {}
}