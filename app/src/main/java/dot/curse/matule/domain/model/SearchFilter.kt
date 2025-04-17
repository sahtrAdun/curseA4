package dot.curse.matule.domain.model

import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.domain.model.shoe.ShoeTag

data class SearchFilter(
    val text: String? = null,
    val sale: Boolean? = null,
    val priceBetween: Map<Int, Int> = mapOf(0 to 100000),
    val shoeCategory: ShoeCategory? = null,
    val shoeTag: ShoeTag? = null,
    val colors: List<String>? = null
)
