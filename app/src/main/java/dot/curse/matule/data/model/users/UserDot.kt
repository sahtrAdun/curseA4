package dot.curse.matule.data.model.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UserDot (
    @SerialName("id") val id: Int? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("payment_methods") val paymentMethods: String? = null,
    @SerialName("image_url") val avatar: String? = null,
    @SerialName("created_at") val date: String? = null,
)

fun UserDot.toUser(): User {
    return User(
        id = this.id?: -1,
        firstName = this.firstName?: "",
        lastName = this.lastName?: "",
        email = this.email?: "",
        phone = this.phone?: "",
        password = this.password?: "",
        paymentMethods = Json.decodeFromString<List<String>>(this.paymentMethods?: "[]"),
        avatar = this.avatar?: "",
        date = this.date?: "",
    )
}
