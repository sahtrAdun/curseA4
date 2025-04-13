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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.ui.items.MatuleHeader
import dot.curse.matule.ui.screens.boarding.OnBoardingViewModel
import dot.curse.matule.ui.screens.boarding.comp.OnBoardingScreen
import dot.curse.matule.ui.screens.splash.SplashViewModel
import dot.curse.matule.ui.screens.splash.comp.SplashScreen
import dot.curse.matule.ui.theme.MatuleTheme
import dot.curse.matule.ui.utils.MainRoute
import dot.curse.matule.ui.utils.OnBoardingRoute
import dot.curse.matule.ui.utils.SignInRoute
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

            val backColor = MaterialTheme.colorScheme.background
            var label by remember { mutableStateOf("") }
            var show by remember { mutableStateOf(false) }
            var headerPlaceholder by remember { mutableStateOf(backColor) }

            var background by remember { mutableStateOf(backColor) }

            var darkTheme by remember { mutableStateOf(
                SharedManager(this).getDarkTheme()
            ) }

            MatuleTheme(darkTheme = darkTheme) {
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    containerColor = background,
                    topBar = {
                        MatuleHeader(
                            show = show,
                            label = label,
                            placeholder = headerPlaceholder
                        )
                    },
                    bottomBar = {}
                ) { pd->
                    NavHost(
                        modifier = Modifier.padding(pd),
                        navController = navController,
                        startDestination = SplashScreenRoute,
                        enterTransition = {
                            fadeIn(animationSpec = tween(100))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(100))
                        },
                    ) {

                        composable<SplashScreenRoute> {
                            val viewModel = hiltViewModel<SplashViewModel>()
                            val headerState by viewModel.headerState.collectAsStateWithLifecycle()
                            label = headerState.label
                            show = headerState.show
                            headerPlaceholder = MaterialTheme.colorScheme.primary
                            loading = !viewModel.state.value.loadingEnd

                            SplashScreen(
                                viewModel = viewModel,
                                navController = navController
                            ) { loading = it }
                        }

                        composable<OnBoardingRoute> {
                            val viewModel = hiltViewModel<OnBoardingViewModel>()
                            val headerState by viewModel.headerState.collectAsStateWithLifecycle()
                            label = headerState.label
                            show = headerState.show
                            headerPlaceholder = MaterialTheme.colorScheme.primary
                            background = MaterialTheme.colorScheme.tertiary
                            loading = false

                            OnBoardingScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable<SignInRoute> {

                            background = MaterialTheme.colorScheme.surface
                            headerPlaceholder = background
                            // TODO
                        }

                        composable<MainRoute> {

                            background = MaterialTheme.colorScheme.background
                            headerPlaceholder = background
                            // TODO
                        }

                    }
                }
            }
        }
    }
}
