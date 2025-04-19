package dot.curse.matule.ui.screens.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.SearchFilter
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.domain.model.shoe.ShoeTag
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import dot.curse.matule.ui.utils.SearchResultRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val shared: SharedManager,
    private val userApi: UserRepository,
    private val shoeApi: ShoeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = shared.getLocalCurrentUser()
            _state.update { it.copy(currentUser = userApi.getUserById(user.id).getOrElse { user }) }
            _state.update { it.copy(
                list = shoeApi.getUserFavorites(it.currentUser.id).getOrElse { emptyList() },
                userCart = shoeApi.getUserCart(it.currentUser.id).getOrElse { emptyList() },
                loading = false
            ) }
        }
    }

    fun addToFavorite(shoe: Shoe) {
        viewModelScope.launch {
            val response = shoeApi.addShoeToUserFavorites(_state.value.currentUser.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    list = it.list.plus(shoe)
                ) }
            }
        }
    }

    fun addToCart(shoe: Shoe) {
        viewModelScope.launch {
            val response = shoeApi.addShoeToUserCart(_state.value.currentUser.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    userCart = it.userCart.plus(shoe)
                ) }
            }
        }
    }

    fun deleteFromFavorite(shoe: Shoe) {
        viewModelScope.launch {
            val response = shoeApi.deleteShoeFromUserFavorites(_state.value.currentUser.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    list =  it.list.minus(shoe)
                ) }
            }
        }
    }

    fun deleteFromCart(shoe: Shoe) {
        viewModelScope.launch {
            val response = shoeApi.deleteShoeFromUserCart(_state.value.currentUser.id, shoe.id)
            if (response.getOrElse { false } == true) {
                _state.update { it.copy(
                    userCart = it.userCart.minus(shoe)
                ) }
            }
        }
    }

    fun NavController.shoeDetail(shoe: Shoe) {
        // TODO Переход
    }

    fun NavController.allShoes() {
        navigate(SearchResultRoute(filter = Json.encodeToString<SearchFilter>(SearchFilter())))
    }

    fun NavController.onTagClick(tag: ShoeTag) {
        navigate(SearchResultRoute(filter = Json.encodeToString<SearchFilter>(SearchFilter(
            shoeTag = tag
        ))))
    }

    fun NavController.onCategoryClick(category: ShoeCategory) {
        navigate(SearchResultRoute(filter = Json.encodeToString<SearchFilter>(SearchFilter(
            shoeCategory = category
        ))))
    }

}