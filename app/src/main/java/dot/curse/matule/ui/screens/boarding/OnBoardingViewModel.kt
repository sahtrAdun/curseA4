package dot.curse.matule.ui.screens.boarding

import android.content.Context
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dot.curse.matule.R
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.utils.SignInRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val shared: SharedManager,
    @ApplicationContext val context: Context
) : ViewModel() {


    private val _state = MutableStateFlow<OnBoardingState>(OnBoardingState())
    val state: StateFlow<OnBoardingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    pages = listOf(
                        BoardingPage(
                            image = R.drawable.board_image_1,
                            header = context.getString(R.string.board_head_1),
                            text = null,
                            buttonText = context.getString(R.string.b_start)
                        ),
                        BoardingPage(
                            image = R.drawable.board_image_2,
                            header = context.getString(R.string.board_head_2),
                            text = context.getString(R.string.board_text_2),
                            buttonText = context.getString(R.string.b_next)
                        ),
                        BoardingPage(
                            image = R.drawable.board_image_3,
                            header = context.getString(R.string.board_head_3),
                            text = context.getString(R.string.board_text_3),
                            buttonText = context.getString(R.string.b_next)
                        )
                    )
                )
            }
        }
    }

    fun CoroutineScope.onButtonClick(
        pagerState: PagerState,
        navController: NavController
    ) {
        this.launch {
            if (pagerState.currentPage < pagerState.pageCount - 1) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                shared.setLocalFirstTime(false)
                navController.navigate(SignInRoute) {
                    popUpTo(SignInRoute)
                    launchSingleTop = true
                }

            }
        }
    }

    fun CoroutineScope.skipToLast(pagerState: PagerState) {
        this.launch {
            pagerState.animateScrollToPage(pagerState.pageCount)
        }
    }
}