package com.kl3jvi.routes

import com.kl3jvi.auth.AuthService
import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.auth.models.UserCredentials
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.authRoutes(authService: AuthService) {
    route("/auth") {
        post("/register") {
            val newUser = call.receive<NewUser>()
            try {
                transaction {
                    authService.register(newUser)
                }
                call.respond("Successfully registered")
            } catch (e: Exception) {
                call.respond("Registration failed: ${e.localizedMessage}")
            }
        }

        post("/login") {
            val credentials = call.receive<UserCredentials>()
            try {
                val token = transaction {
                    authService.login(credentials)
                }
                call.respond(mapOf("token" to token))
            } catch (e: Exception) {
                call.respond("Login failed: ${e.localizedMessage}")
            }
        }
    }
}
