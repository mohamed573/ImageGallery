package com.example.githubapiparsing.devicedatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubapiparsing.domain.DomainModel

//database entity class
@Entity()
data class RoomUsers(
    val login : String,
    @PrimaryKey
    val id : Int,
    val node_id : String,
    val avatar_url : String
)

//extension function to convert the database list to the app domain model list

fun List<RoomUsers>.asDomainModel() : List<DomainModel>{
    return map {
        DomainModel(
            login = it.login,
            id = it.id,
            node_id = it.node_id,
            avatar_url = it.avatar_url
        )
    }
}



//Room Dao
@Dao
interface RoomUsersDao{
    @Insert
    fun insertUsers(users : List<RoomUsers>)

    @Query("select * from roomusers")
    fun retreiveUsers() : LiveData<List<RoomUsers>>
}

//Room database instance
@Database(entities = [RoomUsers::class],version = 1, exportSchema = false)
abstract class RoomDatabase1 : RoomDatabase(){
    abstract val dao : RoomUsersDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase1? = null

        fun getInstance(context: Context) : RoomDatabase1 {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        RoomDatabase1::class.java,
                        "github_users").build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}