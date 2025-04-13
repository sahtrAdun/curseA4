package dot.curse.matule.ui.screens.signin

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    suspend fun checkUserExists(email: String, password: String): Boolean {
        val response = api.checkUserExists(email, password)
        return response.getOrElse { false }
    }

}