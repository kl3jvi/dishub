package com.kl3jvi.routes

import com.kl3jvi.auth.AuthController
import com.kl3jvi.database.dbQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authService: AuthController by inject()

    route("/auth") {
        post("/register") {
            try {
                dbQuery { authService.register(call) }
                call.respond(HttpStatusCode.OK, "Successfully registered")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Registration failed: ${e.localizedMessage}")
            }
        }

        post("/login") {
            try {
                val token = dbQuery {
                    authService.login(call)
                }
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Unauthorized, "Login failed: ${e.localizedMessage}")
            }
        }
    }
}
