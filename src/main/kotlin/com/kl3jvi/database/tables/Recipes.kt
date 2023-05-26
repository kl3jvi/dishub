package com.kl3jvi.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Recipes : IntIdTable() {
    val title = varchar("title", 255)
    val ingredients = text("ingredients")
    val image = varchar("image", 255)
    val createdBy = integer("userId")
    val likes = integer("likes").default(0)
}
