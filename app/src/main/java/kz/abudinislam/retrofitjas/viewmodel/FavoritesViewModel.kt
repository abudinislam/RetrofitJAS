package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(application: Application) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao
    private val repository = MoviesRepository(application)


    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies

    private val _loadingState = MutableLiveData<MoviesViewModel.State>()
    val loadingState: LiveData<MoviesViewModel.State>
        get() = _loadingState

    init {
        movieDao = MovieDatabase.getDatabase(application).movieDao()
    }

    fun getPosts(session: String) {
        launch {
            _loadingState.value = MoviesViewModel.State.ShowLoading
//            val list = withContext(Dispatchers.IO) {
//                try {
//                    val response = RetrofitService.getPostApi().getFavorites(session_id = session)
//                    if (response.isSuccessful) {
//                        val result = response.body()?.results
//                        if (!result.isNullOrEmpty()) {
//                            movieDao.insertAll(result)
//                        }
//                        result
//                    } else {
//                        movieDao.getAll()
//                    }
//                } catch (e: Exception) {
//                    movieDao.getAll()
//                }
//            }
            _movies.value = repository.getPosts(sessionId = session)

            _loadingState.value = MoviesViewModel.State.HideLoading
            _loadingState.value = MoviesViewModel.State.Finish
        }
    }

}