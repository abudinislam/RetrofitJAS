package kz.abudinislam.retrofitjas.model.data

import androidx.room.*
import kz.abudinislam.retrofitjas.model.AccountInfo
import kz.abudinislam.retrofitjas.model.Result


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: AccountInfo)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM movie_table WHERE id == :movieId")
    fun getMovieById(movieId: Int): Result

    @Query("SELECT * FROM users_table WHERE id == :userId")
    fun getUserById(userId: Int): AccountInfo

    @Update
    suspend fun updateState(movie: Result)


}