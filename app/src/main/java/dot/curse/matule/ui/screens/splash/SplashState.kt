package dot.curse.matule.ui.screens.splash

data class SplashState(
    var userId: Int = -1,
    var firstTime: Boolean = true,
    var loadingEnd: Boolean = false,
    var nextScreen: SplashStateNextScreens? = null
)

enum class SplashStateNextScreens {
    OnBoarding,
    Sign,
    Main
}