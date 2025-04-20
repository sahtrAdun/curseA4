package dot.curse.matule.ui.screens.signup

data class SignUpState(
    val loading: Boolean = false,
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmed: Boolean = false
)
