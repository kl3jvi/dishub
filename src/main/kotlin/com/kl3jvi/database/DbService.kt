package com.kl3jvi.database

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.database.models.User
import org.jetbrains.exposed.dao.id.IntIdTable
import org.koin.core.component.KoinComponent

object Users : IntIdTable() {
    val username = varchar("username", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}

class DbService(private val dbController: DbController) : KoinComponent {

    fun createUser(newUser: NewUser): User = dbController.createUser(newUser)

    fun findUser(username: String): User? = dbController.findUser(username)
}
