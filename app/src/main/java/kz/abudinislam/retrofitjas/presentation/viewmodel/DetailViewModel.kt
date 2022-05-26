package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.AddOrDeleteFavoriteUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetDetailUseCase
import kz.abudinislam.retrofitjas.domain.usecase.PostMovieUseCase

class DetailViewModel(
    private val getDetailUseCase: GetDetailUseCase,
    private val addOrDeleteFavoriteUseCase: AddOrDeleteFavoriteUseCase,
    private val postMovieUseCase: PostMovieUseCase
) : ViewModel() {


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState



    fun getMovieDetails(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            _loadingState.value = StateDetail.ShowLoading


            _liveDataDetail.value = getDetailUseCase.invoke(movieId, sessionId)


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
//
            val movie = addOrDeleteFavoriteUseCase.invoke(movieId, sessionId)
            _liveDataDetail.value = movie
            postMovieUseCase.invoke(movieId, sessionId, movie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }

}