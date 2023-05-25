package com.kl3jvi.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Ingredients : IntIdTable() {
    val name = varchar("name", 255)
    val recipe = reference("recipe", Recipes)
}

