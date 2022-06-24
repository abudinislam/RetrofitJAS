package kz.abudinislam.retrofitjas.domain.repository

import kz.abudinislam.retrofitjas.data.cast.CreditResponse
import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.domain.model.Result
import retrofit2.Response

interface MovieRepository {

    suspend fun getMovies(): List<Result>?

    suspend fun getDetail(movieId: Int, sessionId: String?): Result

    suspend fun addOrDeleteFavorite(movieId: Int, sessionId: String): Result

    suspend fun postMovie(movieId: Int, sessionId: String, movie: Result)

    suspend fun getPosts(sessionId: String): List<Result>?

    fun getSessionId(): String

    suspend fun deleteSession()

    suspend fun login(username: String, password: String): String

    fun insertUser(user: AccountInfo)

    suspend fun getCreditResponse(movieId: Int): Response<CreditResponse>



}