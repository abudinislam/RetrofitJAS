package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class AddOrDeleteFavoriteUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, sessionId: String) =
        movieRepository.addOrDeleteFavorite(movieId,sessionId)
}