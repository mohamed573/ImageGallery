package com.example.githubapiparsing.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiparsing.databinding.HeaderLayoutBinding
import com.example.githubapiparsing.databinding.ItemLayoutBinding
import com.example.githubapiparsing.domain.DomainModel
import com.example.githubapiparsing.network.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_USERS = 1

/*class RecyclerViewAdapters : ListAdapter<Users, RecyclerViewAdapters.UsersViewHolder>(DiffCallBack)*/
class RecyclerViewAdapters(val clickListener : OnClickListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallBack){

    override fun getItemViewType(position: Int): Int {
       return when(getItem(position)) {
           is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
           is DataItem.UsersItem -> ITEM_VIEW_TYPE_USERS
       }
    }

    //creating view holders based on the type of view passed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //return UsersViewHolder.usersViewHolder(parent)

        return when(viewType) {
            ITEM_VIEW_TYPE_USERS -> UsersViewHolder.usersViewHolder(parent)
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.createHeaderView(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }

    }

    //submitting the new list of items using coroutines
    private val scope = CoroutineScope(Dispatchers.Default)

    fun addAndSubmitNewList(list : List<DomainModel>?) {
        scope.launch {
            val items = when(list) {
                null -> listOf(DataItem.HeaderItem)
                else -> listOf(DataItem.HeaderItem) + list.map { DataItem.UsersItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UsersViewHolder -> {
                val itemGotten = getItem(position) as DataItem.UsersItem
                holder.itemView.setOnClickListener{
                    clickListener.onClick(itemGotten)
                }
                holder.bind(itemGotten.users)
            }
        }
    }

    //creating header viewHolder class for the recycler view
    class HeaderViewHolder(private val headerBinding : HeaderLayoutBinding) : RecyclerView.ViewHolder(headerBinding.root) {
        companion object {
        fun createHeaderView(parent: ViewGroup) : HeaderViewHolder{
            val binding2 =
                HeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(binding2)
        }}
    }

    //creating users viewholder class for the recycler view
    class UsersViewHolder (private val itemBinding : ItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DomainModel?) {
            itemBinding.user = item
            itemBinding.executePendingBindings()
        }

        companion object {
            fun usersViewHolder(parent: ViewGroup): UsersViewHolder {
                val binding =
                    ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UsersViewHolder(binding)
            }
        }

    }

 //diff util object to update the list of users for the recyclerView Adapter
 companion object DiffCallBack: DiffUtil.ItemCallback<DataItem>(){
     override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
         return oldItem.id == newItem.id
     }

     override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
         return oldItem === newItem
     }
    }

}

class OnClickListener (val clickListener : (user : DataItem.UsersItem) -> Unit) {
    fun onClick (user : DataItem.UsersItem) {
        return clickListener(user)
    }
}

//sealed class to hold the two data items
sealed class DataItem {
    abstract val id : Int
    data class UsersItem (val users : DomainModel) : DataItem(){
        override val id: Int
            get() = users.id
    }

    object HeaderItem : DataItem(){
        override val id: Int
            get() = Int.MIN_VALUE
    }
}