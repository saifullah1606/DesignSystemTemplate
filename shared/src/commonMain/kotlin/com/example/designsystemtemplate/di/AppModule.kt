package com.example.designsystemtemplate.di

import com.example.designsystemtemplate.network.ApiClient
import com.example.designsystemtemplate.network.ApiClientImpl
import com.example.designsystemtemplate.presentation.StoreFrontViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
//
//val appModule = module {
//    single<ApiClient> { ApiClientImpl(get()) }
//    viewModel { StoreFrontViewModel(get()) }
//}