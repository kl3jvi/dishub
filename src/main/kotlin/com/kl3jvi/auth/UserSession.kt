package com.kl3jvi.auth

import io.ktor.auth.*

data class UserSession(val userId: Int) : Principal
