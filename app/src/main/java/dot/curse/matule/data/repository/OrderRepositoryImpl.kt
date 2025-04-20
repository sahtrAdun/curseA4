package dot.curse.matule.data.repository

import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.order.toOrder
import dot.curse.matule.domain.repository.ApiRepository
import dot.curse.matule.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: ApiRepository
) : OrderRepository {
    override suspend fun getAllUserOrders(id: Int): Result<List<Order>> {
        return api.getAllUserOrders(id).map { orderDot ->
            orderDot.map { it.toOrder() }
        }
    }

    override suspend fun deleteOrder(id: Int): Result<Boolean> {
        return api.deleteOrder(id)
    }

    override suspend fun newOrder(order: Order): Result<Boolean> {
        return api.newOrder(order)
    }
}