package dot.curse.matule.ui.screens.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shared: SharedManager,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentUser = shared.getLocalCurrentUser(),
                    loading = false
                )
            }
        }
    }

    fun NavController.onDummySearchFieldClick() {
        // TODO
    }

    fun NavController.onFilterClick() {
        // TODO
    }

    fun NavController.onCategoryClick() {
        // TODO
    }

    fun NavController.shoeDetail() {
        // TODO
    }

    fun NavController.addToFavorite() {
        // TODO
    }

    fun NavController.addToCart() {
        // TODO
    }

    fun NavController.allPopular() {
        // TODO
    }

    fun NavController.allSales() {}

}