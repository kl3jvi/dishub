package com.kl3jvi.routes

import com.kl3jvi.recipe.RecipeController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.recipeRoutes() {
    val recipeService: RecipeController by inject()

    route("/recipe") {
        get("/") {
            recipeService.getAllRecipes(call)
        }

        post("/") {
            try {
                recipeService.addRecipe(call)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.localizedMessage)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val recipe = recipeService.getRecipeById(id)
                if (recipe != null) {
                    call.respond(HttpStatusCode.OK, recipe)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Recipe not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid recipe id")
            }
        }
    }
}
