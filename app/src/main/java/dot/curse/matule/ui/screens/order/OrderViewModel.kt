package dot.curse.matule.ui.screens.order

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.repository.OrderRepository
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.myToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dot.curse.matule.R

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val orderApi: OrderRepository,
    private val shoeApi: ShoeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(user = userApi.getUserById(user.id).getOrElse { user }) }
            _state.update {
                it.copy(
                    items = shoeApi.getUserCart(_state.value.user.id).getOrElse { emptyList() },
                    payments = it.user.paymentMethods,
                    loading = false
                )
            }
        }
    }

    fun parseOrder(order: Order) {
        viewModelScope.launch {
            _state.update { it.copy(order = order) }
        }
    }

    fun updateEmailInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(order = it.order.copy(email = value)) }
        }
    }

    fun updatePhoneInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(order = it.order.copy(phone = value)) }
        }
    }

    fun updateAddressInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(address = value) }
        }
    }

    fun updatePayment(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(order = it.order.copy(payment = value)) }
        }
    }

    fun addPayment(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(user = it.user.copy(paymentMethods = it.user.paymentMethods.plus(value))) }
        }
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            val response = orderApi.newOrder(_state.value.order.copy(
                address = _state.value.address
            ))
            if (response.getOrElse { false } == true) {
                val clear = shoeApi.clearUserCart(_state.value.user.id)
                if (clear.getOrElse { false } == true) {
                    context.myToast(context.getString(R.string.toast_success))
                    popBackStack()
                }
            }
            _state.update { it.copy(loading = false) }
        }
    }

}