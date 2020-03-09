package com.example.githubapiparsing.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiparsing.devicedatabase.RoomDatabase1
import com.example.githubapiparsing.domain.DomainModel
import com.example.githubapiparsing.network.GitHubApiService
import com.example.githubapiparsing.network.Users
import com.example.githubapiparsing.repository.RepositoryClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class UserApiStatus {
    LOADING, DONE, ERROR
}
class HomePageViewModel(app : Application) : AndroidViewModel(app) {
    var appRepository = RepositoryClass(RoomDatabase1.getInstance(app))


    private val _NetworkError = MutableLiveData<Boolean>()
    val NetworkError : LiveData<Boolean>
    get() = _NetworkError

    private val _ApiResponse = MutableLiveData<String>()
    val ApiResponse : LiveData<String>
    get() = _ApiResponse

    private val _MoveUser = MutableLiveData<DomainModel>()
    val MoveUser : LiveData<DomainModel>
    get() = _MoveUser

    //live data that will be observed in order to display error icon or loading icon
    private val _UserStatus = MutableLiveData<UserApiStatus>()
    val UserStatus : LiveData<UserApiStatus>
    get() = _UserStatus

    //liveData to hold the list of users returned from the Api
    val Users = appRepository.usersList

    //coroutine job for asychronous processing
   private var viewModelJob = Job()
   private val coroutineScope2 = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        gitHubApiService()
    }

   private fun gitHubApiService() {

        coroutineScope2.launch {
           try {
               _UserStatus.value = UserApiStatus.LOADING
               appRepository.refreshUsers()
               _UserStatus.value = UserApiStatus.DONE
           }catch (e : Exception) {
               _ApiResponse.value = "Error ${e.message}"
               _UserStatus.value = UserApiStatus.ERROR
               _NetworkError.value = true
           }
       }
    }

    fun diplayUserDetails(user : DomainModel) {
        _MoveUser.value = user
    }

    fun doneDisplayingUser() {
        _MoveUser.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
