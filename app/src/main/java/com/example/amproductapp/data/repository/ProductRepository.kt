package com.example.amproductapp.data.repository

import com.example.amproductapp.data.local.ProductDao
import com.example.amproductapp.data.local.ProductEntity
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.remote.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class ProductRepository @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    @ApplicationContext private val context: android.content.Context
) {
    suspend fun getProducts(): List<Product> {
        return try {
            apiService.getProducts()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun addProductToServer(product: Product): Boolean {
        return try {
            val nameBody = product.productName.toRequestBody("text/plain".toMediaType())
            val typeBody = product.productType.toRequestBody("text/plain".toMediaType())
            val priceBody = product.price.toString().toRequestBody("text/plain".toMediaType())
            val taxBody = product.tax.toString().toRequestBody("text/plain".toMediaType())

            val response = apiService.addProduct(nameBody, typeBody, priceBody, taxBody, null)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
    suspend fun addProductOffline(product: ProductEntity) {
        withContext(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
    }
    suspend fun getOfflineProducts(): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            productDao.getAllProducts()
        }
    }
    suspend fun syncOfflineProducts() {
        val offlineProducts = getOfflineProducts()
        for (product in offlineProducts) {
            try {
                val nameBody = product.productName.toRequestBody("text/plain".toMediaType())
                val typeBody = product.productType.toRequestBody("text/plain".toMediaType())
                val priceBody = product.price.toString().toRequestBody("text/plain".toMediaType())
                val taxBody = product.tax.toString().toRequestBody("text/plain".toMediaType())

                val response = apiService.addProduct(nameBody, typeBody, priceBody, taxBody, null)
                if (response.isSuccessful) {
                    productDao.deleteProduct(product.id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}