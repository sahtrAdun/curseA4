package dot.curse.matule.ui.items

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow

interface HeaderViewModel {
    val headerState: StateFlow<HeaderState>
}

data class HeaderState(
    val show: Boolean = false,
    val label: String = "",
    val placeholderColor: Color = lightColorScheme().background
)
