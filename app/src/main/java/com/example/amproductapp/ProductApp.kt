package com.example.amproductapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // You can initialize global dependencies here if needed
    }
}