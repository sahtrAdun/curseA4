package dot.curse.matule.domain.model.shoe

import kotlinx.serialization.Serializable

@Serializable
enum class ShoeCategory(
    val value: String
) {
    OUTDOOR("Outdoor"),
    SPORT("Sport")
}

fun String.toShoeCategory(): ShoeCategory? {
    return ShoeCategory.entries.find { it.value.contains(this, ignoreCase = true) }
}