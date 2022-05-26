package kz.abudinislam.retrofitjas.data.api

import kz.abudinislam.retrofitjas.data.*
import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.domain.model.LoginApprove
import kz.abudinislam.retrofitjas.domain.model.Result
import retrofit2.Response
import retrofit2.http.*

interface MoviesApi { // интерфейс для создания гет запроса
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Response<Result>

    @GET("authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Token>

    @POST("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body loginApprove: LoginApprove
    ): Response<Token>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body token: Token
    ): Response<Session>

    @Headers(
        "Accept: application/json;charset=utf-8",
        "Content-type: application/json;charset=utf-8"
    )
    @POST("account/{account_id}/favorite")
    suspend fun addFavorite(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Body postMovie: PostMovie
    )

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<Movie>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionId: Session
    )

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieStates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID
    ): Response<MovieState>

    @GET("account")
    suspend fun getAccountInfo(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID
    ): Response<AccountInfo>


    companion object {

        private var SESSION_ID = ""
        private var API_KEY = "6c77f278ed018c5240be802d3a7dfc62"
        private var LANGUAGE = "ru"
        private var PAGE = 1
    }


}