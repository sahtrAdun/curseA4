package dot.curse.matule.domain.model.shoe

enum class ShoeTag(
    val value: String
) {
    NEW("New"),
    POPULAR("Popular")
}

fun String.toShoeTag(): ShoeTag? {
    return ShoeTag.entries.find { it.value.contains(this, ignoreCase = true) }
}