package kz.abudinislam.retrofitjas.domain.usecase

import kz.abudinislam.retrofitjas.domain.model.AccountInfo
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class InsertUserUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(user: AccountInfo) =
        movieRepository.insertUser(user)
    }