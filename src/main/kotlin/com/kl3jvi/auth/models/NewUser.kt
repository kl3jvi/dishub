package com.kl3jvi.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class NewUser(
    val username: String,
    val passwordHash: String
)
