package dot.curse.matule.ui.screens.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.curse.matule.domain.model.SearchFilter
import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.domain.model.shoe.ShoeColors
import dot.curse.matule.domain.model.shoe.ShoeTag
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
class FilterViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    fun updatePriceFrom(value: String) {
        viewModelScope.launch {
            val max = _state.value.filter.priceBetween?.entries?.first()?.value?: 10000
            _state.update {
                it.copy(
                    filter = it.filter.copy(
                        priceBetween = mapOf((if (value.isNotEmpty()) value.toInt() else 0) to max)
                    )
                )
            }
        }
    }

    fun updatePriceTo(value: String) {
        viewModelScope.launch {
            val min = _state.value.filter.priceBetween?.entries?.first()?.key?: 0
            _state.update {
                it.copy(
                    filter = it.filter.copy(
                        priceBetween = mapOf(min to (if (value.isNotEmpty()) value.toInt() else 0))
                    )
                )
            }
        }
    }

    fun onCategoryClick(category: ShoeCategory) {
        viewModelScope.launch {
            val eq = _state.value.filter.shoeCategory == category
            _state.update {
                it.copy(
                    filter = it.filter.copy(
                        shoeCategory = if (eq) null else category
                    )
                )
            }
        }
    }

    fun onTagClick(tag: ShoeTag) {
        viewModelScope.launch {
            val eq = _state.value.filter.shoeTag == tag
            _state.update {
                it.copy(
                    filter = it.filter.copy(
                        shoeTag = if (eq) null else tag
                    )
                )
            }
        }
    }

    fun onColorClick(color: ShoeColors) {
        val currentColors = _state.value.filter.colors ?: emptyList()
        val updatedColors = if (currentColors.contains(color.value)) {
            currentColors - color.value
        } else {
            currentColors + color.value
        }
        _state.update {
            it.copy(
                filter = it.filter.copy(
                    colors = if (updatedColors.isEmpty()) null else updatedColors
                )
            )
        }
        println("Colors: ${_state.value.filter.colors}")
    }

    fun NavController.onButtonClick() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            navigate(SearchResultRoute(filter = Json.encodeToString<SearchFilter>(
                _state.value.filter
            ))) {
                popUpTo(SearchResultRoute(filter = Json.encodeToString<SearchFilter>(
                    _state.value.filter
                ))) { inclusive = true }
            }

            _state.update { it.copy(loading = false) }
        }
    }

}