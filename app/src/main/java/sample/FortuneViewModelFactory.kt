package sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import repository.FortuneRepository

class FortuneViewModelFactory(private val repository: FortuneRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FortuneViewModel(repository) as T? ?: modelClass.newInstance()
    }
}