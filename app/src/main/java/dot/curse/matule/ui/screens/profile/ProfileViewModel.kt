package dot.curse.matule.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.R
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.Validation.validateEmail
import dot.curse.matule.ui.utils.Validation.validatePhone
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val shared: SharedManager,
    private val api: UserRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update {
                it.copy(
                    user = api.getUserById(user.id).getOrElse { user },
                )
            }
            _state.update {
                it.copy(
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    email = it.user.email,
                    phone = it.user.phone,
                    loading = false
                )
            }
        }
    }

    fun onEditClick() {
        viewModelScope.launch {
            _state.update { it.copy(edit = !it.edit) }
        }
    }

    fun updateFirstName(value: String) {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(firstName = value) }
            }
        }

    }

    fun updateLastName(value: String) {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(lastName = value) }
            }
        }

    }

    fun updateEmail(value: String) {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(email = value) }
            }
        }

    }

    fun updatePhone(value: String) {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(phone = value) }
            }
        }

    }

    fun onImageClick() {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(pickImage = true) }
            }
        }
    }

    fun closeImageClick() {
        if (_state.value.edit) {
            viewModelScope.launch {
                _state.update { it.copy(pickImage = false) }
            }
        }
    }

    fun pasteImageByUri(uri: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(pickImage = false, loading = true) }

            val response = api.uploadAvatar(context, _state.value.user, uri)
            if (response.getOrElse { false } == true) {
                closeImageClick()
                context.myToast(context.getString(R.string.toast_success))
            }

            _state.update { it.copy(loading = false) }
        }
    }

    fun updateUser() {
        val user = shared.getLocalCurrentUser()
        val value = _state.value
        if (value.firstName != user.firstName
            || value.lastName != user.lastName
            || value.email != user.email
            || value.phone != user.phone
            ) {
            viewModelScope.launch {
                _state.update { it.copy(loading = true) }

                if (_state.value.firstName.length < 2) {
                    _state.update { it.copy(firstNameError = it.firstName) }
                    return@launch
                }

                if (_state.value.lastName.length < 2) {
                    _state.update { it.copy(lastNameError = it.lastName) }
                    return@launch
                }

                if (context.validateEmail(_state.value.email) == false) {
                    _state.update { it.copy(emailError = it.email) }
                    return@launch
                }

                if (context.validatePhone(_state.value.phone) == false) {
                    _state.update { it.copy(phoneError = it.phone) }
                    return@launch
                }

                val response = api.updateUser(_state.value.user.copy(
                    firstName = _state.value.firstName,
                    lastName = _state.value.lastName,
                    email = _state.value.email,
                    phone = _state.value.phone,
                ))
                if (response.getOrElse { false } == true) {
                    shared.setLocalCurrentUser(_state.value.user)
                    context.myToast(context.getString(R.string.toast_success))
                } else {
                    context.myToast("Error")
                }
                onEditClick()
                _state.update { it.copy(loading = true) }
            }
        } else {
            onEditClick()
        }
    }
}