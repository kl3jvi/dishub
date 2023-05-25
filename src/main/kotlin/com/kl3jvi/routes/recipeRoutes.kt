package com.kl3jvi.routes

import com.kl3jvi.recipe.RecipeService
import com.kl3jvi.recipe.models.NewRecipe
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.recipeRoutes() {
    val recipeService: RecipeService by inject()

    route("/recipe") {
        get("/") {
            call.respond(HttpStatusCode.OK, recipeService.getAllRecipes())
        }

        post("/") {
            val newRecipe = call.receive<NewRecipe>()
            try {
                val recipe = recipeService.addRecipe(newRecipe)
                if (recipe != null)
                    call.respond(HttpStatusCode.Created, recipe)
                else call.respond(HttpStatusCode.NotFound)
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
