package com.example.amproductapp.data.repository

import android.content.Context
import com.example.amproductapp.data.local.AppDatabase
import com.example.amproductapp.data.local.ProductDao
import com.example.amproductapp.data.remote.ApiService
import com.example.amproductapp.data.remote.RetrofitInstance

object ProductRepositorySingleton {
    private var instance: ProductRepository? = null

    fun getInstance(context: Context): ProductRepository {
        if (instance == null) {
            val database = AppDatabase.getDatabase(context)
            val productDao: ProductDao = database.productDao()
            val apiService: ApiService = RetrofitInstance.api
            instance = ProductRepository(apiService, productDao, context)
        }
        return instance!!
    }
}
