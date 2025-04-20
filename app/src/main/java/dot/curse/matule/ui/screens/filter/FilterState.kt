package dot.curse.matule.ui.screens.filter

import dot.curse.matule.domain.model.SearchFilter

data class FilterState(
    val loading: Boolean = false,
    val filter: SearchFilter = SearchFilter()
)
