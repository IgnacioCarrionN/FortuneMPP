package repository

import api.FortuneApi
import api.FortuneResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FortuneRepository(private val fortuneApi: FortuneApi) {

    suspend fun getFortune(scope: CoroutineScope): Flow<FortuneResponse> {
        return withContext(scope.coroutineContext + Dispatchers.Main) {
            try {
                flow {
                    emit(FortuneResponse.Loading)
                    val result = withContext(Dispatchers.Default) {
                        fortuneApi.fetchFortune()
                    }

                    emit(FortuneResponse.Success(result))
                }
            } catch (e: Exception) {
                flow {
                    emit(FortuneResponse.Error(e.message ?: "Unknown Error"))
                }
            }
        }
    }
    
}