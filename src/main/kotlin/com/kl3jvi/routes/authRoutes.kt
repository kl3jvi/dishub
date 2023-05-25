package com.kl3jvi.routes

import com.kl3jvi.auth.AuthService
import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.database.models.UserCredentials
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authService: AuthService by inject()

    route("/auth") {
        post("/register") {
            val newUser = call.receive<NewUser>()
            try {
                transaction { authService.register(newUser) }
                call.respond(HttpStatusCode.OK, "Successfully registered")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Registration failed: ${e.localizedMessage}")
            }
        }

        post("/login") {
            val credentials = call.receive<UserCredentials>()
            try {
                val token = transaction {
                    authService.login(credentials)
                }
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Unauthorized, "Login failed: ${e.localizedMessage}")
            }
        }
    }
}
