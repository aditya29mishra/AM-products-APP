package com.example.amproductapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amproductapp.data.local.ProductEntity
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.data.repository.ProductRepository
import com.example.amproductapp.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun fetchProducts(context: android.content.Context) {
        viewModelScope.launch {
            val localProducts = repository.getOfflineProducts().map { it.toProduct() }
            val onlineProducts = if (NetworkUtils.isOnline(context)) {
                repository.getProducts()
            } else emptyList()

            _products.value = localProducts + onlineProducts
        }
    }

    fun addProduct(product: Product, context: android.content.Context, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (NetworkUtils.isOnline(context)) {
                val success = repository.addProductToServer(product)
                onResult(success, if (success) "Product added successfully!" else "Failed to add product.")
            } else {
                val productEntity = ProductEntity(
                    productName = product.productName,
                    productType = product.productType,
                    price = product.price.toDouble(),
                    tax = product.tax,
                    imagePath = null
                )
                repository.addProductOffline(productEntity)
                onResult(true, "Product saved offline. It will be synced when online.")
            }
        }
    }

    fun syncOfflineProducts() {
        viewModelScope.launch {
            repository.syncOfflineProducts()
        }
    }
}
fun ProductEntity.toProduct(): Product {
    return Product(
        productName = this.productName,
        productType = this.productType,
        price = this.price.toString(),
        tax = this.tax,
        image = this.imagePath
    )
}
