package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class DeleteSessionUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke() =
        movieRepository.deleteSession()
}