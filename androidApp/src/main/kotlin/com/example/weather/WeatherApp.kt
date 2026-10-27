package com.example.weather

import android.app.Application
import com.example.weather.shared.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@WeatherApp) }
    }
}
