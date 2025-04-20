package dot.curse.matule.domain.model

import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.domain.model.shoe.ShoeTag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchFilter(
    @SerialName("text") val text: String? = null,
    @SerialName("price_between") val priceBetween: Map<Int, Int>? = null,
    @SerialName("shoe_category") val shoeCategory: ShoeCategory? = null,
    @SerialName("shoe_tag") val shoeTag: ShoeTag? = null,
    @SerialName("colors") val colors: List<String>? = null
)
