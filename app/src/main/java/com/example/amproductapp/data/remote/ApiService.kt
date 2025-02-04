package com.example.amproductapp.data.remote

import com.example.amproductapp.data.model.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}