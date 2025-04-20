package dot.curse.matule.ui.screens.orderlist

import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.user.User

data class OrderListState(
    val loading: Boolean = true,
    val user: User = User(),
    val orders: List<Order> = emptyList(),
)
