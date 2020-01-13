package sample

import androidx.lifecycle.*
import api.Fortune
import api.FortuneResponse
import data.db.FortuneDatabaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import repository.FortuneRepository
import kotlin.coroutines.CoroutineContext

class FortuneViewModel(private val fortuneRepository: FortuneRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val databaseRepository: FortuneDatabaseRepository = FortuneDatabaseRepository()

    val fortuneLiveData: MutableLiveData<FortuneResponse> = MutableLiveData()
    val fortuneListLiveData: MutableLiveData<List<Fortune>> = MutableLiveData()

    init {
        launch {
            databaseRepository.getAllFortunes().collect {
                fortuneListLiveData.value = it
            }
        }
    }


    fun getFortune() {
        launch {
            fortuneRepository.getFortune(viewModelScope).collect {
                fortuneLiveData.value = it
                if(it is FortuneResponse.Success) {
                    saveFortune(it.response)
                }
            }
        }
    }

    fun saveFortune(fortune: Fortune) {
        databaseRepository.saveFortune(fortune)
    }

}