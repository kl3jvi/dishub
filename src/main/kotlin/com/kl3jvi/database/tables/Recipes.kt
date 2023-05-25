package com.kl3jvi.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Recipes : IntIdTable() {
    val title = varchar("name", 255)
    val ingredients = text("ingredients")
    val image = varchar("image", 255)
    val createdBy = reference("createdBy", Users)
    val likes = integer("likes").default(0)
}

