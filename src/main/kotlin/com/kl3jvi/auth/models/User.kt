package com.kl3jvi.auth.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class User(
    val id: Int,
    val username: String,
    @Transient
    val password: String = ""
)
