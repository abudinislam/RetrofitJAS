package kz.abudinislam.retrofitjas.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.LoginApprove
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.LoginUseCase

class LoginViewModel (private val loginUseCase: LoginUseCase, private val application: Application
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
            val session = loginUseCase.invoke(data.username, data.password)

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