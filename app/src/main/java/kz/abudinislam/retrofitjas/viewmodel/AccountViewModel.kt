package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kotlin.coroutines.CoroutineContext

class AccountViewModel(application: Application): ViewModel(),CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao



    init {
        movieDao = MovieDatabase.getDatabase(application).movieDao()
    }
}