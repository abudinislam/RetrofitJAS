package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.Session
import kz.abudinislam.retrofitjas.model.api.RetrofitService
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