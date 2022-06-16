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

class LoginViewModel(
    application: Application, private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount


    fun login(data: LoginApprove, userName: String, password: String) {
        viewModelScope.launch {
            try {
                val result = validateInput(userName, password)
                if (result) {
                    _loadingState.value = LoadingState.ShowLoading
                    val session = loginUseCase.invoke(data.username, data.password)
                    if (session.isNotBlank()) {
                        _sessionId.value = session
                        _loadingState.value = LoadingState.Finish
                        _loadingState.value = LoadingState.ShowLoading

                    } else {
                        _loadingState.value = LoadingState.Finish
                        _errorInputName.value = true
                        _errorInputCount.value = true
                    }

                } else {
                    _errorInputName.value = true
                    _errorInputCount.value = true
                }

            } catch (e: Exception) {
                _loadingState.value = LoadingState.Finish
                _errorInputName.value = true
                _errorInputCount.value = true
            }

        }
    }

    private fun validateInput(userName: String, password: String): Boolean {
        var result = true
        if (userName.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputCount.value = false
    }

    sealed class LoadingState {
        object ShowLoading : LoadingState()
        object HideLoading : LoadingState()
        object Finish : LoadingState()
    }
}