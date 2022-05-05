package kz.abudinislam.retrofitjas.model.data

import androidx.room.*
import kz.abudinislam.retrofitjas.model.Result


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM movie_table WHERE id == :movieId")
    fun getMovieById(movieId : Int): Result


    @Update
    suspend fun updateState(movie : Result)


}