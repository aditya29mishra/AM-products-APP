package com.example.amproductapp.data.repository

import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.remote.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }
}