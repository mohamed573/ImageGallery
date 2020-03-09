package com.example.githubapiparsing

import android.app.Application
import androidx.work.*
import com.example.githubapiparsing.worker.Worker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        workUnit()
    }

    private fun setUpWork() {

        //work constraints
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresDeviceIdle(true)
            .build()

        //workRequest
        val workRequest = PeriodicWorkRequestBuilder<Worker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        //work manager to schedule work request
        WorkManager.getInstance().enqueueUniquePeriodicWork(Worker.workerName, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    //running the setUpWork function on a separate thread using coroutines
    fun workUnit()
    {
        applicationScope.launch {
            setUpWork()
        }
    }

}