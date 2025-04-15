package dot.curse.matule.ui.screens.otpemail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.Validation.validateEmail
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dot.curse.matule.R
import dot.curse.matule.ui.utils.OTPCodeRoute
import dot.curse.matule.ui.utils.OTPEmailRoute
import dot.curse.matule.ui.utils.SignInRoute

@HiltViewModel
class OtpEmailViewModel @Inject constructor(
    private val api: UserRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow<OtpEmailState>(OtpEmailState())
    val state: StateFlow<OtpEmailState> = _state.asStateFlow()

    fun updateEmailInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    private suspend fun checkEmail(email: String): Boolean {
        val response = api.getUserByEmail(email)
        return response.isSuccess
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val emailValid = context.validateEmail(_state.value.email)
            if (emailValid) {
                val exists = checkEmail(_state.value.email)
                if (exists) {
                    navigate(OTPCodeRoute(email = _state.value.email)) {
                        popUpTo(OTPEmailRoute) { inclusive = true }
                    } // Переходит к следующему экрану удаляя себя из стека
                } else {
                    _state.update { it.copy(emailError = _state.value.email) }
                    context.myToast(context.getString(R.string.er_otpemail_exist))
                }
            } else {
                _state.update { it.copy(emailError = _state.value.email) }
            }

            _state.update { it.copy(loading = false) }
        }
    }

}