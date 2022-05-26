package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.AccountInfo
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import kotlin.coroutines.CoroutineContext

class AccountViewModel(private var repository: MoviesRepository
) : ViewModel() {


    fun insertUser(user: AccountInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }

    }
}