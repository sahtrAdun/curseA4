package dot.curse.matule.ui.utils

import kotlinx.serialization.Serializable

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