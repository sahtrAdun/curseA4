package dot.curse.matule.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.items.HeaderState
import dot.curse.matule.ui.items.HeaderViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val shared: SharedManager
) : ViewModel(), HeaderViewModel {

    private val _state = MutableStateFlow<SplashState>(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    override val headerState = MutableStateFlow(HeaderState())

    init {
        viewModelScope.launch {
            headerState.update {
                it.copy(show = false, label = "SplashNull")
            }
            _state.update {
                it.copy(
                    userId = shared.getLocalUserId(),
                    firstTime = shared.getLocalFirstTime(),
                    nextScreen = when {
                        state.value.firstTime -> SplashStateNextScreens.OnBoarding
                        state.value.userId == -1 -> SplashStateNextScreens.Sign
                        else -> SplashStateNextScreens.Main
                    },
                    loadingEnd = true
                )
            }

        }
    }
}