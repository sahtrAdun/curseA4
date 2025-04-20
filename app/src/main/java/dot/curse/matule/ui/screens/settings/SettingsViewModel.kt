package dot.curse.matule.ui.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.utils.AppLanguage.getSystemLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val shared: SharedManager,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    darkTheme = shared.getDarkTheme(),
                    language = shared.getLanguage()?: getSystemLanguage()
                )
            }
        }
    }

    fun setDarkTheme(): Boolean {
        _state.update { it.copy(darkTheme = !it.darkTheme) }
        shared.setDarkTheme(_state.value.darkTheme)
        return _state.value.darkTheme
    }

    fun setLanguage(code: String): String {
        _state.update { it.copy(language = code) }
        return code
    }

}