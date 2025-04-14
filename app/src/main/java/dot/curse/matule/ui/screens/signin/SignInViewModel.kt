package dot.curse.matule.ui.screens.signin

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dot.curse.matule.R
import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.notDefault
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.OTPEmailRoute
import dot.curse.matule.ui.utils.SignInRoute
import dot.curse.matule.ui.utils.SignUpRoute
import dot.curse.matule.ui.utils.Validation.validateEmail
import dot.curse.matule.ui.utils.Validation.validatePassword

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val api: UserRepository,
    private val shared: SharedManager,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun updateEmailInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    fun updatePasswordInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(password = value) }
        }
    }

    suspend fun checkUserExists(email: String, password: String): User {
        val response = api.checkUserExists(email, password)
        return response.getOrElse { User() }
    }

    fun NavController.toSignUp() {
        this.navigate(SignUpRoute) {
            launchSingleTop = true
        }
    }

    fun NavController.toOtp() {
        this.navigate(OTPEmailRoute) {
            launchSingleTop = true
        }
    }

    fun NavController.buttonClick() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            val emailValid = context.validateEmail(_state.value.email)
            val passwordValid = context.validatePassword(_state.value.password)

            _state.update {
                it.copy(
                    emailError = if (emailValid) null else _state.value.email,
                    passwordError = if (passwordValid) null else _state.value.password
                )
            }

            if (emailValid && passwordValid) {
                val response = checkUserExists(_state.value.email, _state.value.password)
                if (response.notDefault()) {
                    shared.setLocalUserId(response.id)
                    shared.setLocalCurrentUser(response)
                    navigate(MainRoute) {
                        popUpTo(SignInRoute) { inclusive = true }
                    }
                } else {
                    context.myToast("Error checkUserExists()")
                }
            }

            _state.update { it.copy(loading = false) }
        }
    }
}
