package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.GetDetailUseCase
import kz.abudinislam.retrofitjas.domain.usecase.GetPostsUseCase
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel(),CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main




    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies

    private val _loadingState = MutableLiveData<MoviesViewModel.State>()
    val loadingState: LiveData<MoviesViewModel.State>
        get() = _loadingState


    fun getPosts(session: String) {
        launch {
            _loadingState.value = MoviesViewModel.State.ShowLoading
            _movies.value =getPostsUseCase.invoke(sessionId = session)

            _loadingState.value = MoviesViewModel.State.HideLoading
            _loadingState.value = MoviesViewModel.State.Finish
        }
    }

}