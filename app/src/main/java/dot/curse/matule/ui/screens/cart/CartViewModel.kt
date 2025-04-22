package dot.curse.matule.ui.screens.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.R
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.CartRoute
import dot.curse.matule.ui.utils.OrderRoute
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val shoeApi: ShoeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(user = userApi.getUserById(user.id).getOrElse { user }) }
            _state.update {
                it.copy(
                    items = shoeApi.getUserCart(_state.value.user.id).getOrElse { emptyList() },
                    loading = false
                )
            }
            calculateTotals()
        }
    }

    fun deleteFromCart(shoe: Shoe) {
        viewModelScope.launch {
            val response = shoeApi.deleteShoeFromUserCart(_state.value.user.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    items = it.items.minus(shoe)
                ) }
                calculateTotals()
            }
        }
    }

    fun calculateTotals() {
        viewModelScope.launch {
            var price = 0f
            var count = 0
            _state.value.items.forEach {
                price += it.price
                count += 1
            }
            _state.update { it.copy(
                totalPrice = price,
                totalTax = 20f * count
            ) }
        }
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {
            if (_state.value.items.isNotEmpty()) {
                _state.update { it.copy(loading = true) }
                val order = Order(
                    userId = _state.value.user.id,
                    items = _state.value.items.map { it.id },
                    total = _state.value.totalPrice + _state.value.totalTax,
                    price = _state.value.totalPrice,
                    tax = _state.value.totalTax,
                    email = _state.value.user.email,
                    phone = _state.value.user.phone,
                    payment = if (_state.value.user.paymentMethods.isNotEmpty()) _state.value.user.paymentMethods.first() else ""
                )
                navigate(OrderRoute(order = Json.encodeToString<Order>(order))) {
                    popUpTo(CartRoute) { inclusive = true }
                }
                _state.update { it.copy(loading = false) }
            } else {
                context.myToast(context.getString(R.string.er_cart_empty))
            }
        }
    }
}