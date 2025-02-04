package com.example.amproductapp.data.remote

import com.example.amproductapp.data.model.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("get")
    suspend fun getProducts(): List<Product>

    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part("productName") productName: RequestBody,
        @Part("productType") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part image: MultipartBody.Part?
    ): ApiResponse
}