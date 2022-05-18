package kz.abudinislam.retrofitjas.model.repository

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.abudinislam.retrofitjas.model.*
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.viewmodel.MainActivityViewModel
import java.lang.Exception

class MoviesRepository(application: Application) {

    private val api = RetrofitService.getPostApi()
    private val dao = MovieDatabase.getDatabase(application).movieDao()

    private val prefSettings: SharedPreferences =
        application.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
                as SharedPreferences
    private var context = application
    private var editor: SharedPreferences.Editor = prefSettings.edit()


    suspend fun getMovies(): List<Result>? {
        return withContext(
            Dispatchers.IO
        ) {
            try {
                val response = api.getMoviesList()
                if (response.isSuccessful) {
                    val result = response.body()?.results
                    if (!result.isNullOrEmpty()) {
                        dao.insertAll(result)
                    }
                    result
                } else {
                    dao.getAll()

                }
            } catch (e: Exception) {
                dao.getAll()
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): Result {
        return withContext(
            Dispatchers.IO
        ) {
            var result = dao.getMovieById(movieId)
            result
        }
    }

    suspend fun addOrDeleteFavorite(movieId: Int, sessionId: String): Result {
        return withContext(
            Dispatchers.IO
        ) {
            val movie = dao.getMovieById(movieId)
            val newMovie = movie.copy(favoritesState = !movie.favoritesState)
            dao.updateState(newMovie)
            newMovie
        }
    }

    suspend fun postMovie(movieId: Int, sessionId: String, movie: Result) {
        val postMovie = PostMovie(media_id = movieId, favorite = movie.favoritesState)
        api.addFavorite(session_id = sessionId, postMovie = postMovie)
    }

    suspend fun getPosts(sessionId: String): List<Result>? {
        return withContext(
            Dispatchers.IO
        ) {
            try {
                val response = api.getFavorites(sessionId)
                if (response.isSuccessful) {
                    val result = response.body()?.results
                    if (!result.isNullOrEmpty()) {
                        dao.insertAll(result)
                    }
                    result
                } else {
                    dao.getAll()
                }
            } catch (e: Exception) {
                dao.getAll()
            }
        }
    }

    suspend fun login(data: LoginApprove) {
        return withContext(
            Dispatchers.IO
        ) {
            val responseGet = api.getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(

                    username = data.username,
                    password = data.password,
                    request_token = responseGet.body()?.request_token as String
                )

                val responseApprove =
                    api.approveToken(loginApprove = loginApprove)
                if (responseApprove.isSuccessful) {
                    val session =
                        api.createSession(token = responseApprove.body() as Token)
                    if (session.isSuccessful) {
                    }
                }
            }
        }
    }

    suspend fun deleteSession() {
        return withContext(
            Dispatchers.IO
        ) {
            SESSION_ID = getSessionId()
            try {
                RetrofitService.getPostApi()
                    .deleteSession(sessionId = Session(session_id = MainActivityViewModel.SESSION_ID))
            } catch (e: Exception) {
                editor.clear().commit()
            }
        }
    }

    suspend fun getSessionId() {
        withContext(
            Dispatchers.IO
        ) {
            var session = ""
            try {
                session =
                    prefSettings.getString(MainActivityViewModel.SESSION_ID_KEY, "") as String
            } catch (e: Exception) {
                return session
            }

        }
    }




    companion object {

        private var SESSION_ID: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }


}