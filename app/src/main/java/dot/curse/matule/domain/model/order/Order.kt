package dot.curse.matule.domain.model.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Order(
    @SerialName("id") val id: Int = -1,
    @SerialName("user_id") val userId: Int = -1,
    @SerialName("items") val items: List<Int> = emptyList<Int>(),
    @SerialName("address") val address: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("phone") val phone: String = "",
    @SerialName("payment") val payment: String = "",
    @SerialName("total") val total: Float = 0f,
    @SerialName("price") val price: Float = 0f,
    @SerialName("tax") val tax: Float = 0f,
    @SerialName("created_at") val date: String = "",
)

fun Order.toOrderPost(): OrderPost {
    return OrderPost(
        userId = userId,
        items = if(items.isEmpty() == false) Json.encodeToString<List<Int>>(items) else "",
        address = address,
        email = email,
        phone = phone,
        total = total,
        price = price,
        tax = tax,
        payment = payment
    )
}
