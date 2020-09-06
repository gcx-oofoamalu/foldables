package com.obiomaofoamalu.foldableapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin(this)
    }

    private fun setupKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(listOf(
                appModule
            ))
        }
    }
}