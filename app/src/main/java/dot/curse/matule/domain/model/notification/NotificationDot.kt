package dot.curse.matule.domain.model.notification

import dot.curse.matule.ui.utils.MyDateSerializer.serializeDateToString
import dot.curse.matule.ui.utils.TimeManager.convertToLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDot(
    @SerialName("id") val id: Int? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("created_at") val date: String? = null
)


fun NotificationDot.toNotification(): Notification {
    return Notification(
        id = id?: -1,
        userId = userId?: -1,
        label = label?: "",
        description = description?: "",
        date = serializeDateToString(convertToLocalDateTime(date!!))
    )
}