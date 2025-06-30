package com.example.designsystemtemplate.di

import com.example.designsystemtemplate.network.ApiClient
import com.example.designsystemtemplate.network.ApiClientImpl
import com.example.designsystemtemplate.network.createNetworkClient
import com.example.designsystemtemplate.presentation.StoreFrontViewModel
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.example.designsystemtemplate**")
class MainModule

fun initKoin() {
    startKoin {
        modules(
            commonModule(),
            MainModule().module
        )
    }
}

class MainModuleHelper : KoinComponent {
    val storeFrontViewModel: StoreFrontViewModel by inject()
}

fun commonModule(): org.koin.core.module.Module = module {
    single<HttpClient> { createNetworkClient() }
    single<ApiClient> { ApiClientImpl(get()) }
    viewModel { StoreFrontViewModel(get()) }
}