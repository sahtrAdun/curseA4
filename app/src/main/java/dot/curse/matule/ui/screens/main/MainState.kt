package dot.curse.matule.ui.screens.main

import dot.curse.matule.domain.model.user.User

data class MainState(
    val loading: Boolean = true,
    val currentUser: User = User(),
)
