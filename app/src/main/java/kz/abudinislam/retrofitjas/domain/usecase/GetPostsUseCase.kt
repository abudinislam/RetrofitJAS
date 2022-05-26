package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class GetPostsUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(sessionId: String) =
        movieRepository.getPosts(sessionId)

}