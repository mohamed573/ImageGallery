package com.example.githubapiparsing.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiparsing.domain.DomainModel
import com.example.githubapiparsing.network.Users

class DetailFragmentViewModel(users : DomainModel) : ViewModel() {
   private val _SelectedUser = MutableLiveData<DomainModel>()
    val SelectedUser : LiveData<DomainModel>
    get() = _SelectedUser
   init {
       _SelectedUser.value = users
   }
}