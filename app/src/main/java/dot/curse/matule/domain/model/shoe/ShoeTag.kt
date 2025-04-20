package dot.curse.matule.domain.model.shoe

import kotlinx.serialization.Serializable

@Serializable
enum class ShoeTag(
    val value: String
) {
    NEW("New"),
    POPULAR("Popular")
}

fun String.toShoeTag(): ShoeTag? {
    return ShoeTag.entries.find { it.value.contains(this, ignoreCase = true) }
}