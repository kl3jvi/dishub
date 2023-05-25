package com.kl3jvi.di

import com.kl3jvi.auth.AuthService
import com.kl3jvi.database.DbConnector
import com.kl3jvi.database.DbController
import com.kl3jvi.database.DbService
import org.koin.dsl.module

val appModule = module {
    single { DbConnector() }
    single { DbController(get()) }
    single { DbService(get()) }
    single { AuthService(get()) }

//    single { RecipeService(get()) }
//    single { CommentService(get()) }
//    single { LikeService(get()) }
//
//    single { AuthController(get()) }
//    single { RecipeController(get()) }
//    single { CommentController(get()) }
//    single { LikeController(get()) }
}
