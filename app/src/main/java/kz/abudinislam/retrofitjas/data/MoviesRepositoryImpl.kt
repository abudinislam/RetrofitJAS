package kz.abudinislam.retrofitjas.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.abudinislam.retrofitjas.data.api.MoviesApi
import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.domain.model.LoginApprove
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val dao: MovieDao,
    private val sharedPreferences: SharedPreferences,
    private val application: Application
):MovieRepository{

    private var prefSettings: SharedPreferences = application.getSharedPreferences(
        APP_SETTINGS,
        Context.MODE_PRIVATE
    ) as SharedPreferences
    private var editor: SharedPreferences.Editor = prefSettings.edit()

    override fun insertUser(user: AccountInfo) {
        dao.insertUser(user)
    }

    override suspend fun getMovies(): List<Result>? {
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

    override suspend fun getDetail(movieId: Int, sessionId: String?): Result {
        return withContext(Dispatchers.IO) {
            try {
                val result = dao.getMovieById(movieId)
                if (sessionId != null) {
                    val response = api.getMovieStates(
                        movieId,
                        session_id = sessionId
                    )
                    if (response.isSuccessful) {
                        val favoriteState = response.body()?.favorite as Boolean
                        result.favoritesState = favoriteState
                    }
                }
                dao.updateState(result)
                result
            } catch (e: Exception) {
                dao.getMovieById(movieId)
            }
        }
    }

    override suspend fun addOrDeleteFavorite(movieId: Int, sessionId: String): Result {
        return withContext(
            Dispatchers.IO
        ) {
            val movie = dao.getMovieById(movieId)
            val newMovie = movie.copy(favoritesState = !movie.favoritesState)
            dao.updateState(newMovie)
            newMovie
        }
    }

    override suspend fun postMovie(movieId: Int, sessionId: String, movie: Result) {
        val postMovie = PostMovie(media_id = movieId, favorite = movie.favoritesState)
        api.addFavorite(session_id = sessionId, postMovie = postMovie)
    }

    override suspend fun getPosts(sessionId: String): List<Result>? {
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

    override fun getSessionId(): String {
        var session = ""
        try {
            session =
                prefSettings.getString(SESSION_ID_KEY, "") as String
        } catch (e: Exception) {
        }
        return session
    }

    override suspend fun deleteSession() {
        SESSION_ID = getSessionId()
        try {
            api
                .deleteSession(sessionId = Session(session_id = SESSION_ID))
        } catch (e: Exception) {
            editor.clear().commit()
        }
    }

    override suspend fun login(username: String, password: String): String {
        var session = ""
        try {
            val responseGet = api.getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(
                    username = username,
                    password = password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = api.approveToken(
                    loginApprove = loginApprove
                )
                if (responseApprove.isSuccessful) {
                    val response =
                        api.createSession(token = responseApprove.body() as Token)
                    if (response.isSuccessful) {
                        session = response.body()?.session_id as String
                    }
                }
            } else {
                Toast.makeText(application, "Нет подключение к интернету", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(application, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
        }

        return session
    }

    companion object {

        private var SESSION_ID: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }


}