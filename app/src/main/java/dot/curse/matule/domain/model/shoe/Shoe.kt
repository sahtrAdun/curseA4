package dot.curse.matule.domain.model.shoe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Shoe(
    @SerialName("id") val id: Int = -1,
    @SerialName("company") val company: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("price") val price: Float = 0.0f,
    @SerialName("count") val count: Int = 0,
    @SerialName("description") val description: String = "",
    @SerialName("tag") val tag: ShoeTag = ShoeTag.NEW,
    @SerialName("color") val color: ShoeColors = ShoeColors.BLUE,
    @SerialName("category") val category: ShoeCategory = ShoeCategory.OUTDOOR,
    @SerialName("image") val image: String = "",
    @SerialName("big_image") val bigImage: String = "",
    @SerialName("created_at") val date: String = "",
)
