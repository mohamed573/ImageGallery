package com.example.githubapiparsing.worker

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.githubapiparsing.devicedatabase.RoomDatabase1
import com.example.githubapiparsing.repository.RepositoryClass
import retrofit2.HttpException

//worker class to implement the actual work to be run on the background 
class Worker( val context : WorkerParameters, val con : Context) : CoroutineWorker(con, context){

    companion object{
        val workerName = "com.example.githubapiparsing.worker.Worker"
    }
    override suspend fun doWork(): Result {
        val database = RoomDatabase1.getInstance(con)
        val repositoryInstance = RepositoryClass(database)

        try {
            repositoryInstance.refreshUsers()
        }catch (e: HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}
