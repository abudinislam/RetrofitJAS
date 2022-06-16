package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.DeleteSessionUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetMoviesUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetPostsUseCase
import kz.abudinislam.retrofitjas.presentation.view.adapter.MoviesAdapter
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main


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
//        movieDao = MovieDatabase.getDatabase(application).movieDao()
    }


    private fun getPosts() {
        launch {
            _loadingState.value = State.ShowLoading
//
            _movies.value = getMoviesUseCase.invoke()

            _loadingState.value = State.HideLoading
            _loadingState.value = State.Finish
        }

    }

    val recyclerViewItemClickListener = object : MoviesAdapter.OnMovieClickListener {

        override fun onMovieClick(result: Result) {
            _openDetail.value = result
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            deleteSessionUseCase.invoke()
        }
    }



    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        object Finish : State()
    }


}

