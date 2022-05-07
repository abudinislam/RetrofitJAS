package kz.abudinislam.retrofitjas.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.abudinislam.retrofitjas.model.PostMovie
import kotlin.coroutines.CoroutineContext
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.model.data.MovieDao

class DetailViewModel(private val context: Context) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao

    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState

    init {
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }


    fun getMovieDetails(movieId: Int) {
        launch {
            _loadingState.value = StateDetail.ShowLoading
            val list = withContext(Dispatchers.IO) {
                var result = movieDao.getMovieById(movieId)
                result
            }

            _liveDataDetail.value = list


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        launch {
            val favoritesState = withContext(Dispatchers.IO) {
                val movie = movieDao.getMovieById(movieId)
                val newMovie = movie.copy(favoritesState = !movie.favoritesState)
                movieDao.updateState(newMovie)
                newMovie
            }
            _liveDataDetail.value = favoritesState
            val postMovie = PostMovie(media_id = movieId, favorite = favoritesState.favoritesState)
            RetrofitService.getPostApi().addFavorite(session_id = sessionId, postMovie = postMovie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }

}