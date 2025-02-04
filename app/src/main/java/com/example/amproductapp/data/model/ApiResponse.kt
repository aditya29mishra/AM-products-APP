package com.example.amproductapp.data.model

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val productId: Int? = null,
    val productDetails: Product? = null
)