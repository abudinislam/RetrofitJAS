package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class LoginUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(username: String, password: String) =
        movieRepository.login(username,password)
}