package com.example.amproductapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository

) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getProducts()
                _products.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        imageFile: ByteArray? = null
    ){
        viewModelScope.launch {
            val nameBody = productName.toRequestBody("text/plain".toMediaType())
            val typeBody = productType.toRequestBody("text/plain".toMediaType())
            val priceBody = price.toRequestBody("text/plain".toMediaType())
            val taxBody = tax.toRequestBody("text/plain".toMediaType())

            val imagePart = imageFile?.let {
                MultipartBody.Part.createFormData(
                    "files[]", "image.jpg",
                    it.toRequestBody("image/jpeg".toMediaType())
                )
            }
            repository.addProduct(nameBody, typeBody, priceBody, taxBody, imagePart)
        }
    }
}