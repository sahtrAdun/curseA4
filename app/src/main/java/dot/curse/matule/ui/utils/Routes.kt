package dot.curse.matule.ui.utils

import dot.curse.matule.domain.model.SearchFilter
import kotlinx.serialization.Serializable

enum class Routes(val patch: String) {
    SplashScreenRoute("dot.curse.matule.ui.utils.SplashScreenRoute"),
    OnBoardingRoute("dot.curse.matule.ui.utils.OnBoardingRoute"),
    SignInRoute("dot.curse.matule.ui.utils.SignInRoute"),
    SignUpRoute("dot.curse.matule.ui.utils.SignUpRoute"),
    OTPEmailRoute("dot.curse.matule.ui.utils.OTPEmailRoute"),
    OTPCodeRoute("dot.curse.matule.ui.utils.OTPCodeRoute"),
    OTPNewPasswordRoute("dot.curse.matule.ui.utils.OTPNewPasswordRoute"),
    MainRoute("dot.curse.matule.ui.utils.MainRoute"),
    SearchResultRoute("dot.curse.matule.ui.utils.SearchResultRoute"),
    SearchRoute("dot.curse.matule.ui.utils.SearchRoute"),
    ShoeDetailRoute("dot.curse.matule.ui.utils.ShoeDetailRoute"),
}

@Serializable
data object SplashScreenRoute

@Serializable
data object OnBoardingRoute

@Serializable
data object SignInRoute

@Serializable
data object SignUpRoute

@Serializable
data object OTPEmailRoute

@Serializable
data class OTPCodeRoute(val email: String)

@Serializable
data class OTPNewPasswordRoute(val email: String)

@Serializable
data object MainRoute

@Serializable
data object SearchRoute

@Serializable
data class SearchResultRoute(val filter: String)