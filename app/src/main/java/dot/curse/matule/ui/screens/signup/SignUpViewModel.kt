package dot.curse.matule.ui.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.Validation.validateEmail
import dot.curse.matule.ui.utils.Validation.validateName
import dot.curse.matule.ui.utils.Validation.validatePassword
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dot.curse.matule.R
import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserPost
import dot.curse.matule.ui.utils.SignInRoute

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val api: UserRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun updateNameInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(name = value) }
        }
    }

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

    fun updateConfirm(value: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(confirmed = value) }
        }
    }

    private suspend fun checkEmail(email: String): Boolean {
        val response = api.getUserByEmail(email)
        return !response.isSuccess
    }

    fun NavController.buttonClick() {
        viewModelScope.launch {
            if (!_state.value.confirmed) {
                context.myToast(context.getString(R.string.er_signup_confirm))
                return@launch
            }
            _state.update { it.copy(loading = true) }

            val nameValid = context.validateName(_state.value.name)
            val emailValid = context.validateEmail(_state.value.email)
            val passwordValid = context.validatePassword(_state.value.password) > 1

            _state.update {
                it.copy(
                    nameError = if (nameValid) null else _state.value.name,
                    emailError = if (emailValid) null else _state.value.email,
                    passwordError = if (passwordValid) null else _state.value.password
                )
            }

            if (nameValid && emailValid && passwordValid) {
                val notExists = checkEmail(_state.value.email)
                if (!notExists) {
                    _state.update { it.copy(emailError = _state.value.email,) }
                    context.myToast(context.getString(R.string.er_signup_email))
                } else {
                    api.addUser(UserPost(
                        firstName = _state.value.name,
                        email = _state.value.email,
                        password = _state.value.password
                    )).map { response ->
                        if (response) {
                            context.myToast(context.getString(R.string.success_signup))
                            popBackStack()
                        } else {
                            context.myToast("Error SignUp")
                        }
                    }
                }
            }

            _state.update { it.copy(loading = false) }
        }
    }

}