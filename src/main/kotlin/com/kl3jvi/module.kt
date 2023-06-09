package com.kl3jvi

import com.kl3jvi.auth.UserSession
import com.kl3jvi.di.appModule
import com.kl3jvi.routes.authRoutes
import com.kl3jvi.routes.recipeRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun Application.module() {
    configureParser()
    configureLogging()
    configureDI()
    configureSessions()
    configureRouting()
}

fun Application.configureParser() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}

fun Application.configureDI() {
    install(Koin) {
        modules(appModule)
    }
}

fun Application.configureSessions() {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION")
    }
}

fun Application.configureRouting() {
    install(Routing) {
        authRoutes()
        recipeRoutes()
    }
}
