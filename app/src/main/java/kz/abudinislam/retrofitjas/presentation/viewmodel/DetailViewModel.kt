package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.data.cast.Cast
import kz.abudinislam.retrofitjas.domain.usecase.AddOrDeleteFavoriteUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetCreditResponseUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetDetailUseCase
import kz.abudinislam.retrofitjas.domain.usecase.PostMovieUseCase

class DetailViewModel(
    private val getDetailUseCase: GetDetailUseCase,
    private val addOrDeleteFavoriteUseCase: AddOrDeleteFavoriteUseCase,
    private val postMovieUseCase: PostMovieUseCase,
    private val getCreditResponseUseCase: GetCreditResponseUseCase
) : ViewModel() {


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState

    private val _actors = MutableLiveData<List<Cast>>()
    val actors: LiveData<List<Cast>>
        get() = _actors



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

    fun getCreditResponse(movieId: Int) {
        viewModelScope.launch {
            _actors.value = getCreditResponseUseCase.invoke(movieId).body()?.cast
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }

}