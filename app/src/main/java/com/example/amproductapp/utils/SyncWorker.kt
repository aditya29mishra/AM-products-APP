package com.example.amproductapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.amproductapp.data.repository.ProductRepository
import com.example.amproductapp.data.repository.ProductRepositorySingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val repository = ProductRepositorySingleton.getInstance(context) // ✅ Fix Repository Access

    override fun doWork(): Result {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                repository.syncOfflineProducts()
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
