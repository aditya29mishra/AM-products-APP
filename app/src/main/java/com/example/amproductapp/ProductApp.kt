package com.example.amproductapp

import android.app.Application
import androidx.room.Room
import com.example.amproductapp.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductApp : Application() {
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "products.db"
        ).build()
    }
}