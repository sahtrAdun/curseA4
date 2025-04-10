package dot.curse.matule.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(
    @SerialName("id") var id: Int = -1,
    @SerialName("first_name") var firstName: String = "null",
    @SerialName("last_name") var lastName: String = "null",
    @SerialName("email") var email: String = "null",
    @SerialName("phone") var phone: String = "null",
    @SerialName("password") var password: String = "null",
    @SerialName("payment_methods") var paymentMethods: List<String> = emptyList<String>(),
    @SerialName("image_url") var avatar: String = "null",
    @SerialName("created_at") var date: String = "null",
)

fun User.toUserPost(): UserPost {
    return UserPost(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phone = this.phone,
        password = this.password,
        paymentMethods = Json.encodeToString<List<String>>(this.paymentMethods),
        avatar = this.avatar,
    )
}

fun User.notDefault(): Boolean {
    return id != -1 ||
            firstName != "null" ||
            lastName != "null" ||
            email != "null" ||
            phone != "null" ||
            password != "null" ||
            paymentMethods.isNotEmpty() ||
            avatar != "null" ||
            date != "null"
}