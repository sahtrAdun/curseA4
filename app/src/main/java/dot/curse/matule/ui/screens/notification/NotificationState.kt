package dot.curse.matule.ui.screens.notification

import dot.curse.matule.domain.model.notification.Notification
import dot.curse.matule.domain.model.user.User

data class NotificationState(
    val loading: Boolean = true,
    val user: User = User(),
    val list: List<Notification> = emptyList()
)
