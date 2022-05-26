package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class PostMovieUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, sessionId: String, movie: Result) =
        movieRepository.postMovie(movieId,sessionId,movie)
}