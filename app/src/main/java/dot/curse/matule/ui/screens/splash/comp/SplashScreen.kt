package dot.curse.matule.ui.screens.splash.comp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dot.curse.matule.ui.screens.splash.SplashViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.ui.screens.splash.SplashStateNextScreens
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.OnBoardingRoute
import dot.curse.matule.ui.utils.SignInRoute
import dot.curse.matule.ui.utils.SplashScreenRoute
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController,
    endCall: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.loadingEnd) {
        if (state.loadingEnd == true) {
            delay(125)
            endCall(false)
        }
    }

    LaunchedEffect(Unit) {
        state.nextScreen?.let {
            when (state.nextScreen) {
                SplashStateNextScreens.OnBoarding -> {
                    println("Navigating to OnBoardingRoute")
                    navController.navigate(OnBoardingRoute) { popUpTo(SplashScreenRoute) { inclusive = true } }
                }
                SplashStateNextScreens.Sign  -> {
                    println("Navigating to SignInRoute")
                    navController.navigate(SignInRoute) { popUpTo(SplashScreenRoute) { inclusive = true } }
                }
                SplashStateNextScreens.Main  -> {
                    println("Navigating to MainRoute")
                    navController.navigate(MainRoute) { popUpTo(SplashScreenRoute) { inclusive = true } }
                }
                else -> {}
            }
        }

    }
}