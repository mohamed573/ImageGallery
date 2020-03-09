package com.example.githubapiparsing

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapiparsing.network.Users
import com.example.githubapiparsing.overview.UserApiStatus


@BindingAdapter("imageUrl")
fun ImageView.setImg (imgUrl : String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }

}

@BindingAdapter("UserApiStatus")
fun ImageView.setIcon(status : UserApiStatus?) {
    when(status) {
       UserApiStatus.ERROR ->  { visibility = View.VISIBLE
       setImageResource(R.drawable.ic_connection_error)}
       UserApiStatus.DONE ->  { visibility = View.GONE }
        UserApiStatus.LOADING -> {visibility = View.GONE}
}}

@BindingAdapter("ShowProgressBar")
fun ProgressBar.show(status : UserApiStatus?)
{
    when(status) {
        UserApiStatus.ERROR -> { visibility = View.GONE}
        UserApiStatus.DONE -> { visibility = View.GONE}
        UserApiStatus.LOADING-> {visibility = View.VISIBLE}
    }
}

@BindingAdapter("NetworkError", "playist")
fun ProgressBar.networkError(isError : Boolean?, users : Any?) {
   if(users == null) {
        visibility = View.VISIBLE
    }
    if(isError == true){
        visibility = View.GONE
    }
}
