package kz.abudinislam.retrofitjas.di

import android.content.Context
import android.content.SharedPreferences
import kz.abudinislam.retrofitjas.data.api.MoviesApi
import kz.abudinislam.retrofitjas.data.api.RetrofitService
import kz.abudinislam.retrofitjas.data.MovieDao
import kz.abudinislam.retrofitjas.data.MovieDatabase
import kz.abudinislam.retrofitjas.data.MoviesRepositoryImpl
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository
import kz.abudinislam.retrofitjas.domain.usecase.*
import kz.abudinislam.retrofitjas.presentation.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { getMovieApi() }
}

val daoModule = module {
    single { getPostDao(context = get()) }

}

val repositoryModule = module {
    single { getSharedPreferences(context = get()) }
    single<MovieRepository> {
        MoviesRepositoryImpl(
            api = get(),
            dao = get(),
            sharedPreferences = get(),
            application = get()
        )
    }
}


val viewModelModule = module {
    viewModel {
        DetailViewModel(
            getDetailUseCase = get(),
            addOrDeleteFavoriteUseCase = get(),
            postMovieUseCase = get()
        )

    }
    viewModel { FavoritesViewModel(getPostsUseCase = get()) }
    viewModel { MainActivityViewModel(deleteSessionUseCase = get()) }
    viewModel { LoginViewModel(loginUseCase = get(), application = get()) }
    viewModel {
        MoviesViewModel(
            getPostsUseCase = get(),
            getMoviesUseCase = get()
        )
    }
    viewModel { AccountViewModel(insertUserUseCase = get()) }

}

val useCaseModule = module {
    single { AddOrDeleteFavoriteUseCase(movieRepository = get()) }
    single { DeleteSessionUseCase(movieRepository = get()) }
    single { GetDetailUseCase(movieRepository = get()) }
    single { GetMoviesUseCase(movieRepository = get()) }
    single { GetPostsUseCase(movieRepository = get()) }
    single { GetSessionIdUseCase(movieRepository = get()) }
    single { LoginUseCase(movieRepository = get()) }
    single { PostMovieUseCase(movieRepository = get()) }
}


val appModule = networkModule + daoModule + repositoryModule + viewModelModule + useCaseModule





private fun getMovieApi(): MoviesApi = RetrofitService.getPostApi()
private fun getPostDao(context: Context): MovieDao = MovieDatabase.getDatabase(context).movieDao()
private fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}
