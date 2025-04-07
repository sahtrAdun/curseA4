package dot.curse.matule.viewmodel

import androidx.lifecycle.ViewModel
import dot.curse.matule.model.repository.SplashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    private val _userId = MutableStateFlow<Int>(-1)
    val userId: StateFlow<Int> = _userId

    private val _firstTime = MutableStateFlow<Boolean>(true)
    val firstTime: StateFlow<Boolean> = _firstTime

    init {
        loadData()
    }

    private fun loadData() {
        _userId.value = splashRepository.getUserId()
        _firstTime.value = splashRepository.getFirstTime()
    }

}