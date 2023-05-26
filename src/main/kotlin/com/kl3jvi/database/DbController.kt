package com.kl3jvi.database

import com.kl3jvi.auth.models.NewUser
import com.kl3jvi.auth.models.User
import com.kl3jvi.comments.models.Comment
import com.kl3jvi.database.tables.Comments
import com.kl3jvi.database.tables.Ingredients
import com.kl3jvi.database.tables.Recipes
import com.kl3jvi.database.tables.Recipes.createdBy
import com.kl3jvi.database.tables.Users
import com.kl3jvi.recipe.models.NewRecipe
import com.kl3jvi.recipe.models.RecipeData
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DbController {
    init {
        val host = "sql.freedb.tech"
        val port = 3306
        val databaseName = "freedb_naruto"
        val dbUser = "freedb_dishub"
        val password = "GmMf%$$6dd3yfbS"

        Database.connect(
            url = "jdbc:mysql://$host:$port/$databaseName",
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbUser,
            password = password
        )

        transaction {
            SchemaUtils.create(Recipes)
            SchemaUtils.create(Users)
            SchemaUtils.create(Ingredients)
            SchemaUtils.create(Comments)
        }
    }

    fun createUser(newUser: NewUser): User {
        var createdUser: User? = null
        transaction {
            Users.insert { user ->
                user[username] = newUser.username
                user[passwordHash] = newUser.passwordHash
            }

            Users.select { Users.username eq newUser.username }
                .singleOrNull()
                ?.let { row ->
                    createdUser = User(
                        row[Users.id].value,
                        row[Users.username],
                        row[Users.passwordHash]
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
                    )
                }
        }
        return foundUser
    }

    fun getAllRecipes(): List<RecipeData> {
        return transaction {
            Recipes.selectAll().map {
                RecipeData(
                    id = it[Recipes.id].value,
                    title = it[Recipes.title],
                    image = it[Recipes.image],
                    ingredients = getIngredientsByRecipeId(it[Recipes.id].value),
                    createdBy = getUserById(it[Recipes.createdBy]),
                    likes = getLikesByRecipeId(it[Recipes.id].value),
                    comments = getCommentsByRecipeId(it[Recipes.id].value)
                )
            }
        }
    }

    fun addRecipe(newRecipe: NewRecipe): Int {
        return transaction {
            Recipes.insertAndGetId { row ->
                row[title] = newRecipe.title
                row[image] = newRecipe.image
                row[ingredients] = newRecipe.ingredients.joinToString()
                row[createdBy] = newRecipe.createdBy
            }.value
        }
    }

    fun getRecipeById(id: Int): RecipeData? {
        return transaction {
            Recipes.select { Recipes.id eq id }.map {
                RecipeData(
                    id = it[Recipes.id].value,
                    title = it[Recipes.title],
                    image = it[Recipes.image],
                    ingredients = getIngredientsByRecipeId(it[Recipes.id].value),
                    createdBy = getUserById(it[createdBy]),
                    likes = getLikesByRecipeId(it[Recipes.id].value),
                    comments = getCommentsByRecipeId(it[Recipes.id].value)
                )
            }.singleOrNull()
        }
    }

    private fun getIngredientsByRecipeId(recipeId: Int): List<String> {
        return transaction {
            Ingredients
                .select { Ingredients.recipe eq recipeId }
                .map { it[Ingredients.name] }
        }
    }

    private fun getCommentsByRecipeId(recipeId: Int): List<Comment> {
        return transaction {
            Comments
                .select { Comments.recipe eq recipeId }
                .map {
                    Comment(
                        id = it[Comments.id].value,
                        text = it[Comments.text],
                        createdBy = getUserById(it[Comments.createdBy].value),
                        timestamp = it[Comments.timeStamp]
                    )
                }
        }
    }

    private fun getLikesByRecipeId(recipeId: Int): Int {
//        return transaction {
//            Likes
//                .select { Likes.recipe eq recipeId }
//                .count()
//        }
        return -1
    }

    private fun getUserById(userId: Int): User {
        return transaction {
            Users
                .select { Users.id eq userId }
                .map {
                    User(
                        id = it[Users.id].value,
                        username = it[Users.username],
                        password = it[Users.passwordHash]
                    )
                }
                .singleOrNull() ?: throw NotFoundException("User not found")
        }
    }

    private fun getUserIdByUsername(username: String): Int {
        return transaction {
            Users
                .select { Users.username eq username }
                .map { it[Users.id].value }
                .singleOrNull() ?: throw NotFoundException("User not found")
        }
    }


}
