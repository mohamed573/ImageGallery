package com.example.githubapiparsing.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapiparsing.domain.DomainModel
import com.example.githubapiparsing.network.Users

class DetailFragmentViewModelFactory(
    private val users: DomainModel
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java)) {
            return DetailFragmentViewModel(users) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}