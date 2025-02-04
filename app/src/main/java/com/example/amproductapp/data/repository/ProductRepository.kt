package com.example.amproductapp.data.repository

import com.example.amproductapp.data.local.ProductDao
import com.example.amproductapp.data.local.ProductEntity
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.remote.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    suspend fun syncOfflineProducts() {
        val offlineProducts = productDao.getAllProducts()
        for (product in offlineProducts) {
            try {
                val nameBody = product.productName.toRequestBody("text/plain".toMediaType())
                val typeBody = product.productType.toRequestBody("text/plain".toMediaType())
                val priceBody = product.price.toString().toRequestBody("text/plain".toMediaType())
                val taxBody = product.tax.toString().toRequestBody("text/plain".toMediaType())

                val response = apiService.addProduct(nameBody, typeBody, priceBody, taxBody, null)
                if (response.success) {
                    productDao.deleteProduct(product.id) // Remove from offline storage after sync
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}