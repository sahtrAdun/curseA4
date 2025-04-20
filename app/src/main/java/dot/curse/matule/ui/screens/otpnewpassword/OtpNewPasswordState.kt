package dot.curse.matule.ui.screens.otpnewpassword

data class OtpNewPasswordState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordError: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,
    val passwordStrength: Int? = null,
    val passwordsEq: Boolean = passwordConfirm == password
)
