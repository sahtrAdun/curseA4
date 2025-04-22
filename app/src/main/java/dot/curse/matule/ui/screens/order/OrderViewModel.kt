package dot.curse.matule.ui.screens.order

import android.content.Context
import androidx.core.text.isDigitsOnly
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
import dot.curse.matule.ui.utils.Validation.validateEmail
import dot.curse.matule.ui.utils.Validation.validatePhone

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
            if (_state.value.user.paymentMethods.isNotEmpty()) {
                selectPayment(_state.value.user.paymentMethods.last())
            }
        }
    }

    fun parseOrder(order: Order) {
        viewModelScope.launch {
            _state.update { it.copy(order = order) }
            _state.update { it.copy(
                email = _state.value.order.email,
                phone = _state.value.order.phone
            ) }
        }
    }

    fun updateEmailInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(email = value) }
        }
    }

    fun updatePhoneInput(value: String) {
        viewModelScope.launch {
            if (value.isDigitsOnly()) {
                _state.update { it.copy(phone = value) }
            }
        }
    }

    fun updateAddressInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(address = value) }
        }
    }

    fun updatePayment(value: String) {
        viewModelScope.launch {
            if (value.isDigitsOnly()) {
                _state.update { it.copy(order = it.order.copy(payment = value)) }
                if (_state.value.order.payment.length == 16) {
                    selectPayment(_state.value.order.payment)
                }
            }
        }
    }

    fun selectPayment(newCard: String) {
        viewModelScope.launch {
            if (newCard.isDigitsOnly()) {
                _state.update { it.copy(order = it.order.copy(payment = newCard)) }
                addPayment(newCard)
            }

        }
    }

    fun addPayment(value: String) {
        viewModelScope.launch {
            val contains = _state.value.user.paymentMethods.contains(value)
            _state.update { it.copy(
                user = it.user.copy(
                    paymentMethods = if (!contains) it.user.paymentMethods.plus(value) else it.user.paymentMethods
                )
            ) }
            if (!contains) {
                if (userApi.updateUser(_state.value.user).getOrElse { false } == false) {
                    println("Error update user(${_state.value.user.id}) payment methods")
                }
            }
        }
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {

            if (context.validateEmail(_state.value.email) == false) {
                _state.update { it.copy(emailError = _state.value.email) }
                context.myToast(context.getString(R.string.er_email))
                return@launch
            }

            if (context.validatePhone(_state.value.phone) == false) {
                _state.update { it.copy(phoneError = _state.value.phone) }
                context.myToast(context.getString(R.string.er_phone))
                return@launch
            }

            if (_state.value.address.length < 8) {
                _state.update { it.copy(addressError = _state.value.address) }
                context.myToast(context.getString(R.string.er_adderss))
                return@launch
            }

            if (_state.value.order.payment.length < 16) {
                context.myToast(context.getString(R.string.er_payment))
                return@launch
            }

            _state.update { it.copy(loading = true) }

            val response = orderApi.newOrder(_state.value.order.copy(
                email = _state.value.email,
                phone = _state.value.phone,
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