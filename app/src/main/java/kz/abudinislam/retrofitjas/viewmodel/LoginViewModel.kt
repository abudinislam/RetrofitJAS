package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.LoginApprove
import kz.abudinislam.retrofitjas.model.Token
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import kotlin.coroutines.CoroutineContext

class LoginViewModel (private var repository: MoviesRepository,private val application: Application
) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId

    fun login(data: LoginApprove) {
        viewModelScope.launch {
            _loadingState.value = LoadingState.ShowLoading
            val session = repository.login(data.username, data.password)

            _sessionId.value = session
            _loadingState.value = LoadingState.HideLoading
            if (session.isNotBlank()){
                _loadingState.value = LoadingState.Finish
            }
            else{
                Toast.makeText(application, "Неверные данные", Toast.LENGTH_SHORT).show()
            }

        }
    }


    sealed class LoadingState {
        object ShowLoading : LoadingState()
        object HideLoading : LoadingState()
        object Finish : LoadingState()
    }
}