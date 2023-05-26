package com.kl3jvi.comments.models

import com.kl3jvi.auth.models.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Comment(
    val id: Int,
    val text: String,
    val createdBy: User,
    @Contextual
    val timestamp: LocalDateTime
)
