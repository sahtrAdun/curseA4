package dot.curse.matule.domain.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationPost(
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("description") val description: String? = null,
)
