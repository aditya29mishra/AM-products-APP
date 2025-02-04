package com.example.amproductapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.*
import com.example.amproductapp.ui.navigation.AppNavGraph
import com.example.amproductapp.ui.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequestBuilder
import com.example.amproductapp.workers.SyncWorker

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val productViewModel: ProductViewModel = hiltViewModel()
            AppNavGraph()
        }
        scheduleSync()
    }
    private fun scheduleSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncOfflineProducts",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}