package dot.curse.matule.ui.screens.orderlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.repository.OrderRepository
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val orderApi: OrderRepository,
    private val shoeApi: ShoeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(OrderListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(user = userApi.getUserById(user.id).getOrElse { user }) }
            _state.update {
                it.copy(
                    orders = orderApi.getAllUserOrders(it.user.id).getOrElse { emptyList() },
                    loading = false
                )
            }
        }
    }

    fun getFirstItem(order: Order): Shoe {
        return runBlocking {
            shoeApi.getShoeById(order.items.first()).getOrElse { Shoe() }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            val response = orderApi.deleteOrder(order.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    orders = it.orders.minus(order)
                ) }
            }
        }
    }

}