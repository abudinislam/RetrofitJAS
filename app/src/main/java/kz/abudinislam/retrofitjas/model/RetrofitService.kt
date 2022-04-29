package kz.abudinislam.retrofitjas.model

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//object RetrofitService {
//
//    private const val BASE_URL = "https://api.themoviedb.org/3/"
//
//    fun getPostApi(): MoviesApi {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(getOkHttp())
//            .build()
//        return retrofit.create(MoviesApi::class.java)
//    }
//
//    private fun getOkHttp(): OkHttpClient {
//        val okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(getLoggingInterceptor())
//        return okHttpClient.build()
//    }
//
//    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
//            override fun log(message: String) {
//                Log.d("OkHttp", message)
//            }
//        }).apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//}
//
////object RetrofitInstance {
////    private const val BASE_URL = "https://api.themoviedb.org/3/"
////
////    fun getPostApi(): kz.abudinislam.retrofitjas.model.api.MoviesApi {
////        val retrofit = Retrofit.Builder()
////            .baseUrl(BASE_URL)
////            .addConverterFactory(GsonConverterFactory.create())
////            .build()
////        return retrofit.create(kz.abudinislam.retrofitjas.model.api.MoviesApi::class.java)
////    }
////}
//
//interface MoviesApi {
//
//    @GET("movie/popular")
//    fun getMoviesList(
//        @Query("api_key") apiKey:String = "6c77f278ed018c5240be802d3a7dfc62",
//        @Query("language") language:String = "ru",
//        @Query("page") page:Int = 1
//    ): Call<Movie>
//
//
//}
