package dot.curse.matule.ui.utils

import kotlinx.serialization.Serializable

private const val BASE_ROUTE = "dot.curse.matule.ui.utils"
enum class Routes(val patch: String) {
    SplashScreenRoute("$BASE_ROUTE.SplashScreenRoute"),
    OnBoardingRoute("$BASE_ROUTE.OnBoardingRoute"),
    SignInRoute("$BASE_ROUTE.SignInRoute"),
    SignUpRoute("$BASE_ROUTE.SignUpRoute"),
    OTPEmailRoute("$BASE_ROUTE.OTPEmailRoute"),
    OTPCodeRoute("$BASE_ROUTE.OTPCodeRoute"),
    OTPNewPasswordRoute("$BASE_ROUTE.OTPNewPasswordRoute"),
    MainRoute("$BASE_ROUTE.MainRoute"),
    SearchResultRoute("$BASE_ROUTE.SearchResultRoute"),
    SearchRoute("$BASE_ROUTE.SearchRoute"),
    DetailRoute("$BASE_ROUTE.DetailRoute"),
    FavoritesRoute("$BASE_ROUTE.FavoritesRoute"),
    FilterRoute("$BASE_ROUTE.FilterRoute"),
    SettingsRoute("$BASE_ROUTE.SettingsRoute"),
    ProfileRoute("$BASE_ROUTE.ProfileRoute"),
    CartRoute("$BASE_ROUTE.CartRoute"),
    OrderRoute("$BASE_ROUTE.OrderRoute"),
    NotificationRoute("$BASE_ROUTE.NotificationRoute"),
    OrderListRoute("$BASE_ROUTE.OrderListRoute")
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
data object FavoritesRoute

@Serializable
data class SearchResultRoute(val filter: String)

@Serializable
data object FilterRoute

@Serializable
data class DetailRoute(val shoe: String)

@Serializable
data object SettingsRoute

@Serializable
data object ProfileRoute

@Serializable
data object CartRoute

@Serializable
data object OrderListRoute

@Serializable
data object NotificationRoute

@Serializable
data class OrderRoute(val order: String)