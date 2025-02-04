package com.example.amproductapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.amproductapp.ui.listing.ProductListScreen

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object AddProduct : Screen("add_product")
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        composable(Screen.ProductList.route) {
            ProductListScreen(navController, hiltViewModel())
        }
        composable(Screen.AddProduct.route) {
            //AddProductScreen(navController)
        }
    }
}