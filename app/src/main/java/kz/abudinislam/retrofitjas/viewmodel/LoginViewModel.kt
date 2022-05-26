package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.LoginApprove
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository

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
            if (session.isNotBlank()){
                _loadingState.value = LoadingState.Finish
            }
            else{
                _loadingState.value = LoadingState.HideLoading
            }

        }
    }


    sealed class LoadingState {
        object ShowLoading : LoadingState()
        object HideLoading : LoadingState()
        object Finish : LoadingState()
    }
}