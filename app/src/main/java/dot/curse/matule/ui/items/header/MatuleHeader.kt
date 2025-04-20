package dot.curse.matule.ui.items.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dot.curse.matule.ui.utils.Routes
import dot.curse.matule.R
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.utils.OnBoardingRoute

@Composable
fun MatuleHeader(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    navController: NavController,
    menuCallBack: () -> Unit
) {
    val context = LocalContext.current

    var show by remember { mutableStateOf(false) }
    show = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.OnBoardingRoute.patch -> false
        else -> true
    }
    val label by remember(currentRoute) {
        derivedStateOf {
            when {
                currentRoute?.contains(Routes.MainRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_main)
                currentRoute?.contains(Routes.SearchResultRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_result)
                currentRoute?.contains(Routes.FavoritesRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_favor)
                currentRoute?.contains(Routes.SearchRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_search)
                currentRoute?.contains(Routes.FilterRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_filter)
                currentRoute?.contains(Routes.DetailRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_detail)
                currentRoute?.contains(Routes.SettingsRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_settings)
                currentRoute?.contains(Routes.ProfileRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_profile)
                currentRoute?.contains(Routes.CartRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_order_cart)
                currentRoute?.contains(Routes.OrderRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_order_cart)
                currentRoute?.contains(Routes.NotificationRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_notifications)
                currentRoute?.contains(Routes.OrderListRoute.patch, ignoreCase = true) == true -> context.getString(R.string.title_orders)
                else -> ""
            }
        }
    }
    var placeHolderColor by remember { mutableStateOf(Color.White) }
    placeHolderColor = when(currentRoute) {
        Routes.SplashScreenRoute.patch,
        Routes.OnBoardingRoute.patch -> MaterialTheme.colorScheme.primary

        Routes.SignInRoute.patch,
        Routes.SignUpRoute.patch -> MaterialTheme.colorScheme.surface

        else -> MaterialTheme.colorScheme.background
    }

    if (show) {
        Column(modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when(currentRoute) {
                    Routes.NotificationRoute.patch,
                    Routes.ProfileRoute.patch,
                    Routes.MainRoute.patch -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                if (SharedManager(context).getDarkTheme() == true) R.drawable.hamburger_light else R.drawable.hamburger
                            ),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(null, null) {
                                    menuCallBack()
                                }
                        )
                    }
                    Routes.SplashScreenRoute.patch,
                    Routes.OnBoardingRoute.patch -> { Spacer(Modifier.width(30.dp)) }
                    else -> {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(null, null) {
                                    println("Header current route is: $currentRoute")
                                    when(currentRoute) {
                                        Routes.SignInRoute.patch -> {
                                            navController.navigate(OnBoardingRoute) {
                                                popUpTo(OnBoardingRoute)
                                                launchSingleTop = true
                                            }
                                        }
                                        else -> navController.popBackStack()
                                    }
                                }
                        )
                    }
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(20.dp))
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(color = placeHolderColor)
        )
    }
}
