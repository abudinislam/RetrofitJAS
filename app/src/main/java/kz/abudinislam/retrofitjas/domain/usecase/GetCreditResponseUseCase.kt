package kz.abudinislam.retrofitjas.domain.usecase


import kz.abudinislam.retrofitjas.data.cast.CreditResponse
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository
import retrofit2.Response

class GetCreditResponseUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId:Int): Response<CreditResponse> {
        return movieRepository.getCreditResponse(movieId)
    }
}
