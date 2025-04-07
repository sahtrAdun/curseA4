package dot.curse.matule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dot.curse.matule.model.entities.user.User
import dot.curse.matule.model.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<Result<List<User>>>(Result.success(emptyList()))
    val users: StateFlow<Result<List<User>>> = _users

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _users.value = userRepository.getUsers()
        }
    }

    fun saveUserId(userId: Int) {
        userRepository.saveUserId(userId)
    }

    fun getUserId(): Int {
        return userRepository.getUserId()
    }
}