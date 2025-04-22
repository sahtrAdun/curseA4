package dot.curse.matule.ui.screens.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.DetailRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val shoeApi: ShoeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(
                user = userApi.getUserById(user.id).getOrElse { user },
                list = shoeApi.getAllShoe().getOrElse { emptyList() }
            ) }
            _state.update { it.copy(loading = false) }
        }
    }

    fun parseShoe(shoe: Shoe) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val favorite = shoeApi.getUserFavorites(_state.value.user.id).getOrElse { emptyList() }.contains(shoe) == true
            val inCart = shoeApi.getUserCart(_state.value.user.id).getOrElse { emptyList() }.contains(shoe) == true
            _state.update {
                it.copy(
                    shoe = shoe,
                    favorite = favorite,
                    inCart = inCart
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    fun addToFavorite(shoe: Shoe) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response = shoeApi.addShoeToUserFavorites(_state.value.user.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    favorite = true,
                    loading = false
                ) }
            }
        }
    }

    fun addToCart(shoe: Shoe) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response = shoeApi.addShoeToUserCart(_state.value.user.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    inCart = true,
                    loading = false
                ) }
            }
        }
    }

    fun deleteFromFavorite(shoe: Shoe) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response = shoeApi.deleteShoeFromUserFavorites(_state.value.user.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    favorite = false,
                    loading = false
                ) }
            }
        }
    }

    fun deleteFromCart(shoe: Shoe) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response = shoeApi.deleteShoeFromUserCart(_state.value.user.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    inCart = false,
                    loading = false
                ) }
            }
        }
    }

    fun NavController.onItemClick(shoe: Shoe) {
        if (shoe != _state.value.shoe) {
            navigate(DetailRoute(Json.encodeToString<Shoe>(shoe))) {
                popUpTo(DetailRoute(Json.encodeToString<Shoe>(_state.value.shoe))) { inclusive = true }
            }
        }
    }

    fun onDescriptionClick() {
        viewModelScope.launch {
            _state.update { it.copy(expanded = !it.expanded) }
        }
    }
}