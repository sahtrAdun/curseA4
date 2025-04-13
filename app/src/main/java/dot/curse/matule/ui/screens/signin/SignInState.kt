package dot.curse.matule.ui.screens.signin

data class SignInState(
    val email: String = "",
    val emailError: String = "null@@error@@email",
    val password: String = "",
    val passwordError: String = "null@@error@@password"
)
