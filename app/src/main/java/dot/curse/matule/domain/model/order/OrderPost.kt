package dot.curse.matule.domain.model.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderPost(
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("items") val items: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("payment") val payment: String? = null,
    @SerialName("total") val total: Float? = null,
    @SerialName("price") val price: Float? = null,
    @SerialName("tax") val tax: Float? = null
)
