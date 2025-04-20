package dot.curse.matule.ui.screens.otpemail

data class OtpEmailState(
    val loading: Boolean = false,
    val email: String = "",
    val emailError: String? = null
)
