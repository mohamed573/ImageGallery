package com.example.githubapiparsing.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.githubapiparsing.devicedatabase.RoomDatabase1
import com.example.githubapiparsing.devicedatabase.asDomainModel
import com.example.githubapiparsing.domain.DomainModel
import com.example.githubapiparsing.network.GitHubApiService
import com.example.githubapiparsing.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//repository class interfaces for viewmodel, network and database interaction. Great for clean modular architecture
class RepositoryClass (val database : RoomDatabase1){

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val gitHubServ = GitHubApiService.createService().getUsers().await()
            database.dao.insertUsers(gitHubServ.asDataBaseModel())
        }
    }

    val usersList : LiveData<List<DomainModel>> = Transformations.map(database.dao.retreiveUsers()){
        it.asDomainModel()
    }
}
