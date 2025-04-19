package dot.curse.matule.ui.items.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dot.curse.matule.ui.utils.Routes
import dot.curse.matule.R
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.ui.utils.FavoritesRoute
import dot.curse.matule.ui.utils.MainRoute

@Composable
fun MatuleFooter(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String
) {
    var show by remember { mutableStateOf(false) }
    show = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.SearchResultRoute.patch,
        Routes.SearchRoute.patch,
        Routes.FavoritesRoute.patch,
        Routes.OnBoardingRoute.patch -> false
        else -> true
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Adaptive()
            .adaptiveElementWidthMedium()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterIcon(
                icon = R.drawable.bar_home,
                selected = currentRoute == Routes.MainRoute.patch,
            ) {
                navController.navigate(MainRoute) {
                    popUpTo(MainRoute) { inclusive = true }
                }
            }
            Spacer(Modifier.weight(2f))
            FooterIcon(
                icon = R.drawable.bar_heart,
                selected = currentRoute == Routes.FavoritesRoute.patch,
            ) {
                navController.navigate(FavoritesRoute) {
                    popUpTo(FavoritesRoute) { inclusive = true }
                }
            }
            Spacer(Modifier.weight(3f))
            Box(modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                FooterIcon(
                    icon = R.drawable.bar_bag,
                    allowChange = false,
                    selected = false//currentRoute == Routes.MainRoute.patch,
                ) {

                }
            }
            Spacer(Modifier.weight(3f))
            FooterIcon(
                icon = R.drawable.bar_not,
                selected = false//currentRoute == Routes.MainRoute.patch,
            ) {

            }
            Spacer(Modifier.weight(2f))
            FooterIcon(
                icon = R.drawable.bar_profile,
                selected = false//currentRoute == Routes.MainRoute.patch,
            ) {

            }
        }
    }
}

@Composable
private fun FooterIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    selected: Boolean,
    allowChange: Boolean = true,
    onClick: () -> Unit,
) {
    Icon(
        imageVector = ImageVector.vectorResource(icon),
        contentDescription = null,
        tint = if (selected && allowChange) MaterialTheme.colorScheme.primary else Color.Unspecified,
        modifier = modifier.clickable(null, null) {
            if (!selected) { onClick() }
        }
    )
}