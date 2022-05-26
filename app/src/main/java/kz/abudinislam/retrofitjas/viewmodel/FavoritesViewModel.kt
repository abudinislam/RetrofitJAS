package kz.abudinislam.retrofitjas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(
    private var repository: MoviesRepository
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
            _movies.value = repository.getPosts(sessionId = session)

            _loadingState.value = MoviesViewModel.State.HideLoading
            _loadingState.value = MoviesViewModel.State.Finish
        }
    }

}