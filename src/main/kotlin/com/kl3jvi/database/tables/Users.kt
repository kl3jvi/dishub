package com.kl3jvi.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val username = varchar("username", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}