package dot.curse.matule.ui.items

import kotlinx.coroutines.flow.StateFlow

interface HeaderViewModel {
    val headerState: StateFlow<HeaderState>
}

data class HeaderState(
    val show: Boolean = false,
    val label: String = ""
)
