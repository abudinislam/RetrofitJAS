package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
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
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository

class DetailViewModel(application: Application) :  AndroidViewModel(application) {
    private val movieDao: MovieDao
    private val repository = MoviesRepository(application)

    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState

    init {
        movieDao = MovieDatabase.getDatabase(application).movieDao()
    }


    fun getMovieDetails(movieId: Int, sessionId: String?) {
        viewModelScope.launch {
            _loadingState.value = StateDetail.ShowLoading


            _liveDataDetail.value = repository.getDetail(movieId, sessionId)


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
//            val favoritesState = withContext(Dispatchers.IO) {
//                val movie = movieDao.getMovieById(movieId)
//                val newMovie = movie.copy(favoritesState = !movie.favoritesState)
//                movieDao.updateState(newMovie)
//                newMovie
//            }
            val movie = repository.addOrDeleteFavorite(movieId,sessionId)
            _liveDataDetail.value = movie
            repository.postMovie(movieId,sessionId,movie)

//            val postMovie = PostMovie(media_id = movieId, favorite = favoritesState.favoritesState)
//            RetrofitService.getPostApi().addFavorite(session_id = sessionId, postMovie = postMovie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }

}