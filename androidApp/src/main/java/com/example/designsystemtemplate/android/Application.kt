package com.example.designsystemtemplate.android

import android.app.Application
import com.example.designsystemtemplate.di.MainModule
import com.example.designsystemtemplate.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(
                commonModule(),
                MainModule().module
            )
        }
    }
}