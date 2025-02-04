package com.example.amproductapp.ui.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.amproductapp.data.model.Product
import com.example.amproductapp.ui.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddProduct.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}
@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image ?: ""),
                contentDescription = product.productName,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = product.productName, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Price: ₹${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tax: ${product.tax}%", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
