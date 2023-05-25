package com.kl3jvi

import com.kl3jvi.auth.AuthService
import com.kl3jvi.auth.UserSession
import com.kl3jvi.di.appModule
import com.kl3jvi.routes.authRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(Koin) {
        modules(appModule)
    }

    install(Sessions) {
        cookie<UserSession>("USER_SESSION")
    }

    val authService: AuthService by inject()

    install(Routing) {
        authRoutes(authService)
    }
}
