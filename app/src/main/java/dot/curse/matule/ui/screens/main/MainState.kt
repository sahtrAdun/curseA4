package dot.curse.matule.ui.screens.main

import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class MainState(
    val loading: Boolean = true,
    val currentUser: User = User(),
    val popularShoes: List<Shoe> = emptyList(),
    val newShoes: List<Shoe> = emptyList(),
    val userFav: List<Shoe> = emptyList(),
    val userCart: List<Shoe> = emptyList(),
)
