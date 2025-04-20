package dot.curse.matule.ui.screens.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.SearchFilter
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
class SearchViewModel @Inject constructor(
    private val shared: SharedManager,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    history = shared.getLocalSearchHistory(),
                    loading = false
                )
            }
        }
    }


    fun updateTextInput(value: String) {
        viewModelScope.launch {
            _state.update { it.copy(text = value) }
        }
    }

    fun onSearch(navController: NavController) {
        if (_state.value.text.isNotEmpty()) {
            viewModelScope.launch {
                _state.update { it.copy(
                    loading = true,
                    history = if (it.history.contains(_state.value.text) == false) it.history.plus(_state.value.text) else it.history
                ) }
                shared.setLocalSearchHistory(_state.value.history)

                val filter = Json.encodeToString<SearchFilter>(SearchFilter(text = _state.value.text))
                navController.navigate(SearchResultRoute(filter = filter)) {
                    popUpTo(SearchResultRoute(filter = filter)) { inclusive = true }
                }

                _state.update { it.copy(loading = false) }
            }
        }
    }

}