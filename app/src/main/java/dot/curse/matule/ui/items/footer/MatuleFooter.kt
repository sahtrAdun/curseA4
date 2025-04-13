package dot.curse.matule.ui.items.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dot.curse.matule.ui.utils.Routes

@Composable
fun MatuleFooter(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String
) {
    var show by remember { mutableStateOf(false) }
    show = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.OnBoardingRoute.patch -> false
        else -> true
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            // TODO 1
            Spacer(Modifier.weight(2f))
            // TODO 2
            Spacer(Modifier.weight(3f))
            // TODO 3
            Spacer(Modifier.weight(3f))
            // TODO 4
            Spacer(Modifier.weight(2f))
            // TODO 5
        }
    }
}