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
import kz.abudinislam.retrofitjas.view.adapter.MoviesAdapter
import kotlin.coroutines.CoroutineContext
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import java.lang.Exception

class MoviesViewModel(application: Application) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao
    private val repository = MoviesRepository(application)


    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>>()
    val movies: LiveData<List<Result>>
        get() = _movies

    private val _openDetail = MutableLiveData<Result>()
    val openDetail: LiveData<Result>
        get() = _openDetail

    init {
        getPosts()
        movieDao = MovieDatabase.getDatabase(application).movieDao()
    }


    private fun getPosts() {
        launch {
            _loadingState.value = State.ShowLoading
//
            _movies.value = repository.getMovies()

            _loadingState.value = State.HideLoading
            _loadingState.value = State.Finish
        }

    }

    val recyclerViewItemClickListener = object : MoviesAdapter.OnMovieClickListener {

        override fun onMovieClick(result: Result) {
            _openDetail.value = result
        }
    }


    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        object Finish : State()

    }
}