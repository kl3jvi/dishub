package com.kl3jvi.comments.models

import com.kl3jvi.auth.models.User
import java.time.LocalDateTime

data class Comment(
    val id: Int,
    val text: String,
    val createdBy: User,
    val timestamp: LocalDateTime
)
