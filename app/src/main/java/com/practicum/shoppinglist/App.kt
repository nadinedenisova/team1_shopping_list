package com.practicum.shoppinglist

import android.app.Application
import com.practicum.shoppinglist.di.AppComponent
import com.practicum.shoppinglist.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}