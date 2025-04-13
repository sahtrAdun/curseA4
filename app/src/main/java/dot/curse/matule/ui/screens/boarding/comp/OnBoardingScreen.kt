package dot.curse.matule.ui.screens.boarding.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.ui.screens.boarding.OnBoardingViewModel
import dot.curse.matule.R
import dot.curse.matule.ui.items.MatuleButton
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel<OnBoardingViewModel>(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { state.pages.size }

    Column(modifier = modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.tertiary
            )
        )),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
            ) { page ->
                val pageVisible = page == pagerState.currentPage
                val alpha by animateFloatAsState(
                    targetValue = if (pageVisible) 1f else 0f,
                    animationSpec = tween(250)
                )

                AnimatedVisibility(
                    visible = pageVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha)
                        .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
                    ) {
                        Image(
                            painter = painterResource(state.pages[page].image),
                            contentDescription = null,
                            modifier = Adaptive().adaptiveImageWidth()
                                .aspectRatio(1f)
                                .padding(bottom = 40.dp)
                        )
                        Text(
                            text = state.pages[page].header,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                        )
                        Text(
                            text = state.pages[page].text?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .height(45.dp)
                        )
                    }
                }

            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                state.pages.forEachIndexed { index, _ ->
                    pagerState.BoardingRowElement(index)
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MatuleButton(
                modifier = Adaptive().adaptiveButtonWidth(),
                text = state.pages[pagerState.currentPage].buttonText,
                background = MaterialTheme.colorScheme.onPrimary,
                tint = MaterialTheme.colorScheme.scrim,
                action = { viewModel.apply { scope.onButtonClick(pagerState, navController) } }
            )
            if (pagerState.currentPage == 0) {
                Text(
                    text = stringResource(R.string.b_text_skip),
                    color = Color(0xFFBEBEBE),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .clickable(null ,null) { viewModel.apply { scope.skipToLast(pagerState) }
                    }
                )
            }
            Spacer(Modifier.height(30.dp))
        }
    }
}