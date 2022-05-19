package kz.abudinislam.retrofitjas.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.model.Session
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MoviesRepository(application)

//    private var prefSettings: SharedPreferences = application.getSharedPreferences(
//        APP_SETTINGS,
//        Context.MODE_PRIVATE
//    ) as SharedPreferences
//    private var editor: SharedPreferences.Editor = prefSettings.edit()


    fun deleteSession() {

        viewModelScope.launch {
            repository.deleteSession()
        }

//    private fun getSessionId(): String {
//        var session = ""
//        try {
//            session =
//                prefSettings.getString(SESSION_ID_KEY, "") as String
//        } catch (e: Exception) {
//        }
//        return session
//    }

//    companion object {
//
//        private var SESSION_ID = ""
//        const val APP_SETTINGS = "Settings"
//        const val SESSION_ID_KEY = "SESSION_ID"
//    }


    }
}