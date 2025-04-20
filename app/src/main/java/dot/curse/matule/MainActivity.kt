package dot.curse.matule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.model.SearchFilter
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.ui.items.footer.MatuleFooter
import dot.curse.matule.ui.items.header.MatuleHeader
import dot.curse.matule.ui.items.menu.SideMenu
import dot.curse.matule.ui.screens.boarding.OnBoardingViewModel
import dot.curse.matule.ui.screens.boarding.comp.OnBoardingScreen
import dot.curse.matule.ui.screens.cart.CartViewModel
import dot.curse.matule.ui.screens.cart.comp.CartScreen
import dot.curse.matule.ui.screens.detail.DetailViewModel
import dot.curse.matule.ui.screens.detail.comp.DetailScreen
import dot.curse.matule.ui.screens.favorites.comp.FavoritesScreen
import dot.curse.matule.ui.screens.favorites.FavoritesViewModel
import dot.curse.matule.ui.screens.filter.FilterViewModel
import dot.curse.matule.ui.screens.filter.comp.FilterScreen
import dot.curse.matule.ui.screens.main.MainViewModel
import dot.curse.matule.ui.screens.main.comp.MainScreen
import dot.curse.matule.ui.screens.notification.NotificationViewModel
import dot.curse.matule.ui.screens.notification.comp.NotificationScreen
import dot.curse.matule.ui.screens.order.OrderViewModel
import dot.curse.matule.ui.screens.order.comp.OrderScreen
import dot.curse.matule.ui.screens.orderlist.OrderListViewModel
import dot.curse.matule.ui.screens.orderlist.comp.OrderListScreen
import dot.curse.matule.ui.screens.otpcode.OtpCodeViewModel
import dot.curse.matule.ui.screens.otpcode.comp.OtpCodeScreen
import dot.curse.matule.ui.screens.otpemail.OtpEmailViewModel
import dot.curse.matule.ui.screens.otpemail.comp.OtpEmailScreen
import dot.curse.matule.ui.screens.otpnewpassword.OtpNewPasswordViewModel
import dot.curse.matule.ui.screens.otpnewpassword.comp.OtpNewPasswordScreen
import dot.curse.matule.ui.screens.profile.ProfileViewModel
import dot.curse.matule.ui.screens.profile.comp.ProfileScreen
import dot.curse.matule.ui.screens.search.SearchViewModel
import dot.curse.matule.ui.screens.search.comp.SearchScreen
import dot.curse.matule.ui.screens.searchresult.SearchResultViewModel
import dot.curse.matule.ui.screens.searchresult.comp.SearchResultScreen
import dot.curse.matule.ui.screens.settings.SettingsViewModel
import dot.curse.matule.ui.screens.settings.comp.SettingsScreen
import dot.curse.matule.ui.screens.signin.SignInViewModel
import dot.curse.matule.ui.screens.signin.comp.SignInScreen
import dot.curse.matule.ui.screens.signup.SignUpViewModel
import dot.curse.matule.ui.screens.signup.comp.SignUpScreen
import dot.curse.matule.ui.screens.splash.SplashViewModel
import dot.curse.matule.ui.screens.splash.comp.SplashScreen
import dot.curse.matule.ui.theme.MatuleTheme
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.ui.utils.AppLanguage.changeLanguage
import dot.curse.matule.ui.utils.AppLanguage.initializeLanguage
import dot.curse.matule.ui.utils.CartRoute
import dot.curse.matule.ui.utils.DetailRoute
import dot.curse.matule.ui.utils.FavoritesRoute
import dot.curse.matule.ui.utils.FilterRoute
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.NotificationRoute
import dot.curse.matule.ui.utils.OTPCodeRoute
import dot.curse.matule.ui.utils.OTPEmailRoute
import dot.curse.matule.ui.utils.OTPNewPasswordRoute
import dot.curse.matule.ui.utils.OnBoardingRoute
import dot.curse.matule.ui.utils.OrderListRoute
import dot.curse.matule.ui.utils.OrderRoute
import dot.curse.matule.ui.utils.ProfileRoute
import dot.curse.matule.ui.utils.Routes
import dot.curse.matule.ui.utils.SearchResultRoute
import dot.curse.matule.ui.utils.SearchRoute
import dot.curse.matule.ui.utils.SettingsRoute
import dot.curse.matule.ui.utils.SignInRoute
import dot.curse.matule.ui.utils.SignUpRoute
import dot.curse.matule.ui.utils.SplashScreenRoute
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var loading by mutableStateOf(true)
        installSplashScreen().apply {
            setKeepOnScreenCondition { loading == true }
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentRoute by navController.currentBackStackEntryAsState()
            currentRoute?.destination?.route?.let { route ->
                remember(route) { route }
            }
            var background by remember { mutableStateOf(Color.White) }
            var menuVisible by remember { mutableStateOf(false) }
            var darkTheme by remember { mutableStateOf(
                SharedManager(this).getDarkTheme()
            ) }
            this@MainActivity.initializeLanguage()

            MatuleTheme(darkTheme = darkTheme) {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .clickable(null ,null) { menuVisible = false },
                    containerColor = background,
                    topBar = {
                        MatuleHeader(
                            currentRoute = currentRoute?.destination?.route?: Routes.SplashScreenRoute.patch,
                            navController = navController
                        ) { menuVisible = !menuVisible }
                    },
                    bottomBar = {
                        MatuleFooter(
                            currentRoute = currentRoute?.destination?.route?: Routes.SplashScreenRoute.patch,
                            navController = navController
                        )
                    }
                ) { pd->
                    NavHost(
                        modifier = Modifier.padding(pd),
                        navController = navController,
                        startDestination = SplashScreenRoute,
                        enterTransition = { fadeIn(animationSpec = tween(250)) },
                        exitTransition = { fadeOut(animationSpec = tween(250)) },
                    ) {
                        composable<SplashScreenRoute> {
                            val viewModel = hiltViewModel<SplashViewModel>()

                            SplashScreen(
                                viewModel = viewModel,
                                navController = navController
                            ) { loading = it }
                        }

                        composable<OnBoardingRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.tertiary
                            val viewModel = hiltViewModel<OnBoardingViewModel>()

                            OnBoardingScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SignInRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<SignInViewModel>()

                            SignInScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SignUpRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<SignUpViewModel>()

                            SignUpScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPEmailRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpEmailViewModel>()

                            OtpEmailScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPCodeRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpCodeViewModel>()

                            stack.arguments?.getString("email")?.let {
                                viewModel.updateEmail(it)
                            }

                            OtpCodeScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPNewPasswordRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpNewPasswordViewModel>()

                            stack.arguments?.getString("email")?.let {
                                viewModel.updateEmail(it)
                            }

                            OtpNewPasswordScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<MainRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<MainViewModel>()

                            MainScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SearchResultRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<SearchResultViewModel>()

                            val filter = stack.arguments?.getString("filter")?.let {
                                Json.decodeFromString<SearchFilter>(it)
                            }?: SearchFilter()
                            viewModel.parseSearchFilter(filter)

                            SearchResultScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SearchRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<SearchViewModel>()

                            SearchScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<FavoritesRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<FavoritesViewModel>()

                            FavoritesScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<FilterRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<FilterViewModel>()

                            FilterScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<ProfileRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<ProfileViewModel>()

                            ProfileScreen(
                                viewModel = viewModel
                            )
                        }

                        composable<SettingsRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<SettingsViewModel>()

                            SettingsScreen(
                                viewModel = viewModel,
                                callBackTheme = { darkTheme = it },
                                callBackLang = { this@MainActivity.changeLanguage(it) }
                            )
                        }

                        composable<CartRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<CartViewModel>()

                            CartScreen(
                                viewModel = viewModel,
                                navController = navController,
                            )
                        }

                        composable<OrderRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<OrderViewModel>()

                            val order = stack.arguments?.getString("order")?.let {
                                Json.decodeFromString<Order>(it)
                            }?: Order()
                            viewModel.parseOrder(order)

                            OrderScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OrderListRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<OrderListViewModel>()

                            OrderListScreen(
                                viewModel = viewModel,
                            )
                        }

                        composable<NotificationRoute> {
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<NotificationViewModel>()

                            NotificationScreen(
                                viewModel = viewModel
                            )
                        }

                        composable<DetailRoute> { stack ->
                            loading = false
                            menuVisible = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<DetailViewModel>()

                            val shoe = stack.arguments?.getString("shoe")?.let {
                                Json.decodeFromString<Shoe>(it)
                            }?: Shoe()
                            viewModel.parseShoe(shoe)

                            DetailScreen(
                                viewModel = viewModel,
                                navController = navController,
                            )
                        }
                    }
                }
                SideMenu(modifier = Adaptive()
                    .adaptiveElementWidthSmall()
                    .animateContentSize(),
                    navController = navController,
                    visible = menuVisible
                ) {
                    menuVisible = false
                    SharedManager(this).clearUserData()
                    navController.navigate(SignInRoute) {
                        popUpTo(SignInRoute) { inclusive = true }
                    }
                }
            }
        }
    }
}
