package dot.curse.matule.ui.screens.profile

import dot.curse.matule.domain.model.user.User

data class ProfileState(
    val loading: Boolean = true,
    val user: User = User(),
    val edit: Boolean = false,
    val pickImage: Boolean = false,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val phone: String = "",
    val phoneError: String? = null
)
