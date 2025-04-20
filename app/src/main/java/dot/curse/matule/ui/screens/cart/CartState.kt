package dot.curse.matule.ui.screens.cart

import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class CartState(
    val loading: Boolean = true,
    val user: User = User(),
    val items: List<Shoe> = emptyList<Shoe>(),
    val totalPrice: Float = 0f,
    val totalTax: Float = 0f
)
