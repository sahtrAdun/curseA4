package dot.curse.matule.domain.model.order

import dot.curse.matule.ui.utils.MyDateSerializer.serializeDateToString
import dot.curse.matule.ui.utils.TimeManager.convertToLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class OrderDot(
    @SerialName("id") val id: Int? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("items") val items: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("payment") val payment: String? = null,
    @SerialName("total") val total: Float? = null,
    @SerialName("price") val price: Float? = null,
    @SerialName("tax") val tax: Float? = null,
    @SerialName("created_at") val date: String? = null
)

fun OrderDot.toOrder(): Order {
    return Order(
        id = id?: -1,
        userId = userId?: -1,
        items = if (items?.isEmpty() == false) {
            Json.decodeFromString<List<Int>>(items)
        } else { emptyList() },
        address = address?: "",
        email = email?: "",
        phone = phone?: "",
        payment = payment?: "",
        total = total?: 0f,
        price =  price?: 0f,
        tax  = tax?: 0f,
        date = serializeDateToString(convertToLocalDateTime(date!!))
    )
}
