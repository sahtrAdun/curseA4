package dot.curse.matule.ui.screens.search

data class SearchState(
    val text: String = "",
    val history: List<String> = emptyList<String>()
)
