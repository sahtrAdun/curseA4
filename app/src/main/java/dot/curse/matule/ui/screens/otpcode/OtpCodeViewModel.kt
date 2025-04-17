package dot.curse.matule.ui.screens.otpcode

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.OTPCodeRoute
import dot.curse.matule.ui.utils.OTPNewPasswordRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpCodeViewModel @Inject constructor(
    private val api: UserRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(OtpCodeState())
    val state: StateFlow<OtpCodeState> = _state.asStateFlow()

    fun updateEmail(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    fun sendOtpAgain() {
        viewModelScope.launch {
            _state.value.email.let {
                println("OTP resend on ${_state.value.email}")
                api.sendOtp(it)
            }
        }
    }

    suspend fun getOtpValid(otp: String): Boolean {
        return api.checkOtp(
            email = _state.value.email,
            otp = otp
        ).getOrElse { false }
    }

    private fun enterNumber(number: Int?, index: Int, navController: NavController) {
        val newCode = _state.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        viewModelScope.launch {
            _state.update {
                it.copy(
                    code = newCode,
                    focusedIndex = if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                        it.focusedIndex
                    } else {
                        getNextFocusedTextFieldIndex(
                            currentCode = it.code,
                            currentFocusedIndex = it.focusedIndex
                        )
                    }
                )
            }
            val valid = if(_state.value.code.none { it == null }) {
                getOtpValid(_state.value.code.joinToString(""))
            } else null
            _state.update { it.copy(isValid = valid) }
            if (valid == true) {
                navController.navigate(OTPNewPasswordRoute(email = _state.value.email)) {
                    popUpTo(OTPCodeRoute(email = _state.value.email)) { inclusive = true }
                } // Переходит к следующему экрану удаляя себя из стека
            }

        }
    }

    private fun getPreviousFocussedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ) : Int? {
        if (currentFocusedIndex == null) return null
        if (currentFocusedIndex == 5) return currentFocusedIndex
        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) return@forEachIndexed
            if (number == null) return index
        }
        return currentFocusedIndex
    }

    fun onChangeFieldFocused(index: Int) {
        _state.update { it.copy(focusedIndex = index) }
    }

    fun NavController.onEnterNumber(number: Int?, index: Int) {
        enterNumber(number, index, this)
    }

    fun onKeyboardBack() {
        val previousIndex = getPreviousFocussedIndex(_state.value.focusedIndex)
        _state.update {
            it.copy(
                code = it.code.mapIndexed { index, number ->
                    if (index == previousIndex) {
                        null
                    } else {
                        number
                    }
                },
                focusedIndex = previousIndex
            )
        }
    }
}