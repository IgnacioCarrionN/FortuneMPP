package api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.Serializable

class FortuneApi(private val client: HttpClient) {


    suspend fun fetchFortune(): Fortune {
        return client.get {
            url(BASE_URL)
        }
    }

    companion object {
        private const val BASE_URL = "http://yerkee.com/api/fortune"
    }
}

@Serializable
data class Fortune(val fortune: String)

sealed class FortuneResponse {
    object Loading : FortuneResponse()
    data class Success(val response: Fortune) : FortuneResponse()
    data class Error(val error: String) : FortuneResponse()
}