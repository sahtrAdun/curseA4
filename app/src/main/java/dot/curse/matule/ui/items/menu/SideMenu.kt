package dot.curse.matule.ui.items.menu

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dot.curse.matule.R
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.utils.CartRoute
import dot.curse.matule.ui.utils.FavoritesRoute
import dot.curse.matule.ui.utils.NotificationRoute
import dot.curse.matule.ui.utils.OrderListRoute
import dot.curse.matule.ui.utils.ProfileRoute
import dot.curse.matule.ui.utils.SettingsRoute

@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    navController: NavController,
    visible: Boolean,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    val sharedManager = SharedManager(context)
    val user = sharedManager.getLocalCurrentUser()

    val offsetX by animateIntAsState(
        targetValue = if (visible) 0 else -400,
        animationSpec = tween(250)
    )
    val color by animateColorAsState(
        targetValue = if (visible) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(400)
    )

    if (offsetX > -400) {
        Column(modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(color = color)
            .offset(x = offsetX.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
                .padding(top = 65.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(modifier = Modifier
                        .size(96.dp)
                        .background(
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                        .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (user.avatar.isNotEmpty()) {
                            AsyncImage(
                                model = user.avatar,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outlineVariant,
                                modifier = Modifier
                                    .size(72.dp)
                            )
                        }
                    }
                    Text(
                        text = user.firstName + " " + user.lastName,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                SideMenuItem(
                    modifier = Modifier.padding(top = 30.dp),
                    icon = R.drawable.bar_profile,
                    label = stringResource(R.string.title_profile)
                ) {
                    navController.navigate(ProfileRoute)
                }
                SideMenuItem(
                    icon = R.drawable.bar_bag,
                    label = stringResource(R.string.title_cart)
                ) {
                    navController.navigate(CartRoute)
                }
                SideMenuItem(
                    icon = R.drawable.bar_heart,
                    label = stringResource(R.string.title_favor)
                ) {
                    navController.navigate(FavoritesRoute)
                }

                SideMenuItem(
                    icon = R.drawable.menu_orders,
                    label = stringResource(R.string.title_orders)
                ) {
                    navController.navigate(OrderListRoute)
                }
                SideMenuItem(
                    icon = R.drawable.bar_not,
                    label = stringResource(R.string.title_notifications)
                ) {
                    navController.navigate(NotificationRoute)
                }
                SideMenuItem(
                    icon = R.drawable.menu_settings,
                    label = stringResource(R.string.title_settings)
                ) {
                    navController.navigate(SettingsRoute)
                }
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                SideMenuItem(
                    icon = R.drawable.menu_logout,
                    label = stringResource(R.string.title_logout)
                ) {
                    onExit()
                }
            }
        }
    }

}