package dot.curse.matule.ui.screens.otpcode

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpCodeViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(OtpCodeState())
    val state: StateFlow<OtpCodeState> = _state.asStateFlow()

    fun updateEmail(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    fun updateCode(index: Int, value: Int?) {
        val currentCode = _state.value.code.toMutableList()
        currentCode[index] = value
        println("DEBUG: VALUE=$value, INDEX=$index, PRE CODE=${_state.value.code}")
        _state.update { it.copy(code = currentCode) }
        println("DEBUG: VALUE=$value, INDEX=$index, AFTER CODE=${_state.value.code}")
    }

    fun setFocusedIndex(index: Int?) {
        _state.update { it.copy(focusedIndex = index) }
    }

    fun handleBackspace(currentIndex: Int) {
        val currentCode = _state.value.code.toMutableList()
        if (currentCode[currentIndex] != null) {
            // Если текущее поле не пустое, очищаем его
            currentCode[currentIndex] = null
            _state.update { it.copy(code = currentCode) }
        } else {
            // Ищем предыдущее пустое поле или уменьшаем индекс на 1
            for (i in currentIndex - 1 downTo 0) {
                if (currentCode[i] == null) {
                    setFocusedIndex(i)
                    return
                }
            }
            setFocusedIndex(currentIndex - 1)
        }
    }
}