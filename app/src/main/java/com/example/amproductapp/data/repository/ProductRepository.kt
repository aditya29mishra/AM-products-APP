package com.example.amproductapp.data.repository

import com.example.amproductapp.data.local.ProductDao
import com.example.amproductapp.data.local.ProductEntity
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
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

    suspend fun addProductOffline(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    suspend fun getOfflineProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }
}