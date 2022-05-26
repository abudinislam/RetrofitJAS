package kz.abudinislam.retrofitjas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository

class DetailViewModel(
    private var repository: MoviesRepository
) : ViewModel() {


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState



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
//
            val movie = repository.addOrDeleteFavorite(movieId, sessionId)
            _liveDataDetail.value = movie
            repository.postMovie(movieId, sessionId, movie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }

}