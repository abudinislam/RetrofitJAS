package kz.abudinislam.retrofitjas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.domain.model.Result

@Database(entities = [Result::class, AccountInfo::class], version = 6)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "app_database.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }

    }
}