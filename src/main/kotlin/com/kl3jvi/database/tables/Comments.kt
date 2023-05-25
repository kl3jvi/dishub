package com.kl3jvi.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Comments : IntIdTable() {
    val text = text("text")
    val recipe = reference("recipe", Recipes)
    val timeStamp = datetime("date_created").clientDefault { LocalDateTime.now() }
    val createdBy = reference("createdBy", Users)
}