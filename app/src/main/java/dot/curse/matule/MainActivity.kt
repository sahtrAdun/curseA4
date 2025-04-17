package dot.curse.matule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import dot.curse.matule.ui.items.header.MatuleHeader
import dot.curse.matule.ui.screens.boarding.OnBoardingViewModel
import dot.curse.matule.ui.screens.boarding.comp.OnBoardingScreen
import dot.curse.matule.ui.screens.main.MainViewModel
import dot.curse.matule.ui.screens.main.comp.MainScreen
import dot.curse.matule.ui.screens.otpcode.OtpCodeViewModel
import dot.curse.matule.ui.screens.otpcode.comp.OtpCodeScreen
import dot.curse.matule.ui.screens.otpemail.OtpEmailViewModel
import dot.curse.matule.ui.screens.otpemail.comp.OtpEmailScreen
import dot.curse.matule.ui.screens.otpnewpassword.OtpNewPasswordViewModel
import dot.curse.matule.ui.screens.otpnewpassword.comp.OtpNewPasswordScreen
import dot.curse.matule.ui.screens.signin.SignInViewModel
import dot.curse.matule.ui.screens.signin.comp.SignInScreen
import dot.curse.matule.ui.screens.signup.SignUpViewModel
import dot.curse.matule.ui.screens.signup.comp.SignUpScreen
import dot.curse.matule.ui.screens.splash.SplashViewModel
import dot.curse.matule.ui.screens.splash.comp.SplashScreen
import dot.curse.matule.ui.theme.MatuleTheme
import dot.curse.matule.ui.utils.AppLanguage.initializeLanguage
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.OTPCodeRoute
import dot.curse.matule.ui.utils.OTPEmailRoute
import dot.curse.matule.ui.utils.OTPNewPasswordRoute
import dot.curse.matule.ui.utils.OnBoardingRoute
import dot.curse.matule.ui.utils.Routes
import dot.curse.matule.ui.utils.SignInRoute
import dot.curse.matule.ui.utils.SignUpRoute
import dot.curse.matule.ui.utils.SplashScreenRoute

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
            var darkTheme by remember { mutableStateOf(
                SharedManager(this).getDarkTheme()
            ) }
            this@MainActivity.initializeLanguage()

            MatuleTheme(darkTheme = darkTheme) {
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    containerColor = background,
                    topBar = {
                        MatuleHeader(
                            currentRoute = currentRoute?.destination?.route?: Routes.SplashScreenRoute.patch,
                            navController = navController
                        )
                    },
                    bottomBar = {}
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
                            background = MaterialTheme.colorScheme.tertiary
                            val viewModel = hiltViewModel<OnBoardingViewModel>()

                            OnBoardingScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SignInRoute> {
                            loading = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<SignInViewModel>()

                            SignInScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SignUpRoute> {
                            loading = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<SignUpViewModel>()

                            SignUpScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPEmailRoute> {
                            loading = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpEmailViewModel>()

                            OtpEmailScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPCodeRoute> { stack ->
                            loading = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpCodeViewModel>()

                            val email = stack.arguments?.getString("email")?: ""
                            viewModel.updateEmail(email)

                            OtpCodeScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<OTPNewPasswordRoute> { stack ->
                            loading = false
                            background = MaterialTheme.colorScheme.surface
                            val viewModel = hiltViewModel<OtpNewPasswordViewModel>()

                            val email = stack.arguments?.getString("email")?: ""
                            viewModel.updateEmail(email)

                            OtpNewPasswordScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<MainRoute> {
                            loading = false
                            background = MaterialTheme.colorScheme.background
                            val viewModel = hiltViewModel<MainViewModel>()

                            MainScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                    }
                }
            }
        }
    }
}
