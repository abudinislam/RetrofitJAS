package kz.abudinislam.retrofitjas.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class AccountInfo (

    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val userAvatar: String? = null,
    var name: String? = null,
    val includeAdult: Boolean? = null,
    val username: String = "Гость",
    var avatar_uri: String? = null
        )