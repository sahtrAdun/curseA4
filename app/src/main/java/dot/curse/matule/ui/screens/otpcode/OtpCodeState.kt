package dot.curse.matule.ui.screens.otpcode

data class OtpCodeState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null,
    val email: String? = null
)
