package dot.curse.matule.domain.model.shoe

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
enum class ShoeColors(
    val value: String,
    val color: Color
) {
    BLUE("blue", Color.Blue),
    RED("red", Color.Red),
    BLACK("clack", Color.Black),
    GREEN("green", Color.Green),
    YELLOW("yellow", Color.Yellow),
}

fun String.toShoeColor(): ShoeColors? {
    return ShoeColors.entries.find { it.value.contains(this, ignoreCase = true) }
}

fun Color.toShoeColor(): ShoeColors? {
    return ShoeColors.entries.find { it.color == this }
}