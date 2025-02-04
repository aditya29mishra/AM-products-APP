package com.example.amproductapp.data.repository

import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun addProduct(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.addProduct(productName, price, tax, productType, image)
}