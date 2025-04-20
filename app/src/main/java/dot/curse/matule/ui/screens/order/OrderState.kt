package dot.curse.matule.ui.screens.order

import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.user.User

data class OrderState(
    val loading: Boolean = true,
    val user: User = User(),
    val order: Order = Order(),
    val items: List<Shoe> = emptyList<Shoe>(),
    val address: String = "",
    val payments: List<String> = emptyList(),
)
