package com.kl3jvi.database

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.database.models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DbController(dbConnector: DbConnector) {
    init {
        dbConnector
    }

    fun createUser(newUser: NewUser): User {
        var createdUser: User? = null
        transaction {
            Users.insert { user ->
                user[username] = newUser.username
                user[passwordHash] = newUser.passwordHash
                // set other properties...
            }

            // Then retrieve the user just created
            Users.select { Users.username eq newUser.username }
                .singleOrNull()
                ?.let { row ->
                    createdUser = User(
                        row[Users.id].value,
                        row[Users.username],
                        row[Users.passwordHash]
                        // map other properties...
                    )
                }
        }
        return createdUser!!
    }

    fun findUser(username: String): User? {
        var foundUser: User? = null
        transaction {
            Users.select { Users.username eq username }
                .singleOrNull()
                ?.let { row ->
                    foundUser = User(
                        row[Users.id].value,
                        row[Users.username],
                        row[Users.passwordHash]
                        // map other properties...
                    )
                }
        }
        return foundUser
    }
}
