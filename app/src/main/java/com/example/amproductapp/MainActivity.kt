package com.example.amproductapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.amproductapp.ui.navigation.AppNavGraph
import com.example.amproductapp.ui.viewModel.ProductViewModel
import com.example.amproductapp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavGraph()
        }

        lifecycleScope.launch {
            if(NetworkUtils.isOnline(this@MainActivity)){
                productViewModel.syncOfflineProducts()
            }
        }
    }
}