package kz.abudinislam.retrofitjas.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.abudinislam.retrofitjas.model.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM movie_table WHERE id == :movieId")
    fun getMovieById(movieId : Int): Result

}