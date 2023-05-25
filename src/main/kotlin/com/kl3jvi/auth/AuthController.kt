package com.kl3jvi.auth

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.database.models.UserCredentials
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AuthController(private val authService: AuthService) {

    suspend fun login(call: ApplicationCall) {
        val credentials = call.receive<UserCredentials>()
        val token = authService.login(credentials)
        call.respond(HttpStatusCode.OK, token)
    }

    suspend fun register(call: ApplicationCall) {
        val newUser = call.receive<NewUser>()
        authService.register(newUser)
        call.respond(HttpStatusCode.Created)
    }
}
