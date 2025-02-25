package com.example.amproductapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName: String,
    val productType: String,
    val price: Double,
    val tax: Double,
    val imagePath: String?
)