package dot.curse.matule.ui.screens.favorites

import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class FavoritesState(
    val loading: Boolean = true,
    val currentUser: User = User(),
    val userCart: List<Shoe> = emptyList(),
    val list: List<Shoe> = emptyList()
)
