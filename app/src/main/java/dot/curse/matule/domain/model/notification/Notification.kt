package dot.curse.matule.domain.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Notification(
    @SerialName("id") val id: Int = -1,
    @SerialName("user_id") val userId: Int = -1,
    @SerialName("label") val label: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("created_at") val date: String = "",
)

fun Notification.toNotificationPost(): NotificationPost {
    return NotificationPost(
        userId = userId,
        label = null,
        description = null
    )
}