package com.example.amproductapp.data.model

data class Product(
    val productName: String,
    val price: String,
    val tax: Double,
    val productType: String,
    val image: String?
)