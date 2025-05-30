package dot.curse.matule.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.curse.matule.data.storage.SharedManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val shared: SharedManager
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = shared.getLocalUserId()
            val firstTime = shared.getLocalFirstTime()
            _state.update {
                it.copy(
                    userId = userId,
                    firstTime = firstTime,
                    nextScreen = when {
                        firstTime -> SplashStateNextScreens.OnBoarding
                        userId == -1 -> SplashStateNextScreens.Sign
                        else -> SplashStateNextScreens.Main
                    },
                    loadingEnd = true
                )
            }

        }
    }
}