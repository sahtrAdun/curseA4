package dot.curse.matule.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.collections.isNotEmpty

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
        firstName = this.firstName?: "null",
        lastName = this.lastName?: "null",
        email = this.email?: "null",
        phone = this.phone?: "null",
        password = this.password?: "null",
        paymentMethods = Json.decodeFromString<List<String>>(this.paymentMethods?: ""),
        avatar = this.avatar?: "null",
        date = this.date?: "null",
    )
}

fun UserDot.notDefault(): Boolean {
    return id != null ||
            firstName != null ||
            lastName != null ||
            email != null ||
            phone != null ||
            password != null ||
            paymentMethods != null ||
            avatar != null ||
            date != null
}
