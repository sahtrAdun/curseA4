package dot.curse.matule.ui.screens.boarding.comp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerState.BoardingRowElement(
    targetPage: Int
) {
    val animatedWidth by animateIntAsState(
        targetValue = if (targetPage == currentPage) 43 else 28,
        animationSpec = tween(250),
    )
    val animateColor by animateColorAsState(
        targetValue = if (targetPage == currentPage) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
        animationSpec = tween(250)
    )
    Box(modifier = Modifier
        .height(6.dp)
        .width(animatedWidth.dp)
        .background(
            color = animateColor,
            shape = RoundedCornerShape(12.dp)
        )
    )
}