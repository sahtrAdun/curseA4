package dot.curse.matule.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(
    @SerialName("id") var id: Int = -1,
    @SerialName("first_name") var firstName: String = "",
    @SerialName("last_name") var lastName: String = "",
    @SerialName("email") var email: String = "",
    @SerialName("phone") var phone: String = "",
    @SerialName("password") var password: String = "",
    @SerialName("payment_methods") var paymentMethods: List<String> = emptyList<String>(),
    @SerialName("image_url") var avatar: String = "",
    @SerialName("created_at") var date: String = "",
)

fun User.toUserPost(): UserPost {
    return UserPost(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phone = this.phone,
        password = this.password,
        paymentMethods = if (this.paymentMethods.isNotEmpty()) {
            Json.encodeToString<List<String>>(this.paymentMethods)
        } else "",
        avatar = this.avatar,
    )
}

fun User.notDefault(): Boolean {
    return id != -1 ||
            firstName != "" ||
            lastName != "" ||
            email != "" ||
            phone != "" ||
            password != "" ||
            paymentMethods.isNotEmpty() ||
            avatar != "" ||
            date != ""
}