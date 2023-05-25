package com.kl3jvi

import io.ktor.auth.*

data class UserSession(val userId: Int) : Principal
