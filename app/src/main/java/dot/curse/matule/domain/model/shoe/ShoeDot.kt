package dot.curse.matule.domain.model.shoe

import dot.curse.matule.ui.utils.MyDateSerializer.serializeDateToString
import dot.curse.matule.ui.utils.TimeManager.convertToLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoeDot(
    @SerialName("id") val id: Int? = null,
    @SerialName("company") val company: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("price") val price: Float? = null,
    @SerialName("count") val count: Int? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("tag") val tag: String? = null,
    @SerialName("color") val color: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("big_image") val bigImage: String? = null,
    @SerialName("created_at") val date: String? = null,
)

fun ShoeDot.toShoe(): Shoe {
    return Shoe(
        id = this.id?: -1,
        company = this.company?: "",
        name = this.name?: "",
        price = this.price?: 0.0f,
        count = this.count?: 0,
        description = this.description?: "",
        tag = this.tag?.toShoeTag()?: ShoeTag.NEW,
        color = this.color?.toShoeColor()?: ShoeColors.BLUE,
        category = this.category?.toShoeCategory()?: ShoeCategory.OUTDOOR,
        image = this.image?: "",
        bigImage = this.bigImage?: "",
        date = serializeDateToString(convertToLocalDateTime(this.date!!))
    )
}
