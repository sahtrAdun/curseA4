package dot.curse.matule.ui.screens.searchresult

import dot.curse.matule.domain.model.SearchFilter
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class SearchResultState(
    val loading: Boolean = true,
    val currentUser: User = User(),
    val userFav: List<Shoe> = emptyList(),
    val userCart: List<Shoe> = emptyList(),
    val searchFilter: SearchFilter = SearchFilter(),
    val list: List<Shoe> = emptyList<Shoe>(),
)
