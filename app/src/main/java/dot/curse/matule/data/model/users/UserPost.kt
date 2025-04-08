package dot.curse.matule.data.model.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    @SerialName("first_name") val firstName: String = "",
    @SerialName("last_name") val lastName: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("phone") val phone: String = "",
    @SerialName("password") val password: String = "",
    @SerialName("payment_methods") val paymentMethods: String = "",
    @SerialName("image_url") val avatar: String = "",
)
