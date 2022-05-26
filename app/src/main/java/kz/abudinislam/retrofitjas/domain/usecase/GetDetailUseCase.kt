package kz.abudinislam.retrofitjas.domain.usecase

import android.view.contentcapture.ContentCaptureSessionId
import kz.abudinislam.retrofitjas.domain.repository.MovieRepository

class GetDetailUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, sessionId: String) =
        movieRepository.getDetail(movieId, sessionId)
}
