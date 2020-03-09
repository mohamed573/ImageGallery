package com.example.githubapiparsing.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//data source for the views and viewModels in the app
@Parcelize
data class DomainModel (
    val login : String,
    val id : Int,
    val node_id : String,
    val avatar_url : String
): Parcelable