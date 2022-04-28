package kz.abudinislam.retrofitjas.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class LoginViewModel:ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext =Dispatchers.Main



}