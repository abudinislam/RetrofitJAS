package kz.abudinislam.retrofitjas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository

class MainActivityViewModel(
    private var repository: MoviesRepository
) : ViewModel() {

    fun deleteSession() {

        viewModelScope.launch {
            repository.deleteSession()
        }



    }
}