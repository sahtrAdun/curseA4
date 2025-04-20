package dot.curse.matule.ui.screens.notification

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.repository.NotificationRepository
import dot.curse.matule.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val notApi: NotificationRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(user = userApi.getUserById(user.id).getOrElse { user }) }
            _state.update {
                it.copy(
                    list = notApi.getNotificationByUserId(_state.value.user.id).getOrElse { emptyList() },
                    loading = false
                )
            }
        }
    }

}