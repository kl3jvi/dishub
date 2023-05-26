package com.kl3jvi.auth

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.auth.models.User
import com.kl3jvi.database.DbService
import com.kl3jvi.database.models.UserCredentials
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

class AuthService(
    private val dbService: DbService
) {

    private val SECRET_KEY = "mySuperSecretKey"

    fun login(credentials: UserCredentials): String {
        val user = transaction { dbService.findUser(credentials.username) }
        if (user != null && checkPassword(credentials.password, user.password)) {
            return generateToken(user)
        } else {
            throw NotFoundException("Invalid credentials")
        }
    }

    fun register(newUser: NewUser) {
        transaction {
            val existingUser = dbService.findUser(newUser.username)
            if (existingUser == null) {
                dbService.createUser(
                    NewUser(
                        username = newUser.username,
                        passwordHash = hash(newUser.passwordHash) // Storing hashed password
                    )
                )
            } else {
                throw IllegalArgumentException("User with this username already exists")
            }
        }
    }

    private fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun checkPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }

    private fun generateToken(user: User): String {
        val signatureAlgorithm = SignatureAlgorithm.HS256

        val signingKey = SecretKeySpec(
            DatatypeConverter.parseBase64Binary(SECRET_KEY),
            signatureAlgorithm.jcaName
        )

        return Jwts.builder()
            .setId(user.id.toString())
            .setIssuedAt(Date())
            .setSubject(user.username)
            .setIssuer("your-app")
            .signWith(signatureAlgorithm, signingKey)
            .compact()
    }
}
