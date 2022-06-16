package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.DeleteSessionUseCase

class MainActivityViewModel(
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel() {

    fun deleteSession() {
        viewModelScope.launch {
            deleteSessionUseCase.invoke()
        }



    }
}