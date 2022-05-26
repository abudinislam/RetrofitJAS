package kz.abudinislam.retrofitjas.di

import android.content.Context
import android.content.SharedPreferences
import kz.abudinislam.retrofitjas.model.api.MoviesApi
import kz.abudinislam.retrofitjas.model.api.RetrofitService
import kz.abudinislam.retrofitjas.model.data.MovieDao
import kz.abudinislam.retrofitjas.model.data.MovieDatabase
import kz.abudinislam.retrofitjas.model.repository.MoviesRepository
import kz.abudinislam.retrofitjas.viewmodel.*
import org.koin.dsl.module

val networkModule = module {
    single { getMovieApi() }
}

val daoModule = module {
    single { getPostDao(context = get()) }

}

val repositoryModule = module {
    single { getSharedPreferences(context = get()) }
    single { MoviesRepository(api = get (), dao = get(), sharedPreferences = get(), application = get())}
}



val viewModelModule = module {
    single { DetailViewModel(repository = get()) }
    single { FavoritesViewModel(repository = get()) }
    single { MainActivityViewModel(repository = get()) }
    single { LoginViewModel(repository = get(), application = get()) }
    single { MoviesViewModel(repository = get()) }
    single { AccountViewModel(repository = get()) }

}
val appModule = networkModule + daoModule + repositoryModule + viewModelModule




private fun getMovieApi(): MoviesApi = RetrofitService.getPostApi()
private fun getPostDao (context: Context):MovieDao =MovieDatabase.getDatabase(context).movieDao()
private fun getSharedPreferences (context: Context):SharedPreferences{
    return  context.getSharedPreferences("default",  android.content.Context.MODE_PRIVATE)}
