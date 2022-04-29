package kz.abudinislam.retrofitjas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.view.MoviesAdapter
import kotlin.coroutines.CoroutineContext
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.api.RetrofitInstance

class MoviesViewModel:ViewModel(),CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main


    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
    get() =_loadingState

    private val _movies = MutableLiveData<List<Result>>()
    val movies :LiveData<List<Result>>
    get() = _movies

    private val _openDetail = MutableLiveData<Result>()
    val openDetail : LiveData<Result>
    get() = _openDetail

    init {
        getPosts()
    }


    private fun getPosts() {
        launch {
            _loadingState.value = State.ShowLoading
            val response = RetrofitInstance.getPostApi().getMoviesList()
            if (response.isSuccessful){
                _movies.value = response.body()?.results
            }
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