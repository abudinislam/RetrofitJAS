package kz.abudinislam.retrofitjas.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kotlin.coroutines.CoroutineContext

class AccountViewModel(private val context: Context): ViewModel(),CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao



    init {
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }
}