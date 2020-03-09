package com.example.githubapiparsing.network

import android.os.Parcelable
import com.example.githubapiparsing.devicedatabase.RoomUsers
import com.example.githubapiparsing.domain.DomainModel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Users (
    val login : String,
    val id : Int,
    val node_id : String,
    val avatar_url : String,
    val gravatar_id : String,
    val url : String,
    val html_url : String,
    val followers_url : String,
    val following_url : String,
    val gists_url : String,
    val starred_url : String,
    val subscriptions_url : String,
    val organizations_url : String,
    val repos_url : String,
    val events_url : String,
    val received_events_url : String,
    val type : String,
    val site_admin : Boolean
) : Parcelable


//extension function to convert the network data object to the domain object

fun List<Users>.asDataBaseModel() : List<RoomUsers>{
    return map {
        RoomUsers(
            login = it.login,
            id = it.id,
            node_id = it.node_id,
            avatar_url = it.avatar_url
        )
    }
}