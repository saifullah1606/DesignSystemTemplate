package com.example.designsystemtemplate.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

//actual val platformModule: Module = module {
////    single<RoomDatabase.Builder<ExpenseDatabase>> { getExpenseDatabaseBuilder() }
//    single<HttpClientEngineFactory<*>> { Darwin }
//}