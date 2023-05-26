package com.kl3jvi.di

import com.kl3jvi.auth.AuthService
import com.kl3jvi.database.DbController
import com.kl3jvi.database.DbService
import com.kl3jvi.recipe.RecipeService
import org.koin.dsl.module

val appModule = module {
    single { DbController() }
    single { DbService(get()) }
    single { AuthService(get()) }

    single { RecipeService(get()) }

//    single { CommentService(get()) }
//    single { LikeService(get()) }
//
//    single { AuthController(get()) }
//    single { CommentController(get()) }
//    single { LikeController(get()) }
}
