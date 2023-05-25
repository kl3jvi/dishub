package com.kl3jvi.database

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.auth.models.User
import org.koin.core.component.KoinComponent

class DbService(private val dbController: DbController) : KoinComponent {

    fun createUser(newUser: NewUser): User = dbController.createUser(newUser)

    fun findUser(username: String): User? = dbController.findUser(username)
}
