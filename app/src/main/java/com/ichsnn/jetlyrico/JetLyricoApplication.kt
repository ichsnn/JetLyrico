package com.ichsnn.jetlyrico

import android.app.Application
import com.ichsnn.jetlyrico.data.AppContainer
import com.ichsnn.jetlyrico.data.AppDataContainer

class JetLyricoApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}