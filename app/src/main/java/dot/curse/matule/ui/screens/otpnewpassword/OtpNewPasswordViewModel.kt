package dot.curse.matule.ui.screens.otpnewpassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.ui.utils.Validation.validatePasswordWithoutNotice
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dot.curse.matule.R
import dot.curse.matule.domain.model.user.UserPost
import dot.curse.matule.domain.repository.UserRepository

@HiltViewModel
class OtpNewPasswordViewModel @Inject constructor(
    private val api: UserRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(OtpNewPasswordState())
    val state = _state.asStateFlow()

    fun checkPasswordStrength(password: String): Int {
        return context.validatePasswordWithoutNotice(password)
    }

    fun updateEmail(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    fun updatePasswordInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                password = value,
                passwordsEq = it.passwordConfirm == value,
                passwordStrength = checkPasswordStrength(value)
            ) }
        }
    }

    fun updatePasswordConfirmInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                passwordConfirm = value,
                passwordsEq = it.password == value
            ) }
        }
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {
            if (checkPasswordStrength(_state.value.password) <= 1) {
                context.myToast(context.getString(R.string.er_password_weak))
                return@launch
            }
            if(!_state.value.passwordsEq) {
                context.myToast(context.getString(R.string.er_password_eq))
                return@launch
            }
            _state.update { it.copy(loading = true) }

            val response = api.updateUserByEmail(UserPost(
                email = _state.value.email,
                password = _state.value.password
            ))
            if (response.isSuccess) {
                popBackStack()
            } else {
                _state.update {
                    it.copy(
                        passwordError = _state.value.password,
                        passwordConfirmError = _state.value.passwordConfirm
                    )
                }
                context.myToast("Error update user")
            }

            _state.update { it.copy(loading = false) }
        }
    }

}