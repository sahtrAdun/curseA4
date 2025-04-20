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
import dot.curse.matule.ui.utils.CartRoute
import dot.curse.matule.ui.utils.FavoritesRoute
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.NotificationRoute
import dot.curse.matule.ui.utils.ProfileRoute

@Composable
fun MatuleFooter(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String
) {
    var show by remember { mutableStateOf(false) }
    show = when {
        currentRoute.contains(Routes.SplashScreenRoute.patch, true) ||
        currentRoute.contains(Routes.OnBoardingRoute.patch, true) ||
        currentRoute.contains(Routes.SignInRoute.patch, true) ||
        currentRoute.contains(Routes.SignUpRoute.patch, true) ||
        currentRoute.contains(Routes.OTPCodeRoute.patch, true) ||
        currentRoute.contains(Routes.OTPEmailRoute.patch, true) ||
        currentRoute.contains(Routes.OTPNewPasswordRoute.patch, true) ||
        currentRoute.contains(Routes.SearchResultRoute.patch, true) ||
        currentRoute.contains(Routes.SearchRoute.patch, true) ||
        currentRoute.contains(Routes.FavoritesRoute.patch, true) ||
        currentRoute.contains(Routes.FilterRoute.patch, true) ||
        currentRoute.contains(Routes.DetailRoute.patch, true) ||
        currentRoute.contains(Routes.SettingsRoute.patch, true) ||
        currentRoute.contains(Routes.CartRoute.patch, true) ||
        currentRoute.contains(Routes.OrderRoute.patch, true) ||
        currentRoute.contains(Routes.OrderListRoute.patch, true) ||
        currentRoute.contains(Routes.OnBoardingRoute.patch, true) -> false
        else -> true
    }
    if (show) {
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
                        selected = currentRoute == Routes.CartRoute.patch,
                    ) {
                        navController.navigate(CartRoute) {
                            popUpTo(CartRoute) { inclusive = true }
                        }
                    }
                }
                Spacer(Modifier.weight(3f))
                FooterIcon(
                    icon = R.drawable.bar_not,
                    selected = currentRoute == Routes.NotificationRoute.patch,
                ) {
                    navController.navigate(NotificationRoute) {
                        popUpTo(NotificationRoute) { inclusive = true }
                    }
                }
                Spacer(Modifier.weight(2f))
                FooterIcon(
                    icon = R.drawable.bar_profile,
                    selected = currentRoute == Routes.ProfileRoute.patch,
                ) {
                    navController.navigate(ProfileRoute) {
                        popUpTo(ProfileRoute) { inclusive = true }
                    }
                }
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