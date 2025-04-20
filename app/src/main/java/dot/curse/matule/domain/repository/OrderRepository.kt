package dot.curse.matule.domain.repository

import dot.curse.matule.domain.model.order.Order

interface OrderRepository {
    suspend fun getAllUserOrders(id: Int): Result<List<Order>>
    suspend fun deleteOrder(id: Int): Result<Boolean>
    suspend fun newOrder(order: Order): Result<Boolean>
}