package dot.curse.matule.model.entities.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UserPost(
    @SerialName("first_name") val firstName: String = "null",
    @SerialName("last_name") val lastName: String = "null",
    @SerialName("email") val email: String = "null",
    @SerialName("phone") val phone: String = "null",
    @SerialName("password") val password: String = "null",
    @SerialName("payment_methods") val paymentMethods: String = "[]",
    @SerialName("address") val address: String = "null",
    @SerialName("avatar") val avatar: String = "null",
    @SerialName("created_at") val date: String = "null"
)
