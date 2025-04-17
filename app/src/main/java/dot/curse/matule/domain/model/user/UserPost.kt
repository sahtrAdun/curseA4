package dot.curse.matule.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("payment_methods") val paymentMethods: String? = null,
    @SerialName("image_url") val avatar: String? = null,
)
