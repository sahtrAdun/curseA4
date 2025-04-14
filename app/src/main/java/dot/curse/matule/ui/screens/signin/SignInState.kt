package dot.curse.matule.ui.screens.signin

data class SignInState(
    val loading: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
