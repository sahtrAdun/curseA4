package dot.curse.matule.ui.screens.detail

import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class DetailState(
    val loading: Boolean = true,
    val shoe: Shoe = Shoe(),
    val user: User = User(),
    val favorite: Boolean = false,
    val inCart: Boolean = false,
    val expanded: Boolean = false,
    val list: List<Shoe> = emptyList<Shoe>()
)
