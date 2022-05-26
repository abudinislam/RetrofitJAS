package kz.abudinislam.retrofitjas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.usecase.InsertUserUseCase

class AccountViewModel(private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {




    fun insertUser(user: AccountInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUserUseCase.invoke(user)
        }

    }
}